package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.jgrapht.graph.DefaultWeightedEdge;

import ballpuzzle.util.Direction;

public class BFS extends PathFinder {

	public BFS(PuzzleGraph graph, Heuristic heuristic) {
		super(graph, heuristic);
	}

	@Override
	public Direction[] findSolution(Point start, Point goal) {
		HashMap<Point, Integer> paths = new HashMap<Point, Integer>();
		HashMap<Point, Point> came_from = new HashMap<Point, Point>();
		Queue<Point> next_points = new LinkedBlockingQueue<Point>();
		Point current_point, next_point;
		Set<DefaultWeightedEdge> neighbour_edges = new LinkedHashSet<DefaultWeightedEdge>();
		DefaultWeightedEdge[] neighbour_edge_array;
		
		paths.put(start, 0);
		next_points.add(start);
		
		while(next_points.peek() != null) {
			current_point = next_points.poll();
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
				if(paths.get(next_point) == null || paths.get(next_point) > paths.get(current_point)+1) {
					next_points.add(next_point);
					paths.put(next_point, paths.get(current_point)+1);
					came_from.put(next_point, current_point);
				}
			}
		}
		return null;
	}

}
