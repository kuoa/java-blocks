package GameLogic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import GameUtils.Utils;
import Main.GamePanel;

public class Box {

	private int x;
	private int y;
	private int type;
		
	private BufferedImage defaultImage;
	private Image defaultImageResized;
	private BufferedImage solvedImage;
	private Image solvedImageResized;
	private Image fillerImage;
	private Image fillerImageBackup;
	
	private boolean selected = false; // if the box is selected
	
	private boolean solved = false; // if the box has been solved

	public Box(int x, int y, int type) {
				
		this.x = x;
		this.y = y;	
		this.type = type;
						
		switch (type) {
		
		case 0:			
			defaultImage = Utils.elephantImg;									
			solvedImage = Utils.elephantImgRound;
			break;

		case 1:
			defaultImage = Utils.giraffeImg;
			solvedImage = Utils.giraffeImgRound;
			break;

		case 2:
			defaultImage = Utils.hippoImg;
			solvedImage= Utils.hippoImgRound;
			break;

		case 3:
			defaultImage = Utils.monkeyImg;
			solvedImage = Utils.monkeyImgRound;
			break;

		case 4:
			defaultImage = Utils.pandaImg;
			solvedImage = Utils.pandaImgRound;
			break;

		case 5:
			defaultImage = Utils.parrotImg;
			solvedImage = Utils.parrotImgRound;
			break;

		case 6:
			defaultImage = Utils.penguinImg;
			solvedImage = Utils.penguinImgRound;
			break;

		case 7:
			defaultImage = Utils.pigImg;
			solvedImage =  Utils.pigImgRound;
			break;

		case 8:
			defaultImage = Utils.rabbitImg;
			solvedImage = Utils.rabbitImgRound;
			break;

		case 9:
			defaultImage = Utils.snakeImg;
			solvedImage = Utils.snakeImgRound;					
			break;

		default:
			// either failed or center block in odd grid
			//System.out.println("Box image failed in switch");
			
			defaultImage = Utils.centerImage;
			fillerImage = Utils.centerImage.getScaledInstance((int)Grid.BOXW, (int)Grid.BOXH, Image.SCALE_SMOOTH);		
			fillerImageBackup = fillerImage;
			solvedImage = Utils.centerImage;			
			break;
		}
		
		fillerImage = Utils.filerImage.getScaledInstance((int)Grid.BOXW, (int)Grid.BOXH, Image.SCALE_SMOOTH);
		fillerImageBackup = fillerImage;
		defaultImageResized = defaultImage.getScaledInstance((int)Grid.BOXW, (int)Grid.BOXW, Image.SCALE_SMOOTH);
		solvedImageResized = solvedImage.getScaledInstance((int)Grid.BOXW, (int)Grid.BOXW, Image.SCALE_SMOOTH);
				
	}
	
	// GETTERS
	
	public int getType(){
		return type;
	}
	
	public boolean isSolved(){
		
		return solved;
	}
	
	public boolean isSelected(){
		
		return selected;
	}
	
	public double getX(){
		// returns the position of the box on the grid in pixels		
		return (x * Grid.BOXW + GamePanel.BORDERSIDE);
	}
	
	public double getY(){
		// returns the position of the box on the grid in pixels
		return (y * Grid.BOXH + GamePanel.BORDERTOP);
	}

	// SETTERS

	public void select() {
		selected = true;
	}
	
	public void deSelect() {
		selected = false;
	}

	public void solved() {
		solved = true;
		defaultImageResized = solvedImageResized;
	}
	
	public void showImage(){
		fillerImage = defaultImageResized;
	}
	
	public void hideImage(){
		fillerImage = fillerImageBackup;
	}
		
	
	
	// RENDER | UPDATE

	public void render(Graphics2D g) {

		if (!selected) {			
			g.drawImage(fillerImage, (int) (x * Grid.BOXW + GamePanel.BORDERSIDE), (int) (y * Grid.BOXH + GamePanel.BORDERTOP),
					(int) Grid.BOXW, (int) Grid.BOXH, null);
						
		} else {
									
			g.drawImage(defaultImageResized, (int) (x * Grid.BOXW + GamePanel.BORDERSIDE), (int) (y * Grid.BOXH + GamePanel.BORDERTOP),
					(int) Grid.BOXW, (int) Grid.BOXH, null);						
		}
	}
}
