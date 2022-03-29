FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE
ADD target/${JAR_FILE} /docker-demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/docker-demo-0.0.1-SNAPSHOT.jar"]
