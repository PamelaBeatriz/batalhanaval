package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logica.Servidor;
import util.MD5;

public class telaCreateServer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel painelPrincipal = null;
	private JLabel NomeServerLabel = null;
	private JTextField nomeServerField = null;
	private JLabel portaLabel = null;
	private JTextField portaField = null;
	private JLabel passwordLabel = null;
	private JPasswordField passwordField = null;
	private JButton startButton = null;
	private JButton cancelButton = null;
	private JLabel ipLabel = null;
	private JTextField ipField = null;
	private Servidor servidor = null;


	/**
	 * This is the default constructor
	 */
	public telaCreateServer() {
		super();
		this.servidor = new Servidor();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setIconImage(new ImageIcon("src/images/icon.gif").getImage());
		this.setSize(335, 237);
		this.setContentPane(getJContentPane());
		this.setTitle("Create Server - Batalha Naval");
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
			jContentPane.add(getPainelPrincipal(), null);
			jContentPane.add(getStartButton(), null);
			jContentPane.add(getCancelButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes painelPrincipal
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getPainelPrincipal() {
		if (painelPrincipal == null) {
			ipLabel = new JLabel();
			ipLabel.setBounds(new Rectangle(15, 121, 61, 16));
			ipLabel.setText("IP Server:");
			passwordLabel = new JLabel();
			passwordLabel.setBounds(new Rectangle(11, 86, 68, 16));
			passwordLabel.setText("Password: ");
			portaLabel = new JLabel();
			portaLabel.setBounds(new Rectangle(12, 59, 38, 16));
			portaLabel.setText("Port: ");
			NomeServerLabel = new JLabel();
			NomeServerLabel.setBounds(new Rectangle(12, 29, 58, 16));
			NomeServerLabel.setText("Name: ");
			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(null);
			painelPrincipal.setBounds(new Rectangle(7, 6, 309, 153));
			painelPrincipal.setBorder(BorderFactory.createTitledBorder(null,
					"Main Painel", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			painelPrincipal.add(NomeServerLabel, null);
			painelPrincipal.add(getNomeServerField(), null);
			painelPrincipal.add(portaLabel, null);
			painelPrincipal.add(getPortaField(), null);
			painelPrincipal.add(passwordLabel, null);
			painelPrincipal.add(getPasswordField(), null);
			painelPrincipal.add(ipLabel, null);
			painelPrincipal.add(getIpField(), null);
		}
		return painelPrincipal;
	}

	/**
	 * This method initializes nomeServerField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getNomeServerField() {
		if (nomeServerField == null) {
			nomeServerField = new JTextField();
			nomeServerField.setBounds(new Rectangle(90, 27, 165, 19));
			nomeServerField.setText(this.servidor.getNome());
			nomeServerField
					.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return nomeServerField;
	}

	/**
	 * This method initializes portaField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getPortaField() {
		if (portaField == null) {
			portaField = new JTextField();
			portaField.setBounds(new Rectangle(90, 57, 51, 20));

			portaField.setText("9999");
		}
		return portaField;
	}

	/**
	 * This method initializes passwordField
	 *
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(new Rectangle(90, 86, 161, 20));
		}
		return passwordField;
	}

	/**
	 * This method initializes startButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getStartButton() {
		if (startButton == null) {
			startButton = new JButton();
			startButton.setBounds(new Rectangle(66, 169, 128, 27));
			startButton.setText("Start Server");
			startButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (servidor.startServidor(nomeServerField.getText(),
							portaField.getText(), MD5.getHash(passwordField
									.getPassword().toString().getBytes()))) {
						setVisible(false);
						new TelaGerenciaServer(nomeServerField.getText(),
								portaField.getText(), ipField.getText(),servidor);

					} else {
						JOptionPane.showMessageDialog(null,
								"Erro na Criacao do Servidor",
								"Create Server - Batalha Naval",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return startButton;
	}

	/**
	 * This method initializes cancelButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(221, 170, 95, 26));
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes ipField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getIpField() {
		if (ipField == null) {
			ipField = new JTextField();
			ipField.setBounds(new Rectangle(89, 121, 138, 20));
			ipField.setEditable(false);
			ipField.setText(this.servidor.getIpLocal());
		}
		return ipField;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
