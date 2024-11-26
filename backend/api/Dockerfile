FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/padinfo_backend-0.0.1.jar
COPY ${JAR_FILE} app_padinfo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app_padinfo.jar"]