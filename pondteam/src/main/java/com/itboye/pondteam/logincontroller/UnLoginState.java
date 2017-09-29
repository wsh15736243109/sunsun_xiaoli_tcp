package com.itboye.pondteam.logincontroller;

import android.app.Activity;


/**
 * 
 * @author 未登录情况下执行该方法
 * 
 */
public class UnLoginState implements ILoginState {

	// 所有未登录的统一执行Login操作
	private void doUnLogin(Activity act) {
//		Intent intent = new Intent(act, LoginActivity.class);
//
//		act.startActivity(intent);
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
}
