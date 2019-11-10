#!/bin/bash

var=$1.json

if [ -e $var ]; then
   echo "File $var was already created!"
else
   echo "{" >> $var
   echo "  \"parent\": \"item/generated\"," >> $var
   echo "  \"textures\": {" >> $var
   echo "    \"layer0\": \"chemcraftmod:item/$1\"" >> $var
   echo "  }" >> $var
   echo "}" >> $var
fi
