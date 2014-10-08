package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Account {

	private Socket socket;
	private DataInputStream is;
	private DataOutputStream out;

	public Account(Socket socket) {
		this.socket = socket;

		try {
			is = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {

		if (!socket.isClosed()) {
			try {
				socket.shutdownOutput();
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(String message) {

		try {
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String read() {

		try {
			return is.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
