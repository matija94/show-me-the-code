FROM adoptopenjdk/openjdk11

ARG JAR_FILE=target/*.jar

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]