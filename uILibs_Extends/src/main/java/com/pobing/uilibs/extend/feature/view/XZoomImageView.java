package com.pobing.uilibs.extend.feature.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

public class XZoomImageView extends XUrlImageView {
	
	private static final int TYPE_NONE = 0;
	private static final int TYPE_DRAG = TYPE_NONE + 1;
	private static final int TYPE_ZOOM = TYPE_DRAG + 1;
	
	private static final int MAX_SCALE_X = 5;
	private static final int MAX_SCALE_Y = 5;
	
	private int mType = TYPE_NONE;
	
	private PointF mStartPoint = new PointF();
	private PointF mMidPoint = new PointF();
	private float mStartDist;
	
	private Matrix mOriginalMatrix = new Matrix();
	private Matrix mMatrix = new Matrix();
	private Matrix mCurMatrix = new Matrix();
	private Matrix mPreMatrix = new Matrix();
	
	private boolean mFirst = true;
	private boolean mScaleSmall = false;
	private boolean mScaleLarge = false;
	private boolean mOverMaxScale = false;
	
	private float mLeft;
	private float mTop;
	private float mRight;
	private float mBottom;
	
	private Rect mRect;
	
	private int mMaxWidth;
	private int mMaxHeight;
	
	private boolean mIgnoreUpEvent = false;
	
	private float[] mOriginalValues = new float[9];
	
	private XZoomScroller mScroller;
	
	public XZoomImageView(Context context) {
		super(context);
		init();
	}

