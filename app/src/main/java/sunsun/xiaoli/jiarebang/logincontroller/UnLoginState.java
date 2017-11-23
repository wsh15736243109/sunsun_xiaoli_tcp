package sunsun.xiaoli.jiarebang.logincontroller;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.device.jinligang.LoginActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;


/**
 * 
 * @author 未登录情况下执行该方法
 * 
 */
public class UnLoginState implements ILoginState {

	// 所有未登录的统一执行Login操作
	private void doUnLogin(Activity act) {
		Intent intent = new Intent();
		if (BuildConfig.APP_TYPE.equals("森森新零售")) {
			intent.setClass(act, LingShouSwitchLoginOrRegisterActivity.class);
		}else {
			intent.setClass(act, LoginActivity.class);
		}
		act.startActivity(intent);
	}

	/***
	 * 个人信息
	 * @param activity
	 * @param obj
     */
	@Override
	public void PersonnalInfomaton(Activity activity, Object obj) {
		// TODO Auto-generated method stub
		doUnLogin(activity);
	}

	@Override
	public void onSysTime(Activity activity, Object obj) {
		doUnLogin(activity);
	}

	@Override
	public void onMessge(Activity activity, Object obj) {
		doUnLogin(activity);
	}

	@Override
	public void onWeiXiu(Activity activity, Object obj) {
		doUnLogin(activity);
	}

	@Override
	public void onJineng(Activity activity, Object obj) {
		doUnLogin(activity);
	}
	@Override
	public void onQian(Activity activity, Object obj) {
		doUnLogin(activity);
	}
	/**
	 * 购物车
	 */
	@Override
	public void onGouWuChe(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, ShopCartActivity.class);
//		activity.startActivity(intent);
	}

	@Override
	public void onShouHuoDiZhi(Activity activity, Object obj) {
		doUnLogin(activity);
	}

	@Override
	public void onDingDan(Activity activity, Object obj) {
		doUnLogin(activity);
	}

	@Override
	public void goToMyMessage(Activity activity, Object data) {
		doUnLogin(activity);
	}

	@Override
	public void goToMessageList(Activity activity, Object data) {
		doUnLogin(activity);
	}

	@Override
	public void goToPublish(FragmentActivity activity, Object o) {
		doUnLogin(activity);
	}

	@Override
	public void goToQueryAddress(Activity activity, Object o) {
		doUnLogin(activity);
	}
}
