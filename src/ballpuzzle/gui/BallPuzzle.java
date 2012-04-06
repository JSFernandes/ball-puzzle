package ballpuzzle.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class BallPuzzle extends JFrame {
	private static final long serialVersionUID = 8911370464252720977L;

	private JLabel status_bar_;
	
	public BallPuzzle() {
		setLayout(new BorderLayout());
		
		status_bar_ = new JLabel();
		add(status_bar_, BorderLayout.SOUTH);
		
		BoardPanel board_panel = new BoardPanel(this);
		add(board_panel, BorderLayout.CENTER);
		board_panel.start();
		
		setTitle("Ball Puzzle");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JLabel getStatusBar() {
		return status_bar_;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BallPuzzle gui = new BallPuzzle();
				gui.setVisible(true);
			}
		});
	}
}
