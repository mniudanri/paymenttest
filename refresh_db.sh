#!/bin/sh
if [ "$( docker container inspect -f '{{.State.Running}}' $(echo "posgresdb") )" = "false" ]; then 
    docker start $(echo 'posgresdb')
fi

docker exec -it posgresdb dropdb postgres
docker exec -it posgresdb createdb --username=root --owner=root postgres