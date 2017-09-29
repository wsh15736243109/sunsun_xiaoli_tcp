package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mr.w on 2017/3/18.
 */

public class ButtonOpenOrClose extends View {
    Paint paintOrder;
    public ButtonOpenOrClose(Context context) {
        super(context);
    }

    public ButtonOpenOrClose(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonOpenOrClose(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
