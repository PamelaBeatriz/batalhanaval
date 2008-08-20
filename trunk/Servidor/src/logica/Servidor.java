package logica;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;

public class Servidor extends Thread {

	private String porta = "";

	private String ipLocal = "";

	private String nome = "";

	private String senhaCriptografada = "";

	// private String logServer = "";

	private ServerSocket serverSocket;

	private Vector<Socket> clients;

	private JTextArea logTextArea = null;

	private JList clientList = null;

	private DefaultListModel modelo = null;

	/**
	 * Seta os parametros fundamentais do servidor e o inicia.
	 *
	 * @author Thiago A. L. Genez
	 * @param nome
	 * @param ipLocal
	 * @param senhaCriptografada
	 */
	public boolean startServidor(String nome, String porta,
			String senhaCriptografada) {
		this.nome = nome;
		this.porta = porta;
		// this.logServer = "";
		this.senhaCriptografada = senhaCriptografada;
		return (this.inicializar());
	}

	/**
	 * Construtor
	 */
	public Servidor() {
		this.porta = "";
		this.ipLocal = "";
		this.nome = "";
		this.senhaCriptografada = "";
		this.serverSocket = null;
		// this.logServer = "";
		this.setIpLocal();
		this.setNome();
	}

	/**
	 * Inicializa o Servidor
	 *
	 * @author Thiago A. L. Genez
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private boolean inicializar() {
		try {
			this.serverSocket = new ServerSocket(Integer.parseInt(this.porta));
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("ERRO NO NUMERO DA PORTA");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERRO NO SERVER SOCKET");
		}
		return false;

	}

	public void esperaConexaoCliente() {
		this.logTextArea
				.append("** Servidor inicializado com sucesso **\n> Esperando Conexões ...");
		modelo = new DefaultListModel();
		clientList.setModel(modelo);
		clients = new Vector<Socket>();
		Vector<String> cListing = new Vector<String>();
		while (true) {
			try {
				Socket temp = this.serverSocket.accept();
				if(clients.size()>=2) {
					new DataOutput(temp).SendPacket(new Packet("ServerFull",
							"O servidor está cheio\ntente novamente mais tarde"));
					temp.close();
					continue;
				} else if(clients.size()==1) {
					clients.add(temp);

					new DataOutput(clients.lastElement()).SendPacket(new Packet("OK","OK"));
					new ThreadCliente(clients, clients.size()-1, cListing, clientList, this.logTextArea).start();
					new Game(clients,clients.size()-2,clients.size()-1,cListing,this.logTextArea);
				} else {
					clients.add(temp);

					new DataOutput(clients.lastElement()).SendPacket(new Packet("OK","OK"));
					new ThreadCliente(clients, clients.size()-1, cListing, clientList, this.logTextArea).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					clients.lastElement().close();
					this.serverSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void finalize() {
		try {
			this.serverSocket.close();
			while(!this.clients.isEmpty()){
				this.clients.lastElement().close();
				this.clients.remove(this.clients.size()-1);
			}
			// Encerro o ServerSocket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIpLocal() {
		return this.ipLocal;
	}

	public void setIpLocal() {
		InetAddress in;
		try {
			in = InetAddress.getLocalHost();
			this.ipLocal = in.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("ERRO NA CAPTURA DO IP LOCAL");
		}
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome() {
		try {
			InetAddress in = InetAddress.getLocalHost();
			this.nome = in.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("ERRO NA CAPTURA DO IP LOCAL");
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senha) {
		this.senhaCriptografada = senha;
	}

	@Override
	public void run() {
		this.esperaConexaoCliente();
	}

	/*
	 * public String getLogServer() { return logServer; }
	 *
	 * public void setLogServer(String logServer) { this.logServer = logServer; }
	 */

	public void setLogArea(JTextArea logTextArea) {
		this.logTextArea = logTextArea;
	}

	public void setClientList(JList clientList) {
		this.clientList = clientList;
	}
}
