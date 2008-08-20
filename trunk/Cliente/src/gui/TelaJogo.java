package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import logica.Cliente;
import logica.DataOutput;
import logica.Packet;

public class TelaJogo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private String nickName = ""; // @jve:decl-index=0:
	private JPanel chatJPanel = null;
	private JPanel tabuleiroPanel = null;
	private TabuleiroDaCasa tabuleiroDaCasa = null;
	private TabuleiroDoInimigo tabuleiroDoInimigo = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileJMenu = null;
	private JMenu settingJMenu = null;
	private JMenuItem cheatJMenuItem = null;
	private JMenuItem novoJogoJMenuItem = null;
	private JMenuItem sairJMenuItem = null;
	private JMenu helpJMenu = null;
	private JMenuItem comoJogarJMenuItem = null;
	private JMenuItem sobreJMenuItem = null;
	private PainelControle painelControle = null;
	private Cliente client;
	private JLabel tabuleiroCasaLabel = null;
	private JTextArea chatTextArea = null;
	private DataOutput output;
	private JScrollPane chatScrollPane = null;
	private JTextField chatTextField = null;
	private String cheat = null;

	/**
	 * This is the default constructor
	 */
	public TelaJogo(Socket socket) {
		super();
		initialize();
	}

	public TelaJogo(String nickName, Cliente cliente) {
		super();
		this.nickName = nickName;
		this.client = cliente;
		this.output = new DataOutput(this.client);
		this.client.setChatArea(this.chatTextArea);
		initialize();
		this.client.start();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(637, 622);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Batalha Naval - O Jogo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
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
			jContentPane.add(getChatJPanel(), null);
			jContentPane.add(getPainelControleJPanel(), null);
			jContentPane.add(getTabuleiroPanel(), null);
			this.tabuleiroDaCasa.setPainelControle(this.painelControle);
			this.painelControle.setTabuleiroDoInimigo(this.tabuleiroDoInimigo);
		}
		return jContentPane;
	}

	/**
	 * This method initializes chatJPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getChatJPanel() {
		if (chatJPanel == null) {
			chatJPanel = new JPanel();
			chatJPanel.setLayout(null);
			chatJPanel.setBounds(new Rectangle(237, 314, 385, 243));
			chatJPanel.setBorder(BorderFactory.createTitledBorder(null, "Chat",
					TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			chatJPanel.add(getChatScrollPane(), null);
			chatJPanel.add(getChatTextField(), null);
		}
		return chatJPanel;
	}

	/**
	 * This method initializes tabuleiroPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTabuleiroPanel() {
		if (tabuleiroPanel == null) {
			tabuleiroCasaLabel = new JLabel();
			tabuleiroCasaLabel.setBounds(new Rectangle(16, 276, 108, 16));
			tabuleiroCasaLabel.setText("Capitão: " + this.nickName);
			tabuleiroPanel = new JPanel();
			tabuleiroPanel.setLayout(null);
			tabuleiroPanel.setBounds(new Rectangle(5, 4, 616, 301));
			tabuleiroPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Tabuleiros", TitledBorder.CENTER,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			tabuleiroPanel.add(getCasaTabuleiroPanel(), null);
			tabuleiroPanel.add(getAdversarioTabuleiroPanel(), null);
			tabuleiroPanel.add(tabuleiroCasaLabel, null);
		}
		return tabuleiroPanel;
	}

	/**
	 * This method initializes casaTabuleiroPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getCasaTabuleiroPanel() {
		if (tabuleiroDaCasa == null) {
			tabuleiroDaCasa = new TabuleiroDaCasa();
			tabuleiroDaCasa.setLocation(new Point(9, 13));
			tabuleiroDaCasa.setSize(new Dimension(250, 250));
		}
		return tabuleiroDaCasa;
	}

	/**
	 * This method initializes adversarioTabuleiroPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getAdversarioTabuleiroPanel() {
		if (tabuleiroDoInimigo == null) {
			tabuleiroDoInimigo = new TabuleiroDoInimigo();
			tabuleiroDoInimigo.setLocation(new Point(356, 13));
			tabuleiroDoInimigo.setSize(new Dimension(250, 250));
		}
		return tabuleiroDoInimigo;
	}

	/**
	 * This method initializes jJMenuBar
	 *
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setLocation(new Point(0, 0));
			jJMenuBar.add(getFileJMenu());
			jJMenuBar.add(getSettingJMenu());
			jJMenuBar.add(getHelpJMenu());

		}
		return jJMenuBar;
	}

	/**
	 * This method initializes fileJMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getSettingJMenu() {
		if (settingJMenu == null) {
			settingJMenu = new JMenu();
			settingJMenu.setText("Setting");
			settingJMenu.setMnemonic(KeyEvent.VK_S);
			settingJMenu.add(getCheatJMenuItem());
			settingJMenu.addSeparator();
		}
		return settingJMenu;
	}

	/**
	 * This method initializes fileJMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileJMenu() {
		if (fileJMenu == null) {
			fileJMenu = new JMenu();
			fileJMenu.setText("Game");
			fileJMenu.setMnemonic(KeyEvent.VK_G);
			fileJMenu.add(getNovoJogoJMenuItem());
			fileJMenu.addSeparator();
			fileJMenu.add(getSairJMenuItem());
		}
		return fileJMenu;
	}

	/**
	 * This method initializes fileJMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenuItem getCheatJMenuItem() {
		if (cheatJMenuItem == null) {
			cheatJMenuItem = new JMenuItem();
			cheatJMenuItem.setText("Cheat");
			cheatJMenuItem.setMnemonic(KeyEvent.VK_C);
			KeyStroke F10 = KeyStroke.getKeyStroke("F10");
			cheatJMenuItem.setAccelerator(F10);
			cheatJMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							cheat = JOptionPane.showInputDialog(null,
									"Cheat: ", "Batalha Naval",
									JOptionPane.QUESTION_MESSAGE);
						}
					});
		}
		return cheatJMenuItem;
	}

	/**
	 * This method initializes novoJogoJMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNovoJogoJMenuItem() {
		if (novoJogoJMenuItem == null) {
			novoJogoJMenuItem = new JMenuItem();
			novoJogoJMenuItem.setText("New Game");
			novoJogoJMenuItem.setMnemonic(KeyEvent.VK_N);
			KeyStroke F2 = KeyStroke.getKeyStroke("F2");
			novoJogoJMenuItem.setAccelerator(F2);
		}
		return novoJogoJMenuItem;
	}

	/**
	 * This method initializes sairJMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSairJMenuItem() {
		if (sairJMenuItem == null) {
			sairJMenuItem = new JMenuItem();
			sairJMenuItem.setText("Quit");
			sairJMenuItem.setMnemonic(KeyEvent.VK_Q);
			KeyStroke altF4 = KeyStroke.getKeyStroke("alt F4");
			sairJMenuItem.setAccelerator(altF4);
			sairJMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return sairJMenuItem;
	}

	/**
	 * This method initializes helpJMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpJMenu() {
		if (helpJMenu == null) {
			helpJMenu = new JMenu();
			helpJMenu.setText("Help");
			helpJMenu.setMnemonic(KeyEvent.VK_H);
			helpJMenu.setLocation(new Point(0, 0));
			helpJMenu.add(getComoJogarJMenuItem());
			helpJMenu.addSeparator();
			helpJMenu.add(getSobreJMenuItem());

		}
		return helpJMenu;
	}

	/**
	 * This method initializes comoJogarJMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getComoJogarJMenuItem() {
		if (comoJogarJMenuItem == null) {
			comoJogarJMenuItem = new JMenuItem();
			comoJogarJMenuItem.setText("Playing...");
			comoJogarJMenuItem.setMnemonic(KeyEvent.VK_P);
			KeyStroke ajudaF1 = KeyStroke.getKeyStroke("F1");
			comoJogarJMenuItem.setAccelerator(ajudaF1);
		}
		return comoJogarJMenuItem;
	}

	/**
	 * This method initializes sobreJMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSobreJMenuItem() {
		if (sobreJMenuItem == null) {
			sobreJMenuItem = new JMenuItem();
			sobreJMenuItem.setText("About");
			sobreJMenuItem.setMnemonic(KeyEvent.VK_A);
		}
		return sobreJMenuItem;
	}

	/**
	 * This method initializes painelControleJPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getPainelControleJPanel() {
		if (painelControle == null) {
			painelControle = new PainelControle();
			painelControle.setSize(new Dimension(210, 250));
			painelControle.setLocation(new Point(5, 310));
		}
		return painelControle;
	}

	/**
	 * This method initializes chatTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getChatTextArea() {
		if (chatTextArea == null) {
			chatTextArea = new JTextArea();
			chatTextArea.setEditable(false);
		}
		return chatTextArea;
	}

	/**
	 * This method initializes chatTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getChatTextField() {
		if (chatTextField == null) {
			chatTextField = new JTextField();
			chatTextField.setBounds(new Rectangle(12, 210, 361, 20));
			chatTextField
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Packet packet = new Packet("CHAT", chatTextField
									.getText());
							output.SendPacket(packet);
							chatTextArea.append(" ["
									+ new SimpleDateFormat("HH:mm:ss")
											.format(new Date()) + "]"
									+ nickName + " diz: "
									+ chatTextField.getText() + "\n");
							chatTextField.setText("");
						}
					});
		}
		return chatTextField;
	}

	/**
	 * This method initializes chatScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getChatScrollPane() {
		if (chatScrollPane == null) {
			chatScrollPane = new JScrollPane();
			chatScrollPane.setBounds(new Rectangle(12, 16, 361, 190));
			chatScrollPane.setViewportView(getChatTextArea());
		}
		return chatScrollPane;
	}

	public String getCheat() {
		return cheat;
	}

	public void setCheat(String cheat) {
		this.cheat = cheat;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Get a tela do Jogo
	 *
	 * @return JFrame this
	 */
	public JFrame getTelaJogo() {
		return this;
	}

} // @jve:decl-index=0:visual-constraint="10,17"
