package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextArea;

public class ThreadCliente extends Thread {

	private Socket conexao = null;

	private Vector<Socket> clients;

	private int index;

	private int c2;

	private JList clientList;

	private JTextArea logTextArea;

	private ObjectOutputStream output = null;

	private ObjectInputStream input = null;

	private Vector<String> cListing;

	private Vector<ThreadCliente> tc;

	private String nick = null;

	private String packet = null;

	private boolean isGameRunning = false;

	private Vector<Integer> cliCount = null;

	@SuppressWarnings("unchecked")
	public ThreadCliente(Vector<Socket> clients, Vector<ThreadCliente> tc,
			Vector<Integer> cliCount, int index, Vector<String> cListing,
			JList clientList, JTextArea logTextArea) {
		this.clients = clients;
		this.tc = tc;
		this.cliCount = cliCount;
		Integer temp = this.cliCount.firstElement();
		this.cliCount.remove(0);
		this.cliCount.add(++temp);
		this.cListing = cListing;
		this.index = index;
		this.conexao = clients.get(index);
		this.clientList = clientList;
		this.logTextArea = logTextArea;
		try {
			input = new ObjectInputStream(conexao.getInputStream());
			packet = (String) input.readObject();
			if (packet.substring(0, 2).equals("NK")) {
				this.nick = packet.substring(2) + " ["
						+ this.conexao.getInetAddress().getHostAddress() + "]";
				this.cListing.add(this.nick);
			}
			logTextArea.append("\n" + " ["
					+ new SimpleDateFormat("HH:mm:ss").format(new Date())
					+ "] Novo Cliente conectado: " + this.nick + " ["
					+ this.conexao.getInetAddress().getHostName() + "]");
			Vector<String> TcListing = (Vector<String>) this.cListing.clone();
			for (int i = 0; i < TcListing.size(); i++) {
				if (TcListing.remove(null))
					i--;
			}
			this.clientList.setListData(TcListing);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.start();
	}

	@Override
	protected void finalize() {
		try {
			this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			input = new ObjectInputStream(conexao.getInputStream());
			while ((packet = (String) input.readObject()) != null) {
				if (this.isGameRunning) {

					/*
					 * Pacote CH: se o pacote for do chat
					 */
					if (packet.substring(0, 2).equals("CH")) {
						new DataOutput(clients.elementAt(this.c2))
								.SendPacket(new String("CH"
										+ this.cListing.get(this.index)
										+ " diz: " + packet.substring(2)));
						this.logTextArea.append("\n"
								+ " ["
								+ new SimpleDateFormat("HH:mm:ss")
										.format(new Date()) + "] "
								+ this.cListing.get(this.index) + " diz para "
								+ this.cListing.get(this.c2) + ": "
								+ packet.substring(2));
					}

					/*
					 * Pacote FU: Salcifufu
					 */

					else if (packet.substring(0, 2).equals("FU")) {
						new DataOutput(clients.elementAt(this.c2))
								.SendPacket(packet);
					}

					/*
					 * Pacote NG: New Game
					 */
					else if (packet.substring(0, 2).equals("NG")) {
						new DataOutput(clients.elementAt(this.c2))
								.SendPacket(packet);
						new DataOutput(clients.elementAt(index))
								.SendPacket(packet);
					}

					/*
					 * Pacote EN: corrigindo bug do fugiu depois q perdeu
					 */
					else if (packet.substring(0, 2).equals("EN")) {
						this.isGameRunning = false;
					}

					/*
					 * Pacotes se for do jogo
					 */
					else {

						/*
						 * Pacote PE: Perdeu
						 */
						if (packet.substring(0, 2).equals("PE")) {
							this.isGameRunning = false;
							this.logTextArea.append("\n"
									+ " ["
									+ new SimpleDateFormat("HH:mm:ss")
											.format(new Date()) + "] "
									+ this.cListing.get(this.index)
									+ " ganhou a batalha");
						}

						/*
						 * Pacote TI: Informacoes do Tabuleiro do Inimigo
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
							int posicaoX = Integer.parseInt(temp.substring(0,
									temp.length() - 1));
							// recebe a coordenada y
							temp = "";
							do {
								caracter = mensagem.charAt(posicao++);
								temp += caracter;
							} while (caracter != ',');
							int posicaoY = Integer.parseInt(temp.substring(0,
									temp.length() - 1));
							temp = "";
							do {
								caracter = mensagem.charAt(posicao++);
								temp += caracter;
							} while (posicao < mensagem.length());
							int result = Integer.parseInt(temp);

							String pX = null;
							switch ((int) posicaoX / 25) {
							case 0:
								pX = "A";
								break;
							case 1:
								pX = "B";
								break;
							case 2:
								pX = "C";
								break;
							case 3:
								pX = "D";
								break;
							case 4:
								pX = "E";
								break;
							case 5:
								pX = "F";
								break;
							case 6:
								pX = "G";
								break;
							case 7:
								pX = "H";
								break;
							case 8:
								pX = "I";
								break;
							case 9:
								pX = "J";
								break;
							default:
								break;
							}

							String pY = null;
							switch ((int) posicaoY / 25) {
							case 0:
								pY = "1";
								break;
							case 1:
								pY = "2";
								break;
							case 2:
								pY = "3";
								break;
							case 3:
								pY = "4";
								break;
							case 4:
								pY = "5";
								break;
							case 5:
								pY = "6";
								break;
							case 6:
								pY = "7";
								break;
							case 7:
								pY = "8";
								break;
							case 8:
								pY = "9";
								break;
							case 9:
								pY = "10";
								break;
							default:
								break;
							}

							this.logTextArea.append("\n"
									+ " ["
									+ new SimpleDateFormat("HH:mm:ss")
											.format(new Date()) + "] "
									+ this.cListing.get(this.index)
									+ " atirou na posição " + pX + pY
									+ " e acertou ");
							if (result == 0) {
								this.logTextArea.append("na água");
							} else {
								this.logTextArea.append("uma embarcação");
							}

						}

						/*
						 * Outros tipos de pacotes
						 */
						new DataOutput(this.clients.elementAt(this.c2))
								.SendPacket(this.packet);
					}
				}
				input = new ObjectInputStream(conexao.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isGameRunning && (clients.elementAt(this.c2)) != null) {
			new DataOutput(clients.elementAt(this.c2)).SendPacket(new String(
					"EO" + "Parabéns! " + this.cListing.get(this.index)
							+ " fugiu da batalha, você é o vencedor!"));
			this.logTextArea.append("\n" + " ["
					+ new SimpleDateFormat("HH:mm:ss").format(new Date())
					+ "] " + this.cListing.get(this.c2) + " vence a batalha ("
					+ this.cListing.get(this.index) + " abandonou)");
		}
		try {
			this.logTextArea.append("\n ["
					+ new SimpleDateFormat("HH:mm:ss").format(new Date())
					+ "] " + this.nick + " desconectou");
			// this.clients.setElementAt(null, this.index);
			this.clients.setElementAt(null, this.index);
			// this.cListing.setElementAt(null,this.index);
			this.cListing.setElementAt(null, this.index);

			Vector<String> TcListing = (Vector<String>) this.cListing.clone();
			for (int i = 0; i < TcListing.size(); i++) {
				if (TcListing.remove(null))
					i--;
			}
			this.clientList.setListData(TcListing);
			this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tc.setElementAt(null, this.index);
		Integer temp = this.cliCount.firstElement();
		this.cliCount.remove(0);
		this.cliCount.add(--temp);
	}

	public Socket getConexao() {
		return conexao;
	}

	public void setConexao(Socket conexao) {
		this.conexao = conexao;
	}

	public ObjectInputStream getImput() {
		return this.input;
	}

	public void startGame(int c2) {
		this.c2 = c2;
		this.isGameRunning = true;
		new DataOutput(clients.elementAt(this.index)).SendPacket(new String(
				">>"));
	}

	public int getIndex() {
		return this.index;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

}
