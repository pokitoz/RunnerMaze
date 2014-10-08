package map;
import java.awt.Point;
import java.util.Stack;

public class Maze {

	private int[][] maze;
	private int x;
	private int y;

	public Maze(int lengthX, int lengthY) {
		x = lengthX;
		y = lengthY;
		maze = generateMaze();
	}

	public int[][] getMaze() {
		return maze;
	}

	private int[][] generateMaze() {

		Stack<Point> u = new Stack<Point>();
		Stack<Point> v = new Stack<Point>();

		int[][] maze = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				maze[i][j] = 4; // Wall 
			}
		}

	
		maze[0][0] = 0; // Start by picking a cell
		u.add(new Point(0, 0));
		v.addAll(findWall(new Point(0, 0), u));
		Point pos = null;
		Point lastPos = new Point(0, 0);
		
		int random = 0;
	
		int x = 0;
		
		// Terminer la boucle une fois toutes les cases visitées.
		while (!v.isEmpty()) {
			x = v.size();
			random = (int) (Math.random() * x);
			pos = v.remove(random);

			if (!u.contains(pos) && notNear(pos, lastPos, u)) {
				// Mark position
				u.add(pos);
				maze[pos.x][pos.y] = 0;
				// Trouver tout les voisins de pos. Les ajouter au stack sous
				// conditions.
				v.addAll(findWall(pos, u));
				lastPos = new Point(pos.x, pos.y);
			}
		}
		
		/* Deuxieme méthode de création*/
		
		// Un set NotVisited contenant les cases non visitées
		// Un set Discovered contenant les cases visités mais non découvertes 
		// Un set NewFromLast contenant les cases que l'nt peut visitées depuis la derniere position
		// On séléctionne aléatoirement une case dans le set NewFromLast, on la supprime du set NotVisited
		//	on note la case comme last position. Et on recommence. Une fois qu'on est bloqué, on séléctionne une case
		// dans le set discovered et on recommence.
		
