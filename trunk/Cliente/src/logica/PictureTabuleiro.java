package logica;

import java.awt.Image;
import java.awt.Point;

public class PictureTabuleiro {

	private Image imagem = null;

	private Point pointBegin = null;

	public PictureTabuleiro(Image image, Point pointBegin) {
		this.imagem = image;
		this.pointBegin = pointBegin;
	}

	public Image getImagem() {
		return imagem;
	}

	public void setImagem(Image imagem) {
		this.imagem = imagem;
	}

	public Point getPointBegin() {
		return pointBegin;
	}

	public void setPointBegin(Point pointBegin) {
		this.pointBegin = pointBegin;
	}
}
