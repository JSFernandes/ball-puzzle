package ballpuzzle.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import ballpuzzle.util.Resource;
import ballpuzzle.util.Symbol;

public class Teleport extends Tile {
	int id_;
	Point position_;
	
	public Teleport() {
		color_ = new Color(35, 0, 34);
	}
	
	public int getId() {
		return id_;
	}
	
	public void setId(int id) {
		id_ = id;
	}
	
	public Point getPosition() {
		return position_;
	}
	
	public void setPosition(Point position) {
		position_ = position;
	}
	
	@Override
	public Symbol getSymbol() {
		return Symbol.TELEPORT;
	}
	
	private static Image sprite_ = Resource.loadImage("teleport.png");
	
	@Override
	public void draw(Graphics2D g2d, int x, int y, int offset_x, int offset_y,
			int size, ImageObserver observer) {
		g2d.drawImage(sprite_, x * size + offset_x, y * size + offset_y, size, size, observer);
	}
}
