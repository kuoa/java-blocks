package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import GameUtils.Background;
import GameUtils.Utils;
import Main.GamePanel;

public class MenuState extends GameState {

	protected Background bg;
	protected String[] menu = { "START", "HELP", "QUIT" };

	private final Color blue = new Color(0, 153, 204);

	private int textSize[][];
	private boolean textPressed[];
	protected boolean canClick[];
	private boolean sizeSaved = false;
	private final int WIDTH = 0;
	private final int HEIGHT = 1;

	// used in menu animation color
	private int l1; // line start
	private int l2; // line end
	private int c1; // first column start
	private int c2; // first column end / second column start
	private int c3;
	private int c4;

	private double textAnimationStart;
	private double textAnimationDelay = 1000;

	public MenuState(GameStateManager gsm) {

		// gamestate
		this.gsm = gsm;

		// background
		bg = new Background("/Background/colored_land.png", 1);
		bg.setPosition(0, 0);
		bg.setVector(-1, 0);

		// text options
		textSize = new int[menu.length][2];
		textPressed = new boolean[menu.length];
		canClick = new boolean[menu.length];

		for (int i = 0; i < canClick.length; i++) {
			canClick[i] = true;
		}
	}

	protected void update() {
		
		bg.update();
	}

	protected void render(Graphics2D g) {
		
		bg.render(g);

		// TITLE
		String s = "BLOCKS";

		g.setFont(Utils.font.deriveFont(Font.PLAIN, 90));

		g.setColor(blue);
		int width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
		g.drawString(s, (GamePanel.WIDTH - width) / 2, GamePanel.HEIGHT / 4);

		// MENU
		g.setFont(Utils.font.deriveFont(Font.PLAIN, 50));

		for (int i = 0; i < menu.length; i++) {

			s = menu[i];
			if (!sizeSaved) {

				// save the text sizes in array
				width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				int height = (int) g.getFontMetrics().getStringBounds(s, g).getHeight();

				textSize[i][WIDTH] = width;
				textSize[i][HEIGHT] = height;

				if (i == menu.length - 1) {

					// save the text bounds
					l1 = (GamePanel.WIDTH - textSize[0][WIDTH]) / 2;
					l2 = l1 + textSize[0][WIDTH];

					c1 = GamePanel.HEIGHT / 3 + textSize[0][HEIGHT] - 50;
					c2 = c1 + 50;
					c3 = c2 + 50;
					c4 = c3 + 50;

					// all text sizes have been saved
					sizeSaved = true;
				}
			}

			// if mouse pressed on text, change color
			if (canClick[i] && textPressed[i]) {

				double textAnimationDiff = (System.nanoTime() - textAnimationStart) / 1000000;
				if (textAnimationDiff >= textAnimationDelay) {

					textPressed[i] = false;
					textAnimationStart = 0;
					textAnimationDiff = 0;

					// click on menu actions
					menuAction(i);
				}

				int alpha = (int) (255 * Math.sin(Math.PI * (textAnimationDiff / textAnimationDelay)));

				if (alpha > 255) {
					alpha = 255;
				}

				Color colorAlpha = new Color(0, 153, 204, alpha);
				g.setColor(colorAlpha);
			}

			g.drawString(menu[i], (int) (GamePanel.WIDTH - textSize[i][WIDTH]) / 2,
					(GamePanel.HEIGHT / 3) + textSize[i][HEIGHT] + 50 * i);

			g.setColor(blue);
		}
	}

	protected void menuAction(int i) {
		// click on menu action

		switch (i) {
		case 0:
			gsm.setState(GameStateManager.DIFFSTATE);
			break;

		case 1:
			gsm.setState(GameStateManager.HELPSTATE);
			break;

		case 2:
			gsm.quit();
			break;
		}
	}

	protected void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {

			int x = e.getX();
			int y = e.getY();

			if (x >= l1 && x <= l2) {

				if (y >= c1 && y < c2) {

					textPressed[0] = true;
					textAnimationStart = System.nanoTime();

				} else if (y >= c2 && y < c3) {

					textPressed[1] = true;
					textAnimationStart = System.nanoTime();

				} else if (y >= c3 && y < c4) {

					textPressed[2] = true;
					textAnimationStart = System.nanoTime();
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

	@Override
	protected void setDifficulty(int i) {
	}
}
