package main;
import gui.telaCreateServer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				telaCreateServer server = new telaCreateServer();
				server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				server.setVisible(true);
				server.setResizable(false);
				server.setLocationRelativeTo(null);
			}
		});
	}

}
