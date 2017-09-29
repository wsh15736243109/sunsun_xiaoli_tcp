package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import sunsun.xiaoli.jiarebang.R;


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
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize;
		if (widthMode == MeasureSpec.EXACTLY && mRatio > 0) {
			heightSize = (int) (widthSize / mRatio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
					MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setmRatio(float mRatio,int widthMeasureSpec, int heightMeasureSpec){
		this.mRatio=mRatio;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize;
		if (widthMode == MeasureSpec.EXACTLY && mRatio > 0) {
			heightSize = (int) (widthSize / mRatio + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
					MeasureSpec.EXACTLY);
		}
		measure(widthMeasureSpec, heightMeasureSpec);
	}
}