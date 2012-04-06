package ballpuzzle.gui;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public interface Drawable {
	public void draw(Graphics2D g2d, int x, int y, int offset_x, int offset_y, int size,
			ImageObserver observer);
}
