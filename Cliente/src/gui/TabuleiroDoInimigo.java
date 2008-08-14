package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import logica.PictureTabuleiro;
import logica.TabuleiroLogico;

public class TabuleiroDoInimigo extends JPanel {

	private static final long serialVersionUID = 1L;

	private TabuleiroLogico tabuleiroLogico = null; // @jve:decl-index=0:

	private ArrayList<PictureTabuleiro> picturesList = null;

	private AdaptadorDoMouse adaptadorDoMouse = null; // @jve:decl-index=0:

	private AdptadorDoMouseMovimento adptadorDoMouseMovimento = null; // @jve:decl-index=0:

	private Point posicaoDoCursor = null; // @jve:decl-index=0:

	protected boolean turn;

	/**
	 * This is the default constructor
	 */
	public TabuleiroDoInimigo() {
		super();
		initialize();
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
		this.picturesList = new ArrayList<PictureTabuleiro>();
		this.adaptadorDoMouse = new AdaptadorDoMouse();
		this.adptadorDoMouseMovimento = new AdptadorDoMouseMovimento();
		this.setEnabled(false);
		this.setTabuleiroLogico(null);
		this.turn = false;
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

		if (this.posicaoDoCursor == null || this.posicaoDoCursor.x == -1) {
			return;
		}

		// Percorre o array desenhando as imagens armazenadas no ArrayList
		for (PictureTabuleiro i : this.picturesList) {
			g.drawImage(i.getImagem(), i.getPointBegin().x,
					i.getPointBegin().y, this);
		}

		Point p = this.corrigirPoint(this.posicaoDoCursor.x,
				this.posicaoDoCursor.y);

		/*
		 * if (imagemCursorPatada != null) g.drawImage(imagemCursorPatada, p.x,
		 * p.y, this); else if (this.vez) g.drawImage(imagemCursor, p.x, p.y,
		 * this); else { g.drawImage(null, p.x, p.y, this);
		 */

	}

	/**
	 * Retorna um ponto "corrido" para desenhar o navio na tela corretamente
	 */
	private Point corrigirPoint(int x, int y) {

		int pointXcorrigido = (int) (x / 25);
		int pointYcorrigido = (int) (y / 25);

		return new Point(pointXcorrigido * 25, pointYcorrigido * 25);
	}

	private class AdaptadorDoMouse extends MouseAdapter {

	}

	private class AdptadorDoMouseMovimento extends MouseMotionAdapter {

	}

	public TabuleiroLogico getTabuleiroLogico() {
		return tabuleiroLogico;
	}

	public void setTabuleiroLogico(TabuleiroLogico tabuleiroLogico) {
		this.tabuleiroLogico = tabuleiroLogico;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

}
