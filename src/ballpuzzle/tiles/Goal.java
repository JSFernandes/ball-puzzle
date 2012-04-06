package ballpuzzle.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import ballpuzzle.util.Resource;
import ballpuzzle.util.Symbol;

public class Goal extends Tile {
	public Goal() {
		color_ =  new Color(0, 153, 51);
	}
	
	@Override
	public Symbol getSymbol() {
		return Symbol.GOAL;
	}
	
	private static Image sprite_ = Resource.loadImage("goal.png");
	
	@Override
	public void draw(Graphics2D g2d, int x, int y, int offset_x, int offset_y,
			int size, ImageObserver observer) {
		g2d.drawImage(sprite_, x * size + offset_x, y * size + offset_y, size, size, observer);
	}
}
