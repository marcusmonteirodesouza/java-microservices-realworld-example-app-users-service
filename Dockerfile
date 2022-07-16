FROM eclipse-temurin:17.0.3_7-jdk-alpine AS build

WORKDIR app

COPY . .

RUN ./gradlew assemble

FROM open-liberty:22.0.0.6-full-java17-openj9 as staging

COPY --chown=1001:0 --from=build /app/build/libs/users-service-0.0.1-SNAPSHOT.jar /staging/fat-users-service-0.0.1-SNAPSHOT.jar

RUN springBootUtility thin \
 --sourceAppPath=/staging/fat-users-service-0.0.1-SNAPSHOT.jar \
 --targetThinAppPath=/staging/thin-users-service-0.0.1-SNAPSHOT.jar \
 --targetLibCachePath=/staging/lib.index.cache

FROM open-liberty:22.0.0.6-kernel-slim-java17-openj9

RUN cp /opt/ol/wlp/templates/servers/springBoot2/server.xml /config/server.xml

RUN features.sh

COPY --chown=1001:0 --from=staging /staging/lib.index.cache /lib.index.cache
COPY --chown=1001:0 --from=staging /staging/thin-users-service-0.0.1-SNAPSHOT.jar \
                    /config/dropins/spring/thin-users-service-0.0.1-SNAPSHOT.jar

RUN configure.sh