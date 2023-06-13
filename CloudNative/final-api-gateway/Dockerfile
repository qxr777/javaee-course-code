FROM openjdk:11
EXPOSE 80
ARG JAR_FILE
ADD target/${JAR_FILE} /api-gateway-1.0.0.jar
ENTRYPOINT ["java", "-jar","/api-gateway-1.0.0.jar"]
