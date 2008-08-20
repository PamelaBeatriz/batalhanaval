package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextArea;

public class Game extends Thread {

	private Vector<Socket> clients;

	private int c1;

	private int c2;

	private JTextArea logTextArea;

	private ObjectOutputStream cout2 = null;

	private ObjectInputStream cout1 = null;

	private Vector<String> cListing;

	private String nick = null;

	public Game(Vector<Socket> clients, int c1, int c2, Vector<String> cListing, JTextArea logTextArea) {
		this.clients = clients;
		this.cListing = cListing;
		this.c1 = c1;
		this.c2 = c2;
		this.logTextArea = logTextArea;
		this.logTextArea.append("\nPartida iniciada entre " + this.cListing.get(this.c1)
				+ " e " + this.cListing.get(this.c2));
		this.start();
		/*        try {
    		Packet packet = null;
        	input = new ObjectInputStream( conexao.getInputStream() );
			while((packet = (Packet) input.readObject()) != null) {
				this.logTextArea.append("\n> " + packet.getFrom() + " diz para " + packet.getTo() +
						": " + packet.getType() + "\n Dados: " + packet.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    try {
	    	this.logTextArea.append("\n> Cliente desconectou [" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "]");
	    	this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//this.start();
	}

	@Override
	public void run() {
		new ChatListenner(this.clients,this.c1,this.c2,this.cListing,this.logTextArea);
		new ChatListenner(this.clients,this.c2,this.c1,this.cListing,this.logTextArea);
	}

}
