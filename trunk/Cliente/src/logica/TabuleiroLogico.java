package logica;

public class TabuleiroLogico extends Thread {

	private String[][] tabuleiro = null;

	private String nameShip = null;

	private int larguraShip = -1;

	private int alturaShip = -1;

	private int contadorMovimentosAteErrar = 0;

	public static final int ACERTOU_NA_AGUA = 0;

	public static final int ACERTOU_NO_NAVIO = 1;

	public static final int ACERTOU_POSICAO_USADA = 2;

	private int naviosDestruidos = 0;

	public TabuleiroLogico() {
		this.tabuleiro = new String[10][10];
		this.inicialize();

	}

	private void inicialize() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				this.tabuleiro[i][j] = "water";
			}
		}
	}

	/**
	 * Verifica se a posição de clique do mouse pode conter o navio selecionado
	 *
	 * @param nomeDoNavio
	 *            nome do navio a ser posicionado larguraNavio largura do navio
	 *            a ser posicionado alturaNavio altura do navio a ser
	 *            posicionado x posição x a ser colocado o navio y posição y a
	 *            ser colocado o navio
	 * @param verticalShip
	 */
	public boolean validaPosicaoInsercao(String nomeDoNavio, int larguraNavio,
			int alturaNavio, int x, int y, boolean verticalShip) {

		if (verticalShip) {

			int xInicialMatriz = (int) (x / 25);
			int yInicialMatriz = (int) (y / 25);
			int yFinalMatriz = (int) ((y + alturaNavio) / 25);

			if (yInicialMatriz + (yFinalMatriz - yInicialMatriz) - 1 > 9)
				return false;

			for (int i = yInicialMatriz; i < yFinalMatriz; i++) {
				if (!this.tabuleiro[xInicialMatriz][i]
						.equalsIgnoreCase("water"))
					return false;
			}

			for (int i = yInicialMatriz; i < yFinalMatriz; i++) {
				this.tabuleiro[xInicialMatriz][i] = nomeDoNavio;
			}

			return true;
		}

		int xInicialMatriz = (int) (x / 25);
		int yInicialMatriz = (int) (y / 25);
		int xFinalMatriz = (int) ((x + larguraNavio) / 25);

		if (xInicialMatriz + (xFinalMatriz - xInicialMatriz) - 1 > 9)
			return false;

		for (int i = xInicialMatriz; i < xFinalMatriz; i++) {
			if (!this.tabuleiro[i][yInicialMatriz].equalsIgnoreCase("water"))
				return false;
		}

		for (int i = xInicialMatriz; i < xFinalMatriz; i++) {
			this.tabuleiro[i][yInicialMatriz] = nomeDoNavio;
		}
		return true;
	}

	public void clearTabuleiro() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				this.tabuleiro[i][j] = "water";
			}
		}
	}

	/**
	 * Seta uma posicao to tabuleiro
	 *
	 * @param posicaoX
	 * @param posicaoY
	 * @param elemento
	 */
	public void setPosicaoTabuleiro(int posicaoX, int posicaoY, String elemento) {
		this.tabuleiro[posicaoX][posicaoY] = elemento;
	}

	/**
	 * Obtem uma posicao do tabuleiro
	 *
	 * @param posicaoX
	 * @param posicaoY
	 * @return String
	 */
	public String getPosicaoTabuleiro(int posicaoX, int posicaoY) {
		return this.tabuleiro[posicaoX][posicaoY];
	}

	/**
	 * Incrementa o numero de navios destruidos
	 */
	public void navioDestruidosPlusPlus() {
		this.naviosDestruidos++;
	}

	public void resetNaviosDestruidos(){
		this.naviosDestruidos = 0;
	}

	public int getNaviosDestruidos() {
		return naviosDestruidos;
	}

	public void setNaviosDestruidos(int naviosDestruidos) {
		this.naviosDestruidos = naviosDestruidos;
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
