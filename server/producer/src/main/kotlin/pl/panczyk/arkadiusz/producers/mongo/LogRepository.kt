package pl.panczyk.arkadiusz.producers.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface LogRepository : MongoRepository<LogMongo, String>