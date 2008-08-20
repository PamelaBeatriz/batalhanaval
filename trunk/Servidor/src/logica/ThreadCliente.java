package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
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

	private Packet packet = null;

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
        	packet = (Packet) input.readObject();
        	if(packet.getType().equals("setNick")) {
        		this.nick = packet.getData() + " [" + this.conexao.getInetAddress().getHostAddress() + "]";
        		this.cListing.add(this.nick);
        	}
			logTextArea.append("\n"
					+ " ["
					+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
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
        	while( (packet = (Packet) input.readObject()) != null ) {
	        	if(this.isGameRunning) {
	        		if(packet.getType().equals("CHAT")) {
						new DataOutput(clients.lastElement()).SendPacket(new Packet("CHAT",this.cListing.get(this.index)
								+ " diz:" + packet.getData() ));
						this.logTextArea.append("\n" + this.cListing.get(this.index) + " diz para "
								+ this.cListing.get(this.c2) + ": " + packet.getData() );
		        	}
	        	}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    try {
	    	this.logTextArea.append("\n> " + this.nick + " desconectou [" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "]");
	    	this.clients.remove(this.index);
	    	this.cListing.remove(this.index);
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
