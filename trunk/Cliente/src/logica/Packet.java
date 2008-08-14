package logica;

import java.io.Serializable;

public class Packet implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -1771872414183857331L;
	private String type = null;
	private String data = null;

	public Packet(String type, String data) {
		this.type = type;
		this.data = data;
	}

	public String getType(){
		return this.type;
	}

	public String getData(){
		return this.data;
	}

}
