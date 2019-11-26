package control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileManager extends Thread {

	private Socket socket;
	private String path;
	private StreamClient streamFile;

	public FileManager(Socket socket, String path) {
		this.socket = socket;
		this.path = path;
	}

	public void run() {
		try {

			while (true) {
//				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				
//				int filesize = 6022386; // filesize temporary hardcoded
//				long start = System.currentTimeMillis();
//				int bytesRead;
//				int current = 0;
//
//				// receive file
//				byte[] mybytearray = new byte[filesize];
//				InputStream is = socket.getInputStream();
//				FileOutputStream fos = new FileOutputStream("pathminhamaquina");
//				BufferedOutputStream bos = new BufferedOutputStream(fos);
//				bytesRead = is.read(mybytearray, 0, mybytearray.length);
//				current = bytesRead;
//
//				do {
//					bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
//					if (bytesRead >= 0) {
//						current += bytesRead;
//					}
//				} while (bytesRead > -1);
//
//				bos.write(mybytearray, 0, current);
//				bos.flush();
//				long end = System.currentTimeMillis();
//				System.out.println(end - start);
//				bos.close();
//				socket.close();
				
				
				
				
				
				
				
				File myFile = new File(path);
				byte[] mybytearray = new byte[(int) myFile.length()];
				FileInputStream fis = new FileInputStream(myFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				bis.read(mybytearray, 0, mybytearray.length);
				//ObjectOutputStream...
				OutputStream os = socket.getOutputStream();
				System.out.println("Sending...");
				os.write(mybytearray, 0, mybytearray.length);
				os.flush();
				socket.close();
			}
		} catch (Exception e) {
			System.err.println("ERRO ENVIO ARQUIVO "+e);
		}
	}

}
