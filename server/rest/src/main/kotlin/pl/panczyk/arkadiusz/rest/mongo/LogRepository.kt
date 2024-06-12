package pl.panczyk.arkadiusz.rest.mongo

import org.springframework.data.mongodb.repository.MongoRepository;

interface LogRepository : MongoRepository<LogMongo, String>

