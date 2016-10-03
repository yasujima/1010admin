#!/bin/sh
i=0;
while true;
do
    i=$(($i+1));
    echo "$i" >> ../xx.txt;
    sleep 1;
done

