package main;

import gui.telaConectToServer;

import javax.swing.SwingUtilities;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					javax.swing.UIManager
							.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
				} catch (Exception e) {
					e.printStackTrace();
				}

				new telaConectToServer();
			}
		});
	}

}
