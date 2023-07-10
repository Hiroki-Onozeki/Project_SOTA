classpath=".\
:./Self_code/*\
:../lib/*\
:/usr/local/share/OpenCV/java/*\
"

OPTION="-Dfile.encoding=UTF8 -Djava.library.path=../lib"

echo "java -classpath $classpath $OPTION $1"
java -classpath "$classpath" $OPTION $1