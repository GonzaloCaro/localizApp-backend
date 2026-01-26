FROM eclipse-temurin:17-jdk-alpine AS buildstage

RUN apk add --no-cache maven

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

COPY --from=buildstage /app/target/gestion-usuarios-0.0.1-SNAPSHOT.jar /app.jar

RUN mkdir -p /app/wallet

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]