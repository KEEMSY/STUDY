#!/bin/bash

/run.ssh

while true;
        do echo "still alive";
        sleep 5;
done

exec "$@"