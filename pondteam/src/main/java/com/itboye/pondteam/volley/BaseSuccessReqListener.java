package com.itboye.pondteam.volley;

import android.util.Log;

public class BaseSuccessReqListener<T> extends XRequestListener<T>{
	
	public static int SUCCESS = 0;
	private ICompleteListener listener;
	public BaseSuccessReqListener(ICompleteListener listener){
		this.listener = listener;
	}
	
	@Override
	public void onResponse(T resp) {
		Log.v("resp", resp.getClass().getName());
		if(listener != null){
			ResultEntity result = new ResultEntity(SUCCESS, "请求成功", resp);
			listener.success(result);
		}
	}
}
