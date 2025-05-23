FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim
COPY --from=build /target/backend-0.0.1-SNAPSHOT.jar backend.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","backend.jar"]
