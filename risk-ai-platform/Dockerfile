FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/risk-ai-platform-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 1999

ENTRYPOINT ["java", "-jar", "app.jar"]