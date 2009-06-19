#!/bin/sh

if [ "$JAVAMAILSERVER_HOME" = "" ] ; then
  COMMAND=$0
  JAVAMAILSERVER_HOME=`dirname ${COMMAND}`/..
fi


for i in ${JAVAMAILSERVER_HOME}/lib/* ; do
  if [ "$LOCALCLASSPATH" != "" ]; then
    LOCALCLASSPATH=${LOCALCLASSPATH}:$i
  else
    LOCALCLASSPATH=$i
  fi
done

java -cp $LOCALCLASSPATH com.ericdaugherty.mail.server.Mail $JAVAMAILSERVER_HOME
