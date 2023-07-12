classpath=".\
:../lib/*\
"

echo "javac -encoding utf-8 -classpath $classpath -Dfile.encoding=UTF8 $1"
javac -encoding utf-8 -classpath  "$classpath"  $1 #-Dfile.encoding=UTF8 $1

