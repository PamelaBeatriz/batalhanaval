package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextArea;

public class ThreadCliente extends Thread {

	private Socket conexao = null;

	private Vector<Socket> clients;

	private int index;

	private int c2;

	private JList clientList;

	private JTextArea logTextArea;

	private ObjectOutputStream output = null;

	private ObjectInputStream input = null;

	private Vector<String> cListing;

	private String nick = null;

	private String packet = null;

	private boolean isGameRunning = false;

	public ThreadCliente(Vector<Socket> clients, int index, Vector<String> cListing, JList clientList, JTextArea logTextArea) {
		this.clients = clients;
		this.cListing = cListing;
		this.index = index;
		this.conexao = clients.get(index);
		this.clientList = clientList;
		this.logTextArea = logTextArea;
        try {
        	input = new ObjectInputStream( conexao.getInputStream() );
        	packet = (String) input.readObject();
        	if(packet.substring(0,2).equals("NK")) {
        		this.nick = packet.substring(2) + " [" + this.conexao.getInetAddress().getHostAddress() + "]";
        		this.cListing.add(this.nick);
        	}
			logTextArea.append("\n"
					+ " ["
					+ new SimpleDateFormat("HH:mm:ss").format(new Date())
					+ "] Novo Cliente conectado: "
					+ this.nick
					+ " ["
					+ this.conexao.getInetAddress().getHostName()
					+ "]");
			this.clientList.setListData(this.cListing);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.start();
	}

	@Override
	protected void finalize() {
		try {
			this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
        try {
        	input = new ObjectInputStream( conexao.getInputStream() );
        	while( (packet = (String) input.readObject()) != null ) {
	        	if(this.isGameRunning) {
	        		if(packet.substring(0,2).equals("CH")) {
						new DataOutput(clients.elementAt(this.c2)).SendPacket(new String("CH"+this.cListing.get(this.index)
								+ " diz: " + packet.substring(2) ));
						this.logTextArea.append("\n" + "["
								+ new SimpleDateFormat("HH:mm:ss").format(new Date())
								+ "] " + this.cListing.get(this.index) + " diz para "
								+ this.cListing.get(this.c2) + ": " + packet.substring(2) );
		        	}
	        	}
	        	input = new ObjectInputStream( conexao.getInputStream() );
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    try {
	    	this.logTextArea.append("\n> " + this.nick + " desconectou [" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "]");
	    	//this.clients.setElementAt(null, this.index);
	    	this.clients.removeElement(this.conexao);
	    	//this.cListing.setElementAt(null,this.index);
	    	this.cListing.removeElement(this.nick);

	    	this.clientList.setListData(this.cListing);
	    	this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getNick(){
		return this.nick;
	}

	public Socket getConexao() {
		return conexao;
	}

	public void setConexao(Socket conexao) {
		this.conexao = conexao;
	}

	public ObjectInputStream getImput() {
		return this.input;
	}

	public void startGame(int c2) {
		this.c2 = c2;
		this.isGameRunning = true;
	}

}
