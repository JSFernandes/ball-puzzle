package game;
import java.awt.geom.Point2D;

import util.Direction;


public class Ball {
	Direction movementDir;
	Point2D pos;
	
	public Ball(Point2D pos) {
		this.pos = pos;
	}
	
	public void updatePos() {
		switch(movementDir) {
			case UP:
				pos.setLocation(pos.getX(), pos.getY() - 1);
				break;
			case DOWN:
				pos.setLocation(pos.getX(), pos.getY() + 1);
				break;
			case LEFT:
				pos.setLocation(pos.getX() - 1, pos.getY());
				break;
			case RIGHT:
				pos.setLocation(pos.getX() + 1, pos.getY());
				break;
			default:
				break;
		}
	}
	
	public void stop() {
		movementDir = null;
	}
	
	public void invertDir() {
		switch(movementDir) {
			case UP:
				movementDir = Direction.DOWN;
				break;
			case DOWN:
				movementDir = Direction.UP;
				break;
			case LEFT:
				movementDir = Direction.RIGHT;
				break;
			case RIGHT:
				movementDir = Direction.LEFT;
				break;
			default:
				break;
		}
	}
	
	public Direction getMovementDir() {
		return movementDir;
	}
	public void setMovementDir(Direction movementDir) {
		this.movementDir = movementDir;
	}
	public Point2D getPos() {
		return pos;
	}
	public void setPos(Point2D pos) {
		this.pos = pos;
	}

}
