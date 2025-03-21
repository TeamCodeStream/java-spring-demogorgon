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
COPY newrelic ./newrelic
COPY build.gradle settings.gradle browserMonitoringTemplate.js ./
COPY --chmod=0755 gradlew ./

ARG BROWSER_LICENSE_KEY
ARG BROWSER_ACCOUNT_ID
ARG BROWSER_TRUST_KEY
ARG BROWSER_AGENT_ID
ARG BROWSER_APPLICATION_ID
ARG FOSSA_API_KEY
ARG NEW_RELIC_API_KEY
ARG NEW_RELIC_METADATA_COMMIT
ARG NEW_RELIC_METADATA_RELEASE_TAG

ENV BROWSER_LICENSE_KEY=$BROWSER_LICENSE_KEY
ENV BROWSER_ACCOUNT_ID=$BROWSER_ACCOUNT_ID
ENV BROWSER_TRUST_KEY=$BROWSER_TRUST_KEY
ENV BROWSER_AGENT_ID=$BROWSER_AGENT_ID
ENV BROWSER_APPLICATION_ID=$BROWSER_APPLICATION_ID
ENV FOSSA_API_KEY=$FOSSA_API_KEY
ENV NEW_RELIC_API_KEY=$NEW_RELIC_API_KEY
ENV NEW_RELIC_METADATA_COMMIT=$NEW_RELIC_METADATA_COMMIT
ENV NEW_RELIC_METADATA_RELEASE_TAG=$NEW_RELIC_METADATA_RELEASE_TAG

RUN --mount=type=cache,target=/root/.gradle ./gradlew downloadNewRelicAgent --console=plain --info --no-daemon --no-watch-fs
RUN --mount=type=cache,target=/root/.gradle ./gradlew build --console=plain --info --no-daemon --no-watch-fs

RUN if [ -z "$FOSSA_API_KEY" ] ; then \
    echo --SKIPPING FOSSA CLI DOWNLOAD ; \
    else \
        curl -H 'Cache-Control: no-cache' https://raw.githubusercontent.com/fossas/fossa-cli/master/install-latest.sh | bash; \
    fi
RUN if [ -z "$FOSSA_API_KEY" ] ; then \
    echo --SKIPPING FOSSA SCAN ; \
    else \
        fossa analyze; \
    fi

RUN if [ -z "$NEW_RELIC_API_KEY" ] ; then \
    echo --SKIPPING SOURCE MAP UPLOAD ; \
    else \
      filename=$(ls ./client/dist/assets/*.js | grep -v '.map.js' | xargs -n 1 basename) && \
      curl -H "Api-Key: $NEW_RELIC_API_KEY" \
           -F "sourcemap=@client/dist/assets/$filename.map" \
           -F "javascriptUrl=https://petclinic-demogorgon.staging-service.nr-ops.net/react/assets/$filename" \
           -F "buildCommit=$NEW_RELIC_METADATA_COMMIT" \
           -F "releaseName=$NEW_RELIC_METADATA_RELEASE_TAG" \
           https://sourcemaps.service.newrelic.com/v2/applications/$BROWSER_APPLICATION_ID/sourcemaps ;\
    fi

FROM base AS final
WORKDIR /app
COPY --from=build /src/build/libs/petclinic-backend-1.0.0.jar .
COPY --from=build /src/newrelic/ ./newrelic/

COPY --chmod=0755 entrypoint.sh /
COPY --chmod=0755 tester.sh /app

ARG NEW_RELIC_METADATA_COMMIT
ENV NEW_RELIC_METADATA_COMMIT=$NEW_RELIC_METADATA_COMMIT

ARG NEW_RELIC_METADATA_RELEASE_TAG
ENV NEW_RELIC_METADATA_RELEASE_TAG=$NEW_RELIC_METADATA_RELEASE_TAG

ENTRYPOINT ["/entrypoint.sh"]
