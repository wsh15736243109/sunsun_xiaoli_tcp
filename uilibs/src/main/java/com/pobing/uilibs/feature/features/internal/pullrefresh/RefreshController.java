package com.pobing.uilibs.feature.features.internal.pullrefresh;

import java.util.Date;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Scroller;

import com.pobing.uilibs.R;
import com.pobing.uilibs.feature.features.PullToRefreshFeature.OnPullToRefreshListener;
import com.pobing.uilibs.utils.Config;

/**
 * 
 * 下拉刷新的控制管理
 */
public class RefreshController {


	private final static int RATIO = 3;   //padding的距离与界面上偏移距离的比例 屏幕滑动3个单位  上面head偏移一个单位
	
	private static final String TAG = "DownRefreshControler";

	public final static int RELEASE_To_REFRESH = 0;   //松手即可刷新状态
	public final static int PULL_To_REFRESH = 1;      //提示下拉刷新状态
	public final static int REFRESHING = 2;           //正在刷新状态
	public final static int DONE = 3;                 //不可见状态
	
	public final static int DOWN_PULL = 4;
	public final static int UP_PULL = 5;
	
	private RefreshHeadViewManager mHeaderViewManager;
	private RefreshHeadViewManager mFooterViewManager;
	private int mState;
	private boolean mIsBack;             // 是否从松手刷新往回推且要变成下拉刷新状态 
	private int mStartY;
	private boolean mIsRefreshable;
	private boolean mIsRecored;
	private OnPullToRefreshListener mRefreshListener;
	private OnPullDownRefreshCancle mCancle;
	private Scroller mScroller;
	private boolean mIsScrolling = false;
	private IViewEdgeJudge mEdgeJudge;
	private Context mContext;
	private boolean mDownRefreshEnable = true;
	private boolean mUpRefreshEnable   = true;
	private int mPullDirection ;
	private boolean mUpPullFinish = false;
	private boolean mDownPullFinish = false;
	
	public interface OnPullDownRefreshCancle{
		void onRefreshCancle();
	}
	
	public RefreshController(IViewEdgeJudge edgeJudger, Context context, Scroller scroller)
	{
		mEdgeJudge = edgeJudger;
		mScroller = scroller;
		mContext = context;
		mState = DONE;
		mIsRefreshable = true;
	}
	
	public void setRefreshCancle(OnPullDownRefreshCancle cancle) {
		mCancle = cancle;
	}
	
	public void setDownRefreshTips(String[] array)
	{
		if(mHeaderViewManager!=null)
		{
			mHeaderViewManager.setTipArray(array);
		}
	}
	
	public void setUpRefreshTips(String[] array)
	{
		if(mFooterViewManager!=null)
		{
			mFooterViewManager.setTipArray(array);
		}
	}
	
