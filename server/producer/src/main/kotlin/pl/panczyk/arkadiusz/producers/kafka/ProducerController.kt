package pl.panczyk.arkadiusz.producers.kafka

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.requestreply.RequestReplyFuture
import org.springframework.web.bind.annotation.*
import pl.panczyk.arkadiusz.producers.model.Data
import pl.panczyk.arkadiusz.producers.replying.KafkaReplyProducer
import java.util.concurrent.ExecutionException


@RestController
@RequestMapping("/api")
class ProducerController(
    val producer: ApnKafkaProducer,
    val replyProducer: KafkaReplyProducer
) {

    @GetMapping("/auto")
    fun autoCreate(@RequestParam toSend: Int = 10000) {
        val data = listOf(Data(
            "John", "Doe", "Name", 10))

        for (send in 0..toSend)
            producer.send(data, send, toSend)
    }

    @GetMapping("/multiple")
    fun multiple(@RequestParam toSend: Int = 10000) {
        val data = Data(
            firstName = "test",
            surname  = "test",
            name = "test",
            age = 100)
        val multipleData = mutableListOf<Data>()
        for (i in 0..25) {
            multipleData += data
        }
        val multipleDataList = multipleData.toList()

        for (send in 0..toSend)
            producer.send(multipleDataList, send, toSend)
    }

    @GetMapping("/async")
    fun async(@RequestParam toSend: Int = 10000) = runBlocking {
        var send = 0
        val data = listOf(Data("John", "Doe", "Name", 10))

        while (send < toSend) {
            launch {
                producer.send(data, send, toSend)
            }
            send++
        }
    }

    @GetMapping("/request-result")
    fun requestResult(@RequestParam toSend: Int = 10000) {
        val data = listOf(Data(
            "John", "Doe", "Name", 10))

        for (send in 0..toSend) {
            replyProducer.send(data, send, toSend)
        }
    }
}