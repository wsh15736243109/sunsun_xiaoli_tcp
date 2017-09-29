package com.pobing.uilibs.feature.callback;

/**
 */
public interface ScrollCallback
{
    public void beforeComputeScroll();
    public void afterComputeScroll();
    
    public void beforeOnScrollChanged(int l, int t, int oldl, int oldt);
    public void afterOnScrollChanged(int l, int t, int oldl, int oldt);
}
