#!/bin/sh

set -e

echo Starting the application

case $DEBUG_MODE in
  true)
    echo "Starting in debug mode..."
    exec java $JVM_ARGS \
      -Xdebug -Xrunjdwp:transport=dt_socket,address=*:$DEBUG_PORT,server=y,suspend=n \
      -jar /app/app.jar;;
  suspend)
    echo "Starting in debug suspended mode, please attach to the proccess."
    exec java $JVM_ARGS  \
      -Xdebug -Xrunjdwp:transport=dt_socket,address=*:$DEBUG_PORT,server=y,suspend=y \
      -jar /app/app.jar;;
  *)
    exec java $JVM_ARGS \
      -jar /app/app.jar;;
esac
