package pl.panczyk.arkadiusz.producers.replying

import com.google.gson.Gson
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service
import pl.panczyk.arkadiusz.producers.model.Data
import pl.panczyk.arkadiusz.producers.mongo.DataMongo
import pl.panczyk.arkadiusz.producers.mongo.DataRepository
import pl.panczyk.arkadiusz.producers.mongo.LogMongo
import pl.panczyk.arkadiusz.producers.mongo.LogRepository


@Service
class KafkaReplyProducer(
    private  val replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>,
    private val config: KafkaConfig,
    private val dataRepository: DataRepository,
    private val logRepository: LogRepository
) {

    fun send(data: List<Data>, actual: Int, of: Int): Any {
        val request = dataRepository.save(DataMongo(data))
        val json = Gson().toJson(request)
        val record: ProducerRecord<String, String> =
            ProducerRecord(config.topicName, null, request.id, json)
        val future = replyingKafkaTemplate.sendAndReceive(record)
        val response: ConsumerRecord<String, String> = future.get()
        val jsonResponse = Gson().fromJson(response.value(), LogMongo.InternalData::class.java)
        log.info("Body: ${record.value()}")
        logRepository.save(
            LogMongo(jsonResponse, "kafkaReply")
        )
        return ResponseEntity<Any>(response.value(), HttpStatus.OK)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(KafkaReplyProducer::class.java)
    }
}