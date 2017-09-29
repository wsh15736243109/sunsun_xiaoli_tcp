package com.pobing.uilibs.feature.callback;

import android.view.MotionEvent;

/**
 */
public interface TouchEventCallback
{
    public void beforeOnTouchEvent(MotionEvent event);

    public void afterOnTouchEvent(MotionEvent event);

    public void beforeDispatchTouchEvent(MotionEvent event);

    public void afterDispatchTouchEvent(MotionEvent event);
    
}
