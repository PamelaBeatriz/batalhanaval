package logica;

import gui.TelaJogo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Cliente extends Thread {

	private String nick = "";

	private Socket socket = null;

	private String porta = "";

	private String ipServer = "";

	private String senhaCriptografada = "";

	private JTextArea chatTextArea;

	private ObjectInputStream input = null;

	private String packet = null;

	private TelaJogo telaJogo = null;

	private TabuleiroDoInimigoListener tabuleiroDoInimigoListener = null;

	private PainelControleListener painelControleListener = null;

	/**
	 * This is the default constructor
	 */
	public Cliente() {
		this.nick = "";
		this.porta = "";
		this.ipServer = "";
		this.socket = null;
		this.senhaCriptografada = "";
		this.tabuleiroDoInimigoListener = new TabuleiroDoInimigoListener();
		this.painelControleListener = new PainelControleListener();
	}

	/**
	 * Tenta a conexao com o servidor
	 *
	 * @return True se o cliente esta conectado
	 * @throws NumberFormatException
	 * @throws UnknownHostException
	 * @throws ConnectException
	 * @throws IOException
	 */
	public boolean tentarConexaoServer() throws NumberFormatException,
			UnknownHostException, ConnectException, IOException {
		this.socket = new Socket(this.ipServer, Integer.parseInt(this.porta));
		return this.socket.isConnected();
	}

	/**
	 * Seta os atributos do cliente
	 *
	 * @param nick
	 * @param ipServer
	 * @param porta
	 * @param senhaCriptografada
	 */
	public void setCliente(String nick, String ipServer, String porta,
			String senhaCriptografada) {
		this.nick = nick;
		this.ipServer = ipServer;
		this.porta = porta;
		this.senhaCriptografada = senhaCriptografada;
	}

	@Override
	public void run() {

		this.telaJogo.getTabuleiroDoInimigo().addMouseListener(
				this.tabuleiroDoInimigoListener);
		this.telaJogo.getPainelControle().getOkGost().addActionListener(
				this.painelControleListener);
		try {
			input = new ObjectInputStream(this.socket.getInputStream());
			while ((packet = (String) input.readObject()) != null) {

				/*
				 * verifica se o pacote eh do chat
				 */
				if (packet.substring(0, 2).equals("CH")) {
					this.chatTextArea.append("["
							+ new SimpleDateFormat("HH:mm:ss")
									.format(new Date()) + "] "
							+ packet.substring(2) + "\n");
				}

				/*
				 * verifica se o pacote eh do jogo, vem da classe
				 * TabuleiroDoInimigoListener, ou seja, vem so inimigo para
				 * configurar o tabuleiro da casa, dependendo da jogada do
				 * inimigo
				 *
				 * desenhar a imagem jogada no tabuleiro do jogador
				 *
				 * TI = Tabuleiro Inimigo
				 */

				else if (packet.substring(0, 2).equals("TI")) {
					String temp = "";
					String mensagem = packet.substring(2);
					char caracter;
					int posicao = 0;
					// recebe a coordenada x
					do {
						caracter = mensagem.charAt(posicao++);
						temp += caracter;
					} while (caracter != ',');
					int posicaoX = Integer.parseInt(temp.substring(0, temp
							.length() - 1));
					// recebe a coordenada y
					temp = "";
					do {
						caracter = mensagem.charAt(posicao++);
						temp += caracter;
					} while (caracter != ',');
					int posicaoY = Integer.parseInt(temp.substring(0, temp
							.length() - 1));
					telaJogo.getTabuleiroDaCasa().configuraJogada(posicaoX,
							posicaoY);
					/*
					 * if (painel.getMeuTabuleiro().getNaviosDestruidos() == 15)
					 * painel.getPainelCentral().habilitaPatada();
					 */
				}

				/*
				 * O inimigo errou, acertou na agua, e perde a vez. Vez da casa
				 * PJ = Perdeu a Jogada
				 */
				else if (packet.substring(0, 2).equals("PJ")) {
					this.telaJogo.setTurn(true);
					this.telaJogo.getTabuleiroDoInimigo().setTurn(true);
				}

				/*
				 * Jogo Acabou e jogador da casa perdeu
				 *
				 * PE = Perdeu
				 */
				else if (packet.substring(0, 2).equals("PE")) {
					JOptionPane.showMessageDialog(null, "Voce Perdeu!=/",
							"Game Over!", JOptionPane.WARNING_MESSAGE);
				}

				/*
				 * Tabuleiro do adversario esta pronto!configurado para jogar MA =
				 * Matriz
				 */
				else if (packet.substring(0, 2).equals("MA")) {

					String[][] tabuleiroLogico = new String[10][10];
					String mensagem = packet.substring(2);
					String temp = "";
					char caracter = ' ';
					int posicao = 0;
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							do {
								caracter = mensagem.charAt(posicao++);
								temp = temp += caracter;
							} while (caracter != ',');
							tabuleiroLogico[i][j] = temp.substring(0, temp
									.length() - 1);
							temp = "";
						}
					}
					telaJogo.getTabuleiroDoInimigo().getTabuleiroLogico()
							.setTabuleiro(tabuleiroLogico);

					if (this.telaJogo.isJogadorDaCasaPronto()) {
						JOptionPane.showMessageDialog(null,
								"VOCÊ COMEÇA JOGANDO", "AVISO",
								JOptionPane.WARNING_MESSAGE);
						this.telaJogo.setTurn(true);
						this.telaJogo.getTabuleiroDoInimigo().setTurn(true);
					}

				}
				packet = null;
				input = new ObjectInputStream(this.socket.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Classe private para dedicar ao MouseListener do tabuleiro do Inimigo
	 *
	 * @author Thiago A. L. Genez
	 *
	 */
	private class TabuleiroDoInimigoListener extends MouseAdapter {
		public void mousePressed(MouseEvent me) {

			if (telaJogo.isTurn()) {
				String packet = new String("");
				packet += "TI";
				packet += me.getX();
				packet += ",";
				packet += me.getY();
				packet += ",";

				new DataOutput(telaJogo.getClient()).SendPacket(packet);

				int resultadoDaJogada = telaJogo.getTabuleiroDoInimigo()
						.getCheckJogada(me.getX(), me.getY());

				/*
				 * acertou na agua, logo perde a vez e avisa q eh a vez do
				 * adversario
				 */
				if (resultadoDaJogada == TabuleiroLogico.ACERTOU_NA_AGUA) {

					new DataOutput(telaJogo.getClient()).SendPacket(new String(
							"PJ"));
					telaJogo.setTurn(false);
				}

				/*
				 * Acertou no navio, mamao esperto!, ++ no placar!
				 */
				else if (resultadoDaJogada == TabuleiroLogico.ACERTOU_NO_NAVIO) {
					telaJogo.numeroAcertosPlusPlus();

					/*
					 * jogo termina quando numero de acertos forem 18
					 */
					if (telaJogo.getNumeroAcertos() == 18) {

						/*
						 * avisa o adversario que perdeu
						 */
						new DataOutput(telaJogo.getClient())
								.SendPacket(new String("PE"));
						JOptionPane.showMessageDialog(null,
								"Congratulations , You Kill your enemy", "Win",
								JOptionPane.INFORMATION_MESSAGE);

						// Som.playAudio(Som.VITORIA);
						// reinicia o jogo
						// EnviaDado("*", "Jogada");
						// ReinciaJogo();
					}
				}
			}
		}
	}

	private class PainelControleListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (/* telaJogo.isConected() */true) {
				telaJogo.setJogadorDaCasaPronto(true);
				String tabuleiroLogico[][] = telaJogo.getTabuleiroLogico()
						.getTabuleiro();
				String packet = "";
				// Habilita o tabuleiro inimigo
				telaJogo.getTabuleiroDoInimigo().turnONHandlers();
				// Toca o som de configuração de navio
				// Som.stopAudio(Som.SOM_CONFIG);

				// transforma a matriz em um String
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						packet = packet + tabuleiroLogico[i][j];
						packet = packet + ",";
					}
				}
				new DataOutput(telaJogo.getClient()).SendPacket("MA"+packet);
			} else {
				JOptionPane.showMessageDialog(null,
						"AGUARDE A CONEXÃO SER ESTABELECIDA E TENTE NOVAMENTE",
						"ERRO DE CONEXÃO", JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public void finalize() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setChatArea(JTextArea chatTextArea) {
		this.chatTextArea = chatTextArea;
	}

	public TelaJogo getTelaJogo() {
		return telaJogo;
	}

	public void setTelaJogo(TelaJogo telaJogo) {
		this.telaJogo = telaJogo;
	}

}
