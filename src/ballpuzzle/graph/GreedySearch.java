package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

import ballpuzzle.util.Direction;

public class GreedySearch extends PathFinder {

	public GreedySearch(PuzzleGraph graph, Heuristic heuristic) {
		super(graph, heuristic);
		// TODO Auto-generated constructor stub
	}
	
	private int getMinScore(ArrayList<Point> points, HashMap<Point, Double> scores) {
		int min_point = 0;
		Double min_score = Double.MAX_VALUE;
		for(int i = 0; i < points.size(); ++i) {
			if(scores.get(points.get(i)) < min_score) {
				min_point = i;
				min_score = scores.get(points.get(i));
			}
		}
		return min_point;
	}

	@Override
	public Direction[] findSolution(Point start, Point goal) {
		ArrayList<Point> open = new ArrayList<Point>();
		ArrayList<Point> closed = new ArrayList<Point>();
		HashMap<Point, Point> came_from = new HashMap<Point, Point>();
		HashMap<Point, Double> path_costs = new HashMap<Point, Double>();
		open.add(start);
		path_costs.put(start, heuristic_.func(start, goal, graph_.getGame()));
		int best_point;
		Double next_cost;
		Point current_point, next_point;
		Set<DefaultWeightedEdge> neighbour_edges = new LinkedHashSet<DefaultWeightedEdge>();
		DefaultWeightedEdge[] neighbour_edge_array;
		
		while(!open.isEmpty()) {
			best_point = getMinScore(open, path_costs);
			current_point = open.get(best_point);
			open.remove(current_point);
			closed.add(current_point);
			
			if(current_point.equals(goal)) {
				ArrayList<Direction> path = new ArrayList<Direction>();
				reconstructPath(current_point, came_from, path);
				Collections.reverse(path);
				return (Direction[]) path.toArray(new Direction[path.size()]);
			}
			
			neighbour_edges = graph_.game_graph_.outgoingEdgesOf(current_point);
			neighbour_edge_array = neighbour_edges.toArray(new DefaultWeightedEdge[neighbour_edges.size()]);
			
			for(int i = 0; i < neighbour_edge_array.length; ++i) {
				next_point = graph_.game_graph_.getEdgeTarget(neighbour_edge_array[i]);
				next_cost = path_costs.get(current_point) + 1 + heuristic_.func(next_point, goal, graph_.getGame());
				if((!closed.contains(next_point) && !open.contains(next_point)) || next_cost < path_costs.get(next_point)) {
					open.add(next_point);
					came_from.put(next_point, current_point);
					path_costs.put(next_point, next_cost);
				}
			}
			
		}
		return null;
	}

}
