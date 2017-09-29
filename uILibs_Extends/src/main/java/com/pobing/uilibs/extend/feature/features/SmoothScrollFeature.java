package com.pobing.uilibs.extend.feature.features;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.pobing.uilibs.feature.features.AbsFeature;
import com.pobing.uilibs.feature.view.XImageView;


public class SmoothScrollFeature extends AbsFeature<ListView> implements OnScrollListener
{

    @Override
    public void constructor(Context context, AttributeSet attrs, int defStyle)
    {

    }

    @Override
    public void setHost(ListView host)
    {
        super.setHost(host);
        host.setOnScrollListener(this);
    }

    private ImageLoadFeature getImageLoadFeature(XImageView view)
    {
        return (ImageLoadFeature) view.findFeature(ImageLoadFeature.class);
    }

    private void resume(View v)
    {
        if (v instanceof ViewGroup)
        {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++)
            {
                resume(vg.getChildAt(i));
            }
        }
        else
        {
            if (v instanceof XImageView)
            {
                ImageLoadFeature imageLoad = getImageLoadFeature((XImageView) v);
                if (imageLoad != null)
                {
                    imageLoad.resume();
                }
            }
        }
    }

    private void pause(View v)
    {
        if (v instanceof ViewGroup)
        {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++)
            {
                pause(vg.getChildAt(i));
            }
        }
        else
        {
            if (v instanceof XImageView)
            {
                ImageLoadFeature imageLoad = getImageLoadFeature((XImageView) v);
                if (imageLoad != null)
                {
                    imageLoad.pause();
                }
            }
        }
    }

    @Deprecated
    public void setImageViewTag(ImageView imageView, Object tag) {
        //该方法已经不再需要被调用了
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        switch (scrollState)
        {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                pause(view);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                resume(view);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                pause(view);
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {

    }

}
