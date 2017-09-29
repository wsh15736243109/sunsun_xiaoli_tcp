package sunsun.xiaoli.jiarebang.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import sunsun.xiaoli.jiarebang.R;

public class PhSeekBar extends android.support.v7.widget.AppCompatSeekBar{
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private float mTitleTextSize;
    private String mTitleText;//文字的内容

    /**
     * 背景图片
     */
    private int img;
    //bitmap对应的宽高
    private float img_width, img_height;
    Paint paint;

    private float numTextWidth;
    //测量seekbar的规格
    private Rect rect_seek;
    private Paint.FontMetrics fm;

    public static final int TEXT_ALIGN_LEFT = 0x00000001;
    public static final int TEXT_ALIGN_RIGHT = 0x00000010;
    public static final int TEXT_ALIGN_CENTER_VERTICAL = 0x00000100;
    public static final int TEXT_ALIGN_CENTER_HORIZONTAL = 0x00001000;
    public static final int TEXT_ALIGN_TOP = 0x00010000;
    public static final int TEXT_ALIGN_BOTTOM = 0x00100000;
    /**
     * 文本中轴线X坐标
     */
    private float textCenterX;
    /**
     * 文本baseline线Y坐标
     */
    private float textBaselineY;
    /**
     * 文字的方位
     */
    private int textAlign;

    public PhSeekBar(Context context) {
        this(context, null);
        mTitleText=getProgress()+".00";
    }

    public PhSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mTitleText=getProgress()+".00";
    }

    public PhSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MySeekBar, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MySeekBar_textsize:
                    mTitleTextSize = array.getDimension(attr, 15f);
                    break;
                case R.styleable.MySeekBar_textcolor:
                    mTitleTextColor = array.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.MySeekBar_img:
                    img = array.getResourceId(attr, R.drawable.bgr_green);
                    break;
            }
        }
        array.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setTextSize(mTitleTextSize);//设置文字大小
        paint.setColor(mTitleTextColor);//设置文字颜色
        //设置控件的padding 给提示文字留出位置
        setPadding(10, 10, 10, 10);
        textAlign = TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL;
        mTitleText=getProgress()+".00";
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setTextLocation();//定位文本绘制的位置
//        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.bg3);
        rect_seek=getThumb().getBounds();
        setThumbOffset(0);
//        rect_seek = this.getProgressDrawable().getBounds();
//        //定位文字背景图片的位置
//        float bm_x = rect_seek.width() * getProgress() / getMax();
//        float bm_y = rect_seek.height() + 20;
//        float bm_y = getPaddingTop() ;
//        //计算文字的中心位置在bitmap
        float text_x = rect_seek.centerX() ;
//        canvas.drawBitmap(map, bm_x, bm_y, paint);//画背景图
        // canvas.drawRoundRect();

        Rect rect=new Rect();
        paint.getTextBounds(mTitleText, 0, mTitleText.length(), rect);
        paint.setColor(Color.parseColor("#000000"));
//        bitmap=getResizedBitmap(bitmap,rect.width()+10,getHeight());
//        Drawable draw=new BitmapDrawable(getResources(),bitmap);
//        setProgressDrawable(draw);
//        setThumb(draw);
        canvas.drawText(mTitleText, text_x-rect.width()/2, getHeight()/2+rect.height()/2, paint);//画文字

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        invalidate();//监听手势滑动，不断重绘文字和背景图的显示位置
//        return super.onTouchEvent(event);
        return false;
    }

    /**
     * 定位文本绘制的位置
     */
    private void setTextLocation() {

        fm = paint.getFontMetrics();
        //文本的宽度
        mTitleText = getProgress() + 10 + "℃";

        numTextWidth = paint.measureText(mTitleText);

//        float textCenterVerticalBaselineY = img_height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
//        switch (textAlign) {
//            case TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL:
//                textCenterX = img_width / 2;
//                textBaselineY = textCenterVerticalBaselineY;
//                break;
//            case TEXT_ALIGN_LEFT | TEXT_ALIGN_CENTER_VERTICAL:
//                textCenterX = numTextWidth / 2;
//                textBaselineY = textCenterVerticalBaselineY;
//                break;
//            case TEXT_ALIGN_RIGHT | TEXT_ALIGN_CENTER_VERTICAL:
//                textCenterX = img_width - numTextWidth / 2;
//                textBaselineY = textCenterVerticalBaselineY;
//                break;
//            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_CENTER_HORIZONTAL:
//                textCenterX = img_width / 2;
//                textBaselineY = img_height - fm.bottom;
//                break;
//            case TEXT_ALIGN_TOP | TEXT_ALIGN_CENTER_HORIZONTAL:
//                textCenterX = img_width / 2;
//                textBaselineY = -fm.ascent;
//                break;
//            case TEXT_ALIGN_TOP | TEXT_ALIGN_LEFT:
//                textCenterX = numTextWidth / 2;
//                textBaselineY = -fm.ascent;
//                break;
//            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_LEFT:
//                textCenterX = numTextWidth / 2;
//                textBaselineY = img_height - fm.bottom;
//                break;
//            case TEXT_ALIGN_TOP | TEXT_ALIGN_RIGHT:
//                textCenterX = img_width - numTextWidth / 2;
//                textBaselineY = -fm.ascent;
//                break;
//            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_RIGHT:
//                textCenterX = img_width - numTextWidth / 2;
//                textBaselineY = img_height - fm.bottom;
//                break;
//        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION  
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP  
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP  
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void setMyProgress(double jiaozhunValue) {
        mTitleText=jiaozhunValue+"";
        setProgress((int)jiaozhunValue);
    }


}
