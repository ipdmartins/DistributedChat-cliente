/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
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
				System.out.println("REQUEST MAN LEU: " + res);
				if (!res.equalsIgnoreCase("")) {
					clienteControl.contactMessage(res, socket);
				}
				streamContact.closeStream();
				socket.close();
			}
		} catch (SocketException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		} catch (IOException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		}
	}

}
