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

public class CustomDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton cancelButton = null;
	private JLabel jLabel = null;
	private String msg = null;
	private String title = null;

	/**
	 * @param owner
	 */
	public CustomDialog(String title, String msg) {
		super();
		this.msg = msg;
		this.title = title;
		initialize();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.exit(0);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setIconImage(new ImageIcon("src/images/icon.gif").getImage());
	    this.setVisible(true);
	    this.setLocationRelativeTo(null);
	    this.setSize(309, 109);
	    this.setTitle(title);
	    this.setContentPane(getJContentPane());
	    this.setVisible(true);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
			jLabel.setText(msg);
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
