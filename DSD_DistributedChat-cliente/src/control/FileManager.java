package control;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FileManager extends Thread {

	private Socket socket;
	private String path;

	public FileManager(Socket socket, String path) {
		this.socket = socket;
		this.path = path;
	}

	public void run() {
		try {
			
			byte[] mybytearray = new byte[6022386];
			InputStream input = socket.getInputStream();
			FileOutputStream output = new FileOutputStream(path);
			BufferedOutputStream bos = new BufferedOutputStream(output);
			int bytesRead = input.read(mybytearray,0,mybytearray.length);
			int current = bytesRead;
			
			while (bytesRead > -1) {
				input.read(mybytearray, current, (mybytearray.length-current));
		         if(bytesRead >= 0) current += bytesRead;
			}
			bos.write(mybytearray, 0 , current);
		      bos.flush();

		} catch (Exception e) {
			System.err.println("ERRO ENVIO ARQUIVO " + e);
		}
	}

}
