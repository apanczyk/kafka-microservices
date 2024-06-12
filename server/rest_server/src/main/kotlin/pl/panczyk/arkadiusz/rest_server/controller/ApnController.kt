package pl.panczyk.arkadiusz.rest_server.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.panczyk.arkadiusz.rest_server.model.Data
import pl.panczyk.arkadiusz.rest_server.mongo.LogMongo
import pl.panczyk.arkadiusz.rest_server.mongo.LogRepository

@RestController
@RequestMapping("/api")
class ApnController(
    val logRepository: LogRepository
) {

    @PostMapping
    fun post(@RequestBody dataMongo: Data) {
        saveAndLog(dataMongo)
    }

    @PostMapping("/return")
    fun withReturn(@RequestBody dataMongo: Data): Data {
        return dataMongo
    }

    fun saveAndLog(dataMongo: Data) {
        logRepository.save(
            LogMongo(
                dataMongo.id!!,
                dataMongo,
                "rest"
            )
        )
        log(dataMongo)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ApnController::class.java)

        fun log(request: Data) {
            log.info("Consumed: key: ${request.id}, value: $request")
        }
    }
}