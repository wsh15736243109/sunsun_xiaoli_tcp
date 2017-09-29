package sunsun.xiaoli.jiarebang.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import java.io.File;

import sunsun.xiaoli.jiarebang.R;


@SuppressLint("AppCompatCustomView")
public class ShuiBengButtomImageView extends ImageView {

    /**
     * 图片比例. 比例=宽/高
     */
    private float mRatio;


    Paint paintInner;//最内部的
    Paint paintSecond;//第二层
    Paint paintOut;//最外层
    Paint paintFont;//文字画刷
    Paint paintSmallCircle;//圆外层的小圆
    float r_1 = 10, r_2 = 20, r_3 = 30, r_small = 5;//从内到外的半径
    private String secondColor = "#D9D8DE";//第二层颜色
    private String outColor = "#8B8B8B";//最外层颜色
    double angle = 45;//最外层圆点扫过的度数
    private int state;
    private int dangwei;
    private Typeface font;

    public ShuiBengButtomImageView(Context context) {
        this(context, null);
    }

    public ShuiBengButtomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShuiBengButtomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        paintInner = new Paint();
        paintInner.setStyle(Paint.Style.FILL);
        paintInner.setAntiAlias(true);
        paintInner.setColor(getResources().getColor(R.color.main_green));
        paintOut = new Paint();
        paintOut.setAntiAlias(true);
        paintOut.setColor(Color.parseColor(outColor));
        paintSecond = new Paint();
        paintSecond.setAntiAlias(true);
        paintSecond.setColor(Color.parseColor(secondColor));
        paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setTextSize(20);
        paintFont.setColor(Color.WHITE);
        paintSmallCircle = new Paint();
        paintSmallCircle.setAntiAlias(true);
        paintSmallCircle.setColor(getResources().getColor(R.color.white));
        paintSmallCircle.setStyle(Paint.Style.FILL);
        setDangWei(0);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setBackgroundColor(getResources().getColor(R.color.red));
        r_1 = getWidth() / 6;
        r_2 = getWidth() / 3;
        r_3 = getWidth() / 2 - r_small * 2;


        drawCircle(canvas, state);

    }

    public void setDangWei(int dangWei) {
        this.dangwei = dangWei;
//        invalidate();
    }

    private void drawCircle(Canvas canvas, int status) {
        Rect rect = new Rect();
        String text = "";

        //上（0档位）
        if (dangwei == 0 && state != 2) {
            paintSmallCircle.setColor(getResources().getColor(R.color.red500));
            canvas.drawCircle(getWidth() / 2, (getHeight() - 2 * r_3) - 3 * r_small, r_small, paintSmallCircle);
        } else {
            paintSmallCircle.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle(getWidth() / 2, (getHeight() - 2 * r_3) - 3 * r_small, r_small, paintSmallCircle);
        }
        //下（3档位）
        if (dangwei == 3 && state != 2) {
            paintSmallCircle.setColor(getResources().getColor(R.color.red500));
            canvas.drawCircle((float) (getWidth() / 2), (getHeight() - r_small), r_small, paintSmallCircle);
        } else {
            paintSmallCircle.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle((float) (getWidth() / 2), (getHeight() - r_small), r_small, paintSmallCircle);
        }
        //右上（1档位）
        if (dangwei == 1 && state != 2) {
            paintSmallCircle.setColor(getResources().getColor(R.color.red500));
            canvas.drawCircle((float) (getWidth() / 2 + (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 - (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        } else {
            paintSmallCircle.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle((float) (getWidth() / 2 + (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 - (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        }
        //右下（2档位）
        if (dangwei == 2 && state != 2) {
            paintSmallCircle.setColor(getResources().getColor(R.color.red500));
            canvas.drawCircle((float) (getWidth() / 2 + (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 + (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        } else {
            paintSmallCircle.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle((float) (getWidth() / 2 + (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 + (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        }
        //左下（4档位）
        if (dangwei == 4 && state != 2) {
            paintSmallCircle.setColor(getResources().getColor(R.color.red500));
            canvas.drawCircle((float) (getWidth() / 2 - (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 + (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        } else {
            paintSmallCircle.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle((float) (getWidth() / 2 - (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 + (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        }
        //左上（5档位）
        if (dangwei == 5 && state != 2) {
            paintSmallCircle.setColor(getResources().getColor(R.color.red500));
            canvas.drawCircle((float) (getWidth() / 2 - (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 - (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        } else {
            paintSmallCircle.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle((float) (getWidth() / 2 - (r_3 + r_small) * Math.cos(angle * Math.PI / 180)), (float) (getHeight() / 2 - (r_3 + r_small) * Math.sin(angle * Math.PI / 180)), r_small, paintSmallCircle);
        }

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r_3 - 5, paintOut);


        Paint paintState = new Paint();
        paintState.setStyle(Paint.Style.FILL);
        paintState.setAntiAlias(true);
        if (state == 0 || state == 2) {
//            angle = 45;
            if (state == 0) {
//                text = "STOP";
                angle = 30;
                text = dangwei + "";
                paintState.setColor(getResources().getColor(R.color.main_green));
            } else {
                text = "ERR";
                dangwei = 6;
                angle = 45;
                paintState.setColor(getResources().getColor(R.color.red500));
                //右
                canvas.drawCircle((getWidth() - r_small), (float) (getHeight() / 2), r_small, paintSmallCircle);
                //左
                canvas.drawCircle(r_small, (getHeight() / 2 + r_small), r_small, paintSmallCircle);
            }

            paintFont.setTextSize(40);
            paintFont.getTextBounds(text, 0, text.length(), rect);

        } else if (state == 1) {
            text = dangwei + "";
            angle = 30;
            paintState.setColor(getResources().getColor(R.color.main_green));
            paintFont.setTextSize(40);
            paintFont.getTextBounds(text, 0, text.length(), rect);
        } else {

        }
        RectF rectDangwei = new RectF(getWidth() / 2 - r_3 + 5, getWidth() / 2 - r_3 + 5, getWidth() / 2 + (int) r_3 - 5, getWidth() / 2 + (int) r_3 - 5);
        if (state == 0) {

        } else {
            canvas.drawArc(rectDangwei, -90, (float) dangwei * 60, true, paintState);
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r_2, paintSecond);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r_1, paintInner);
        String FONT_DIGITAL_7 = "fonts" + File.separator
                + "digital-7.ttf";//定义字体文件路径。

        AssetManager assets = getResources().getAssets();

        paintFont.setTypeface(font);

        font = Typeface.createFromAsset(assets, FONT_DIGITAL_7);
        paintFont.getTextBounds(text, 0, text.length(), rect);
        if (dangwei != 1) {
            canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paintFont);
        } else {
            canvas.drawText(text, getWidth() / 2 - rect.width() * 4, getHeight() / 2 + rect.height() / 2, paintFont);
        }
    }

    public void setState(int state) {
        this.state = state;
        invalidate();
    }
}