package com.pobing.uilibs.extend.feature.view;

import com.pobing.uilibs.extend.R;
import com.pobing.uilibs.extend.events.DownloadEvent;
import com.pobing.uilibs.extend.events.IUILibsListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class XZoomPagerItem extends RelativeLayout {
	private static final String TAG = "XZoomPagerItem";
	
	private XZoomImageView mImageView;
	private ProgressBar mProgressBar;
	private LinearLayout mErrorView;
	
	public XZoomPagerItem(Context context) {
		super(context);
		init(context);
	}

	public XZoomPagerItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public XZoomPagerItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.uilibs_zoom_page_item, this);
		mImageView  = (XZoomImageView)view.findViewById(R.id.img);
		mProgressBar = (ProgressBar)view.findViewById(R.id.progressbar);
		mErrorView = (LinearLayout)view.findViewById(R.id.error_view);
		
		mImageView.succListener(new IUILibsListener<DownloadEvent.SuccessEvent>() {

			@Override
			public boolean onHappen(DownloadEvent.SuccessEvent arg0) {
				Log.i(TAG, "onSuccesss----" + mImageView);
				mProgressBar.setVisibility(View.GONE);
				mErrorView.setVisibility(View.GONE);
				return false;
			}
		});

		mImageView.failListener(new IUILibsListener<DownloadEvent.FailureEvent>() {

			@Override
			public boolean onHappen(DownloadEvent.FailureEvent arg0) {
				Log.i(TAG, "onFail----" + mImageView);
				mProgressBar.setVisibility(View.GONE);
				mErrorView.setVisibility(View.VISIBLE);
				return false;
			}
		});
	}
	
	public XZoomImageView getImageView() {
		return mImageView;
	}
	
}
