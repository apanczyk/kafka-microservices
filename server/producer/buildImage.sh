./gradlew bootBuildImage
docker tag producer:0.0.2-SNAPSHOT apanczyk/producer
docker push apanczyk/producer
