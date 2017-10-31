package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;

import com.itboye.pondteam.bean.TemperatureHistoryBean;
import com.itboye.pondteam.utils.NumberUtils;
import com.itboye.pondteam.utils.ScreenUtil;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.ActivityTemperature;

import static com.itboye.pondteam.utils.CaculateDays.formatTimesYMD2MD;
import static com.itboye.pondteam.utils.CaculateDays.formatTimesYMDH2H;
import static com.itboye.pondteam.utils.CaculateDays.getWeek;
import static com.itboye.pondteam.utils.CaculateDays.judgeIsToday2;
import static com.itboye.pondteam.utils.NumberUtils.parse2Float;

/**
 * Created by Mr.w on 2017/3/4.
 */

public class HistoryWaterTemperatureView extends View {
    Paint paintPoint_normal;
    Paint paintPoint_yichang;
    Paint paintLine;
    Paint paintNormal;
    Paint paintError;
    Paint paintTextGreen;
    Paint paintTextRed;
    Paint paintY;
    Paint paintLianXian;
    //y轴 #CACACA
    Paint paintY_shuxian;
    //异常数据 #F21B2F
    Paint paint_yichang;
    //x轴上的按钮文字状态
    Paint text_normal_or_no;
    int maxY;//Y轴最大刻度值
    int maxPoint_Y = getBottom() / 2 - 200;//Y轴最低处坐标
    int keduX;//X轴间隔
    int keduY;//Y轴间隔
    private double keduY2;
    ArrayList<TemperatureHistoryBean> array = new ArrayList<>();
    //最左下角x坐标
    private int min_left_x = 0;
    ArrayList<PointF> ar = new ArrayList<>();
    /**
     * dataType:1、日   2、周   3、月
     */
    private int dataType = 1;
    private int bottomTemperatue, topTemperature;
    private boolean isPh = false;
    public boolean setIsUpdate;
    private ActivityTemperature activityTemperature;
    private float maxX;

    public HistoryWaterTemperatureView(Context context) {
        super(context);
        init();
    }

