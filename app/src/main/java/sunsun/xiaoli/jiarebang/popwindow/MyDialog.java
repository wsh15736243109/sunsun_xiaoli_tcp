package sunsun.xiaoli.jiarebang.popwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.interfaces.SmartConfigType;

public class MyDialog extends Dialog {
    public ImageView img_back;
    Context context;
    public Button btn_no, btn_yes;

    public TextView txt_tips, txt_jiaozhunsuccess,txt_seconds;

    public RelativeLayout re_jiaozhuning;

    public ProgressBar progress;

    LinearLayout li_btn;

    View line1,line_2;

    public MyDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);
        initview();
    }

    private void initview() {
        // TODO Auto-generated method stub
        img_back = (ImageView) findViewById(R.id.img_close);
        btn_no = (Button) findViewById(R.id.btn_no);
        txt_tips = (TextView) findViewById(R.id.txt_tips);
        li_btn= (LinearLayout) findViewById(R.id.li_btn);
        line1=findViewById(R.id.line1);
        line_2=findViewById(R.id.line_2);
        txt_jiaozhunsuccess = (TextView) findViewById(R.id.txt_jiaozhunsuccess);
        re_jiaozhuning = (RelativeLayout) findViewById(R.id.re_jiaozhuning);
        progress = (ProgressBar) findViewById(R.id.progress);
        txt_seconds= (TextView) findViewById(R.id.txt_seconds);
//        btn_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        btn_yes = (Button) findViewById(R.id.btn_yes);
//        img_back.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                setJiaoZhunStatus(SmartConfigType.JIAOZHUN_FINISH);
////                dismiss();
////                MyDialog.this.dismiss();
//            }
//        });
    }

    public void setJiaoZhunStatus(SmartConfigType smartConfigType) {
        switch (smartConfigType) {
            case JIAOZHUN_INIT:
                jiaoZhunInit();
                break;
            case JIAOZHUN_ING:
                jiaoZhunIng();
                break;
            case JIAOZHUN_FINISH:
                jiaoZhunFinish();
                break;
        }
    }

    private void jiaoZhunInit() {
        img_back.setVisibility(View.GONE);
        re_jiaozhuning.setVisibility(View.GONE);
        txt_jiaozhunsuccess.setVisibility(View.GONE);
        txt_tips.setVisibility(View.VISIBLE);
        btn_no.setVisibility(View.VISIBLE);
    }

    private void jiaoZhunIng() {
        img_back.setVisibility(View.VISIBLE);
        re_jiaozhuning.setVisibility(View.VISIBLE);
        txt_jiaozhunsuccess.setVisibility(View.GONE);
        txt_tips.setVisibility(View.VISIBLE);
        txt_tips.setCompoundDrawables(null,null,null,null);
        li_btn.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
    }

    private void jiaoZhunFinish() {
        li_btn.setVisibility(View.VISIBLE);
        re_jiaozhuning.setVisibility(View.GONE);
        btn_yes.setVisibility(View.GONE);
        btn_no.setVisibility(View.VISIBLE);
        line1.setVisibility(View.VISIBLE);
        txt_tips.setCompoundDrawables(null,null,null,null);
        line_2.setVisibility(View.GONE);
        btn_no.setText(App.getInstance().getString(R.string.ok));
        img_back.setVisibility(View.GONE);
    }
}
