#!/bin/bash

##### FUNCTIONS #####
function Exit()
{
	echo "========================================================================="
	exit 1
}

function Help()
{

	echo "Usage: start.sh MODE LOG_LEVEL"
	echo "Usage: start.sh [interactive|background] [ERROR|WARN|INFO|DEBUG]"
	echo "Default: start.sh interactive INFO"

}
##### SANITY CHECKS & SETUP BEFOR SERVER START #####

echo "========================================================================="

# Setting JLNS start mode & log level

mode=$1
logLevel=$2

# Default mode
[ "${mode}x" = "x" ] && mode="interactive" 

# Default Log Level
[ "${logLevel}x" = "x" ] && logLevel="INFO"

case $mode in
"background");;
"interactive");;
*) Help && Exit
esac

case $logLevel in
"ERROR");;
"WARN");;
"INFO");;
"DEBUG");;
*) Help && Exit
esac


# Checking JAVA_HOME
if [ "${JAVA_HOME}x" = "x" ];
then
	echo "WARNING: JAVA_HOME variable is not set"

	# Trying to find JAVA_HOME
	if [ "`which java 2>/dev/null | grep -v 'alias java'`x" = "x" ];
	then
		echo "ERROR: I haven't found JAVA in the path"
		echo "INFO: If you have installed JDK in your system, please set JAVA_HOME properly (directory where JDK has been installed) or add JAVA bin directory to you PATH"
		echo "INFO: If you haven't installed JDK in your system yet, please visit http://www.oracle.com/technetwork/java/javase/downloads/index.html and follow the given instructions"
		Exit
	else
		[ `which java 2>/dev/null | wc -l` -gt 1 ] && echo "ERROR: Something confusing has happend, too many JAVA exec in your PATH. Please set one JAVA environment in your PATH and try again" && Exit
	
		JAVA_PATH=`which java 2>/dev/null | grep -v 'alias java'`

		if [ `echo ${JAVA_PATH} | grep -Ec "^~"` -lt 0 ];
		then
			 JAVA_FULL_PATH="${HOME}`echo ${JAVA_PATH} | sed s/"~"/""/g | sed s/"\/bin\/java"/""/g`" 
		else
			JAVA_FULL_PATH="`echo ${JAVA_PATH} | sed s/"\/bin\/java"/""/g`"
		fi

		echo "INFO: Setting JAVA_HOME: ${JAVA_FULL_PATH}"
		export JAVA_HOME=${JAVA_FULL_PATH}
	fi	
fi


# Checking JAVA exec
JLUPIN_JAVA_EXE="${JAVA_HOME}/bin/java"

if [ ! -x "$JLUPIN_JAVA_EXE" ];
then
	echo "ERROR: JAVA exec does not exist or is not executable: $JLUPIN_JAVA_EXE"
	Exit
fi

# Setting JLUPIN_SERVER_HOME 
REL_START_DIR=`dirname $0`
cd ${REL_START_DIR}
CURRENT_DIR=`pwd`
cd ..
JLUPIN_SERVER_HOME=`pwd`
cd ${CURRENT_DIR}

echo "INFO: Setting JLUPIN_SERVER_HOME: ${JLUPIN_SERVER_HOME}"

# Server out logs
JLUPIN_SERVER_OUT_DIR="${JLUPIN_SERVER_HOME}/logs/server/main/start"
JLUPIN_SERVER_OUT="${JLUPIN_SERVER_OUT_DIR}/server.out"

if [ ! -e ${JLUPIN_SERVER_OUT_DIR} ]
then
        mkdir -p ${JLUPIN_SERVER_OUT_DIR}
        [ $? -ne 0 ] && echo "Cannot create SERVER_OUT directory (${JLUPIN_SERVER_OUT_DIR}), exiting..." && exit 1
fi

# Classpath and other options
CLASSPATH_START="${JLUPIN_SERVER_HOME}/start/lib"
JLUPIN_CLASSPATH="$CLASSPATH_START/jlupin-starter-1.4.0.0.jar:$CLASSPATH_START/jlupin-util-1.4.0.0.jar:$CLASSPATH_START/jlupin-starter-logger-1.4.0.0.jar:$CLASSPATH_START/jlupin-reference-container-1.4.0.0.jar:$CLASSPATH_START/jlupin-interfaces-1.4.0.0.jar:$CLASSPATH_START/jlupin-classloader-1.4.0.0.jar:$CLASSPATH_START/jlupin-command-interpreter-1.4.0.0.jar:$CLASSPATH_START/jlupin-command-executor-1.4.0.0.jar:$CLASSPATH_START/jlupin-classloader-manager-1.4.0.0.jar:$CLASSPATH_START/jlupin-compilator-1.4.0.0.jar:$CLASSPATH_START/jlupin-singleton-container-1.4.0.0.jar:$CLASSPATH_START/jlupin-starter-logger-manager-1.4.0.0.jar:$CLASSPATH_START/jlupin-control-information-1.4.0.0.jar:$CLASSPATH_START/jlupin-printstream-strategy-manager-1.4.0.0.jar:$CLASSPATH_START/jlupin-common-1.4.0.0.jar:$CLASSPATH_START/jlupin-client-1.4.0.0.jar:$CLASSPATH_START/ext/snakeyaml-1.11.jar"
JAVA_OPTS="-Xms128M -Xmx512M"


##### STARTING JLUPIN NEXT SERVER #####

echo "========================================================================="
echo ""
echo "  JLUPIN NEXT SERVER (mode: ${mode}, log level: ${logLevel})"
echo ""
echo "  JAVA_HOME: ${JAVA_HOME}"
echo ""
echo "  JAVA_OPTS: $JAVA_OPTS"
echo ""
echo "  JLUPIN_SERVER_HOME: ${JLUPIN_SERVER_HOME}"
echo ""
echo "  CLASSPATH_START: ${CLASSPATH_START}"
echo ""
echo "  JLUPIN_CLASSPATH: $JLUPIN_CLASSPATH"
echo ""
echo "  JAVA VERSION"
"$JLUPIN_JAVA_EXE" -version
echo ""
echo "========================================================================="
echo ""

DEBUG_PARAMS=""
[ "$logLevel" = "DEBUG" ] && DEBUG_PARAMS="-agentlib:jdwp=transport=dt_socket,address=12998,server=y,suspend=n "

JLUPIN_SERVER_EXE="${JLUPIN_JAVA_EXE} ${JAVA_OPTS} ${DEBUG_PARAMS}-classpath ${JLUPIN_CLASSPATH} com.jlupin.starter.main.init.JLupinMainServerInitializer serverStart main.yml"
echo ${JLUPIN_SERVER_EXE}

echo ""

if [ "${mode}" = "background" ]; then
	JLUPIN_SERVER_EXE="${JLUPIN_SERVER_EXE} consoleCommandModeOff startApplicationParallelModeOff main_server $logLevel >>${JLUPIN_SERVER_OUT} 2>&1 &"
else
	JLUPIN_SERVER_EXE="${JLUPIN_SERVER_EXE} consoleCommandModeOn startApplicationParallelModeOff main_server $logLevel"
fi

eval "${JLUPIN_SERVER_EXE}"
