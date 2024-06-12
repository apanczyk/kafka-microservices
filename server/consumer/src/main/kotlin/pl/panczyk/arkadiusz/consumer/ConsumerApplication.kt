package pl.panczyk.arkadiusz.consumer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import pl.panczyk.arkadiusz.consumer.kafka.ApnKafkaConsumer

@SpringBootApplication
@EnableMongoRepositories
class ConsumerApplication(
    val consumer: ApnKafkaConsumer,
    val config: Config
) {
    @EventListener(ApplicationReadyEvent::class)
    fun initConsumer() {
        consumer.start(config.threads)
    }
}

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}
