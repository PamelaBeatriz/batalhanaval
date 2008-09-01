package util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class FileLog {

	private FileOutputStream fileOutputStream;
	private File file;
	private PrintWriter pw;
	private int numeroPacotes;

	public FileLog(String path) {
		this.numeroPacotes = 0;
		file = new File(path);
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Erro na Abertura do Arquivo");
		}
		try {
			pw = new PrintWriter (new FileOutputStream(path,false),false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.fileOutputStream.close();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro no Fechamento do Arquivo");
		}
	}

	public void write(String s) {
		try {
			pw.print("Pacote "
					+ String.valueOf(numeroPacotes) + " : " + s.trim());
			pw.println();
			this.numeroPacotes++;
		} catch (Exception e) {
			System.out.println("Erro no Escrita do Arquivo");
			e.printStackTrace();
		}
	}

}
