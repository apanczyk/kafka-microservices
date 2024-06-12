package pl.panczyk.arkadiusz.rest.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pl.panczyk.arkadiusz.rest.model.Data
import pl.panczyk.arkadiusz.rest.mongo.DataMongo
import pl.panczyk.arkadiusz.rest.mongo.DataRepository
import pl.panczyk.arkadiusz.rest.mongo.LogMongo
import pl.panczyk.arkadiusz.rest.mongo.LogRepository

@Service
class ApnClient(
    val dataRepository: DataRepository,
    val logRepository: LogRepository,
    val restTemplate: RestTemplate,
    @Value("\${local.server.host}")
    val host: String,
    @Value("\${local.server.port}")
    val port: String
) {

    fun send(data: List<Data>, actual: Int, of: Int) {
        val request = dataRepository.save(DataMongo(data))
        LOGGER.info("Produced: ${request.id} with status: $actual/$of")
        restTemplate.postForObject("http://$host:$port/api", request, LogMongo::class.java)
    }

    fun sendWithResponse(data: List<Data>, actual: Int, of: Int) {
        val request = dataRepository.save(DataMongo(data))
        LOGGER.info("Produced: ${request.id} with status: $actual/$of")
        val response: DataMongo = restTemplate.postForObject("http://$host:$port/api/return", request, DataMongo::class.java)!!
        saveAndLog(response)
    }

    fun saveAndLog(dataMongo: DataMongo) {
        logRepository.save(
            LogMongo(
                dataMongo.id!!,
                dataMongo,
                "restReturn"
            )
        )
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ApnClient::class.java)
    }
}