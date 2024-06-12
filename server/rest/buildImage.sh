./gradlew bootBuildImage
docker tag rest:0.0.2-SNAPSHOT apanczyk/rest
docker push apanczyk/rest
