package amicity.graphs.android;



import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.graph.SimpleNode;
import amicity.graphs.amicity_android.R;
import amicity.graphs.android.common.Dimension;
import amicity.graphs.android.common.FRLayout;
import amicity.graphs.android.common.Point2D;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
	FRLayout layout;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private ListView drawerList;

	GraphSurfaceView graphSurface;
	
	
	private void selectItem(int position) {

	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		graph = new SimpleGraph();
		
		Node v1 = new SimpleNode("v1");
		Node v2 = new SimpleNode("v2");
		graph.addNode(v1); graph.addNode(v2);
		graph.addEdge(new SimpleEdge(v1, v2, "12"));
		Node v3 = new SimpleNode("v3");
		graph.addNode(v3);
		graph.addEdge(new SimpleEdge(v2, v3, "23"));
		v1 = new SimpleNode("v4");
		graph.addNode(v1);
		graph.addEdge(new SimpleEdge(v3, v1, "34"));
		v1 = new SimpleNode("v5");
		graph.addNode(v1);
		graph.addEdge(new SimpleEdge(v3, v1, "35"));
		v1 = new SimpleNode("v6");
		graph.addNode(v1);
		graph.addEdge(new SimpleEdge(v3, v1, "36"));
		
		layout = new FRLayout(graph, new Dimension(400, 400));
		
		while (layout.done() == false)
			layout.step();
		for (int i = 0; i< 10; i++)
			layout.step();

		
		swapBtn = (Button) findViewById(R.id.buttonswap);
		
		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		String entries[] = { "first", "second", "third" };
		drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, entries));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

 
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
		drawerLayout.setDrawerListener(drawerToggle);
		

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
		while(locker) {
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
		for (Edge edge : graph.getEdges()) {
			Point2D p1 = layout.transform(edge.getFrom());
			Point2D p2 = layout.transform(edge.getTo());
			canvas.drawLine((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY(), paint);
			canvas.drawCircle((float)p1.getX(), (float)p1.getY(), 20, paint);
			canvas.drawCircle((float)p2.getX(), (float)p2.getY(), 20, paint);
		}
		paint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
		for (Node node : graph.getNodes()) {
			Point2D position = layout.transform(node);
			canvas.drawCircle((float)position.getX(), (float)position.getY(), 20, paint);
		}
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
		if(radius>=maxRadius) {
			speed = -baseSpeed;
		}
		else if (radius<=baseRadius) {
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