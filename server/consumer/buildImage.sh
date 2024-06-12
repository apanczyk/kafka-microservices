./gradlew bootBuildImage
docker tag consumer:0.0.2-SNAPSHOT apanczyk/consumer
docker push apanczyk/consumer
