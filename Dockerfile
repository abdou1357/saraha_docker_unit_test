FROM amazoncorretto:11-alpine-jdk
COPY target/sarahaa-0.0.1-SNAPSHOT.jar sarahaa.jar
ENTRYPOINT ["java","-jar","/sarahaa.jar"]