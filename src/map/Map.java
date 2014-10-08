package map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


//import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import client.Personnage;

public class Map extends JPanel implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private int randomX, randomY;

	//private FireColection colection = new FireColection();

	static int[][] map;

	public final static int PIXEL = 20;

	public static int CELL_X;
	public static int CELL_Y;
	private final int DIMX;
	private final int DIMY;

	private BufferedImage wall;
	private BufferedImage img;
	private BufferedImage orbe;
	private BufferedImage floor;
	private BufferedImage up;
	private BufferedImage down;
	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage wallp;

	private boolean north = true;
	private boolean south = false;
	private boolean east = false;
	private boolean west = false;

	// private Socket s;

	private Personnage perso = new Personnage("", 1, 14);

	public Map(int[][] m) {

		// new Maze(CELL_X, CELL_Y);
		Map.CELL_X = m.length;
		Map.CELL_Y = m[0].length;
		
		this.DIMX = CELL_X * PIXEL;
		this.DIMY = CELL_Y * PIXEL;
		map = m;

		try {
			up = ImageIO.read(new File("down.png"));
			down = ImageIO.read(new File("down.png"));
			left = ImageIO.read(new File("down.png"));
			right = ImageIO.read(new File("down.png"));
			floor = ImageIO.read(new File("floor.png"));
			orbe = ImageIO.read(new File("orbe.png"));
			wall = ImageIO.read(new File("mur.png"));
			wallp = ImageIO.read(new File("murp.png"));
			img = up;

		} catch (IOException e) {
			new JOptionPane("Error can't load the images");
		}

		Timer dataTimer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {

				randomX = (int) Math.floor(Math.random() * CELL_X);
				randomY = (int) Math.floor(Math.random() * CELL_Y);
				if (map[randomX][randomY] == 0) {
					map[randomX][randomY] = 3;
				}
				repaint();

			}
		});

		dataTimer.start();

		JFrame frame = new JFrame("Game");
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(this, BorderLayout.CENTER);
		// final JLabel t = new JLabel("Score - " + score);
		// panel.add(t, BorderLayout.SOUTH);

		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		MenuItem com = new MenuItem("Communication");

		file.add(com);
		menu.add(file);

		frame.setMenuBar(menu);

		Timer time = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// t.setText("Score - " + score);
			}
		});

		time.start();

		frame.add(panel);
		frame.setVisible(true);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(DIMX+100, DIMY + 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {

		if (!perso.isDead()) {
			super.paint(g);
		//	colection.move();

			slidebarlife(g);
		//	drawfire(g);

			g.setColor(Color.white);
			g.fillRect(0, 0, DIMX, DIMY);
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					if (map[i][j] == 1) {
						g.setColor(Color.black);
						g.fillRect(i * PIXEL, j * PIXEL, PIXEL, PIXEL);
						g.drawImage(wall, i * PIXEL, j * PIXEL, null);
					} else if (map[i][j] == 2) {

						perso.setx(i);
						perso.sety(j);

						map[i][j] = 0;
					} else if (map[i][j] == 0) {
						g.drawImage(floor, i * PIXEL, j * PIXEL, null);

					} else if (map[i][j] == 3) {

						g.drawImage(floor, i * PIXEL, j * PIXEL, null);
						g.drawImage(orbe, i * PIXEL, j * PIXEL, null);
					} else if (map[i][j] == 4) {

						g.drawImage(wallp, i * PIXEL, j * PIXEL, null);
					}

				}
			}

			g.drawImage(img, (int) perso.getx(), (int) perso.gety(), null);

		} else {
			g.setColor(Color.black);
			g.fillRect(0, 0, 1400, 1400);
			perso.setx(0);
			perso.setj(0);
			perso.res();
			perso.setLife(14);
			
		}
	}


	private void slidebarlife(Graphics g) {

		if (perso.getLife() == 0) {
			perso.die();
		}
		g.setColor(Color.GREEN);
		if (3 < perso.getLife() && perso.getLife() < 8) {
			g.setColor(Color.orange);
		} else if (perso.getLife() <= 3) {
			g.setColor(Color.red);
		}
		// Controle de la barre de perso.getLife()
		g.fillRect(0, DIMY+20, DIMX * perso.getLife() / 20, 30);

	}

	@Override
	public void keyPressed(KeyEvent kp) {
		actionCommand(kp.getKeyCode());
	}

	public void actionCommand(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			if (goesUP()) perso.moveUp();
			img = up;
			break;
			
		case KeyEvent.VK_DOWN:
			if (goesDOWN()) perso.moveDown();
			img = down;
			break;

		case KeyEvent.VK_LEFT:
			if (goesLEFT()) perso.moveLeft();
			img = left;
			break;

		case KeyEvent.VK_RIGHT:
			if (goesRIGHT()) perso.moveRight();
			img = right;
			break;

		case KeyEvent.VK_E:
			if (east && perso.geti() + 1 < map.length
					&& map[perso.geti() + 1][perso.getj()] == 0) {
				map[perso.geti() + 1][perso.getj()] = 1;
			} else if (west && perso.geti() - 1 != -1
					&& map[perso.geti() - 1][perso.getj()] == 0) {
				map[perso.geti() - 1][perso.getj()] = 1;
			} else if (north && perso.getj() - 1 != -1
					&& map[perso.geti()][perso.getj() - 1] == 0) {
				map[perso.geti()][perso.getj() - 1] = 1;
			} else if (south && perso.getj() + 1 < map.length
					&& map[perso.geti()][perso.getj() + 1] == 0) {
				map[perso.geti()][perso.getj() + 1] = 1;
			} else if (east && perso.geti() + 1 < map.length
					&& map[perso.geti() + 1][perso.getj()] == 1) {
				map[perso.geti() + 1][perso.getj()] = 0;
			} else if (west && perso.geti() - 1 != -1
					&& map[perso.geti() - 1][perso.getj()] == 1) {
				map[perso.geti() - 1][perso.getj()] = 0;
			} else if (north && perso.getj() - 1 != -1
					&& map[perso.geti()][perso.getj() - 1] == 1) {
				map[perso.geti()][perso.getj() - 1] = 0;
			} else if (south && perso.getj() + 1 < map.length
					&& map[perso.geti()][perso.getj() + 1] == 1) {
				map[perso.geti()][perso.getj() + 1] = 0;
			}
			break;

		case KeyEvent.VK_F:
			//colection.add((int) perso.getx(), (int) perso.gety(), 1);
			break;

		default:
			break;
		}
		repaint();
	}

	private boolean goesRIGHT() {
		north = false;
		south = false;
		east = true;
		west = false;
		perso.setdeplacement(3);

		if (perso.geti() + 1 == map.length) {
			return false;
		}
		if (map[perso.geti() + 1][perso.getj()] == 4
				|| map[perso.geti() + 1][perso.getj()] == 1) {
			return false;
		}
		if (map[perso.geti() + 1][perso.getj()] == 3) {
			perso.decrementLife();
			map[perso.geti() + 1][perso.getj()] = 0;
			repaint();
		}

		return true;
	}

	private boolean goesLEFT() {
		north = false;
		south = false;
		east = false;
		west = true;
		perso.setdeplacement(2);

		if (perso.geti() == 0) {
			return false;
		} else if (map[perso.geti() - 1][perso.getj()] == 4
				|| map[perso.geti() - 1][perso.getj()] == 1) {
			return false;
		} else if (map[perso.geti() - 1][perso.getj()] == 3) {
			perso.decrementLife();
			map[perso.geti() - 1][perso.getj()] = 0;
			repaint();
		}

		return true;
	}

	private boolean goesDOWN() {
		north = false;
		south = true;
		east = false;
		west = false;

		perso.setdeplacement(4);

		if (perso.getj() + 1 == map[0].length) {
			return false;
		}
		if (map[perso.geti()][perso.getj() + 1] == 1
				|| map[perso.geti()][perso.getj() + 1] == 4) {
			return false;
		}
		if (map[perso.geti()][perso.getj() + 1] == 3) {
			perso.decrementLife();
			map[perso.geti()][perso.getj() + 1] = 0;
			repaint();
		}

		return true;
	}

	private boolean goesUP() {
		north = true;
		south = false;
		east = false;
		west = false;

		perso.setdeplacement(1);

		if (perso.getj() == 0) {
			return false;
		}
		if (map[perso.geti()][perso.getj() - 1] == 4
				|| map[perso.geti()][perso.getj() - 1] == 1) {
			return false;
		}
		if (map[perso.geti()][perso.getj() - 1] == 3) {
			perso.decrementLife();
			map[perso.geti()][perso.getj() - 1] = 0;
			repaint();
		}
		return true;
	}

	@Override
	public void keyReleased(KeyEvent kp) {
	}

	@Override
	public void keyTyped(KeyEvent kp) {
		actionCommand(kp.getKeyCode());
	}

	public static void main(String[] args) {
		// Map m = new Map();
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		System.out.println(m.getX() + " " + m.getY());
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 1
						&& (i * PIXEL <= m.getX() && m.getX() <= i * PIXEL)
						&& (j * PIXEL <= m.getY() && m.getY() <= j * PIXEL)) {

					map[i][j] = 0;
					System.out.println("Clicker");

				}
			}
		}

		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent m) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent m) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
