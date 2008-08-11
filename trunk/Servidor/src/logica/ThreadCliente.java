package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class ThreadCliente extends Thread {

	private Socket conexao = null;

	private JTextArea logTextArea;

	private ObjectOutputStream saida = null;

	private ObjectInputStream entrada = null;

	public ThreadCliente(Socket socket, JTextArea logTextArea) {
		this.conexao = socket;
		this.logTextArea = logTextArea;
        //criação dos canais de entrada e saida de pacotes
		/*esta pausando a execução da thread, não sei se porque ainda não foi implementado
		  os canais no cliente, ou se proque é necessário fazer essa comunicação em outra thread*/
		/*try {
			saida = new ObjectOutputStream( conexao.getOutputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			saida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			entrada = new ObjectInputStream( conexao.getInputStream() );
		} catch (IOException e) {
			e.printStackTrace();
		}*/
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
		// enviando msg de teste ao cliente
		/*try {
			saida.writeObject("** Seja bem vindo ao Servidor **");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			saida.flush();
		} catch (IOException e2) {
			e2.printStackTrace();
		}*/
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
