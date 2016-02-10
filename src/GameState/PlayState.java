package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import GameLogic.Box;
import GameLogic.Grid;
import GameUtils.Background;
import GameUtils.CircleAnimation;
import GameUtils.Utils;
import Main.GamePanel;

public class PlayState extends GameState {

	private Grid grid;
	private Background bg;
	private Box selectedBoxes[];

	private int level = 1;
	int maxLevels = 10;
	private int gridSize = level + 2;
	private int types = 10; // types of boxes

	private double levelStart;
	private double levelDelay = 2000;
	private boolean levelStarted = false;

	private int minimumMoves;
	private int allowedMoves;
	private int currentMoves;
	private int difficulty;
	private int score = 0;
	private int streak = 0;

	private double textMessageStart = 0;
	private double textMessageDelay = 1000;

	private boolean right = false;
	private boolean wrong = false;

	private boolean gameOver = false;

	private ArrayList<CircleAnimation> animation; // circle animation

	private String rightText[] = { "Well Done!", "YAAAY!", "Good Job!", "Nice One!", "Lucky you!" };
	private String wrongText[] = { "Too bad!", "Close one!", "Almost!", "Next Time!" };
	private String currentRightText = rightText[0];
	private String currentWrongText = wrongText[0];

	public PlayState(GameStateManager gsm) {

		this.gsm = gsm;

		animation = new ArrayList<CircleAnimation>();

		// init the grid and field
		grid = new Grid(gridSize, gridSize, types);
		grid.init();

		// init selected boxes
		selectedBoxes = new Box[2];

		// background
		bg = new Background("/Background/colored_grass.png", 1);
		bg.setPosition(0, 0);
		bg.setVector(0, 0);

		// show the grid for the first time
		grid.showGrid();
		levelStartAnimation(level);

		// timer for show grid + calibration
		levelStarted = true;	
	}

	protected void update() {

		if (gameOver) {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			gsm.setState(GameStateManager.ENDSTATE);
			return;
		}

		// background update
		bg.update();

		// animation update
		for (int i = 0; i < animation.size(); i++) {

			boolean remove = animation.get(i).update();

			if (remove) {
				animation.remove(i);
				i--;
			}
		}

		// grid show update
		double levelTimeDiff = (System.nanoTime() - levelStart) / 1000000;

		if (levelStarted && levelTimeDiff >= levelDelay) {

			grid.hideGrid();

			levelStart = 0;
			levelTimeDiff = 0;
			levelStarted = false;
			levelStartAnimation(level);
		}
	}

	protected void render(Graphics2D g) {

		// background render
		bg.render(g);

		// top SIDE live text render

		String s = "";
		int fontSize = 40;
		int width = 0;

		g.setFont(Utils.font.deriveFont(Font.PLAIN, fontSize));
		g.setColor(Utils.blue);

		// current level
		s = "Level: " + level;
		g.drawString(s, (int) GamePanel.BORDERSIDE, (int) GamePanel.BORDERSIDE + fontSize / 2);

		s = "Streak: " + streak;
		g.drawString(s, (int) GamePanel.BORDERSIDE, (int) GamePanel.BORDERSIDE + (fontSize / 2) + 30);

		// moves done
		s = "Score: " + score;
		width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();

		g.drawString(s, (int) (GamePanel.WIDTH - GamePanel.BORDERSIDE - width),
				(int) GamePanel.BORDERSIDE + fontSize / 2);

		// moves left
		s = "Moves left: " + (allowedMoves - currentMoves);
		width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		g.drawString(s, (int) (GamePanel.WIDTH - GamePanel.BORDERSIDE - width),
				(int) GamePanel.BORDERSIDE + (fontSize / 2) + 30);

		// WRONG | RIGHT MOVE TEXT
		double textMessageDiff = (System.nanoTime() - textMessageStart) / 1000000;

		if (right) {

			if (textMessageDiff >= textMessageDelay) {

				int i = (int) (Math.random() * rightText.length);
				currentRightText = rightText[i];
				right = false;
				textMessageStart = 0;
				textMessageDiff = 0;

			} else {

				g.setFont(Utils.font.deriveFont(Font.PLAIN, fontSize * 2));

				int alpha = (int) (255 * Math.sin(Math.PI * (textMessageDiff / textMessageDelay)));

				if (alpha > 255) {
					alpha = 255;
				}

				Color colorAlpha = new Color(0, 153, 204, alpha);
				g.setColor(colorAlpha);

				width = (int) g.getFontMetrics().getStringBounds(currentRightText, g).getWidth();
				g.drawString(currentRightText, (GamePanel.WIDTH - width) / 2,
						(int) GamePanel.BORDERSIDE + (fontSize / 2) + 130);
			}

		} else if (wrong) {
			// FINISH WRONG

			if (textMessageDiff >= textMessageDelay) {

				int i = (int) (Math.random() * wrongText.length);
				currentWrongText = wrongText[i];
				right = false;
				textMessageStart = 0;
				textMessageDiff = 0;

			} else {

				g.setFont(Utils.font.deriveFont(Font.PLAIN, fontSize * 2));

				int alpha = (int) (255 * Math.sin(Math.PI * (textMessageDiff / textMessageDelay)));

				if (alpha > 255) {
					alpha = 255;
				}

				Color colorAlpha = new Color(0, 153, 204, alpha);
				g.setColor(colorAlpha);

				width = (int) g.getFontMetrics().getStringBounds(currentWrongText, g).getWidth();
				g.drawString(currentWrongText, (GamePanel.WIDTH - width) / 2,
						(int) GamePanel.BORDERSIDE + (fontSize / 2) + 130);
			}
		}

		// grid render
		grid.render(g);

		// animation render
		for (int i = 0; i < animation.size(); i++) {
			animation.get(i).render(g);
		}

		if (currentMoves >= allowedMoves || level == maxLevels) {

			gameOver = true;
		}
	}

