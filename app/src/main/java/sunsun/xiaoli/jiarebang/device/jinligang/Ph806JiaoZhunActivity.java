package sunsun.xiaoli.jiarebang.device.jinligang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.itboye.pondteam.volley.TimesUtils;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.PhSeekBar;
import sunsun.xiaoli.jiarebang.interfaces.SmartConfigType;
import sunsun.xiaoli.jiarebang.popwindow.MyDialog;

import static com.itboye.pondteam.utils.ScreenUtil.getDimension;


public class Ph806JiaoZhunActivity extends BaseActivity implements Observer {

    TextView txt_ph_di_1, txt_ph_gao_1;
    ImageView img_back;
    TextView txt_title;
    Button btn_beginjiaozhun, btn_fuwei;
    App mApp;
    UserPresenter userPresenter;
    private MyDialog mydialog;
    private double jiaozhunValue = -1;
    PhSeekBar ph_seekBar;
    TextView shouci, shangci;
    boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_806ph_jiao_zhun);
        mApp = (App) getApplication();
        mApp.ph806JiaoZhunUI = this;
        txt_title.setText(getString(R.string.ph_jiaozhun));
        userPresenter = new UserPresenter(this);
        setJiaoZhunTimes();
    }

    int delayTime = 180;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            delayTime--;
            mydialog.txt_seconds.setText("剩余" + (delayTime) + "秒");
            if (delayTime == 7) {
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, (int) (jiaozhunValue * 100));
            }
            if (delayTime <= 0) {
                mydialog.setJiaoZhunStatus(SmartConfigType.JIAOZHUN_FINISH);
                mydialog.txt_tips.setText(isSuccess ? "电极校准成功" : "电极校准失败");
                handler.removeCallbacks(runnable);
            }
            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_fuwei:
                //出厂复位
                userPresenter.deviceSet_806(mApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, 1);
                break;
            case R.id.btn_beginjiaozhun:
                if (jiaozhunValue == -1) {
                    MAlert.alert(getString(R.string.choose_jiaozhun));
                    return;
                }
                mydialog = new MyDialog(this, R.style.MyDialog);
                mydialog.show();
                mydialog.setCancelable(false);
                mydialog.setJiaoZhunStatus(SmartConfigType.JIAOZHUN_INIT);
                mydialog.txt_tips.setText("确定使用ph" + jiaozhunValue + "校准吗？");
                mydialog.btn_yes.setOnClickListener(this);
                mydialog.btn_no.setOnClickListener(this);
                mydialog.img_back.setOnClickListener(this);
//                alert = new AlertDialog.Builder(this,R.style.MyDialog);
//                View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
//                alert.setView(view);
//                alert.create();
//                alertDialog = alert.show();
//                Button btn_yes = (Button) view.findViewById(R.id.btn_yes);
//                btn_yes.setOnClickListener(this);
//                Button btn_no = (Button) view.findViewById(R.id.btn_no);
//                btn_no.setOnClickListener(this);
//                ImageView img_close = (ImageView) view.findViewById(R.id.img_close);
//                img_close.setOnClickListener(this);
//                TextView txt_tips = (TextView) view.findViewById(R.id.txt_tips);
//                LinearLayout li_btn = (LinearLayout) view.findViewById(R.id.li_btn);
                break;
            case R.id.btn_yes:
                delayTime = 10;
                mydialog.txt_tips.setText("电极正在校准中");
                mydialog.setJiaoZhunStatus(SmartConfigType.JIAOZHUN_ING);
                new Thread(runnable).start();
                break;
            case R.id.btn_no:
            case R.id.img_close:
                mydialog.dismiss();
                handler.removeCallbacks(runnable);
                break;
            case R.id.txt_ph_di_1:
                setSelectDianJi(txt_ph_di_1);
//                txt_ph_di_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhun_bg_green));
//                txt_ph_di_1.setPadding( txt_ph_di_1.getPaddingLeft(), txt_ph_di_1.getPaddingTop(), txt_ph_di_1.getPaddingRight(), txt_ph_di_1.getPaddingBottom());
                break;
            case R.id.txt_ph_gao_1:
                setSelectDianJi(txt_ph_gao_1);
                break;
        }
    }

    public void setSelectDianJi(TextView textView) {
        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhun_bg_green));
        textView.setSelected(true);
        jiaozhunValue = Double.valueOf(textView.getText().toString());
        ph_seekBar.setMyProgress(jiaozhunValue);
        switch (textView.getId()) {
            case R.id.txt_ph_di_1:
                txt_ph_gao_1.setSelected(false);
                txt_ph_gao_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                break;
            case R.id.txt_ph_gao_1:
                txt_ph_di_1.setSelected(false);
                txt_ph_di_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                break;
        }
        txt_ph_di_1.setPadding(getDimension(this, 20), getDimension(this, 20), getDimension(this, 20), getDimension(this, 20));
        txt_ph_gao_1.setPadding(getDimension(this, 20), getDimension(this, 20), getDimension(this, 20), getDimension(this, 20));
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
           if (entity.getEventType() == UserPresenter.deviceSet_806success) {
//                MAlert.alert(entity.getData() + "");
                isSuccess = true;
            } else if (entity.getEventType() == UserPresenter.deviceSet_806fail) {
//                MAlert.alert(entity.getData() + "");
                isSuccess = false;
            }else if (entity.getEventType() == UserPresenter.deviceSet_806FuWeisuccess) {
                MAlert.alert(entity.getData() + "");
           }else if (entity.getEventType() == UserPresenter.deviceSet_806FuWeifail) {
                MAlert.alert(entity.getData() + "");
           }
        }
    }

    public void setJiaoZhunTimes() {
        int firstTime = mApp.jinLiGangdetailUI.deviceDetailModel.getFirst_ph_upd();
        int lastTime = mApp.jinLiGangdetailUI.deviceDetailModel.getLast_ph_upd();
        if (firstTime != 0) {
            shouci.setText("首次校准完成时间：" + TimesUtils.timeStamp2DateNo1000(1000*Long.parseLong(firstTime + "")));
        }
        if (lastTime != 0) {
            shangci.setText("上次校准完成时间：" + TimesUtils.timeStamp2DateNo1000(1000*Long.parseLong(lastTime + "")));
        }
    }

}
