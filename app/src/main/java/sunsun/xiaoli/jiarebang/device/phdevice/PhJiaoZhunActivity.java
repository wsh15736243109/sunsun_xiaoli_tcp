package sunsun.xiaoli.jiarebang.device.phdevice;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.itboye.pondteam.volley.TimesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.PhSeekBar;
import sunsun.xiaoli.jiarebang.interfaces.SmartConfigType;
import sunsun.xiaoli.jiarebang.popwindow.MyDialog;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


public class PhJiaoZhunActivity extends BaseActivity implements Observer {

    TextView txt_ph_di_1, txt_ph_di_2, txt_ph_gao_1, txt_ph_gao_2;
    ImageView img_back;
    TextView txt_title;
    Button btn_beginjiaozhun;
    App mApp;
    UserPresenter userPresenter;
    AlertDialog alertDialog;
    private AlertDialog.Builder alert;
    private MyDialog mydialog;
    private double jiaozhunValue = -1;
    PhSeekBar ph_seekBar;

    TextView shouci, shangci;

    boolean isFirst = true;
    private boolean beginJiaoZhun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph_jiao_zhun);
        mApp = (App) getApplication();
        mApp.phJiaoZhunUI = this;
        txt_title.setText(getString(R.string.ph_jiaozhun));
        userPresenter = new UserPresenter(this);
        beginJiaoZhun = false;
        setJiaoZhunTimes();
    }

    public void getSche() {
        userPresenter.getDeviceDetailInfo(mApp.devicePhUI.deviceDetailModel.getDid(), getSp(Const.UID));
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getSche();
            handler.sendEmptyMessage(1);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.phJiaoZhunUI =null;
        handler.removeCallbacks(runnable);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
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
                mydialog.txt_tips.setText(String.format(getString(R.string.userph_to_calibrate), jiaozhunValue));
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
                userPresenter.deviceSet_300Ph(mApp.devicePhUI.deviceDetailModel.getDid(), -1, -1, -1, -1, -1, -1, -1, 180, (int) (jiaozhunValue * 100));
//                alertDialog.dismiss();
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
            case R.id.txt_ph_di_2:
                setSelectDianJi(txt_ph_di_2);
                break;
            case R.id.txt_ph_gao_1:
                setSelectDianJi(txt_ph_gao_1);
                break;
            case R.id.txt_ph_gao_2:
                setSelectDianJi(txt_ph_gao_2);
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
                txt_ph_di_2.setSelected(false);
                txt_ph_gao_1.setSelected(false);
                txt_ph_gao_2.setSelected(false);
                txt_ph_di_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_gao_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_gao_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                break;
            case R.id.txt_ph_di_2:
                txt_ph_di_1.setSelected(false);
                txt_ph_gao_1.setSelected(false);
                txt_ph_gao_2.setSelected(false);
                txt_ph_di_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_gao_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_gao_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                break;
            case R.id.txt_ph_gao_1:
                txt_ph_di_1.setSelected(false);
                txt_ph_di_2.setSelected(false);
                txt_ph_gao_2.setSelected(false);
                txt_ph_di_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_di_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_gao_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                break;
            case R.id.txt_ph_gao_2:
                txt_ph_di_1.setSelected(false);
                txt_ph_di_2.setSelected(false);
                txt_ph_gao_1.setSelected(false);
                txt_ph_di_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_di_2.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                txt_ph_gao_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.phjiaozhunbg_white));
                break;
        }
    }

    public void setData() {
        if (beginJiaoZhun) {
            mydialog.setJiaoZhunStatus(SmartConfigType.JIAOZHUN_ING);
            mydialog.txt_tips.setText(getString(R.string.begin_calebrated));
            if (mApp.devicePhUI.detailModelTcp != null) {
                int remainSeconds = mApp.devicePhUI.detailModelTcp.getPh_dly() - mApp.devicePhUI.detailModelTcp.getPh_sche();
                if (remainSeconds > 0) {
                    mydialog.txt_seconds.setText(String.format(getString(R.string.seconds_left), remainSeconds));
                } else {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        handler.removeCallbacks(runnable);
                        mydialog.txt_tips.setText(getString(R.string.jiaozhu_success));
                        beginJiaoZhun = false;
                        mydialog.setJiaoZhunStatus(SmartConfigType.JIAOZHUN_FINISH);
                        userPresenter.updateJiaoZhunTime(mApp.devicePhUI.deviceDetailModel.getId(), -1, -1, -1, -1, -1, -1, System.currentTimeMillis());
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.deviceSet_300success) {
                MAlert.alert(entity.getData());
                beginJiaoZhun = true;
            } else if (entity.getEventType() == UserPresenter.deviceSet_300fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {

            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                handler.removeCallbacks(runnable);
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.updateJiaoZhunTime_success) {
                MAlert.alert(entity.getData() + "");
                mApp.mDeviceUi.getDeviceList();
            } else if (entity.getEventType() == UserPresenter.updateJiaoZhunTime_fail) {
                MAlert.alert(entity.getData() + "");
            }
        }
    }

    public void setJiaoZhunTimes() {
        String times = mApp.devicePhUI.deviceDetailModel.getExtra();
        try {
            JSONObject jsonObject = new JSONObject(times);
            String firstTime = jsonObject.getString("first_upd");
            String lastTime = jsonObject.getString("last_upd");
            if (!firstTime.equals("0")) {
                shouci.setText(getString(R.string.first_jiaozhun) + TimesUtils.timeStamp2DateNo1000(Long.parseLong(firstTime)));
            }
            if (!lastTime.equals("0")) {
                shangci.setText(getString(R.string.last_jiaozhun) + TimesUtils.timeStamp2DateNo1000(Long.parseLong(lastTime)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
