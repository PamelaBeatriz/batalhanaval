package util;

import gui.telaConectToServer;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Splash extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel splashPanel = null;
	private JProgressBar splashProgressBar = null;
	private JLabel imgSplashLabel = null;
	private Thread thread;

	/**
	 * This is the default constructor
	 */
	public Splash() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(420, 555);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);

		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.thread = new Thread(new Progress(this));
		this.thread.start();
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getSplashPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes splashPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getSplashPanel() {
		if (splashPanel == null) {
			imgSplashLabel = new JLabel();
			imgSplashLabel.setBounds(new Rectangle(0, 0, 420, 538));
			imgSplashLabel.setText("");
			imgSplashLabel.setIcon(new ImageIcon("src\\images\\splash.jpg"));
			splashPanel = new JPanel();
			splashPanel.setLayout(null);
			splashPanel.setBackground(new Color(0, 0, 0));
			splashPanel.setBounds(new Rectangle(0, 0, 420, 555));
			splashPanel.add(getSplashProgressBar(), null);
			splashPanel.add(imgSplashLabel, null);
		}
		return splashPanel;
	}

	/**
	 * This method initializes splashProgressBar
	 *
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getSplashProgressBar() {
		if (splashProgressBar == null) {
			splashProgressBar = new JProgressBar(0, 100);
			splashProgressBar.setBounds(new Rectangle(0, 539, 420, 15));
			splashProgressBar.setStringPainted(true);
			splashProgressBar.setBackground(Color.white);
			splashProgressBar.setForeground(Color.BLACK);
		}
		return splashProgressBar;
	}

	public class Progress implements Runnable {

		private JFrame frame = null;

		public Progress(JFrame frame) {
			this.frame = frame;
		}

		@SuppressWarnings("static-access")
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for (int i = 1; i < 100; i++) {
				splashProgressBar.setValue(i);
				splashProgressBar.setString(i + " %");
				try {
					thread.sleep(72);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("ERRO NO SPLASH");
					frame.dispose();
				}
			}
			frame.dispose();
			MP3.close();
			new telaConectToServer();
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"
