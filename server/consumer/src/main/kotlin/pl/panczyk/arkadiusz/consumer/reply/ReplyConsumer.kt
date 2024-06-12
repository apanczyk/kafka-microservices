package pl.panczyk.arkadiusz.consumer.reply

import com.google.gson.Gson
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import pl.panczyk.arkadiusz.consumer.model.Data
import pl.panczyk.arkadiusz.consumer.mongo.LogMongo

@Component
class ReplyConsumer {
    @KafkaListener(topics = ["\${spring.kafka.topic.name}"], groupId = "\${spring.kafka.consumer.group-id}")
    @SendTo
    fun handle(response: String): String {
        val json = Gson().fromJson(response, Data::class.java)
        val result = LogMongo("nokey", json, "kafkaReply")
        return Gson().toJson(result)
    }
}