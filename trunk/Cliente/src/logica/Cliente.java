package logica;

import gui.TelaJogo;

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
		try {
			input = new ObjectInputStream(this.socket.getInputStream());
			while ((packet = (String) input.readObject()) != null) {
				if (packet.substring(0, 2).equals("CH")) {
					this.chatTextArea.append("["
							+ new SimpleDateFormat("HH:mm:ss")
									.format(new Date()) + "] "
							+ packet.substring(2) + "\n");
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
				String packet = new String();
				packet += "DJ";
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
							"DJwater"));
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
								.SendPacket(new String("DJlose"));
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
