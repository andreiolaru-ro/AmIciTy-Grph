package amicity.graphs.android;

import amicity.graphs.android.common.Dimension;
import amicity.graphs.android.common.FRLayout;
import amicity.graphs.android.common.Point2D;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;

public class GraphView {
	private Graph graph;
	private Dimension size;
	private FRLayout layout;
	
	public GraphView(Graph graph, Dimension size) {
		this.graph = graph;
		this.size = size;
		layout = new FRLayout(graph, size);
	}
	
	public void doLayout() {
		while (!layout.done())
			layout.step();
	}
	
	public void draw(Canvas canvas, Resources resources) {
		Paint paint = new Paint();
		paint.setColor(resources.getColor(android.R.color.black));
		for (Edge edge : graph.getEdges()) {
			Point2D p1 = layout.transform(edge.getFrom());
			Point2D p2 = layout.transform(edge.getTo());
			canvas.drawLine((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY(), paint);
			canvas.drawCircle((float)p1.getX(), (float)p1.getY(), 20, paint);
			canvas.drawCircle((float)p2.getX(), (float)p2.getY(), 20, paint);
		}
		paint.setColor(resources.getColor(android.R.color.holo_orange_dark));
		for (Node node : graph.getNodes()) {
			Point2D position = layout.transform(node);
			canvas.drawCircle((float)position.getX(), (float)position.getY(), 20, paint);
		}
	}
	
	public void resize(Dimension newSize) {
		this.size = newSize;
	}
	
}
