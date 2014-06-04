import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageRecievingThread extends Thread {
	private ServerSocket LocServersock;
	private int byeCounter=0;
	private boolean close = false;

	public MessageRecievingThread() {
		try {
			String port = ((Node)Application.nodes.get(Application.my_id+"")).getPort();
			LocServersock = new ServerSocket(Integer.parseInt(port));
			LocServersock.setSoTimeout(10000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (!close) {
			try {
				Socket server = LocServersock.accept();
				/*
				 * DataInputStream in = new DataInputStream(
				 * server.getInputStream());
				 */
				InputStream is = server.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object obj = ois.readObject();
				//System.out.println("\nrecieved a request message");
				// Listen for requests and decide..
				if (obj instanceof Message) {
					Message recievedMessage = (Message) obj;

					for (int i = 0; i < Application.nodes.size(); i++) {

						boolean flag = ((recievedMessage.getClock()[recievedMessage
								.getId()] > Math.max(Application.clock[i],
								recievedMessage.getClock()[i])) || ((recievedMessage
								.getCheckpoint()[Application.my_id] == Application.checkpoint[Application.my_id]) && recievedMessage
								.getTaken()[Application.my_id]));
						if (Application.sentTo[i]
								&& Application.minTo[i] < recievedMessage
										.getClock()[recievedMessage.getId()]
								&& flag) {
							Application.forcedChkpt++;
							Application.takeCheckpoint();
						}
					}
					Application.clock[Application.my_id] = Math
							.max(Application.clock[Application.my_id],
									recievedMessage.getClock()[recievedMessage
											.getId()]);
					for (int k = 0; k < Application.nodes.size(); k++) {
						if (k != Application.my_id) {
							Application.clock[k] = Math.max(
									Application.clock[k],
									recievedMessage.getClock()[k]);
							if (recievedMessage.getCheckpoint()[k] > Application.checkpoint[k]) {
								Application.checkpoint[k] = recievedMessage
										.getCheckpoint()[k];
								Application.taken[k] = recievedMessage
										.getTaken()[k];
							} else if (recievedMessage.getCheckpoint()[k] == Application.checkpoint[k]) {
								Application.taken[k] = Application.taken[k]
										|| recievedMessage.getTaken()[k];
							}
						}
					}
				}
				else if( obj instanceof ByeMessage ){
					System.out.println("Recieved a Bye Message");
					byeCounter++;
				}
			} catch (IOException e) {
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(byeCounter == Application.nodes.size()-1){
				close = true;
			}
		}
		System.out.println("Forced Checkpoints: "+ Application.forcedChkpt);
		//System.out.println("");
		return;
	}
}
