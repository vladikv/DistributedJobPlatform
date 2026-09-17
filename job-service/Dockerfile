# Stage 1: build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY job-service/pom.xml job-service/pom.xml
COPY worker-service/pom.xml worker-service/pom.xml

RUN chmod +x mvnw

COPY job-service/src job-service/src

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -f pom.xml -pl job-service -am package -DskipTests

# Stage 2: run
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/job-service/target/job-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]