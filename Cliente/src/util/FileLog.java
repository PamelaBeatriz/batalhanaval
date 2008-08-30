package util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileLog {

	private FileOutputStream fileOutputStream;
	private DataOutputStream dataOutputStream;
	private File file;
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
		dataOutputStream = new DataOutputStream(fileOutputStream);
	}

	public void close() {
		try {
			this.fileOutputStream.close();
			this.dataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro no Fechamento do Arquivo");
		}
	}

	public void write(String s) {
		try {
			dataOutputStream.writeUTF("Pacote "
					+ String.valueOf(numeroPacotes) + " : " + s.trim());
			dataOutputStream.writeUTF("\n");
			this.numeroPacotes++;
		} catch (IOException e) {
			System.out.println("Erro no Escrita do Arquivo");
			e.printStackTrace();
		}
	}

}
