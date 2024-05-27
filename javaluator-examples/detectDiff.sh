#bin/sh

# ./detectDiff.sh ../../hosting/javaluator/www ./src/main/java/com/fathzer

echo "compare"$1" with "$2

#ls -l $1/*.java

#echo "---------------"

#ls -l $2


diff -q $1 $2 | grep "\.javax" > diff.txt
if [ -s diff.txt ]
then
    echo "do something because they're different"
else
    echo "do something because they're identical"
fi