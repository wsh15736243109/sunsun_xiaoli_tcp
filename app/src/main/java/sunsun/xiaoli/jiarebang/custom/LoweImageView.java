package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import sunsun.xiaoli.jiarebang.R;


public class LoweImageView extends ImageView {

    /**
     * 图片比例. 比例=宽/高
     */
    private float mRatio;
    private static final int DEFAULT_VALUES = 20;// 默认的值
    private static final int DEFAULT_BACK_LINE_HEIGHT = DEFAULT_VALUES;// 进度条默认高度（无进度）
    private static final int DEFAULT_BACK_LINE_COLOR = 0xffFF8080;// 进度条默认颜色(粉)
    private int back_color = DEFAULT_BACK_LINE_COLOR;

    private static final int DEFAULT_INDICATOR_COLOR = 0xffFF0000;// 指示器默认的颜色(红)
    private int indicator_color = DEFAULT_INDICATOR_COLOR;
    private int indicator_height = dp2px(DEFAULT_INDICATOR_HEIGHT);
    private int triangle_width = indicator_height;// 底边宽的一半
    private static final int DEFAULT_INDICATOR_HEIGHT = DEFAULT_VALUES;// 三角形的底边宽
    private int back_height = dp2px(DEFAULT_BACK_LINE_HEIGHT);
    //画底部节点开始的y坐标
    float y_line = 5;
    //画底部节点高度
    float y_line_height = 15;

    private Paint indicator_paint;// 绘制指示器的画笔
    private float progress;
    private float currentWendu;
    private float minPro,maxPro;

    public LoweImageView(Context context) {
        this(context, null);
    }

    public LoweImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoweImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * dp转px
     *
     * @param dpValues
     * @return
     */
    private int dp2px(int dpValues) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValues, getResources().getDisplayMetrics());
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.LoweImageView);
        mRatio = typedArray.getFloat(R.styleable.LoweImageView_ratio, 0);
        typedArray.recycle();
        // 绘制进度条的底部
        back_paint = new Paint();
        back_paint.setStyle(Paint.Style.FILL);// 填充
        back_paint.setColor(back_color);// 设置颜色
        back_paint.setStrokeWidth(back_height);

        // 绘制指示器的画笔
        indicator_paint = new Paint();
        indicator_paint.setAntiAlias(true);
        indicator_paint.setStyle(Paint.Style.FILL);// 填充
        indicator_paint.setColor(Color.parseColor("#ffffff"));// 设置颜色
    }

    public void setProgress(String wendu) {
        currentWendu = Float.parseFloat(wendu);
    }

    public void setMin(String wendu) {
        minPro = Float.parseFloat(wendu);
    }

    public void setMax(String wendu) {
        maxPro = Float.parseFloat(wendu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 宽模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 宽大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 高大小
        int heightSize;
        // 只有宽的值是精确的才对高做精确的比例校对
        if (widthMode == MeasureSpec.EXACTLY && mRatio > 0) {
            heightSize = (int) (widthSize / mRatio + 0.5f);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private Paint back_paint;// 绘制进度条的底部

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalWidth = getWidth() - 20;
        canvas.translate(getPaddingLeft() - 10, getHeight() / 3);
        back_paint.setColor(getResources().getColor(R.color.type_1));
        canvas.drawLine(0, 0, totalWidth / 8, 0, back_paint);// 底部
        back_paint.setColor(getResources().getColor(R.color.type_2));
        canvas.drawLine(totalWidth / 8, 0, totalWidth / 4, 0, back_paint);// 2
        back_paint.setColor(getResources().getColor(R.color.type_3));
        canvas.drawLine(totalWidth / 4, 0, totalWidth * 3 / 8, 0, back_paint);// 3
        back_paint.setColor(getResources().getColor(R.color.type_4));
        canvas.drawLine(totalWidth * 3 / 8, 0, totalWidth * 4 / 8, 0, back_paint);// 4
        back_paint.setColor(getResources().getColor(R.color.type_5));
        canvas.drawLine(totalWidth * 4 / 8, 0, totalWidth * 5 / 8, 0, back_paint);// 5
        back_paint.setColor(getResources().getColor(R.color.type_6));
        canvas.drawLine(totalWidth * 5 / 8, 0, totalWidth * 6 / 8, 0, back_paint);// 6
        back_paint.setColor(getResources().getColor(R.color.type_7));
        canvas.drawLine(totalWidth * 6 / 8, 0, totalWidth * 7 / 8, 0, back_paint);// 7
        back_paint.setColor(getResources().getColor(R.color.type_8));
        canvas.drawLine(totalWidth * 7 / 8, 0, totalWidth, 0, back_paint);// 8
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setTextSize(15);


        canvas.drawLine(0, y_line + back_height - y_line_height, 0, y_line + back_height, paint);//
        canvas.drawLine(totalWidth * 3 / 8, y_line + back_height - y_line_height, totalWidth * 3 / 8, y_line + back_height, paint);//
        canvas.drawLine(totalWidth * 6 / 8, y_line + back_height - y_line_height, totalWidth * 6 / 8, y_line + back_height, paint);//
        canvas.drawLine(totalWidth - 2, y_line + back_height - y_line_height, totalWidth - 2, y_line + back_height, paint);//
        canvas.drawLine(0, y_line + back_height, totalWidth - 2, y_line + back_height, paint);//
        paint.setTextSize(30);
        Rect rect = new Rect();
        paint.getTextBounds("寒冷", 0, 1, rect);
        canvas.drawText("寒冷", ((totalWidth / 8) + (totalWidth / 4)) / 2 - rect.width(), y_line + back_height + 30, paint);
        canvas.drawText("舒适", ((totalWidth * 4 / 8) + (totalWidth * 5 / 8)) / 2 - rect.width(), y_line + back_height + 30, paint);
        canvas.drawText("过热", totalWidth * 7 / 8 - rect.width(), y_line + back_height + 30, paint);
        progress = totalWidth * ((currentWendu-20)) / 15.0f;
        // 绘制三角形
        setTriangle(canvas, progress);
//        canvas.restore();
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private void setTriangle(Canvas canvas, float progressPosX) {

        // 绘制等边三角形
        float w = triangle_width / 2;
        float h = (float) (triangle_width * Math.sin(45));
        Path path = new Path();
        path.moveTo(progressPosX, -back_height / 2);// 起点
        path.lineTo(w + progressPosX - 10, -h - back_height / 2 + 10);
        path.lineTo(-w + progressPosX + 10, -h - back_height / 2 + 10);
        path.close();
        canvas.drawPath(path, indicator_paint);
        System.out.println("progressPosX" + progressPosX + " triangle_width:" + triangle_width + "  w:" + w);
    }
}