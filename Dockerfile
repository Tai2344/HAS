FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/Proyecto-1.0-SNAPSHOT.jar app.jar
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "app.jar"]