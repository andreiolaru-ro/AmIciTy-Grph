package com.example.amicity_android;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;

public class GraphSurfaceView extends SurfaceView {

	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;

	public GraphSurfaceView(Context context) {
		super(context);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Let the ScaleGestureDetector inspect all events.
		mScaleDetector.onTouchEvent(ev);
		return true;
	}

	public float getScaleFactor() {
		return mScaleFactor;
	}

	private class ScaleListener 
	extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();

			// Don't let the object get too small or too large.
			mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

			invalidate();
			return true;
		}
	}

}
