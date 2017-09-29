package sunsun.xiaoli.jiarebang.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Mr.w on 2017/3/4.
 */

public class ScreenUtil {
    public static int[] getPhoneSize(Activity activity) {
        int[] size = new int[2];
        size[0] = activity.getWindowManager().getDefaultDisplay().getWidth();
        size[1] = activity.getWindowManager().getDefaultDisplay().getHeight();
        return size;
    }

    public static int[] getPhoneDistry(Activity activity) {
        // 方法2
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width=dm.widthPixels*dm.density;
        float height=dm.heightPixels*dm.density;
        int[] size = new int[2];
        size[0] = (int)width;
        size[1] = (int)height;
        return size;
    }
}
