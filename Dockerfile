FROM openjdk:21-jdk-slim
ADD target/jobPortal-rest.jar jobPortal-rest.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/jobPortal-rest.jar"]