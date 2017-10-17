package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.ActivityTemperature;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class DeviceAq806PhActivity extends BaseActivity implements Observer {

    @IsNeedClick
    ImageView img_back;
    ImageView img_phbaojing;
    @IsNeedClick
    TextView txt_title, txt_806ph, ph_806high, ph_806low;
    RelativeLayout re_806phzoushi, re_806dianjijiaozhun, re_806phgaowei, re_806phdiwei;
    App mApp;
    private DeviceDetailModel deviceDetailModel;
    private NumberPicker mPicker;
    private int mNewTempValue;
    UserPresenter userPresenter;
    String id;//设备列表的ID
    String extra;//设备列表的额外字段
    Gson gson = new Gson();
    private boolean isOpen;
    private double phH = 0;
    private double phL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_806_phdetail);
        mApp = (App) getApplication();
        id = mApp.mDeviceUi.mSelectDeviceInfo.getId();
        userPresenter = new UserPresenter(this);
        mApp.deviceAq806PhActivity = this;
        mApp.jinLiGangdetailUI.beginRequest();
        txt_title.setText(getString(R.string.ph_setting_title));
        set806pHData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.deviceAq806PhActivity = null;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.re_806phzoushi:
                if (deviceDetailModel == null || !mApp.jinLiGangdetailUI.isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }

                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("isPh", true);
                intent.putExtra("did", deviceDetailModel.getDid());
                intent.putExtra("topValue", phH);
                intent.putExtra("bottomValue", phL);
                intent.putExtra("title", getString(R.string.ph_history));
                startActivity(intent);
                break;
            case R.id.re_806dianjijiaozhun:
                intent = new Intent(this, Ph806JiaoZhunActivity.class);
                startActivity(intent);
                break;
            case R.id.img_phbaojing:
                if (deviceDetailModel == null || mApp.jinLiGangdetailUI.isConnect == false) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                userPresenter.deviceSet_806Ph(id, isOpen ? 0 : 1, -1, -1, -1, -1, -1);
                break;
            case R.id.re_806phdiwei:
                mNewTempValue = (int) (phL);
                mPicker = new NumberPicker(
                        this);
                mPicker.setMinValue(0);
                mPicker.setMaxValue(14);
                mPicker.setValue(mNewTempValue);
                mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        DeviceAq806PhActivity.this.mNewTempValue = newVal;
                    }
                });

                showAlertDialog(getString(R.string.ph_low), mPicker, 2, null);
                break;
            case R.id.re_806phgaowei:
                mNewTempValue = (int) (phH);
                mPicker = new NumberPicker(
                        this);
                mPicker.setMinValue(0);
                mPicker.setMaxValue(14);
                mPicker.setValue(mNewTempValue);
                mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        DeviceAq806PhActivity.this.mNewTempValue = newVal;
                    }
                });

                showAlertDialog(getString(R.string.ph_high), mPicker, 1, null);
                break;
        }
    }

    int ph_on = -1, ph_h = -1, ph_l = -1, temp_on = -1, temp_h = -1, temp_l = -1;

    public void showAlertDialog(String title, View view, final int type, final EditText edit) {
        final AlertDialog alert = new AlertDialog.Builder(this).
                setTitle(title).setView(view).setPositiveButton(getString(R.string.ok), null).setNegativeButton(getString(R.string.cancel), null).create();
        alert.show();
        alert.getButton(BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 1:
                        //设置ph高位
                        if (mPicker.getValue() * 100 < phL * 100) {
                            MAlert.alert(getString(R.string.ph_high_value_error));
                            return;
                        }
                        userPresenter.deviceSet_806Ph(id, ph_on, mPicker.getValue() * 100, ph_l, temp_on, temp_h, temp_l);
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 2:
                        //设置ph低位
                        if (mPicker.getValue() * 100 > phH * 100) {
                            MAlert.alert(getString(R.string.ph_low_value_error));
                            return;
                        }
                        userPresenter.deviceSet_806Ph(id, ph_on, ph_h, mPicker.getValue() * 100, temp_on, temp_h, temp_l);
//                        userPresenter.deviceSet_300Ph(did,-1,-1,-1,DevicePHDetailActivity.this.mNewTempValue*100,-1,-1,-1,-1,-1);
//                        ((TextView)textView).setText(edit.getText());
                        break;
                }
                alert.dismiss();
            }
        });
    }

    public void set806pHData() {
        deviceDetailModel = mApp.jinLiGangdetailUI.deviceDetailModel;
        if (mApp.jinLiGangdetailUI.detailModelTcp!=null) {
            txt_806ph.setText(String.format("%.2f", mApp.jinLiGangdetailUI.detailModelTcp.getPh() / 100));
        }
        if (deviceDetailModel != null) {
            extra = deviceDetailModel.getExtra();
            isOpen = false;
            phH = 0;
            phL = 0;
            try {
                JSONObject jsonObject = new JSONObject(extra);
                if (jsonObject.has("ph_on")) {
                    isOpen = jsonObject.getInt("ph_on") == 1;
                }
                if (jsonObject.has("ph_h")) {
                    phH = jsonObject.getDouble("ph_h") / 100;
                    ph_806high.setText(String.format("%.2f", phH));
                }
                if (jsonObject.has("ph_l")) {
                    phL = jsonObject.getDouble("ph_l") / 100;
                    ph_806low.setText(String.format("%.2f", phL));
                }
            } catch (JSONException e) {
                MAlert.alert(e.getLocalizedMessage() + "ERROR");
            }
            if (isOpen) {
                img_phbaojing.setBackgroundResource(R.drawable.kai);
            } else {
                img_phbaojing.setBackgroundResource(R.drawable.guan);
            }
        }else{
            MAlert.alert(getString(R.string.device_error));
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
            if (entity.getEventType() == UserPresenter.update806ph_success) {
                MAlert.alert(entity.getData());
                mApp.jinLiGangdetailUI.beginRequest();
            } else if (entity.getEventType() == UserPresenter.update806ph_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
