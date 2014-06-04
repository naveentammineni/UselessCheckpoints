import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/* Application Module */

public class Application {
	static int my_id;
	Scanner rfile;
	int num_nodes = 0;
	String rline;
	int valcn = 0, p;
	static HashMap<String, Node> nodes = new HashMap<String, Node>();
	static int[] clock;
	static int[] checkpoint;
	static boolean[] taken;
	String msg;
	static Message message;
	static boolean[] sentTo;
	static int[] minTo;
	static boolean sendThreadFlag = false;
	static int forcedChkpt=0;
	/*
	 * Thread that generates CS request after a random wait If token is with
	 * this node and the Q is empty no request for CS is generated else Request
	 * for CS is generated
	 */
	public static void main(String args[]) {
		my_id = Integer.parseInt(args[0]);
		int counter = 0;
		Application app = new Application();
		app.readFile();
	}
	public void readFile(){
		
		try {
			//rfile = new Scanner(new File("H:\\workspace\\AOSProject3\\src\\Config.txt"));
			rfile = new Scanner(new File("Config.txt"));
			while (rfile.hasNextLine()) {
				rline = rfile.nextLine();
				if (rline.charAt(0) != '#') {
					// Skipping the first line of input, Which has only the
					// count of the nodes
					if (valcn == 0) {
						num_nodes = Character.getNumericValue(rline.charAt(0));
						valcn = 1;
						rline = rfile.nextLine();
					}
					String[] params = rline.split(" ");
					Node temp = new Node(params[0], params[1], params[2]);
					temp.print();
					nodes.put(params[0], temp);
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		int size = nodes.size();
		//initialization of the values
		minTo = new int[size];
		sentTo = new boolean[size];
		clock = new int[size];
		checkpoint = new int[size];
		taken = new boolean[size];
		for(int i =0;i<size;i++){
			sentTo[i] = false;
			minTo[i] = 0;
			clock[i] = 0;
			checkpoint[0] = 0;
		}
		
		//Starting the regular checkpoint thread
		CheckpointThread ckptThread = new CheckpointThread();
		ckptThread.start();
		//Starting the Message Sending Thread
		MessageSendingThread msgSendThread = new MessageSendingThread();
		msgSendThread.start();
		//Starting the Message recieving thread
		MessageRecievingThread msgRcvThread = new MessageRecievingThread();
		msgRcvThread.start();

	}
	//Resetting the values after every checkpoint
	public synchronized static void takeCheckpoint(){
		clock[my_id]++;
		checkpoint[my_id]++;
		for(int i =0;i<nodes.size();i++){
			sentTo[i] = false;
			minTo[i] = 0;
			taken[i] = true;
		}
		taken[my_id] = false;
	}
}
