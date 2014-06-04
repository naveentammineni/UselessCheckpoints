remoteuser=nxt120630
remotecomputer1=net31.utdallas.edu
remotecomputer2=net32.utdallas.edu
remotecomputer3=net33.utdallas.edu
remotecomputer4=net34.utdallas.edu
remotecomputer5=net35.utdallas.edu

cd ../
javac -d bin -cp uncommons-maths-1.2.3.jar src/*.java

ssh -l "$remoteuser" "$remotecomputer1" "cd $HOME/workspace/AOSProject3/bin;java -cp .:uncommons-maths-1.2.3.jar Application 0 > machine0.out &"
ssh -l "$remoteuser" "$remotecomputer2" "cd $HOME/workspace/AOSProject3/bin;java -cp .:uncommons-maths-1.2.3.jar Application 1 > machine1.out &"
ssh -l "$remoteuser" "$remotecomputer3" "cd $HOME/workspace/AOSProject3/bin;java -cp .:uncommons-maths-1.2.3.jar Application 2 > machine2.out &"
ssh -l "$remoteuser" "$remotecomputer4" "cd $HOME/workspace/AOSProject3/bin;java -cp .:uncommons-maths-1.2.3.jar Application 3 > machine3.out &"
ssh -l "$remoteuser" "$remotecomputer5" "cd $HOME/workspace/AOSProject3/bin;java -cp .:uncommons-maths-1.2.3.jar Application 4 > machine4.out "

echo "Execution completed"

cd bin
echo "Counting the check points"
java ForcedCheckpointCount >> ../out/forced
