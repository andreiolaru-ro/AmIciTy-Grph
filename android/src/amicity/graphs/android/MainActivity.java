package amicity.graphs.android;



import amicity.graphs.amicity_android.R;
import amicity.graphs.android.common.Dimension;
import amicity.graphs.graph_samples.GraphSampler;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements Runnable {
	private SurfaceHolder holder;
	private boolean locker=true;
	private Thread thread;


	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private ListView drawerList;

	GraphSurfaceView graphSurface;
	GraphView currentGraphView;
	
	
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
		
		currentGraphView = GraphSampler.sample1(new Dimension(400, 400));
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

			currentGraphView.resize(new Dimension(graphSurface.getWidth(), graphSurface.getHeight()));
			currentGraphView.doLayout();
			
			draw(canvas);


			// End of painting to canvas. system will paint with this canvas,to the surface.
			holder.unlockCanvasAndPost(canvas);
		}
	}
	/**This method deals with paint-works. Also will paint something in background*/
	private void draw(Canvas canvas) {
		float scaleFactor = graphSurface.getScaleFactor();
		
		canvas.drawColor(Color.WHITE);
		canvas.save();
		canvas.scale(scaleFactor, scaleFactor);
		currentGraphView.draw(canvas, getResources());
		canvas.restore();
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