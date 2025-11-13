FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /root

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ENV TZ=Asia/Taipei
EXPOSE 8080

COPY --from=builder /root/base-rest/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]