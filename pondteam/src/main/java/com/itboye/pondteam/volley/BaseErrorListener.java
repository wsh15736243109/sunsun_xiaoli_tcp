package com.itboye.pondteam.volley;

import android.content.Intent;

import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.Const;

public class BaseErrorListener implements XErrorListener {
	
	private ICompleteListener listener;
	public BaseErrorListener(ICompleteListener listener){
		this.listener = listener;
	}
	
	@Override
	public void onErrorResponse(Exception exception, String code, String msg) {
//			Log.v("error", exception.getMessage());
		if(listener != null){
			if(code == "1111"){
				Intent intent=new Intent();
				intent.setAction(Const.LOGIN_ACTION);
				intent.putExtra("code",code);
				MyApplication.getInstance().sendBroadcast(intent);
			}
			if(code == "0"){
					code = "-199";
			}
			if(msg == null || msg.isEmpty()){
				if(exception != null){
					msg = exception.getMessage();
				}else{
					msg = "发生未知错误!";
				}
			}
			if(exception instanceof ServerError){
				ServerError error = (ServerError) exception;
				code = ""+ error.networkResponse.statusCode;
				msg = "服务器返回错误!";
				
			}
				
			if(exception instanceof VolleyError){
				VolleyError jsonException=(VolleyError) exception;
				String error=jsonException.getLocalizedMessage();
				msg = "服务器开小差了...";
			}
			ResultEntity result = new ResultEntity(Integer.parseInt(code),msg, exception);
			listener.failure(result);
		}				
	}
}

