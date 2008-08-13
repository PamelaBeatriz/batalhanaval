package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
	private JPanel casaTabuleiroPanel = null;
	private JPanel adversarioTabuleiroPanel = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileJMenu = null;
	private JMenuItem novoJogoJMenuItem = null;
	private JMenuItem sairJMenuItem = null;
	private JMenu helpJMenu = null;
	private JMenuItem comoJogarJMenuItem = null;
	private JMenuItem sobreJMenuItem = null;
	private JPanel painelControleJPanel = null;
	private Cliente client;
	private JLabel tabuleiroCasaLabel = null;
	private JTextArea chatTextArea = null;
	private JTextField inputTextField = null;
	private DataOutput output;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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
		this.client = client;
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(721, 622);
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
			jContentPane.add(getTabuleiroPanel(), null);
			jContentPane.add(getPainelControleJPanel(), null);
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
			chatJPanel.setBounds(new Rectangle(341, 332, 347, 203));
			chatJPanel.setBorder(BorderFactory.createTitledBorder(null, "Chat",
					TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			chatJPanel.add(getChatTextArea(), null);
			chatJPanel.add(getInputTextField(), null);
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
			tabuleiroPanel.setBounds(new Rectangle(5, 4, 686, 301));
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
		if (casaTabuleiroPanel == null) {
			casaTabuleiroPanel = new TabuleiroDaCasa();
			casaTabuleiroPanel.setLocation(new Point(9, 13));
			casaTabuleiroPanel.setSize(new Dimension(250, 250));
		}
		return casaTabuleiroPanel;
	}

	/**
	 * This method initializes adversarioTabuleiroPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getAdversarioTabuleiroPanel() {
		if (adversarioTabuleiroPanel == null) {
			adversarioTabuleiroPanel = new JPanel();
			adversarioTabuleiroPanel.setLayout(null);
			adversarioTabuleiroPanel
					.setBounds(new Rectangle(378, 15, 236, 227));
			adversarioTabuleiroPanel.setBorder(BorderFactory
					.createTitledBorder(null, "Adversário",
							TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
							new Font("Dialog", Font.BOLD, 12), new Color(51,
									51, 51)));
		}
		return adversarioTabuleiroPanel;
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
			jJMenuBar.add(getHelpJMenu());

		}
		return jJMenuBar;
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
		if (painelControleJPanel == null) {
			painelControleJPanel = new PainelControle();
			painelControleJPanel.setSize(new Dimension(308, 254));
			painelControleJPanel.setLocation(new Point(5, 310));
		}
		return painelControleJPanel;
	}

	/*
	 * private void initComponents(){ //Cria uma box, que é um gerenciador de
	 * containeres. Neste caso criamos uma box horizontal, e todo container
	 * adicionado à box //o será da esquerda para a direita. Para quem quiser
	 * saber mais sobre Box (BoxLayout), indiquei o link do tutorial da Sun //na
	 * descrição da classe. Box boxHorizontalCima = Box.createHorizontalBox();
	 *
	 * //Aqui criamos 4 box auxiliares. A box1CimaX é uma box horizontal que
	 * adiciona o container com as letras e o tabuleiro do //jogador. Depois
	 * "empacotamos" esta box na box1CimaY, junto com o container com os
	 * números. O mesmo acontece com box2CimaX //e box2CimaY. Explicarei melhor
	 * no decorrer do código ;-) Box box1CimaX = Box.createHorizontalBox(); Box
	 * box2CimaX = Box.createHorizontalBox(); Box box1CimaY =
	 * Box.createVerticalBox(); Box box2CimaY = Box.createVerticalBox();
	 *
	 *
	 *
	 * Dimension distPadraoX = new Dimension(10, 249); Dimension distPadraoY =
	 * new Dimension(250,10);
	 *
	 * //Criamos os containeres com as letras e números. bottom1 é um container
	 * que serve apenas para dar um espaço de preenchimento. Container letras =
	 * criarVetorLetras(); Container numeros = criarVetorNumeros(); Container
	 * bottom1 = new Container();
	 *
	 * bottom1.setPreferredSize(new Dimension(251,15));
	 *
	 * //Primeiro adicionamos um espaço de preenchimento na box horizontal, com
	 * dimensão 10,252. Isto cria o espaço entre o lado //esquerdo do frame e as
	 * letras box1CimaX.add(new
	 * Box.Filler(distPadraoX,distPadraoX,distPadraoX)); //Adicionamos as letras
	 * e o tabuleiro do jogador box1CimaX.add(letras); //
	 * box1CimaX.add(meuTabuleiro);
	 *
	 * //Depois adicionamos um espaço de preenchimento similar à box horizontal,
	 * mas agora na vertical. Isto cria o espaço entre o //a parte de cima do
	 * frame e os números box1CimaY.add(new
	 * Box.Filler(distPadraoY,distPadraoY,distPadraoY)); //Adicionamos os
	 * números e a box horizontal, para empacotar tudo numa grande box
	 * box1CimaY.add(numeros); box1CimaY.add(box1CimaX); //Adicionamos um espaço
	 * de preenchimento também na vertical. Isto cria o espaço entre o tabuleiro
	 * e o painel com os botões //(ou dados da rede). box1CimaY.add(new
	 * Box.Filler(distPadraoY,distPadraoY,distPadraoY)); //Por fim, adicionamos
	 * a box que contém o tabuleiro, letras e números na box horizontal de cima.
	 * Entenda que isto funciona //basicamente como uma série de empacotamentos
	 * de containeres. Até aqui, adicionamos apenas o tabuleiro do jogador
	 * boxHorizontalCima.add(box1CimaY); boxHorizontalCima.add(new
	 * Box.Filler(distPadraoX,distPadraoX,distPadraoX)); }
	 */

	/*	 *//**
			 * Retorna um container com as letras de cada linha do tabuleiro
			 *
			 * @return container - container com os labels
			 */
	/*
	 * private Container criarVetorLetras() {
	 *
	 * Container container = new Container(); container.setPreferredSize(new
	 * Dimension(10,250)); container.setLayout(new BoxLayout(container,
	 * BoxLayout.Y_AXIS));
	 *
	 * JLabel label = null;
	 *
	 * for(char c = 'A'; c <= 'J'; c++) { label = new JLabel(c+"");
	 * container.add(label); container.add(Box.createRigidArea(new
	 * Dimension(0,9))); } return container; }
	 *
	 *//**
		 * Retorna um container com os números de cada linha do tabuleiro
		 *
		 * @return container - container com os labels que indicam os números
		 */
	/*
	 * private Container criarVetorNumeros() {
	 *
	 * Container container = new Container();
	 *
	 * container.setPreferredSize(new Dimension(250,10));
	 * container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
	 *
	 * JLabel label = null;
	 *
	 * container.add(Box.createRigidArea(new Dimension(30,0)));
	 *
	 * for(int i = 0; i < 10; i++) { label = new JLabel(i+"");
	 * container.add(label); container.add(Box.createRigidArea(new
	 * Dimension(18,0))); } return container; }
	 */

	/**
	 * This method initializes chatTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getChatTextArea() {
		if (chatTextArea == null) {
			chatTextArea = new JTextArea();
			chatTextArea.setBounds(new Rectangle(10, 13, 324, 155));
		}
		return chatTextArea;
	}

	/**
	 * This method initializes inputTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getInputTextField() {
		if (inputTextField == null) {
			inputTextField = new JTextField();
			inputTextField.setBounds(new Rectangle(32, 178, 224, 20));
			inputTextField
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Packet packet = new Packet("FROM", "TO", "CHAT",
									inputTextField.getText());
							output.SendPacket(packet);
							chatTextArea
									.append(inputTextField.getText() + "\n");
							inputTextField.setText("");
						}
					});
		}
		return inputTextField;
	}

} // @jve:decl-index=0:visual-constraint="10,17"
