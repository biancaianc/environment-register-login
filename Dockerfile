FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/register-login-0.0.1-SNAPSHOT.jar register-login.jar
ENTRYPOINT ["java","-jar","/register-login.jar"]
