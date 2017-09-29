package com.pobing.uilibs.extend.feature.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.pobing.uilibs.extend.events.DownloadEvent;
import com.pobing.uilibs.extend.events.IUILibsListener;
import com.pobing.uilibs.extend.feature.features.ImageLoadFeature;
import com.pobing.uilibs.feature.view.XImageView;

/**
 * 
 * The TUrlImageView is a imageView which contains the feature of
 * {@link com.pobing.uilibs.extend.feature.features.ImageLoadFeature}.
 * 
 *
 */
public class XUrlImageView extends XImageView {
	
	private ImageLoadFeature mImageLoad;

	/**
	 * get the image load feature
	 * */
	public ImageLoadFeature getmImageLoad() {
		return mImageLoad;
	}



	public XUrlImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public XUrlImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public XUrlImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mImageLoad = new ImageLoadFeature();
		this.addFeature(mImageLoad);
		
		
	}
	
    public ImageLoadFeature succListener( IUILibsListener<DownloadEvent.SuccessEvent> listener)
    {
    	return mImageLoad.succListener(listener);
    }

    public ImageLoadFeature failListener( IUILibsListener<DownloadEvent.FailureEvent> listener)
    {
    	return mImageLoad.failListener(listener);
    }
    

	/**
	 * set download url of the image
	 * 
	 * @param url
	 */
	public void setImageUrl(String url) {
		mImageLoad.setImageUrl(url);
	}

	/**
	 * Sets the default image resource ID to be used for this view until the
	 * attempt to load it completes.
	 * 
	 * @param id
	 */
	public void setPlaceHoldImageResId(int id) {
		mImageLoad.setPlaceHoldImageResId(id);
	}

	/**
	 * Sets the default image drawable to be used for this view until the
	 * attempt to load it completes. If this is set, setPlaceHoldImageResId will not work.
	 * 
	 * @param id
	 */
	public void setPlaceHoldForground(Drawable d)
    {
		mImageLoad.setPlaceHoldForground(d);
    }
	
	/**
	 * Sets the error image resource ID to be used for this view in the event
	 * that the image requested fails to load.
	 * 
	 * @param id
	 */
	public void setErrorImageResId(int id) {
		mImageLoad.setErrorImageResId(id);
	}
	
	/**
	 * Set whether not to use the default URL strategy. if set YES, should decide the size for your own.
	 * 
	 */
	public void setSkipAutoSize(boolean b) {
		mImageLoad.skipAutoSize(b);
	}
	
	
	

	
}
