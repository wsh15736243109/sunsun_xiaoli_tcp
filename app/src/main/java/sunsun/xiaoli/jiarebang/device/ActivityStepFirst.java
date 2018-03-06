package sunsun.xiaoli.jiarebang.device;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.base.BaseActivity;

import sunsun.xiaoli.jiarebang.BuildConfig;
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
    //    String type = "";
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
        deviceType = (DeviceType) getIntent().getSerializableExtra("device");
        aq_did = getIntent().getStringExtra("aq_did");
        switch (deviceType) {
            case DEVICE_AQ806:
                img.setBackgroundResource(R.drawable.aq);
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhineng806));
                break;
            case DEVICE_AQ500:
                img.setBackgroundResource(R.drawable.smart_500);
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhineng500));
                break;
            case DEVICE_AQ700:
                img.setBackgroundResource(R.drawable.smart_700);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_smart_ph)));
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhineng700));
                break;
            case DEVICE_AQ118:
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhineng118));
                break;
            case DEVICE_AQ600:
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhineng600));
                break;
            case DEVICE_JIAREBANG:
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_jiarebang)));
                img.setBackgroundResource(R.drawable.add_jiarebangnew);
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhinengjiarebang));
                break;
            case DEVICE_PH:
                img.setBackgroundResource(R.drawable.smart_ph);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_smart_ph)));
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_yuancheng_ph));
                break;
            case DEVICE_SHUIBENG:
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_zhinengbianpinshuibeng));
                break;
            case DEVICE_GUOLVTONG:
                img.setBackgroundResource(R.drawable.pondteam_smartconfig);
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_chitangguolv));
                break;
            case DEVICE_CAMERA:
                img.setBackgroundResource(R.drawable.smart_shexiangtou);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_shexiangtou)));
                txt_title.setText(getString(R.string.add) + (BuildConfig.APP_TYPE.equals("小绵羊智能") ? getString(R.string.device_zhinengshexiangtou_yihu) : getString(R.string.device_zhinengshexiangtou)));
                break;
            case DEVICE_SHUIZUDENG:
                img.setBackgroundResource(R.drawable.smart_light);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_smart_light)));
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_shuizudeng));
                break;
            case DEVICE_QIBENG:
                txt_title.setText(getString(R.string.add) + (BuildConfig.APP_TYPE.equals("小绵羊智能") ?MyApplication.getInstance().getResources().getString(R.string.device_zhinengyangqibeng):MyApplication.getInstance().getResources().getString(R.string.device_zhinengqibeng)));
                break;
            case DEVICE_WEISHIQI:
                img.setBackgroundResource(R.drawable.smart_weishiqi);
                txt_description.setText(Html.fromHtml(getResources().getString(R.string.smartconfig_tip_weishiqi)));
                txt_title.setText(getString(R.string.add) + MyApplication.getInstance().getResources().getString(R.string.device_weishiqing));
                break;
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
                intent.putExtra("aq_did", aq_did == null ? "" : aq_did);
                intent.putExtra("device", deviceType);
                startActivity(intent);
                break;
        }
    }
}
