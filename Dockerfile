#FROM eclipse-temurin:17-jdk AS build
#WORKDIR /app
#COPY . .
#RUN ./mvnw -q -DskipTests package || mvn -q -DskipTests package
#
#FROM eclipse-temurin:17-jre
#WORKDIR /app
#COPY --from=build /app/target/demo_j5_asm1-0.0.1-SNAPSHOT.jar app.jar
#ENV JAVA_OPTS=""
#EXPOSE 8080
#ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
# syntax=docker/dockerfile:1

# --- BUILD STAGE ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
COPY .mvn ./.mvn
COPY mvnw ./

# chống CRLF + cấp quyền thực thi cho wrapper nếu có
RUN set -eux; \
    if [ -f mvnw ]; then \
      sed -i 's/\r$//' mvnw; \
      chmod +x mvnw; \
    fi

# build: ưu tiên mvnw nếu có, không thì dùng mvn trong image
RUN if [ -f mvnw ]; then ./mvnw -q -DskipTests package; else mvn -q -DskipTests package; fi

# --- RUNTIME STAGE ---
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 10000
CMD ["java","-jar","/app/app.jar"]

