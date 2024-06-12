package pl.panczyk.arkadiusz.rest_server.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.panczyk.arkadiusz.rest_server.model.Data
import java.time.LocalDateTime

@Document
data class LogMongo(
    @Id
    val id: String? = null,
    val requestId: String,
    val requestTime: LocalDateTime,
    val responseTime: LocalDateTime,
    val body: Data,
    val source: String
) {
    constructor(key: String, data: Data, source: String) : this(
        id = null,
        requestId = key,
        requestTime = data.requestTime,
        responseTime = LocalDateTime.now(),
        body = data,
        source = source
    )

    override fun toString(): String {
        return "LogMongo(id=$id, requestId='$requestId', requestTime=$responseTime, body=$body)"
    }
}