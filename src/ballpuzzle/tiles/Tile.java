package ballpuzzle.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ballpuzzle.gui.Drawable;
import ballpuzzle.util.Symbol;

public class Tile implements Drawable {	
	public Symbol getSymbol() {
		return Symbol.TILE;
	}
	
	protected Color color_ = new Color(91, 189, 255);
	
	@Override
	public void draw(Graphics2D g2d, int x, int y, int offset_x, int offset_y, int size,
			ImageObserver observer) {
	}
}
