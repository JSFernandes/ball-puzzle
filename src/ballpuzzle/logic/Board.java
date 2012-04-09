package ballpuzzle.logic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ballpuzzle.tiles.*;
import ballpuzzle.util.BallLoopException;
import ballpuzzle.util.Direction;
import ballpuzzle.util.Resource;
import ballpuzzle.util.Symbol;

public class Board {
	private String title_;
	private Tile[][] tileset_;
	private Teleport teleport1_, teleport2_;
	private Ball ball_;
	private int size_;

	protected static Tile[][] parseTileset(String[] raw_data, Point ball_position) {
		Tile[][] tileset = new Tile[raw_data.length][];
		String row_data;
		Symbol symbol;
		Tile tile = null;
		for (int row = 0; row < raw_data.length; ++row) {
			row_data = raw_data[row];
			tileset[row] = new Tile[row_data.length()];
			for (int col = 0; col < row_data.length(); ++col) {
				symbol = Symbol.fromChar(row_data.charAt(col));
				
				switch (symbol) {
					case BALL :
						ball_position.setLocation(col, row);
					case TILE :
						tile = new Tile();
						break;
					case WALL :
						tile = new Wall();
						break;
					case GOAL :
						tile = new Goal();
						break;
					case TELEPORT :
						tile = new Teleport();
						break;
					case UNIDIRECTION_DOWN :
						tile = new Unidirection(Direction.DOWN);
						break;
					case UNIDIRECTION_UP :
						tile = new Unidirection(Direction.UP);
						break;
					case UNIDIRECTION_LEFT :
						tile = new Unidirection(Direction.LEFT);
						break;
					case UNIDIRECTION_RIGHT :
						tile = new Unidirection(Direction.RIGHT);
						break;
					default :
						break;
				}
				
				tileset[row][col] = tile;
			}
		}

		return tileset;
	}
	
	public Board(Tile[][] tileset, Point ball_position) {
		tileset_ = tileset;
		size_ = tileset.length;
		ball_ = new Ball(ball_position);
		
		parseTeleports();
	}
	
	public Board(String file_name) {
		load(file_name);
	}
	
	public void load(String file_name) {
		String line;
		ArrayList<String> level_data = new ArrayList<String>();
		try {
			String location = "/ballpuzzle/levels/";
			InputStream stream = Resource.class.getResourceAsStream(location + file_name);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			while ((line = reader.readLine()) != null) {
				level_data.add(line);
			}
			reader.close();
			stream.close();
			
			title_ = level_data.get(0);
			level_data.remove(0);
			Point ball_position = new Point();
			String[] level_data_array = new String[level_data.size()];
			level_data.toArray(level_data_array);
			tileset_ = parseTileset((String[]) level_data_array, ball_position);
			size_ = tileset_.length;
			parseTeleports();

			ball_ = new Ball(ball_position);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public int getSize() {
		return size_;
	}
	
	public Tile tileAt(int x, int y) {
		return tileset_[y][x];
	}
	
	public Ball getBall() {
		return ball_;
	}
	
	public void setBallMovementDirection(Direction movement_direction) {
		ball_.setMovementDirection(movement_direction);
	}
	
	public boolean isEnd(Point point) {
		return tileset_[point.y][point.x].getClass() == Goal.class;
	}
	
	
	public Point getBallPosition() {
		return ball_.getPosition();
	}
	
	public void setBallPosition(Point new_pos) {
		ball_.setPosition(new_pos);
	}
	
	private void updateBallPosition() {
		ball_.updatePosition();
		
		double x = ball_.getPosition().getX();
		double y = ball_.getPosition().getY();
		
		if (x < 0) {
			ball_.getPosition().x = tileset_[0].length - 1;
		} else if (x >= tileset_[0].length) {
			ball_.getPosition().x = 0;
		} else if (y < 0) {
			ball_.getPosition().y = tileset_[0].length - 1;
		} else if (y >= tileset_.length) {
			ball_.getPosition().y = 0;
		}
	}
	
	// Esta função é para user usada no modo gráfico. É o timer que a invoca de x em x tempo
	// para que se desenhe a bola a mover-se
	public boolean moveBall() {
		Point ball_position = ball_.getPosition();
		Tile current_tile;
		
		updateBallPosition();
		
		current_tile = tileset_[ball_position.y][ball_position.x];
		if (current_tile.getClass() == Goal.class) {
			ball_.stop();
			
			return true;
		} else if (current_tile.getClass() == Wall.class || 
				(current_tile.getClass() == Unidirection.class && 
				((Unidirection)current_tile).getDirection() != ball_.getMovementDirection())) {
			ball_.invertDirection();
			updateBallPosition();
			ball_.stop();
		} else if (current_tile.getClass() == Teleport.class) {
			int tel = ((Teleport) current_tile).getId();
			if (tel == 1)
				ball_position.setLocation(teleport2_.getPosition().getX(), teleport2_.getPosition().getY());
			else
				ball_position.setLocation(teleport1_.getPosition().getX(), teleport1_.getPosition().getY());
		}
		
		return false;
	}
	
	// Esta função é usada nos testes e será usada nos algoritmos porque não depende do timer; apenas
	// mexe na lógica.
	public boolean moveBall(Direction movement_direction) throws BallLoopException {
		setBallMovementDirection(movement_direction);
		boolean result = false;
		Point initial_pos = (Point) ball_.getPosition().clone();
		
		while (ball_.isMoving()) {
			result = moveBall();
			if (ball_.getPosition().x == initial_pos.x && ball_.getPosition().y == initial_pos.y)
				throw new BallLoopException();
		}
		
		return result;
	}
	
	private void parseTeleports() {
		boolean foundFirst = false;
		for (int y = 0; y < tileset_.length; ++y) {
			for (int x = 0; x < tileset_[y].length; ++x) {
				if (tileset_[y][x].getClass() == Teleport.class) {
					if (foundFirst) {
						teleport2_ = ((Teleport)tileset_[y][x]);
						teleport2_.setPosition(new Point(x, y));
						teleport2_.setId(2);
						return;
					}
					else {
						teleport1_ = ((Teleport) tileset_[y][x]);
						teleport1_.setPosition(new Point(x, y));
						teleport1_.setId(1);
						foundFirst = true;
					}
				}
			}
		}
	}
	

	public String getTitle() {
		return title_;
	}

}
