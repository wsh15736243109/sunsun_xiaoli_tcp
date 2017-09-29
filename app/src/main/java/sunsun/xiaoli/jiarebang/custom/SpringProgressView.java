package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class SpringProgressView extends View {

    /**
     * 渐变颜色区域
     */
    private static final int[] SECTION_COLORS = {Color.BLUE, Color.YELLOW,
            Color.RED};
    /**
     * 最大值
     */
    private float maxCount;
    /**
     * 当前进度
     */
    private float currentCount;
    private Paint mPaint;
    private Paint mPaintCircle;
    private int mWidth, mHeight;
    /**
     * 进度条高度
     */
    private int seekHeight = 20;

    public SpringProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SpringProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SpringProgressView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        int round = mHeight / 2;
        seekHeight = 20;
        maxCount = (maxCount == 0 ? 100.f : maxCount);
        mPaint.setColor(Color.WHITE);

        float section = 1;
        RectF rectProgressBg = new RectF(3, 10, (mWidth - 3) * section,
                mHeight - 10);
        seekHeight = getHeight() - 20;
        int count = 3;
        int[] colors = new int[count];
        System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
        float[] positions = new float[count];
        if (count == 2) {
            positions[0] = 0.0f;
            positions[1] = 1.0f - positions[0];
        } else {
            positions[0] = 0.0f;
            positions[1] = (maxCount / 3) / currentCount;
            positions[2] = 1.0f - positions[0] * 2;
        }
        positions[positions.length - 1] = 1.0f;
        LinearGradient shader = new LinearGradient(3, 3, (mWidth - 3)
                * section, mHeight - 3, colors, null,
                Shader.TileMode.MIRROR);
        mPaint.setShader(shader);
//		}
        int progress = getWidth() * (((int) currentCount - 20)) / (15);
        //设置边框颜色
        Paint paintBord = new Paint();
        paintBord.setColor(Color.WHITE);
        paintBord.setStyle(Paint.Style.STROKE);
        paintBord.setStrokeWidth(2);
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
        canvas.drawRoundRect(rectProgressBg, round, round, paintBord);
        if (progress < getPaddingLeft()) {
            canvas.drawCircle(getPaddingLeft(), getHeight() / 2, getHeight() / 2, mPaintCircle);
        }else if(progress >= getWidth()-getPaddingLeft()){
            canvas.drawCircle(getWidth()-getPaddingLeft(), getHeight() / 2, getHeight() / 2, mPaintCircle);
        }else{
            canvas.drawCircle(progress, getHeight() / 2, getHeight() / 2, mPaintCircle);
        }
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY
                || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

}