FROM openjdk:11
EXPOSE 80
ARG JAR_FILE
ADD target/${JAR_FILE} /config-server-1.0.0.jar
ENTRYPOINT ["java", "-jar","/config-server-1.0.0.jar"]
