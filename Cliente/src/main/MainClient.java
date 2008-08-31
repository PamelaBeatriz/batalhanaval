package main;

import javax.swing.SwingUtilities;

import util.MP3;
import util.Splash;

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
				MP3.play(MP3.YELLOW_SUBMARINE);
				new Splash();

			}
		});
	}

}
