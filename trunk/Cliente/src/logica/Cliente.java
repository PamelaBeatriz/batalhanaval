package logica;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;


public class Cliente extends Thread {

	private String nick = "";

	private Socket socket = null;

	private String porta = "";

	private String ipServer = "";

	private String senhaCriptografada = "";

	public Cliente() {
		this.nick = "";
		this.porta = "";
		this.ipServer = "";
		this.socket = null;
		this.senhaCriptografada = "";
	}

	public void tentarConexaoServer() throws NumberFormatException,
			UnknownHostException, ConnectException, IOException {
		this.socket = new Socket(this.ipServer, Integer.parseInt(this.porta));
	}

	public void setCliente(String nick, String ipServer, String porta,
			String senhaCriptografada) {
		this.nick = nick;
		this.ipServer = ipServer;
		this.porta = porta;
		this.senhaCriptografada = senhaCriptografada;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

}
