package GameUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background {

	private double x;
	private double y;
	private double dx;
	private double dy;
	private double delay;

	private BufferedImage image;

	public Background(String s, double delay) {

		try {

			image = ImageIO.read(getClass().getResourceAsStream(s));
			this.delay = delay;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPosition(double x, double y) {

		// initial position
		this.x = x % GamePanel.WIDTH;
		this.y = y % GamePanel.HEIGHT;
	}

	public void setVector(double dx, double dy) {

		// vector direction
		this.dx = dx * delay;
		this.dy = dy * delay;
	}

	public void setImage(BufferedImage newImage) {
		image = newImage;
	}

	public void update() {

		x = (x + dx) % GamePanel.WIDTH;
		y = (y + dy) % GamePanel.HEIGHT;
	}

	public void render(Graphics2D g) {

		g.drawImage(image, (int) x, (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);

		// draw second image
		if (x < GamePanel.WIDTH) {
			g.drawImage(image, (int) (GamePanel.WIDTH + x), (int) y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		}
	}
}
