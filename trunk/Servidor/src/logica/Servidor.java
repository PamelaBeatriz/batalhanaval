package logica;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor extends Thread {

	private String porta = "";

	private String ipLocal = "";

	private String nome = "";

	private String senhaCriptografada = "";

	public void startServidor(String nome, String ipLocal, String senhaCriptografada) {
		this.nome = nome;
		this.ipLocal = ipLocal;
		this.senhaCriptografada = senhaCriptografada;
		try {
			this.inicializar();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("SERVIDOR DESCONHECIDO");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERRO NA INIALIZACAO DO SERVIDOR");
		}
	}

	public Servidor() {
		this.setIpLocal();
		this.setNome();
	}

	private void inicializar() throws UnknownHostException, IOException {
		Socket socket = new Socket(this.ipLocal, 8989);

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

}
