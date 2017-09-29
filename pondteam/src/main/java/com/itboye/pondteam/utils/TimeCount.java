package com.itboye.pondteam.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;

/**
 * 倒计时可点击
 *
 */
public class TimeCount extends CountDownTimer{
	
	
	private TextView tv;

	public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
		super(millisInFuture, countDownInterval);
		this.tv=tv;
	}



	@Override
	public void onFinish() {
		tv.setText(MyApplication.getInstance().getString(R.string.revalidation));
		tv.setClickable(true);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		tv.setClickable(false);
		tv.setText(String.format(MyApplication.getInstance().getString(R.string.after),millisUntilFinished / 1000));
		
	}

}
