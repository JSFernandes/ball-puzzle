package junit;


import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import game.Ball;
import game.Board;

import org.junit.Test;

import tiles.Goal;
import tiles.Teleport;
import tiles.Tile;
import tiles.Unidirection;
import tiles.Wall;

import util.Direction;

public class Tests {
	@Test public void testBall() {
		Ball b = new Ball(new Point2D.Float(0, 0));
		
		b.setMovementDir(Direction.UP);
		assertEquals(b.getMovementDir(), Direction.UP);
		b.updatePos();
		assertEquals(b.getPos().getY(), -1, 0);
		assertEquals(b.getPos().getX(), 0, 0);
		b.invertDir();
		assertEquals(b.getMovementDir(), Direction.DOWN);
		b.updatePos();
		assertEquals(b.getPos().getY(), 0, 0);
		assertEquals(b.getPos().getX(), 0, 0);
		
		b.setMovementDir(Direction.LEFT);
		b.updatePos();
		assertEquals(b.getPos().getY(), 0, 0);
		assertEquals(b.getPos().getX(), -1, 0);
		
		b.setMovementDir(Direction.RIGHT);
		b.updatePos();
		assertEquals(b.getPos().getY(), 0, 0);
		assertEquals(b.getPos().getX(), 0, 0);
		
		b.stop();
		assertEquals(b.getMovementDir(), null);
	}
	
	@Test public void testBoard() {
		Tile[][] set =  {{new Tile(), new Tile(), new Tile()},
                        {new Tile(), new Wall(), new Tile()},
                        {new Tile(), new Tile(), new Tile()}};
		Board board = new Board(set, new Point2D.Float(1, 0));
		
		boolean result = board.moveBall(Direction.UP);
		assertEquals(result, false);
		Point2D pos = board.getBallPos();
		assertEquals(pos.getX(), 1, 0);
		assertEquals(pos.getY(), 2, 0);
		
		set[0][0] = new Goal();
		set[0][1] = new Unidirection(Direction.RIGHT);
		set[0][2] = new Unidirection(Direction.UP);
		set[2][1] = new Teleport();
		set[2][0] = new Teleport();
		board = new Board(set, new Point2D.Float(1, 0));
		result = board.moveBall(Direction.LEFT);
		assertEquals(result, true);
		board.moveBall(Direction.RIGHT);
		pos = board.getBallPos();
		assertEquals(pos.getX(), 1, 0);
		assertEquals(pos.getY(), 0, 0);
		result = board.moveBall(Direction.UP);
		assertEquals(result, true);
	}

}
