package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import map.Map;
import server.Server;

public class Client {

	private int lengthX = 0;
	private int lengthY = 0;
	private int[][] maze = null;
	private Map m = null;

	private int number;
	private Socket socket = null;
	private static DataInputStream is;
	private DataOutputStream out;

	public Client(int number) {
		this.number = number;
	}

	private void connect() {

		try {
			socket = new Socket(Server.add, Server.PORT);
			is = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.write(number);
			lengthX = is.read();
			lengthY = is.read();

			maze = readMaze(is);
			m = new Map(maze);
		} catch (IOException e) {
			new JOptionPane("Error with the socket Client");

		}
	}

	private int[][] readMaze(DataInputStream is) throws IOException {

		maze = new int[lengthX][lengthY];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				maze[i][j] = is.read();
				System.out.println(i + " " + j);
			}
		}
		return maze;
	}

	public static void main(String[] args) throws IOException {
		Client c = new Client(2112);
		c.connect();

		c.out.writeUTF("CONNECTED");
	}

}
