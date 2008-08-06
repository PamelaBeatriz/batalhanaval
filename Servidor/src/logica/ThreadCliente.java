package logica;

import java.net.Socket;

public class ThreadCliente extends Thread {

	private Socket conexao = null;

	public ThreadCliente(Socket socket) {
		this.conexao = socket;
	}

	@Override
	public void run() {

	}

	public Socket getConexao() {
		return conexao;
	}

	public void setConexao(Socket conexao) {
		this.conexao = conexao;
	}

}
