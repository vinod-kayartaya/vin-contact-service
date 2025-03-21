FROM maven:3.9-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Create a directory for SQLite database
RUN mkdir -p /app/data
VOLUME /app/data
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.datasource.url=jdbc:sqlite:/app/data/contacts.db", "app.jar"] 