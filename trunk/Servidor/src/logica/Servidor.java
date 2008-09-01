package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
				.append("["
						+ new SimpleDateFormat("HH:mm:ss").format(new Date())
						+ "] "
						+ "** Servidor inicializado com sucesso **\n> Esperando Conexões ...");
		modelo = new DefaultListModel();
		clientList.setModel(modelo);
		clients = new Vector<Socket>();
		Vector<Integer> cliCount = new Vector<Integer>();
		cliCount.add(0);
		Vector<String> cListing = new Vector<String>();
		Vector<ThreadCliente> tc = new Vector<ThreadCliente>();
		while (true) {
			try {
				Socket temp = this.serverSocket.accept();
				if (cliCount.firstElement() >= 2) {
					if (this.checkPassword(temp)) {
						new DataOutput(temp)
								.SendPacket(new String(
										"SF"
												+ "O servidor está cheio\ntente novamente mais tarde"));
						logTextArea.append("\n" + " ["
								+ new SimpleDateFormat("HH:mm:ss").format(new Date())
								+ "] Conexão recusada para o IP "
								+ temp.getInetAddress().getHostAddress()
								+ " (Servidor Lotado)");

						temp.close();
						continue;
					} else {
						new DataOutput(temp).SendPacket(new String("##"
								+ "Senha Inválida"));
						logTextArea.append("\n" + " ["
								+ new SimpleDateFormat("HH:mm:ss").format(new Date())
								+ "] Tentativa de conexão inválida vinda do IP "
								+ temp.getInetAddress().getHostAddress());
						temp.close();
						continue;
					}

				} else if (cliCount.firstElement() == 1) {

					if (this.checkPassword(temp)) {
						clients.add(temp);
						int i;
						for (i = 0; tc.get(i) == null; i++)
							;
						new DataOutput(clients.lastElement())
								.SendPacket(new String("OK"));
						tc.add(new ThreadCliente(clients, tc, cliCount, clients
								.size() - 1, cListing, clientList,
								this.logTextArea));
						this.logTextArea.append("\n "
								+ "["
								+ new SimpleDateFormat("HH:mm:ss")
										.format(new Date()) + "] "
								+ "Partida iniciada: "
								+ cListing.get(tc.get(i).getIndex()) + " VS "
								+ cListing.get(tc.lastElement().getIndex()));

						tc.get(i).startGame(tc.lastElement().getIndex());
						tc.lastElement().startGame(tc.get(i).getIndex());
					} else {
						new DataOutput(temp).SendPacket(new String("##"
								+ "Senha Inválida"));
						logTextArea.append("\n" + " ["
								+ new SimpleDateFormat("HH:mm:ss").format(new Date())
								+ "] Tentativa de conexão inválida vinda do IP "
								+ temp.getInetAddress().getHostAddress());
						temp.close();
					}

				} else {

					if (this.checkPassword(temp)) {
						clients.add(temp);
						new DataOutput(clients.lastElement())
								.SendPacket(new String("OK"));
						tc.add(new ThreadCliente(clients, tc, cliCount, clients
								.size() - 1, cListing, clientList,
								this.logTextArea));
					} else {
						new DataOutput(temp).SendPacket(new String("##"
								+ "Senha Inválida"));
						logTextArea.append("\n" + " ["
								+ new SimpleDateFormat("HH:mm:ss").format(new Date())
								+ "] Tentativa de conexão inválida vinda do IP "
								+ temp.getInetAddress().getHostAddress());
						temp.close();
					}

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
			logTextArea.setCaretPosition( logTextArea.getText().length()  );
		}
	}

	@Override
	protected void finalize() {
		try {
			this.serverSocket.close();
			while (!this.clients.isEmpty()) {
				this.clients.lastElement().close();
				this.clients.remove(this.clients.size() - 1);
			}
			// Encerro o ServerSocket
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkPassword(Socket socket) throws IOException {
		String packet = null;
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		try {
			packet = (String) input.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if (packet.substring(0, 2).equals("**")) {
			if (this.senhaCriptografada.equals(packet.substring(2))) {
				return true;
			}
		}
		return false;
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
