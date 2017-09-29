package sunsun.xiaoli.jiarebang.sunsunlingshou.utils;

import android.app.Activity;
import android.view.View;

import com.itboye.pondteam.base.LingShouBaseActivity;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.BaseOtherActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

/**
 * Created by Administrator on 2017/6/29.
 */

public class UiUtils {
    public static void initTitlebarStyle1(Activity activity, TranslucentActionBar actionBar, String title,int leftIcon,String strLeft,int rightIcon,String strRight) {
        //初始actionBar
        actionBar.setData(title, leftIcon, strLeft, rightIcon, strRight, null);
        //开启渐变
//        actionBar.setNeedTranslucent();
        //设置状态栏高度
        if (activity instanceof LingShouBaseActivity) {
            actionBar.setStatusBarHeight(((LingShouBaseActivity)activity).getStatusBarHeight());
        }else{
            actionBar.setStatusBarHeight(((BaseOtherActivity)activity).getStatusBarHeight());
        }
        actionBar.setBarBackgroundColor(activity.getResources().getColor(R.color.main_yellow));
    }

    public static void initTitleBarStyle2(Activity activity, TranslucentActionBar actionBar, String title, TranslucentScrollView pullzoom_scrollview, TranslucentScrollView.TranslucentChangedListener translucentChangedListener, View pull_zoom){
        //初始actionBar
        actionBar.setData(title, R.drawable.img_dingwei, "定位中", R.drawable.img_unread, "", null);
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(((LingShouBaseActivity)activity).getStatusBarHeight());

        //设置透明度变化监听
        pullzoom_scrollview.setTranslucentChangedListener(translucentChangedListener);
        //关联需要渐变的视图
        pullzoom_scrollview.setTransView(actionBar);
        if (pull_zoom!=null) {
            pullzoom_scrollview.setPullZoomView(pull_zoom);
        }
    }
}
