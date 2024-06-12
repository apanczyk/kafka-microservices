package pl.panczyk.arkadiusz.consumer.kafka

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KafkaConfig(
    @Value("\${spring.kafka.topic.name}")
    val topicName: String,
    @Value("\${spring.kafka.servers}")
    val servers: String,
    @Value("\${spring.kafka.consumer.group-id}")
    val groupId: String,
)
