FROM openjdk:11-jdk

ADD . /tmp

WORKDIR /tmp

RUN ./gradlew build

RUN mkdir /app

WORKDIR /app

RUN cp /tmp/build/libs/*.jar /app

CMD ["java", "-Xmx2G", "-jar", "/app/minnesotaDNRSurveyExporter-1.0.jar"]
