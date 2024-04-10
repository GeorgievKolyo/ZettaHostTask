FROM openjdk:17
ARG JAR_FILE=build/libs/exchange-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} exchange-app.jar
ENTRYPOINT ["java","-jar", "/exchange-app.jar"]