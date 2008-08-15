package main;

import gui.telaCreateServer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				telaCreateServer server = new telaCreateServer(2);
				server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				server.setVisible(true);
				server.setResizable(false);
				server.setLocationRelativeTo(null);
			}
		});
	}

}
