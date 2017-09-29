package sunsun.xiaoli.jiarebang.utils.loadingutil;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;

public class RedBagWindow extends PopupWindow {
	public TextView txt_message;
	View cameraMenuView;
	public RedBagWindow(Activity context,String message) {
		super(context);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cameraMenuView = layoutInflater.inflate(R.layout.popup_redbag,
				null);
		txt_message = (TextView) cameraMenuView.findViewById(R.id.txt_message);
		txt_message.setText(message);
		Rect rect = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int winHeight = context.getWindow().getDecorView().getHeight();
		this.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0,
				winHeight + rect.bottom);
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		this.setContentView(cameraMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		 this.setAnimationStyle(R.style.AnimBottom);
		ColorDrawable dw = new ColorDrawable(0xb000000);
		this.setBackgroundDrawable(dw);
		cameraMenuView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				int height = cameraMenuView.findViewById(R.id.camera_layout)
//						.getTop();
//				int y = (int) event.getY();
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (y < height) {
						dismiss();
//					}
//				}

				return true;
			}
		});
	}
}
