package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.graph.DefaultWeightedEdge;

import ballpuzzle.util.Direction;

public abstract class PathFinder {

	protected PuzzleGraph graph_;
	protected Heuristic heuristic_;
	
	public abstract Direction[] findSolution(Point start, Point goal);
	
	public PathFinder(PuzzleGraph graph, Heuristic heuristic) {
		graph_ = graph;
		heuristic_ = heuristic;
	}
	
	protected void reconstructPath(Point current_node, HashMap<Point, Point> came_from, ArrayList<Direction> path) {
		if(came_from.get(current_node) != null) {
			DefaultWeightedEdge connecting_edge = graph_.game_graph_.getEdge(came_from.get(current_node), current_node);
			path.add(Direction.getDirByWeight(graph_.game_graph_.getEdgeWeight(connecting_edge)));
			reconstructPath(came_from.get(current_node), came_from, path);
		}
	}
}
