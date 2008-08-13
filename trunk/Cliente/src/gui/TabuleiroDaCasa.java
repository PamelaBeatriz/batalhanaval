package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import logica.PictureTabuleiro;
import logica.TabuleiroLogico;

public class TabuleiroDaCasa extends JPanel {

	private static final long serialVersionUID = 1L;
	private TabuleiroLogico tabuleiroLogico = null;  //  @jve:decl-index=0:

	private ArrayList<PictureTabuleiro> picturesList = null;

	private PainelControle painelControle = null;

	private MouseHandler mouseHandler = null;

	private MouseMotionHandler mouseMotionHandler = null;

	private Vector<PictureTabuleiro> pictureTabuleiro = null;

	private Point posicaoDoCursor = null;  //  @jve:decl-index=0:

	private boolean handlerdesligado;

	private String nickName;  //  @jve:decl-index=0:

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public TabuleiroDaCasa(String nickName) {
		super();
		this.nickName = nickName;
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
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 90)));

		this.setBorder(BorderFactory.createTitledBorder(null,
				this.nickName, TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, new Font("Dialog",
						Font.BOLD, 12), new Color(51, 51, 51)));


		this.tabuleiroLogico = new TabuleiroLogico();
		this.picturesList = new ArrayList<PictureTabuleiro>();
		this.pictureTabuleiro = new Vector<PictureTabuleiro>(5);
		this.handlerdesligado = false;
		this.mouseHandler = new MouseHandler(this);
		this.mouseMotionHandler = new MouseMotionHandler(this);
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
	}

}
