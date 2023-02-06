FROM openjdk:17
FROM maven:3

WORKDIR /app

COPY /back/pom.xml ./
RUN mvn dependency:go-offline

COPY /back/src ./src

CMD ["mvn", "spring-boot:run"]