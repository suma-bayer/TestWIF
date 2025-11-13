FROM eclipse-temurin:11-jre-focal
WORKDIR /workdir/

COPY target/my-app-1-jar-with-dependencies.jar my-java-app.jar
COPY vegpmcnp.json vegpmcnp.json

ENV GOOGLE_APPLICATION_CREDENTIALS="vegpmcnp.json"
ENV AWS_DEFAULT_REGION="us-east-1"
ENV AWS_ROLE_ARN="arn:aws:iam::859924236380:role/default-fargate-execution-role"

#EXPOSE 8080
#cmd java -cp "/workdir/simbadriver/*:datahub-kafka-libs.jar" -Dconfig.file=/workdir/producer_config.conf com.bayer.datahub.kafkaclientbuilder.Main
ENTRYPOINT ["java", "-jar", "my-java-app.jar"]

