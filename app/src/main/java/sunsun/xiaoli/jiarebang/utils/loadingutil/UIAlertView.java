package sunsun.xiaoli.jiarebang.utils.loadingutil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;


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
        public void doUpdateDevice();//修改设备

        public void doDeleteDevice();//删除设备

        public void doCancel();//取消
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
        //赋值标题文字
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvLeft.setText(cancel);
        //设置点击事件
        tvLeft.setOnClickListener(new clickListener());
        tvMessage.setOnClickListener(new clickListener());
        tvTitle.setOnClickListener(new clickListener());
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
            int id = v.getId();
            switch (id) {
                case R.id.tvBtnLeft:
                    clickListenerInterface.doCancel();
                    break;
                case R.id.tvMessage:
                    clickListenerInterface.doDeleteDevice();
                    break;
                case R.id.tvTitle:
                    clickListenerInterface.doUpdateDevice();
                    break;
                default:
                    break;
            }
        }
    }
}
