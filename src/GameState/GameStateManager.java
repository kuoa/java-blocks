package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import GameUtils.Utils;

public class GameStateManager {

	private GameState gameStates[];
	private int currentState;

	public static final int NBGAMESTATES = 5;

	public static final int MENUSTATE = 0;
	public static final int DIFFSTATE = 1;
	public static final int PLAYSTATE = 2;
	public static final int HELPSTATE = 3;
	public static final int ENDSTATE = 4;

	public GameStateManager() {

		gameStates = new GameState[NBGAMESTATES];
		currentState = MENUSTATE;

		// load images and sound
		Utils.init();

		loadState(currentState);

		Utils.menumusic.loop();
	}

	public GameState getState(int i) {
		return gameStates[i];
	}

	private void loadState(int state) {

		switch (state) {
		case MENUSTATE:
			gameStates[state] = new MenuState(this);
			break;

		case PLAYSTATE:
			gameStates[state] = new PlayState(this);

			Utils.menumusic.stop();
			Utils.bgmusic.loop();
			break;

		case DIFFSTATE:
			gameStates[state] = new DiffState(this);
			break;

		case HELPSTATE:
			gameStates[state] = new HelpState(this);
			break;

		case ENDSTATE:
			Utils.bgmusic.stop();
			Utils.menumusic.loop();
			Utils.over.play();

			gameStates[state] = new EndState(this);
			break;

		default:
			System.out.println("State not recognized");
		}
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(state);
		currentState = state;
		loadState(currentState);
	}

	public void update() {
		gameStates[currentState].update();
	}

	public void render(Graphics2D g) {
		gameStates[currentState].render(g);
	}

	public void quit() {
		System.exit(0);
	}

	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}

	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}

	public void mousePressed(MouseEvent e) {
		Utils.click.play();
		gameStates[currentState].mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		gameStates[currentState].mouseReleased(e);
	}

	public void mouseClicked(MouseEvent e) {
		gameStates[currentState].mouseClicked(e);
	}
}
