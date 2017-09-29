package com.itboye.pondteam.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.interfaces.SmartConfigTypeSingle;

import static com.itboye.pondteam.interfaces.SmartConfigTypeSingle.NO_UPDTE;
import static com.itboye.pondteam.interfaces.SmartConfigTypeSingle.UPDATE_FAIL;
import static com.itboye.pondteam.interfaces.SmartConfigTypeSingle.UPDATE_FINISH;
import static com.itboye.pondteam.interfaces.SmartConfigTypeSingle.UPDATE_ING;
import static com.itboye.pondteam.interfaces.SmartConfigTypeSingle.UPDATE_INIT;

public class CustomProgressBarSingle extends ProgressBar {

    /**
     * 进度条相关状态
     */
    private SmartConfigTypeSingle smartConfigType;

    public SmartConfigTypeSingle getSmartConfigType() {
        return smartConfigType;
    }

    public void setSmartConfigType(SmartConfigTypeSingle smartConfigType) {
        this.smartConfigType = smartConfigType;
        invalidate();
    }

    public CustomProgressBarSingle(Context context) {
        super(context);

    }

    public CustomProgressBarSingle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomProgressBarSingle(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);

    }

    public CustomProgressBarSingle(Context context, AttributeSet attrs, int defStyle, int styleRes) {

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
        String buttonText = MyApplication.getInstance().getString(R.string.update);
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.shen_red));
        if (smartConfigType == UPDATE_INIT) {
            setProgress(0);
            buttonText = MyApplication.getInstance().getString(R.string.update);

        } else if (smartConfigType == UPDATE_ING) {
            paint.setColor(getResources().getColor(R.color.black));
            buttonText = (getProgress() * 100 / getMax()) + "%";

        } else if (smartConfigType == UPDATE_FINISH) {
            paint.setColor(getResources().getColor(R.color.black));
            setProgress(0);
            buttonText = MyApplication.getInstance().getString(R.string.update_finish);

        } else if (smartConfigType == UPDATE_FAIL) {
            paint.setColor(getResources().getColor(R.color.shen_red));
            setProgress(0);
            buttonText = MyApplication.getInstance().getString(R.string.update_fail);

        } else if (smartConfigType == NO_UPDTE) {
            setProgress(0);
            paint.setColor(getResources().getColor(R.color.black));
            buttonText = MyApplication.getInstance().getString(R.string.ok);

        }
        Rect rect = new Rect();
        paint.getTextBounds(buttonText, 0, buttonText.length(), rect);
        canvas.drawText(buttonText, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);
    }
}
