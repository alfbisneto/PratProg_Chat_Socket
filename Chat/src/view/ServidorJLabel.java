package view;

import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import handlers.ServidorSocket;

public class ServidorJLabel {
	
	public static void main(String[] args) {
		try {
			JLabel lblMessage = new JLabel("Porta do Servidor:");
			JTextField port = new JTextField("Ex: 105");
			Object[] texts = { lblMessage, port };
			JOptionPane.showMessageDialog(null, texts);
			ServidorSocket.server = new ServerSocket(Integer.parseInt(port.getText()));
			ServidorSocket.clientes = new ArrayList<BufferedWriter>();
			JOptionPane.showMessageDialog(null, "Novo servidor: " + port.getText());
			while (true) {
				System.out.println("Idle..." + "Port: " +  port.getText());
				Socket conn = ServidorSocket.server.accept();
				System.out.println("Conectando...");
				Thread t = new ServidorSocket(conn);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
