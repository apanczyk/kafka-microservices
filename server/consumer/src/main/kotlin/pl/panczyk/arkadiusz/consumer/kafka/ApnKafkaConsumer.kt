package pl.panczyk.arkadiusz.consumer.kafka

import com.google.gson.Gson
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.panczyk.arkadiusz.consumer.model.Data
import pl.panczyk.arkadiusz.consumer.mongo.LogMongo
import pl.panczyk.arkadiusz.consumer.mongo.LogRepository
import java.time.Duration
import java.util.function.Consumer


@Service
class ApnKafkaConsumer(
    val logRepository: LogRepository,
    val config: KafkaConfig
) {
    private var running = false

    fun start(
        threadsCount: Int = 2
    ) {
        running = true
        for (i in 1..threadsCount) {
            Thread({
                KafkaConsumer<String, String>(
                    mapOf<String, Any>(
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to config.servers,
                        ConsumerConfig.GROUP_ID_CONFIG to config.groupId,
                        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
                    )
                ).use { consumer ->
                    consumer.subscribe(listOf(config.topicName))
                    while (running) {
                        val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
                        saveAndLog(records, config.topicName, i)
                        consumer.commitSync()
                    }
                }
            }, "Consumer-$i").start()
        }
    }

    fun saveAndLog(records: ConsumerRecords<String, String>, topic: String?, threadId: Int) {
        records.records(topic)
            .forEach(Consumer<ConsumerRecord<String, String>> { record: ConsumerRecord<String, String> ->
                run {
                    val json = Gson().fromJson(record.value(), Data::class.java)
                    log.info("Body: ${record.value()}")
                    logRepository.save(
                        LogMongo(record.key(), json, "kafka")
                    )
                    log(record, threadId)
                }
            })
    }

    fun stop() {
        running = false
    }

    fun isRunning() = running

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ApnKafkaConsumer::class.java)

        fun log(record: ConsumerRecord<String, String>, threadId: Int) {
            log.info("Consumed: key: ${record.key()}, value: ${record.value()}, partition: ${record.partition()}, threadId: $threadId")
        }
    }
}
