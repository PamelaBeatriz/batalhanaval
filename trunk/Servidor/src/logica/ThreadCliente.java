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

	private Vector<ThreadCliente> tc;

	private String nick = null;

	private String packet = null;

	private boolean isGameRunning = false;

	private Vector<Integer> cliCount = null;

	public ThreadCliente(Vector<Socket> clients, Vector<ThreadCliente> tc, Vector<Integer> cliCount, int index, Vector<String> cListing, JList clientList, JTextArea logTextArea) {
		this.clients = clients;
		this.tc = tc;
		this.cliCount = cliCount;
		Integer temp = this.cliCount.firstElement();
		this.cliCount.remove(0);
		this.cliCount.add(++temp);
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
	    	Vector<String> TcListing = (Vector<String>) this.cListing.clone();
			for(int i=0;i<TcListing.size();i++) {
				if(TcListing.remove(null))
					i--;
			}
			this.clientList.setListData(TcListing);
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
	    	this.clients.setElementAt(null,this.index);
	    	//this.cListing.setElementAt(null,this.index);
	    	this.cListing.setElementAt(null,this.index);

	    	Vector<String> TcListing = (Vector<String>) this.cListing.clone();
			for(int i=0;i<TcListing.size();i++) {
				if(TcListing.remove(null))
					i--;
			}
	    	this.clientList.setListData(TcListing);
	    	this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tc.setElementAt(null,this.index);
		Integer temp = this.cliCount.firstElement();
		this.cliCount.remove(0);
		this.cliCount.add(--temp);
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

	public int getIndex(){
		return this.index;
	}

}
