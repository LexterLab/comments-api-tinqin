FROM amazoncorretto:21-alpine

WORKDIR /comment

COPY rest/target/rest-0.0.1-SNAPSHOT.jar /comment/comment-docker.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/comment/comment-docker.jar"]