#bin/sh

echo "comparing "$1" with "$2" for java different files"

if [ ! -d $1 ]
then
    echo "Directory "$1" is missing"
	exit 2
fi

if [ ! -d $2 ]
then
    echo "Directory "$2" is missing"
	exit 2
fi

diff -q --strip-trailing-cr $1 $2 | grep "\.java" > diff.txt
if [ -s diff.txt ]
then
    echo "differences detected:"
	cat diff.txt
	exit 1
else
    echo "Every thing seems identical"
	rm diff.txt
fi