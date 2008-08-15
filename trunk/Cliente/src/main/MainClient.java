package main;
import gui.telaConectToServer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.Container;
import java.awt.GridLayout;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				telaConectToServer client = new telaConectToServer(2);
				client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.setResizable(false);
				client.setLocationRelativeTo(null);
			}
		});
	}

}
