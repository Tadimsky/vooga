package vooga.rts.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;


class GrayFilter extends RGBImageFilter {
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
public class FilterImageColor {
	
	
	public static Image changeImageColor (Image toFilter, int playerID) {
		Image grayImage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(toFilter.getSource(),
						new GrayFilter()));
		int R = 255-(playerID*10);
		int G = 150-(playerID*10);
		int B = 200-(playerID*10);
		Image grayToColorImage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(grayImage.getSource(),
						new GrayToColorFilter(new Color(0, 0, 200))));
		return grayToColorImage;
	}
}
