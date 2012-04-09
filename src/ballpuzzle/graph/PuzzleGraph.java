package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import ballpuzzle.logic.Board;
import ballpuzzle.util.BallLoopException;
import ballpuzzle.util.Direction;

public class PuzzleGraph {
	
	private Board game_state_;
	public DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge> game_graph_;
	
	public PuzzleGraph(Board game) {
		game_state_ = game;
		game_graph_ = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	private Point attemptMove(Direction dir) {
		Point ret_point, current_pos;
		current_pos = game_state_.getBallPosition();
		try {
			game_state_.moveBall(dir);
			ret_point = game_state_.getBallPosition();
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
		if (new_pos != null && !visited.contains(new_pos)) {
			visited.add((Point) new_pos.clone());
			if(!game_state_.isEnd(new_pos)) {
				ball_positions.push((Point) new_pos.clone());
			}
			if(!game_graph_.containsVertex(new_pos)) {
				game_graph_.addVertex((Point) new_pos.clone());
			}
			DefaultWeightedEdge edge = game_graph_.addEdge(current_pos, new_pos);
			game_graph_.setEdgeWeight(edge, weight);
		}
	}
	
	public void generateGraph() {
		Point current_pos;
		Stack<Point> ball_positions = new Stack<Point>();
		ArrayList<Point> visited_points = new ArrayList<Point>();
		visited_points.add((Point) game_state_.getBallPosition().clone());
		ball_positions.push((Point) game_state_.getBallPosition().clone());
		game_graph_.addVertex((Point) game_state_.getBallPosition().clone());
		
		while(!ball_positions.empty()) {
			current_pos = ball_positions.pop();
			game_state_.setBallPosition(current_pos);
			
			for(double i = 0; i < 4; ++i)
				updateGraph(i, current_pos, visited_points, ball_positions);
		}
	}
}
