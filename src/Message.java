import java.io.Serializable;


public class Message implements Serializable {
	private boolean[] taken;
	private int[] checkpoint;
	private int[] clock;
	private int id;
	private String msg;
	
	public boolean[] getTaken() {
		return taken;
	}

	public void setTaken(boolean[] taken) {
		this.taken = taken;
	}

	public int[] getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(int[] checkpoint) {
		this.checkpoint = checkpoint;
	}

	public int[] getClock() {
		return clock;
	}

	public void setClock(int[] clock) {
		this.clock = clock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Message(int size){
		taken = new boolean[size];
		checkpoint = new int[size];
		clock = new int[size];
		id = Application.my_id;
		for(int i=0;i<size;i++){
			taken[i] = false;
			checkpoint[i] = 0;
			clock[i] = 0;
		}
	}
	public Message(boolean[] taken, int[] checkpoint, int[] clock){
		this.taken = taken;
		this.checkpoint = checkpoint;
		this.clock = clock;
		this.id = Application.my_id;
	}
	public Message(boolean[] taken, int[] checkpoint, int[] clock, String message){
		this.taken = taken;
		this.checkpoint = checkpoint;
		this.clock = clock;
		this.id = Application.my_id;
		this.msg = message;
	}
	
}
