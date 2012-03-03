package tiles;

import util.Direction;

public class Unidirection extends Tile {
	Direction dir;
	
	public Unidirection(Direction dir) {
		this.dir = dir;
	}

	public Direction getDir() {
		return dir;
	}
}
