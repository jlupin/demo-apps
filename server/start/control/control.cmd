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

set SCRIPT_PATH=%~dp0

set ARGS=
:loop
if "%1"=="" goto continue
set ARGS=%ARGS% %1
shift
goto Loop
:continue

%JLUPIN_JAVA_EXE% -Dconsole.installation.dir=%SCRIPT_PATH%.. -Dcontrol.address="127.0.0.1" -Dcontrol.port="9096" -Dcontrol.connectionTimeout="1000" -Dcontrol.readTimeout="300000" -jar %SCRIPT_PATH%control.jar %ARGS%

:quit
