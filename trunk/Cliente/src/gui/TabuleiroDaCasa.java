package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.PictureTabuleiro;
import logica.TabuleiroLogico;

public class TabuleiroDaCasa extends JPanel {

	private static final long serialVersionUID = 1L;
	private TabuleiroLogico tabuleiroLogico = null; // @jve:decl-index=0:

	private ArrayList<PictureTabuleiro> picturesList = null;

	private PainelControle painelControle = null;

	private MouseHandler mouseHandler = null;

	private MouseMotionHandler mouseMotionHandler = null; // @jve:decl-index=0:

	private Vector<PictureTabuleiro> pictureTabuleiro = null;

	private Point posicaoDoCursor = null; // @jve:decl-index=0:

	private boolean handlerOFF;

	private String shipName = null;

	private int larguraShip = -1;

	private int alturaShip = -1;

	private int naviosPosicionadosParaJogar = 0;

	public int getNaviosPosicionadosParaJogar() {
		return naviosPosicionadosParaJogar;
	}

	public void setNaviosPosicionadosParaJogar(int naviosPosicionadosParaJogar) {
		this.naviosPosicionadosParaJogar = naviosPosicionadosParaJogar;
	}

	public TabuleiroLogico getTabuleiroLogico() {
		return tabuleiroLogico;
	}

	public void setTabuleiroLogico(TabuleiroLogico tabuleiroLogico) {
		this.tabuleiroLogico = tabuleiroLogico;
	}

