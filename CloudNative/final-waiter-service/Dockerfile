FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE
ADD target/${JAR_FILE} /waiter-service-1.0.0.jar
ENTRYPOINT ["java", "-jar","/waiter-service-1.0.0.jar"]
