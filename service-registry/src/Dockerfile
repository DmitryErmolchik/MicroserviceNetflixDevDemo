FROM openjdk:latest
ARG JAR_FILE
ENV ACTIVE_PROFILES="serverDev"
ENV CLUSTER_PEER_SEVER=""
WORKDIR /application

COPY ${JAR_FILE} /application/application.jar

EXPOSE 8761

CMD ["java", "-Dspring.profiles.active=${ACTIVE_PROFILES}" , "-DdockerProdCluster.peerServer=${CLUSTER_PEER_SEVER}", "-jar", "application.jar"]