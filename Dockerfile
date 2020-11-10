FROM adoptopenjdk:11.0.4_11-jdk-hotspot-bionic

WORKDIR /app

COPY entrypoint.sh ./
COPY chat-service/target/lib/* /app/lib/
COPY chat-service/target/*-runner.jar /app/app.jar

EXPOSE 8080

CMD ["/app/entrypoint.sh"]
