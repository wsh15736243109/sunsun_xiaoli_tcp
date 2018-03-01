package sunsun.xiaoli.jiarebang.device.qibeng;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.DeviceStatusShow;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.MyBattery;
import sunsun.xiaoli.jiarebang.utils.loadingutil.PopupShuiBeng;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class DeviceQiBengBatteryDetailActivity extends BaseActivity implements Observer {

    //    ShuiBengButtomImageView shuibeng_wiget;
    ImageView shuibeng_wiget;
    ImageView img_yichangbaojing;
    ImageView img_right, img_back;
    private PopupShuiBeng popupShuiBeng;

    UserPresenter userPresenter;

    TextView txt_title;
    App mApp;
    public DeviceQiBengBatteryDetailActivity shuibengUI;
    public DeviceDetailModel deviceDetailModel;
    public boolean isConnect;
    TextView device_status;
    PtrFrameLayout ptr;
    private boolean isYiChangBaoJing;
    //    TextView txt_current_status_value;
    private int state;
    @IsNeedClick
    TextView txt_liuliangchoose, txt_weishitime;
    RelativeLayout re_liuliang_choose;
    RelativeLayout re_gif;
    @IsNeedClick
    TextView qibengbattery_leijicount, current_electricity;
    int seconds = 0;
    RelativeLayout re_battery, re_chuqiliang_choose, re_workmode;
    MyBattery battery_wiget;
    private int height, width;
    boolean hasDraw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_qi_beng_battery_detail);
        mApp = (App) getApplication();
        mApp.deviceQiBengBatteryUI = this;
        img_right.setVisibility(View.GONE);
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                beginRequest();
            }
        });
        img_right.setBackgroundResource(R.drawable.menu);
        userPresenter = new UserPresenter(this);
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void beginRequest() {
        userPresenter.getDeviceDetailInfo(mApp.deviceQiBengUI.deviceDetailModel.getDid(), getSp(Const.UID));
    }

    @Override
    protected void onDestroy() {
        mApp.deviceQiBengBatteryUI = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }

    }


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        ptr.refreshComplete();
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                finish();
                return;
            }
            if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                deviceDetailModel = (DeviceDetailModel) entity.getData();
                setData();
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deviceSet_shuiBengsuccess) {
                MAlert.alert(entity.getData());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        beginRequest();
                    }
                }, 1000);
            } else if (entity.getEventType() == UserPresenter.deviceSet_shuiBengfail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update_devicename_success) {
                MAlert.alert(entity.getData());
                refreshDeviceList();
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.shuibengExtraUpdate_success) {
                MAlert.alert(entity.getData());
                refreshDeviceList();
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.shuibengExtraUpdate_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void refreshDeviceList() {
        if (mApp.mXiaoLiUi != null) {
            mApp.mXiaoLiUi.getDeviceList();
        } else if (mApp.mDeviceUi != null){
            mApp.mDeviceUi.getDeviceList();
        }
    }

    public void setData() {
        deviceDetailModel = mApp.deviceQiBengUI.deviceDetailModel;
        txt_title.setText(deviceDetailModel.getDevice_nickname());
        isConnect = deviceDetailModel.getIs_disconnect().equals("0");
        seconds = 0;
        DeviceStatusShow.setDeviceStatus(device_status, deviceDetailModel.getIs_disconnect());
        try {
            JSONObject json = new JSONObject(deviceDetailModel.getExtra());
            if (json.has("push_cfg")) {
                isYiChangBaoJing = json.getInt("push_cfg") == 1;
            }
            if (json.has("fcd")) {
                seconds = json.getInt("fcd");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mApp.deviceQiBengUI.detailModelTcp != null) {
            int batt = mApp.deviceQiBengUI.detailModelTcp.getBatt();
//            txt_leijitime.setText(Html.fromHtml(String.format(getString(R.string.qibengbattery_chongdian_time), mApp.deviceQiBengUI.detailModelTcp.getWh())));
            qibengbattery_leijicount.setText(Html.fromHtml(String.format(getString(R.string.qibengbatter_leijichongdiancount), mApp.deviceQiBengUI.detailModelTcp.getB_life())));
            battery_wiget.setBatteryValue(batt);
            battery_wiget.setBatteryStatus(MyBattery.BatteryStatus.BatteryCHARGING, batt);
            current_electricity.setText(String.format(getString(R.string.current_electricity), batt));
            int state = mApp.deviceQiBengUI.detailModelTcp.getState();
            if ((mApp.deviceQiBengUI.detailModelTcp.getState() & (int) Math.pow(2, 1)) == Math.pow(2, 1)) {
                //充电中
            } else {
                //充电断开
            }
            //电池供电工作中
            if (((state & (int) Math.pow(2, 2)) != Math.pow(2, 2))
                    && (state & (int) Math.pow(2, 1)) == Math.pow(2, 1)) {
//                txt_current_status_value.setText("电池供电工作中");
            } else if (((state & (int) Math.pow(2, 2)) == Math.pow(2, 2))
                    && (state & (int) Math.pow(2, 1)) == Math.pow(2, 1)) {
                //充电并运行中
//                txt_current_status_value.setText("充电并运行中");
            } else if (batt >= 100
                    && (state & (int) Math.pow(2, 1)) != Math.pow(2, 1)) {
                //已充满，待机中
//                txt_current_status_value.setText("已充满，待机中");
            }
            if (((state & (int) Math.pow(2, 3)) == Math.pow(2, 3))
                    ) {
                //已充满，待机中
//                txt_current_status_value.setTextColor(getResources().getColor(R.color.red500));
                battery_wiget.setBatteryStatus(MyBattery.BatteryStatus.BatteryPowerSupply, batt);
            } else {
//                txt_current_status_value.setTextColor(getResources().getColor(R.color.main_green));
                battery_wiget.setBatteryStatus(MyBattery.BatteryStatus.BatteryCHARGING, batt);
            }


            //判断是否是低电量模式

        }
    }

}
