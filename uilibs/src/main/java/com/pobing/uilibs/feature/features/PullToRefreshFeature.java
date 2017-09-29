package com.pobing.uilibs.feature.features;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;

import com.pobing.uilibs.feature.callback.ScrollCallback;
import com.pobing.uilibs.feature.callback.TouchEventCallback;
import com.pobing.uilibs.feature.features.internal.pullrefresh.IViewEdgeJudge;
import com.pobing.uilibs.feature.features.internal.pullrefresh.RefreshController;

/**
 * Date: 14-4-18
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class PullToRefreshFeature extends AbsFeature<ListView> implements TouchEventCallback, IViewEdgeJudge,ScrollCallback {

	public interface OnPullToRefreshListener {
		
		public void onPullDownToRefresh();
		
		public void onPullUpToRefresh();

	}
	
    private RefreshController mRefreshController;
    private Scroller mScroller;
    private Context mContext;
    public PullToRefreshFeature(Context context){
		mScroller = new Scroller(context,
                 new DecelerateInterpolator());
    	mRefreshController = new RefreshController(this,
    			context, mScroller);
    	mContext = context;
    }

    /**
     * 设置正在下拉刷新中
     */
    public void setIsDownRefreshing(){
        if(mRefreshController!=null){
            mRefreshController.setIsDownRefreshing();
        }
    }

    /**
     * 设置正在上拉刷新中
     */
    public void setIsUpRefreshing(){
        if(mRefreshController!=null){
            mRefreshController.setIsUpRefreshing();
        }
    }
    
    @Override
    public void constructor(Context context, AttributeSet attrs, int defStyle) {
    }

	@Override
	public void setHost(ListView host) {
		// TODO Auto-generated method stub
		super.setHost(host);
		mRefreshController.addFooterView();
		mRefreshController.addHeaderView();
	}

	@Override
    public void beforeOnTouchEvent(MotionEvent event) {
        if (mRefreshController != null)
            mRefreshController.onTouchEvent(event);
    }

    @Override
    public void afterOnTouchEvent(MotionEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeDispatchTouchEvent(MotionEvent event) {

    }

    @Override
    public void afterDispatchTouchEvent(MotionEvent event) {

    }

    @Override
    public boolean hasArrivedTopEdge() {
        return mHost.getFirstVisiblePosition() == 0;
    }

    @Override
    public boolean hasArrivedBottomEdge() {
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.GINGERBREAD_MR1) {
            return mHost.getLastVisiblePosition() == mHost.getCount() - 1 && mHost.getFirstVisiblePosition() != 0;
        }else{
            return mHost.getLastVisiblePosition() >= mHost.getCount() - 2 && mHost.getFirstVisiblePosition() != 0;
        }
    }

    @Override
    public void setHeadView(View view) {
        mHost.addHeaderView(view);
    }

    @Override
    public void setFooterView(View view) {
        mHost.addFooterView(view);
    }

    @Override
    public void keepTop() {
        mHost.setSelection(0);
    }

    @Override
    public void keepBottom() {
        mHost.setSelection(mHost.getCount());
    }

 
    public void enablePullDownToRefresh(boolean enable){
		ImageView view = new ImageView(mContext);
        view.setImageResource(com.pobing.uilibs.R.drawable.uilibs_list_logo);
        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

    	enablePullDownToRefresh(enable, com.pobing.uilibs.R.drawable.uilibs_arrow, 0,view);
    }
    /**
     * 设置下拉动画的颜色值
     * @param color
     */
    public void setProgressBarColor(int color) {
    	if(mRefreshController != null)
    		mRefreshController.setProgressBarColor(color);
    }
    
    public void enablePullUpToRefresh(boolean enable){	
    	enablePullUpToRefresh(enable, com.pobing.uilibs.R.drawable.uilibs_arrow, 0,null);
    }
    
    public void enablePullDownToRefresh(boolean enable,int arrowDrawableId, int progressViewID,View custom) {
    	if(enable){
            mRefreshController.enablePullDownRefresh(enable,arrowDrawableId,
                    progressViewID,custom);
    	}else{
    		mRefreshController.enablePullDownRefresh(enable,0, 0,null);
    	}	

    }

    public void enablePullUpToRefresh(boolean enable,int arrowDrawableId, int progressViewID, View custom) {
    	
    	if(mRefreshController == null){
    		return;
    	}
    	
    	if(enable){   		
    		mRefreshController.enablePullUpRefresh(enable,arrowDrawableId,
                    progressViewID, custom);
    	}else{
    		mRefreshController.enablePullUpRefresh(enable,0, 0, null);
    	}	
    }

	public void setPullDownRefreshTips(String[] array)
	{
		if(mRefreshController == null){
    		return;
    	}
		
		mRefreshController.setDownRefreshTips(array);
	}
	
	public void setPullUpRefreshTips(String[] array)
	{
		if(mRefreshController == null){
    		return;
    	}		
		mRefreshController.setUpRefreshTips(array);
	}

    public void setOnPullToRefreshListener(OnPullToRefreshListener refreshListener) {
    	if (mRefreshController != null)
            mRefreshController.setOnRefreshListener(refreshListener);
    }

    public void onPullRefreshComplete() {
        if (mRefreshController != null)
            mRefreshController.onRefreshComplete();
    }

	@Override
	public void beforeComputeScroll() {
		if (mScroller != null && mScroller.computeScrollOffset()) {
			if (mRefreshController != null)
				mRefreshController.onScrollerStateChanged(
						mScroller.getCurrY(), true);
			this.getHost().postInvalidate();
		} else {
			if (mRefreshController != null)
				mRefreshController.onScrollerStateChanged(
						mScroller.getCurrY(), false);
		}
	}


	@Override
	public void afterComputeScroll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeOnScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterOnScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDownRefreshFinish(boolean downRefreshLoadFinish)
	{
		mRefreshController.setDownRefreshFinish(downRefreshLoadFinish);		
	}
	
	public void setUpRefreshFinish(boolean downRefreshLoadFinish)
	{
		mRefreshController.setUpRefreshFinish(downRefreshLoadFinish);	
	}
}
