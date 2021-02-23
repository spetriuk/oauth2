#!/bin/bash
TOMCAT_HOME="/opt/tomcat";
ARTIFACT="roles-api.war";

# Get time as a UNIX timestamp (seconds elapsed since Jan 1, 1970 0:00 UTC)
T="$(date +%s)"

mvn clean org.apache.maven.plugins:maven-dependency-plugin:3.0.0:tree package -Dmaven.test.skip=true 

rm -rf  $TOMCAT_HOME/webapps/ROOT.war;
rm -rf  $TOMCAT_HOME/webapps/ROOT;
    
echo "Copying $ARTIFACT to $TOMCAT_HOME/webapps as ROOT.war";  
cp ./target/$ARTIFACT $TOMCAT_HOME/webapps/ROOT.war;

echo "Starting Tomcat from $TOMCAT_HOME";
$TOMCAT_HOME/bin/catalina.sh run;

#Calculate execution time
T="$(($(date +%s)-T))";
echo "Total deploy time: ${T} seconds"
