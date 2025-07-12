FROM openjdk:17
LABEL maintainer="proxy-server"
WORKDIR /app
COPY target/proxy-server-1.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]

