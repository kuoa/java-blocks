package GameUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CircleAnimation {

	// FIELDS
	private double x;
	private double y;

	private int r;
	private int maxR;
	private Color color1;
	private int red;
	private int green;
	private int blue;

	public CircleAnimation(double x, double y, int r, int maxR) {

		this.x = x;
		this.y = y;
		this.r = r;
		this.maxR = maxR;

		color1 = Color.WHITE;
		red = (int) (Math.random() * 255);
		green = (int) (Math.random() * 255);
		blue = (int) (Math.random() * 255);
		
		red = (red + color1.getRed()) / 2;
		green = (green + color1.getGreen()) / 2;
		blue = (blue + color1.getBlue()) / 2;

	}

	public boolean update() {

		r++;
		return (r >= maxR);
	}

	public void render(Graphics2D g) {

		// use alpha it may look coolor (sin r / max R)
		int alpha = (int) (255 * Math.sin(Math.PI * r / maxR));

		if (alpha > 255) {
			alpha = 255;
		}

		color1 = new Color(red, green, blue, alpha);
		
		g.setColor(color1.brighter());
		g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(6));
		g.drawOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));

	}
}