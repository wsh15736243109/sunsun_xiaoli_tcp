package sunsun.xiaoli.jiarebang.utils.loadingutil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;


@SuppressLint("NewApi")
public class LoadingDialog extends DialogFragment {

	private String mMsg = "Loading";

	public void setMsg(String msg) {
		this.mMsg = msg;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_loading, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.queryPersonData);
		if (imageView != null) {
			imageView.setBackgroundResource(R.drawable.loading_anim);
			// 获取动画
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView
					.getBackground();
			animationDrawable.start();
			TextView title = (TextView) view
					.findViewById(R.id.id_dialog_loading_msg);
			title.setText(mMsg);
			Dialog dialog = new Dialog(getActivity(), R.style.dialog);
			dialog.setContentView(view);
			dialog.setCanceledOnTouchOutside(false);

			return dialog;
		} else {
			return null;
		}
	}
}
