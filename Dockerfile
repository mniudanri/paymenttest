# development
FROM openjdk:20
COPY target/paymenttest-0.0.1-SNAPSHOT.jar /payment-test.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/payment-test.jar"]