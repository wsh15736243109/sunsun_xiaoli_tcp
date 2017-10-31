package com.itboye.pondteam.utils;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.TypedValue;

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
    public static int getDimension(Activity activity,int value){
        int xiangsu=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, activity.getResources().getDisplayMetrics()));
        return xiangsu;
    }

    /**
     * 保持屏幕唤醒状态（即背景灯不熄灭）
     *
     * @param on
     * 是否唤醒
     */
    private static PowerManager.WakeLock wl;

    public static void keepScreenOn(Context context, boolean on) {
        if (on) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "==KeepScreenOn==");
            wl.acquire();
        } else {
            if (wl != null) {
                wl.release();
                wl = null;
            }
        }
    }

    /**
     * 获取手机IMEI号
     *
     * 需要动态权限: android.permission.READ_PHONE_STATE
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

}
