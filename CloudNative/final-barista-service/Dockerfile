FROM openjdk:11
EXPOSE 8070
ARG JAR_FILE
ADD target/${JAR_FILE} /barista-service-1.0.0.jar
ENTRYPOINT ["java", "-jar","/barista-service-1.0.0.jar"]
