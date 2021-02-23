FROM openjdk:8-jdk-slim

EXPOSE 80
RUN mkdir /app
RUN mkdir /config
VOLUME /tmp

ADD build/libs/recognition-*.jar app/app.jar
ADD build/resources/main/application.yml config/application.yml

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
