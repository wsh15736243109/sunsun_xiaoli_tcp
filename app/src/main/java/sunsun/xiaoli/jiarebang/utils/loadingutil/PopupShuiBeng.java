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

public class PopupShuiBeng extends Dialog {
    private Context context;
    View.OnClickListener onClickListener;

    public PopupShuiBeng(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.UIAlertViewStyle);
        this.context = context;
        this.onClickListener = onClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inite();
    }

    public void inite() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popup_shuibeng, null);
        setContentView(view);
        TextView tvUpdateDeviceName = (TextView) view.findViewById(R.id.tvUpdateDeviceName);
        TextView tvDelete = (TextView) view.findViewById(R.id.tvDelete);
        TextView tvBtnLeft = (TextView) view.findViewById(R.id.tvBtnLeft);
        TextView tvFanKui = (TextView) view.findViewById(R.id.tvFanKui);
        TextView tvShengji = (TextView) view.findViewById(R.id.tvShengji);
        //赋值标题文字
        //tvDelete.setText(title);
        //tvFuwei.setText(cancel);
        //设置点击事件
        tvUpdateDeviceName.setOnClickListener(onClickListener);
        tvDelete.setOnClickListener(onClickListener);
        tvFanKui.setOnClickListener(onClickListener);
        tvShengji.setOnClickListener(onClickListener);
        tvBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

}
