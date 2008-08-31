package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel aboutPanel = null;
	private JLabel imagem = null;
	private JPanel dadosPanel = null;
	private JLabel versionLabel = null;
	private JLabel softwareLabel = null;
	private JLabel ChrisJhessica = null;
	private JLabel ThiagoXanxe = null;

	/**
	 * This is the default constructor
	 */
	public About() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(424, 655);
		this.setIconImage(new ImageIcon("src/images/icon.gif").getImage());
		this.setContentPane(getJContentPane());
		this.setTitle("Batalha Naval - About");
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
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
			jContentPane.add(getAboutPanel(), null);
			jContentPane.add(getDadosPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes aboutPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutPanel() {
		if (aboutPanel == null) {
			imagem = new JLabel();
			imagem.setBounds(new Rectangle(0, 0, 420, 538));
			imagem.setIcon(new ImageIcon("src/images/splash.jpg"));
			imagem.setText("");
			aboutPanel = new JPanel();
			aboutPanel.setLayout(null);
			aboutPanel.setBounds(new Rectangle(0, 0, 420, 538));
			aboutPanel.add(imagem, null);
		}
		return aboutPanel;
	}

	/**
	 * This method initializes dadosPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getDadosPanel() {
		if (dadosPanel == null) {
			ThiagoXanxe = new JLabel();
			ThiagoXanxe.setBounds(new Rectangle(58, 65, 347, 16));
			ThiagoXanxe
					.setText("Pedro Sanches Junior   -   Thiago Augusto L. Genez");
			ThiagoXanxe.setForeground(Color.BLACK);
			ThiagoXanxe.setFont(new Font("Arial Bold", Font.BOLD, 13));
			ChrisJhessica = new JLabel();
			ChrisJhessica.setBounds(new Rectangle(54, 46, 320, 16));
			ChrisJhessica
					.setText("Chris Andrew C. Lopes   -   Jhessica Mary Kanda");
			ChrisJhessica.setFont(new Font("Arial Bold", Font.BOLD, 13));
			ChrisJhessica.setForeground(Color.BLACK);
			softwareLabel = new JLabel();
			softwareLabel.setBounds(new Rectangle(119, 28, 200, 16));
			softwareLabel.setText("This Game is developed by:");
			softwareLabel.setForeground(Color.BLACK);
			softwareLabel.setFont(new Font("Arial Bold", Font.BOLD, 13));
			versionLabel = new JLabel();
			versionLabel.setBounds(new Rectangle(120, 10, 195, 16));
			versionLabel.setFont(new Font("Arial Bold", Font.BOLD, 13));
			versionLabel.setText("Batalha Naval - Version 1.1.2b");
			versionLabel.setForeground(Color.BLACK);
			dadosPanel = new JPanel();
			dadosPanel.setLayout(null);
			dadosPanel.setBackground(new Color(255, 255, 255));
			dadosPanel.setForeground(Color.WHITE);
			dadosPanel.setBounds(new Rectangle(0, 538, 420, 93));
			dadosPanel.setVisible(true);
			dadosPanel.setEnabled(true);
			dadosPanel.add(versionLabel, null);
			dadosPanel.add(softwareLabel, null);
			dadosPanel.add(ChrisJhessica, null);
			dadosPanel.add(ThiagoXanxe, null);
		}
		return dadosPanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
