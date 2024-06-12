./gradlew bootBuildImage
docker tag rest_server:0.0.2-SNAPSHOT apanczyk/rest-server
docker push apanczyk/rest-server
