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
	private int porta;
	private Socket socket;


//    private StreamClient streamClient;
//    private String res;
//    private int idStream;

	public ConnectionManager(int porta) {
		this.porta = porta;
	}

	@Override
	public void run() {
		try {
			this.serverCliente = new ServerSocket(porta);
			this.serverCliente.setReuseAddress(true);
			
			while (true) {
				socket = null;
				System.out.println("CLIENTE NO AGUARDO");
				socket = serverCliente.accept();

				if (socket != null) {
					requestManager = new RequestManager(socket, false);
					requestManager.start();
				} else {
					System.err.println("ERRO CONEXÃO requesteManager");
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
