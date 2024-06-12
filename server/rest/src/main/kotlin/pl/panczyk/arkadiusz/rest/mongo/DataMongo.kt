package pl.panczyk.arkadiusz.rest.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.panczyk.arkadiusz.rest.model.Data
import java.time.LocalDateTime

@Document
data class DataMongo(
    @Id val id: String?,
    val data: List<Data>,
    val requestTime: LocalDateTime
) {
    constructor(data: List<Data>) : this(
        id = null,
        data  = data,
        requestTime = LocalDateTime.now()
    )

    override fun toString(): String {
        return "DataMongo(id=$id, data=$data, requestTime=$requestTime)"
    }
}