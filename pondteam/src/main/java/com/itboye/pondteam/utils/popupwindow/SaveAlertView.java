package com.itboye.pondteam.utils.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.itboye.pondteam.R;


public class SaveAlertView extends Dialog {

    private Context context;
    private String title;
    private String message;
    private String buttonLeftText;
    private String buttonRightText;
    private ClickListenerInterface clickListenerInterface;
    public EditText edit_msg;
    TextView tvName;
    int type;
    private View view;

    /**
     * @param context
     * @param title
     * @param message
     * @param buttonLeftText
     * @param buttonRightText
     */
    public SaveAlertView(Context context, String title, String message,
                         String buttonLeftText, String buttonRightText, int type) {
        super(context, R.style.UIAlertViewStyle);

        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonLeftText = buttonLeftText;
        this.buttonRightText = buttonRightText;
        this.type = type;
    }

    public EditText getEditTextView() {
        return edit_msg;
    }

    public interface ClickListenerInterface {
        public void doLeft();

        public void doRight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.ui_alert_save_name, null);
        setContentView(view);
        tvName = (TextView) view.findViewById(R.id.title);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        TextView tvRight = (TextView) view.findViewById(R.id.tvBtnRight);
        edit_msg = (EditText) view.findViewById(R.id.edit_msg);
        edit_msg.setText(message);
        if (type == 1) {
            edit_msg.setEnabled(false);
        } else {
            edit_msg.setEnabled(true);
        }
        tvName.setText(title);
        tvLeft.setText(buttonLeftText);
        tvRight.setText(buttonRightText);

        tvLeft.setOnClickListener(new clickListener());
        tvRight.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();

        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    private void setText(TextView tvChang, String str) {
        tvChang.setText(str);
    }

    private void setVisible(View v, boolean b) {
        v.setVisibility(b == true ? View.VISIBLE : View.GONE);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {


        public void onClick(View v) {

            int id = v.getId();
            if (id== R.id.tvBtnLeft) {
                clickListenerInterface.doLeft();
            }else if (id== R.id.tvBtnRight) {
                clickListenerInterface.doRight();
            }
        }
    }

    public View getCustomView() {
        return view;
    }
}
