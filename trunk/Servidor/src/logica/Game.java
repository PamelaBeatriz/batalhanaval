package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game extends Thread {

	private ThreadCliente c1;

	private ThreadCliente c2;

	public Game(/*Cliente c1, Cliente c2*/) {
		// TODO Auto-generated constructor stub
		/*        try {
    		Packet packet = null;
        	input = new ObjectInputStream( conexao.getInputStream() );
			while((packet = (Packet) input.readObject()) != null) {
				this.logTextArea.append("\n> " + packet.getFrom() + " diz para " + packet.getTo() +
						": " + packet.getType() + "\n Dados: " + packet.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    try {
	    	this.logTextArea.append("\n> Cliente desconectou [" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "]");
	    	this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//this.start();
	}

}
