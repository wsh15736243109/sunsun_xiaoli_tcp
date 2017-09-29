package com.pobing.uilibs.extend.component.filter;

import android.graphics.drawable.Drawable;


public class FilterTab
{
    private String mTag;
    private String mLabel;
    private Drawable mIcon;
    private FilterAdapter mFilterAdapter;
    private FilterLevel mFilterLevel;

    public enum FilterLevel
    {
        ONLY_CHILD,GROUP_AND_CHILD
    }

    public FilterTab(String tag)
    {
        mTag = tag;
    }

    /**
     * Specify a label for the tab indicator.
     */
    public FilterTab setIndicatorLabel(String label)
    {
        mLabel = label;
        return this;
    }

    /**
     * Specify a icon for the tab indicator.
     */
    public FilterTab setIndicatorIcon(Drawable icon)
    {
        mIcon = icon;
        return this;
    }

    public FilterTab setFilterAdapter(FilterAdapter filterAdapter)
    {
        mFilterAdapter = filterAdapter;
        return this;
    }

    public void setFilterLevel(FilterLevel level)
    {
        mFilterLevel = level;
    }

    public FilterLevel getFilterLevel()
    {
        return mFilterLevel;
    }

    public FilterAdapter getFilterAdapter()
    {
        return mFilterAdapter;
    }

    public String getTag()
    {
        return mTag;
    }

    public String getLabel()
    {
        return mLabel;
    }

    public Drawable getIcon()
    {
        return mIcon;
    }
}
