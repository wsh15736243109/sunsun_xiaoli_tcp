package sunsun.xiaoli.jiarebang.device;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

/**
 * Created by Mr.w on 2017/3/4.
 */

public class ActivityInputWifiAndPass extends BaseActivity implements Observer {
    ImageView img_back;
    Button btn_search_device;
    String type = "";
    App myApplication;
    EditText edit_wifiname, edit_wifipass;
    ToggleButton pass_hide_visible;
    int position;
    UserPresenter userPresenter;
    String aq_did;
    public static ActivityInputWifiAndPass instance;
    private String wifiName;
    private String wifi_name;

    public static ActivityInputWifiAndPass getInstance() {
        return instance;
    }

    public static void setInstance(ActivityInputWifiAndPass instance) {
        ActivityInputWifiAndPass.instance = instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputwifi);
        myApplication = (App) getApplication();
        myApplication.addDeviceInputWifi = this;
        userPresenter = new UserPresenter(this);
        aq_did = getIntent().getStringExtra("aq_did");
        position = getIntent().getIntExtra("position", position);
        type = getIntent().getStringExtra("device_type");
        wifiName = myApplication.aqSmartConfig.getCurrentWiFiSSID();
        if (wifiName == null || "".equals(wifiName)) {
            MAlert.alert(getString(R.string.connect_wifi));
        }
        setWIFINameText(wifiName);
        pass_hide_visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edit_wifipass.setTransformationMethod(!isChecked ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
            }
        });
        instance = this;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_search_device:
//                type = getIntent().getStringExtra("device_type");
//                if (type != null) {
//                    if (type.equals("摄像头")) {
//                        intent = new Intent(this, VideoActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (type.equals("AQ")) {
//                        intent = new Intent(this, ActivityStepThree.class);
//                        startActivity(intent);
//                    }
//                }
                wifi_name = edit_wifiname.getText().toString();
                if (wifi_name.equals("")) {
                    MAlert.alert(getString(R.string.connect_wifi));
                    return;
                }
                intent = new Intent(this, ActivityStepThree.class);
                intent.putExtra("position", position);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("type", type);
                intent.putExtra("wifi_name", wifi_name);
                intent.putExtra("wifi_pass", edit_wifipass.getText().toString());
                startActivity(intent);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        myApplication.addDeviceInputWifi = null;
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.adddevice_success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.adddevice_fail) {
                MAlert.alert(entity.getData());
            }

        }
    }

    public void setWIFINameText(String name) {
        edit_wifiname.setText(name);
    }
}