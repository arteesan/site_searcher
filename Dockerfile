FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/app
WORKDIR /opt/app
COPY ./target/SiteSearcher1-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/app/

CMD java -jar SiteSearcher1-1.0-SNAPSHOT-jar-with-dependencies.jar