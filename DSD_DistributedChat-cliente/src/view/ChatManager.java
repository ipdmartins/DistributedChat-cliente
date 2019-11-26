package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import control.ClienteControl;

public class ChatManager extends Thread {

	private String nome;
	private int contatoSelecionado;
	private JFrame frameChat;
	private JPanel panelChatClient;
	private JPanel panelText;
	private JPanel panelChatButtons;
	private JLabel jlnomeContato;
	private JButton jbSend;
	private JButton jbFile;
	private JTextField jtxtChatTextSender;
	private JTextArea jtxtChat;
	private ClienteControl clienteControl;
	private JFileChooser fileChooser;
	private int listachatManagerIndex;
	private List<String> listaConversa;

	public ChatManager(String nome, int index) {
		this.nome = nome;
		this.contatoSelecionado = index;
		this.clienteControl = ClienteControl.getInstance();
		this.listaConversa = new ArrayList<String>();

	}

	public void panelChat(String message) {
		frameChat = new JFrame();
		frameChat.setTitle("CHAT");
		frameChat.setSize(450, 550);
		frameChat.setResizable(false);
		frameChat.setLocationRelativeTo(null);

		jlnomeContato = new JLabel("Seu contato: "+nome);
		panelChatClient = new JPanel(new BorderLayout());
		panelChatClient.add(jlnomeContato, BorderLayout.NORTH);

		if (!message.equalsIgnoreCase("")) {
			jtxtChat = new JTextArea();
			jtxtChat.append("Contato" + ": " + message + "\n");
		} else {
			jtxtChat = new JTextArea();
		}
		clienteControl.setListachatManagerIndex(listachatManagerIndex);
		panelChatClient.add(jtxtChat, BorderLayout.CENTER);

		jbSend = new JButton("Send text");
		jbFile = new JButton("Send file");
		panelText = new JPanel(new GridLayout(2, 1));
		this.jtxtChatTextSender = new JTextField();
		panelText.add(jtxtChatTextSender);
		panelChatButtons = new JPanel();
		panelChatButtons.setLayout(new FlowLayout());
		panelChatButtons.add(jbSend);
		panelChatButtons.add(jbFile);
		panelText.add(panelChatButtons);

		panelChatClient.add(panelText, BorderLayout.SOUTH);
		
		jbSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clienteControl.sendMessage(jtxtChatTextSender.getText(), contatoSelecionado, listachatManagerIndex);
				chatFeeder("You", jtxtChatTextSender.getText());
			}
		});

		jbFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Select the file");
				File dir = new File(System.getProperty("user.dir"));
				fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setCurrentDirectory(dir);
				int r = fileChooser.showOpenDialog(null);

				if (r == JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "File selected!");
					clienteControl.sendFile(fileChooser.getSelectedFile().getAbsolutePath(), contatoSelecionado);
				} else {
					JOptionPane.showMessageDialog(null, "Operation aborted");
				}
			}
		});
		frameChat.add(panelChatClient);
		frameChat.setVisible(true);
	}

	public void chatFeeder(String nome, String txt) {
		jtxtChat.append(nome + ": " + txt + "\n");
		listaConversa.add(txt);
	}

	public int getListachatManagerIndex() {
		return listachatManagerIndex;
	}

	public void setListachatManagerIndex(int indexList) {
		this.listachatManagerIndex = indexList;
		System.out.println("METEU: "+indexList);
	}

	public List<String> getListaConversa() {
		return listaConversa;
	}

	public void addConversa(String txt) {
		this.listaConversa.add(txt);
	}

}
