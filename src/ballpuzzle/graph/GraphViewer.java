package ballpuzzle.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

public class GraphViewer extends JApplet{
	
    private static final Color     DEFAULT_BG_COLOR = Color.decode( "#FAFBFF" );
    private static final Dimension DEFAULT_SIZE = new Dimension( 530, 320 );

	/**
	 * 
	 */
	private static final long serialVersionUID = -3920305294413705747L;
	
	private ListenableDirectedWeightedGraph<Point, DefaultWeightedEdge> game_graph_;
	private JGraphModelAdapter<Point, DefaultWeightedEdge> m_jgAdapter_;
	
	public GraphViewer(ListenableDirectedWeightedGraph<Point, DefaultWeightedEdge> graph) {
		game_graph_ = graph;
	}
	
	public void init() {
		 m_jgAdapter_ = new JGraphModelAdapter<Point, DefaultWeightedEdge>(game_graph_);

		 JGraph jgraph = new JGraph(m_jgAdapter_);
		 
		 adjustDisplaySettings( jgraph );
	     getContentPane(  ).add( jgraph );
	     resize(DEFAULT_SIZE);
	}
	
	private void adjustDisplaySettings( JGraph jg ) {
        jg.setPreferredSize( DEFAULT_SIZE );

        Color  c        = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter( "bgcolor" );
        }
         catch( Exception e ) {}

        if( colorStr != null ) {
            c = Color.decode( colorStr );
        }

        jg.setBackground( c );
    }
	
	// TODO TROLHICE PARA TESTAR
	public void view() {
		JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("Grafo do jogo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
	}
}
