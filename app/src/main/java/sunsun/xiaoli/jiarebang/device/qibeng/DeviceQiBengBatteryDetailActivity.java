package sunsun.xiaoli.jiarebang.device.qibeng;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
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
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
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
    TextView txt_current_status_value;
    private int state;
    @IsNeedClick
    TextView txt_liuliangchoose, txt_weishitime;
    RelativeLayout re_liuliang_choose;
    RelativeLayout re_gif;
    @IsNeedClick
    TextView txt_leijitime, qibengbattery_leijicount,current_electricity;
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
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                beginRequest();
            }
        });
        img_right.setBackgroundResource(R.drawable.menu);
        userPresenter = new UserPresenter(this);
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
        Intent intent = null;
        int currentGear = 0;
        if (deviceDetailModel != null) {
            currentGear = deviceDetailModel.getGear();
        }
        switch (v.getId()) {
            case R.id.img_right:
                showMenu();
                break;
            case R.id.battery_wiget:
                if (battery_wiget.getBatteryStatus() == MyBattery.BatteryStatus.BatteryPowerSupply) {
                    battery_wiget.setBatteryStatus(MyBattery.BatteryStatus.BatteryCHARGING, 5);
                } else {
                    battery_wiget.setBatteryStatus(MyBattery.BatteryStatus.BatteryPowerSupply, 3);
                }
                break;
            case R.id.tvUpdateDeviceName:
                if (popupShuiBeng != null && popupShuiBeng.isShowing()) {
                    popupShuiBeng.dismiss();
                }
                View view = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
                EditText edit = (EditText) view.findViewById(R.id.editIntPart);
                showAlertDialog(getString(R.string.nickname), view, 3, edit);
                break;
            case R.id.tvDelete:
                if (popupShuiBeng != null && popupShuiBeng.isShowing()) {
                    popupShuiBeng.dismiss();
                }
                showAlertDialog(getString(R.string.make_sure_delete), null, 4, null);
                break;
            case R.id.tvShengji:
                if (popupShuiBeng != null && popupShuiBeng.isShowing()) {
                    popupShuiBeng.dismiss();
                }
                intent = new Intent(this, VersionUpdateActivity.class);
                intent.putExtra("mApp.deviceQiBengUI.deviceDetailModel.getDid()", mApp.deviceQiBengUI.deviceDetailModel.getDid());
                intent.putExtra("version", deviceDetailModel.getVer());
                intent.putExtra("deviceType", "S05");
                startActivity(intent);
                break;
            case R.id.tvFanKui:
                if (popupShuiBeng != null && popupShuiBeng.isShowing()) {
                    popupShuiBeng.dismiss();
                }
                intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.img_yichangbaojing:
                if (mApp.deviceShuiBengUI.isConnect) {
//                    userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, -1, isYiChangBaoJing ? 0 : 1, -1);
                    userPresenter.shuibengExtraUpdate(mApp.mDeviceUi.mSelectDeviceInfo.getId(), isYiChangBaoJing ? "0" : "1", -1, -1);
                } else {
                    MAlert.alert(getString(R.string.disconnect));
                }
                break;
            case R.id.up:
                if (state == 2) {
                    MAlert.alert(getString(R.string.device_trouble));
                    return;
                }
                if (mApp.deviceShuiBengUI.isConnect && deviceDetailModel != null) {
                    if (currentGear < 4) {
                        currentGear++;
                        userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, currentGear, -1, -1, -1);
                    } else {
                        MAlert.alert(getString(R.string.highest));
                    }
                } else {
                    MAlert.alert(getString(R.string.disconnect));
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.re_chuqiliang_choose:
                String[] liuliang = new String[5];
                for (int i = 0; i < liuliang.length; i++) {
                    liuliang[i] = String.format(getString(R.string.dang), i + 1);
                }
                showAlert(txt_liuliangchoose, getString(R.string.liuliang_choose), liuliang);
                break;
            case R.id.re_workmode:
                String[] weishiTime = new String[60];
                for (int i = 0; i < weishiTime.length; i++) {
                    weishiTime[i] = (i + 1) + getString(R.string.minute);
                }
                showAlert(txt_weishitime, getString(R.string.weishi_timechoose), weishiTime);
                break;
            case R.id.shuibeng_wiget:
                switch (state) {
                    case 0:
                        //当前处于停机状态
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 2);//设置为喂食状态
                        userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, -1, -1, 2, seconds);
                        break;
                    case 1:
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 2);//设置为喂食状态
                        userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, -1, -1, 2, seconds);//
                        break;
                    case 2:
                        //设备有误不能进行操作
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 1);//设置为运行状态
//                        userPresenter.deviceSet_shuiBeng(mApp.deviceShuiBengUI.mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, -1, -1, 1, -1);
                        MAlert.alert(getString(R.string.device_trouble));
                        break;
                    case 3:
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 1);//设置为运行状态
                        userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, -1, -1, 1, -1);
                        break;
                }
                break;
        }

    }

    private void showAlert(final TextView txt_liuliangchoose, String title, String[] msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        alert.setTitle(title);
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setDisplayedValues(msg);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(msg.length - 1);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (txt_liuliangchoose.getId()) {
                    case R.id.txt_liuliangchoose:
                        //设置档位
                        userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, numberPicker.getValue(), -1, -1, -1);
                        break;
                    case R.id.txt_weishitime:
                        //设置喂食
                        int fcd = numberPicker.getValue() + 1;
                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", fcd * 60, -1);
//                        userPresenter.deviceSet_shuiBeng(mApp.deviceQiBengUI.deviceDetailModel.getDid(), -1, -1, -1, -1, -1, fcd * 60);
                        break;
                }
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setView(numberPicker);
        alert.show();
    }

    public void showAlertDialog(String title, View view, final int type, final EditText edit) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        if (type == 1 || type == 2 || type == 3) {
            edit.setHint(title);
            alert.setView(view);
        } else if (type == 4) {
            alert.setMessage(getString(R.string.make_sure_delete));
        } else if (type == 5 || type == 6) {
            alert.setView(view);
        }
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (type) {
                    case 1:
                        //设置高温
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 2:
                        //设置低温
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 3:
                        //修改设备名称
                        String nickName = edit.getText().toString();
                        if (TextUtils.isEmpty(nickName)) {
                            MAlert.alert(getString(R.string.device_name_empty));
                            return;
                        }
                        userPresenter.updateDeviceName(mApp.mDeviceUi.mSelectDeviceInfo.getId(), nickName, "", "", "", "", -1, -1);
                        break;
                    case 4:
                        //删除设备
                        userPresenter.deleteDevice(mApp.mDeviceUi.mSelectDeviceInfo.getId(), getSp(Const.UID));
                        break;
                    case 5:
                        //高温报警
                        break;
                    case 6:
                        //低温报警
                        break;
                }

//                ((TextView) textView).setText(edit.getText());
            }
        });
        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create();
        alert.show();
    }

    private void showMenu() {
        popupShuiBeng = new PopupShuiBeng(this, this);
        popupShuiBeng.show();
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
                mApp.mDeviceUi.getDeviceList();
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
                mApp.mDeviceUi.getDeviceList();
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.shuibengExtraUpdate_fail) {
                MAlert.alert(entity.getData());
            }
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
        txt_leijitime.setText(String.format(getString(R.string.qibengbattery_chongdian_time), deviceDetailModel.getWh()));
        qibengbattery_leijicount.setText(String.format(getString(R.string.qibengbatter_leijichongdiancount), deviceDetailModel.getCh_cnt()));
        battery_wiget.setBatteryValue(mApp.deviceQiBengUI.deviceDetailModel.getBatt());
        battery_wiget.setBatteryStatus(MyBattery.BatteryStatus.BatteryCHARGING,mApp.deviceQiBengUI.deviceDetailModel.getBatt());
        current_electricity.setText(String.format(getString(R.string.current_electricity),mApp.deviceQiBengUI.deviceDetailModel.getBatt()));

    }

}
