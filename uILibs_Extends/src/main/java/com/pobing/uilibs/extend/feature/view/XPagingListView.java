package com.pobing.uilibs.extend.feature.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;

import com.pobing.uilibs.feature.features.PageNumberFeature;
import com.pobing.uilibs.feature.features.PullToRefreshFeature;
import com.pobing.uilibs.feature.view.XListView;

public class XPagingListView extends XListView implements
        PullToRefreshFeature.OnPullToRefreshListener {

	public interface OnPageLoadListener {
		/**
		 * @param page
		 *            , the page to load, 0 means the load previous page.
		 * @return the result of whether the page loading is finished,true means
		 *         finished ,false means not.
		 */
		boolean onPageLoad(int page);
	}

	private PullToRefreshFeature mPullToRefresh;
	private PageNumberFeature mPageNumber;
	private OnPageLoadListener mOnPageLoadListener;

	public XPagingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public XPagingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public XPagingListView(Context context) {
		super(context);
		init();
	}

	private void init() {

		mPullToRefresh = new PullToRefreshFeature(this.getContext());
		mPullToRefresh.enablePullDownToRefresh(true);
		mPullToRefresh.enablePullUpToRefresh(true);

		this.addFeature(mPullToRefresh);

		mPullToRefresh.setOnPullToRefreshListener(this);
		mPageNumber = new PageNumberFeature();
		this.addFeature(mPageNumber);

	}

	public void setShowType(int showType) {
		mPageNumber.setShowType(showType);
	}

	public void setOnPageLoadListener(OnPageLoadListener onPageLoadListener) {
		mOnPageLoadListener = onPageLoadListener;
	}

	public void setPageSize(int pageSize) {
		mPageNumber.setPageSize(pageSize);
	}

	public void setTotalCount(int totalCount) {
		mPageNumber.setTotalCount(totalCount);
	}

	@Override
	public void onPullDownToRefresh() {
		new PageLoadTask(0).execute();
		// mOnPageLoadListener.onPageLoad(0);
	}

	@Override
	public void onPullUpToRefresh() {
		new PageLoadTask(mPageNumber.getCurrentPage()).execute();
		// mOnPageLoadListener.onPageLoad(mPageNumber.getCurrentPage());
	}

	public void enablePagingUpward(boolean enable) {
		mPullToRefresh.enablePullDownToRefresh(enable);
	}

	public void enablePagingDownward(boolean enable) {
		mPullToRefresh.enablePullUpToRefresh(enable);
	}
	
    public void setFinishState(boolean downState,boolean upState){
    	mPullToRefresh.setDownRefreshFinish(downState);
    	mPullToRefresh.setUpRefreshFinish(upState);
    }
    
	private class PageLoadTask extends AsyncTask<Void, Void, Boolean> {
		private int mPage;

		public PageLoadTask(int page) {
			mPage = page;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			if (mOnPageLoadListener != null) {
				return mOnPageLoadListener.onPageLoad(mPage);
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mPullToRefresh.onPullRefreshComplete();
			if (result) {
				if (mPage == 0) {
					mPullToRefresh.setDownRefreshFinish(true);
				} else {
					mPullToRefresh.setUpRefreshFinish(true);
				}
			}
		}

	}
}
