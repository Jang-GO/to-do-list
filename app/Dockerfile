# 1단계: 빌드(Build) 환경
# Java 21과 Gradle이 설치된 이미지를 기반으로 시작합니다.
FROM gradle:jdk21 AS build

# 작업 디렉토리를 /app 으로 설정합니다.
WORKDIR /app

# Gradle 빌드 파일과 래퍼를 먼저 복사하여 Docker의 레이어 캐싱을 활용합니다.
COPY build.gradle settings.gradle ./
COPY gradlew ./
COPY gradle ./gradle

# 소스 코드를 복사합니다.
COPY src ./src

# Gradle 래퍼(wrapper)에 실행 권한을 부여합니다.
RUN chmod +x ./gradlew

# Gradle 래퍼를 사용해 애플리케이션을 빌드합니다. (테스트는 생략)
RUN ./gradlew build -x test


# 2단계: 실행(Runtime) 환경
# 더 가벼운 Java 21 JRE(Java Runtime Environment) 이미지를 기반으로 시작합니다.
FROM eclipse-temurin:21-jre

# 작업 디렉토리를 /app 으로 설정합니다.
WORKDIR /app

# 1단계(build 스테이지)에서 생성된 JAR 파일을 복사해옵니다.
# Gradle 빌드 결과물은 build/libs/ 디렉토리에 생성됩니다.
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너가 시작될 때 실행될 명령어를 정의합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]