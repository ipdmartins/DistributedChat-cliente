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
	private JFrame frameChat;
	private JLabel jlnomeContato;
	private JPanel panelChatClient;

	private JPanel panelContact;
	private JPanel panelText;

	private JPanel panelChatButtons;
	private JTextField jtxtChatText;
	private JTextField jtxtChatTextSender;
	private JButton jbSend;
	private JButton jbFile;
	private List<JLabel> listaLabelChats;
	private List<JButton> listaBtnTexts;
	private List<JButton> listaBtnFiles;
	private List<JTextField> listaJtxtFields;
	private List<JPanel> listaPanel;
	private JFileChooser file;
	String wordSender;

	private int counter1;
	private int counter2;

	private JOptionPane optionPane;

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
		optionPane = new JOptionPane();
		model = new DefaultListModel();
		list = new JList(model);
		listaLabelChats = new ArrayList<JLabel>();
		listaBtnTexts = new ArrayList<JButton>();
		listaBtnFiles = new ArrayList<JButton>();
		listaJtxtFields = new ArrayList<JTextField>();
		listaPanel = new ArrayList<JPanel>();
		file = new JFileChooser();
		counter1 = 0;
		counter2 = 0;

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
				clienteControl.logout();
			}
		});

		panelMenuBtn.add(jbLoginMenu);
		panelMenuBtn.add(jbMyAccount);
		panelMenuBtn.add(jbLogout);
	}
	
	public void setLogout(String res) {
		optionPane.showMessageDialog(null, res);
		if(res.equalsIgnoreCase("logged out")) {
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
				panelChat(res, list.getSelectedIndex(), "");
			}
		});

		jbRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clienteControl.removeContact(list.getSelectedIndex());

