package pl.panczyk.arkadiusz.producers.kafka

import com.google.gson.Gson
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.panczyk.arkadiusz.producers.model.Data
import pl.panczyk.arkadiusz.producers.mongo.DataMongo
import pl.panczyk.arkadiusz.producers.mongo.DataRepository
import pl.panczyk.arkadiusz.producers.replying.KafkaConfig

@Service
class ApnKafkaProducer(
    val config: KafkaConfig,
    val dataRepository: DataRepository
) {
    private val producer: KafkaProducer<String, String> = KafkaProducer<String, String>(
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to config.servers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
    )

    fun send(data: List<Data>, actual: Int, of: Int): RecordMetadata? {
        val request = dataRepository.save(DataMongo(data))
        val json = Gson().toJson(request)
        val future = producer.send(ProducerRecord(config.topicName, request.id, json))
        LOGGER.info("Produced: ${request.id} with status: $actual/$of")
        return future.get()
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ApnKafkaProducer::class.java)
    }
}