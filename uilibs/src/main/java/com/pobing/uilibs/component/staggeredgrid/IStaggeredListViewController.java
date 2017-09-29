package com.pobing.uilibs.component.staggeredgrid;

import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;

/**
 * 瀑布流listview控件数据相关的controller
 */
public interface IStaggeredListViewController extends OnScrollListener{

    /**
     * 获取listdaapter
     * @return
     */
    public ListAdapter getAdapter();
    
    /**
     * 获取数据个数
     * @return
     */
    public int getItemCount();
    
    
}
