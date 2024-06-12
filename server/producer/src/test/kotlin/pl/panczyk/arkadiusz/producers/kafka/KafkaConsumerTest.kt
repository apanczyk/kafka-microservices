package pl.panczyk.arkadiusz.producers.kafka

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.panczyk.arkadiusz.producers.model.Data

@SpringBootTest
class KafkaConsumerTest {

    @Autowired
    lateinit var producer: ApnKafkaProducer

//    @Test
//    fun shouldCommunicateUsingKafka() {
//        var send = 0
//        val toSend = 10
//
//        while (send < toSend) {
//            producer.send(Data("John", "Doe", "Name", 10), send, toSend)
//            send++
//        }
//        producer.send(Data("END", "DATA", "BYE", 0), send, toSend)
//    }
}