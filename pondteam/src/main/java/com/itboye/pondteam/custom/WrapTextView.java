/*
 * @author Jerry
 * @time 2015-12-29 上午10:14:34
 */
package com.itboye.pondteam.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 无上下边距的TextView
 * 
 */
public class WrapTextView extends TextView
{
	private float		mWidth;

	private float		mHeight;

	private FontMetrics	mFontMetrics;

	public WrapTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		init();
	}

	public WrapTextView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public WrapTextView(Context context)
	{
		this(context, null);
	}

	/**
	 * 初始化
	 * 
	 * @return void
	 * @author Jerry
	 * @time 2015-12-29 上午10:16:09
	 */
	private void init()
	{
		TextPaint paint = getPaint();
		mWidth = paint.measureText(getText().toString());
		mFontMetrics = paint.getFontMetrics();

		// 上边距为负数 相加求文字高度
		mHeight = -(mFontMetrics.ascent + mFontMetrics.descent);

		// 可以显示全像g这样的字母，但是下边会空出来一部分
//		mHeight = -mFontMetrics.ascent;

	}

	/* （非 Javadoc）
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int desWidth = MeasureSpec.getSize(widthMeasureSpec);

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int desHeight = MeasureSpec.getSize(heightMeasureSpec);

		switch (widthMode)
		{
			case MeasureSpec.EXACTLY:
			case MeasureSpec.UNSPECIFIED:

				mWidth = desWidth;

				break;

			case MeasureSpec.AT_MOST:

				mWidth = mWidth > desWidth ? desWidth : mWidth;

				break;
		}

		switch (heightMode)
		{
			case MeasureSpec.EXACTLY:
			case MeasureSpec.UNSPECIFIED:

				mHeight = desHeight;

				break;

			case MeasureSpec.AT_MOST:
				mHeight = mHeight > desHeight ? desHeight : mHeight + 1;

				break;
		}
		setMeasuredDimension((int) Math.ceil(mWidth), (int) Math.ceil(mHeight));
	}

	/* （非 Javadoc）
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		TextPaint paint = getPaint();
		paint.setColor(getCurrentTextColor());
		// 不能显示全像g这样的字母
		canvas.drawText(getText().toString(), 0, mHeight - 2, paint);

		// 可以显示全像g这样的字母，但是下边会空出来一部分
//		canvas.drawText(getText().toString(), 0, mHeight - 2 - mFontMetrics.descent, getPaint());
	}
}
