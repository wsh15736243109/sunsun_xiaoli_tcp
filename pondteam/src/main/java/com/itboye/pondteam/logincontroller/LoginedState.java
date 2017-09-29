package com.itboye.pondteam.logincontroller;

import android.app.Activity;



/***
 * 
 * @author 在登录状态下执行该方法
 * 
 */
public class LoginedState implements ILoginState {

//	BasePresenter projectpresenter;

	String uid;
	String id;
	// 状态为登录的时候执行本类里的方法

	/**
	 * 个人信息
	 * @param activity
	 * @param obj
     */
	@Override
	public void PersonnalInfomaton(Activity activity, Object obj) {
		// TODO Auto-generated method stub
//		Intent intent = new Intent(activity, ActivityPersonnalInfomaton.class);
//		activity.startActivityForResult(intent, 101);
	}

	@Override
	public void onSysTime(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, SysTemMessgeActivity.class);
//		activity.startActivityForResult(intent, 101);
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
	public void onMessge(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, RepairMessActivity.class);
//		activity.startActivityForResult(intent, 101);
	}

	@Override
	public void onWeiXiu(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, WeiXiuRecord.class);
//		activity.startActivityForResult(intent, 101);
	}

	@Override
	public void onJineng(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, ActivityRepair.class);
//		activity.startActivityForResult(intent, 101);
	}

	/**
	 * 收货地址
	 */
	@Override
	public void onShouHuoDiZhi(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, AddressListActivity.class);
//		intent.putExtra("type","2");
//		activity.startActivity(intent);
	}

	@Override
	public void onDingDan(Activity activity, Object obj) {
//		Intent intent = new Intent(activity, OrderActivity.class);
//		activity.startActivity(intent);
	}

	@Override
	public void onQian(Activity activity, Object obj) {
//		String pwd= IsUtilUid.isPay();
//		if(pwd==null || pwd.equals("")){
//			Intent intent=new Intent(MyApplcation.ctx,ZhiFuPassActivity.class);
//			activity.startActivity(intent);
//		}else{
//			Intent intent = new Intent(activity, ActivityQianBao.class);
//			activity.startActivityForResult(intent, 101);
//		}
	}


}
