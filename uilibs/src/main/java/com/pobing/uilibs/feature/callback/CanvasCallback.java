package com.pobing.uilibs.feature.callback;

import android.graphics.Canvas;

/**
 */
public interface CanvasCallback
{
    public void beforeDraw(Canvas canvas);

    public void afterDraw(Canvas canvas);

    public void beforeDispatchDraw(Canvas canvas);

    public void afterDispatchDraw(Canvas canvas);

    public void beforeOnDraw(Canvas canvas);

    public void afterOnDraw(Canvas canvas);
}
