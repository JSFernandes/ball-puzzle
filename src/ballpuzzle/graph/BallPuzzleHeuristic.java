package ballpuzzle.graph;

import java.awt.Point;

import ballpuzzle.logic.Board;
import ballpuzzle.tiles.Tile;

public class BallPuzzleHeuristic implements Heuristic {

	@Override
	public double func(Point ball_pos, Point goal_pos, Board game_state) {
		if(ball_pos.x == goal_pos.x || ball_pos.y == goal_pos.y)
			return 1;
		else if(game_state.tileAt(ball_pos.x, goal_pos.y).getClass() == Tile.class ||
				game_state.tileAt(goal_pos.x, ball_pos.y).getClass() == Tile.class)
			return 2;
		else
			return 3;
	}

}
