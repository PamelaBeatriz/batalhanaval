package logica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextArea;

public class ChatListenner {

	private Socket conexao = null;

	private Vector<Socket> clients;

	private int c1;

	private int c2;

	private JTextArea logTextArea;

	private ObjectOutputStream cout2 = null;

	private ObjectInputStream cout1 = null;

	private Vector<String> cListing;

	private String nick = null;

	public ChatListenner(Vector<Socket> clients, int c1, int c2, Vector<String> cListing, JTextArea logTextArea) {
    	try {
        	BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        	String alive;

    		while((alive = entrada.readLine()) != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
