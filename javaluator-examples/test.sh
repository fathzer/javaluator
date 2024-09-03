./detectDiff.sh ../../hosting/javaluator/www/en/doc/tutorial ./src/main/java/com/fathzer/soft/javaluator/examples
echo "result diff="$?

./detectDiff.sh ../../hosting/javaluator/www/en/doc/tutorial ../../hosting/javaluator/www/en/doc/tutorial
echo "result same dir="$?

./detectDiff.sh ../../hosting/javaluator/www/en/doc/tutorialx ./src/main/java/com/fathzer/soft/javaluator/examples
echo "result missing src dir="$?

./detectDiff.sh ../../hosting/javaluator/www/en/doc/tutorial ./src/main/java/com/fathzer/soft/javaluator/exampless
echo "result missing dest dir="$?
