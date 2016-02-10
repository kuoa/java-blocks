package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class GameState {
	
	public GameStateManager gsm;		
	
	protected abstract void update();
	protected abstract void render(Graphics2D g);	
	
	protected abstract void keyPressed(int k);
	protected abstract void keyReleased(int k);

	protected abstract void mousePressed(MouseEvent e);	
	protected abstract void mouseReleased(MouseEvent e);
	protected abstract void mouseClicked(MouseEvent e);
	
	protected abstract void setDifficulty(int i);	
}
