FROM gradle:jdk17 AS base

RUN apt-get update && apt-get install -y curl

WORKDIR /app
EXPOSE 8081

FROM gradle:jdk17 AS build
WORKDIR /src

ENV NODE_VERSION=20.15.1
RUN apt-get update && apt-get install -y curl
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/refs/tags/v0.40.1/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use v${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"

COPY src ./src
COPY client ./client
COPY gradle ./gradle
COPY build.gradle settings.gradle browserMonitoringTemplate.js ./
COPY --chmod=0755 gradlew ./

ARG BROWSER_LICENSE_KEY
ARG BROWSER_ACCOUNT_ID
ARG BROWSER_TRUST_KEY
ARG BROWSER_AGENT_ID
ARG BROWSER_APPLICATION_ID

ENV BROWSER_LICENSE_KEY=$BROWSER_LICENSE_KEY
ENV BROWSER_ACCOUNT_ID=$BROWSER_ACCOUNT_ID
ENV BROWSER_TRUST_KEY=$BROWSER_TRUST_KEY
ENV BROWSER_AGENT_ID=$BROWSER_AGENT_ID
ENV BROWSER_APPLICATION_ID=$BROWSER_APPLICATION_ID

RUN --mount=type=cache,target=/root/.gradle ./gradlew build --console=plain --info --no-daemon --no-watch-fs

FROM base AS final
WORKDIR /app
COPY --from=build /src/build/libs/petclinic-backend-1.0.0.jar .
COPY ["newrelic/", "./newrelic"]

COPY --chmod=0755 entrypoint.sh /
COPY --chmod=0755 tester.sh /app

ENTRYPOINT ["/entrypoint.sh"]
