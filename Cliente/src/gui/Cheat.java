package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logica.PictureTabuleiro;

public class Cheat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel salcifufuPanel = null;
	private Vector<PictureTabuleiro> pictureTabuleiro = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public Cheat(Vector<PictureTabuleiro> pictureTabuleiro) {
		super();
		this.pictureTabuleiro = pictureTabuleiro;
		this.initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(280, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("Batalha Naval - Cheat");
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
					Graphics2D g2 = (Graphics2D) g;

					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new File(
								"src/images/tabuleiro.gif"));
					} catch (IOException e) {
						e.printStackTrace();
					}

					setSize(imagem.getWidth(null), imagem.getHeight(null));

					setVisible(true);
					g2.drawImage(imagem, 0, 0, null);

					// Desenha as imagens
					if (!pictureTabuleiro.isEmpty()) {
						for (int i = 0; i < pictureTabuleiro.size(); i++) {

							if (pictureTabuleiro.get(i) != null) {

								g2.drawImage(pictureTabuleiro.get(i)
										.getImagem(), pictureTabuleiro.get(i)
										.getPointBegin().x, pictureTabuleiro
										.get(i).getPointBegin().y, this);
							}
						}
					}
				}
			};
			salcifufuPanel.setLayout(null);
			salcifufuPanel.setBounds(new Rectangle(6, 8, 351, 231));
			salcifufuPanel.setSize(new Dimension(250, 250));
			salcifufuPanel.setBorder(BorderFactory.createLineBorder(new Color(
					0, 100, 90), 1));
		}
		return salcifufuPanel;

	}

} // @jve:decl-index=0:visual-constraint="10,10"
