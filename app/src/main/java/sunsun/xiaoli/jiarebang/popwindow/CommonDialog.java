package sunsun.xiaoli.jiarebang.popwindow;

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


public class CommonDialog extends Dialog {

    private Context context;
    private String title;
    private String message;
    private String buttonLeftText;
    private String buttonRightText;
    private ClickListenerInterface clickListenerInterface;
    int type;
    private View view;

    /**
     * @param context
     * @param title
     * @param message
     * @param buttonLeftText
     * @param buttonRightText
     * @param type            几何类型1、直线  2、夹角线 3、三角形  4、正方形  5、梯形  6、立体图形
     */
    public CommonDialog(Context context, String title, String message,
                        String buttonLeftText, String buttonRightText, int type) {
        super(context, R.style.UIAlertViewStyle);

        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonLeftText = buttonLeftText;
        this.buttonRightText = buttonRightText;
        this.type = type;
    }

    public interface ClickListenerInterface {
        void doLeft();

        void doRight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.common_alert, null);
        setContentView(view);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        TextView tvRight = (TextView) view.findViewById(R.id.tvBtnRight);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvMsg = (TextView) view.findViewById(R.id.tvMsg);
        tvTitle.setText(title);
        tvMsg.setText(message);
        if (buttonLeftText.equals("")) {
            tvLeft.setVisibility(View.GONE);
        } else {
            tvLeft.setText(buttonLeftText);
        }
        if (buttonRightText.equals("")) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setText(buttonRightText);
        }


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
            switch (id) {
                case R.id.tvBtnLeft:
                    clickListenerInterface.doLeft();
                    break;
                case R.id.tvBtnRight:
                    clickListenerInterface.doRight();
                    break;

                default:
                    break;
            }
        }
    }
}
