package com.itboye.pondteam.logincontroller;


import android.app.Activity;
import android.util.Log;

/**
 * 登录相关控制类--状态模式</br></br>
 * <p>
 * 登录成功后调用setLoginState(new LoginedState());</br></br>
 * 退出登录后调用setLoginState(new UnLoginState());</br></br>
 * 进入主Activity前一定要设置一次，要么登录要么未登录</br></br>
 *
 * @author Mr.w
 */
public class LoginController {

    private static ILoginState loginState = new UnLoginState();
//    private static PushAgent mPushAgent;

    public static void setLoginState(ILoginState loginState) {

        LoginController.loginState = loginState;
        if (loginState instanceof UnLoginState) {
            Log.d("Login", "未登录");
        } else if (loginState instanceof LoginedState) {
            Log.d("bytag", "已登录。。。");

//            mPushAgent = PushAgent.getInstance(MyApplcation.ctx);
//            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
//            mPushAgent.onAppStart();
//            String uid = (String) SPUtils.get(MyApplcation.ctx, null,
//                    Const.UID, "");
//            mPushAgent.addExclusiveAlias(uid, "htb", new UTrack.ICallBack() {
//
//                @Override
//                public void onMessage(boolean arg0, String arg1) {
//                    // TODO Auto-generated method stub
//                    System.out.println(arg0 + "arg222" + arg1);
//                }
//            });
        }
    }
    /**
     * 维修消息
     */
    public  static void onGouWuChe(Activity settingActivity, Object data){
        loginState.onGouWuChe(settingActivity, data);
    }

    /**
     * 我的订单
     */
    public  static void onDingDan(Activity settingActivity, Object data){
        loginState.onDingDan(settingActivity, data);
    }
    /**
     * 个人信息
     * @param settingActivity
     * @param data
     */
    public static void PersonnalInfomaton(Activity settingActivity, Object data) {
        loginState.PersonnalInfomaton(settingActivity, data);
    }
    /**
     * 个人信息
     * @param settingActivity
     * @param data
     */
    public static void onSysTime(Activity settingActivity, Object data) {
        loginState.onSysTime(settingActivity, data);
    }


    /**
     * 个人信息
     * @param settingActivity
     * @param data
     */
    public static void onMessge(Activity settingActivity, Object data) {
        loginState.onMessge(settingActivity, data);
    }
    /**
     * 个人信息
     * @param settingActivity
     * @param data
     */
    public static void onWeiXiu(Activity settingActivity, Object data) {
        loginState.onWeiXiu(settingActivity, data);
    }
    /**
     * 个人信息
     * @param settingActivity
     * @param data
     */
    public static void onJineng(Activity settingActivity, Object data) {
        loginState.onJineng(settingActivity, data);
    }
    /**
     * 收货地址
     */
    public  static void onShouHuoDiZhi(Activity settingActivity, Object data){
        loginState.onShouHuoDiZhi(settingActivity, data);
    }

    /**
     * 个人信息
     * @param settingActivity
     * @param data
     */
    public static void onQian(Activity settingActivity, Object data) {
        loginState.onQian(settingActivity, data);
    }


}
