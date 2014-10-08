package client;

import map.Map;

public class Personnage {

	private String name;
	private double x;
	private double y;
	private boolean die;
	private int life;

	private int i;
	private int j;

	/*
	 * 1 2 3 4
	 */
	private int deplacement;

	public Personnage(String name, int deplacement, int life) {
		this.name = name;
		this.deplacement = deplacement;
		die = false;
		this.life = life;
	}

	public void moveUp() {
		if (!die) {
			if (this.y < Map.PIXEL) {
				this.y = 0;
			} else {
				this.y -= Map.PIXEL;
				j--;
			}
		}
	}

	public void moveRight() {
		if (!die) {

			if (this.x + Map.PIXEL < Map.CELL_X * Map.PIXEL) {
				this.x += Map.PIXEL;
				i++;
			} else {
				this.x = Map.CELL_X * Map.PIXEL;
			}
		}
	}

	public void moveLeft() {
		if (!die) {
			if (this.x < Map.PIXEL) {
				this.x = 0;
			} else {
				this.x -= Map.PIXEL;
				i--;
			}
		}
	}

	public void moveDown() {
		if (!die) {
			if (this.y + Map.PIXEL < Map.CELL_Y * Map.PIXEL) {
				this.y += Map.PIXEL;
				j++;
			} else {
				this.y = Map.CELL_Y * Map.PIXEL;
			}
		}
	}

	public double getx() {
		return x;

	}

	public int geti() {
		return i;
	}

	public double gety() {
		return y;
	}

	public int getj() {
		return j;
	}

	public void setx(int x) {
		this.x = Map.PIXEL * x;
		i = x;
	}

	public void sety(int y) {
		this.y = y * Map.PIXEL;
		j = y;
	}

	public void seti(int i) {
		this.i = i;
	}

	public void setj(int j) {
		this.j = j;
	}

	public void setdeplacement(int d) {
		deplacement = d;
	}

	public int getDeplacement() {
		return deplacement;
	}

	public boolean isDead() {
		return die;
	}

	public void die() {
		die = true;
	}

	public String getName() {
		return name;
	}

	public void res() {
		die = false;
		i = 0;
		j = 0;
		x = 0;
		y = 0;

	}

	public int getLife() {
		return life;
	}

	public void setLife(int k) {
		life = k;
	}

	public void decrementLife() {
		life = life - 1;
	}
}
