package sunsun.xiaoli.jiarebang.device.shuibeng;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.interfaces.SmartConfigTypeSingle;
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
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.utils.RequestUtil;
import sunsun.xiaoli.jiarebang.utils.loadingutil.PopupShuiBeng;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class DeviceShuiBengDetailActivity extends BaseActivity implements Observer {

    //    ShuiBengButtomImageView shuibeng_wiget;
    ImageView shuibeng_wiget;
    ImageView img_yichangbaojing;
    ImageView img_right, img_back;
    private PopupShuiBeng popupShuiBeng;

    UserPresenter userPresenter;

    TextView txt_title, txt_shuibengliuliang, txt_gonglv;
    App mApp;
    public DeviceShuiBengDetailActivity shuibengUI;
    private String did;
    public DeviceDetailModel deviceDetailModel;
    public boolean isConnect;
    TextView device_status;
    PtrFrameLayout ptr;
    private boolean isYiChangBaoJing;
    TextView txt_current_status_value;
    private int state;
    @IsNeedClick
    TextView txt_liuliangchoose, txt_weishitime;
    RelativeLayout re_weishi_time, re_liuliang_choose;
    RelativeLayout re_gif;
    @IsNeedClick
    TextView txt_status, txt_leijitime;
    int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_shui_beng_detail);
        mApp = (App) getApplication();
        mApp.deviceShuiBengUI = this;
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                beginRequest();
            }
        });
        did = getIntent().getStringExtra("did");
        img_right.setBackgroundResource(R.drawable.menu);
        userPresenter = new UserPresenter(this);
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            beginRequest();
            handler.sendEmptyMessage(1);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.postDelayed(runnable, Const.intervalTime);
        }
    };

    private void beginRequest() {
        userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
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
                intent.putExtra("did", did);
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
//                    userPresenter.deviceSet_shuiBeng(did, -1, -1, -1, isYiChangBaoJing ? 0 : 1, -1);
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
                        userPresenter.deviceSet_shuiBeng(did, -1, -1, currentGear, -1, -1, -1);
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
            case R.id.re_liuliang_choose:
                String[] liuliang = new String[5];
                for (int i = 0; i < liuliang.length; i++) {
                    liuliang[i] = String.format(getString(R.string.dang), i + 1);
                }
                showAlert(txt_liuliangchoose, getString(R.string.liuliang_choose), liuliang);
                break;
            case R.id.re_weishi_time:
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
                        userPresenter.deviceSet_shuiBeng(did, -1, -1, -1, -1, 2, seconds);
                        break;
                    case 1:
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 2);//设置为喂食状态
                        userPresenter.deviceSet_shuiBeng(did, -1, -1, -1, -1, 2, seconds);//
                        break;
                    case 2:
                        //设备有误不能进行操作
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 1);//设置为运行状态
//                        userPresenter.deviceSet_shuiBeng(mApp.deviceShuiBengUI.did, -1, -1, -1, -1, 1, -1);
                        MAlert.alert(getString(R.string.device_trouble));
                        break;
                    case 3:
//                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", seconds, 1);//设置为运行状态
                        userPresenter.deviceSet_shuiBeng(did, -1, -1, -1, -1, 1, -1);
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
                        userPresenter.deviceSet_shuiBeng(did, -1, -1, numberPicker.getValue() , -1, -1, -1);
                        break;
                    case R.id.txt_weishitime:
                        //设置喂食
                        int fcd = numberPicker.getValue() + 1;
                        userPresenter.shuibengExtraUpdate(deviceDetailModel.getId(), "", fcd * 60, -1);
