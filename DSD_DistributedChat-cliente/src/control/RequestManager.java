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
public class RequestManager extends Thread {

	private Socket socket;
	private String res;
	private ClienteControl clienteControl;

//	private ServerControl serverControl;
//    private StreamServer streamServer;
//    private int idStream;

	public RequestManager(Socket socket) {
		this.socket = socket;
		this.res = "";
		this.clienteControl = ClienteControl.getInstance();

//    	this.serverControl = serverControl;
//        this.streamServer = streamServer;
//        this.idStream = idStream;
	}

	@Override
	public void run() {
		try {
			StreamClient streamContact = new StreamClient();
			streamContact.createStream(socket);
			res = streamContact.readMessage();

			if (!res.equalsIgnoreCase("")) {
				clienteControl.contactMessage(res, socket);
			}
			streamContact.closeStream();
			socket.close();
			
		} catch (SocketException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		} catch (IOException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		}
	}

}
