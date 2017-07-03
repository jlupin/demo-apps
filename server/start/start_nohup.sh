#!/bin/bash

# Setting JLUPIN_SERVER_HOME
REL_START_DIR=`dirname $0`
cd ${REL_START_DIR}
cd ../
JLUPIN_SERVER_HOME=`pwd`
cd ${REL_START_DIR}

# Server out logs
JLUPIN_SERVER_OUT_DIR="${JLUPIN_SERVER_HOME}/logs/server/main/start"
JLUPIN_SERVER_OUT="${JLUPIN_SERVER_OUT_DIR}/server.out"

if [ ! -e ${JLUPIN_SERVER_OUT_DIR} ]
then
        mkdir -p ${JLUPIN_SERVER_OUT_DIR}
        [ $? -ne 0 ] && echo "Cannot create SERVER_OUT directory (${JLUPIN_SERVER_OUT_DIR}), exiting..." && exit 1
fi

${JLUPIN_SERVER_HOME}/start/start.sh background $1 >>${JLUPIN_SERVER_OUT} 2>&1 &

echo "Server out: ${JLUPIN_SERVER_OUT}"
