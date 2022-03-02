FROM openjdk:13-alpine
COPY ./target/spring_test-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
