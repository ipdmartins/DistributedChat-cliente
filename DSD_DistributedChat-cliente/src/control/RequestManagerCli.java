/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author ipdmartins
 */
public class RequestManagerCli extends Thread {

	private Socket socket;
	private boolean server;
	private String res;
	private ClienteControl clienteControl;
	private StreamClient streamContact;

	public RequestManagerCli(Socket socket, boolean server) {
		this.socket = socket;
		this.server = server;
		this.res = "";
		this.clienteControl = ClienteControl.getInstance();
	}

	@Override
	public void run() {
		try {
			streamContact = new StreamClient();
			streamContact.createStream(socket);

			if (server) {
				while (true) {
					res = streamContact.readMessage();
					switch (res) {
					case "A":
						res = streamContact.readMessage();
						this.clienteControl.setRegister(res);
						break;
					case "B":
						res = streamContact.readMessage();
						this.clienteControl.setValidate(res);
						break;
					case "C":
						res = streamContact.readMessage();
						this.clienteControl.setRegister(res);
						break;
					case "D":
						res = streamContact.readMessage();
						this.clienteControl.setLogin(res);
						break;
					case "E":
						res = streamContact.readMessage();
						this.clienteControl.setListContactLogin(res);
						break;
					case "F":
						res = streamContact.readMessage();
						this.clienteControl.setListContactUpdate(res);
						break;
					case "G":
						res = streamContact.readMessage();
						this.clienteControl.setLogout(res);
						break;
					case "H":
						res = streamContact.readMessage();
						this.clienteControl.setRemoved(res);
						break;
					case "I":
						res = streamContact.readMessage();
						this.clienteControl.setAdded(res);
						break;
					}
				}
			} else {
				res = streamContact.readMessage();

				if (res.equalsIgnoreCase("A")) {
					res = streamContact.readMessage();
					clienteControl.contactMessage(res, socket);
				} else if (res.equalsIgnoreCase("B")) {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					File myFile = new File("C:\\users\\");
					FileOutputStream fis = new FileOutputStream(myFile);
					byte[] mybytearray = new byte[4096];
					
					while(true) {
						int filesize = inputStream.read(mybytearray);
						if(filesize == -1) {
							break;
						}
						fis.write(mybytearray, 0, filesize);
					}
			}
				socket.close();
				streamContact.closeStream();
			}
		} catch (SocketException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		} catch (IOException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		} 
	}

}
