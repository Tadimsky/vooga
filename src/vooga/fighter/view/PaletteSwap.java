package vooga.fighter.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import sun.awt.image.ToolkitImage;
import util.Pixmap;
import vooga.fighter.util.Paintable;

public class PaletteSwap {

	public PaletteSwap(Image i) {

	}

	public void setImageToGreyScale(Paintable paintable) {
		BufferedImage buffered = ((ToolkitImage) paintable).getBufferedImage();
		BufferedImage temp = new BufferedImage(buffered.getWidth(),
				buffered.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		temp.getScaledInstance(buffered.getWidth(), buffered.getHeight(), 0);
		Graphics g = temp.getGraphics();
		g.drawImage(buffered, 0, 0, null);
		buffered = temp;
		g.dispose();
		paintable = (Image) buffered;
	}
}