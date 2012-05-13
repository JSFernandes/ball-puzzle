package ballpuzzle.graph;

import java.awt.Point;

import ballpuzzle.util.Direction;

public abstract class PathFinder {

	protected PuzzleGraph graph_;
	protected Heuristic heuristic_;
	
	public abstract Direction[] findSolution(Point start, Point goal);
}