	public XZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public XZoomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setScaleType(ScaleType.FIT_CENTER);
		mScroller = new XZoomScroller(new AccelerateDecelerateInterpolator());
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mMaxWidth = right - left;
		mMaxHeight = bottom - top;
	}


	public void reset() {
		if(!mFirst) {
			mFirst = true;
			mMatrix = new Matrix();
			mPreMatrix = new Matrix();
			mCurMatrix = new Matrix();
			setScaleType(ScaleType.FIT_CENTER);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(null == getDrawable()) {
			return super.onTouchEvent(event);
		}
		
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mStartPoint.set(event.getX(), event.getY());
			if(mFirst) {
				mOriginalMatrix.set(getImageMatrix());
				mMatrix.preConcat(getImageMatrix());
				mFirst = false;
				mOriginalMatrix.getValues(mOriginalValues);
			}
			mCurMatrix.set(mMatrix);
			mType = TYPE_NONE;
			
			mRect = getDrawable().getBounds();
			float[] values = new float[9];
			mCurMatrix.getValues(values);
			mLeft = values[Matrix.MTRANS_X];
			mTop = values[Matrix.MTRANS_Y];
			mRight = mLeft + mRect.width() * values[Matrix.MSCALE_X];
			mBottom = mTop + mRect.height() * values[Matrix.MSCALE_Y];
			
			if(mScaleLarge) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(mType == TYPE_NONE) {
				float dxx = event.getX() - mStartPoint.x;
				float dyy = event.getY() - mStartPoint.y;
				if(Math.abs(dxx) > 10.f || Math.abs(dyy) > 10.0f) {
					mType = TYPE_DRAG;
				}
			} else if(mType == TYPE_DRAG && mScaleLarge) {
				float dx = event.getX() - mStartPoint.x;
				float dy = event.getY() - mStartPoint.y;
				//边界检查
				if(dx > 0) {	//右滑
					if(mLeft >= 0) {
						getParent().requestDisallowInterceptTouchEvent(false);
						dx = 0;
					} else {
						getParent().requestDisallowInterceptTouchEvent(true);
						if(mLeft + dx > 0) {
							dx = -mLeft;
						}
					}
				} else {	//左滑
					if(mRight <= mMaxWidth) {
						getParent().requestDisallowInterceptTouchEvent(false);
						dx = 0;
					} else {
						getParent().requestDisallowInterceptTouchEvent(true);
						if(mRight + dx < mMaxWidth) {
							dx = mMaxWidth - mRight;
						}
					}
				}
				
				if(dy > 0) {	//向下
					if(mTop > 0) {
						dy = 0;
					} else {
						if(mTop + dy > 0) {
							dy = -mTop;
						}
					}
	
				} else {	//向上
					if(mBottom < mMaxHeight) {
						dy = 0;
					} else {
						if(mBottom + dy < mMaxHeight) {
							dy = mMaxHeight - mBottom;
						}
					}
				}
				mMatrix.set(mCurMatrix);
				mMatrix.postTranslate(dx, dy);
				setScaleType(ScaleType.MATRIX);
				setImageMatrix(mMatrix);
				
			} else if(mType == TYPE_ZOOM) {
				getParent().requestDisallowInterceptTouchEvent(true);
				float dist = distance(event);
				if(dist > 10.0f) {
					mMatrix.set(mCurMatrix);
					float scale = dist / mStartDist;
					mMatrix.postScale(scale, scale, mMidPoint.x, mMidPoint.y);
					float[] v = new float[9];
					mMatrix.getValues(v);
					if(v[Matrix.MSCALE_X] / mOriginalValues[Matrix.MSCALE_X] < 1.0 || 
							v[Matrix.MSCALE_Y] / mOriginalValues[Matrix.MSCALE_Y] < 1.0) {
						mScaleSmall = true;
						mScaleLarge = false;
					} else {
						mScaleSmall = false;
						mScaleLarge = true;
					}
					
					if(v[Matrix.MSCALE_X] / mOriginalValues[Matrix.MSCALE_X]> MAX_SCALE_X ||
							v[Matrix.MSCALE_Y] / mOriginalValues[Matrix.MSCALE_Y] > MAX_SCALE_Y) {
						mOverMaxScale = true;
						mPreMatrix.set(mCurMatrix);
					} else {
						mOverMaxScale = false;
					}
				}
				setScaleType(ScaleType.MATRIX);
				setImageMatrix(mMatrix);
			}

			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mStartDist = distance(event);
			if(mStartDist > 10.0f) {
				mCurMatrix.set(mMatrix);
				mType = TYPE_ZOOM;
				mMidPoint = midPoint(event);
			}
			break;
		case MotionEvent.ACTION_UP:
			if(mIgnoreUpEvent) {
				mIgnoreUpEvent = false;
				return true;
			}
		case MotionEvent.ACTION_POINTER_UP:
			if(mType == TYPE_DRAG) {
				mType = TYPE_NONE;
				return true;
			} else if(mType == TYPE_ZOOM) {
				mType = TYPE_NONE;
				mIgnoreUpEvent = true;
				getParent().requestDisallowInterceptTouchEvent(false);
				if(mScaleSmall) {
					mScaleSmall = false;
					mScaleLarge = false;
					startScroll(mMatrix, mOriginalMatrix);
				}
				if(mOverMaxScale) {
					mOverMaxScale = false;
					startScroll(mMatrix, mPreMatrix);
				}
				return true;
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	private float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0); 
		return (float) Math.sqrt(dx*dx + dy*dy);
	}
	
	private PointF midPoint(MotionEvent event) {
        float x = (event.getX(1) + event.getX(0)) / 2;  
        float y = (event.getY(1) + event.getY(0)) / 2;  
        return new PointF(x,y); 
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if(mScroller.computeScrollOffset()) {
			float[] v = new float[9];
			mMatrix.getValues(v);
			v[Matrix.MTRANS_X] = mScroller.getCurrX();
			v[Matrix.MTRANS_Y] = mScroller.getCurrY();
			v[Matrix.MSCALE_X] = mScroller.getCurrZ();
			v[Matrix.MSCALE_Y] = mScroller.getCurrZ();
			mMatrix.setValues(v);
			setImageMatrix(mMatrix);
			invalidate();
		}
	}
	
	private void startScroll(Matrix srcMatrix, Matrix dstMatrix) {
		if(null != srcMatrix && null != dstMatrix) {
			float[] srcValues = new float[9];
			float[] dstValues = new float[9];
			srcMatrix.getValues(srcValues);
			dstMatrix.getValues(dstValues);
			
			float srcTransX = srcValues[Matrix.MTRANS_X];
			float dstTransX = dstValues[Matrix.MTRANS_X];
			float srcTransY = srcValues[Matrix.MTRANS_Y];
			float dstTransY = dstValues[Matrix.MTRANS_Y];
			float srcScaleX = srcValues[Matrix.MSCALE_X];
			float dstScaleX = dstValues[Matrix.MSCALE_X];
			
			mScroller.startScroll(srcTransX, srcTransY, srcScaleX, dstTransX - srcTransX,
					dstTransY - srcTransY, dstScaleX - srcScaleX, 300);
			invalidate();
		}
	}
	
	
}
