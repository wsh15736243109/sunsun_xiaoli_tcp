package sunsun.xiaoli.jiarebang.utils.loadingutil;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;

public class CameraConsolePopupWindow extends PopupWindow {
	public TextView tvSttring, tvUpdate, pick_upgrade, pick_Delete,
			pick_share, pick_feedback, camera_cancel;
	private View cameraMenuView;
	private View share_line,update_line,udpatenickname_line;
	@SuppressWarnings("deprecation")
	public CameraConsolePopupWindow(Activity context,
			OnClickListener itemOnclick) {
		super(context);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cameraMenuView = layoutInflater.inflate(R.layout.item_windows_meun,
				null);
		tvSttring = (TextView) cameraMenuView.findViewById(R.id.tvSttring);
		tvUpdate = (TextView) cameraMenuView.findViewById(R.id.tvUpdate);
		pick_upgrade = (TextView) cameraMenuView
				.findViewById(R.id.pick_upgrade);

		pick_Delete = (TextView) cameraMenuView.findViewById(R.id.pick_Delete);
		pick_share = (TextView) cameraMenuView.findViewById(R.id.pick_share);
		pick_feedback = (TextView) cameraMenuView
				.findViewById(R.id.pick_feedback);
		share_line=cameraMenuView.findViewById(R.id.share_line);
		update_line=cameraMenuView.findViewById(R.id.update_line);
		udpatenickname_line=cameraMenuView.findViewById(R.id.udpatenickname_line);
		camera_cancel = (TextView) cameraMenuView
				.findViewById(R.id.camera_cancel);

		camera_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		tvSttring.setOnClickListener(itemOnclick);
		tvUpdate.setOnClickListener(itemOnclick);
		pick_upgrade.setOnClickListener(itemOnclick);

		pick_Delete.setOnClickListener(itemOnclick);
		pick_share.setOnClickListener(itemOnclick);
		pick_feedback.setOnClickListener(itemOnclick);

		camera_cancel.setOnClickListener(itemOnclick);
		Rect rect = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int winHeight = context.getWindow().getDecorView().getHeight();
		this.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0,
				winHeight + rect.bottom);
		Log.d("titltetete", winHeight+"");
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+winHeight);
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		this.setContentView(cameraMenuView);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
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

	public void setShareViewVisible(boolean isVi){
		pick_share.setVisibility(isVi?View.VISIBLE:View.GONE);
		share_line.setVisibility(isVi?View.VISIBLE:View.GONE);
	}
	public void setShengJiVisible(boolean isVi){
		pick_upgrade.setVisibility(isVi?View.VISIBLE:View.GONE);
		update_line.setVisibility(isVi?View.VISIBLE:View.GONE);
	}
	public void setUpdateNickNameVisible(boolean isVi){
		tvUpdate.setVisibility(isVi?View.VISIBLE:View.GONE);
		udpatenickname_line.setVisibility(isVi?View.VISIBLE:View.GONE);
	}
}
