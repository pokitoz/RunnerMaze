package server;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Communication extends JFrame implements ActionListener,
		ListSelectionListener {

	/**
	 * 
	 */
	private JPanel chat = new JPanel();
	private JTextArea texte;
	private JTextField message;

	private JList<String> liste;
//	private DefaultListModel dlm;
//	private ArrayList<String> listeName = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	public Communication() {

		setTitle("Communication");
		buildChat();
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, chat);
		buildList();

		add(BorderLayout.EAST, liste);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();

	}

	private void buildList() {
	//	dlm = new DefaultListModel<>();
	//	liste = new JList(dlm);
		liste.addListSelectionListener(this);

	}



	private void buildChat() {

		chat.setLayout(new BorderLayout());
		JPanel messagePanel = new JPanel(new GridLayout(2, 1));
		message = new JTextField();
		JButton send = new JButton("send");
		send.addActionListener(this);
		texte = new JTextArea(" ", 10, 15);

		texte.setWrapStyleWord(true);
		texte.setLineWrap(true);

		JScrollPane scroll = new JScrollPane(texte);

		texte.setEditable(false);

		messagePanel.add(message);
		messagePanel.add(send);

		chat.add(BorderLayout.CENTER, scroll);
		chat.add(BorderLayout.SOUTH, messagePanel);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.equals("send")) {

		}
	}

	public static void main(String[] args) {

		//Communication com = new Communication();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {

	}

}
