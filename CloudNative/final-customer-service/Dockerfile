FROM openjdk:11
ENV SERVER_PORT 8090
ARG JAR_FILE
ADD target/${JAR_FILE} /customer-service-1.0.0.jar
ENTRYPOINT ["java", "-jar","/customer-service-1.0.0.jar"]
