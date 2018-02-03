package sunsun.xiaoli.jiarebang.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;


/**
 * Created by itboye on 2016/12/10.
 */

public class CameraPopupWindowmap extends PopupWindow {
    private TextView open_camera, pick_image, camera_cancel_tv;
    private View cameraMenuView;

    @SuppressWarnings("deprecation")
    public CameraPopupWindowmap(Activity context, View.OnClickListener itemOnclick) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cameraMenuView = layoutInflater.inflate(R.layout.camepapopupdwindmap,
                null);
        open_camera = (TextView) cameraMenuView.findViewById(R.id.open_camera);
        pick_image = (TextView) cameraMenuView.findViewById(R.id.pick_image);
        camera_cancel_tv = (TextView) cameraMenuView
                .findViewById(R.id.camera_cancel_tv);

        camera_cancel_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
        open_camera.setOnClickListener(itemOnclick);
        pick_image.setOnClickListener(itemOnclick);
        this.setContentView(cameraMenuView);
        this.setWidth(ActionBar.LayoutParams.FILL_PARENT);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
//		this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        this.setBackgroundDrawable(dw);
        cameraMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = cameraMenuView.findViewById(R.id.camera_layout)
                        .getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }

                return true;
            }
        });
    }
}
