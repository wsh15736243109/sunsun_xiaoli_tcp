package com.itboye.pondteam.utils.loadingutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.itboye.pondteam.R;


/**
 * Created by itboye on 2017/3/1.
 * <p>
 * 设备修改删除取消工具类
 * dodou
 */

public class UIAlertView extends Dialog {
    private Context context;
    private String title;
    private String message;
    private String cancel;
    private ClickListenerInterface clickListenerInterface;

    public UIAlertView(Context context, String title, String message,
                       String cancel) {
        super(context, R.style.UIAlertViewStyle);
        this.context = context;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
    }

    public interface ClickListenerInterface {
        public void doUpdateDevice(String title);//修改设备

        public void doDeleteDevice();//删除设备

        public void doCancel();//取消

        public void gujiangengxin();//取消

        public void feedBack();//反馈意见
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inite();
    }

    public void inite() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ui_alert_view, null);
        setContentView(view);
        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvgengxingujian=(TextView) view.findViewById(R.id.tvgengxingujian);
        TextView tvFeedback=(TextView) view.findViewById(R.id.tvFeedback);
        //赋值标题文字
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvLeft.setText(cancel);
        //设置点击事件
        tvLeft.setOnClickListener(new clickListener());
        tvMessage.setOnClickListener(new clickListener());
        tvTitle.setOnClickListener(new clickListener());
        tvgengxingujian.setOnClickListener(new clickListener());
        tvFeedback.setOnClickListener(new clickListener());
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v.getId()== R.id.tvBtnLeft) {
                clickListenerInterface.doCancel();
            }else  if (v.getId()== R.id.tvMessage) {
                clickListenerInterface.doDeleteDevice();
            }else  if (v.getId()== R.id.tvTitle) {
                clickListenerInterface.doUpdateDevice(title);
            }else  if (v.getId()== R.id.tvgengxingujian) {
                clickListenerInterface.gujiangengxin();
            }else  if (v.getId()== R.id.tvFeedback) {
                clickListenerInterface.feedBack();
            }
        }
    }
}
