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
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import control.ClienteControl;
import control.Observador;

public class ClientScreen extends JFrame implements Observador {

	// PAINEL PRINCIPAL
	private JPanel panelMenuBtn;
	private JButton jbLoginMenu;
	private JButton jbMyAccount;
	private JButton jbLogout;
	private JPanel panelUsers;
	private JPanel panelUsersTitle;
	private JLabel jlStatus;
	private JLabel jlUsers;
	private JLabel jlMyContacts;
	private JPanel panelUsersBotton;
	private JButton jbAddMyList;
	private JButton jbStartChat;
	private JButton jbRemove;

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
	private JLabel jlName;
	private JLabel jlEmailAccount;
	private JLabel jlYearBirth;
	private JLabel jlMyPort;
	private JLabel jlMyIP;
	private JLabel jlServerPort;
	private JLabel jlServerIP;
	private JLabel jlPasswordAccount;
	private JTextField jtxtName;
	private JTextField jtxtEmailAccount;
	private JTextField jtxtBirth;
	private JTextField jtxtMyPort;
	private JTextField jtxtMyIp;
	private JTextField jtxtServerPort;
	private JTextField jtxtServerIP;
	private JTextField jtxtPasswordAccount;
	private JButton jbSave;

	// PAINEL CHAT
	private JFrame frameChat;
	private JPanel panelChatClient;
	private JLabel jlnomeContato;
	private JTextField jtxtChatText;
	private JTextField jtxtChatTextSender;
	private JPanel panelChatButtons;
	private JButton jbSend;
	private JButton jbFile;
	int numer;

	JOptionPane optionPane;
	private JTable chatPrincipal;

	private ClienteControl clienteControl;

