package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import GameUtils.Background;
import GameUtils.Utils;
import Main.GamePanel;

public class EndState extends MenuState {

	public EndState(GameStateManager gsm) {
		super(gsm);

		// new background
		bg = new Background("/Background/blue_desert.png", 1);
		bg.setPosition(0, 0);
		bg.setVector(-1, 0);

		// new menu

		String newMenu[] = { "GAME OVER :(", "SCORE: " + Integer.toString(Utils.FINALSCORE), ">PLAY AGAIN" };
		menu = newMenu;
		canClick[0] = false;
		canClick[1] = false;
	}

	public void render(Graphics2D g) {

		super.render(g);

		String s = "kuoa.github.io";
		int fontSize = 50;

		g.setColor(new Color(106, 121, 165));
		g.setFont(Utils.font.deriveFont(Font.PLAIN, fontSize));
		int width = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();

		g.drawString(s, (int) (GamePanel.WIDTH - width) / 2, (int) (GamePanel.HEIGHT - GamePanel.BORDERSIDE));
	}

	@Override
	protected void menuAction(int i) {
		// click on menu action
		switch (i) {
		case 2:

			gsm.setState(GameStateManager.DIFFSTATE);
			break;
		}
	}
}
