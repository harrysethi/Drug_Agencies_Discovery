#!/bin/bash

echo ""
if [ $# -lt 1 ]
then
	echo "Error: ---missing required parameters"
	echo ""
	exit 1
fi

#echo ""

java -jar drugs.jar 1 $1

rm temp.txt
