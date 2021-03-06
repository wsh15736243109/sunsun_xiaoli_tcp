package com.itboye.pondteam.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.itboye.pondteam.R;


public class LoweRelaLayout extends RelativeLayout {

	/**
	 */
	private float mRatio;

	public LoweRelaLayout(Context context) {
		this(context, null);
	}

	public LoweRelaLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoweRelaLayout(Context context, AttributeSet attrs,
						  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 * @param attrs
	 */
	private void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.LoweImageView);
		mRatio = typedArray.getFloat(R.styleable.LoweImageView_ratio, 0);
		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// ��ģʽ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// ���С
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		// �ߴ�С
		int heightSize;
		// ֻ�п��ֵ�Ǿ�ȷ�ĲŶԸ�����ȷ�ı���У��
		if (widthMode == MeasureSpec.EXACTLY && mRatio > 0) {
			heightSize = (int) (widthSize / mRatio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
					MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}