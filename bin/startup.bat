@ECHO OFF
SET CATALINA_HOME="your path to tomcat"
copy /y "path to .war file" %CATALINA_HOME%"\webapps"
start %CATALINA_HOME%\bin\startup.bat