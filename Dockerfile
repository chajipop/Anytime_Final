FROM openjdk:17-ea-11-jdk-slim
COPY ./target/anytime-0.0.1-SNAPSHOT.war /anytime.war
copy ./logo.png  /usr/src/logo.png 
ENTRYPOINT ["java", "-jar", "anytime.war"]