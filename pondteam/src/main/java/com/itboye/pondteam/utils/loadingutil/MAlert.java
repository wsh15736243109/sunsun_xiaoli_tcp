package com.itboye.pondteam.utils.loadingutil;

import android.widget.Toast;

import com.itboye.pondteam.app.MyApplication;


/**
 * Created by admin on 2017/1/13.
 */

public class MAlert {

    /**
     * 普通谈弹框，默认显示在顶部
     *
     * @param s
     */
    public static void alert(Object s) {
        CustomerToast.makeCustomText(MyApplication.getInstance(), s + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 增加位置参数，
     *
     * @param s
     * @param diretion 弹框显示位置
     */
    public static void alert(Object s, int diretion) {
        CustomerToast.makeCustomText(MyApplication.getInstance(), s + "", Toast.LENGTH_SHORT, diretion).show();
    }

    /**
     * 增加显示位置与弹框颜色
     *
     * @param s
     * @param diretion
     * @param color    弹框的颜色
     */
    public static void alert(Object s, int diretion, int color) {
        CustomerToast.makeCustomText(MyApplication.getInstance(), s + "", Toast.LENGTH_SHORT, diretion, color).show();
    }
}
