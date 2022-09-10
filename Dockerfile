FROM maven:3.8.4-jdk-11@sha256:bf5cb3d5ac99bc24110b8dd810f0313c52c63a3a2a708060d540d1324112b6c5 as jarBuilder

WORKDIR /src

ARG NEXUS_REPO_USER
ARG NEXUS_REPO_PASS

ENV NEXUS_REPO_USER=${NEXUS_REPO_USER}
ENV NEXUS_REPO_PASS=${NEXUS_REPO_PASS}

COPY . .
RUN --mount=type=cache,target=/root/.m2 mvn -T 1C -B -U -s .m2/settings.xml clean package -DskipTests

FROM eclipse-temurin:11.0.16.1_1-jre-focal@sha256:76245981725c32267cebbd9a1906b1860633c95a36ab1d910a35c532dfc7fabd as builder

WORKDIR /application

COPY --from=jarBuilder /src/target/*.jar ./application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:11.0.16.1_1-jre-focal@sha256:76245981725c32267cebbd9a1906b1860633c95a36ab1d910a35c532dfc7fabd

WORKDIR /application

ENV TZ=Asia/Ho_Chi_Minh

ARG APM_JAVA_AGENT_VER=1.26.0
RUN curl -s -Lo apm.jar https://search.maven.org/remotecontent\?filepath\=co/elastic/apm/elastic-apm-agent/${APM_JAVA_AGENT_VER}/elastic-apm-agent-${APM_JAVA_AGENT_VER}.jar

ARG GRPC_HEALTH_PROBE_VERSION=v0.4.6
RUN curl -s -Lo /bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 && \
    chmod +x /bin/grpc_health_probe

ARG DUMBINIT_VER=1.2.5
RUN curl -s -Lo /usr/local/bin/dumb-init https://github.com/Yelp/dumb-init/releases/download/v${DUMBINIT_VER}/dumb-init_${DUMBINIT_VER}_x86_64 && \
    chmod +x /usr/local/bin/dumb-init

COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./

ENTRYPOINT ["dumb-init", "java", "-javaagent:apm.jar", "org.springframework.boot.loader.JarLauncher"]