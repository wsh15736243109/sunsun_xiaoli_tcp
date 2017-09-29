package sunsun.xiaoli.jiarebang.popwindow;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;


/**
 * Created by itboye on 2017/3/1.
 * <p>
 * 设备修改删除取消工具类
 * dodou
 */

public class PHUpdate extends PopupWindow {
    private Activity context;
    private String title;
    private String message;
    private String cancel;
    private ClickListenerInterface clickListenerInterface;
    View view = null;

    public PHUpdate(Activity context, String title, String message,
                    String cancel) {
        super(context);
        this.context = context;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
        inite();
    }

    public interface ClickListenerInterface {
        public void doUpdateDevice(String title);//修改设备

        public void doDeleteDevice();//删除设备

        public void fankui();//反馈

        public void gujiangengxin();//取消
    }


    public void inite() {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.ph_alert_update_device, null);

        TextView tvUpdate = (TextView) view.findViewById(R.id.tvUpdate);
        TextView pick_upgrade = (TextView) view.findViewById(R.id.pick_upgrade);
        TextView pick_Delete = (TextView) view.findViewById(R.id.pick_Delete);
        TextView pick_feedback = (TextView) view.findViewById(R.id.pick_feedback);
        TextView camera_cancel = (TextView) view.findViewById(R.id.camera_cancel);
        //设置点击事件
        pick_upgrade.setOnClickListener(new clickListener());
        tvUpdate.setOnClickListener(new clickListener());
        pick_Delete.setOnClickListener(new clickListener());
        pick_feedback.setOnClickListener(new clickListener());
        camera_cancel.setOnClickListener(new clickListener());
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = context.getWindow().getDecorView().getHeight();
        this.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0,
                winHeight - rect.bottom);
        Log.d("titltetete", winHeight + "");
        System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + winHeight);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setWidth(ActionBar.LayoutParams.FILL_PARENT);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setContentView(view);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        this.setBackgroundDrawable(dw);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.camera_layout)
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

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.pick_upgrade:
                    //检查固件升级
                    clickListenerInterface.gujiangengxin();
                    break;
                case R.id.pick_Delete:
                    //删除设备
                    clickListenerInterface.doDeleteDevice();
                    break;
                case R.id.tvUpdate:
                    //修改设备名称
                    clickListenerInterface.doUpdateDevice(title);
                    break;
                case R.id.pick_feedback:
                    //反馈
                    clickListenerInterface.fankui();
                    break;
                case R.id.camera_cancel:

                    break;
                default:
                    break;
            }
            dismiss();
        }
    }
}