	public ClientScreen() {
		setTitle("CLIENT CHAT TABLE");
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {

		clienteControl = ClienteControl.getInstance();
		clienteControl.addObservador(this);
		optionPane = new JOptionPane();

		// LABELS
		jlEmailLogin = new JLabel("E-mail: ");
		jlEmailAccount = new JLabel("E-mail: ");
		jlMyContacts = new JLabel("                              MY CONTACTS");
		jlName = new JLabel("Name: ");
		jlPassword = new JLabel("Password: ");
		jlPasswordAccount = new JLabel("Password: ");
		jlUsers = new JLabel("  USERS");
		jlYearBirth = new JLabel("Year of Birth: ");
		jlMyPort = new JLabel("My Port: ");
		jlMyIP = new JLabel("My IP Address: ");
		jlServerPort = new JLabel("Server Port: ");
		jlServerIP = new JLabel("Server IP: ");
		jlStatus = new JLabel("              Status ");

		// BUTTONS
		jbAddMyList = new JButton("Add to MyContacts");
		jbLogin = new JButton("Login");
		jbLoginMenu = new JButton("Login");
		jbLogout = new JButton("Logout");
		jbMyAccount = new JButton("Register");
		jbSave = new JButton("Save");
		jbSend = new JButton("Send text");
		jbStartChat = new JButton("Start chat");
		jbRemove = new JButton("Remove Contact");
		jbFile = new JButton("Send file");

		// TEXT FIELDS
		jtxtBirth = new JTextField();
		jtxtChatText = new JTextField();
		jtxtEmailLogin = new JTextField();
		jtxtEmailAccount = new JTextField();
		jtxtName = new JTextField();
		jtxtPassword = new JTextField();
		jtxtPasswordAccount = new JTextField();
		jtxtMyPort = new JTextField();
		jtxtMyIp = new JTextField();
		jtxtServerIP = new JTextField();
		jtxtServerPort = new JTextField();
		jtxtChatTextSender = new JTextField();

		// PANES E FRAMES
		panelMenuBtn();
		panelUsers();
		add(panelMenuBtn, BorderLayout.NORTH);
		add(panelUsers, BorderLayout.CENTER);

		panelMyAccount();
		frameMyAccount.add(panelMyAccount, BorderLayout.CENTER);

		panelMyLogin();
		frameLogin.add(panelMyLogin);

		panelChat();
		frameChat.add(panelChatClient, BorderLayout.CENTER);

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
			}
		});

		panelMenuBtn.add(jbMyAccount);
		panelMenuBtn.add(jbLoginMenu);
		panelMenuBtn.add(jbLogout);
	}

	private void panelUsers() {
		panelUsers = new JPanel();
		panelUsers.setLayout(new BorderLayout());

		panelUsersTitle = new JPanel();
		panelUsersTitle.setLayout(new GridLayout(1, 2));
		panelUsersTitle.add(jlStatus);
		panelUsersTitle.add(jlUsers);
		panelUsersTitle.add(jlMyContacts);

		chatPrincipal = new JTable();
		chatPrincipal.setModel(new chatTable());
		for (int x = 0; x < chatPrincipal.getColumnModel().getColumnCount(); x++) {
			chatPrincipal.getColumnModel().getColumn(x).setWidth(300);
			chatPrincipal.getColumnModel().getColumn(x).setMinWidth(100);
			chatPrincipal.getColumnModel().getColumn(x).setMaxWidth(300);
		}
		chatPrincipal.setRowHeight(40);
		chatPrincipal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		chatPrincipal.setShowGrid(true);
		chatPrincipal.setIntercellSpacing(new Dimension(1, 1));
		chatPrincipal.setDefaultRenderer(Object.class, new tabuleiroRenderer());

		chatPrincipal.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					clienteControl.pegarIndexTabuleiro(chatPrincipal.getSelectedRow(),
							chatPrincipal.getSelectedColumn());

					// e.getComponent().getComponentAt(x, y)

					// setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 4));
					// panelBorderSetter("west");
					// controle.pegarIndexRed(redtabuleiro.getSelectedRow(),
					// redtabuleiro.getSelectedColumn());
				}
			}
		});

		panelUsersBotton = new JPanel();
		panelUsersBotton.setLayout(new FlowLayout());
		panelUsersBotton.add(jbAddMyList);
		panelUsersBotton.add(jbStartChat);
		panelUsersBotton.add(jbRemove);

		jbAddMyList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = clienteControl.addContact("");
				optionPane.showMessageDialog(null, s);
			}
		});

		jbStartChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frameChat.setVisible(true);
				// clienteControl.startChat();
			}
		});

		jbRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = clienteControl.removeContact("");
				optionPane.showMessageDialog(null, s);
			}
		});

		panelUsers.add(panelUsersTitle, BorderLayout.NORTH);
		panelUsers.add(chatPrincipal, BorderLayout.CENTER);
		panelUsers.add(panelUsersBotton, BorderLayout.SOUTH);

	}

	private void panelMyAccount() {
		frameMyAccount = new JFrame();
		frameMyAccount.setTitle("MY ACCOUNT");
		frameMyAccount.setSize(400, 400);
		frameMyAccount.setResizable(false);
		frameMyAccount.setLocationRelativeTo(null);

		panelMyAccount = new JPanel();
		panelMyAccount.setLayout(new GridLayout(9, 3));

		panelMyAccount.add(jlName);
		panelMyAccount.add(jtxtName);
		panelMyAccount.add(jlEmailAccount);
		panelMyAccount.add(jtxtEmailAccount);
		panelMyAccount.add(jlYearBirth);
		panelMyAccount.add(jtxtBirth);
		panelMyAccount.add(jlMyPort);
		panelMyAccount.add(jtxtMyPort);
		panelMyAccount.add(jlMyIP);
		panelMyAccount.add(jtxtMyIp);
		panelMyAccount.add(jlServerPort);
		panelMyAccount.add(jtxtServerPort);
		panelMyAccount.add(jlServerIP);
		panelMyAccount.add(jtxtServerIP);
		panelMyAccount.add(jlPasswordAccount);
		panelMyAccount.add(jtxtPasswordAccount);
		panelMyAccount.add(new JLabel());
		panelMyAccount.add(jbSave);

		jbSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = clienteControl.register(jtxtName.getText(), jtxtEmailAccount.getText(), jtxtBirth.getText(),
						jtxtPassword.getText(), jtxtMyPort.getText(), jtxtMyIp.getText(), jtxtServerPort.getText(),
						jtxtServerIP.getText());
				optionPane.showMessageDialog(null, s);
				if (s.equalsIgnoreCase("stored")) {
					jtxtEmailAccount.setText(jtxtEmailAccount.getText());
					jtxtEmailAccount.setEnabled(false);
				}
			}
		});
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
				String s = clienteControl.login(jtxtEmailLogin.getText(), jtxtPassword.getText());
				optionPane.showMessageDialog(null, s);
			}
		});
	}

	private void panelChat() {
		frameChat = new JFrame();
		frameChat.setTitle("CHAT");
		frameChat.setSize(400, 400);
		frameChat.setResizable(false);
		frameChat.setLocationRelativeTo(null);

		panelChatClient = new JPanel();
		panelChatClient.setLayout(new BorderLayout());

		jlnomeContato = new JLabel("INSERIR NOME CONTATO");
		panelChatClient.add(jlnomeContato, BorderLayout.NORTH);

		panelChatClient.add(jtxtChatText, BorderLayout.CENTER);
		panelChatClient.add(jtxtChatTextSender, BorderLayout.SOUTH);

		panelChatButtons = new JPanel();
		panelChatButtons.setLayout(new FlowLayout());
		panelChatButtons.add(jbSend);
		panelChatButtons.add(jbFile);

		panelChatClient.add(panelChatButtons, BorderLayout.SOUTH);

		jbSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clienteControl.sendMessage();

			}
		});

		jbFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clienteControl.sendFile();

			}
		});
	}

	class chatTable extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return 10;
		}

		@Override
		public Object getValueAt(int row, int col) {
			try {
				return clienteControl.getchatTabuleiro(row, col);
			} catch (Exception e) {
				optionPane.showMessageDialog(null, e.toString());
				return null;
			}
		}
	}

	class tabuleiroRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			
			setLabelFor(new JLabel(value.toString()));
			return this;
		}
	}

	@Override
	public void mudouTabuleiro() {
		chatPrincipal.getAutoResizeMode();
		chatPrincipal.getFont();
		chatPrincipal.updateUI();
		chatPrincipal.repaint();
	}

}
