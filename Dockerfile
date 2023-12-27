FROM gradle:8.5.0-jdk21 AS build
COPY . .
RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim
COPY --from=build /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
