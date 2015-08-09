#!/usr/bin/env bash

ATI_HOME=/ati
LOG_DIR=${ATI_HOME}/tmp
NODE_ID=10
## Creates tmp dir if it does not exists
if [[ ! -e ${LOG_DIR} ]]; then
    mkdir -p ${LOG_DIR}
fi

echo "Starting ATI services..."
cd ${ATI_HOME}
nohup java -XX:+UseParallelGC -XX:ParallelGCThreads=4 -cp .:ati-app-1.0-SNAPSHOT.jar:lib -Dlog4j.configurationFile="file:${ATI_HOME}/properties/log4j2.${NODE_ID}.xml" com.nuevatel.ati.ATI ${ATI_HOME}/properties/ati.${NODE_ID}.properties > ${LOG_DIR}/ati.${NODE_ID}.tmp &
echo "ATI service was started..."
