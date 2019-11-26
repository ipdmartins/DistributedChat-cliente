package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import control.ClienteControl;

public class ClientScreen extends JFrame {

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
	private JTextField jtxtChatText;
	private List<ChatManager> listachatManager;
	private ChatManager chatManager;

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
		model = new DefaultListModel();
		list = new JList(model);
		listachatManager = new ArrayList<ChatManager>();

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
		jbStartChat = new JButton("Start chat");
		jbRemove = new JButton("Remove Contact");
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
				clienteControl.logout();
			}
		});

		panelMenuBtn.add(jbLoginMenu);
		panelMenuBtn.add(jbMyAccount);
		panelMenuBtn.add(jbLogout);
	}

	public void setLogout(String res) {
		JOptionPane.showMessageDialog(null, res);
		if (res.equalsIgnoreCase("logged out")) {
			model.clear();
		}
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
				if (res.equalsIgnoreCase("OFFLINE")) {
					JOptionPane.showMessageDialog(null, "Seu contato está OFFLINE");
				} else {
					chatManager = new ChatManager(res, list.getSelectedIndex());//passa o 1
					listachatManager.add(chatManager);// na posicao 0
					chatManager.setListachatManagerIndex(listachatManager.size() - 1);
					chatManager.panelChat("");
				}
			}
		});

		jbRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clienteControl.removeContact(list.getSelectedIndex());
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
				clienteControl.addContact(JtxtNewContact.getText());
			}
		});
	}

	public void chatFeed(int indexLista, String res, int req, String nome) {
		if (req == 1) {
			listachatManager.get(indexLista).chatFeeder(nome, res);
		} else if (req == 2) {
			chatManager = new ChatManager(nome,indexLista);
			listachatManager.add(chatManager);
			chatManager.setListachatManagerIndex(listachatManager.size() - 1);
			chatManager.panelChat(res);

		}
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
				clienteControl.register(jtxtName.getText(), jtxtEmailAccount.getText(), jtxtBirth.getText(),
						jtxtPasswordAccount.getText());
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
				clienteControl.validatePass(jtxtValidatePass.getText());
			}
		});
	}

	public void setValidate(boolean b) {
		if (b) {
			JOptionPane.showMessageDialog(null, "Granted");
			enablePanel(1);
		} else {
			JOptionPane.showMessageDialog(null, "Validation failed");
		}
	}

	public void setRegister(String res) {
		JOptionPane.showMessageDialog(null, res);
		if (res.equalsIgnoreCase("stored") || res.equalsIgnoreCase("actualized")) {
			jtxtEmailAccount.setEnabled(false);
			jtxtPasswordAccount.setEnabled(false);
			jtxtEmailAccount.setVisible(false);
			jtxtPasswordAccount.setVisible(false);
			enablePanel(0);
		}
	}

	private void enablePanel(int cond) {
		if (cond == 0) {
			jtxtName.setEnabled(false);
			jtxtBirth.setEnabled(false);
			jtxtEmailAccount.setEnabled(false);
			jtxtPasswordAccount.setEnabled(false);
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
				clienteControl.login(jtxtEmailLogin.getText(), jtxtPassword.getText());
			}
		});
	}

	public void setListContact(List lista, int opt, List<String> dados) {
		List<String> list;
		list = lista;
		if (opt == 1) {
			JOptionPane.showMessageDialog(null, "Welcome");
			jtxtName.setText(dados.get(0));
			jtxtEmailAccount.setText(dados.get(1));
			jtxtBirth.setText(dados.get(2));
			jtxtPasswordAccount.setText(dados.get(3));
			enablePanel(0);
		} else if (opt == 2) {
			JOptionPane.showMessageDialog(null, "removed");
		} else if (opt == 3) {
			JOptionPane.showMessageDialog(null, "added");
		}
		if (list != null) {
			model.clear();
			for (int i = 0; i < list.size(); i++) {
				model.add(i, i + ") " + list.get(i));
			}
		}
	}
	
	public void actualizeJList(int index, String contato) {
		model.add(index, index + ") " + contato);
	}


}
