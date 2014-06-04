import java.io.Serializable;

public class ByeMessage implements Serializable {
	private int id;

	public ByeMessage(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
