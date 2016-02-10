package GameLogic;

import java.awt.Graphics2D;
import java.util.Random;

import Main.GamePanel;

public class Grid {

	private int rows;
	private int columns;
	private int types; // number of different case types

	public static double BOXW; // box image width
	public static double BOXH; // box image height

	private Box[][] grid;

	public Grid(int rows, int columns, int types) {

		this.rows = rows;
		this.columns = columns;
		this.types = types;

		grid = new Box[rows][columns];
	}

	public void init() {

		int x1, y1;
		int x2, y2;
		int boxType;
		int centerX = -1;
		int centerY = -1;

		BOXW = (GamePanel.WIDTH - 2 * GamePanel.BORDERSIDE) / columns;
		BOXH = BOXW;

		if (rows % 2 != 0) {
			// grid is even X even so center block is not selectable
			centerX = rows / 2;
			centerY = columns / 2;

			Box box = new Box(centerX, centerY, 10);
			box.select();
			box.solved();
			grid[centerX][centerY] = box;
		}

		Random rand = new Random();

		for (int i = 0; i < (rows * columns) / 2; i++) {

			boxType = rand.nextInt(types);

			do {
				x1 = rand.nextInt(rows);
				y1 = rand.nextInt(columns);

			} while ((x1 == centerX && y1 == centerY) || grid[x1][y1] != null);

			do {

				x2 = rand.nextInt(rows);
				y2 = rand.nextInt(columns);

				// the wierd error case was when x1 = x2 and y1 = y2 :D
			} while ((x1 == centerX && y1 == centerY) || (x1 == x2 && y1 == y2) || grid[x2][y2] != null);

			grid[x1][y1] = new Box(x1, y1, boxType);
			grid[x2][y2] = new Box(x2, y2, boxType);
		}
	}

	// GETTERS
	public Box getBox(int x, int y) {
		return grid[x][y];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	// SETTERS

	public void showGrid() {
		// show grid in starting screen

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Box box = grid[i][j];
				box.showImage();
			}
		}
	}

	public void hideGrid() {
		// hide grid after starting screen

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Box box = grid[i][j];
				box.hideImage();
			}
		}
	}

	// RENDER | UPDATE

	public void update() {
	}

	public void render(Graphics2D g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Box box = grid[i][j];
				box.render(g);
			}
		}
	}
}
