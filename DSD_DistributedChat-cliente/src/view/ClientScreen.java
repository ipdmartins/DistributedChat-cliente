package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import control.ClienteControl;
import control.Observador;

public class ClientScreen extends JFrame implements Observador {

	// PAINEL PRINCIPAL
	private JFrame frameNewContact;
	private JPanel panelMenuBtn;
	private JPanel panelNewContact;
	private JPanel panelUsers;
	private JPanel panelUsersTitle;
	private JPanel panelUsersBotton;
	private JButton jbLoginMenu;
	private JButton jbMyAccount;
	private JButton jbLogout;
	private JButton jbAddMyList;
	private JButton jbStartChat;
	private JButton jbRemove;
	private JButton jbNewContact;
	private JLabel jlMyContacts;
	private JLabel jlNewContact;
	private JTextField JtxtNewContact;
	private DefaultListModel model;
	private JList list;

		// PAINEL LOGIN
	private JFrame frameLogin;
	private JPanel panelMyLogin;
	private JLabel jlEmailLogin;
	private JLabel jlPassword;
	private JTextField jtxtEmailLogin;
	private JTextField jtxtPassword;
	private JButton jbLogin;

	// PAINEL MY ACCOUNT
	private JFrame frameMyAccount;
	private JPanel panelMyAccount;
	private JFrame framePass;
	private JPanel panelPass;
	private JLabel jlName;
	private JLabel jlEmailAccount;
	private JLabel jlYearBirth;
	private JLabel jlPasswordAccount;
	private JLabel jlValidatePass;
	private JTextField jtxtName;
	private JTextField jtxtEmailAccount;
	private JTextField jtxtBirth;
	private JTextField jtxtPasswordAccount;
	private JTextField jtxtValidatePass;
	private JButton jbSave;
	private JButton jbActualize;
	private JButton jbValidatePass;

	// PAINEL CHAT
	private JFrame frameChat;
	private JPanel panelChatClient;
	private JPanel panelChatButtons;
	private JPanel panelContact;
	private JPanel panelText;
	private JLabel jlnomeContato;
	private JTextField jtxtChatText;
	private JTextField jtxtChatTextSender;
	private JButton jbSend;
	private JButton jbFile;
	private DefaultListModel modelChat;
	private JList jListChat;
	private List<DefaultListModel> listaModelChats;
	private JFileChooser file;

	private JOptionPane optionPane;
	private JTable chatPrincipal;

	private ClienteControl clienteControl;

	public ClientScreen() {
		setTitle("CLIENT CHAT TABLE");
		setSize(400, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {

		clienteControl = ClienteControl.getInstance();
		clienteControl.setScreen(this);
		clienteControl.addObservador(this);
		optionPane = new JOptionPane();
		model = new DefaultListModel();
		list = new JList(model);
		listaModelChats = new ArrayList<DefaultListModel>();
		file = new JFileChooser();

		// LABELS
		jlValidatePass = new JLabel("Digite sua senha");
		jlEmailLogin = new JLabel("E-mail: ");
		jlEmailAccount = new JLabel("E-mail: ");
		jlMyContacts = new JLabel("MY CONTACTS");
		jlName = new JLabel("Name: ");
		jlPassword = new JLabel("Password: ");
		jlPasswordAccount = new JLabel("Password: ");
		jlYearBirth = new JLabel("Year of Birth: ");
		jlNewContact = new JLabel("Type e-mail contact: ");

		// BUTTONS
		jbAddMyList = new JButton("Add Contact");
		jbLogin = new JButton("Login");
		jbLoginMenu = new JButton("Login");
		jbLogout = new JButton("Logout");
		jbMyAccount = new JButton("Register");
		jbSave = new JButton("Save");
		jbSend = new JButton("Send text");
		jbStartChat = new JButton("Start chat");
		jbRemove = new JButton("Remove Contact");
		jbFile = new JButton("Send file");
		jbActualize = new JButton("Edit");
		jbValidatePass = new JButton("Validate");
		jbNewContact = new JButton("Add");

		// TEXT FIELDS
		jtxtValidatePass = new JTextField();
		jtxtBirth = new JTextField();
		jtxtChatText = new JTextField();
		jtxtEmailLogin = new JTextField();
		jtxtEmailAccount = new JTextField();
		jtxtName = new JTextField();
		jtxtPassword = new JTextField();
		jtxtPasswordAccount = new JTextField();
		jtxtChatTextSender = new JTextField();
		JtxtNewContact = new JTextField();

		// PANES E FRAMES
		panelMenuBtn();
		panelUsers();
		add(panelMenuBtn, BorderLayout.NORTH);
		add(panelUsers, BorderLayout.CENTER);
		frameNewContact.add(panelNewContact);

		panelMyAccount();
		frameMyAccount.add(panelMyAccount, BorderLayout.CENTER);
		framePass.add(panelPass, BorderLayout.CENTER);

		panelMyLogin();
		frameLogin.add(panelMyLogin);
		
//		frameChat.add(panelChatClient, BorderLayout.CENTER);

	}

	private void panelMenuBtn() {
		panelMenuBtn = new JPanel();
		panelMenuBtn.setLayout(new FlowLayout());

		jbMyAccount.addActionListener((ActionEvent e) -> {
			frameMyAccount.setVisible(true);
		});

		jbLoginMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameLogin.setVisible(true);
			}
		});

		jbLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = clienteControl.logout();
				optionPane.showMessageDialog(null, s);
				//FALTA MODEL.CLEAR
			}
		});

		panelMenuBtn.add(jbLoginMenu);
		panelMenuBtn.add(jbMyAccount);
		panelMenuBtn.add(jbLogout);
	}

	private void panelUsers() {
		panelUsers = new JPanel();
		panelUsers.setLayout(new BorderLayout());

		panelUsersTitle = new JPanel();
		panelUsersTitle.setLayout(new FlowLayout());
		panelUsersTitle.add(jlMyContacts);

		panelUsersBotton = new JPanel();
		panelUsersBotton.setLayout(new FlowLayout());
		panelUsersBotton.add(jbAddMyList);
		panelUsersBotton.add(jbStartChat);
		panelUsersBotton.add(jbRemove);

		jbAddMyList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameNewContact.setVisible(true);
			}
		});

		jbStartChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String res = clienteControl.startChat(list.getSelectedIndex());
				panelChat(res, list.getSelectedIndex());
			}
		});

		jbRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> lista = null;
				lista = clienteControl.removeContact(list.getSelectedIndex());
				if (lista != null) {
					optionPane.showMessageDialog(null, "removed");
					model.clear();
					for (int i = 0; i < lista.size(); i++) {
						model.add(i, i + ") " + lista.get(i));
					}
				}
			}
		});

		panelUsers.add(panelUsersTitle, BorderLayout.NORTH);
		panelUsers.add(list, BorderLayout.CENTER);
		panelUsers.add(panelUsersBotton, BorderLayout.SOUTH);

		frameNewContact = new JFrame();
		frameNewContact.setTitle("ADD NEW CONTACT");
		frameNewContact.setSize(300, 150);
		frameNewContact.setResizable(false);
		frameNewContact.setLocationRelativeTo(null);

		panelNewContact = new JPanel();
		panelNewContact.setLayout(new GridLayout(3, 1));

		panelNewContact.add(jlNewContact);
		panelNewContact.add(JtxtNewContact);
		panelNewContact.add(jbNewContact);

		jbNewContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> lista = null;
				lista = clienteControl.addContact(JtxtNewContact.getText());
				if (lista != null) {
					optionPane.showMessageDialog(null, "added");
					model.clear();
					for (int i = 0; i < lista.size(); i++) {
						model.add(i, i + ") " + lista.get(i));
					}
				}
			}
		});
	}

	private void panelMyAccount() {
		frameMyAccount = new JFrame();
		frameMyAccount.setTitle("MY ACCOUNT");
		frameMyAccount.setSize(450, 250);
		frameMyAccount.setResizable(false);
		frameMyAccount.setLocationRelativeTo(null);

		panelMyAccount = new JPanel();
		panelMyAccount.setLayout(new GridLayout(5, 3));

		panelMyAccount.add(jlName);
		panelMyAccount.add(jtxtName);
		panelMyAccount.add(jlEmailAccount);
		panelMyAccount.add(jtxtEmailAccount);
		panelMyAccount.add(jlYearBirth);
		panelMyAccount.add(jtxtBirth);
		panelMyAccount.add(jlPasswordAccount);
		panelMyAccount.add(jtxtPasswordAccount);
		jbActualize.setEnabled(false);
		panelMyAccount.add(jbActualize);
		panelMyAccount.add(jbSave);

		jbSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = clienteControl.register(jtxtName.getText(), jtxtEmailAccount.getText(), jtxtBirth.getText(),
						jtxtPasswordAccount.getText());
				optionPane.showMessageDialog(null, s);
				if (s.equalsIgnoreCase("stored") || s.equalsIgnoreCase("actualized")) {
					jtxtEmailAccount.setEnabled(false);
					jtxtPasswordAccount.setEnabled(false);
					jtxtEmailAccount.setVisible(false);
					jtxtPasswordAccount.setVisible(false);
					enablePanel(0);
				}
			}
		});

		jbActualize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				framePass.setVisible(true);
			}
		});

		framePass = new JFrame();
		framePass.setTitle("Pass validation");
		framePass.setSize(200, 150);
		framePass.setResizable(false);
		framePass.setLocationRelativeTo(null);

		panelPass = new JPanel();
		panelPass.setLayout(new GridLayout(3, 1));

		panelPass.add(jlValidatePass);
		panelPass.add(jtxtValidatePass);
		panelPass.add(jbValidatePass);

		jbValidatePass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean res = clienteControl.validatePass(jtxtValidatePass.getText());
				if (res) {
					optionPane.showMessageDialog(null, "Granted");
					enablePanel(1);
				} else {
					optionPane.showMessageDialog(null, "Validation failed");
				}
			}
		});
	}

	private void enablePanel(int cond) {
		if (cond == 0) {
			jtxtName.setEnabled(false);
			jtxtBirth.setEnabled(false);
			jbSave.setEnabled(false);
			jbActualize.setEnabled(true);
		} else if (cond == 1) {
			jtxtEmailAccount.setVisible(true);
			jtxtPasswordAccount.setVisible(true);
			jtxtName.setEnabled(true);
			jtxtBirth.setEnabled(true);
			jbSave.setEnabled(true);
			jbActualize.setEnabled(false);
		}
	}

	private void panelMyLogin() {
		frameLogin = new JFrame();
		frameLogin.setTitle("LOGIN");
		frameLogin.setSize(300, 200);
		frameLogin.setResizable(false);
		frameLogin.setLocationRelativeTo(null);

		panelMyLogin = new JPanel();
		panelMyLogin.setLayout(new GridLayout(5, 1));

		panelMyLogin.add(jlEmailLogin);
		panelMyLogin.add(jtxtEmailLogin);
		panelMyLogin.add(jlPassword);
		panelMyLogin.add(jtxtPassword);
		panelMyLogin.add(jbLogin);

		jbLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> lista = null;
				lista = clienteControl.login(jtxtEmailLogin.getText(), jtxtPassword.getText());
				if (lista != null) {
					optionPane.showMessageDialog(null, "Welcome");
					for (int i = 0; i < lista.size(); i++) {
						model.add(i, i + ") " + lista.get(i));
					}
				}
			}
		});
	}

	private void panelChat(String nome, int index) {
		frameChat = new JFrame();
		frameChat.setTitle("CHAT");
		frameChat.setSize(400, 400);
		frameChat.setResizable(false);
		frameChat.setLocationRelativeTo(null);
		frameChat.setVisible(true);
		
		panelChatClient = new JPanel(new BorderLayout());
		
		jlnomeContato = new JLabel(nome);
		panelChatClient.add(jlnomeContato, BorderLayout.NORTH);
		
		modelChat = new DefaultListModel();
		listaModelChats.add(index, modelChat);
		jListChat = new JList(modelChat);
		panelChatClient.add(jListChat, BorderLayout.CENTER);
		JScrollPane jscroll = new JScrollPane(jListChat);
		
		panelText = new JPanel(new GridLayout(2, 1));
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
				clienteControl.sendMessage(jtxtChatTextSender.getText(), index);
				modelChat.add(index, "you: "+jtxtChatTextSender.getText());
			}
		});

		jbFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//String path = file.setFileSelectionMode(FileSystemView.getFileSystemView());
				
				// clienteControl.sendFile();

			}
		});
		frameChat.add(panelChatClient);
	}
	
	public void chatFeed(int index, String res) {
		listaModelChats.get(index).add(index, res);
	}

	@Override
	public void mudouTabuleiro() {
		chatPrincipal.getAutoResizeMode();
		chatPrincipal.getFont();
		chatPrincipal.updateUI();
		chatPrincipal.repaint();
	}

}
