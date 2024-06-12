package pl.panczyk.arkadiusz.producers.replying

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate


@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.consumer.group-id}")
    val groupId: String,
    @Value("\${spring.kafka.topic.name}")
    val topicName: String,
    @Value("\${spring.kafka.servers}")
    val servers: String
) {

    @Bean
    fun replyingKafkaTemplate(
        pf: ProducerFactory<String, String>,
        factory: ConcurrentKafkaListenerContainerFactory<String, String>
    ): ReplyingKafkaTemplate<String, String, String> {
        val replyContainer: ConcurrentMessageListenerContainer<String, String> = factory.createContainer(topicName)
        replyContainer.containerProperties.isMissingTopicsFatal = false
        replyContainer.containerProperties.setGroupId(groupId)
        return ReplyingKafkaTemplate(pf, replyContainer)
    }

    @Bean
    fun replyTemplate(
        pf: ProducerFactory<String, String>,
        factory: ConcurrentKafkaListenerContainerFactory<String, String>
    ): KafkaTemplate<String, String> {
        val kafkaTemplate: KafkaTemplate<String, String> = KafkaTemplate(pf)
        factory.containerProperties.isMissingTopicsFatal = false
        factory.setReplyTemplate(kafkaTemplate)
        return kafkaTemplate
    }
}