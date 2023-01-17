FROM gradle:7.6-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM amazoncorretto:17-alpine3.16
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar ./forum.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "forum.jar"]