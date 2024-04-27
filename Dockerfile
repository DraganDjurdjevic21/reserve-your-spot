FROM openjdk:11
COPY target/reserve-your-spot-1.0.0.jar reserve-your-spot.jar
ENTRYPOINT ["java","-jar","/reserve-your-spot.jar"]