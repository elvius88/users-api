#!/bin/sh
getPort() {
    echo $1 | cut -d : -f 3 | xargs basename
}

echo "********************************************************"
echo "Inicializando el GENERAL API"
echo "********************************************************"

echo "********************************************************"
echo "Chequeando que el Discovery este activo en $DISCOVERY_URI "
echo "********************************************************"
while ! `nc -z $PEER1_SERVICE  $DISCOVERY_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Discovery Server se inicializo"


echo "********************************************************"
echo "Chequeando que el configuration server este activo en $CONFIGSERVER_URI"
echo "********************************************************"
while ! `nc -z $CONFIGSERVER_SERVICE $(getPort $CONFIGSERVER_URI)`; do sleep 3; done
echo "*******  Configuration Server se inicializo"

echo "********************************************************"
echo "Iniciando el GENERAL Api con el Discovery en:  $DISCOVERY_URI";
echo "********************************************************"
cd /usr/local/@project.artifactId@/
java    -Djava.security.egd=file:/dev/./urandom  -Dserver.port=$SERVER_PORT \
        -Doracle.jdbc.timezoneAsRegion=false \
        -Deureka.client.serviceUrl.defaultZone=$DISCOVERY_URI \
        -Dspring.profiles.active=$PROFILE \
        $CUSTOM_ENVS \
        -jar @project.build.finalName@.jar