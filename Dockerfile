FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# COPY docker-image/.env .env
ENTRYPOINT ["java","-jar","/app.jar"]