package tiles;

import java.awt.geom.Point2D;

public class Teleport extends Tile {
	
	short id;
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public Point2D getPos() {
		return pos;
	}
	public void setPos(Point2D pos) {
		this.pos = pos;
	}
	Point2D pos;
	
}
