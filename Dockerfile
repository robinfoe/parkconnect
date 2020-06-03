#FROM openjdk:8-jdk-alpine
FROM openjdk:13-ea-27-jdk-alpine3.10
#VOLUME /tmp
RUN mkdir /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/park-connect.jar

WORKDIR /app 

CMD ["java", "-jar", "park-connect.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]