//                        userPresenter.deviceSet_shuiBeng(did, -1, -1, -1, -1, -1, fcd * 60);
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
    public void threadStart() {
        RequestUtil.threadStart(handler, runnable);
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
                Const.intervalTime = 500;
                threadStart();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Const.intervalTime = 10000;
                    }
                }, 5000);

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

    private void setData() {
        txt_title.setText(mApp.mDeviceUi.mSelectDeviceInfo.getDevice_nickname());
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
        img_yichangbaojing.setBackgroundResource(isYiChangBaoJing ? R.drawable.kai : R.drawable.guan);
        txt_gonglv.setText(deviceDetailModel.getPwr() + "W");
        String strState = "";
        //运行状态
        state = deviceDetailModel.getState();
        switch (state) {
            case 0:
                //停机
                txt_status.setText(getString(R.string.stop));
                strState = String.format(getString(R.string.dang), deviceDetailModel.getGear() + 1) + "," + getString(R.string.stop);
//                String.format(getString(R.string.device_will), caculcateSeconds(deviceDetailModel.getFcd()));
                txt_status.setText(Html.fromHtml("<b>" + getString(R.string.normal) + "</b>"));
                Glide.with(this).load(R.drawable.weishi_stop).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shuibeng_wiget);
                break;
            case 1:
                //运行
                strState = (deviceDetailModel.getGear() + 1) + getString(R.string.status_running);
                txt_status.setText(Html.fromHtml("<b>" + getString(R.string.weishi) + "</b>"));
                Glide.with(this).load(R.drawable.weishi_running).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shuibeng_wiget);
                break;
            case 2:
                //故障
                strState = getString(R.string.device_error) + "," + getString(R.string.paichu);
                txt_status.setText(Html.fromHtml("<b>" + getString(R.string.error) + "</b>"));
                Glide.with(this).load(R.drawable.weishi_error_noch).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shuibeng_wiget);
                break;
            case 3:
                strState = String.format(getString(R.string.device_will), caculcateSeconds(deviceDetailModel.getFcd()));
//                strState = deviceDetailModel.getGear() + getString(R.string.status_running);
                txt_status.setText(Html.fromHtml("<b>" + getString(R.string.normal) + "</b>"));
                Glide.with(this).load(R.drawable.weishi_stop).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shuibeng_wiget);
                break;
        }
        txt_liuliangchoose.setText(String.format(getString(R.string.dang), deviceDetailModel.getGear() + 1));
        txt_weishitime.setText(seconds / 60 + getString(R.string.minute));

        txt_leijitime.setText(String.format(getString(R.string.leiji_time), deviceDetailModel.getOnline_time() / 3600.0));
//        shuibeng_wiget.setDangWei(deviceDetailModel.getGear());
//        shuibeng_wiget.setState(state);
        txt_current_status_value.setText(strState);
//        getLiuLiang();
        if (mApp.updateActivityUI != null) {
            if (mApp.updateActivityUI.smartConfigType == SmartConfigTypeSingle.UPDATE_ING) {//==3时名用户已经点击了开始更新，这里开始更新按钮进度
                mApp.updateActivityUI.setProgress(deviceDetailModel.getUpd_state() + "");
            }
        }
    }

    private String caculcateSeconds(int fcd) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = fcd % 3600;
        if (fcd > 3600) {
            h = fcd / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = fcd / 60;
            if (fcd % 60 != 0) {
                s = fcd % 60;
            }
        }
        return (d < 10 ? "0" + d : d) + ":" + (s < 10 ? "0" + s : s);
    }

    private void getLiuLiang() {
        int pw = deviceDetailModel.getGear();
        int shuiBengType = deviceDetailModel.getType() == null ? 0 : (Integer.parseInt(deviceDetailModel.getType().equals("") ? "0" : deviceDetailModel.getType()));
        int liuliang = 0;
        switch (pw) {
            case 0:
                liuliang = 0;
                break;
            case 1:
                switch (shuiBengType) {
                    case 0:
                    case -1:
                        liuliang = 3000;
                        break;
                    case 1:
                        liuliang = 6000;
                        break;
                }
                break;
            case 2:
                switch (shuiBengType) {
                    case 0:
                    case -1:
                        liuliang = 4000;
                        break;
                    case 1:
                        liuliang = 7500;
                        break;
                }
                break;
            case 3:
                switch (shuiBengType) {
                    case 0:
                    case -1:
                        liuliang = 4700;
                        break;
                    case 1:
                        liuliang = 8500;
                        break;
                }
                break;
            case 4:
                switch (shuiBengType) {
                    case 0:
                    case -1:
                        liuliang = 5400;
                        break;
                    case 1:
                        liuliang = 9400;
                        break;
                }
                break;
            case 5:
                switch (shuiBengType) {
                    case 0:
                    case -1:
                        liuliang = 6000;
                        break;
                    case 1:
                        liuliang = 10000;
                        break;
                }
                break;
        }
        txt_shuibengliuliang.setText(liuliang + "L/h");

    }
}
