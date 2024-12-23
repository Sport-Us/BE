FROM amazoncorretto:21-alpine
COPY build/libs/be-0.0.1-SNAPSHOT.jar sportus.jar

ENV TZ Asia/Seoul
ARG ENV

ENTRYPOINT ["java", "-Xms512m", "-jar","-Dspring.profiles.active=prod", "-Dserver.env=${ENV}", "sportus.jar"]