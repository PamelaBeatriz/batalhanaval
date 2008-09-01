package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.PictureTabuleiro;
import logica.TabuleiroLogico;
import util.MP3;

public class TabuleiroDoInimigo extends JPanel {

	private static final long serialVersionUID = 1L;

	private TabuleiroLogico tabuleiroLogico = null; // @jve:decl-index=0:

	private ArrayList<PictureTabuleiro> picturesList = null;

	private AdaptadorDoMouse adaptadorDoMouse = null; // @jve:decl-index=0:

	private AdptadorDoMouseMovimento adptadorDoMouseMovimento = null; // @jve:decl-index=0:

	private Point posicaoDoCursor = null; // @jve:decl-index=0:

	protected boolean turn;

	private Image imagemDoCursor = null;

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
		this.tabuleiroLogico = new TabuleiroLogico();
		this.turn = false;
	}

	/**
	 * Pinta a tela
	 *
	 * @param Graphics
	 */
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		BufferedImage imagem = null;
		try {
			imagem = ImageIO.read(new File("src/images/tabuleiroEnemy.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setSize(imagem.getWidth(null), imagem.getHeight(null));

		setVisible(true);
		g2.drawImage(imagem, 0, 0, null);

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

		if (this.turn) {
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

		int checkJogada = this.getCheckJogada(posicaoX, posicaoY);
		Point point = this.corrigirPoint(posicaoX, posicaoY);

		/*
		 * Acertou a Agua => Errou mamao
		 */
		if (checkJogada == TabuleiroLogico.ACERTOU_NA_AGUA) {
			this.tabuleiroLogico.setPosicaoTabuleiro(posicaoX / 25,
					posicaoY / 25, "A");
			this.picturesList.add(new PictureTabuleiro(new ImageIcon(
					PainelControle.DIRETORIO_IMAGES + "splash.gif").getImage(),
					point));

			MP3.play(MP3.WATER);
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
			MP3.play(MP3.EXPLOSAO);
			repaint();
			return;
		}

		/*
		 * Acertou posicao ja usada => eh Mamao mesmo
		 */
		else if (checkJogada == TabuleiroLogico.ACERTOU_POSICAO_USADA) {
			JOptionPane.showMessageDialog(null, "Esta posição já foi clicada!",
					"Posição já escolhida", JOptionPane.WARNING_MESSAGE);
		}
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
