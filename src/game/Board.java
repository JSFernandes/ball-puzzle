package game;
import java.awt.geom.Point2D;

import tiles.Goal;
import tiles.Teleport;
import tiles.Tile;
import tiles.Unidirection;
import tiles.Wall;
import util.Direction;


public class Board {
	
	Tile[][] tileset;
	Teleport tel1, tel2;
	Ball ball;
	
	public Board(Tile[][] tileset, Point2D initialBallPos) {
		this.tileset = tileset;
		parseTeleports();
		
		ball = new Ball(initialBallPos);
	}
	
	private void updateBallPos() {
		ball.updatePos();
		double x = ball.getPos().getX();
		double y = ball.getPos().getY();
		if (x < 0)
			ball.getPos().setLocation(tileset[0].length - 1, y);
		else if (x >= tileset[0].length)
			ball.getPos().setLocation(0, y);
		else if (y < 0)
			ball.getPos().setLocation(x, tileset.length - 1);
		else if (y >= tileset.length)
			ball.getPos().setLocation(x, 0);
	}
	
	public boolean moveBall(Direction dir) {
		ball.setMovementDir(dir);
		Point2D ballPos = ball.getPos();
		Tile currentTile;
		while (true) {
			updateBallPos();
			
			currentTile = tileset[(int) ballPos.getY()][(int) ballPos.getX()];
			if (currentTile.getClass() == Goal.class)
				return true;
			
			else if (currentTile.getClass() == Wall.class || 
					(currentTile.getClass() == Unidirection.class && 
					((Unidirection)currentTile).getDir() != ball.getMovementDir()))
				break;
			
			else if (currentTile.getClass() == Teleport.class) {
				short tel = ((Teleport)currentTile).getId();
				if (tel == 1)
					ballPos.setLocation(tel2.getPos().getX(), tel2.getPos().getY());
				else
					ballPos.setLocation(tel1.getPos().getX(), tel1.getPos().getY());
			}
		}
		
		ball.invertDir();
		updateBallPos();
		ball.stop();
		
		return false;
	}

	public Tile[][] getTileset() {
		return tileset;
	}
	
	public Point2D getBallPos() {
		return ball.getPos();
	}
	
	private void parseTeleports() {
		boolean foundFirst = false;
		for (int y = 0; y < tileset.length; ++y) {
			for (int x = 0; x < tileset[y].length; ++x) {
				if (tileset[y][x].getClass() == Teleport.class) {
					if (foundFirst) {
						tel2 = ((Teleport)tileset[y][x]);
						tel2.setPos(new Point2D.Float(x, y));
						tel2.setId((short) 2);
						return;
					}
					else {
						tel1 = ((Teleport)tileset[y][x]);
						tel1.setPos(new Point2D.Float(x, y));
						tel1.setId((short) 1);
						foundFirst = true;
					}
				}
			}
		}
	}
}
