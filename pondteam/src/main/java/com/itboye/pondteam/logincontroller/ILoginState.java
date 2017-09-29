package com.itboye.pondteam.logincontroller;


import android.app.Activity;

/**
 * 2016.5.12
 *
 * @author 在需要判断登陆才能进入activity实现的接口
 * @author Mr.w
 */

public interface ILoginState {
    /**
     * 个人信息
     * @param activity
     * @param obj
     */
    void PersonnalInfomaton(Activity activity, Object obj);


    /**
     * 个人信息
     * @param activity
     * @param obj
     */
    void onSysTime(Activity activity, Object obj);


    /**
     * 个人信息
     * @param activity
     * @param obj
     */
    void onMessge(Activity activity, Object obj);

    /**
     * 个人信息
     * @param activity
     * @param obj
     */
    void onWeiXiu(Activity activity, Object obj);

    /**
     * 个人信息
     * @param activity
     * @param obj
     */
    void onJineng(Activity activity, Object obj);



    /**
     * 个人信息
     * @param activity
     * @param obj
     */
    void onQian(Activity activity, Object obj);
    /**
     * 购物车
     */
    void onGouWuChe(Activity activity, Object obj);

    /**
     * 收货地址
     */
    void onShouHuoDiZhi(Activity activity, Object obj);

    /**
     * 我的订单
     */
    void onDingDan(Activity activity, Object obj);


}
