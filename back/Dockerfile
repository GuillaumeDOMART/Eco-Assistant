FROM openjdk:17
FROM maven:3

WORKDIR /back

COPY /pom.xml ./
RUN mvn dependency:go-offline

COPY /src ./src

CMD ["mvn", "spring-boot:run"]