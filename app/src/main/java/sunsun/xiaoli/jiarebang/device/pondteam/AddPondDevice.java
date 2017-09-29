package sunsun.xiaoli.jiarebang.device.pondteam;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.ActivityInputWifiAndPass;
import sunsun.xiaoli.jiarebang.device.AddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.ManualAddDeviceActivity;


/**
 * Created by Administrator on 2017/3/7.
 */
public class AddPondDevice extends BaseActivity implements Observer {


    Button btn_match, btn_cancel;
    ImageView img_back;
    EditText ed_device_id;
    UserPresenter userPresenter;
    TextView txt_title;
    App myApp;
    private String did;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ponddevice);
        txt_title.setText(getString(R.string.add_device));
        myApp = (App) getApplication();
        myApp.addPondDeviceUI = this;
        userPresenter = new UserPresenter(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_match) {
//            did = getCustomText(ed_device_id);
//            boolean has = false;
//            for (DeviceListBean deviceListBean : myApp.mDeviceUi.arrayList) {
//                if (did.equals(deviceListBean.getDid())) {
//                    has = true;
//                    break;
//                } else {
//                    has = false;
//                }
//            }
//            if (has) {
//                MAlert.alert(getString(R.string.has));
//                return;
//            }
//            uid = SPUtils.get(this, null, Const.UID, "") + "";
//
//            if (isEmpty(did)) {
//                MAlert.alert(getString(R.string.deviceid_empty));
//                return;
//            }
            showPopwindow(1);


        } else if (i == R.id.btn_cancel) {
            finish();
//                String did = getCustomText(ed_device_id);
//                String uid = SPUtils.get(this, null, Const.UID, "") + "";
//                if (isEmpty(did)) {
//                    MAlert.alert(getString(R.stringWeeks.deviceid_empty));
//                    return;
//                }
//                userPresenter.addDevice(uid,did,"");

        } else if (i == R.id.img_back) {
            finish();
        }
    }

    private void showPopwindow(final int position) {
        View popView = View.inflate(this, R.layout.add_menu_windss, null);

        TextView open_configuration = (TextView) popView
                .findViewById(R.id.open_configuration);
        TextView open_camera = (TextView) popView
                .findViewById(R.id.open_camera);
        TextView pick_image = (TextView) popView.findViewById(R.id.pick_image);
        TextView camera_cancel_tv = (TextView) popView
                .findViewById(R.id.camera_cancel_tv);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels - 20;

        final PopupWindow popWindow = new PopupWindow(popView, width, ActionBar.LayoutParams.WRAP_CONTENT);
        // popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失


        open_configuration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                Intent intent = new Intent(AddPondDevice.this, ActivityInputWifiAndPass.class);
                intent.putExtra("device_type", getIntent().getStringExtra("device_type"));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        open_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 添加局域网设备
                popWindow.dismiss();
                Intent mainIntent = new Intent(AddPondDevice.this,
                        AddDeviceActivity.class);
                startActivity(mainIntent);
            }
        });
        pick_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 手动添加设备

                popWindow.dismiss();
                Intent mainIntent = new Intent(AddPondDevice.this,
                        ManualAddDeviceActivity.class);
                startActivity(mainIntent);
//				Intent mainIntent = new Intent(AddDeviceNewActivity.this,
//						ManualAddDeviceActivity.class);
//				startActivity(mainIntent);
            }
        });
        camera_cancel_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popWindow.dismiss();
            }
        });

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(popView, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity resultEntity = handlerError(data);
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.adddevice_success) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.adddevice_fail) {
                MAlert.alert(resultEntity.getData());
            }

        }
    }
}
