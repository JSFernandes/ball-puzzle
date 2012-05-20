package ballpuzzle.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
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
	
	@Override
	public void init() {
		 m_jgAdapter_ = new JGraphModelAdapter<Point, DefaultWeightedEdge>(game_graph_);
		 JGraph jgraph = new JGraph(m_jgAdapter_);
		 
		 adjustDisplaySettings( jgraph );
	     getContentPane(  ).add( jgraph );
	     resize(DEFAULT_SIZE);
	}
	
	public void positionVertexes() {
		System.out.println("cenas");
		Point[] vertexes = game_graph_.vertexSet().toArray(new Point[game_graph_.vertexSet().size()]);
		Point p;
		
		for(int i = 0; i < vertexes.length; ++i) {
			p = vertexes[i];
			System.out.println(p);
			positionVertexAt(p, p.x*55, p.y*35);
		}
	}
	
    @SuppressWarnings("unchecked")
	private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = m_jgAdapter_.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        m_jgAdapter_.edit(cellAttr, null, null, null);
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
	
	public void view() {
		JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("Grafo do jogo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
	}
}
