package vooga.rts.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import vooga.rts.resourcemanager.ResourceManager;

public class SpriteEngine {

	//read sprite sheet
	//number 
	public static final String FILEPATH = System.getProperty("user.dir") + "\\bin\\vooga\\rts\\resources\\animation\\crocy\\";
	private Pixmap mySheet;
	private int imageSize_x;
	private int imageSize_y;
	private int myNumAnimation_x;
	private int myNumAnimation_y;
	private Dimension mySize;
	
	
	public SpriteEngine(String fileName, int x_numAnimation, int y_numAnimation, Dimension size) {
		mySheet = new Pixmap(ResourceManager.getInstance()
                .<BufferedImage> getFile("images/mineral.gif", BufferedImage.class));
		mySize = size;
		myNumAnimation_x = x_numAnimation;
		myNumAnimation_y = y_numAnimation;
		divideAnimations();
	}
	
	private void divideAnimations() {
		imageSize_y = (int) (mySize.getHeight()/myNumAnimation_y);
		imageSize_x = (int) (mySize.getWidth()/myNumAnimation_x);
		
		
	}
}
