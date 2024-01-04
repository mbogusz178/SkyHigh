FROM amazoncorretto:11
COPY target/*.jar /application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]