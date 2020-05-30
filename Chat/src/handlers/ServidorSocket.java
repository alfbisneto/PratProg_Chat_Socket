package handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorSocket extends Thread {

	public static ArrayList<BufferedWriter> clientes;
	public static ServerSocket server;
	private String nome;
	private Socket conn;
	private InputStream in;
	private InputStreamReader inr;
	private BufferedReader bfr;

	public ServidorSocket(Socket conn) {
		this.conn = conn;
		try {
			in = conn.getInputStream();
			inr = new InputStreamReader(in);
			bfr = new BufferedReader(inr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String msg;
			OutputStream out = this.conn.getOutputStream();
			Writer w = new OutputStreamWriter(out);
			BufferedWriter buffw = new BufferedWriter(w);
			clientes.add(buffw);
			nome = msg = bfr.readLine();
			while (!"Disconnect".equalsIgnoreCase(msg) && msg != null) {
				msg = bfr.readLine();
				sendToAll(buffw, msg);
				System.out.println(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
		BufferedWriter bwS;
		for (BufferedWriter bw : clientes) {
			bwS = (BufferedWriter) bw;
			if (!(bwSaida == bwS)) {
				bw.write(nome + " -> " + msg + "\r\n");
				bw.flush();
			}
		}
	}
}
