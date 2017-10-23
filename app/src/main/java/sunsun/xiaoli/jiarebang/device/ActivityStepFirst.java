package sunsun.xiaoli.jiarebang.device;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.utils.DeviceType;

/**
 * Created by Mr.w on 2017/3/4.
 * 此界面为通用界面
 */

public class ActivityStepFirst extends BaseActivity {
    TextView txt_title;
    ImageView img_back, img_right;
    Button btn_next;
    RatioImageView img;
    String type = "";
    int position;
    App app;
    TextView txt_description;

    //用于摄像头绑定的设备did
    String aq_did;
    DeviceType deviceType;
    public static ActivityStepFirst instance;

    public static ActivityStepFirst getInstance() {
        return instance;
    }

    public static void setInstance(ActivityStepFirst instance) {
        ActivityStepFirst.instance = instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_step1);
        instance = this;
        app = (App) getApplication();
        app.addDeviceFirst = this;
        deviceType= (DeviceType) getIntent().getSerializableExtra("device");
        aq_did = getIntent().getStringExtra("aq_did");
        txt_title.setText("添加" + getIntent().getStringExtra("device_type"));
        type = getIntent().getStringExtra("device_type");
        position = getIntent().getIntExtra("position", 0);
        if (type != null) {
            if (deviceType == DeviceType.DEVICE_CAMERA) {
                img.setBackgroundResource(R.drawable.smart_shexiangtou);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_shexiangtou)));
            } else if (deviceType == DeviceType.DEVICE_AQ806) {
                img.setBackgroundResource(R.drawable.aq);
            } else if (deviceType == DeviceType.DEVICE_AQ500) {
                img.setBackgroundResource(R.drawable.smart_500);
            } else if (deviceType == DeviceType.DEVICE_PH) {
                img.setBackgroundResource(R.drawable.smart_ph);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_smart_ph)));
            } else if (deviceType == DeviceType.DEVICE_JIAREBANG) {
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_jiarebang)));
                img.setBackgroundResource(R.drawable.add_jiarebangnew);
            } else if (deviceType == DeviceType.DEVICE_GUOLVTONG) {
                img.setBackgroundResource(R.drawable.add_jiarebang);
            } else if (deviceType == DeviceType.DEVICE_SHUIZUDENG) {
                img.setBackgroundResource(R.drawable.smart_light);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_smart_light)));
            } else if (deviceType == DeviceType.DEVICE_WEISHIQI) {
                img.setBackgroundResource(R.drawable.smart_weishiqi);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_weishiqi)));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                intent = new Intent(this, ActivityInputWifiAndPass.class);
                intent.putExtra("device_type", type);
                intent.putExtra("position", position);
                intent.putExtra("aq_did", aq_did == null ? "" : aq_did);
                startActivity(intent);
                break;
        }
    }
}
