#!/usr/bin/env bash
# Startup script for visp-loadsimulator
# Run ./run-loadsim docker to run as Docker container
# Run ./run-loadsim spring to just run the Spring Boot application


usage()
{
    echo "usage: run-loadsim [ docker | spring [-mq mqhost] [-process | -system] [-H host] [-xmx memory]] | [-h]"
   : # emtpy
}

mode=
mqhost="127.0.0.1"
host="127.0.0.1"
scope="-process"
xmx=

while [ "$1" != "" ]; do
    case $1 in
        spring | docker )       mode=$1
                                ;;
        -mq | --rabbitmq )      shift
                                mqhost=$1
                                ;;
        -process | -system )    scope=$1
                                ;;
        -H | --host )           shift
                                host=$1
                                ;;
        -xmx )                  shift
                                xmx="-Xmx$1"
                                ;;
        -h | --help )           usage
                                exit
                                ;;
        * )                     usage
                                exit 1
    esac
    shift
done


remote="-Dcom.sun.management.jmxremote=true"
authenticate="-Dcom.sun.management.jmxremote.authenticate=false"
ssl="-Dcom.sun.management.jmxremote.ssl=false"
rmiport="-Dcom.sun.management.jmxremote.rmi.port=9010"
port="-Dcom.sun.management.jmxremote.port=9010"

options="$xmx $remote $authenticate $ssl $rmiport $port"

if [ "$mode" == "spring" ]; then
    host="-Djava.rmi.server.hostname=$host"
    options="$options $host"
    arguments="-h,$mqhost,$scope"

    mvn spring-boot:run -Drun.jvmArguments="$options" -Drun.arguments="$arguments"
elif [ "$mode" == "docker" ]; then
    host="-Djava.rmi.server.hostname=$host"
    options="$options $host"
    arguments="-h $mqhost $scope"
    docker run --net="host" -d --name loadsim -e JAVA_OPTS="$options" -e ARGUMENTS="$arguments" -p 9010:9010 msigwart/visp-loadsimulator
else
    usage
    exit 1
fi
