package com.pobing.uilibs.extend.feature.view;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/*
 * 配合XZoomImageView 使用，用于反弹动画
 */
public class XZoomScroller {
	
	private Interpolator mInterPolator;
	
	private boolean mFinished = true;
	
	private long mStartTime;
	private int mDuration;
    private float mDurationReciprocal;
    
	private float mStartX;
	private float mStartY;
	private float mStartZ;
	
	private float mFinalX;
	private float mFinalY;
	private float mFinalZ;
	
	private float mDeltaX;
	private float mDeltaY;
	private float mDeltaZ;
	
	private float mCurrX;
	private float mCurrY;
	private float mCurrZ;
	
	public XZoomScroller(Interpolator interpolator) {
		mInterPolator = interpolator;
		if(null == mInterPolator) {
			mInterPolator = new AccelerateDecelerateInterpolator();
		}
	}
	
	public void startScroll(float startX, float startY, float startZ, 
			float dx, float dy, float dz, int duration) {
		mStartTime = AnimationUtils.currentAnimationTimeMillis();
		mDuration = duration;
	    mDurationReciprocal = 1.0f / (float)mDuration;
	    mFinished = false;
	    
		mStartX = startX;
		mStartY = startY;
		mStartZ = startZ;
		
		mFinalX = mStartX + dx;
		mFinalY = mStartY + dy;
		mFinalZ = mStartZ + dz;
		
		mDeltaX = dx;
		mDeltaY = dy;
		mDeltaZ = dz;
	}
	
	public boolean computeScrollOffset() {
		if(mFinished) {
			return false;
		}
		int time = (int) (AnimationUtils.currentAnimationTimeMillis() - mStartTime);
		if(time < mDuration) {
			float x = time * mDurationReciprocal;
			x = mInterPolator.getInterpolation(x);
			mCurrX = mStartX + x * mDeltaX;
			mCurrY = mStartY + x * mDeltaY;
			mCurrZ = mStartZ + x * mDeltaZ;
		} else {
			mCurrX = mFinalX;
			mCurrY = mFinalY;
			mCurrZ = mFinalZ;
			mFinished = true;
		}
		return true;
	}
	
	public float getCurrX() {
		return mCurrX;
	}
	
	public float getCurrY() {
		return mCurrY;
	}
	
	public float getCurrZ() {
		return mCurrZ;
	}
}