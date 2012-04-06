package ballpuzzle.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import ballpuzzle.util.Direction;
import ballpuzzle.util.Resource;
import ballpuzzle.util.Symbol;

public class Unidirection extends Tile {
	Direction direction_;
	
	public Unidirection(Direction direction) {
		direction_ = direction;
		
		color_ = new Color(34, 56, 78);
	}

	public Direction getDirection() {
		return direction_;
	}
	
	@Override
	public Symbol getSymbol() {
		switch (direction_) {
			case UP :
				return Symbol.UNIDIRECTION_UP;
			case RIGHT :
				return Symbol.UNIDIRECTION_RIGHT;
			case DOWN :
				return Symbol.UNIDIRECTION_DOWN;
			case LEFT :
				return Symbol.UNIDIRECTION_LEFT;
		}
		
		// TODO: throw an exception
		return null;
	}
	
	private static Image[] sprites_;
	static {
		sprites_ = new Image[4];
		sprites_[Direction.UP.ordinal()] = Resource.loadImage("unidirection-up.png");
		sprites_[Direction.RIGHT.ordinal()] = Resource.loadImage("unidirection-right.png");
		sprites_[Direction.DOWN.ordinal()] = Resource.loadImage("unidirection-down.png");
		sprites_[Direction.LEFT.ordinal()] = Resource.loadImage("unidirection-left.png");
	}
	
	@Override
	public void draw(Graphics2D g2d, int x, int y, int offset_x, int offset_y,
			int size, ImageObserver observer) {
		g2d.drawImage(sprites_[direction_.ordinal()], x * size + offset_x, y * size + offset_y, size, size, observer);

	}
}
