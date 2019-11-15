/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import static java.lang.Thread.sleep;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import control.StreamClient;
import model.Cliente;

/**
 *
 * @author ipdmartins
 */
public class ClienteControl {

	private Cliente cliente;
	private List<Cliente> listaClintes;
	private Gson gson;
	private String answer;
	private ServerSocket serverCliente;
	private Socket socketCliente;
	private StreamClient streamClient;
	private String[][] chatTabuleiro;
	private int selectedRow;
	private int selectedCol;
	private int tabuleiroRow;
	private int tabuleiroCol;
	private int serverPort;
	private String serverIP;
	private List<Observador> observadores;

	private static ClienteControl instance;

	private ClienteControl() {
		this.tabuleiroRow = 10;
		this.tabuleiroCol = 4;
		this.cliente = new Cliente();
		this.cliente.setEmail(null);
		this.listaClintes = new ArrayList<Cliente>();
		this.gson = new Gson();
		this.answer = "";
		this.serverCliente = null;
		this.socketCliente = null;
		this.chatTabuleiro = new String[tabuleiroRow][tabuleiroCol];
		this.observadores = new ArrayList<>();
		this.serverPort = 56003;
		this.serverIP = "192.168.2.171";
		initTable();
	}

	public void initTable() {
		for (int i = 0; i < tabuleiroRow; i++) {
			for (int j = 0; j < tabuleiroCol; j++) {
				chatTabuleiro[i][j] = " - ";
			}
		}
	}

	public synchronized static ClienteControl getInstance() {
		if (instance == null) {
			instance = new ClienteControl();
		}
		return instance;
	}

	public void connect() {
		try {
			serverCliente = new ServerSocket(cliente.getPortaCliente());
			serverCliente.setReuseAddress(true);
			socketCliente = new Socket(serverIP, serverPort);
			if (socketCliente != null) {
				streamClient = new StreamClient();
				streamClient.createStream(socketCliente);
			} else {
				System.out.println("CONEXÃO NÃO EFETUADA");
			}
		} catch (IOException e) {
			System.err.println("ERRO NA CONEXAO connect() cliente " + e);
		}
	}

	public String register(String nome, String email, String nasc, String pass, String porta, String myIP) {
		// cadastro inicial do cliente no server
		this.cliente.setNome(nome);
		this.cliente.setEmail(email);
		this.cliente.setAnoNasc(nasc);
		this.cliente.setSenha(pass);
		this.cliente.setPortaCliente(Integer.parseInt(porta));
		this.cliente.setIpCliente(myIP);
		this.cliente.setPortaServer(serverPort);
		this.cliente.setIpServer(serverIP);
		this.cliente.setStatus("null");
		this.answer = gson.toJson(cliente);
		if (serverCliente == null || socketCliente == null || socketCliente.isClosed()) {
			connect();
		}
		// answer ja é uma uma String Json com todos os atributos de cliente.
		answer = addresser("A", answer);
		return answer;
	}

	public boolean validatePass(String text) {
		if (serverCliente == null || socketCliente == null || socketCliente.isClosed()) {
			connect();
		}
		answer = cliente.getEmail() + "," + text;
		answer = addresser("G", answer);
		if (answer.equalsIgnoreCase("Granted")) {
			return true;
		}
		return false;
	}

	public List login(String email, String pass) {
		answer = email + "," + pass;
		if (serverCliente == null || socketCliente == null || socketCliente.isClosed()) {
			connect();
		}
		answer = addresser("B", answer);
		if (answer.equalsIgnoreCase("Welcome")) {
			if(listManager(2) == null) {
				return listManager(1);
			}
		}
		return null;
	}

	public List addContact(String email) {

		String add = cliente.getEmail() + "," + email;
		System.out.println("cli add: "+add);
		add = addresser("D", add);
		if (add.equalsIgnoreCase("added")) {
			return listManager(1);
		}
		return null;
	}

	public List removeContact(int index) {
		String remove = cliente.getEmail() + "," + cliente.getMyContacts().get(index).getEmail();
		remove = addresser("E", remove);
		if(remove.equalsIgnoreCase("removed")) {
			return listManager(1);
		}
		return null;
	}

	public List listManager(int option) {
		List<String> contatos = null;
		try {
			if (option == 1) {
				String jsonLista = streamClient.readMessage();
				Type tipoLista = new TypeToken<ArrayList<Cliente>>() {
				}.getType();
				listaClintes = gson.fromJson(jsonLista, tipoLista);
				cliente.setMyContacts(listaClintes);
				contatos = new ArrayList<String>();
				for (int i = 0; i < listaClintes.size(); i++) {
					contatos.add(listaClintes.get(i).getStatus() + " - " + listaClintes.get(i).getNome() + 
							" - " + listaClintes.get(i).getEmail() + " - Porta: " + 
							listaClintes.get(i).getPortaCliente() + " - IP: " + listaClintes.get(i).getIpCliente());
				}
			}else if (option == 2) {
				String cliente = streamClient.readMessage();
				this.cliente = gson.fromJson(cliente, Cliente.class);
				System.out.println(this.cliente.getEmail());
			}
		} catch (IOException e) {
			System.err.println("ERRO AO RECEBER LISTA GSON DO SERVER " + e);
		}
		return contatos;
	}

	public void notifylive() {
		try {
			sleep(40000);
			answer = cliente.getEmail();
			addresser("F", answer);
		} catch (InterruptedException e) {
			System.err.println("ERRO ENVIO GSON REGISTRO" + e);
		}
	}

	public String logout() {
		
		answer = addresser("C", cliente.getEmail());

		if (answer.equalsIgnoreCase("logged out")) {
			if (!socketCliente.isClosed()) {
				try {
					socketCliente.close();
				} catch (IOException e) {
					System.err.println("ERRO AO FECHAR SOCKET NO CLIENTE" + e);
				}
			}
		}
		return answer;
	}

	public String addresser(String req, String response) {
		try {
			streamClient.sendMessage(req);
			streamClient.sendMessage(response);// response é o Json sendo enviado
			this.answer = streamClient.readMessage();
		} catch (IOException e) {
			System.err.println("ERRO ENVIO GSON REGISTRO" + e);
		}
		return answer;
	}

	public void startChat(String email) {
		// com outro cliente
	}

	public void sendMessage(String message) {
		// para outro cliente
	}

//	public void String sendFile(File file) {
//		// para outro cliente
//		return "";
//	}

	public Object getchatTabuleiro(int row, int col) {
		return (chatTabuleiro[row][col] == null ? null : chatTabuleiro[row][col]);
	}

	public void pegarIndexTabuleiro(int selectionRow, int selectedColumn) {
		selectedRow = selectionRow;
		selectedCol = selectedColumn;
	}

	public void notificarMudancaTabuleiro() {
		for (Observador obs : observadores) {
			obs.mudouTabuleiro();
		}
	}

	public void removeObservador(Observador obs) {
		observadores.remove(obs);
	}

	public void addObservador(Observador obs) {
		observadores.add(obs);
	}

}
