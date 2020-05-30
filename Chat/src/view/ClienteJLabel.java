package view;

import java.io.IOException;

import handlers.ClienteSocket;

public class ClienteJLabel {
	
	public static void main(String[] args) throws IOException {
		ClienteSocket app = new ClienteSocket();
		app.conectar();
		app.escutar();
	}

}
