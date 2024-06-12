package pl.panczyk.arkadiusz.consumer.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface LogRepository : MongoRepository<LogMongo, String>