	public void setTimeVisible(boolean flag)
	{
		if(mFooterViewManager != null){
			mFooterViewManager.setTimeVisible(flag);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void enablePullDownRefresh(boolean enable,int drawableID,int progressViewID,View custom)
	{
		if(enable){
			ImageView view = new ImageView(mContext);
	        view.setImageResource(R.drawable.uilibs_list_logo);
	        view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//			mHeaderViewManager = new RefreshHeadViewManager(mContext,mContext.getResources().getDrawable(drawableID), LayoutInflater.from(mContext).inflate(progressViewID, null),view,RefreshHeadViewManager.TYPE_HEADER);
	        //自定义ProgressBar，不支持接口设置个性化的ProgressBar
	        mHeaderViewManager = new RefreshHeadViewManager(mContext,mContext.getResources().getDrawable(drawableID), null,view,RefreshHeadViewManager.TYPE_HEADER);
			mHeaderViewManager.setUpdatedTextView("最近更新:" + new Date().toLocaleString());
		}else{
			mHeaderViewManager = null;
		}
			//mEdgeJudge.setHeadView(headerViewManager.getView());
	}
	
	public void setProgressBarColor(int color) {
		if(mHeaderViewManager != null)
			mHeaderViewManager.setProgressBarColor(color);
		if(mFooterViewManager != null)
			mFooterViewManager.setProgressBarColor(color);
	}
	
	public void addHeaderView(){
		if(mHeaderViewManager != null){
			mEdgeJudge.setHeadView(mHeaderViewManager.getView());
		}
	}
	@SuppressWarnings("deprecation")
	public void enablePullUpRefresh(boolean enable,int drawableID,int progressViewID, View custom)
	{
		if(enable){
//			mFooterViewManager = new RefreshHeadViewManager(mContext,mContext.getResources().getDrawable(drawableID), LayoutInflater.from(mContext).inflate(progressViewID, null), custom,RefreshHeadViewManager.TYPE_FOOTER);
			mFooterViewManager = new RefreshHeadViewManager(mContext,mContext.getResources().getDrawable(drawableID), null, custom,RefreshHeadViewManager.TYPE_FOOTER);
			mFooterViewManager.setUpdatedTextView("最近更新:" + new Date().toLocaleString());
		}else{
			mFooterViewManager = null;
		}
		//mEdgeJudge.setFooterView(footerViewManager.getView());
	}
	public void addFooterView(){
		if(mFooterViewManager != null){
			mEdgeJudge.setFooterView(mFooterViewManager.getView());
		}
	}
	
    private int getTopRealScrollY(int distanceY){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        if(mHeaderViewManager!= null){
            int paddingTop = mHeaderViewManager.getPaddingTop();
            int diffY      = mHeaderViewManager.getHeight() + paddingTop;
            float percent = (float)((((float)displayMetrics.heightPixels)/(displayMetrics.heightPixels+diffY)/1.3));
            return (int)(percent*distanceY);
        }else{
            return distanceY;
        }
    }
	
	/**
	 * 是否到达可松开刷新的位置
	 * @return
	 */
	private boolean JudgeArrivedRecoredEdge(MotionEvent event)
	{
		if(mEdgeJudge!=null)
		{
			if (mDownRefreshEnable && mEdgeJudge.hasArrivedTopEdge() && !mIsRecored) 
			{
				mIsRecored = true;
				mStartY = (int) event.getRawY();
				
				return true;
			}else if(mUpRefreshEnable && mEdgeJudge.hasArrivedBottomEdge() && !mIsRecored)
			{
				mIsRecored = true;
				mStartY = (int) event.getRawY();
				
				return true;
			}
		}
		return false;
	}
	
	private void processActionMove(int distance,int tempY)
	{
		if (mState == RELEASE_To_REFRESH)                                 //松开刷新相邻的状态只可能是下拉刷新
		{
		                                   								 
			if(mPullDirection == DOWN_PULL &&  mHeaderViewManager != null)
			{
				//当超过一屏幕且head可见时 ，往上推动时候，除了附加的padding改变，
                //列表本身在向下滑动，会导致数值出错，此时通过setpadding禁止列表滑动
				mEdgeJudge.keepTop();
				//往下拉 未到达可刷新区域
				if ((getTopRealScrollY(distance) < mHeaderViewManager.getHeight())         //head 没有完全露出
						&& (tempY - mStartY) > 0) 
				{
					mState = PULL_To_REFRESH;
					changeHeaderViewByState();
				}
			}else if(mPullDirection == UP_PULL && mFooterViewManager != null)
			{
				mEdgeJudge.keepBottom();
				//往上拉 为到达可刷新区域
				if ((Math.abs(distance / RATIO) < mFooterViewManager.getHeight())         //head 没有完全露出 
						&& (tempY - mStartY) < 0) 
				{
					mState = PULL_To_REFRESH;
					changeFooterViewByState();
				}
			}
			
		}else if (mState == PULL_To_REFRESH)                              //下拉刷新相邻的状态可能是松开刷新 可能是完全盖住的down状态
		{
		//	mListView.setSelection(0);
			if(mPullDirection == DOWN_PULL && mHeaderViewManager != null)
			{
				mEdgeJudge.keepTop();
				if (getTopRealScrollY(distance) >= mHeaderViewManager.getHeight())         //head 完全露出
				{ 
					mState = RELEASE_To_REFRESH;
					mIsBack = true;
				}else if (tempY - mStartY <= 0)                                //head 完全盖住
				{
					mState = DONE;
				}
				changeHeaderViewByState();
				changeHeaderProgressBarState(getTopRealScrollY(distance));
			}else if(mPullDirection == UP_PULL && mFooterViewManager != null)
			{
				mEdgeJudge.keepBottom();
				if (distance / RATIO <= -1*mFooterViewManager.getHeight())         //head 完全露出
				{ 
					mState = RELEASE_To_REFRESH;
					mIsBack = true;
//					changeFooterViewByState();
				}else if (tempY - mStartY >= 0)                                //head 完全盖住
				{
					mState = DONE;
				}
				changeFooterViewByState();
				changeFooderProgressBarState(-distance / RATIO);
			}
		}else if (mState == DONE)                                          //从完全盖住的done状态的相邻状态只可能是下拉刷新
		{
			if (distance > 0 && mEdgeJudge.hasArrivedTopEdge()) 
			{
				mPullDirection = DOWN_PULL;
				mState = PULL_To_REFRESH;
				changeHeaderViewByState();
				
			}else if(distance<0 && mEdgeJudge.hasArrivedBottomEdge())
			{
				mPullDirection = UP_PULL;
				mState = PULL_To_REFRESH;
				changeFooterViewByState();
			}
		}

		
		if (mState == PULL_To_REFRESH || mState==RELEASE_To_REFRESH)        // 更新headView的padding位置
		{
			if(mPullDirection == DOWN_PULL && mHeaderViewManager != null)
			{
				mHeaderViewManager.setPadding(0, -1 * mHeaderViewManager.getHeight() + getTopRealScrollY(distance), 0, 0);
			}
			else if(mPullDirection == UP_PULL && mFooterViewManager != null)
			{
				mFooterViewManager.setPadding(0, 0, 0, -1 * mFooterViewManager.getHeight() - distance / RATIO);
			}
		}
	}
	
	public void onTouchEvent(MotionEvent event)
	{
		if (mIsRefreshable && !mIsScrolling) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				JudgeArrivedRecoredEdge(event);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_OUTSIDE:
//			    if (mFooterViewManager == null || mHeaderViewManager == null)
//			    {
//			        return;
//			    }
				if (mState != REFRESHING) 
				{
					if(mPullDirection == DOWN_PULL)
					{
						if (mState == PULL_To_REFRESH) 
						{
							mState = DONE;
							changeHeaderViewByState();
							if(mCancle != null)
								mCancle.onRefreshCancle();
						}
						if (mState == RELEASE_To_REFRESH) 
						{
							mState = REFRESHING;
							changeHeaderViewByState();
							onRefresh();
						}
					}else if(mPullDirection == UP_PULL)					
					{
						if (mState == PULL_To_REFRESH) 
						{
							mState = DONE;
							changeFooterViewByState();
	
						}
						if (mState == RELEASE_To_REFRESH) 
						{
							mState = REFRESHING;
							changeFooterViewByState();
							onRefresh();
						}
					}
				}
				mIsRecored = false;
				mIsBack = false;
				break;
			case MotionEvent.ACTION_MOVE:
//			    if (mHeaderViewManager == null){
//			        return;
//			    }
				int tempY = (int) event.getRawY();
				JudgeArrivedRecoredEdge(event);
				int distance = tempY - mStartY;
                if(Config.Debug){
				    Log.d(TAG, distance + "");
                }
                
				if (mState != REFRESHING && mIsRecored) 
				{
					//如果在下拉刷新状态
					processActionMove(distance,tempY);
				}

				break;
			}
		}
	}
	
