package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

import ballpuzzle.logic.Board;
import ballpuzzle.util.Direction;

public class AStar extends PathFinder {

	
	public AStar(PuzzleGraph graph, Heuristic heuristic) {
		super(graph, heuristic);
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
		Board game = graph_.getGame();
		
		ArrayList<Point> closed_set = new ArrayList<Point>();
		ArrayList<Point> open_set = new ArrayList<Point>();
		HashMap<Point, Point> came_from = new HashMap<Point, Point>();
		
		open_set.add(start);
		
		HashMap<Point, Double> g_score = new HashMap<Point, Double>();
		HashMap<Point, Double> h_score = new HashMap<Point, Double>();
		HashMap<Point, Double> f_score = new HashMap<Point, Double>();
		
		g_score.put(start, 0.0);
		h_score.put(start, heuristic_.func(start, goal, game));
		f_score.put(start, h_score.get(start));
		
		int current_index;
		Point current_point, next_point;
		Set<DefaultWeightedEdge> neighbour_edges = new LinkedHashSet<DefaultWeightedEdge>();
		DefaultWeightedEdge[] neighbour_edge_array;
		boolean update = false;
		while(!open_set.isEmpty()) {
			current_index = getMinScore(open_set, f_score);
			current_point = open_set.get(current_index);
			if(current_point.equals(goal)) {
				ArrayList<Direction> path = new ArrayList<Direction>();
				reconstructPath(current_point, came_from, path);
				Collections.reverse(path);
				return (Direction[]) path.toArray(new Direction[path.size()]);
			}
			
			open_set.remove(current_index);
			closed_set.add(current_point);
			
			neighbour_edges = graph_.game_graph_.outgoingEdgesOf(current_point);
			neighbour_edge_array = neighbour_edges.toArray(new DefaultWeightedEdge[neighbour_edges.size()]);
			for(int i = 0; i < neighbour_edge_array.length; ++i) {
				next_point = graph_.game_graph_.getEdgeTarget(neighbour_edge_array[i]);
				if(!closed_set.contains(next_point)) {
					if(!open_set.contains(next_point)) {
						open_set.add(next_point);
						h_score.put(next_point, heuristic_.func(next_point, goal, game));
						update = true;
					}
					else if(g_score.get(current_point) + 1 < g_score.get(next_point))
						update = true;
					else
						update = false;
					
					if(update) {
						came_from.put(next_point, current_point);
						g_score.put(next_point, g_score.get(current_point)+1);
						f_score.put(next_point, g_score.get(next_point) + h_score.get(next_point));
					}
				}
			}
		}
		
		return null;
	}

}
