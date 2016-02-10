package GameUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Utils {

	public static final int EASY = 5;
	public static final int MEDIUM = 3;
	public static final int HARD = 2;

	public static int FINALSCORE = 0;

	public static final int ELEPHANT = 0;
	public static final int GIRAFFE = 1;
	public static final int HIPPO = 2;
	public static final int MONKEY = 3;
	public static final int PANDA = 4;
	public static final int PARROT = 5;
	public static final int PENGUIN = 6;
	public static final int PIG = 7;
	public static final int RABBIT = 8;
	public static final int SNAKE = 9;

	public static final Color blue = new Color(0, 153, 204);

	public static String background[] = {
			"blue_desert.png", "blue_grass.png", "blue_land.png", "blue_shroom.png", 
			"colored_castle.png", "colored_desert.png", "colored_forest.png", "colored_grass.png", 
			"colored_land.png", "colored_sahara.png", "colored_shroom.png"
	};

	public static BufferedImage elephantImg;
	public static BufferedImage giraffeImg;
	public static BufferedImage hippoImg;
	public static BufferedImage monkeyImg;
	public static BufferedImage pandaImg;
	public static BufferedImage parrotImg;
	public static BufferedImage penguinImg;
	public static BufferedImage pigImg;
	public static BufferedImage rabbitImg;
	public static BufferedImage snakeImg;

	public static BufferedImage elephantImgRound;
	public static BufferedImage giraffeImgRound;
	public static BufferedImage hippoImgRound;
	public static BufferedImage monkeyImgRound;
	public static BufferedImage pandaImgRound;
	public static BufferedImage parrotImgRound;
	public static BufferedImage penguinImgRound;
	public static BufferedImage pigImgRound;
	public static BufferedImage rabbitImgRound;
	public static BufferedImage snakeImgRound;

	public static BufferedImage filerImage;
	public static BufferedImage centerImage;
	public static BufferedImage solvedImage;
	
	public static Sound menumusic;
	public static Sound bgmusic;
	public static Sound click;
	public static Sound right;
	public static Sound wrong;
	public static Sound finish;
	public static Sound over;
	

	public static Font font;

	public static void init() {

		try {
			filerImage = ImageIO.read(Utils.class.getResourceAsStream("/Box/boxGlass.png"));
			centerImage = ImageIO.read(Utils.class.getResourceAsStream("/Box/boxGlassCenter.png"));

			
			elephantImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/elephant.png"));
			giraffeImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/giraffe.png"));
			hippoImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/hippo.png"));
			monkeyImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/monkey.png"));
			pandaImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/panda.png"));
			parrotImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/parrot.png"));
			penguinImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/penguin.png"));
			pigImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/pig.png"));
			rabbitImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/rabbit.png"));
			snakeImg = ImageIO.read(Utils.class.getResourceAsStream("/Box/snake.png"));
			
			
			elephantImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/elephantRound.png"));
			giraffeImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/giraffeRound.png"));
			hippoImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/hippoRound.png"));
			monkeyImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/monkeyRound.png"));
			pandaImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/pandaRound.png"));
			parrotImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/parrotRound.png"));
			penguinImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/penguinRound.png"));
			pigImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/pigRound.png"));
			rabbitImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/rabbitRound.png"));
			snakeImgRound = ImageIO.read(Utils.class.getResourceAsStream("/Box/snakeRound.png"));

			
			font = Font.createFont(Font.TRUETYPE_FONT, Utils.class.getResourceAsStream("/Font/blocks.ttf"));
			
			
			menumusic = new Sound("/Music/menumusic.wav");
			bgmusic = new Sound("/Music/bgmusic.wav");
			click = new Sound("/SFX/click.wav");
			finish = new Sound ("/SFX/finish.wav");
			right = new Sound ("/SFX/right.wav");
			wrong = new Sound ("/SFX/wrong.wav");
			over = new Sound ("/SFX/over.wav");
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
