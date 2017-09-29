package com.pobing.uilibs.feature.callback;

/**
 */
public interface LayoutCallback
{
    public void beforeOnLayout(boolean changed, int left, int top, int right, int bottom);

    public void afterOnLayout(boolean changed, int left, int top, int right, int bottom);
}
