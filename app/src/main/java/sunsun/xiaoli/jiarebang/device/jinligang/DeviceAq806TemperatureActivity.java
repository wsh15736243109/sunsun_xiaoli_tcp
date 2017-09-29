package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.SpringProgressView;
import sunsun.xiaoli.jiarebang.device.ActivityTemperature;
import sunsun.xiaoli.jiarebang.utils.ColoTextUtil;
import sunsun.xiaoli.jiarebang.utils.loadingutil.CameraConsolePopupWindow;

import static android.content.DialogInterface.BUTTON_POSITIVE;


/**
 * Created by Mr.w on 2017/3/4.
 */

public class DeviceAq806TemperatureActivity extends BaseActivity implements Observer {
    private static final String CONNECTED_TEXT = "数据请求中,请稍后";
    private static final String DISCONNECTED_TEXT = "当前设备已断开,请稍后重试";
    ImageView img_back;
    TextView txt_title;
    CameraConsolePopupWindow popupWindow;
    RelativeLayout re_wendu_history;
    RelativeLayout re_settemperature;
    RelativeLayout re_gaowen_sheding, re_diwen_sheding;
    TextView txt_wendu_setting;
    private double mNewTempValue;
    TextView txt_wendu_sheding_high, txt_wendu_sheding_low;
    TextView txt_gonglv;
    SpringProgressView img_progress;
    public String did = "";
    UserPresenter userPresenter;
    //    public DeviceDetailModel deviceDetailModel;
    ImageView wendu_baojing;
    TextView wendu;
    String id;
    App myApp;
    TextView device_status;
    private boolean deviceConnectstatus;
    boolean wenDuBaoJingStatus;
    //    boolean yiChangBaoJingStatus;
    ImageView loading;
    PtrFrameLayout ptr;
    private int cfg;

