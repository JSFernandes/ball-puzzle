package ballpuzzle.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;

import ballpuzzle.util.Direction;

public class IterativeDFS extends PathFinder {

	public IterativeDFS(PuzzleGraph graph, Heuristic heuristic) {
		super(graph, heuristic);
	}

	@Override
	public Direction[] findSolution(Point start, Point goal) {
		int depth = 0;
		ArrayList<Direction> result;
		
		while(true) {
			result = Iter(start, goal, depth);
			if(result != null) {
				Collections.reverse(result);
				return (Direction[]) result.toArray(new Direction[result.size()]);
			}
			++depth;
		}
	}

	private ArrayList<Direction> Iter(Point current_point, Point goal, int depth) {
		if(current_point.equals(goal) && depth >= 0) {
			ArrayList<Direction> path = new ArrayList<Direction>();
			Collections.reverse(path);
			return new ArrayList<Direction>();
		}
		else if(depth > 0) {
			Set<DefaultWeightedEdge> neighbour_edges = graph_.game_graph_.outgoingEdgesOf(current_point);
			DefaultWeightedEdge[] neighbour_edge_array = neighbour_edges.toArray(new DefaultWeightedEdge[neighbour_edges.size()]);
			ArrayList<Direction> result;
			Point next_point;
			for(int i = 0; i < neighbour_edge_array.length; ++i) {
				next_point = graph_.game_graph_.getEdgeTarget(neighbour_edge_array[i]);
				result = Iter(next_point, goal, depth-1);
				if(result!= null) {
					DefaultWeightedEdge connector = graph_.game_graph_.getEdge(current_point, next_point);
					Direction new_dir = Direction.getDirByWeight(graph_.game_graph_.getEdgeWeight(connector));
					result.add(new_dir);
					return result;
				}
			}
			return null;
		}
		else
			return null;
	}

}