//				List<String> lista = null;
//				if (lista != null) {
//					optionPane.showMessageDialog(null, "removed");
//					model.clear();
//					for (int i = 0; i < lista.size(); i++) {
//						model.add(i, i + ") " + lista.get(i));
//					}
//				}
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

	public void panelChat(String nome, int indexJList, String message) {
		frameChat = new JFrame();
		frameChat.setTitle("CHAT");
		frameChat.setSize(450, 550);
		frameChat.setResizable(false);
		frameChat.setLocationRelativeTo(null);
		frameChat.setVisible(true);

		panelChatClient = new JPanel(new BorderLayout());
//		listaPanel.add(panelChatClient);

		jlnomeContato = new JLabel(nome);
		panelChatClient.add(jlnomeContato, BorderLayout.NORTH);

//		JPanel painel = new JPanel();
		JLabel jlChat;
		if(!message.equalsIgnoreCase("")) {
			jlChat = new JLabel(nome +": "+ message+" ");
		}else {
			jlChat = new JLabel();
		}
//		listaLabelChats.add(jlChat);
//		painel.add(listaLabelChats.get(listaLabelChats.size()-1));
//		System.out.println(listaLabelChats.size());
		panelChatClient.add(jlChat, BorderLayout.CENTER);
//		JScrollPane jscroll = new JScrollPane(painel);
			
		jbSend = new JButton("Send text");
		jbFile = new JButton("Send file");
		jbSend.setMnemonic(counter1);
		jbFile.setMnemonic(counter2);
//		System.out.println(jbSend.getMnemonic());
//		System.out.println(jbFile.getMnemonic());
//		listaBtnTexts.add(jbSend);
//		listaBtnTexts.add(jbFile);

		panelText = new JPanel(new GridLayout(2, 1));
		this.jtxtChatTextSender = new JTextField();
		listaJtxtFields.add(this.jtxtChatTextSender);
		panelText.add(jtxtChatTextSender);
		panelChatButtons = new JPanel();
		panelChatButtons.setLayout(new FlowLayout());
		panelChatButtons.add(jbSend);
		panelChatButtons.add(jbFile);
		panelText.add(panelChatButtons);

		panelChatClient.add(panelText, BorderLayout.SOUTH);
		String count1 = String.valueOf(counter1);
		String count2 = String.valueOf(counter2);
		jbSend.setActionCommand(count1);
		jbFile.setActionCommand(count2);
		
		wordSender = "";

		jbSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clienteControl.sendMessage(jtxtChatTextSender.getText(), indexJList);
				wordSender = jlChat.getText();
				wordSender += "You: " + jtxtChatTextSender.getText() + "<br>";
				System.out.println(wordSender);
				jlChat.setText("<html>" + wordSender + "</html>");
				
//				for (int i = 0; i < listaBtnTexts.size(); i++) {
//					String teste = String.valueOf(i);
//					if(jbSend.getActionCommand().equalsIgnoreCase(teste)) {
//						clienteControl.sendMessage(jtxtChatTextSender.getText(), indexJList, i );
						
//						wordSender = listaLabelChats.get(i).getText();
//						System.out.println(newWord);
//						wordSender += "You: " + listaJtxtFields.get(i).getText() + "<br>";
//						listaLabelChats.get(i).setText("<html>" + wordSender + "</html>");
//						listaPanel.get(i).add(listaLabelChats.get(i), BorderLayout.CENTER);
//						String old = listaStringWords.get(i);
//						listaStringWords.get(i).replace(listaStringWords.get(i), wordSender);
//						System.out.println("achou " + jbSend.getActionCommand());
	//					System.out.println(listaStringWords.get(i).toString());
	//					wordSender = "";
//					}
//				}
			}
		});

		jbFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// String path = file.setFileSelectionMode(FileSystemView.getFileSystemView());

				// clienteControl.sendFile();

			}
		});
		frameChat.add(panelChatClient);
		counter1++;
		counter2++;
	}

	public void chatFeed(int index, String res) {
//		wordSender += "Contact: " + res + "<br>";
//		listaLabelChats.get(index).setText("<html>" + wordSender + "</html>");

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
//				optionPane.showMessageDialog(null, s);
//				if (s.equalsIgnoreCase("stored") || s.equalsIgnoreCase("actualized")) {
//					jtxtEmailAccount.setEnabled(false);
//					jtxtPasswordAccount.setEnabled(false);
//					jtxtEmailAccount.setVisible(false);
//					jtxtPasswordAccount.setVisible(false);
//					enablePanel(0);
//				}
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
//				if (res) {
//					optionPane.showMessageDialog(null, "Granted");
//					enablePanel(1);
//				} else {
//					optionPane.showMessageDialog(null, "Validation failed");
//				}
			}
		});
	}
	
	public void setValidate(boolean b) {
		if (b) {
			optionPane.showMessageDialog(null, "Granted");
			enablePanel(1);
		} else {
			optionPane.showMessageDialog(null, "Validation failed");
		}

	}
	
	public void setRegister(String res) {
		optionPane.showMessageDialog(null, res);
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
//				List<String> lista = null;
				clienteControl.login(jtxtEmailLogin.getText(), jtxtPassword.getText());
//				if (lista != null) {
//					optionPane.showMessageDialog(null, "Welcome");
//					for (int i = 0; i < lista.size(); i++) {
//						model.add(i, i + ") " + lista.get(i));
//					}
//				}
			}
		});
	}
	
	public void setListContact(List lista, int opt, List<String> dados) {
		List<String> list;
		list = lista;
		if(opt == 1) {
			optionPane.showMessageDialog(null, "Welcome");
			jtxtName.setText(dados.get(0));
			jtxtEmailAccount.setText(dados.get(1));
			jtxtBirth.setText(dados.get(2));
			jtxtPasswordAccount.setText(dados.get(3));
			enablePanel(0);
		}else if(opt ==2) {
			optionPane.showMessageDialog(null, "removed");
		}else if(opt ==3) {
			optionPane.showMessageDialog(null, "added");
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
