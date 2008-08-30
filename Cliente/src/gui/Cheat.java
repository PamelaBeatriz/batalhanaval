package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import logica.PictureTabuleiro;

public class Cheat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel salcifufuPanel = null;
	private PictureTabuleiro pictureTabuleiro = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public Cheat() {
		super();
	}

	public void inicializar() {
		this.initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(383, 286);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setVisible(true);
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
			jContentPane.add(getSalcifufuPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes salcifufuPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getSalcifufuPanel() {
		if (salcifufuPanel == null) {
			salcifufuPanel = new JPanel() {

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(pictureTabuleiro.getImagem(), 0, 0, this);

				}
			};
			salcifufuPanel.setLayout(null);
			salcifufuPanel.setBounds(new Rectangle(6, 8, 351, 231));
			salcifufuPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Salcifufu", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return salcifufuPanel;

	}

	public PictureTabuleiro getPictureTabuleiro() {
		return pictureTabuleiro;
	}

	public void setPictureTabuleiro(PictureTabuleiro pictureTabuleiro) {
		this.pictureTabuleiro = pictureTabuleiro;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
