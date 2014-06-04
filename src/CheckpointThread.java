import java.util.Random;

import org.uncommons.maths.random.ExponentialGenerator;

public class CheckpointThread extends Thread {
	int count = 0;
	
	public CheckpointThread() {
		
	}

	public void run() {
		
		while(count < 50){
			try {
				count++;
				Application.clock[Application.my_id] ++;				
				Application.takeCheckpoint();
				this.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Application.sendThreadFlag=true;
		System.out.println("Regular Checkpoint count: "+count);
		return;
	}
}
