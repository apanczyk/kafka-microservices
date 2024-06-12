package pl.panczyk.arkadiusz.rest.client

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.panczyk.arkadiusz.rest.model.Data

@RestController
@RequestMapping("/api")
class RestController(
    val client: ApnClient
) {
    @GetMapping
    fun get(@RequestParam toSend: Int = 10000) {
        val data = listOf(Data("John", "Doe", "Name", 10))

        for (send in 0..toSend)
            client.send(data, send, toSend)
    }

    @GetMapping("/multiple")
    fun multiple(@RequestParam toSend: Int = 10000) {
        var send = 0
        val data = Data("John", "Doe", "Name", 10)
        val multipleData = mutableListOf<Data>()
        for (i in 0..25) {
            multipleData += data
        }
        val multipleData2 = multipleData.toList()

        while (send < toSend) {
            client.send(multipleData2, send, toSend)
            send++
        }
    }

    @GetMapping("/async")
    fun async(@RequestParam toSend: Int = 10000) = runBlocking {
        var send = 0
        val data = listOf(Data("John", "Doe", "Name", 10))

        while (send < toSend) {
            launch {
                client.send(data, send, toSend)
            }
            send++
        }
    }

    @GetMapping("/withReturn")
    fun withReturn(@RequestParam toSend: Int = 10000) = runBlocking {
        var send = 0
        val data = listOf(Data("John", "Doe", "Name", 10))

        while (send < toSend) {
            launch {
                client.sendWithResponse(data, send, toSend)
            }
            send++
        }
    }

}