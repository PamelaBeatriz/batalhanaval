package gui;

import java.awt.Rectangle;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.LookAndFeel;
import logica.Cliente;

public class WaitingEnemy extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton cancelButton = null;
	private JLabel jLabel = null;
	private String nickName;  //  @jve:decl-index=0:
	private Cliente client;
	private ObjectInputStream input = null;
	private String packet = null;
	private ImageIcon icone = new ImageIcon("src/images/icon.gif");
	private JFrame frame = null;

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
	    this.setVisible(true);
	    this.setLocationRelativeTo(null);
	    this.setSize(309, 109);
	    this.setTitle("Waiting Enemy");
	    this.setContentPane(getJContentPane());
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
			cancelButton.setText("Exit");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return cancelButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
