package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NWdata extends Thread {

	private Socket socket = null;

	private ObjectInputStream entrada = null;

	private ObjectOutputStream saida = null;

	private Cliente client;

	public NWdata(Cliente cliente) {
		this.socket = cliente.getSocket();
	}

	@Override
	public void run() {
		super.run();
		try {
			saida = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			saida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*try {
			entrada = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		while (true) {
			try {
				saida.writeObject("\n> Cliente diz: Olá!");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				saida.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
