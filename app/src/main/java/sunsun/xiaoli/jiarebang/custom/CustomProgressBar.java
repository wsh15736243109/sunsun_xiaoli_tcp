package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.itboye.pondteam.app.MyApplication;

import sunsun.xiaoli.jiarebang.interfaces.SmartConfigType;

public class CustomProgressBar extends ProgressBar {

	/**
	 * 进度条相关状态
	 */
	private SmartConfigType smartConfigType;

	public SmartConfigType getSmartConfigType() {
		return smartConfigType;
	}

	public void setSmartConfigType(SmartConfigType smartConfigType) {
		this.smartConfigType = smartConfigType;
		invalidate();
	}

	public CustomProgressBar(Context context) {
		super(context);

	}

	public CustomProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle,0);

	}

	public CustomProgressBar(Context context, AttributeSet attrs, int defStyle, int styleRes) {
        
		super(context, attrs, defStyle);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		String buttonText= MyApplication.getInstance().getString(com.itboye.pondteam.R.string.update);
		Paint paint=new Paint();
		paint.setTextSize(25);
		paint.setAntiAlias(true);
		paint.setColor(getResources().getColor(com.itboye.pondteam.R.color.shen_red));
		switch (smartConfigType) {
			case UPDATE_INIT:
				setProgress(0);
				buttonText= MyApplication.getInstance().getString(com.itboye.pondteam.R.string.update);
				break;
			case UPDATE_ING:
				paint.setColor(getResources().getColor(com.itboye.pondteam.R.color.black));
				buttonText= (getProgress() * 100 / getMax()) + "%";
				break;
			case UPDATE_FINISH:
				paint.setColor(getResources().getColor(com.itboye.pondteam.R.color.black));
				setProgress(0);
				buttonText= MyApplication.getInstance().getString(com.itboye.pondteam.R.string.update_finish);
				break;
			case UPDATE_FAIL:
				paint.setColor(getResources().getColor(com.itboye.pondteam.R.color.shen_red));
				setProgress(0);
				buttonText= MyApplication.getInstance().getString(com.itboye.pondteam.R.string.update_fail);
				break;
			case NO_UPDTE:
				setProgress(0);
				paint.setColor(getResources().getColor(com.itboye.pondteam.R.color.black));
				buttonText= MyApplication.getInstance().getString(com.itboye.pondteam.R.string.ok);
				break;
		}
		Rect rect = new Rect();
		paint.getTextBounds(buttonText, 0, buttonText.length(), rect);
		canvas.drawText(buttonText, getWidth()/2-rect.width()/2, getHeight()/2, paint);
	}
}
