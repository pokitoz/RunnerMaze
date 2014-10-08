package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import map.Maze;

public final class Server {
	public final static int PORT = 4444;
	public final static String add = "localhost";
	
	public static int Cell_X = 8;
	public static int Cell_Y = 16;

	public static void main(String[] args) {

		HashMap<String, Socket> acc = new HashMap<>();

		JFrame frame = null;

		frame = new JFrame("SERVER");
		JTextField text = new JTextField("CONNECTED");
		text.setEditable(false);
		frame.add(text);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();



		Maze maze;
		ServerSocket s = null;
		Socket sc = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		int i = 0;
		try {
			s = new ServerSocket(PORT);
			while (i != 100) {
				maze = new Maze(Cell_X, Cell_Y);
				sc = s.accept();
				dos = new DataOutputStream(sc.getOutputStream());
				dis = new DataInputStream(sc.getInputStream());
				acc.put(String.valueOf(dis.read()), sc);
				dos.write(Cell_X);
				dos.write(Cell_Y);
				dos.flush();
				int[][] mazeTab = maze.getMaze();

				i++;
				sendMaze(mazeTab, dos);

			}

			System.out.println(acc.size());

			for (int j = 0; j < 100; j++) {
				dos = new DataOutputStream(acc.get(String.valueOf(j))
						.getOutputStream());
				dos.writeUTF("OK");
				dos.flush();
			}

		} catch (IOException e) {
			new JOptionPane("Error Server");
		}

	}

	public static void sendMaze(int[][] maze, DataOutputStream dos)
			throws IOException {

		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				dos.write(maze[i][j]);
				dos.flush();
			}
		}
	}

}
