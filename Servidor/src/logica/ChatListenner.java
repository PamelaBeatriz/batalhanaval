package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JTextArea;

public class ChatListenner extends Thread {

	private Vector<Socket> clients;

	private int c1;

	private int c2;

	private JTextArea logTextArea;

	private ObjectInputStream input = null;

	private Vector<String> cListing;

	private String nick = null;

	public ChatListenner(Vector<Socket> clients, int c1, int c2, Vector<String> cListing, ObjectInputStream input, JTextArea logTextArea) {
		this.clients = clients;
		this.cListing = cListing;
		this.input = input;
		this.c1 = c1;
		this.c2 = c2;
		this.logTextArea = logTextArea;
	}

	@Override
	public void run() {
    	try {
    		Packet packet = null;
    		//ObjectInputStream input = new ObjectInputStream( this.clients.get(this.c1).getInputStream() );
        	while( (packet = (Packet) input.readObject()) != null ) {
	        	if(packet.getType().equals("CHAT")) {
					new DataOutput(clients.lastElement()).SendPacket(new Packet("CHAT",this.cListing.get(this.c1)
							+ " diz:" + packet.getData() ));
					this.logTextArea.append("\n" + this.cListing.get(this.c1) + "diz para"
							+ this.cListing.get(this.c2) + ":" + packet.getData() );
	        	}

        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
