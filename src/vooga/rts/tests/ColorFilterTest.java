package vooga.rts.tests;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * test for seeing if color filtering will work
 * 
 * @author Junho Oh
 * 
 */
public class ColorFilterTest {

	private static Color[] myPlayerColors = { Color.BLACK, Color.RED, Color.YELLOW,
			Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.CYAN };
	
	public static void main(String[] args) {
		final String mapUrlPath = "http://starcraft-2-strategy.net/wp-content/uploads/2012/04/siege-tank.jpg";

		try {
			BufferedImage mapImage = ImageIO.read(new URL(mapUrlPath));

			Image grayImage = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(mapImage.getSource(),
							new MyGrayFilter()));
			int R = 255 - (2*50);
			int G = 2*40;
			int B = 50+(2*40);
			Image grayToColorImage = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(grayImage.getSource(),
							new GrayToColorFilter(myPlayerColors[1])));

			ImageIcon mapIcon = new ImageIcon(mapImage);
			ImageIcon newGrayIcon = new ImageIcon(grayImage);

			ImageIcon grayToColorIcon = new ImageIcon(grayToColorImage);

			JPanel imagePanel = new JPanel(new GridLayout(2, 2));
			imagePanel.add(new JLabel(mapIcon));
			imagePanel.add(new JLabel(newGrayIcon));
			imagePanel.add(new JLabel(grayToColorIcon));

			JOptionPane.showMessageDialog(null, imagePanel,
					"Lol image awesomeness", JOptionPane.PLAIN_MESSAGE);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyGrayFilter extends RGBImageFilter {
	public int filterRGB(int x, int y, int argb) {
		int r = (argb & 0x00ff0000) >> 0x10;
		int g = (argb & 0x0000ff00) >> 0x08;
		int b = (argb & 0x000000ff);
		int ave = (r + g + b) / 3;

		return ((argb & 0xff000000) | (ave << 0x10 | ave << 0x08 | ave));
	}
}

class GrayToColorFilter extends RGBImageFilter {
	private Color c;

	public GrayToColorFilter(Color c) {
		this.c = c;
	}

	public int filterRGB(int x, int y, int argb) {
		return (argb | c.getRGB());
	}

}
