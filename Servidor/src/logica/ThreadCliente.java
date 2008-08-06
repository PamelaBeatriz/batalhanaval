package logica;

import java.io.IOException;
import java.net.Socket;

public class ThreadCliente extends Thread {

	private Socket conexao = null;

	public ThreadCliente(Socket socket) {
		this.conexao = socket;
	}

	@Override
	protected void finalize() {
		try{
		    //Encerro o ServerSocket
			this.conexao.close();
		}catch(IOException e){
		}
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
