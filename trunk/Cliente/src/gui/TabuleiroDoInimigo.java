package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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

	private Image imagemDoCursor = null;

	private Image imagemCursorProibido = null; // @jve:decl-index=0:

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

		this.imagemDoCursor = new ImageIcon(PainelControle.DIRETORIO_IMAGES
				+ "cursor.gif").getImage();
		this.setEnabled(false);
		this.setTabuleiroLogico(null);
		this.turn = false;
	}

	public void resetaCursorProibido() {
		this.imagemCursorProibido = null;
	}

	public void setImagemCursorProibido() {
		this.imagemCursorProibido = new ImageIcon(
				PainelControle.DIRETORIO_IMAGES + "cursorProibido.gif")
				.getImage();
	}

	/**
	 * Pinta a tela
	 *
	 * @param Graphics
	 */
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

		if (this.imagemCursorProibido != null) {
			g.drawImage(this.imagemCursorProibido, p.x, p.y, this);
		} else if (this.turn) {
			g.drawImage(this.imagemDoCursor, p.x, p.y, this);
		} else {
			g.drawImage(null, p.x, p.y, this);
		}

	}

	/**
	 * Retorna um ponto "corrido" para desenhar o navio na tela corretamente
	 */
	private Point corrigirPoint(int x, int y) {

		int pointXcorrigido = (int) (x / 25);
		int pointYcorrigido = (int) (y / 25);

		return new Point(pointXcorrigido * 25, pointYcorrigido * 25);
	}

	/**
	 *
	 * @author Thiago A. L. Genez
	 *
	 */
	public class AdaptadorDoMouse extends MouseAdapter {
		public void mousePressed(MouseEvent me) {

			if (turn) {
				configuraJogada(me.getX(), me.getY());
			}
		}
	}

	/**
	 *
	 * @author Thiago A. L. Genez
	 *
	 */
	private class AdptadorDoMouseMovimento extends MouseMotionAdapter {

		public void mouseMoved(MouseEvent me) {

			if (!TabuleiroDoInimigo.this.isEnabled()) {
				return;
			}
			posicaoDoCursor.setLocation(me.getPoint());
			repaint();
		}
	}

	/**
	 *
	 * Configura a jogada no tabuleiro, pois sera reenviada ao adversario para q
	 * o jogo continue rolando
	 *
	 * @param posicaoX
	 * @param posicaoY
	 */
	private void configuraJogada(int posicaoX, int posicaoY) {

		if (this.imagemCursorProibido != null) {

			Point point = this.corrigirPoint(posicaoX, posicaoY);

			int posicaoXFinal = (point.x + 100);
			int posicaoYFinal = (point.y + 100);

			if (posicaoXFinal > 250) {
				posicaoXFinal = 250;
			}

			if (posicaoYFinal > 250) {
				posicaoYFinal = 250;
			}

			for (int i = point.x / 25; i < posicaoXFinal / 25; i++) {
				for (int j = point.y / 25; j < posicaoYFinal / 25; j++) {

					System.out.println("\nCoordenada X: " + i
							+ " Coordenada Y: " + j);

					switch (this.getCheckJogada(i * 25, j * 25)) {

					case TabuleiroLogico.ACERTOU_NA_AGUA:
						this.tabuleiroLogico.setPosicaoTabuleiro(i, j, "A");
						this.picturesList.add(new PictureTabuleiro(
								new ImageIcon(PainelControle.DIRETORIO_IMAGES
										+ "splash.gif").getImage(), new Point(
										i * 25, j * 25)));
						break;
					case TabuleiroLogico.ACERTOU_NO_NAVIO:
						this.tabuleiroLogico.setPosicaoTabuleiro(i, j, "N");
						this.picturesList.add(new PictureTabuleiro(
								new ImageIcon(PainelControle.DIRETORIO_IMAGES
										+ "explodido.gif").getImage(),
								new Point(i * 25, j * 25)));
						break;
					}
				}
			}

			// Som.playAudio(Som.PATADA);
			this.imagemCursorProibido = null; // gastou a jogada
			this.turn = false;

		} else {
			int checkJogada = this.getCheckJogada(posicaoX, posicaoY);
			Point point = this.corrigirPoint(posicaoX, posicaoY);

			/*
			 * Acertou a Agua => Errou mamao
			 */
			if (checkJogada == TabuleiroLogico.ACERTOU_NA_AGUA) {
				this.tabuleiroLogico.setPosicaoTabuleiro(posicaoX / 25,
						posicaoY / 25, "A");
				this.picturesList.add(new PictureTabuleiro(new ImageIcon(
						PainelControle.DIRETORIO_IMAGES + "splash.gif")
						.getImage(), point));

				// Som.playAudio(Som.ERRO);
				repaint();
				this.turn = false;
				return;

			}

			/*
			 * Acertou o navio => Mamao esperto
			 */
			else if (checkJogada == TabuleiroLogico.ACERTOU_NO_NAVIO) {

				this.tabuleiroLogico.setPosicaoTabuleiro(posicaoX / 25,
						posicaoY / 25, "N");
				this.picturesList.add(new PictureTabuleiro(new ImageIcon(
						PainelControle.DIRETORIO_IMAGES + "explodido.gif")
						.getImage(), point));
				// Som.playAudio(Som.ACERTO);
				repaint();
				return;
			}

			/*
			 * Acertou posicao ja usada => eh Mamao mesmo
			 */
			else if (checkJogada == TabuleiroLogico.ACERTOU_POSICAO_USADA) {
				JOptionPane.showMessageDialog(null,
						"Esta posição já foi clicada!", "Posição já escolhida",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 *
	 * @param posicaoX
	 * @param posicaoY
	 * @param largura
	 * @param altura
	 * @return int[][]
	 */
	public int[][] getJogadaAera(int posicaoX, int posicaoY, int largura,
			int altura) {

		Point point = this.corrigirPoint(posicaoX, posicaoY);

		int posicaoXFinal = (point.x + largura);
		int posicaoYFinal = (point.y + altura);

		if (posicaoXFinal > 250) {
			posicaoXFinal = 250;
		}

		if (posicaoYFinal > 250) {
			posicaoYFinal = 250;
		}

		int[][] resultado = new int[10][10];

		for (int i = 0; i < 100; i++) {
			resultado[i / 10][i % 10] = -1;
		}

		for (int i = point.x / 25; i < posicaoXFinal / 25; i++) {
			for (int j = point.y / 25; j < posicaoYFinal / 25; j++) {

				switch (this.getCheckJogada(i * 25, j * 25)) {
				case TabuleiroLogico.ACERTOU_NA_AGUA:
					resultado[i][j] = TabuleiroLogico.ACERTOU_NA_AGUA;
					break;
				case TabuleiroLogico.ACERTOU_NO_NAVIO:
					resultado[i][j] = TabuleiroLogico.ACERTOU_NO_NAVIO;
					break;
				}
			}
		}

		return resultado;
	}

	/**
	 * Check a Jogada
	 *
	 * @param x
	 * @param y
	 * @return int
	 */
	public int getCheckJogada(int x, int y) {

		int jogadaX = (int) x / 25;
		int jogadaY = (int) y / 25;

		/*
		 * se acertou na agua
		 */
		if (this.tabuleiroLogico.getPosicaoTabuleiro(jogadaX, jogadaY)
				.equalsIgnoreCase("water")) {
			return TabuleiroLogico.ACERTOU_NA_AGUA;
		}

		/*
		 * se acertou em alguma posicao ja acertada
		 */
		else if (this.tabuleiroLogico.getPosicaoTabuleiro(jogadaX, jogadaY)
				.equalsIgnoreCase("A")
				|| this.tabuleiroLogico.getPosicaoTabuleiro(jogadaX, jogadaY)
						.equalsIgnoreCase("N")) {

			return TabuleiroLogico.ACERTOU_POSICAO_USADA;
		}

		/*
		 * se acertou na agua
		 */
		return TabuleiroLogico.ACERTOU_NO_NAVIO;
	}

	/**
	 * Metodo para retirar os Eventos do mouse, depois que o tabuleiro estiver
	 * Pronto
	 */
	public void turnOFFHandlers() {
		setEnabled(false);
		removeMouseListener(this.adaptadorDoMouse);
		removeMouseMotionListener(this.adptadorDoMouseMovimento);
		this.posicaoDoCursor = new Point(-1, -1);
		repaint();

	}

	/**
	 * Metodo para ativar os Eventos do Mouse
	 */
	public void turnONHandlers() {

		this.setEnabled(true);
		this.addMouseListener(this.adaptadorDoMouse);
		this.addMouseMotionListener(this.adptadorDoMouseMovimento);
		this.posicaoDoCursor = new Point(-1, -1);
		repaint();
	}

	/**
	 * Get o tabuleiro logico do jogo
	 *
	 * @return TabuleiroLogico
	 */
	public TabuleiroLogico getTabuleiroLogico() {
		return tabuleiroLogico;
	}

	/**
	 * Seta a matriz Logica, que vira pela REDE!
	 *
	 * @param tabuleiroLogico
	 */
	public void setTabuleiroLogico(TabuleiroLogico tabuleiroLogico) {
		this.tabuleiroLogico = tabuleiroLogico;
	}

	/**
	 * Metodo para Limpar o tabuleiro e derivados
	 */
	public void clearPicture() {
		this.tabuleiroLogico.clearTabuleiro();
		this.picturesList.clear();
		this.turnOFFHandlers();
		repaint();
	}

	/**
	 * Get Tabuleiro do Inimigo
	 *
	 * @return TabuleiroDoInimigo
	 */
	public TabuleiroDoInimigo getTabuleiroDoInimigo() {
		return this;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

}
