package ballpuzzle.junit;


import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import ballpuzzle.graph.PuzzleGraph;
import ballpuzzle.logic.Ball;
import ballpuzzle.logic.Board;
import ballpuzzle.tiles.*;
import ballpuzzle.util.*;


public class Tests {
	@Test public void testBall() {
		Ball b = new Ball(new Point(0, 0));
		
		b.setMovementDirection(Direction.UP);
		assertEquals(b.getMovementDirection(), Direction.UP);
		b.updatePosition();
		assertEquals(b.getPosition().getY(), -1, 0);
		assertEquals(b.getPosition().getX(), 0, 0);
		b.invertDirection();
		assertEquals(b.getMovementDirection(), Direction.DOWN);
		b.updatePosition();
		assertEquals(b.getPosition().getY(), 0, 0);
		assertEquals(b.getPosition().getX(), 0, 0);
		
		b.setMovementDirection(Direction.LEFT);
		b.updatePosition();
		assertEquals(b.getPosition().getY(), 0, 0);
		assertEquals(b.getPosition().getX(), -1, 0);
		
		b.setMovementDirection(Direction.RIGHT);
		b.updatePosition();
		assertEquals(b.getPosition().getY(), 0, 0);
		assertEquals(b.getPosition().getX(), 0, 0);
		
		b.stop();
		assertEquals(b.getMovementDirection(), null);
	}
	
	@Test public void testBoard() {
		Tile[][] set =  {{new Tile(), new Tile(), new Tile()},
                        {new Tile(), new Wall(), new Tile()},
                        {new Tile(), new Tile(), new Tile()}};
		Board board = new Board(set, new Point(1, 0));
		
		
		board.setBallMovementDirection(Direction.UP);
		boolean result = board.moveBall();
		assertEquals(result, false);
		Point pos = board.getBallPosition();
		assertEquals(pos.getX(), 1, 0);
		assertEquals(pos.getY(), 2, 0);
		try {
			set[0][0] = new Goal();
			set[0][1] = new Unidirection(Direction.RIGHT);
			set[0][2] = new Unidirection(Direction.UP);
			set[2][1] = new Teleport();
			set[2][0] = new Teleport();
			board = new Board(set, new Point(1, 0));
			result = board.moveBall(Direction.LEFT);
			assertEquals(result, true);
			board.moveBall(Direction.RIGHT);
			pos = board.getBallPosition();
			assertEquals(pos.getX(), 1, 0);
			assertEquals(pos.getY(), 0, 0);
			result = board.moveBall(Direction.UP);
			assertEquals(result, true);
		}
		catch(BallLoopException e) {
			System.out.println("Ball loop");
		}
	}
	
	@Test public void testGraph() {
		Board board = new Board("level_1.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		
		graph.generateGraph();
		assertEquals(graph.game_graph_.vertexSet().size(), 2);
		assertEquals(graph.game_graph_.edgeSet().size(), 1);
	}

}
