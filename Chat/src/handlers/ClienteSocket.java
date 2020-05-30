package handlers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import javax.swing.*;

public class ClienteSocket extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JTextArea texto;
	private JTextField mensagem;
	private JButton send;
	private JButton sair;
	private JLabel mensageml;
	private JPanel contents;
	private Socket socket;
	private OutputStream out;
	private Writer w;
	private BufferedWriter buffw;
	private JTextField ip;
	private JTextField port;
	private JTextField nome;

	public ClienteSocket() throws IOException {
		JLabel lblMessage = new JLabel("Server: ");
		ip = new JTextField("localhost");
		port = new JTextField("Porta do servidor");
		nome = new JTextField("Username");
		Object[] texts = { lblMessage, ip, port, nome };
		JOptionPane.showMessageDialog(null, texts);
		contents = new JPanel();
		texto = new JTextArea(20, 30);
		texto.setEditable(false);
		mensagem = new JTextField(20);
		mensageml = new JLabel("Discordson");
		send = new JButton("Enviar");
		send.setToolTipText("Enviar Mensagem");
		sair = new JButton("Desconectar");
		sair.setToolTipText("Sair do Chat");
		send.addActionListener(this);
		sair.addActionListener(this);
		send.addKeyListener(this);
		mensagem.addKeyListener(this);
		JScrollPane scroll = new JScrollPane(texto);
		texto.setLineWrap(true);
		contents.add(scroll);
		contents.add(mensageml);
		contents.add(mensagem);
		contents.add(sair);
		contents.add(send);
		contents.setBackground(Color.LIGHT_GRAY);
		setTitle(nome.getText());
		setContentPane(contents);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(540, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void conectar() throws IOException {

		socket = new Socket(ip.getText(), Integer.parseInt(port.getText()));
		out = socket.getOutputStream();
		w = new OutputStreamWriter(out);
		buffw = new BufferedWriter(w);
		buffw.write(nome.getText() + "\r\n");
		buffw.flush();
	}

	public void enviarMensagem(String msg) throws IOException {

		if (msg.equals("Sair")) {
			buffw.write("Desconectado \r\n");
			texto.append("Desconectado \r\n");
		} else {
			buffw.write(msg + "\r\n");
			texto.append(nome.getText() + " diz -> " + mensagem.getText() + "\r\n");
		}
		buffw.flush();
		mensagem.setText("");
	}

	public void escutar() throws IOException {

		InputStream in = socket.getInputStream();
		InputStreamReader inr = new InputStreamReader(in);
		BufferedReader bfr = new BufferedReader(inr);
		String msg = "";

		while (!"Sair".equalsIgnoreCase(msg))

			if (bfr.ready()) {
				msg = bfr.readLine();
				if (msg.equals("Sair"))
					texto.append("Servidor caiu! \r\n");
				else
					texto.append(msg + "\r\n");
			}
	}

	public void sair() throws IOException {

		enviarMensagem("Sair");
		buffw.close();
		w.close();
		out.close();
		socket.close();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			if (e.getActionCommand().equals(send.getActionCommand()))
				enviarMensagem(mensagem.getText());
			else if (e.getActionCommand().equals(sair.getActionCommand()))
				sair();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				enviarMensagem(mensagem.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}

	  