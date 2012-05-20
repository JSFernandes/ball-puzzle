package ballpuzzle.junit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import org.junit.Test;

import ballpuzzle.graph.AStar;
import ballpuzzle.graph.BFS;
import ballpuzzle.graph.BallPuzzleHeuristic;
import ballpuzzle.graph.DFS;
import ballpuzzle.graph.GreedySearch;
import ballpuzzle.graph.IterativeDFS;
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
		assertNotNull(graph.game_graph_.getEdge(new Point(4, 9), new Point(15, 9)));
	}

	@Test public void testHeuristic() {
		Board board = new Board("level_4.dat");
		
		BallPuzzleHeuristic heuristic = new BallPuzzleHeuristic();
		double h = heuristic.func(board.getBallPosition(), board.getGoal(), board);
		assertEquals(h, 2, 0.0);
		
		board.setBallPosition(new Point(6, 5));
		h = heuristic.func(board.getBallPosition(), board.getGoal(), board);
		assertEquals(h, 1, 0.0);
	}
	
	@Test public void testAStar() {
		
		Board board = new Board("level_7.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		graph.generateGraph();
		
		AStar a = new AStar(graph, new BallPuzzleHeuristic());
		Direction[] solution = a.findSolution(board.getBallPosition(), board.getGoal());
		assertEquals(solution.length, 10);
		
		assertEquals(solution[0], Direction.RIGHT);
		assertEquals(solution[1], Direction.LEFT);
		assertEquals(solution[2], Direction.DOWN);
		assertEquals(solution[3], Direction.LEFT);
		assertEquals(solution[4], Direction.UP);
		assertEquals(solution[5], Direction.RIGHT);
		assertEquals(solution[6], Direction.LEFT);
		assertEquals(solution[7], Direction.DOWN);
		assertEquals(solution[8], Direction.UP);
		assertEquals(solution[9], Direction.RIGHT);
	}
	
	@Test public void testBFS() {
		Board board = new Board("level_4.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		graph.generateGraph();
		
		BFS b = new BFS(graph, new BallPuzzleHeuristic());
		Direction[] solution = b.findSolution(board.getBallPosition(), board.getGoal());
		assertEquals(solution.length, 9);
		
		assertEquals(solution[0], Direction.RIGHT);
		assertEquals(solution[1], Direction.LEFT);
		assertEquals(solution[2], Direction.DOWN);
		assertEquals(solution[3], Direction.UP);
		assertEquals(solution[4], Direction.LEFT);
		assertEquals(solution[5], Direction.RIGHT);
		assertEquals(solution[6], Direction.UP);
		assertEquals(solution[7], Direction.DOWN);
		assertEquals(solution[8], Direction.RIGHT);
	}
	
	@Test public void testIterativeDFS() {
		Board board = new Board("level_4.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		graph.generateGraph();
		
		IterativeDFS b = new IterativeDFS(graph, new BallPuzzleHeuristic());
		Direction[] solution = b.findSolution(board.getBallPosition(), board.getGoal());
		assertEquals(solution.length, 9);
		
		assertEquals(solution[0], Direction.RIGHT);
		assertEquals(solution[1], Direction.LEFT);
		assertEquals(solution[2], Direction.DOWN);
		assertEquals(solution[3], Direction.UP);
		assertEquals(solution[4], Direction.LEFT);
		assertEquals(solution[5], Direction.RIGHT);
		assertEquals(solution[6], Direction.UP);
		assertEquals(solution[7], Direction.DOWN);
		assertEquals(solution[8], Direction.RIGHT);
	}
	
	@Test public void testDFS() {
		Board board = new Board("level_4.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		graph.generateGraph();
		
		DFS b = new DFS(graph, new BallPuzzleHeuristic());
		Direction[] solution = b.findSolution(board.getBallPosition(), board.getGoal());
		System.out.println("DFS solution size: " + solution.length);
	}
	
	@Test public void testGreedy() {
		Board board = new Board("level_4.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		graph.generateGraph();
		
		GreedySearch b = new GreedySearch(graph, new BallPuzzleHeuristic());
		Direction[] solution = b.findSolution(board.getBallPosition(), board.getGoal());
		assertEquals(solution.length, 9);
		
		assertEquals(solution[0], Direction.RIGHT);
		assertEquals(solution[1], Direction.LEFT);
		assertEquals(solution[2], Direction.DOWN);
		assertEquals(solution[3], Direction.UP);
		assertEquals(solution[4], Direction.LEFT);
		assertEquals(solution[5], Direction.RIGHT);
		assertEquals(solution[6], Direction.UP);
		assertEquals(solution[7], Direction.DOWN);
		assertEquals(solution[8], Direction.RIGHT);
	}
	
	public void getSolutionFor(String level) {
		Board board = new Board(level);
		PuzzleGraph graph = new PuzzleGraph(board);
		graph.generateGraph();
		
		AStar a = new AStar(graph, new BallPuzzleHeuristic());
		Direction[] solution = a.findSolution(board.getBallPosition(), board.getGoal());
		
		for(int i = 0; i < solution.length; ++i)
			System.out.println(solution[i]);
	}
	
	@Test public void getSolutions() {
		getSolutionFor("level_30.dat");
	}
	
	@Test public void compareSolutionSizes() {
		String level;
		Board board;
		PuzzleGraph graph;
		BallPuzzleHeuristic h = new BallPuzzleHeuristic();
		int astar_size, dfs_size, bfs_size, greedy_size, iterative_size;
		AStar star;
		DFS dfs;
		BFS bfs;
		GreedySearch greedy;
		IterativeDFS iterative;
		Direction[] solution;
		for(int i = 1; i < 31; ++i) {
			level = "level_"+i+".dat";
			board = new Board(level);
			graph = new PuzzleGraph(board);
			
			star = new AStar(graph, h);
			solution = star.findSolution(board.getBallPosition(), board.getGoal());
			astar_size = solution.length;
			
			dfs = new DFS(graph, h);
			solution = dfs.findSolution(board.getBallPosition(), board.getGoal());
			dfs_size = solution.length;
			
			bfs = new BFS(graph, h);
			solution = bfs.findSolution(board.getBallPosition(), board.getGoal());
			bfs_size = solution.length;
			
			greedy = new GreedySearch(graph, h);
			solution = greedy.findSolution(board.getBallPosition(), board.getGoal());
			greedy_size = solution.length;
			
			iterative = new IterativeDFS(graph, h);
			solution = iterative.findSolution(board.getBallPosition(), board.getGoal());
			iterative_size = solution.length;
			
			System.out.println(astar_size + " " + dfs_size + " " + bfs_size + " " + greedy_size + " " + iterative_size);
		}
	}
	
	@Test public void compareSolutionTimes() {
		String level;
		Board board;
		PuzzleGraph graph;
		BallPuzzleHeuristic h = new BallPuzzleHeuristic();
		long astar_time, dfs_time, bfs_time, greedy_time, iterative_time, start_time;
		AStar star;
		DFS dfs;
		BFS bfs;
		GreedySearch greedy;
		IterativeDFS iterative;
		for(int i = 1; i < 31; ++i) {
			level = "level_"+i+".dat";
			board = new Board(level);
			graph = new PuzzleGraph(board);
			
			star = new AStar(graph, h);
			start_time = System.nanoTime();
			star.findSolution(board.getBallPosition(), board.getGoal());
			astar_time = System.nanoTime() - start_time;
			
			dfs = new DFS(graph, h);
			start_time = System.nanoTime();
			dfs.findSolution(board.getBallPosition(), board.getGoal());
			dfs_time = System.nanoTime() - start_time;
			
			bfs = new BFS(graph, h);
			start_time = System.nanoTime();
			bfs.findSolution(board.getBallPosition(), board.getGoal());
			bfs_time = System.nanoTime() - start_time;
			
			greedy = new GreedySearch(graph, h);
			start_time = System.nanoTime();
			greedy.findSolution(board.getBallPosition(), board.getGoal());
			greedy_time = System.nanoTime() - start_time;
			
			iterative = new IterativeDFS(graph, h);
			start_time = System.nanoTime();
			iterative.findSolution(board.getBallPosition(), board.getGoal());
			iterative_time = System.nanoTime() - start_time;
			
			System.out.println(astar_time + " " + dfs_time + " " + bfs_time + " " + greedy_time + " " + iterative_time);
		}
	}
	
	
}
