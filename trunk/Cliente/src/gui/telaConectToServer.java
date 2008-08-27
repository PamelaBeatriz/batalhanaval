package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;

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

import logica.Cliente;
import logica.DataOutput;
import util.MD5;

public class telaConectToServer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel painelPrincipal = null;
	private JLabel nickLabel = null;
	private JTextField nickField = null;
	private JLabel portaLabel = null;
	private JTextField portaField = null;
	private JLabel passwordLabel = null;
	private JPasswordField passwordField = null;
	private JButton connectButton = null;
	private JButton cancelButton = null;
	private JLabel IP = null;
	private JTextField IPfield = null;
	private Cliente cliente = null;
	private javax.swing.UIManager.LookAndFeelInfo looks[];

	/**
	 * This is the default constructor
	 */
	public telaConectToServer(int index) {
		super();
		this.cliente = new Cliente();
		looks = javax.swing.UIManager.getInstalledLookAndFeels();
		try {
			javax.swing.UIManager.setLookAndFeel(looks[index].getClassName());
			javax.swing.SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */

	private void initialize() {
		this.setIconImage(new ImageIcon("src/images/icon.gif").getImage());
		this.setSize(306, 237);
		this.setContentPane(getJContentPane());
		this.setTitle("Connect to Server - Batalha Naval");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
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
			jContentPane.add(getPainelPrincipal(), null);
			jContentPane.add(getConnectButton(), null);
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
			IP = new JLabel();
			IP.setBounds(new Rectangle(13, 57, 38, 16));
			IP.setText("IP:");
			passwordLabel = new JLabel();
			passwordLabel.setBounds(new Rectangle(11, 111, 68, 16));
			passwordLabel.setText("Password: ");
			portaLabel = new JLabel();
			portaLabel.setBounds(new Rectangle(12, 84, 38, 16));
			portaLabel.setText("Port: ");
			nickLabel = new JLabel();
			nickLabel.setBounds(new Rectangle(12, 29, 58, 16));
			nickLabel.setText("Nick:");
			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(null);
			painelPrincipal.setBounds(new Rectangle(7, 6, 280, 146));
			painelPrincipal.setBorder(BorderFactory.createTitledBorder(null,
					"Main Painel", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			painelPrincipal.add(nickLabel, null);
			painelPrincipal.add(getNickField(), null);
			painelPrincipal.add(portaLabel, null);
			painelPrincipal.add(getPortaField(), null);
			painelPrincipal.add(passwordLabel, null);
			painelPrincipal.add(getPasswordField(), null);
			painelPrincipal.add(IP, null);
			painelPrincipal.add(getIPfield(), null);
		}
		return painelPrincipal;
	}

	/**
	 * This method initializes nickField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getNickField() {
		if (nickField == null) {
			nickField = new JTextField();
			nickField.setBounds(new Rectangle(90, 27, 165, 19));
			nickField
					.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		return nickField;
	}

	/**
	 * This method initializes portaField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getPortaField() {
		if (portaField == null) {
			portaField = new JTextField();
			portaField.setBounds(new Rectangle(90, 82, 51, 20));

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
			passwordField.setBounds(new Rectangle(90, 110, 161, 20));
		}
		return passwordField;
	}

	/**
	 * This method initializes connectButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getConnectButton() {
		if (connectButton == null) {
			connectButton = new JButton();
			connectButton.setBounds(new Rectangle(54, 170, 128, 27));
			connectButton.setText("Connect");
			final JFrame frame = this;
			connectButton
					.addActionListener(new java.awt.event.ActionListener() {

						@SuppressWarnings("deprecation")
						public void actionPerformed(java.awt.event.ActionEvent e) {
							frame.setVisible(false);
							cliente.setCliente(nickField.getText(), IPfield
									.getText(), portaField.getText(), MD5
									.getHash(passwordField.getText().getBytes()));
							try {
								if (cliente.tentarConexaoServer()) {
									frame.setVisible(false);
									String packet = null;

									/*
									 * manda a senha para o server
									 */
									new DataOutput(cliente)
											.SendPacket(new String("**"
													+ MD5.getHash(passwordField
															.getText()
															.toString()
															.getBytes())));

									ObjectInputStream input = new ObjectInputStream(
											cliente.getSocket()
													.getInputStream());

									try {
										packet = (String) input.readObject();
									} catch (ClassNotFoundException e1) {
										e1.printStackTrace();
									}

									/*
									 * se a senha nao for valida
									 */
									if (packet.substring(0, 2).equals("##")) {
										JOptionPane.showMessageDialog(null,
												packet.substring(2),
												"Batalha Naval - Erro",
												JOptionPane.ERROR_MESSAGE);
										frame.setVisible(true);
									}

									/*
									 * se a senha for valida, simbora!
									 */
									else {
										/*input = new ObjectInputStream(cliente
												.getSocket().getInputStream());

										try {
											packet = (String) input
													.readObject();
										} catch (ClassNotFoundException e1) {
											e1.printStackTrace();
										}*/
										if (packet.substring(0, 2).equals("OK")) {
											new DataOutput(cliente)
													.SendPacket(new String("NK"
															+ cliente.getNick()));
											new TelaJogo(nickField.getText(),
													cliente);
										} else if (packet.substring(0, 2)
												.equals("SF")) {
											JOptionPane.showMessageDialog(null,
													packet.substring(2),
													"Batalha Naval - Erro",
													JOptionPane.ERROR_MESSAGE);
											frame.setVisible(true);
										}
									}

								} else {
									frame.setVisible(true);
								}
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(null,
										"Porta Inválida",
										"Batalha Naval - Erro",
										JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
								frame.setVisible(true);
							} catch (UnknownHostException e1) {
								JOptionPane.showMessageDialog(null,
										"Endereco IP Desconhecido",
										"Batalha Naval - Erro",
										JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
								frame.setVisible(true);
							} catch (ConnectException e1) {
								JOptionPane.showMessageDialog(null,
										"Conexão recusada",
										"Batalha Naval - Erro",
										JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
								frame.setVisible(true);
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "Erro",
										"Batalha Naval - Erro",
										JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
								frame.setVisible(true);
							}
						}
					});
		}
		return connectButton;
	}

	/**
	 * This method initializes cancelButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(192, 170, 95, 26));
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
	 * This method initializes IPfield
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getIPfield() {
		if (IPfield == null) {
			IPfield = new JTextField();
			IPfield.setBounds(new Rectangle(90, 54, 100, 20));
			IPfield.setText("localhost");
		}
		return IPfield;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
