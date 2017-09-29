package com.itboye.pondteam.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;


/**
 * 自定义的progressBar
 *
 * @author zhl
 */
public class ButtonProgressBar extends View {
    private static final int STYLE_HORIZONTAL = 0;
    private static final int STYLE_ROUND = 1;
    private static final int STYLE_SECTOR = 2;
    /**进度背景画笔**/
//	private Paint mBgpaint;
    /**进度画笔**/
//	private Paint mPrgpaint;
    /**进度文字画笔**/
//	private Paint mTextpaint;
    /**
     * 圆形进度条边框宽度
     **/
    private int strokeWidth = 20;
    /**
     * 进度条中心X坐标
     **/
    private int centerX;
    /**
     * 进度条中心Y坐标
     **/
    private int centerY;
    /**
     * 进度提示文字大小
     **/
    private int percenttextsize = 18;
    /**
     * 进度提示文字颜色
     **/
    private int percenttextcolor = 0xff009ACD;
    /**
     * 进度条背景颜色
     **/
    private int progressBarBgColor = 0xff636363;
    /**
     * 进度颜色
     **/
    private int progressColor = 0xff00C5CD;
    /**
     * 扇形扫描进度的颜色
     */
    private int sectorColor = 0xaaffffff;
    /**
     * 扇形扫描背景
     */
    private int unSweepColor = 0xaa5e5e5e;
    /**
     * 进度条样式（水平/圆形）
     **/
    private int orientation = STYLE_HORIZONTAL;
    /**
     * 圆形进度条半径
     **/
    private int radius = 10;
    /**
     * 进度最大值
     **/
    private int max = 100;
    /**
     * 进度值
     **/
    private int progress = 0;
    /**
     * 水平进度条是否是空心
     **/
    private boolean isHorizonStroke;
    /**
     * 水平进度圆角值
     **/
    private int rectRound = 5;
    /**
     * 进度文字是否显示百分号
     **/
    private boolean showPercentSign;
    private Paint mPaint;
    int status;

    private int mStartAngle_LeftArc = 90;// 左边半圆或弧度的初始角度

    public ButtonProgressBar(Context context) {
        this(context, null);
    }

    public ButtonProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.cbprogressbar);
        percenttextcolor = array.getColor(R.styleable.cbprogressbar_percent_text_color, percenttextcolor);
        progressBarBgColor = array.getColor(R.styleable.cbprogressbar_progressBarBgColor, progressBarBgColor);
        progressColor = array.getColor(R.styleable.cbprogressbar_progressColor, progressColor);
        sectorColor = array.getColor(R.styleable.cbprogressbar_sectorColor, sectorColor);
        unSweepColor = array.getColor(R.styleable.cbprogressbar_unSweepColor, unSweepColor);
        percenttextsize = (int) array.getDimension(R.styleable.cbprogressbar_percent_text_size, percenttextsize);
        strokeWidth = (int) array.getDimension(R.styleable.cbprogressbar_stroke_width, strokeWidth);
        rectRound = (int) array.getDimension(R.styleable.cbprogressbar_rect_round, rectRound);
        orientation = array.getInteger(R.styleable.cbprogressbar_orientation, STYLE_HORIZONTAL);
        isHorizonStroke = array.getBoolean(R.styleable.cbprogressbar_isHorizonStroke, false);
        showPercentSign = array.getBoolean(R.styleable.cbprogressbar_showPercentSign, true);
