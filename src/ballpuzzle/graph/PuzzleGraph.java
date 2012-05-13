package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

import ballpuzzle.logic.Board;
import ballpuzzle.util.BallLoopException;
import ballpuzzle.util.Direction;

public class PuzzleGraph {
	
	private Board game_state_;
	public ListenableDirectedWeightedGraph<Point, DefaultWeightedEdge> game_graph_;
	GraphViewer viewer;
	
	public PuzzleGraph(Board game) {
		game_state_ = game;
		game_graph_ = new ListenableDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		viewer = new GraphViewer (game_graph_);
		viewer.init();
	}
	
	private Point attemptMove(Direction dir) {
		Point ret_point, current_pos;
		current_pos = new Point(game_state_.getBallPosition().x, game_state_.getBallPosition().y);
		try {
			game_state_.moveBall(dir);
			ret_point = new Point(game_state_.getBallPosition().x, game_state_.getBallPosition().y);
			game_state_.setBallPosition(current_pos);
			return ret_point;
		}
		catch(BallLoopException e) {
			game_state_.setBallPosition(current_pos);
			return null;
		}
	}
	
	private void updateGraph(double weight, Point current_pos, ArrayList<Point> visited, Stack<Point> ball_positions) {
		Point new_pos;
		Direction dir = Direction.getDirByWeight(weight);
		new_pos = attemptMove(dir);
		if (new_pos != null) {
			if(!visited.contains(new_pos)) {
				visited.add(new_pos);
				if(!game_state_.isEnd(new_pos)) {
					ball_positions.push(new_pos);
				}
			}
			if(!game_graph_.containsVertex(new_pos)) {
				game_graph_.addVertex(new_pos);
			}
			DefaultWeightedEdge edge = game_graph_.addEdge(current_pos, new_pos);
			game_graph_.setEdgeWeight(edge, weight);
		}
	}
	
	public void generateGraph() {
		Point current_pos;
		Stack<Point> ball_positions = new Stack<Point>();
		ArrayList<Point> visited_points = new ArrayList<Point>();
		current_pos = (Point)game_state_.getBallPosition().clone();
		visited_points.add(current_pos);
		ball_positions.push(current_pos);
		game_graph_.addVertex(current_pos);
		
		while(!ball_positions.empty()) {
			current_pos = ball_positions.pop();
			game_state_.setBallPosition(current_pos);
			
			for(double i = 0.0; i < 4; ++i)
				updateGraph(i, current_pos, visited_points, ball_positions);
		}
	}
	
	public Board getGame() {
		return game_state_;
	}
	
	//TODO TROLHICE PARA TESTAR
	public static void main(String[] args) {
		Board board = new Board("level_4.dat");
		PuzzleGraph graph = new PuzzleGraph(board);
		
		graph.generateGraph();
		graph.viewer.view();
		while(true);
	}
}
