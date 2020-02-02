FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/auth
RUN mkdir -p /app/auth/logs/
ADD target/auth-1.0.0-SNAPSHOT.jar /app/auth/auth.jar
ENTRYPOINT ["java", "-jar", "/app/auth/auth.jar"]