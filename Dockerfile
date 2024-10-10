FROM gradle:jdk17 AS base

RUN apt-get update && apt-get install -y curl

WORKDIR /app
EXPOSE 8081

FROM gradle:jdk17 AS build
WORKDIR /src
COPY src ./src
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY --chmod=0755 gradlew ./
RUN --mount=type=cache,target=/root/.gradle ./gradlew build --console=plain --info --no-daemon --no-watch-fs

FROM base AS final
WORKDIR /app
COPY --from=build /src/build/libs/java-spring-demogorgon-1.0.0.jar .
COPY ["newrelic/", "./newrelic"]

COPY --chmod=0755 entrypoint.sh /
COPY --chmod=0755 tester.sh /app

ENTRYPOINT ["/entrypoint.sh"]
