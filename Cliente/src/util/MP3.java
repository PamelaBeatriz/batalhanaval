package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MP3 {
	private static Player player;

	public static final String EXPLOSAO = "src\\MP3\\explosao.mp3";

	public static final String SYSTEM_FAILURE_TRIPLE = "src\\MP3\\system_failure_triple.mp3";

	public static final String YELLOW_SUBMARINE = "src\\MP3\\yellowSubmarine.mp3";

	public static final String WIN = "src\\MP3\\win.mp3";

	public static final String LOSE = "src\\MP3\\lose.mp3";

	public static final String WATER = "src\\MP3\\water.mp3";

	public static void close() {
		if (player != null) {
			player.close();
		}
	}

	public static void play(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch (Exception e) {
			System.out.println("Problem playing file " + filename);
			System.out.println(e);
		}

		// run in new thread to play in background
		new Thread() {
			public void run() {
				try {
					player.play();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}.start();

	}
}
