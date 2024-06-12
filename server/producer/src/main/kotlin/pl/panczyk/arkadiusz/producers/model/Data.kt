package pl.panczyk.arkadiusz.producers.model

data class Data(
    val firstName: String,
    val surname: String,
    val name: String,
    val age: Int
) {
    override fun toString(): String {
        return "Data(" +
                "firstName='$firstName', " +
                "surname='$surname', " +
                "name='$name', " +
                "age=$age)"
    }
}