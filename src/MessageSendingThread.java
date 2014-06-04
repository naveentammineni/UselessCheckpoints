import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

import org.uncommons.maths.random.ExponentialGenerator;

public class MessageSendingThread extends Thread {
	private ExponentialGenerator exp_rv;
	private double val;
	private int msgCount=0;
	
	public MessageSendingThread() {

	}

	public void run() {
		boolean done = false;
		while (!done){
			// Broadcast request message to all nodes
			for (String node : Application.nodes.keySet()) {
				try {
					String id = Application.nodes.get(node).id;
					if (!id.equals(Integer.toString(Application.my_id))) {
						// Setting the application sent to flag
						Application.sentTo[Integer.parseInt(id)] = true;
						// if(Application.minTo[Integer.parseInt(id)] == 0)
						// updating the MintO value
						if (Application.minTo[Integer.parseInt(id)] > Application.clock[Integer
								.parseInt(id)])
							Application.minTo[Integer.parseInt(id)] = Application.clock[Integer
									.parseInt(id)];

						String hostname = Application.nodes.get(node).hostname;
						int port = Integer
								.parseInt(Application.nodes.get(node).port);
						/*System.out.println("request to " + hostname + " port: "
								+ port);*/
						Socket client = new Socket(hostname, port);
						OutputStream os = client.getOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(os);
						Message message = new Message(Application.taken, Application.checkpoint, Application.clock);
						oos.writeObject(message);
						oos.close();
						os.close();
						client.close();
					}
				} catch (IOException e) {
					// e.printStackTrace();
				}
				//exp_rv = new ExponentialGenerator(50,  new Random());
				RandomVariable rv = new RandomVariable(50);
				val = rv.nextValue();
				//System.out.println("exp_rv: "+val);
				try {
					this.sleep((long) val);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				msgCount++;
				if(Application.sendThreadFlag)
					done=true;
			}
		}
		// Broadcast request message to all nodes
		for (String node : Application.nodes.keySet()) {
			try {
				String id = Application.nodes.get(node).id;
				if (!id.equals(Integer.toString(Application.my_id))) {
					String hostname = Application.nodes.get(node).hostname;
					int port = Integer
							.parseInt(Application.nodes.get(node).port);
					Socket client = new Socket(hostname, port);
					OutputStream os = client.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					ByeMessage message = new ByeMessage(Application.my_id);
					oos.writeObject(message);
					oos.close();
					os.close();
					client.close();
				}
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
		System.out.println("Total Messages Sent: "+msgCount);
		return;
	}
}
