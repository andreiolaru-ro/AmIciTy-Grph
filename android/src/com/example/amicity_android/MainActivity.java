package com.example.amicity_android;



import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.Graphs;
import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.graph.SimpleNode;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements Runnable {
	private Button swapBtn;
	private SurfaceHolder holder;
	private boolean locker=true;
	private Thread thread;
	private int radiusBlack, radiusWhite;
	private boolean left = true;
	//physics
	private static final int baseRadius = 10;
	private static final int maxRadius = 50;
	private static final int baseSpeed = 1;
	private int speed = 0;
	Graph graph;

	GraphSurfaceView graphSurface;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		graph = new SimpleGraph();
		graph.addNode(new SimpleNode("gigel"));
		graph.addNode(new SimpleNode("andrei"));
		graph.addEdge(new SimpleEdge(new SimpleNode("Ionut"), new SimpleNode("Andreea"), "test"));
		
		/*
		edu.uci.ics.jung.graph.Graph<Node, Edge> jungGraph = new DirectedSparseMultigraph<Node,Edge>();
		for (Edge edge : graph.getEdges()) {
			jungGraph.addEdge(edge, edge.getFrom(), edge.getTo());
		}
		*/
		
		swapBtn = (Button) findViewById(R.id.buttonswap);

		View surface = (SurfaceView) findViewById(R.id.mysurface);
		RelativeLayout parent = (RelativeLayout) surface.getParent();
		int index = parent.indexOfChild(surface);
		parent.removeView(surface);
		graphSurface = new GraphSurfaceView(this.getApplicationContext());
		RelativeLayout.LayoutParams layoutParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.FILL_PARENT);
		layoutParams.addRule(RelativeLayout.BELOW, R.id.buttonswap);
		parent.addView(graphSurface, index, layoutParams);
		holder = graphSurface.getHolder();


		thread = new Thread(this);
		thread.start();
		swapBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				left = !left;
			}
		});
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(locker){
			//checks if the lockCanvas() method will be success,and if not, will check this statement again
			if(!holder.getSurface().isValid()) {
				continue;
			}
			/** Start editing pixels in this surface.*/
			Canvas canvas = holder.lockCanvas();

			//ALL PAINT-JOB MAKE IN draw(canvas); method.
			float scaleFactor = graphSurface.getScaleFactor();

			
			canvas.drawColor(Color.WHITE);
			canvas.save();
			canvas.scale(scaleFactor, scaleFactor);
			draw(canvas);
			canvas.restore();

			// End of painting to canvas. system will paint with this canvas,to the surface.
			holder.unlockCanvasAndPost(canvas);
		}
	}
	/**This method deals with paint-works. Also will paint something in background*/
	private void draw(Canvas canvas) {


		Paint paint = new Paint();    


		
		calculateRadiuses();
		//paint left circle(black)
		paint.setColor(getResources().getColor(android.R.color.black));
		canvas.drawCircle(canvas.getWidth()/4, canvas.getHeight()/2, radiusBlack, paint);

		//paint right circle(white)
		paint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
		canvas.drawCircle(canvas.getWidth()/4*3, canvas.getHeight()/2, radiusWhite, paint);
	}

	private void calculateRadiuses() {
		// TODO Auto-generated method stub
		if(left){
			updateSpeed(radiusBlack);
			radiusBlack += speed;
			radiusWhite = baseRadius;
		}
		else{
			updateSpeed(radiusWhite);
			radiusWhite += speed;
			radiusBlack = baseRadius;
		}
	}
	/**Change speed according to current radius size.
	 * if, radius is bigger than maxRad the speed became negative otherwise 
	 * if radius is smaller then baseRad speed will positive.
	 * @param radius
	 */
	private void updateSpeed(int radius) {
		// TODO Auto-generated method stub
		if(radius>=maxRadius){
			speed = -baseSpeed;
		}
		else if (radius<=baseRadius){
			speed = baseSpeed;
		}

	}

	@Override
	protected void onPause() {    
		super.onPause();
		pause();
	}

	private void pause() {
		//CLOSE LOCKER FOR run();
		locker = false;
		while(true){
			try {
				//WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
				thread.join();
			} catch (InterruptedException e) {e.printStackTrace();
			}
			break;
		}
		thread = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		resume();    
	}

	private void resume() {
		//RESTART THREAD AND OPEN LOCKER FOR run();
		locker = true;
		thread = new Thread(this);
		thread.start();
	}
} 