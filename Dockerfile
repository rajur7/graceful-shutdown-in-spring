FROM openjdk:11-jre-slim
ENV AWS_ACCESS_KEY_ID=no_id
ENV AWS_SECRET_ACCESS_KEY=no_secret
ENV AWS_REGION=us-east-1
COPY ./build/libs/graceful-shutdown-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]