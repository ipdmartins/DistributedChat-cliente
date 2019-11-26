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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import control.StreamClient;
import model.Cliente;
import view.ClientScreen;

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
	private List<StreamClient> listStreams;
	private int serverPort;
	private String serverIP;
	private int portaMyContact;
	private String ipMyContact;
	private List<Observador> observadores;
	private ConnectionManager connectionManager;
	private ClientScreen screen;
	private List<String> listaIps;
	private List<Integer> listaIndex;
	private int listachatManagerIndex;

	private static ClienteControl instance;

	private ClienteControl() {
		this.cliente = new Cliente();
		this.cliente.setEmail(null);
		this.listaClintes = new ArrayList<Cliente>();
		this.gson = new Gson();
		this.answer = "";
		this.serverCliente = null;
		this.socketCliente = null;
		this.observadores = new ArrayList<>();
		this.serverPort = 56005;
		this.serverIP = "192.168.2.171";
		this.listStreams = new ArrayList<StreamClient>();
		this.listaIps = new ArrayList<String>();
		this.listaIndex = new ArrayList<Integer>();
		this.listachatManagerIndex = 0;
	}

	public synchronized static ClienteControl getInstance() {
		if (instance == null) {
			instance = new ClienteControl();
		}
		return instance;
	}

	public boolean connect() {
		try {
			socketCliente = new Socket(serverIP, serverPort);

			if (socketCliente != null) {
				streamClient = new StreamClient();
				streamClient.createStream(socketCliente);
				RequestManagerCli requestManagerCli = new RequestManagerCli(socketCliente, true);
				requestManagerCli.start();
			} else {
				System.out.println("CONEXÃO NÃO EFETUADA");
				return false;
			}
		} catch (IOException e) {
			System.err.println("ERRO NA CONEXAO connect() cliente " + e);
			return false;
		}
		return true;
	}

	public void setServer() {
		this.connectionManager = new ConnectionManager(cliente.getPortaCliente());
		this.connectionManager.start();
	}

	public void register(String nome, String email, String nasc, String pass) {
		// cadastro inicial do cliente no server
		this.cliente.setNome(nome);
		this.cliente.setEmail(email);
		this.cliente.setAnoNasc(nasc);
		this.cliente.setSenha(pass);
		this.cliente.setPortaCliente(0);
		this.cliente.setIpCliente("myIP");
		this.cliente.setPortaServer(serverPort);
		this.cliente.setIpServer(serverIP);
		this.cliente.setStatus("OFFLINE");
		this.answer = gson.toJson(cliente);
		if (serverCliente == null || socketCliente == null || socketCliente.isClosed()) {
			connect();
		}
		// answer ja é uma uma String Json com todos os atributos de cliente.
		addresser("A", answer);
	}

	public void setRegister(String res) {
		this.screen.setRegister(res);
	}

	public void validatePass(String text) {
		if (serverCliente == null || socketCliente == null || socketCliente.isClosed()) {
			connect();
		}
		answer = cliente.getEmail() + "," + text;
		addresser("G", answer);
	}

	public void setValidate(String res) {
		if (res.equalsIgnoreCase("Granted")) {
			this.screen.setValidate(true);
		} else {
			this.screen.setValidate(false);
		}
	}

	public void login(String email, String pass) {
		answer = email + "," + pass;
		if (serverCliente == null || socketCliente == null || socketCliente.isClosed()) {
			connect();
		}
		addresser("B", answer);

//		if (answer.equalsIgnoreCase("Welcome")) {
//			System.out.println("recebeu welcome");
//			if (listManager(2, "") == null) {
//				System.out.println("recebeu lista null");
//				setServer();
//				System.out.println("agora a lista de conatos");
//				return listManager(1, "");
//			}
//		}

	}

	public void setLogin(String res) {
		this.answer = res;
		if (!answer.equalsIgnoreCase("fail to log")) {
			if (listManager(2, answer) == null) {
				setServer();
				NotifyAlive notifyalive = new NotifyAlive(this.cliente.getEmail(), this.streamClient);
				notifyalive.start();
			}
		}
	}

	public void setListContactLogin(String res) {
		List contatos = listManager(1, res);
		if (contatos != null) {
			List<String> dados = new ArrayList<String>();
			dados.add(this.cliente.getNome());
			dados.add(this.cliente.getEmail());
			dados.add(this.cliente.getAnoNasc());
			dados.add(this.cliente.getSenha());
			this.screen.setListContact(contatos, 1, dados);
		}
	}

	public void setListContactUpdate(String res) {
		Cliente cli1 = gson.fromJson(res, Cliente.class);
		List contatosUpdate = new ArrayList<String>();

		for (int i = 0; i < this.cliente.getMyContacts().size(); i++) {
			if (this.cliente.getMyContacts().get(i).getEmail().equalsIgnoreCase(cli1.getEmail())) {
				this.cliente.getMyContacts().set(i, cli1);
			}
			contatosUpdate.add(cliente.getMyContacts().get(i).getStatus() + " - "
					+ cliente.getMyContacts().get(i).getNome() + " - " + cliente.getMyContacts().get(i).getEmail());
		}
		this.screen.setListContact(contatosUpdate, 0, null);
	}

	public void addContact(String email) {
		String add = cliente.getEmail() + "," + email;
		System.out.println("cli add: " + add);
		addresser("D", add);

//		if (add.equalsIgnoreCase("added")) {
//			return listManager(1, "");
//		}
//		return null;
	}

	public void setAdded(String res) {
		List contatos = listManager(1, res);
		if (contatos != null) {
			this.screen.setListContact(contatos, 3, null);
		}
	}

	public void removeContact(int index) {
		String remove = cliente.getEmail() + "," + cliente.getMyContacts().get(index).getEmail();
		addresser("E", remove);

//		if (remove.equalsIgnoreCase("removed")) {
//			return listManager(1, "");
//		}
//		return null;
	}

	public void setRemoved(String res) {
		List contatos = listManager(1, res);
		if (contatos != null) {
			this.screen.setListContact(contatos, 2, null);
		}
	}

	public String startChat(int index) {
		if (cliente.getMyContacts().get(index).getStatus().equalsIgnoreCase("OFFLINE")) {
			return "OFFLINE";
		}
		return cliente.getMyContacts().get(index).getNome();
	}
								///contatoSelecionado, listachatManagerIndex
	public void sendMessage(String message, int index, int indexLista) {
		String ip = cliente.getMyContacts().get(index).getIpCliente();
		int porta = cliente.getMyContacts().get(index).getPortaCliente();
		System.out.println("ip: " + ip + " porta: " + porta);

		try {
			Socket socketContato = new Socket(ip, porta);
			if (socketContato != null) {
				StreamClient streamContact = new StreamClient();
				streamContact.createStream(socketContato);
				streamContact.sendMessage("A");
				streamContact.sendMessage(message);
				boolean cond = false;
				for (int i = 0; i < listaIps.size(); i++) {
					if(listaIps.get(i).equalsIgnoreCase(ip)) {
						cond = true;
					}
				}
				if(!cond) {
					listaIps.add(ip);
					listaIndex.add(listachatManagerIndex);
				}
				streamContact.closeStream();
				socketContato.close();
			} else {
				System.err.println("ERRO CONEXÃO sendMessage() AO CLIENTE ");
			}
		} catch (IOException e) {
			System.err.println("ERRO NO CLIENTE sendMessage() AO CLIENTE " + e);
		}
	}

	public void contactMessage(String res, Socket socket) {
		System.out.println("chegou " + res);
		String ip = socket.getInetAddress().toString();
		if (ip.contains("/")) {
			ip = ip.replace("/", "");
		}
		System.out.println("ip aqui: " + ip);
		boolean cond = true;
		for (int i = 0; i < listaIps.size(); i++) {
			if (listaIps.get(i).equalsIgnoreCase(ip)) {
				System.out.println("valor int do hashmap " + listaIps.get(i));
				cond = false;
				this.screen.chatFeed(listaIndex.get(i), res, 1, "Contato");
				break;
			}
		}
		if (cond) {
			for (int i = 0; i < cliente.getMyContacts().size(); i++) {
				if (cliente.getMyContacts().get(i).getIpCliente().equalsIgnoreCase(ip)) {
					this.screen.chatFeed(i, res, 2, cliente.getMyContacts().get(i).getNome());
					listaIps.add(ip);
					listaIndex.add(listachatManagerIndex);
					break;
				}
			}
		}
	}

	public void sendFile(String path, int index) {
		String ip = cliente.getMyContacts().get(index).getIpCliente();
		int porta = cliente.getMyContacts().get(index).getPortaCliente();

		try {
			Socket socketContato = new Socket(ip, porta);
			if (socketContato != null) {
				StreamClient streamContact = new StreamClient();
				streamContact.createStream(socketContato);
				streamContact.sendMessage("B");
				streamContact.sendMessage(path);
				FileManager file = new FileManager(socketContato, path);
				file.start();
				streamContact.closeStream();
				socketContato.close();
			} else {
				System.err.println("ERRO CONEXÃO sendMessage() AO CLIENTE ");
			}
		} catch (IOException e) {
			System.err.println("ERRO NO CLIENTE sendMessage() AO CLIENTE " + e);
		}

	}

	public List listManager(int option, String message) {
		List<String> contatos = null;

		if (option == 1) {
			contatos = new ArrayList<String>();
			String jsonLista = message;
			Type tipoLista = new TypeToken<ArrayList<Cliente>>() {
			}.getType();
			listaClintes = gson.fromJson(jsonLista, tipoLista);
			System.out.println(listaClintes + " - " + listaClintes.size());
			cliente.setMyContacts(listaClintes);
			for (int i = 0; i < listaClintes.size(); i++) {
				contatos.add(listaClintes.get(i).getStatus() + " - " + listaClintes.get(i).getNome() + " - "
						+ listaClintes.get(i).getEmail());
			}
		} else if (option == 2) {
//				String cliente = streamClient.readMessage();
//				System.out.println(cliente);
			this.cliente = gson.fromJson(message, Cliente.class);
			System.out.println(this.cliente.toString());

		} else if (option == 3) {
			Cliente cli = new Cliente();
			cli.setCliente(gson.fromJson(message, Cliente.class));
			for (int i = 0; i < cliente.getMyContacts().size(); i++) {
				if (cliente.getMyContacts().get(i).getEmail().equalsIgnoreCase(cli.getEmail())) {
					cliente.getMyContacts().get(i).setCliente(cli);
					String contato = cli.getStatus() + " - " + cli.getNome() + " - " + cli.getEmail();
					this.screen.actualizeJList(i, contato);
				}
			}
		}

		return contatos;
	}

	public void logout() {
		addresser("C", cliente.getEmail());
	}

	public void setLogout(String res) {
		this.screen.setLogout(res);
		if (res.equalsIgnoreCase("logged out")) {
			if (!socketCliente.isClosed()) {
				try {
					socketCliente.close();
				} catch (IOException e) {
					System.err.println("ERRO AO FECHAR SOCKET NO CLIENTE" + e);
				}
			}
		}
	}

	public void addresser(String req, String response) {
		try {
			streamClient.sendMessage(req);
			streamClient.sendMessage(response);// response é o Json sendo enviado
//			this.answer = streamClient.readMessage();
		} catch (IOException e) {
			System.err.println("ERRO ENVIO GSON REGISTRO" + e);
		}
//		return this.answer;
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

	public ClientScreen getScreen() {
		return screen;
	}

	public void setScreen(ClientScreen screen) {
		this.screen = screen;
	}

	public int getListachatManagerIndex() {
		return listachatManagerIndex;
	}

	public void setListachatManagerIndex(int listachatManagerIndex) {
		this.listachatManagerIndex = listachatManagerIndex;
	}
	
	

}
