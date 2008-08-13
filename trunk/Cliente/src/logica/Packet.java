package logica;

public class Packet {
	private String from = null;
	private String to = null;
	private String type = null;
	private String data = null;

	public Packet(String from, String to, String type, String data) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.data = data;
	}

	public String getFrom(){
		return this.from;
	}

	public String getTo(){
		return this.to;
	}

	public String getType(){
		return this.type;
	}

	public String getData(){
		return this.data;
	}

}