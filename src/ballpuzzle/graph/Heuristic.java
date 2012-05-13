package ballpuzzle.graph;

import java.awt.Point;

import ballpuzzle.logic.Board;

public interface Heuristic {
	
	public double func(Point ball_pos, Point goal_pos, Board game_state);

}
