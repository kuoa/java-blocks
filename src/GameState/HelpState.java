package GameState;

import GameUtils.Background;

public class HelpState extends MenuState {

	public HelpState(GameStateManager gsm) {
		super(gsm);

		// new background
		bg = new Background("/Background/colored_land.png", 1);
		bg.setPosition(0, 0);
		bg.setVector(-1, 0);

		// new menu
		String newMenu[] = { "Click the boxes", "Find two identical images", "Click here to Start" };
		menu = newMenu;
		canClick[0] = false;
		canClick[1] = false;
	}

	@Override
	protected void menuAction(int i) {
		// click on menu action
		switch (i) {
		case 2:
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		}
	}
}
