package vooga.rts.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vooga.rts.resourcemanager.ImageLoader;
import vooga.rts.resourcemanager.ResourceManager;

public class SpriteEngine {

	//read sprite sheet
	//This has to change... 
	private BufferedImage mySheet;
	private int imageSize_x;
	private int imageSize_y;
	private int cols;
	private int rows;
	private Dimension mySize;
	private List<BufferedImage> lolTest = new ArrayList<BufferedImage>();
	private Iterator<BufferedImage> imageIt;
	
	
	public SpriteEngine(String fileName, int x_numAnimation, int y_numAnimation) {

		mySheet = ResourceManager.getInstance().<BufferedImage> getFile(fileName, BufferedImage.class);
		
		mySize = new Dimension(mySheet.getWidth(), mySheet.getHeight());
		cols = x_numAnimation;
		rows = y_numAnimation;
		
		divideAnimations();
	}
	
	private void divideAnimations() {
		imageSize_y = (int) (mySize.getHeight()/rows);
		imageSize_x = (int) (mySize.getWidth()/cols);
		
		System.out.println(imageSize_x + " that was x " + imageSize_y);
		
		for (int i = 0; i < rows; i++)
		{
		    for (int j = 0; j < cols; j++)
		    {
		        lolTest.add(mySheet.getSubimage(
		            j * imageSize_x,
		            i * imageSize_y,
		            imageSize_x,
		            imageSize_y
		        ));
		    }
		}
		imageIt = lolTest.iterator();
	}
	private void resetIterator() {
		imageIt = lolTest.iterator();
	}
	public BufferedImage getNextImage() {
		if(!imageIt.hasNext()) {
			resetIterator();
		}
		return imageIt.next();
	}
	public void printStuff() {
		System.out.println("Dimension " + mySize.width + mySize.height);
	}
	
	public static void main(String[] args) {
		ResourceManager.getInstance().registerResourceLoader(new ImageLoader());
		ResourceManager.getInstance().setResourceBase("/vooga/rts/resources/");
		
	}
	
	
	
	
	
}
