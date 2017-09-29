package com.pobing.uilibs.feature.callback;

/**
 */
public interface MeasureCallback
{
    public void beforeOnMeasure(int widthMeasureSpec, int heightMeasureSpec);

    public void afterOnMeasure(int widthMeasureSpec, int heightMeasureSpec);
}
