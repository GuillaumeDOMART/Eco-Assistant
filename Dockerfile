FROM openjdk:17
FROM maven:3

WORKDIR /app

COPY /Back/pom.xml ./
RUN mvn dependency:go-offline

COPY /Back/src ./src

CMD ["mvn", "spring-boot:run"]