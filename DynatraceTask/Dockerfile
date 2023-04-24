FROM openjdk:17

COPY target/dynatrace-backed-task.jar dynatrace-backed-task.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","dynatrace-backed-task.jar"]
