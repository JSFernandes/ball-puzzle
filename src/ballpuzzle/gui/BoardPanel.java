package ballpuzzle.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import ballpuzzle.graph.AStar;
import ballpuzzle.graph.BFS;
import ballpuzzle.graph.BallPuzzleHeuristic;
import ballpuzzle.graph.DFS;
import ballpuzzle.graph.GreedySearch;
import ballpuzzle.graph.IterativeDFS;
import ballpuzzle.graph.PathFinder;
import ballpuzzle.graph.PuzzleGraph;
import ballpuzzle.logic.Ball;
import ballpuzzle.logic.Board;
import ballpuzzle.tiles.Tile;
import ballpuzzle.util.Direction;

public class BoardPanel extends JPanel implements KeyListener, ActionListener, MouseListener {
	private static final long serialVersionUID = 6877539859684016004L;

	private BallPuzzle parent_;
	private Board board_;

	private String[] levels_;
	private int current_level_;
	
	private boolean is_started_;
	private boolean is_paused_;
	
	Direction[] solution_;
	private boolean is_solving_;
	private int solution_step_;
	
	private Timer timer_;
	
	private JLabel status_bar_;
	
	private void initialize(BallPuzzle parent, String[] levels) {
		parent_ = parent;
		
		levels_ = levels;
		current_level_ = 0;
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		
		is_paused_ = false;
		is_started_ = false;
		
		is_solving_ = false;
		
		timer_ = new Timer(100, this);
		timer_.start();
		
		status_bar_ = parent_.getStatusBar();
	}
	
	public BoardPanel(String file_name, BallPuzzle parent) {
		initialize(parent, new String[] {file_name});

		board_ = new Board(levels_[0]);
		
		status_bar_.setText("Level: " + board_.getTitle());
	}
	
	private PathFinder getAlgorithm(String algorithm_name) {
		if(algorithm_name.equals("AStar"))
			return new AStar(new PuzzleGraph(board_), new BallPuzzleHeuristic());
		else if(algorithm_name.equals("BFS"))
			return new BFS(new PuzzleGraph(board_), new BallPuzzleHeuristic());
		else if(algorithm_name.equals("DFS"))
			return new DFS(new PuzzleGraph(board_), new BallPuzzleHeuristic());
		else if(algorithm_name.equals("Greedy"))
			return new GreedySearch(new PuzzleGraph(board_), new BallPuzzleHeuristic());
		else if(algorithm_name.equals("IterativeDFS"))
			return new IterativeDFS(new PuzzleGraph(board_), new BallPuzzleHeuristic());
		else
			return null;
	}
	public BoardPanel(BallPuzzle parent) {
		String[] levels = {
				"level_1.dat",
				"level_2.dat",
				"level_3.dat",
				"level_4.dat",
				"level_5.dat",
				"level_6.dat",
				"level_7.dat",
				"level_8.dat",
				"level_9.dat",
				"level_10.dat",
				"level_11.dat",
				"level_12.dat",
				"level_13.dat",
				"level_14.dat",
				"level_15.dat",
				"level_16.dat",
				"level_17.dat",
				"level_18.dat",
				"level_19.dat",
				"level_20.dat",
				"level_21.dat",
				"level_22.dat",
				"level_23.dat",
				"level_24.dat",
				"level_25.dat",
				"level_26.dat",
				"level_27.dat",
				"level_28.dat",
				"level_29.dat",
				"level_30.dat"
				};
		
		initialize(parent, levels);
		
		board_ = new Board(levels_[0]);
		
		status_bar_.setText("Level: " + board_.getTitle());
	}
	
	public void loadNextLevel() {
		is_solving_ = false;
		++current_level_;
		if (current_level_ == levels_.length) {
			current_level_ = 0;
		}
		
		board_.load(levels_[current_level_]);
		
		status_bar_.setText("Level: " + board_.getTitle());
	}
	
	public void start() {
		if (is_paused_) {
			return;
		}
		
		is_started_ = true;
		
		timer_.start();
	}
	
	public void pause() {
		if (!is_started_) {
			return;
		}
		
		is_paused_ = !is_paused_;
		
		if (is_paused_) {
			timer_.stop();
			status_bar_.setText("Paused");
		} else {
			timer_.start();
			status_bar_.setText("Level: " + board_.getTitle());
		}
		
		repaint();
	}
	
	int getCellSize() {
		return (int) (Math.min(getSize().getHeight(), getSize().getWidth()) / board_.getSize());
	}
	
	int getBoardSize() {
		return board_.getSize();
	}
	
	Tile tileAt(int x, int y) {
		return board_.tileAt(x, y);
	}
	
	Ball getBall() {
		return board_.getBall();
	}
	
	boolean moveBall() {
		return board_.moveBall();
	}
	
	boolean isBallMoving() {
		return getBall().isMoving();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		Dimension size = getSize();
		
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, size.width, size.height);
		
		int tile_size = getCellSize();
		int board_size = getBoardSize();
		
		int offset_x = (int) ((size.getWidth() - tile_size * board_size) / 2.0);
		int offset_y = (int) ((size.getHeight() - tile_size * board_size) / 2.0);
		
		g2d.setBackground(new Color(91, 189, 255));
		g2d.clearRect(offset_x, offset_y, board_size * tile_size, board_size * tile_size);
		
		g2d.setColor(new Color(125, 203, 255));
		for (int i = 0; i < board_size; ++i) {
			g2d.drawLine(0 + offset_x, i * tile_size + offset_y, tile_size * board_size - 1 + offset_x, i * tile_size + offset_y);
			g2d.drawLine(i * tile_size + offset_x, 0 + offset_y, i * tile_size + offset_x, tile_size * board_size - 1 + offset_y);
		}
		
		for (int row = 0; row < board_size; ++row) {
			for (int col = 0; col < board_size; ++col) {
				Tile tile = tileAt(col, row);
				tile.draw(g2d, col, row, offset_x, offset_y, tile_size, this);
			}
			
		}
		

		
		getBall().draw(g2d, -1, -1, offset_x, offset_y, tile_size, this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!is_started_) {
			return;
		}
		
		int key_code = e.getKeyCode();
		
		if (key_code == 'p' || key_code == 'P') {
			pause();
			return;
		}
		
		if (is_paused_ || isBallMoving()) {
			return;
		}
		
		if(is_solving_) {
			getBall().setMovementDirection(solution_[solution_step_]);
			++solution_step_;
			return;
		}
		
		switch (key_code) {
			case KeyEvent.VK_SPACE:
				String algorithm = (String) parent_.getComboBox().getSelectedItem();
				PathFinder completer = getAlgorithm(algorithm);
				solution_ = completer.findSolution(board_.getBallPosition(), board_.getGoal());
				is_solving_ = true;
				solution_step_ = 1;
				getBall().setMovementDirection(solution_[0]);
				break;
			case KeyEvent.VK_R :
				board_.load(levels_[current_level_]);
				repaint();
				break;
			case KeyEvent.VK_LEFT :
				getBall().setMovementDirection(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT :
				getBall().setMovementDirection(Direction.RIGHT);
				break;
			case KeyEvent.VK_UP :
				getBall().setMovementDirection(Direction.UP);
				break;
			case KeyEvent.VK_DOWN :
				getBall().setMovementDirection(Direction.DOWN);
				break;
			default :
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean success = false;
		
		if (isBallMoving()) {
			success = moveBall();
			repaint();
		}
		
		if (success) {
			loadNextLevel();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		grabFocus();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
