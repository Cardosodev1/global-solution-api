FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline
COPY src src

RUN mvn package -DskipTests
FROM eclipse-temurin:21-jre-jammy

COPY --from=build /app/target/global-solution-api-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080