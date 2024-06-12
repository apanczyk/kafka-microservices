package pl.panczyk.arkadiusz.producers.mongo

import java.time.LocalDateTime

data class LogMongo(
    val id: String? = null,
    val requestId: String,
    val requestTime: LocalDateTime,
    val responseTime: LocalDateTime,
    val endTime: LocalDateTime,
    val body: InternalData,
    val source: String
) {

    constructor(logMongo: InternalData, source: String): this (
        id = logMongo.id,
        requestId = logMongo.requestId,
        requestTime = logMongo.requestTime,
        responseTime = logMongo.responseTime,
        endTime = LocalDateTime.now(),
        body = logMongo,
        source = source
    )

    override fun toString(): String {
        return "LogMongo(id=$id, requestId='$requestId', requestTime=$responseTime, body=$body)"
    }

    data class InternalData (
        val id: String?,
        val requestId: String,
        val requestTime: LocalDateTime,
        val responseTime: LocalDateTime,
        val body: DataMongo,
        val source: String
    )
}