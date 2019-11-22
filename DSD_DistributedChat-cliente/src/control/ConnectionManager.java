/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author ipdmartins
 */
public class ConnectionManager extends Thread {

	private ServerSocket serverCliente;
	private RequestManager requestManager;


//    private StreamClient streamClient;
//    private String res;
//    private int idStream;

	public ConnectionManager(ServerSocket server) {
		this.serverCliente = server;

	}

	@Override
	public void run() {
		try {
			while (true) {
				Socket socket = null;
				System.out.println("CLIENTE NO AGUARDO");
				socket = serverCliente.accept();

				if (socket != null) {
					requestManager = new RequestManager(socket);
					requestManager.start();
				} else {
					System.err.println("ERRO CONEXÃO sendMessage() AO CLIENTE");
				}

//				clienteControl.clienteConnector(socket);

			}
		} catch (SocketException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		} catch (IOException ex) {
			System.err.println("ERRO NO REQUESTMANAGER " + ex);
		}
	}

}
