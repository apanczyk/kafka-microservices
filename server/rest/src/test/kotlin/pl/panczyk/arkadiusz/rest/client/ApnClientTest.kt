package pl.panczyk.arkadiusz.rest.client

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.panczyk.arkadiusz.rest.model.Data

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApnClientTest {

    @Autowired
    lateinit var apnClient: ApnClient

//    @Test
//    fun shouldCommunicateRest() {
//        var send = 0
//        val toSend = 10
//
//        while (send < toSend) {
//            apnClient.send(listOf(Data("John", "Doe", "Name", 10)), send, toSend)
//            send++
//        }
//    }
}