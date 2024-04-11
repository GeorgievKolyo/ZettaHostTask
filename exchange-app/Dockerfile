FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} exchangeapp.jar
ENTRYPOINT ["java","-jar","/app.jar"]