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

	private JList clientList;

	private JTextArea logTextArea;

	private ObjectOutputStream output = null;

	private ObjectInputStream input = null;

	private Vector<String> cListing;

	private String nick = null;

	public ThreadCliente(Vector<Socket> clients, int index, Vector<String> cListing, JList clientList, JTextArea logTextArea) {
		this.clients = clients;
		this.cListing = cListing;
		this.index = index;
		this.conexao = clients.get(index);
		this.clientList = clientList;
		this.logTextArea = logTextArea;
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
    		Packet packet = null;
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
					+ this.conexao.getInetAddress().getHostAddress()
					+ "]");
			this.clientList.setListData(this.cListing);
        	BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        	String alive;
        	while((alive = entrada.readLine()) != null);
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

}