	// SETTERS

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;

		minimumMoves = (gridSize * gridSize) / 2;
		allowedMoves = (int) (minimumMoves * difficulty * 0.8);
		currentMoves = 0;

	}

	// PLAY STATE FUNCTIONS
	public void checkSelectedBoxes() {
		// check if selected boxes are solved;
		Box b0 = selectedBoxes[0];
		Box b1 = selectedBoxes[1];

		if (b0 != null && b1 != null) {

			// sleep to produce the animation
			try {
				Thread.sleep(400);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (b0.getType() == b1.getType()) {

				b0.solved();
				b1.solved();

				int xAnimation = (int) (b0.getX() + Grid.BOXH / 2);
				int yAnimation = (int) (b0.getY() + Grid.BOXW / 2);
				animation.add(
						new CircleAnimation(xAnimation, yAnimation, (int) (Grid.BOXW / 2), (int) (Grid.BOXW / 2) + 40));

				xAnimation = (int) (b1.getX() + Grid.BOXH / 2);
				yAnimation = (int) (b1.getY() + Grid.BOXW / 2);

				animation.add(
						new CircleAnimation(xAnimation, yAnimation, (int) (Grid.BOXW / 2), (int) (Grid.BOXW / 2) + 40));

				right = true;
				wrong = false;
				score += Utils.EASY + (2 * streak) + 2 - difficulty;
				streak++;

				Utils.right.play();
				
				checkLevelFinished();
								
				textMessageStart = System.nanoTime();

			} else {

				b0.deSelect();
				b1.deSelect();

				wrong = true;
				right = false;
				streak = 0;
				
				Utils.wrong.play();

				textMessageStart = System.nanoTime();
			}

			selectedBoxes[0] = null;
			selectedBoxes[1] = null;
		}
	}

	public void checkLevelFinished() {

		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getColumns(); j++) {

				if (!grid.getBox(i, j).isSolved()) {
					return;
				}
			}
		}

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		nextLevel();
	}

	public void nextLevel() {

		level++;
		gridSize = level + 2;

		int i = (int) (Math.random() * Utils.background.length);
		// change background
		bg = new Background("/Background/" + Utils.background[i], 1);
		bg.setPosition(0, 0);
		bg.setVector(0, 0);

		// new grid
		grid = new Grid(gridSize, gridSize, 10);
		grid.init();

		// reset moves
		minimumMoves = (gridSize * gridSize) / 2;
		minimumMoves = (gridSize * gridSize) / 2;
		allowedMoves = minimumMoves * difficulty;
		currentMoves = 0;
						
		// show the grid for the first time
		grid.showGrid();
		// timer for show grid
		levelStart = System.nanoTime();
		
		Utils.finish.play();
		
		// start animation
		levelStartAnimation(level);
		levelStarted = true;

	}

	public void retryLevel() {
		level--;
		nextLevel();
	}

	public void levelStartAnimation(int level) {

		animation.clear();

		int xAnimation = 0;
		int yAnimation = 0;

		if (level <= 6) {

			for (int i = 0; i < grid.getRows(); i++) {
				for (int j = 0; j < grid.getColumns(); j++) {

					Box box = grid.getBox(i, j);

					xAnimation = (int) (box.getX() + Grid.BOXH / 2);
					yAnimation = (int) (box.getY() + Grid.BOXW / 2);
					animation.add(new CircleAnimation(xAnimation, yAnimation, (int) (Grid.BOXW / 2),
							(int) (Grid.BOXW / 2 + 50)));
				}
			}
		} else {

			for (int i = 0; i < grid.getRows(); i += 2) {
				for (int j = 0; j < grid.getColumns(); j += 2) {

					Box box = grid.getBox(i, j);

					xAnimation = (int) (box.getX() + Grid.BOXH / 2);
					yAnimation = (int) (box.getY() + Grid.BOXW / 2);
					animation.add(new CircleAnimation(xAnimation + 10, yAnimation, (int) (Grid.BOXW / 2),
							(int) (Grid.BOXW / 2 + 50)));
				}
			}
		}
	}

	protected void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {

			int x = e.getX();
			int y = e.getY();

			// if we are in the grid area
			if (x >= GamePanel.BORDERSIDE && x <= GamePanel.WIDTH - GamePanel.BORDERSIDE && y >= GamePanel.BORDERTOP
					&& y <= GamePanel.HEIGHT - GamePanel.BORDERBOT) {

				// coordonates of the box in the grid 2x array
				int xRow = (int) ((x - GamePanel.BORDERSIDE) / Grid.BOXW);
				int yColumn = (int) ((y - GamePanel.BORDERTOP) / Grid.BOXH);

				// the box
				Box box = grid.getBox(xRow, yColumn);

				if (!box.isSelected() && !box.isSolved()) {

					box.select();

					if (selectedBoxes[0] == null) {
						selectedBoxes[0] = box;
					} else {
						selectedBoxes[1] = box;

						// check if two same type boxes have been selected
						currentMoves++;
						checkSelectedBoxes();
					}
				}
			}
		}
	}

	protected void keyPressed(int k) {
	}

	protected void keyReleased(int k) {
	}

	protected void mouseReleased(MouseEvent e) {
	}

	protected void mouseClicked(MouseEvent e) {
	}

}
