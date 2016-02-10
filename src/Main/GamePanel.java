package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import GameUtils.Utils;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	public static final double BORDERBOT = (HEIGHT - WIDTH) / 4;
	public static final double BORDERTOP = BORDERBOT * 5;
	public static final double BORDERSIDE = BORDERBOT;
			
	private Thread thread;

	private BufferedImage image;
	private Graphics2D g;
	private boolean running;

	private double averageFPS = 0;
	
	GameStateManager gsm;
	
	public GamePanel() {

		super();

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {

		super.addNotify();

		if (thread == null) {
			addKeyListener(this);
			addMouseListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}

	// initialize relevant variables
	private void init() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		// anti-aliasing graphics
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// anti-aliasing text
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		gsm = new GameStateManager();		
						
		running = true;
	}

	// thread run method
	@Override
	public void run() {

		init();

		int FPS = 60;
		int targetTime = 1000 / FPS; // duration of 1 frame

		long start;
		long elapsed;
		long wait;
		
		// average FPS
		int frameCount = 0;
		int maxFrameCount = FPS;
		double totalTime = 0;
		

		// the game loop

		while (running) {

			start = System.nanoTime();

			update();
			render();
			draw();

			elapsed = (System.nanoTime() - start) / 1000000;
			wait = targetTime - elapsed;

			if (wait < 0) {
				wait = 5;
			}

			try {
				Thread.sleep(wait);
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			
			frameCount++;
			totalTime += System.nanoTime() - start;
			
			if (frameCount == maxFrameCount){
				
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}			
		}
	}

	// updates the game
	private void update() {
		gsm.update();
	}

	// draws the game onto an off-screen buffered image
	private void render() {
				
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// average FPS
		g.setFont(Utils.font.deriveFont(Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		String fpsString = String.format("FPS: %.1f", averageFPS);
		
		g.drawString(fpsString, 10, 20);
		
		
		gsm.render(g);
		
	}

	// draws the off-screen buffered image to the screen
	private void draw() {

		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	
	// used key | mouse events methods
	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}
			
	@Override
	public void mousePressed(MouseEvent e) {	
		gsm.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		gsm.mouseClicked(e);
	}

	
	
	// NOT used key | mouse events methods
		
	@Override
	public void keyTyped(KeyEvent e) {	
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
