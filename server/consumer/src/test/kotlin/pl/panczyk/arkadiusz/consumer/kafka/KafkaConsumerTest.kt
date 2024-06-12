package pl.panczyk.arkadiusz.consumer.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KafkaConsumerTest {

    @Autowired
    lateinit var consumer: ApnKafkaConsumer

//    @Test
//    fun shouldCommunicateUsingKafka() {
//        var send = 0
//        val toSend = 100000
//        consumer.start(2)
//
//        while (send < toSend) {
//            producer.send(Data("John", "Doe", "Name", 10, LocalDateTime.now()), send, toSend)
//            send++
////            Thread.sleep(1000)
//        }
//        producer.send(Data("END", "DATA", "BYE", 0, LocalDateTime.now()), send, toSend)
//        while(consumer.isRunning())
//            Thread.sleep(1000)
//    }
}