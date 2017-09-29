package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.DensityUtil;
import com.itboye.pondteam.volley.ResultEntity;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


@SuppressLint("ResourceAsColor")
public abstract class BaseOtherActivity extends FragmentActivity implements
        OnClickListener {
    protected Context mContext;
    private View statusBar;
    public boolean isGestureOpen;
    private ViewContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = getApplicationContext();
        other();
        initData();
    }
    protected abstract int getLayoutId();


    protected abstract void initData();


    @SuppressLint("InlinedApi")
    void other() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

//            Window window = getWindow();
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            statusBar = new View(this);
//            ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
//            statusBar.setLayoutParams(param);
//            setStatusBarColor(R.color.main_yellow);
//            this.addContentView(statusBar, param);
        }
        smartInject();// 自动获取控件
    }

    public static final String EVENT_TYPE_UNKNOWN = BaseOtherActivity.class
            .getName() + "_unknown";

    /**
     * @param data
     * @return 2016-4-23 hebidu
     */
    public ResultEntity handlerError(Object data) {
        ResultEntity result = (ResultEntity) data;
        if (result == null) {
            result = new ResultEntity(-1, "数据格式错误!~", data);
            return result;
        }

        if (result.hasError()) {
         //   ByAlert.alert(result.getMsg());
            if (result.getMsg().contains("java.net.UnknownHostException")) {
                MAlert.alert("请连接网络 ");
            } else {
                MAlert.alert(result.getMsg());
            }
            result.setEventType(EVENT_TYPE_UNKNOWN);
            return result;
        }

        return result;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 设置状态栏颜色，
     *
     * @param color
     *            颜色资源id， 如 R.color.orange</br> 0 黑色
     */
    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (color == 0) {
//                statusBar.setBackgroundColor(Color.BLACK);
            } else {
//                statusBar.setBackgroundColor(getResources().getColor(color));
            }

        }
    }

    public int getStatusBarHeight() {
        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height",
//                "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
        return result;
    }

    private void smartInject() {

        try {
            Class<? extends Activity> clz = getClass();

            while (clz != BaseOtherActivity.class) {

                Field[] fs = clz.getDeclaredFields();
                Resources res = getResources();
                String packageName = getPackageName();
                for (Field field : fs) {
                    if (!View.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    int viewId = res.getIdentifier(field.getName(), "id",
                            packageName);
                    if (viewId == 0)
                        continue;
                    field.setAccessible(true);
                    try {
                        View v = findViewById(viewId);
                        field.set(this, v);
                        Class<?> c = field.getType();
                        Method m = c.getMethod("setOnClickListener",
                                OnClickListener.class);
                        m.invoke(v, this);
                    } catch (Throwable e) {
                    }
                    field.setAccessible(false);

                }

                clz = (Class<? extends Activity>) clz.getSuperclass();

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 屏幕左侧右划返回容器 ,
     *
     * @author Young
     */
    private class ViewContainer extends LinearLayout {

        private int leftMargin;
        private VelocityTracker tracker;
        private float startX;
        private float startY;

        public ViewContainer(Context context) {
            super(context);
            leftMargin = DensityUtil.dip2px(35);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (isGestureOpen == false) {
                return super.dispatchTouchEvent(ev);
            }
            switch (ev.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    // 当满足下面条件时 视为 右划关闭手势
                    // 起始按压位置x坐标小与leftMargin&& 向右滑动 && 向右滑动距离 > 竖直方向距离
                    if (startX < leftMargin
                            && ev.getRawX() > startX
                            && ev.getRawX() - startX > Math.abs(ev.getRawY()
                            - startY)) {
                        // 速度大于2500时关闭activity
                        tracker.computeCurrentVelocity(1000);
                        if (tracker.getXVelocity() > 2500) {
                            finish();
                        }

                    }

                    tracker.recycle();
                    break;

                case MotionEvent.ACTION_DOWN:
                    startX = ev.getRawX();
                    startY = ev.getRawY();
                    tracker = VelocityTracker.obtain();
                    tracker.addMovement(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    tracker.addMovement(ev);
                    break;
            }

            return super.dispatchTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (isGestureOpen == false) {
                return super.onTouchEvent(event);
            }
            return true;
        }

    }
}
