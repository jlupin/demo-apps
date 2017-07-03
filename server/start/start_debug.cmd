@echo off


if NOT exist "%JAVA_HOME%" (
    echo JAVA_HOME variable is not set. Set this variable and run again
	goto quit
)

if NOT exist "%JAVA_HOME%\bin\java.exe" (
    echo JAVA_HOME variable is not properly set. Set this variable and run again
	goto quit
)

if exist "%JAVA_HOME%\bin\java.exe" SET JLUPIN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"


cd /d %~dp0
SET CURRENT_PATH=%~dp0
SET CLASSPATH_START=%CURRENT_PATH%lib
SET JAVA_OPTS=-Xms128M -Xmx512M
SET JLUPIN_CLASSPATH=%CLASSPATH_START%\jlupin-starter-1.4.0.0.jar;%CLASSPATH_START%\jlupin-util-1.4.0.0.jar;%CLASSPATH_START%\jlupin-starter-logger-1.4.0.0.jar;%CLASSPATH_START%\jlupin-reference-container-1.4.0.0.jar;%CLASSPATH_START%\jlupin-interfaces-1.4.0.0.jar;%CLASSPATH_START%\jlupin-classloader-1.4.0.0.jar;%CLASSPATH_START%\jlupin-command-interpreter-1.4.0.0.jar;%CLASSPATH_START%\jlupin-command-executor-1.4.0.0.jar;%CLASSPATH_START%\jlupin-classloader-manager-1.4.0.0.jar;%CLASSPATH_START%\jlupin-compilator-1.4.0.0.jar;%CLASSPATH_START%\jlupin-singleton-container-1.4.0.0.jar;%CLASSPATH_START%\jlupin-starter-logger-manager-1.4.0.0.jar;%CLASSPATH_START%\jlupin-control-information-1.4.0.0.jar;%CLASSPATH_START%\jlupin-common-1.4.0.0.jar;%CLASSPATH_START%\jlupin-printstream-strategy-manager-1.4.0.0.jar;%CLASSPATH_START%\jlupin-client-1.4.0.0.jar;%CLASSPATH_START%\ext\snakeyaml-1.11.jar
SET DEBUG_PARAMS=-agentlib:jdwp=transport=dt_socket,address=12998,server=y,suspend=n
echo '
echo '========================================================================='
echo '
echo JLUPIN NEXT SERVER
echo '
echo JAVA_HOME directory is %JAVA_HOME%
echo '
echo JAVA_OPTS: %JAVA_OPTS%
echo '
echo CURRENT_PATH: %CURRENT_PATH%
echo '
echo JLUPIN_CLASSPATH: %JLUPIN_CLASSPATH%
echo '
echo DEBUG_PARAMS: %DEBUG_PARAMS%
echo '
echo jvm info:
%JLUPIN_JAVA_EXE% -version
echo '
echo '========================================================================='
echo '

%JLUPIN_JAVA_EXE% %JAVA_OPTS% %DEBUG_PARAMS% -cp %JLUPIN_CLASSPATH% com.jlupin.starter.main.init.JLupinMainServerInitializer serverStart main.yml consoleCommandModeOn startApplicationParallelModeOff main_server DEBUG

:quit
