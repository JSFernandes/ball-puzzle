package ballpuzzle.logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

import ballpuzzle.gui.Drawable;
import ballpuzzle.util.Direction;


public class Ball implements Drawable {
	protected Direction movement_direction_;
	protected Point position_;
	
	public Ball(Point position) {
		movement_direction_ = null;
		position_ = position;
	}
	
	public void updatePosition() {
		switch(movement_direction_) {
			case UP:
				--position_.y;
				break;
			case DOWN:
				++position_.y;
				break;
			case LEFT:
				--position_.x;
				break;
			case RIGHT:
				++position_.x;
				break;
			default:
				break;
		}
	}
	
	public void stop() {
		movement_direction_ = null;
	}
	
	public boolean isMoving() {
		return movement_direction_ != null;
	}
	
	public void invertDirection() {
		movement_direction_ = movement_direction_.getOpposite();
	}
	
	public Direction getMovementDirection() {
		return movement_direction_;
	}
	
	public void setMovementDirection(Direction movement_direction) {
		movement_direction_ = movement_direction;
	}
	
	public Point getPosition() {
		return position_;
	}
	
	public void setPosition(Point position) {
		position_.x = position.x;
		position_.y = position.y;
	}
	
	private final Color color_ = Color.RED;
	
	@Override
	public void draw(Graphics2D g2d, int x, int y, int offset_x, int offset_y, int size, ImageObserver observer) {
		g2d.setColor(color_);
		g2d.fillOval(position_.x * size + offset_x, position_.y * size + offset_y, size, size);
	}
}