//		Stack<Point> w = new Stack<Point>();
//		while (!v.isEmpty()) {
//				w=findWall(lastPos, new Stack<Point>());
//				x = w.size();
//				random = (int) (Math.random() * x);
//				pos=w.remove(random);
//				
//			if (!u.contains(pos) && notNear(pos, lastPos, u)) {
//				// Mark position
//				u.add(pos);
//				maze[pos.x][pos.y] = 0;
//				// Trouver tout les voisins de pos. Les ajouter au stack sous
//				// conditions.
//				w=findWall(pos, new Stack<Point>());
//				lastPos = new Point(pos.x, pos.y);
//			}
//		}
//		
		
		return maze;
	}


	/**
	 * 
	 * @param pos
	 * @param lastpos
	 * @param u
	 * @return 
	 * check if the position can create a circle.
	 */
	private boolean notNear(Point pos, Point lastpos, Stack<Point> u) {
		Point north = new Point(pos.x, pos.y - 1);
		Point east = new Point(pos.x + 1, pos.y);
		Point west = new Point(pos.x - 1, pos.y);
		Point south = new Point(pos.x, pos.y + 1);

		if (u.contains(east)) {
			return !(u.contains(west) || u.contains(north) || u.contains(south));
		} else if (u.contains(west)) {
			return !(u.contains(east) || u.contains(north) || u.contains(south));
		} else if (u.contains(south)) {
			return !(u.contains(west) || u.contains(north) || u.contains(east));
		} else if (u.contains(north)) {
			return !(u.contains(west) || u.contains(east) || u.contains(south));
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param position
	 * @param positionSet
	 * @return all the position where it's possibe to go
	 */
	private Stack<Point> findWall(Point position, Stack<Point> positionSet) {
		Stack<Point> wallSet = new Stack<Point>();

		if (position.x == 0) {
			
			/*			OX**
			 *			X***
			 * 			****
			 */
			if (position.y == 0) {
				if (!positionSet.contains(new Point(1, 0)))
					wallSet.add(new Point(1, 0));
				if (!positionSet.contains(new Point(0, 1)))
					wallSet.add(new Point(0, 1));
				
			/*			****
			 * 			X***
			 * 			OX**
			 */
			} else if (position.y == y - 1) {
				if (!positionSet.contains(new Point(1, y - 1)))
					wallSet.add(new Point(1, y - 1));
				if (!positionSet.contains(new Point(0, y - 2)))
					wallSet.add(new Point(0, y - 2));
				
			/*			X****
			* 			OX***
			* 			X****
			*/
			} else {
				if (!positionSet.contains(new Point(0, position.y - 1)))
					wallSet.add(new Point(0, position.y - 1));
				if (!positionSet.contains(new Point(0, position.y + 1)))
					wallSet.add(new Point(0, position.y + 1));
				if (!positionSet.contains(new Point(1, position.y)))
					wallSet.add(new Point(1, position.y));
			}
			return wallSet;

		} else if (position.x == x - 1) {
			
			/*			****
			 * 			***X
			 * 			**XO
			 */
			if (position.y == y - 1) {
				if (!positionSet.contains(new Point(x - 2, y - 1)))
					wallSet.add(new Point(x - 2, y - 1));
				if (!positionSet.contains(new Point(x - 1, y - 2)))
					wallSet.add(new Point(x - 1, y - 2));
			
			/*			**XO
			* 			***X
			* 			****
			*/
			} else if (position.y == 0) {
				if (!positionSet.contains(new Point(x - 2, 0)))
					wallSet.add(new Point(x - 2, 0));
				if (!positionSet.contains(new Point(x - 1, 1)))
					wallSet.add(new Point(x - 1, 1));
				
			/*			***X
			* 			**XO
			* 			***X
			*/
			} else {
				if (!positionSet.contains(new Point(x - 1, position.y - 1)))
					wallSet.add(new Point(x - 1, position.y - 1));
				if (!positionSet.contains(new Point(x - 1, position.y + 1)))
					wallSet.add(new Point(x - 1, position.y + 1));
				if (!positionSet.contains(new Point(x - 2, position.y)))
					wallSet.add(new Point(x - 2, position.y));
			}
			return wallSet;

		} else if (position.y == y - 1) {
			
			/*			****
			 * 			**X**
			 * 			*XOX*
			 */
			if (!positionSet.contains(new Point(position.x - 1, position.y)))
				wallSet.add(new Point(position.x - 1, position.y));
			if (!positionSet.contains(new Point(position.x + 1, position.y)))
				wallSet.add(new Point(position.x + 1, position.y));
			if (!positionSet.contains(new Point(position.x, position.y - 1)))
				wallSet.add(new Point(position.x, position.y - 1));

			return wallSet;

			
			/*			*XOX*
			 *			**X**
			 * 			*****
			 */
		} else if (position.y == 0) {

			if (!positionSet.contains(new Point(position.x - 1, 0)))
				wallSet.add(new Point(position.x - 1, 0));
			if (!positionSet.contains(new Point(position.x + 1, 0)))
				wallSet.add(new Point(position.x + 1, 0));
			if (!positionSet.contains(new Point(position.x, 1)))
				wallSet.add(new Point(position.x, 1));

			return wallSet;

			
			
			/*			**X**
			 * 			*XOX*
			 * 			**X**
			 */
		} else {
			if (!positionSet.contains(new Point(position.x + 1, position.y)))
				wallSet.add(new Point(position.x + 1, position.y));
			if (!positionSet.contains(new Point(position.x - 1, position.y)))
				wallSet.add(new Point(position.x - 1, position.y));
			if (!positionSet.contains(new Point(position.x, position.y + 1)))
				wallSet.add(new Point(position.x, position.y + 1));
			if (!positionSet.contains(new Point(position.x, position.y - 1)))
				wallSet.add(new Point(position.x, position.y - 1));

			return wallSet;
		}

	}
}
