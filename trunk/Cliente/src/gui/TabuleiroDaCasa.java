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

	private MouseMotionHandler mouseMotionHandler = null;

	private Vector<PictureTabuleiro> pictureTabuleiro = null;

	private Point posicaoDoCursor = null; // @jve:decl-index=0:

	private boolean handlerdesligado;

	public TabuleiroLogico getTabuleiroLogico() {
		return tabuleiroLogico;
	}

	public void setTabuleiroLogico(TabuleiroLogico tabuleiroLogico) {
		this.tabuleiroLogico = tabuleiroLogico;
	}

	/**
	 * This is the default constructor
	 */
	public TabuleiroDaCasa() {
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

		/*
		 * this.setBorder(BorderFactory.createTitledBorder(null, this.nickName,
		 * TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new
		 * Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		 */

		this.tabuleiroLogico = new TabuleiroLogico();
		this.picturesList = new ArrayList<PictureTabuleiro>();
		this.pictureTabuleiro = new Vector<PictureTabuleiro>(5);
		this.handlerdesligado = false;
		this.mouseHandler = new MouseHandler();
		this.mouseMotionHandler = new MouseMotionHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseMotionHandler);
		this.posicaoDoCursor = new Point();
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gp = new GradientPaint(0.0f, 0.0f, Color.CYAN, 250.0f,
				250.0f, Color.WHITE);
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

		// Percorre o array desenhando as imagens armazenadas no ArrayList
		for (PictureTabuleiro i : this.picturesList) {
			g.drawImage(i.getImagem(), i.getPointBegin().x,
					i.getPointBegin().y, this);
		}

		/*
		 * if (areaCentral.imagemUltimoNavio == null) return; Point p =
		 * normalizaPonto(posicaoCursor.x, posicaoCursor.y);
		 *
		 * if (areaCentral.verticalShip) g2.fill3DRect(p.x, p.y, 25,
		 * areaCentral.larguraUltimoNavio, false); else g2.fill3DRect(p.x, p.y,
		 * areaCentral.larguraUltimoNavio, 25, false);
		 */
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

		// public void mousePressed(MouseEvent me){

		/*
		 * //Posiciona uma imagem no tabuleiro if(me.getButton() == me.BUTTON1){
		 *
		 * if(areaCentral.nomeUltimoNavio == null &&
		 * areaCentral.posicaoUltimoNavio == -1){
		 * JOptionPane.showMessageDialog(null,"SELECIONE UM NAVIO PARA
		 * POSICIONAR NO TABULEIRO.", "SELECIONE UM NAVIO",
		 * JOptionPane.INFORMATION_MESSAGE Icone vem aqui.); return; }
		 *
		 * if(areaCentral != null){
		 *
		 * if(areaCentral.verticalShip){
		 *
		 * nomeDoNavio = areaCentral.nomeUltimoNavio+"v"; larguraNavio = 25;
		 * alturaNavio = areaCentral.larguraUltimoNavio;
		 *
		 * }else{
		 *
		 * nomeDoNavio = areaCentral.nomeUltimoNavio; alturaNavio = 25;
		 * larguraNavio = areaCentral.larguraUltimoNavio; }
		 *
		 * configuraImagem(me.getX(),me.getY()); areaCentral.verticalShip =
		 * false; } return; }
		 *
		 * //Altera o modo em que a imagem será colocada if(me.getButton() ==
		 * me.BUTTON3){
		 *
		 * areaCentral.verticalShip = !areaCentral.verticalShip; repaint(); }
		 */

		// }
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

		/*
		 * public void mouseMoved(MouseEvent me) {
		 *
		 * posicaoCursor.setLocation(me.getPoint()); repaint(); }
		 */
	}

}
