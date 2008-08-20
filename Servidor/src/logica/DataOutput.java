package logica;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DataOutput{

	private Socket socket = null;

	private ObjectOutputStream saida = null;

	//private Cliente client;

	private String packet;

	public DataOutput(Socket socket) {
		this.socket = socket;

		try {
			saida = new ObjectOutputStream(socket.getOutputStream());
			saida.flush();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public void SendPacket(String packet) {
		try {
			saida.writeObject(packet);
			saida.flush();
		} catch (IOException e2) {
			e2.printStackTrace();
			System.out.println("ERRO NO ENVIO DO PACOTE");
		}
	}
}
