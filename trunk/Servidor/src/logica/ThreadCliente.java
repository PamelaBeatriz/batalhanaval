package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class ThreadCliente extends Thread {

	private Socket conexao = null;

	private JTextArea logTextArea;

	public ThreadCliente(Socket socket, JTextArea logTextArea) {
		this.conexao = socket;
		this.logTextArea = logTextArea;
	}

	@Override
	protected void finalize() {
		try {
			// Encerro o ServerSocket
			this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("cliente conectou server no server");
		while(true) {
			BufferedReader entrada = null;
			try {
				entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String linha = null;
			try {
				linha = entrada.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    while (linha != null && !(linha.trim().equals(""))) {
		        try {
					linha = entrada.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
			try {
				this.conexao.close();
				this.logTextArea.append("\n> Cliente desconectou");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Socket getConexao() {
		return conexao;
	}

	public void setConexao(Socket conexao) {
		this.conexao = conexao;
	}

}
