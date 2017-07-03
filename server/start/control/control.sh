#!/bin/bash

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
		exit
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
	exit
fi

SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
  DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
SCRIPT_PATH="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

ARGS=""
for var in "$@"
do
    ARGS="${ARGS} ${var}"
done

eval "${JLUPIN_JAVA_EXE} -Dconsole.installation.dir=${SCRIPT_PATH}/.. -Dcontrol.address="127.0.0.1" -Dcontrol.port="9096" -Dcontrol.connectionTimeout="1000" -Dcontrol.readTimeout="300000" -jar ${SCRIPT_PATH}/control.jar ${ARGS}"
