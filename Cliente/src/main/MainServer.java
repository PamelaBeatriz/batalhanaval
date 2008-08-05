package main;
import gui.telaConectToServer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class MainServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				telaConectToServer client = new telaConectToServer();
				client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.setResizable(false);
				client.setLocationRelativeTo(null);
			}
		});
	}

}
