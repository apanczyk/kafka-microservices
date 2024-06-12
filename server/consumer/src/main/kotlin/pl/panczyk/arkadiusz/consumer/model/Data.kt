package pl.panczyk.arkadiusz.consumer.model

import java.time.LocalDateTime

data class Data(
    val id: String?,
    val data: List<InnerData>,
    val requestTime: LocalDateTime
) {
    data class InnerData(
        val firstName: String,
        val surname: String,
        val name: String,
        val age: Int
    )
    override fun toString(): String {
        return "Data(id=$id, data=$data, requestTime=$requestTime)"
    }
}