	private void changeHeaderProgressBarState(int distance) {
		if(mHeaderViewManager != null){
			mHeaderViewManager.changeProgressBarState(distance);
		}
	}
	
	private void changeFooderProgressBarState(int distance) {
		if(mFooterViewManager != null){
			mFooterViewManager.changeProgressBarState(distance);
		}
	}
	
	public void onScrollerStateChanged(int y,boolean isScrolling)
	{
		if(mPullDirection == DOWN_PULL)
		{
			if(mIsScrolling)
			{
				if(isScrolling &&mHeaderViewManager != null){
					mHeaderViewManager.setPadding(0, y, 0, 0);
				}
				else
					mIsScrolling = false;
			}else
			{
				if(mState == REFRESHING)
				{
					//onRefresh();
				}
			}
		}else if(mPullDirection == UP_PULL)
		{
			if(mIsScrolling)
			{
				if(isScrolling && mFooterViewManager != null){
					mFooterViewManager.setPadding(0, 0, 0, y);
				}
				else
					mIsScrolling = false;
			}else
			{
				if(mState == REFRESHING)
				{
					//onRefresh();
				}
			}
		}
	}
	
	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
	    if (mHeaderViewManager == null)
	    {
	        return;
	    }
	    
		mHeaderViewManager.changeHeaderViewByState(mState, mIsBack);
		if (mState == PULL_To_REFRESH && mIsBack) {
			mIsBack = false;
		} else if (mState == RefreshController.REFRESHING) {
            if(Config.Debug){
			    Log.v(TAG, "刷新造成scroll");
            }
			resetHeadViewPadding(mState);
		} else if (mState == RefreshController.DONE) {
            if(Config.Debug){
			    Log.v(TAG, "不需要刷新或者刷新完成造成scroll");
            }
			resetHeadViewPadding(mState);
		}

	}
	
	// 当状态改变时候，调用该方法，以更新界面
	private void changeFooterViewByState() {
	    if (mFooterViewManager == null)
	    {
	        return;
	    }
		mFooterViewManager.changeHeaderViewByState(mState, mIsBack);
		if (mState == PULL_To_REFRESH && mIsBack) {
			mIsBack = false;
		} else if (mState == RefreshController.REFRESHING) {
            if(Config.Debug){
                Log.v(TAG, "刷新造成scroll");
            }
			resetfooterViewPadding(mState);
		} else if (mState == RefreshController.DONE) {
            if(Config.Debug){
			    Log.v(TAG, "不需要刷新或者刷新完成造成scroll");
            }
			resetfooterViewPadding(mState);
		}

	}
	
	/**
	 * 据观察 多数下拉回弹的效果仅在下拉回弹到刷新或者下拉不成功的状况下  对于刷新成功都是直接隐藏的 感觉比较生硬 不如都差用滑动的效果
	 * @param state
	 */
	public void resetfooterViewPadding(int state)
	{
		if(mFooterViewManager == null){
			return;
		}
		int height = mFooterViewManager.getHeight();
		if (height == 0) 
			return;

		int finalPaddingBottom =0;
		if(state== RefreshController.REFRESHING)
			finalPaddingBottom = 0;
		else if(state== RefreshController.DONE)
			finalPaddingBottom = -height;
		mIsScrolling = true;
		mScroller.startScroll(0, mFooterViewManager.getPaddingBottom(), 0,finalPaddingBottom-mFooterViewManager.getPaddingBottom() ,350);

	}
	
	public void setDownRefreshFinish(boolean downRefreshLoadFinish)
	{
		if(mHeaderViewManager!=null)
		{
			mDownPullFinish = downRefreshLoadFinish;
			mHeaderViewManager.setFinish(downRefreshLoadFinish);
		}
		
	}
	
	public void setUpRefreshFinish(boolean downRefreshLoadFinish)
	{
		if(mFooterViewManager!=null)
		{
			mUpPullFinish = downRefreshLoadFinish;
			mFooterViewManager.setFinish(downRefreshLoadFinish);
		}
		
	}
	
	private void resetHeadViewPadding(int state)
	{
		if(mHeaderViewManager == null){
			return;
		}
		int height = mHeaderViewManager.getHeight();
		if (height == 0) 
			return;

		int finalPaddingTop =0;
		if(state== RefreshController.REFRESHING)
			finalPaddingTop = 0;
		else if(state== RefreshController.DONE)
			finalPaddingTop = -height;
		mIsScrolling = true;
		mScroller.startScroll(0, mHeaderViewManager.getPaddingTop(), 0,finalPaddingTop-mHeaderViewManager.getPaddingTop() ,350);
	//	mListView.invalidate();
	}
	
	public void setOnRefreshListener(OnPullToRefreshListener refreshListener) {
		this.mRefreshListener = refreshListener;
		mIsRefreshable = true;
	}
	
	private void onRefresh() {
		if (mRefreshListener != null) {
			if(mPullDirection == DOWN_PULL)
			{
				if(this.mDownPullFinish)
				{
					this.onRefreshComplete();
				}else
				{
					mRefreshListener.onPullDownToRefresh();
				}
			}
			else if(mPullDirection == UP_PULL)
			{
				if(this.mUpPullFinish)
				{
					this.onRefreshComplete();
				}else
				{
					mRefreshListener.onPullUpToRefresh();
				}
			}
		}
	}

