package com.itboye.pondteam.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 圆形背景Textview
 * Created by Mr.w on 2017/6/8.
 */

@SuppressLint("AppCompatCustomView")
public class MyCircleTextView extends TextView {
    Paint paintCircle;
    private String text;
    Paint paintText;

    public MyCircleTextView(Context context) {
        super(context);
        init();
    }

    public MyCircleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        paintCircle = new Paint();
        paintCircle.setStrokeWidth(2);
        paintCircle.setColor(Color.parseColor("#8F8F8F"));
        paintCircle.setAntiAlias(false);
        paintCircle.setStyle(Paint.Style.STROKE);

        paintText = new Paint();
        paintText.setTextSize(28);
        paintText.setColor(Color.parseColor("#8F8F8F"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        paintText.getTextBounds(text, 0, text.length(), rect);
        int r;
        if (rect.width() >= rect.height()) {
            r = rect.width();
        } else {
            r = rect.height();
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, paintCircle);

        canvas.drawText(text, getWidth() / 2 - rect.width()/2 , getHeight() / 2 + rect.height() / 2, paintText);
    }

    public void setMyText(String text) {
        this.text = text;
        invalidate();
    }
}