    public void setIsPh(boolean isPh) {
        this.isPh = isPh;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setMaxY(int max) {
        this.maxY = max;
    }

    public void setWarnTemperature(int top, int bottom) {
        this.topTemperature = top;
        this.bottomTemperatue = bottom;
    }

    public void setArray(ArrayList<TemperatureHistoryBean> array) {
        this.array = array;
        invalidate();
    }

    private void init() {
        ar = new ArrayList<>();
        paintPoint_normal = new Paint();
        paintLine = new Paint();
        paintNormal = new Paint();
        paintError = new Paint();
        paintTextGreen = new Paint();
        paintTextRed = new Paint();
        paintY = new Paint();
        text_normal_or_no = new Paint();
        text_normal_or_no.setTextSize(30);
        text_normal_or_no.setAntiAlias(true);
        text_normal_or_no.setColor(getResources().getColor(R.color.white));//点的颜色


        paintY_shuxian = new Paint();
        paintY_shuxian.setColor(Color.parseColor("#CACACA"));
        paintY_shuxian.setColor(Color.parseColor("#CACACA"));
        paint_yichang = new Paint();
        paint_yichang.setColor(Color.parseColor("#F21B2F"));
        paint_yichang.setTextSize(30);

        paintPoint_normal.setTextSize(30);
        paintPoint_normal.setStrokeWidth(15);//点直径
        paintPoint_normal.setAntiAlias(true);
        paintPoint_normal.setColor(getResources().getColor(R.color.shenlv));//点的颜色

        paintPoint_yichang = new Paint();
        paintPoint_yichang.setTextSize(30);
        paintPoint_yichang.setStrokeWidth(15);//点直径
        paintPoint_yichang.setAntiAlias(true);
        paintPoint_yichang.setColor(Color.parseColor("#F21B2F"));//点的颜色

        paintLianXian = new Paint();
        paintLianXian.setTextSize(2);
        paintLianXian.setStrokeWidth(2);//点直径
        paintLianXian.setAntiAlias(true);
        paintLianXian.setColor(getResources().getColor(R.color.shenlv));//点的颜色
        paintLine.setAntiAlias(true);
        paintLine.setColor(getResources().getColor(R.color.colorAccent));
        maxPoint_Y = getBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        maxPoint_Y = getBottom();
        int innerMaxY = maxY * 2;
        if (!isPh) {
            keduX = 120;
            min_left_x = keduX / 2;
            keduY = (maxPoint_Y - min_left_x) / 11;
            keduY2 = innerMaxY / 10;
            int start_Y = innerMaxY;
            int pointY = 0;
            ar = new ArrayList<>();
            //画Y轴
            for (int i = 0; i < 10; i++) {
                pointY += keduY;
//            canvas.drawText(start_Y + "", min_left_x, pointY, paintPoint_normal);
//            canvas.drawLine(10, pointY, getWidth(), pointY, paintLine);
                start_Y -= keduY2;
            }
            //画X轴
            for (int i = 0; i < array.size(); i++) {
                float y = ((innerMaxY - Float.parseFloat(array.get(i).getAvg_temp()) / 10.f) / (float) keduY2 * (float) keduY + keduY) - 100;

                float x = (i) * keduX + min_left_x;
                PointF pointF = new PointF(x, y);
                ar.add(pointF);
//                canvas.drawText(i + "", i * keduX + min_left_x, maxPoint_Y, paintPoint_normal);
            }
            for (int i = 0; i < ar.size(); i++) {
                float y = ((innerMaxY - Float.parseFloat(array.get(i).getAvg_temp()) / 10.f) / (float) keduY2 * (float) keduY + keduY) - 100;
                if (y >= maxPoint_Y) {
                    y = 230;
                }
                String value = parse2Float(array.get(i).getAvg_temp(), true);
                RectF rect = new RectF((int) (ar.get(i).x - min_left_x + 10), (int) (maxPoint_Y - min_left_x), (int) (ar.get(i).x + min_left_x - 10), (int) (maxPoint_Y));
                Rect rectMessure = new Rect();
                float pointX = i * (float) keduX + min_left_x;
                if (Float.parseFloat(array.get(i).getAvg_temp()) / 10.f < topTemperature && Float.parseFloat(array.get(i).getAvg_temp()) / 10.f > bottomTemperatue) {
                    //画点
                    canvas.drawCircle(pointX, y, 4.0f, paintPoint_normal);
                    rectMessure = new Rect();
                    text_normal_or_no.getTextBounds(App.getInstance().getString(R.string.trend_normal), 0, 1, rectMessure);
                    canvas.drawRoundRect(rect, 10, 10, paintPoint_normal);//画圆角矩形
                    canvas.drawText(App.getInstance().getString(R.string.trend_normal), rect.centerX() - rectMessure.width(), rect.centerY() + rectMessure.height() / 2, text_normal_or_no);
                    rectMessure = new Rect();
                    paintPoint_normal.getTextBounds(value, 0, value.length(), rectMessure);
                    canvas.drawText(value, (i + 1) * keduX - min_left_x - rectMessure.width() / 2, y + rectMessure.height() + 10, paintPoint_normal);
                } else {
                    //画点
                    canvas.drawCircle(pointX, y, 4.0f, paintPoint_yichang);
                    rectMessure = new Rect();
                    paint_yichang.getTextBounds(App.getInstance().getString(R.string.trend_abnormal), 0, 1, rectMessure);
                    canvas.drawRoundRect(rect, 10, 10, paint_yichang);//画圆角矩形
                    canvas.drawText(App.getInstance().getString(R.string.trend_abnormal), rect.centerX() - rectMessure.width(), rect.centerY() + rectMessure.height() / 2, text_normal_or_no);
                    rectMessure = new Rect();
                    paint_yichang.getTextBounds(value, 0, value.length(), rectMessure);
                    canvas.drawText(value + "", (i + 1) * keduX - min_left_x - rectMessure.width() / 2, y + rectMessure.height() + 10, paint_yichang);
                }
                Rect rectDate = new Rect();
                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#858585"));
                paint.setTextSize(30);

                paint.setAntiAlias(true);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                String week = getWeek(array.get(i).getHis_date());
                if (week.equals("yesterday")) {
                    week = "yday";
                }
                String date = "";
                paint.getTextBounds(week, 0, week.length(), rectDate);
                if (dataType == 1) {
                    //当天(区分今天、昨天)
                    String today = judgeIsToday2(array.get(i).getHis_date());
                    if (today.equals("yesterday")) {
                        today = "yday";
                    }
                    paint.getTextBounds(today, 0, today.length(), rectDate);
                    date = formatTimesYMDH2H(array.get(i).getHis_date());
                    if (date.equals("yesterday")) {
                        date = "yday";
                    }
                    canvas.drawText(today, pointX - rectDate.width() / 2, min_left_x, paint);
                } else {
                    //周或日
                    date = formatTimesYMD2MD(array.get(i).getHis_date());
                    if (date.equals("yesterday")) {
                        date = "yday";
                    }
                    canvas.drawText(week, pointX - rectDate.width() / 2, min_left_x, paint);

                }
                paint.getTextBounds(date, 0, date.length(), rectDate);
                canvas.drawText(date, pointX - rectDate.width() / 2, min_left_x + rectDate.height() + 10, paint);
                if (i != 0) {
                    canvas.drawLine(ar.get(i).x, ar.get(i).y, ar.get(i - 1).x, ar.get(i - 1).y, paintLianXian);
                }
                maxX = rect.right + 10;
                canvas.drawLine(maxX, min_left_x, maxX, maxPoint_Y, paintY_shuxian);
            }
        } else {
//            ------------------------------------------------ph分割线--------------------------------------------------------------------------
//            maxPoint_Y = getBottom();
            keduX = 120;
            min_left_x = keduX / 2;
            keduY = (maxPoint_Y - min_left_x) / 11;
            keduY2 = innerMaxY / 10.0;
            int start_Y = innerMaxY;
            int pointY = 0;
            ar = new ArrayList<>();
            //画Y轴
            for (int i = 0; i < 10; i++) {
                pointY += keduY;
//            canvas.drawText(start_Y + "", min_left_x, pointY, paintPoint_normal);
//            canvas.drawLine(10, pointY, getWidth(), pointY, paintLine);
                start_Y -= keduY2;
            }
            //计算Xy坐标
            for (int i = 0; i < array.size(); i++) {
                float y = ((innerMaxY - Float.parseFloat(array.get(i).getAvg_ph()) / 100.f) / (float) keduY2 * (float) keduY + keduY) - 40;

                float x = (i) * keduX + min_left_x;
                PointF pointF = new PointF(x, y);
                ar.add(pointF);
//                canvas.drawText(i + "", i * keduX + min_left_x, maxPoint_Y, paintPoint_normal);
            }
            for (int i = 0; i < ar.size(); i++) {
                float y = ((innerMaxY - Float.parseFloat(array.get(i).getAvg_ph()) / 100.f) / (float) keduY2 * (float) keduY + keduY) - 40;
                if (y >= maxPoint_Y) {
                    y = 230;
                }
                String value = NumberUtils.parse2AnyFloat(array.get(i).getAvg_ph(), 100);
                RectF rect = new RectF((int) (ar.get(i).x - min_left_x + 10), (int) (maxPoint_Y - min_left_x), (int) (ar.get(i).x + min_left_x - 10), (int) (maxPoint_Y));
                Rect rectMessure = new Rect();
                float pointX = i * (float) keduX + min_left_x;
                if (Float.parseFloat(value) < topTemperature && Float.parseFloat(value) > bottomTemperatue) {
                    //画点
                    canvas.drawCircle(pointX, y, 4.0f, paintPoint_normal);
                    rectMessure = new Rect();
                    text_normal_or_no.getTextBounds(App.getInstance().getString(R.string.trend_normal), 0, 1, rectMessure);
                    canvas.drawRoundRect(rect, 10, 10, paintPoint_normal);//画圆角矩形
                    canvas.drawText(App.getInstance().getString(R.string.trend_normal), rect.centerX() - rectMessure.width(), rect.centerY() + rectMessure.height() / 2, text_normal_or_no);
                    rectMessure = new Rect();
                    paintPoint_normal.getTextBounds(value + "", 0, (value + "").length(), rectMessure);
                    canvas.drawText(value + "", (i + 1) * keduX - min_left_x - rectMessure.width() / 2, y + rectMessure.height() + 10, paintPoint_normal);
                } else {
                    //画点
                    canvas.drawCircle(pointX, y, 4.0f, paintPoint_yichang);
                    rectMessure = new Rect();
                    paint_yichang.getTextBounds(App.getInstance().getString(R.string.trend_abnormal), 0, 1, rectMessure);
                    canvas.drawRoundRect(rect, 10, 10, paint_yichang);//画圆角矩形
                    canvas.drawText(App.getInstance().getString(R.string.trend_abnormal), rect.centerX() - rectMessure.width(), rect.centerY() + rectMessure.height() / 2, text_normal_or_no);
                    rectMessure = new Rect();
                    paint_yichang.getTextBounds(value + "", 0, ("" + value).length(), rectMessure);
                    canvas.drawText(value + "", (i + 1) * keduX - min_left_x - rectMessure.width() / 2, y + rectMessure.height() + 10, paint_yichang);
                }
                Rect rectDate = new Rect();
                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#858585"));
                paint.setTextSize(30);

                paint.setAntiAlias(true);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                String week = getWeek(array.get(i).getHis_date());
                if (week.equals("yesterday")) {
                    week = "ytd";
                }
                String date = "";
                paint.getTextBounds(week, 0, week.length(), rectDate);
                if (dataType == 1) {
                    //当天(区分今天、昨天)
                    String today = judgeIsToday2(array.get(i).getHis_date());
                    if (today.equals("yesterday")) {
                        today = "ytd";
                    }
                    paint.getTextBounds(today, 0, today.length(), rectDate);
                    date = formatTimesYMDH2H(array.get(i).getHis_date());
                    if (date.equals("yesterday")) {
                        date = "ytd";
                    }
                    canvas.drawText(today, pointX - rectDate.width() / 2, min_left_x, paint);
                } else {
                    //周或日
                    date = formatTimesYMD2MD(array.get(i).getHis_date());
                    if (date.equals("yesterday")) {
                        date = "ytd";
                    }
                    canvas.drawText(week, pointX - rectDate.width() / 2, min_left_x, paint);
                }
                paint.getTextBounds(date, 0, date.length(), rectDate);
                canvas.drawText(date, pointX - rectDate.width() / 2, min_left_x + rectDate.height() + 10, paint);
                if (i != 0) {
                    canvas.drawLine(ar.get(i).x, ar.get(i).y, ar.get(i - 1).x, ar.get(i - 1).y, paintLianXian);
                }
                maxX = rect.right + 10;
                canvas.drawLine(maxX, min_left_x, maxX, maxPoint_Y, paintY_shuxian);
            }
        }
        if (setIsUpdate) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) maxX, ScreenUtil.getPhoneSize(activityTemperature)[1] / 2);
            setLayoutParams(layoutParams);
            setIsUpdate = false;
        }
    }

    public void setAtivity(ActivityTemperature activityTemperature) {
        this.activityTemperature = activityTemperature;
    }
}
