FROM openjdk:21-jdk-slim
VOLUME /tmp
ADD target/*.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]