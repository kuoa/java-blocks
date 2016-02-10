package GameState;

import GameUtils.Background;
import GameUtils.Utils;

public class DiffState extends MenuState {

	public DiffState(GameStateManager gsm) {
		super(gsm);

		// new background
		bg = new Background("/Background/colored_grass.png", 1);
		bg.setPosition(0, 0);
		bg.setVector(-1, 0);

		// new menu
		String newMenu[] = { "EASY", "MEDIUM", "HARD" };
		menu = newMenu;
	}

	@Override
	protected void menuAction(int i) {
		// click on menu action

		gsm.setState(GameStateManager.PLAYSTATE);

		switch (i) {
		case 0:
			gsm.getState(GameStateManager.PLAYSTATE).setDifficulty(Utils.EASY);
			break;
		case 1:
			gsm.getState(GameStateManager.PLAYSTATE).setDifficulty(Utils.MEDIUM);
			break;
		case 2:
			gsm.getState(GameStateManager.PLAYSTATE).setDifficulty(Utils.HARD);
			break;
		}
	}
}
