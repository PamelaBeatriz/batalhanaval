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

	private ObjectInputStream input1 = null;

	private ObjectInputStream input2 = null;

	private JTextArea logTextArea;

	private Vector<String> cListing;

	private String nick = null;

	public Game(Vector<Socket> clients, int c1, int c2, Vector<String> cListing,
			ObjectInputStream input1,
			ObjectInputStream input2, JTextArea logTextArea) {
		this.clients = clients;
		this.cListing = cListing;
		this.input1 = input1;
		this.input2 = input2;
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
		ChatListenner c1 = new ChatListenner(this.clients,this.c1,this.c2,this.cListing, this.input1,this.logTextArea);
		ChatListenner c2 = new ChatListenner(this.clients,this.c2,this.c1,this.cListing, this.input2,this.logTextArea);
		c1.start();
		c2.start();
	}

}
