package pl.panczyk.arkadiusz.rest_server.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface LogRepository : MongoRepository<LogMongo, String>