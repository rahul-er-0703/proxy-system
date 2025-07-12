FROM openjdk:17
LABEL maintainer="proxy-client"
WORKDIR /app
COPY /target/proxy-client-1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
