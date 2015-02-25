rm test.*
python problemGenerator.py $1 $2
./run1.sh test
minisat test.satinput test.satoutput
./run2.sh test
python checker.py test.graph test.subgraphs