    TextView txt_status;
    private int th = 0, tl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
//        BasePtr.setRefreshOnlyStyle(ptr);
        userPresenter = new UserPresenter(this);
        myApp = (App) getApplication();
        findViewById(R.id.re_zhuangtai).setVisibility(View.GONE);
        findViewById(R.id.re_yichang).setVisibility(View.GONE);
        myApp.deviceAq806TemperatureUI = this;
        popupWindow = new CameraConsolePopupWindow(
                DeviceAq806TemperatureActivity.this, this);
        img_progress.setMaxCount(35);
        setDeviceTitle("温度设置");
        setDeviceData();
    }

    public void setLoadingIsVisible(boolean is) {
        if (is) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    public void setDeviceTitle(String text) {

        txt_title.setText(text);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.re_wendu_history:
//                if (isConnect == false) {
                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("isPh", false);
                intent.putExtra("did", myApp.jinLiGangdetailUI.deviceDetailModel.getDid());
                intent.putExtra("topValue", (myApp.jinLiGangdetailUI.deviceDetailModel.getTh()) / 10 + "");
                intent.putExtra("bottomValue", (myApp.jinLiGangdetailUI.deviceDetailModel.getTl()) / 10 + "");
                intent.putExtra("title", getString(R.string.lishishuiwen));
                startActivity(intent);
//                } else {
//                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
//                }
                break;
            case R.id.re_gaowen_sheding:
            case R.id.txt_wendu_sheding_high:
                if (deviceConnectstatus == false) {
                    setWenDu(getString(R.string.wendu_high), txt_wendu_sheding_high, Double.parseDouble((txt_wendu_sheding_high.getText().toString().substring(0, txt_wendu_sheding_high.getText().toString().length() - 1))));
                } else {
                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
                }
                break;
            case R.id.re_diwen_sheding:
            case R.id.txt_wendu_sheding_low:
                if (deviceConnectstatus == false) {
                    setWenDu(getString(R.string.wendu_low), txt_wendu_sheding_low, Double.parseDouble((txt_wendu_sheding_low.getText().toString().substring(0, txt_wendu_sheding_low.getText().toString().length() - 1))));
                } else {
                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
                }
                break;
            case R.id.re_settemperature:
            case R.id.txt_wendu_setting:
                if (deviceConnectstatus == false) {
                    setWenDu(getString(R.string.wendu), txt_wendu_setting, Double.parseDouble((txt_wendu_setting.getText().toString().substring(0, txt_wendu_setting.getText().toString().length() - 1))));
                } else {
                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
                }
                break;
            case R.id.wendu_baojing:
                if (deviceConnectstatus == false) {
                    showProgressDialog(CONNECTED_TEXT, false);
                    setCheckOrUnCheck(wendu_baojing, !wenDuBaoJingStatus);
                } else {
                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
                }
                break;
        }
    }

    private void setCheckOrUnCheck(ImageView toggle_exception_warn, boolean checked) {
        if (toggle_exception_warn.getId() == R.id.toggle_exception_warn) {
            //设置异常报警开关
            userPresenter.deviceSet_jiarebang(did, "", "", checked ? "1" : "0", "");
        } else if (toggle_exception_warn.getId() == R.id.wendu_baojing) {
            //温度报警
//            cfg = myApp.jinLiGangdetailUI.deviceDetailModel.getPush_cfg() ^ (int) Math.pow(2, 5);
//            userPresenter.deviceSet_806(myApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", "", "", "", "", cfg + "", "", -1, -1, -1, -1);
            userPresenter.deviceSet_806Ph(myApp.jinLiGangdetailUI.deviceDetailModel.getId(), -1, -1, -1, wenDuBaoJingStatus ? 0 : 1, -1, -1);
        }
    }


    float temp = 0;

    private void setWenDu(String title, final TextView textView, double mNewTempValue) {
        this.mNewTempValue = mNewTempValue;
        temp = (float) mNewTempValue;
//        mNewTempValue = 20;
        NumberPicker mPicker = new NumberPicker(
                this);
        mPicker.setMinValue(0);
        mPicker.setMaxValue(35);
        mPicker.setValue((int) mNewTempValue);
        mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                temp = newVal;
            }
        });
        final AlertDialog mAlertDialog = new AlertDialog.Builder(
                this)
                .setTitle(title)
                .setView(mPicker)
                .setPositiveButton(getString(R.string.ok), null).setNegativeButton(getString(R.string.cancel), null).create();

        mAlertDialog.show();
        mAlertDialog.getButton(BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (textView.getId()) {
                    case R.id.txt_wendu_setting:
                    case R.id.re_settemperature:
                        //温度设定
                        userPresenter.deviceSet_806(myApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", temp * 10 + "", "", "", "", "", "", "", "", -1, -1, -1, -1);
                        break;
                    case R.id.re_diwen_sheding:
                    case R.id.txt_wendu_sheding_low:
                        if (temp * 10 > th) {
                            MAlert.alert(getString(R.string.low_tem_error));
                            return;
                        }
                        setHighOrLow(false, temp * 10);
                        break;
                    case R.id.re_gaowen_sheding:
                    case R.id.txt_wendu_sheding_high:
                        if (temp * 10 < tl) {
                            MAlert.alert(getString(R.string.high_tem_error));
                            return;
                        }
                        setHighOrLow(true, temp * 10);
                        break;
                }
//                textView.setText(temp + "℃");
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void update(Observable o, Object data) {
        setLoadingIsVisible(false);
        try {
            ptr.refreshComplete();
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg() + "设备信息有误");
                finish();
                return;
            } else if (entity.getEventType() == UserPresenter.deviceSet_806success) {
                MAlert.alert(entity.getData());
                Const.intervalTime = 500;
                myApp.jinLiGangdetailUI.threadStart();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Const.intervalTime = 10000;
                    }
                }, 5000);
            } else if (entity.getEventType() == UserPresenter.deviceSet_806fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update806ph_success) {
                MAlert.alert(entity.getData());
                Const.intervalTime = 500;
                myApp.jinLiGangdetailUI.threadStart();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Const.intervalTime = 10000;
                    }
                }, 5000);
            } else if (entity.getEventType() == UserPresenter.update806ph_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    public void setDeviceData() {
        double wenduValue = myApp.jinLiGangdetailUI.deviceDetailModel.getT() / 10;
        int startPo1 = ("" + wenduValue).length();
        int endPo1 = (wenduValue + "℃").length();
        ColoTextUtil.setDifferentSizeForTextView(startPo1, endPo1, (wenduValue + "℃"), wendu);
        ColoTextUtil.setDifferentSizeForTextView2(startPo1, endPo1, (wenduValue + "℃"), wendu);
        String extra = myApp.jinLiGangdetailUI.deviceDetailModel.getExtra();
        int pushCfg = 0;
        try {
            JSONObject jsonObject = new JSONObject(extra);
            if (jsonObject.has("temp_on")) {
                pushCfg = jsonObject.getInt("temp_on");
            }
            if (jsonObject.has("temp_h")) {
                th = jsonObject.getInt("temp_h");
            }
            if (jsonObject.has("temp_l")) {
                tl = jsonObject.getInt("temp_l");
            }
        } catch (JSONException e) {

        }
        if (pushCfg == 1) {
            wenDuBaoJingStatus = true;
        } else {
            wenDuBaoJingStatus = false;
        }
        setImageViewCheckOrUnCheck(wendu_baojing);
        mNewTempValue = Double.parseDouble(myApp.jinLiGangdetailUI.deviceDetailModel.getT_max()) / 10;
        txt_wendu_setting.setText(mNewTempValue + "℃");
        txt_wendu_sheding_high.setText(th / 10 + "℃");
        txt_wendu_sheding_low.setText(tl / 10 + "℃");
        if (wenduValue < 20) {
            img_progress.setCurrentCount(20);
        } else if (wenduValue > 35) {
            img_progress.setCurrentCount(35);
        } else {
            img_progress.setCurrentCount((float) wenduValue);
        }
        img_progress.invalidate();
        boolean needHot = false;
        if (mNewTempValue > wenduValue) {
            //当前温度值小于设定的温度，需要加热
            needHot = true;
        } else {
            //当前温度值大于设定的温度，不需要加热
            needHot = false;
        }
        txt_status.setText("加热棒工作状态:" + (needHot ? getString(R.string.open) : getString(R.string.alClose)));
        if (myApp.jinLiGangdetailUI.deviceDetailModel.getEx_dev().equalsIgnoreCase("AQ500")) {
            re_settemperature.setVisibility(View.GONE);
        } else {
            re_settemperature.setVisibility(View.VISIBLE);
        }
    }

    private void setImageViewCheckOrUnCheck(ImageView wendu_baojing) {
        if (wenDuBaoJingStatus) {
            wendu_baojing.setBackgroundResource(R.drawable.kai);
        } else {
            wendu_baojing.setBackgroundResource(R.drawable.guan);
        }
    }

    private void setHighOrLow(boolean isHigh, float temp) {
        if (isHigh) {
            //设置高温
//            userPresenter.deviceSet_806(myApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", temp + "", "", "", "", "", "", "", -1, -1, -1, -1);
            userPresenter.deviceSet_806Ph(myApp.jinLiGangdetailUI.deviceDetailModel.getId(), -1, -1, -1, -1, (int) temp, -1);
        } else {
            //设置低温
//            userPresenter.deviceSet_806(myApp.jinLiGangdetailUI.deviceDetailModel.getDid(), "", "", "", "", "", "", "", temp + "", "", "", "", "", "", -1, -1, -1, -1);
            userPresenter.deviceSet_806Ph(myApp.jinLiGangdetailUI.deviceDetailModel.getId(), -1, -1, -1, -1, -1, (int) temp);
        }
    }

}
