package gui;

import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import logica.Cliente;
import logica.DataOutput;

public class WaitingEnemy extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton cancelButton = null;
	private JLabel jLabel = null;
	private String nickName;  //  @jve:decl-index=0:
	private Cliente client;
	private ObjectInputStream input = null;
	private String packet = null;


	/**
	 * @param owner
	 */
	public WaitingEnemy() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setLocationRelativeTo(null);
		this.setSize(300, 101);
		this.setTitle("Waiting Enemy");
		this.setContentPane(getJContentPane());
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 7, 275, 16));
			jLabel.setText("Please, wait while your enemy don't show up...");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCancelButton(), null);
			jContentPane.add(jLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cancelButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(107, 40, 73, 26));
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return cancelButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
