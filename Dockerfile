FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/environmental-0.0.1-SNAPSHOT.jar env-report.jar
ENTRYPOINT ["java","-jar","/env-report.jar"]
