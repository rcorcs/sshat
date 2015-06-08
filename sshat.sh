CPFILES='./build/jar/sshat.jar'
for f in $(find ./lib/ -name "*.jar"); do
   CPFILES=$CPFILES':'$f
done
java -cp $CPFILES sshat.$1 ${*:2}
