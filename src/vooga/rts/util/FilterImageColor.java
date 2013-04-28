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
	private static Color[] myPlayerColors = { Color.BLACK, Color.RED, Color.YELLOW,
			Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA };

	public static Image changeImageColor(Image toFilter, int playerID) {
		Image grayImage = Toolkit.getDefaultToolkit()
				.createImage(
						new FilteredImageSource(toFilter.getSource(),
								new GrayFilter()));

		Image grayToColorImage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(grayImage.getSource(),
						new GrayToColorFilter(myPlayerColors[playerID])));
		return grayToColorImage;
	}
}