//		mBgpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		mPrgpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		mTextpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        array.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = centerX - strokeWidth / 2;
        if (orientation == STYLE_HORIZONTAL) {
            drawHoriRectProgressBar(canvas, mPaint);
        } else if (orientation == STYLE_ROUND) {
            drawRoundProgressBar(canvas, mPaint);
        } else {
            drawSectorProgressBar(canvas, mPaint);
        }
    }

    /**
     * 画半圆左侧的任意部分
     */
    private void drawLeftArc(Canvas canvas, Paint paint) {

        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor("#ff00ff"));
        paint1.setTextSize(10);
        float r = 45f;
        float progressBarWidthNowTemp = ((((float) progress / max) * getWidth())) < r ? (((float) progress / max) * getWidth()) : r;// 当前进度条不能超过左边圆的半径
        System.out.println("progressBarWidthNowTemp" + progressBarWidthNowTemp + "  progress" + progress + " max" + max + "  rectROund" + rectRound);
        float leftArcWidth = progressBarWidthNowTemp;
        float left = centerX - getWidth() / 2.f;
        float top = centerY - getHeight() / 2.f;
        float right = 2 * r;
        float bottom = centerY + getHeight() / 2.f;
        RectF rectF = new RectF(left, top, right, bottom);
        System.out.println("left" + left + "  top" + top + "  right" + right + "  bottom" + bottom);
        /**
         * ∠A 指的是 x轴和竖直切线的夹角 demo图见
         * https://code.aliyun.com/hi31588535/outside_chain/raw/master/blog_custom_view_show_pic.png
         */
        double LinBian = r - leftArcWidth;// 直角三角形∠A邻边
        double cosValue = LinBian / r;// cosA=邻边/斜边

        double radian = Math.acos(cosValue);// 反余弦 返回值单位是弧度
        // 用角度表示的角
        double angle = Math.toDegrees(radian);// 转化角度

        float startAngle = (float) (mStartAngle_LeftArc + (90 - angle));
        float sweepAngle = (float) angle * 2;

        // Log.d(TAG, "onDraw: angle" + angle);//直角三角形 锐角A （∠A的） sinA=对边/斜边
        // cosA=邻边/斜边 tanA=对边/邻边
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
//        canvas.drawRect(rectF, paint1);
    }

    /**
     * 绘制圆形进度条
     *
     * @param canvas
     */
    private void drawRoundProgressBar(Canvas canvas, Paint paint) {
        // 初始化画笔属性
        paint.setColor(progressBarBgColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        // 画圆
        canvas.drawCircle(centerX, centerY, radius, paint);
        // 画圆形进度
        paint.setColor(progressColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
//        RectF oval = new RectF(centerX - radius, centerY - radius, radius + centerX, radius + centerY);
//        canvas.drawArc(oval, -90, 360 * progress / max, false, paint);
        // 画进度文字
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(percenttextcolor);
        paint.setTextSize(percenttextsize);

        String percent = (int) (progress * 100 / max) + "%";
        Rect rect = new Rect();
        paint.getTextBounds(percent, 0, percent.length(), rect);
        float textWidth = rect.width();
        float textHeight = rect.height();
        if (textWidth >= radius * 2) {
            textWidth = radius * 2;
        }
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float baseline = (getMeasuredHeight() - metrics.bottom + metrics.top) / 2 - metrics.top;
        canvas.drawText(percent, centerX - textWidth / 2, baseline, paint);

    }

    /**
     * 绘制水平矩形进度条
     *
     * @param canvas
     */
    private void drawHoriRectProgressBar(Canvas canvas, Paint paint) {
        // 初始化画笔属性
        paint.setColor(progressBarBgColor);
        if (isHorizonStroke) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }
        // 画水平矩形
        canvas.drawRoundRect(new RectF(centerX - getWidth() / 2, centerY - getHeight() / 2,
                centerX + getWidth() / 2, centerY + getHeight() / 2), rectRound, rectRound, paint);

        // 画水平进度
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(progressColor);
        if (isHorizonStroke) {
//            paint.setStrokeCap(Paint.Cap.ROUND);
            if (progress >= 22.5) {
                canvas.drawRoundRect(new RectF(centerX - getWidth() / 2, centerY - getHeight() / 2,
                        ((progress * 100 / max) * getWidth()) / 100, centerY + getHeight() / 2), rectRound, rectRound, paint);
            } else {
                //(x-a)^2+(y-b)^2=r^2
//                //给顶面添加背景色
////                Path path1 = new Path();
////                canvas.drawRoundRect(new RectF(centerX - getWidth() / 2,30,15,30),30,getHeight()/2,paint);
                drawLeftArc(canvas, paint);
////                path1.moveTo(0, getHeight()/2);
////                path1.lineTo(45, 0);
////                path1.lineTo(45, getHeight());
////                path1.close();// 封闭
////                canvas.drawPath(path1, paint);
////                canvas.drawCircle(((progress * 100 / max) * getWidth()) / 100, getHeight() / 2, ((progress * 100 / max) * getWidth()) / 100, paint);
////                canvas.drawCircle(45.f, getHeight() / 2, 45, paint);
//                //左半边圆心坐标 45.f, getHeight() / 2
//
////                canvas.drawRoundRect(new RectF(centerX - getWidth() / 2, centerX - getWidth() / 2,
////                        ((progress * 100 / max) * getWidth()) / 100, centerY + getHeight() / 2), rectRound, rectRound, paint);
            }
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawRoundRect(new RectF(centerX - getWidth() / 2, centerY - getHeight() / 2,
                    ((progress * 100 / max) * getWidth()) / 100, centerY + getHeight() / 2), rectRound, rectRound, paint);
            paint.setXfermode(null);
        }

        // 画进度文字
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(percenttextcolor);
        paint.setTextSize(percenttextsize);
        String percent = (int) (progress * 100 / max) + "%";
        Rect rect = new Rect();
        paint.getTextBounds(percent, 0, percent.length(), rect);
        float textWidth = rect.width();
        float textHeight = rect.height();
        if (textWidth >= getWidth()) {
            textWidth = getWidth();
        }
        Paint.FontMetrics metrics = paint.getFontMetrics();
        Paint paintBegin = new Paint();
        paintBegin.setTextSize(30);
        paintBegin.setAntiAlias(true);
        float baseline = (getMeasuredHeight() - metrics.bottom + metrics.top) / 2 - metrics.top;
        if (status == 1) {
            paintBegin.setColor(getResources().getColor(R.color.shen_red));
            String text = MyApplication.getInstance().getString(R.string.update);
            Rect rect2 = new Rect();
            paintBegin.getTextBounds(text, 0, text.length(), rect2);
            canvas.drawText(MyApplication.getInstance().getString(R.string.update), centerX - rect2.width() / 2, baseline, paintBegin);
        } else if (status == 2) {
            paintBegin.setColor(getResources().getColor(R.color.progressbar_bg_color_1));
            String text = MyApplication.getInstance().getString(R.string.ok);
            Rect rect2 = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect2);
            canvas.drawText(MyApplication.getInstance().getString(R.string.ok), centerX - rect2.width() / 2, baseline, paint);
        } else if (status == 4) {
            //更新完成
            String text = MyApplication.getInstance().getString(R.string.update_finish);
            Rect rect2 = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect2);
            canvas.drawText(text, centerX - rect2.width() / 2, baseline, paint);
            // 画水平矩形
//            paint.setColor(Color.parseColor("#ffffff"));
//            canvas.drawRoundRect(new RectF(centerX - getWidth() / 2, centerY - getHeight() / 2,
//                    centerX + getWidth() / 2, centerY + getHeight() / 2), rectRound, rectRound, paint);
        } else if (status == 5) {
            //更新失败
            String text = MyApplication.getInstance().getString(R.string.update_fail);
            Rect rect2 = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect2);
            canvas.drawText(text, centerX - rect2.width() / 2, baseline, paint);
        } else if (status == 3) {
            canvas.drawText(percent, centerX - textWidth / 2, baseline, paint);
        } else if (status == 0) {
            canvas.drawText("确定", centerX - textWidth / 2, baseline, paint);
        }

    }

    /**
     * 绘制扇形扫描式进度
     *
     * @param canvas
     * @param piant
     */
    private void drawSectorProgressBar(Canvas canvas, Paint piant) {
        // 初始化画笔属性
        piant.setColor(sectorColor);
        piant.setStyle(Paint.Style.STROKE);
        piant.setStrokeWidth(2);
        // 绘外圈
        canvas.drawCircle(centerX, centerY, radius, piant);
        // 绘内圈
        piant.setColor(unSweepColor);
        piant.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius - 2, piant);
        piant.setColor(sectorColor);
        RectF oval = new RectF(centerX - radius + 2, centerY - radius + 2, radius + centerX - 2, radius + centerY - 2);
        canvas.drawArc(oval, -90, 360 * progress / max, true, piant);
    }

    public void setProgress(int progress) {
        if (progress > max) {
            progress = max;
        } else {
            this.progress = progress;
            postInvalidate();
        }
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getPercenttextsize() {
        return percenttextsize;
    }

    public void setPercenttextsize(int percenttextsize) {
        this.percenttextsize = percenttextsize;
    }

    public int getPercenttextcolor() {
        return percenttextcolor;
    }

    public void setPercenttextcolor(int percenttextcolor) {
        this.percenttextcolor = percenttextcolor;
    }

    public int getProgressBarBgColor() {
        return progressBarBgColor;
    }

    public void setProgressBarBgColor(int progressBarBgColor) {
        this.progressBarBgColor = progressBarBgColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean isHorizonStroke() {
        return isHorizonStroke;
    }

    public void setHorizonStroke(boolean isHorizonStroke) {
        this.isHorizonStroke = isHorizonStroke;
    }

    public int getRectRound() {
        return rectRound;
    }

    public void setRectRound(int rectRound) {
        this.rectRound = rectRound;
    }

    public int getMax() {
        return max;
    }

    public int getProgress() {
        return progress;
    }


}