	/**
	 * This is the default constructor
	 */
	public TabuleiroDaCasa(PainelControle painelControle) {
		super();
		initialize();
		this.painelControle = painelControle;
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(new Dimension(250, 250));
		this.setLayout(null);
		this
				.setBorder(BorderFactory.createLineBorder(
						new Color(0, 100, 90), 1));

		this.tabuleiroLogico = new TabuleiroLogico();
		this.picturesList = new ArrayList<PictureTabuleiro>();
		this.pictureTabuleiro = new Vector<PictureTabuleiro>(5);
		this.pictureTabuleiro.setSize(5);
		this.handlerOFF = false;
		this.mouseHandler = new MouseHandler();
		this.mouseMotionHandler = new MouseMotionHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseMotionHandler);
		this.posicaoDoCursor = new Point();
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.BLUE, 250.0f,
				250.0f, Color.CYAN);
		g2.setPaint(gp);

		// Desenha o tabuleiro, de acordo com o gradiente
		g2.fillRect(0, 0, 250, 251);

		g2.setColor(new Color(0, 100, 90));

		// Desenha as linhas do tabuleiro
		for (int i = 1; i < 10; i++) {
			g2.drawLine(i * 25, 0, i * 25, 250);
			g2.drawLine(0, i * 25, 250, i * 25);
		}

		// Desenha as imagens
		for (int i = 0; i < pictureTabuleiro.size(); i++) {

			if (pictureTabuleiro.get(i) != null) {

				g2.drawImage(pictureTabuleiro.get(i).getImagem(),
						pictureTabuleiro.get(i).getPointBegin().x,
						pictureTabuleiro.get(i).getPointBegin().y, this);
			}
		}

		for (PictureTabuleiro i : this.picturesList) {
			g.drawImage(i.getImagem(), i.getPointBegin().x,
					i.getPointBegin().y, this);
		}

		if (this.painelControle.imagemLastShip == null) {
			return;
		}

		Point p = normalizaPonto(this.posicaoDoCursor.x, this.posicaoDoCursor.y);

		if (this.painelControle.verticalShip) {
			g2.fill3DRect(p.x, p.y, 25, this.painelControle.larguraLastShip,
					false);
		} else {
			g2.fill3DRect(p.x, p.y, this.painelControle.larguraLastShip, 25,
					false);
		}

	}

	/**
	 * Retorna um ponto normalizado onde deve-se desenhar cada navio
	 */
	private Point normalizaPonto(int x, int y) {

		int xNormalizado = (int) (x / 25);
		int yNormalizado = (int) (y / 25);

		return new Point(xNormalizado * 25, yNormalizado * 25);
	}

	/**
	 * MouseHandler.java
	 *
	 * Criado em 12 de Agosto de 2007, 22:08
	 *
	 * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro,
	 * de forma a saber em qual posição será inserida a imagem
	 */

	private class MouseHandler extends MouseAdapter {

		public void mousePressed(MouseEvent me) {

			// Posiciona uma imagem no tabuleiro
			if (me.getButton() == me.BUTTON1) {

				if (painelControle.lastShipName == null
						&& painelControle.posicaoLastShip == -1) {
					JOptionPane.showMessageDialog(null,
							"SELECIONE UM NAVIO PARA POSICIONAR NO TABULEIRO.",
							"SELECIONE UM NAVIO", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (painelControle != null) {

					if (painelControle.verticalShip) {

						shipName = painelControle.lastShipName + "v";
						larguraShip = 25;
						alturaShip = painelControle.larguraLastShip;

					} else {

						shipName = painelControle.lastShipName;
						alturaShip = 25;
						larguraShip = painelControle.larguraLastShip;
					}

					configuraImagem(me.getX(), me.getY());
					painelControle.verticalShip = false;
				}
				return;
			}

			// Altera o modo em que a imagem será colocada
			if (me.getButton() == me.BUTTON3) {

				painelControle.verticalShip = !painelControle.verticalShip;
				repaint();
			}

		}
	}

	/**
	 * MouseMotionHandler.java
	 *
	 * Criado em 12 de Agosto de 2007, 22:08
	 *
	 * O propósito desta classe é lidar com eventos de mouse sobre o tabuleiro,
	 * de forma a ajustar o ponto atual do cursor e orientar o usuário a saber
	 * se o navio está na vertical ou horizontal.
	 */
	private class MouseMotionHandler extends MouseMotionAdapter {

		public void mouseMoved(MouseEvent me) {

			posicaoDoCursor.setLocation(me.getPoint());
			repaint();
		}

	}

	/**
	 * Configura a imagem no tabuleiro do jogador
	 *
	 * @param x
	 *            coordenada x do evento do mouse y coordenada y do evento do
	 *            mouse
	 */
	private void configuraImagem(int x, int y) {

		boolean checkPosicao = this.tabuleiroLogico.validaPosicaoInsercao(
				this.shipName, this.larguraShip, this.alturaShip, x, y,
				this.painelControle.verticalShip);

		if (checkPosicao) {

			// Atualiza o nome da imagem para "X.v", onde X é o nome do navio
			// solicitado.

			if (painelControle.verticalShip) {

				this.pictureTabuleiro.setElementAt(new PictureTabuleiro(
						new ImageIcon(PainelControle.DIRETORIO_IMAGES
								+ this.shipName + ".gif").getImage(), this
								.normalizaPonto(x, y)),
						this.painelControle.posicaoLastShip);

			} else

				this.pictureTabuleiro.add(this.painelControle.posicaoLastShip,
						new PictureTabuleiro(
								this.painelControle.imagemLastShip, this
										.normalizaPonto(x, y)));

			// Reconfigura a área de navio
			this.painelControle.desativarLastShipSelected();
			this.painelControle.lastShipName = null;
			this.painelControle.larguraLastShip = -1;
			this.painelControle.posicaoLastShip = -1;
			this.painelControle.imagemLastShip = null;

			// Se todos os navios estão configurados, retira-se o listener de
			// evento do tabuleiro.
			if (++this.naviosPosicionadosParaJogar == 5) {

				this.turnOFFHandlers();
				// painelDoJogo.trocarPaineis();
				// areaCentral.habilitaBotaoOk();
				JOptionPane
						.showMessageDialog(
								null,
								"Pecas Posicionadas!!!\n\nPronto para Jogar!",
								"Ao Ataque!",
								JOptionPane.INFORMATION_MESSAGE);
			}
			repaint();
		} else {
			JOptionPane
					.showMessageDialog(
							null,
							"SEU NAVIO NÃO PODE SER COLOCADO NESTA POSIÇÃO. TENTE NOVAMENTE",
							"ERRO DE POSICIONAMENTO DO NAVIO",
							JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Remove o listener de evento do mouse, depois que o tabuleiro já estiver
	 * configurado
	 */
	public void turnOFFHandlers() {

		if (!this.handlerOFF) {
			removeMouseListener(this.mouseHandler);
			removeMouseMotionListener(this.mouseMotionHandler);
			setEnabled(false);
			this.handlerOFF = true;
		}

	}

	/**
	 * Adiciona o listener de evento do mouse, caso seja necessário reconfigurar
	 * o tabuleiro
	 */
	public void ligarHandlers() {

		if (this.handlerOFF) {
			addMouseListener(this.mouseHandler);
			addMouseMotionListener(this.mouseMotionHandler);
			setEnabled(true);
			this.handlerOFF = false;
		}
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public int getLarguraShip() {
		return larguraShip;
	}

	public void setLarguraShip(int larguraShip) {
		this.larguraShip = larguraShip;
	}

	public int getAlturaShip() {
		return alturaShip;
	}

	public void setAlturaShip(int alturaShip) {
		this.alturaShip = alturaShip;
	}

	public boolean isHandlerOFF() {
		return handlerOFF;
	}

	public void setHandlerOFF(boolean handlerOFF) {
		this.handlerOFF = handlerOFF;
	}

}
