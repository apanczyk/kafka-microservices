package pl.panczyk.arkadiusz.consumer

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class Config(
    @Value("\${kafka.threads}")
    val threads: Int
)