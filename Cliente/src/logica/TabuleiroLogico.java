package logica;

public class TabuleiroLogico extends Thread {

	private String[][] tabuleiro = null;

	private String nameShip = null;

	private int larguraShip = -1;

	private int alturaShip = -1;

	private int contadorMovimentosAteErrar = 0;

	private static final int ACERTOU_AGUA = 0;

	private static final int ACERTOU_NAVIO = 1;

	private static final int ACERTOU_POSICAO_USADA = 2;

	private int naviosDestruidos = 0;

	public int getNaviosDestruidos() {
		return naviosDestruidos;
	}

	public void setNaviosDestruidos(int naviosDestruidos) {
		this.naviosDestruidos = naviosDestruidos;
	}

	public TabuleiroLogico() {
		this.inicialize();
	}

	private void inicialize() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				this.tabuleiro[i][j] = "water";
			}
		}
	}

	public String[][] getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(String[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public String getNameShip() {
		return nameShip;
	}

	public void setNameShip(String nameShip) {
		this.nameShip = nameShip;
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

	public int getContadorMovimentosAteErrar() {
		return contadorMovimentosAteErrar;
	}

	public void setContadorMovimentosAteErrar(int contadorMovimentosAteErrar) {
		this.contadorMovimentosAteErrar = contadorMovimentosAteErrar;
	}

}
