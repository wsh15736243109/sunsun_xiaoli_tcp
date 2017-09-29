package com.pobing.uilibs.extend.feature.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class XZoomViewPager extends ViewPager {
	private static final String TAG = "XZoomViewPager";
	
	public XZoomViewPager(Context context) {
		super(context);
	}

	public XZoomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {		//fix event index out of range error
		boolean ret = false;
		try {
			ret = super.onInterceptTouchEvent(arg0);
		} catch (Exception e) {
			
		}
		return ret;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		Log.i(TAG, "onTouchEvent--------");
		return super.onTouchEvent(arg0);
	}
	
	
}