//    /**
//     *
//     * @param distance
//     * @param contentTop
//     * @return
//     */
//    private boolean getRealScrollY(float distance,int contentTop){
//         float percent = 1/(1+
//    }
	
	@SuppressWarnings("deprecation")
	public void onRefreshComplete() {
		mState = DONE;
		if(mPullDirection == DOWN_PULL)
		{
			if(mHeaderViewManager != null){
				mHeaderViewManager.setUpdatedTextView("最近更新:" + new Date().toLocaleString());
				changeHeaderViewByState();
			}
		}
		else if(mPullDirection == UP_PULL)
		{
			if(mFooterViewManager != null){
				mFooterViewManager.setUpdatedTextView("最近更新:" + new Date().toLocaleString());	
				changeFooterViewByState();
			}
		}
	}
	
	public void setIsDownRefreshing()
	{
		if(mHeaderViewManager!=null)
		{
			mState= RefreshController.REFRESHING;
			this.changeHeaderViewByState();
			mHeaderViewManager.setPadding(0, 0, 0, 0);
            mPullDirection = DOWN_PULL;
		}
	}

    public void setIsUpRefreshing()
    {
        if(mFooterViewManager!=null)
        {
            mPullDirection = UP_PULL;
            mState= RefreshController.REFRESHING;
            this.changeHeaderViewByState();
            mFooterViewManager.setPadding(0, 0, 0, 0);
        }
    }
	
	public int getPullDirection()
	{
		return this.mPullDirection;
	}
		
	public void destroy()
	{
		mRefreshListener = null;
	}
}
