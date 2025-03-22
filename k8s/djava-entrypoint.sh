#!/bin/bash
set -e
#
# Set up the environment
#
APP="djava"
NODE_IP=$(awk 'END{print $1}' /etc/hosts)
NODE_HOSTNAME=$(awk 'END{print $2}' /etc/hosts)
#ZK_NODE_HOSTNAME=${ZOO_MY_HOSTNAME}
APP_LOG=/logs/app.log
mkdir -p /logs
APP_JAR=/${APP}.jar
RUN_CONFIG="-classpath /app/target/classes"
#RUN_CONFIG="-Dserver.port=3000 -Dmanagement.server.port=3001"
#RUN_CONFIG="-Dzk.url=${ZK_NODE_IP}:2181 -Dleader.algo=2"

#
# Processes and Logging
#
echo "Container's hostname: $NODE_HOSTNAME" | tee -a ${APP_LOG}
echo "Container's IP address: $NODE_IP" | tee -a ${APP_LOG}
#
# Start app
#
echo "Starting App Server" | tee -a ${APP_LOG}
#java ${RUN_CONFIG} -jar ${APP_JAR} 2>&1 >> ${APP_LOG}
java ${RUN_CONFIG} -jar ${APP_JAR} 2>&1 >> ${APP_LOG}
# do not put the app in the background; otherwise, the container will exit.