package sunsun.xiaoli.jiarebang.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * MyTextView
 * <p>
 * Created by Mr.w on 2018/3/8.
 * <p>
 * 版本      ${version}
 * <p>
 * 修改时间
 * <p>
 * 修改内容
 */


@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v("request_params","要改变");
    }

    public boolean isExceed() {
        return isExceed;
    }

    public void setExceed(boolean exceed) {
        isExceed = exceed;
        invalidate();
    }

    boolean isExceed = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
//        mPaint.setTextSize(getTextSize());
        mPaint.setColor(getResources().getColor(android.R.color.holo_orange_dark));
        String text = "点击进入";
        Rect rect = new Rect();

        float density = getResources().getDisplayMetrics().density;
        mPaint.setTextSize(getTextSize());
        mPaint.setAntiAlias(true);
        mPaint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width();//文本的宽度
        int height = rect.height();//文本的高度
        int count = getLineCount();
        String content = getText().toString();
        Rect rectContent = new Rect();
        getPaint().getTextBounds(content, 0, content.length(), rectContent);
        if (count == 1) {
            if (getPaint().measureText(getText().toString()) + width+6 >= getRight()) {
                if (isExceed == false) {
                    setHeight(getHeight() * 2);
                    isExceed = true;
                }
            } else {
            }
        } else if (count >= 2) {
            if (count == 2) {
                try {
                    if (getLayout().getLineWidth(1) + width >= getRight()) {
                        if (isExceed == false) {
                            setHeight(getHeight() + getHeight() / 2);
                            isExceed = true;
                        } else {
                        }
                    }
                } catch (Exception e) {

                }

            } else {
                if (isExceed == false) {
                    String result = "";
                    for (int i = 0; i < 2; i++) {
                        int start = getLayout().getLineStart(i);
                        int end = getLayout().getLineEnd(i);
                        result += getText().toString().substring(start, end);
                    }
                    setText(result);
                    setHeight(getHeight() + getHeight()/2);
                    isExceed = true;
                } else {
                }
            }

        } else {
            if (isExceed == false) {
                setHeight(getHeight() * 2);
                isExceed = true;
            } else {

            }
        }

        Log.v("textView_params", "getY(): " + getY() + " getHeight():" + getHeight() + " height: " + height + " getPaddingTop(): " + getTopPaddingOffset() + " getPaddingBottom(): " + getBottomPaddingOffset());
//        canvas.drawText(text, getRight() - width - getPaddingRight(), getY() + height, mPaint);
        canvas.drawText(text, getRight() - width - getPaddingRight()-6, getHeight() - 6, mPaint);
    }
}
