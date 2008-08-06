package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
//import java.awt.Dimension;
//import javax.swing.JTextPane;
//import java.awt.GridBagLayout;
import javax.swing.JTextArea;

import logica.Servidor;

public class TelaGerenciaServer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel painelControlePanel = null;
	private JLabel nameServerLabel = null;
	private JLabel portaLabel = null;
	private JLabel ipLabel = null;
	private JTextField nomeField = null;
	private JTextField portaField = null;
	private JTextField ipField = null;
	private JPanel clientePanel = null;
	private JButton fecharButton = null;
	private JPanel logPanel = null;
	private JTextArea logTextArea = null;
	private Servidor servidor;

	/**
	 *
	 * @param nome
	 * @param porta
	 * @param ip
	 * @param servidor
	 */
	public TelaGerenciaServer(String nome, String porta, String ip, Servidor servidor) {
		super();
		initialize();
		this.nomeField.setText(nome);
		this.portaField.setText(porta);
		this.ipField.setText(ip);
		this.nomeField.setEditable(false);
		this.portaField.setEditable(false);
		this.ipField.setEditable(false);
		this.logTextArea.setText("** Servidor Inicializado **");
		this.servidor = servidor;
		final Servidor server = this.servidor;
		class waitConn extends Thread {
			public void run() {
				server.esperaConexaoCliente();
			}
		}
		Thread esperarCliente = new waitConn();
		esperarCliente.start();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(614, 417);
		this.setContentPane(getJContentPane());
		this.setTitle("Server Manager - Batalha Naval");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
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
			jContentPane.add(getPainelControlePanel(), null);
			jContentPane.add(getClientePanel(), null);
			jContentPane.add(getFecharButton(), null);
			jContentPane.add(getLogPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes painelControlePanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getPainelControlePanel() {
		if (painelControlePanel == null) {
			ipLabel = new JLabel();
			ipLabel.setBounds(new Rectangle(16, 90, 38, 16));
			ipLabel.setText("IP:");
			portaLabel = new JLabel();
			portaLabel.setBounds(new Rectangle(14, 60, 38, 16));
			portaLabel.setText("Port: ");
			nameServerLabel = new JLabel();
			nameServerLabel.setBounds(new Rectangle(15, 30, 47, 16));
			nameServerLabel.setText("Name: ");
			painelControlePanel = new JPanel();
			painelControlePanel.setLayout(null);
			painelControlePanel.setBounds(new Rectangle(8, 7, 293, 130));
			painelControlePanel.setBorder(BorderFactory.createTitledBorder(null, "Control Painel - Server", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			painelControlePanel.add(nameServerLabel, null);
			painelControlePanel.add(portaLabel, null);
			painelControlePanel.add(ipLabel, null);
			painelControlePanel.add(getNomeField(), null);
			painelControlePanel.add(getPortaField(), null);
			painelControlePanel.add(getIpField(), null);
		}
		return painelControlePanel;
	}

	/**
	 * This method initializes nomeField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getNomeField() {
		if (nomeField == null) {
			nomeField = new JTextField();
			nomeField.setBounds(new Rectangle(76, 30, 162, 20));
		}
		return nomeField;
	}

	/**
	 * This method initializes portaField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getPortaField() {
		if (portaField == null) {
			portaField = new JTextField();
			portaField.setBounds(new Rectangle(77, 59, 56, 20));
		}
		return portaField;
	}

	/**
	 * This method initializes ipField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getIpField() {
		if (ipField == null) {
			ipField = new JTextField();
			ipField.setBounds(new Rectangle(77, 91, 162, 20));
		}
		return ipField;
	}

	/**
	 * This method initializes clientePanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getClientePanel() {
		if (clientePanel == null) {
			clientePanel = new JPanel();
			clientePanel.setLayout(null);
			clientePanel.setBounds(new Rectangle(309, 5, 288, 131));
			clientePanel.setBorder(BorderFactory.createTitledBorder(null, "Clients", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return clientePanel;
	}

	/**
	 * This method initializes fecharButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getFecharButton() {
		if (fecharButton == null) {
			fecharButton = new JButton();
			fecharButton.setBounds(new Rectangle(492, 359, 109, 24));
			fecharButton.setText("Close Server");
			fecharButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return fecharButton;
	}

	/**
	 * This method initializes logPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getLogPanel() {
		if (logPanel == null) {
			logPanel = new JPanel();
			logPanel.setLayout(null);
			logPanel.setBounds(new Rectangle(7, 150, 590, 194));
			logPanel.setBorder(BorderFactory.createTitledBorder(null, "Log", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			logPanel.add(getLogTextArea(), null);
		}
		return logPanel;
	}

	/**
	 * This method initializes logTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getLogTextArea() {
		if (logTextArea == null) {
			logTextArea = new JTextArea();
			logTextArea.setBounds(new Rectangle(17, 22, 556, 156));
			logTextArea.setEditable(false);
		}
		return logTextArea;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
