package com.itboye.pondteam.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;

/***
 * 版本更新下载进度条
 *
 * @author itboye
 */
public class DeviceProgress extends ProgressBar {
    String text;
    Paint mPaint;

    public DeviceProgress(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        System.out.println("1");
        initText();
    }

    public DeviceProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        System.out.println("2");
        initText();
    }

    public DeviceProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        System.out.println("3");
        initText();
    }

    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText(progress);
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    // ��ʼ��������
    private void initText() {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.BLACK);
        this.mPaint.setTextSize(14);
        // ��ԭ���ߵĻ��������������´��룬��Ϊ�ڸ߷ֱ��ʵ��ֻ��£��������������̫С
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        if (displayWidth > 480) {
            this.mPaint.setTextSize(36);
        } else {
            this.mPaint.setTextSize(18);
        }

    }

    private void setText() {
        setText(this.getProgress());
    }

    // ������������
    private void setText(int progress) {
        int i = (progress * 100) / this.getMax();
        text = String.valueOf(i) + "%";

    }

    /**
     * @param context
     * @return float
     * @Description: TODO
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * @param context
     * @return float
     * @Description: TODO
     */
    public static int getScreenHeight(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

}


/*********
 * <com.feng.DeviceProgress
 * android:id="@+id/pgsBar"
 * style="?android:attr/progressBarStyleHorizontal"
 * android:layout_width="fill_parent"
 * android:layout_height="35dp"
 * android:layout_marginLeft="20dp"
 * android:layout_marginRight="20dp"
 * android:layout_gravity="bottom"
 * android:layout_marginTop="80px"
 * android:progressDrawable="@drawable/progressbar"
 * android:max="100" />
 * <p>
 * java代码
 * private DeviceProgress myProgress = null;
 * <p>
 * private void addListener(){
 * btn_go.setOnClickListener(new OnClickListener() {
 *
 * @Override public void onClick(View v) {
 * // TODO Auto-generated method stub
 * new Thread(new Runnable() {
 * @Override public void run() {
 * // TODO Auto-generated method stub
 * for(int i = 0;i <=50; i++){
 * mHandler.sendEmptyMessage(i * 2);
 * try {
 * Thread.sleep(100);
 * } catch (InterruptedException e) {
 * // TODO Auto-generated catch block
 * e.printStackTrace();
 * }
 * }
 * }
 * }).start();
 * }
 * });
 * }
 ******/
