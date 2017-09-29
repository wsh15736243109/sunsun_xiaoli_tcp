package com.pobing.uilibs.feature.features;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.pobing.uilibs.feature.callback.CanvasCallback;
import com.pobing.uilibs.feature.callback.LayoutCallback;

/**
 * 支持在View中裁剪出圆角菱形区域
 *
 */
public class RotateFeature extends AbsFeature<View> implements CanvasCallback, LayoutCallback {

	private static final String TAG = "RotateFeature";
	
	private static final float sRotateDegree = 45;
	
	private static final float sDefaultRoundX = 20;
	private static final float sDefaultRoundY = 20;
	private static final int sDefaultFrameColor = Color.BLACK;
	private static final int sDefaultFrameWidth = 6;
	
	private float mRoundX = 20;
	private float mRoundY = 20;
	private boolean mFrameEnable = false;

	private Path mPath;
	private Paint mPaint;
	
	private Region dirtyRegion;
		
	@Override
	public void constructor(Context context, AttributeSet attrs, int defStyle) {
		int frameColor = Color.BLACK;
		float frameWidth = 6;
		
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, com.pobing.uilibs.R.styleable.RotateFeature);
            if (null != a) {
                mRoundX = a.getDimension(com.pobing.uilibs.R.styleable.RotateFeature_uik_roundX, sDefaultRoundX);
                mRoundY = a.getDimension(com.pobing.uilibs.R.styleable.RotateFeature_uik_roundY, sDefaultRoundY);
                mFrameEnable = a.getBoolean(com.pobing.uilibs.R.styleable.RotateFeature_uik_frameEnable, false);
                frameColor = a.getColor(com.pobing.uilibs.R.styleable.RotateFeature_uik_frameColor, sDefaultFrameColor);
                frameWidth = a.getDimension(com.pobing.uilibs.R.styleable.RotateFeature_uik_frameWidth, sDefaultFrameWidth);
                a.recycle();
            }
        }
        
        mPaint = new Paint();
        mPaint.setColor(frameColor);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(frameWidth);
        mPaint.setAntiAlias(true);
	}
	
	@Override
	public void beforeOnLayout(boolean changed, int left, int top, int right,
			int bottom) {
		
	}

	@Override
	public void afterOnLayout(boolean changed, int left, int top, int right,
			int bottom) {
		mPath = new Path();
		float width = mHost.getMeasuredWidth() < mHost.getMeasuredHeight() ? 
				mHost.getMeasuredWidth() : mHost.getMeasuredHeight();
				
		float centerX = width / 2;
		float centerY = width / 2;
		
		float tmp = width / 2;
		float smallWidth = (float) Math.sqrt(tmp * tmp + tmp * tmp); 
		
		RectF rect = new RectF();
		rect.set(centerX - smallWidth / 2,  centerY - smallWidth / 2, centerX + smallWidth / 2, centerY + smallWidth / 2);
		
		mPath.addRoundRect(rect, mRoundX, mRoundY, Direction.CCW);
		mPath.setFillType(FillType.INVERSE_WINDING);
		
		Matrix matrix = new Matrix();
		matrix.postRotate(sRotateDegree, centerX, centerY);
		mPath.transform(matrix);
		Log.i(TAG, "afterOnLayout----------rect.left = " + rect.left + " rect.top = " + rect.top 
				+ " rect.bottom = " + rect.bottom + " rect.right = " + rect.right);
		
		dirtyRegion = new Region();
		dirtyRegion.setPath(mPath, new Region(0, 0, mHost.getMeasuredWidth(), mHost.getMeasuredHeight()));
	}
	
	/**
	 * (x, y)是否在mPath内
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contain(int x, int y){
		return dirtyRegion.contains(x, y);
	}

	public void setRoundX(float roundX) {
		mRoundX = roundX;
	}
	
	public void setRoundY(float roundY) {
		mRoundY = roundY;
	}
	
	public void setFrameEnable(boolean enable) {
		mFrameEnable = enable;
	}
	
	public void setFrameWidth(float width) {
		mPaint.setStrokeWidth(width);
	}
	
	public void setFrameColor(int color) {
		mPaint.setColor(color);
	}
	
	@Override
	public void beforeDraw(Canvas canvas) {
		Log.i(TAG, "beforeDraw--------");
		canvas.clipPath(mPath, Op.DIFFERENCE);
		
	}

	@Override
	public void afterDraw(Canvas canvas) {
		Log.i(TAG, "afterDraw--------mFrameEnable = " + mFrameEnable);
		if(mFrameEnable) {
			Path tmpPath = new Path(mPath);
			tmpPath.setFillType(FillType.WINDING);
			canvas.drawPath(tmpPath, mPaint);
		}
	}

	@Override
	public void beforeDispatchDraw(Canvas canvas) {
		
	}

	@Override
	public void afterDispatchDraw(Canvas canvas) {
		
	}

	@Override
	public void beforeOnDraw(Canvas canvas) {
		
	}

	@Override
	public void afterOnDraw(Canvas canvas) {
		
	}

}
