package sunsun.xiaoli.jiarebang.device.qibeng;

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

import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.utils.TcpUtil;
import sunsun.xiaoli.jiarebang.utils.loadingutil.PopupShuiBeng;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class DeviceQiBengDetailActivity extends BaseActivity implements Observer {

    //    ShuiBengButtomImageView shuibeng_wiget;
    ImageView shuibeng_wiget;
    ImageView img_yichangbaojing;
    ImageView img_right, img_back;
    private PopupShuiBeng popupShuiBeng;

    UserPresenter userPresenter;

    TextView txt_title;
    App mApp;
    public DeviceQiBengDetailActivity shuibengUI;
    private String did;
    public DeviceDetailModel deviceDetailModel;
    public boolean isConnect;
    TextView device_status;
    PtrFrameLayout ptr;
    private boolean isYiChangBaoJing;
    TextView txt_current_status_value;
    private int state;
    @IsNeedClick
    TextView txt_chuqiliangchoose, txt_weishitime;
    RelativeLayout re_liuliang_choose;
    RelativeLayout re_gif;
    @IsNeedClick
    TextView txt_status, txt_leijitime;
    int seconds = 0;
    RelativeLayout re_battery, re_chuqiliang_choose, re_workmode;
    @IsNeedClick
    TextView txt_workmode;
    ImageView img_workstatustips;
    private TcpUtil mTcpUtil;
    private int pushCfg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_qi_beng_detail);
        mApp = (App) getApplication();
        mApp.deviceQiBengUI = this;
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
        userPresenter.deviceSet_qibeng(did, -1, -1, -1, -1, -1, -1, -1, -1);
        showProgressDialog(getString(R.string.posting), true);
        mTcpUtil = new TcpUtil(handData, did, getSp(Const.UID), "101");
        mTcpUtil.start();
        new Thread(runnable).start();
    }

    public DeviceDetailModel detailModelTcp;
    Handler handData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 101:
                    System.out.println(mTcpUtil.getMsg() + "----->101 TCP 接收数据");
                    System.out.println(mTcpUtil.getMsg() + "----->101 TCP 接收数据 " + msg.obj);
                    break;
                case 102:
                    detailModelTcp = (DeviceDetailModel) msg.obj;
                    try {
                        closeProgressDialog();
                    } catch (Exception e) {

                    }
                    setData();
                    System.out.println(mTcpUtil.getMsg() + "----->102 TCP 接收数据 ");
                    System.out.println(mTcpUtil.getMsg() + "----->102 TCP 接收数据 " + detailModelTcp.getDid());
                    break;
            }
        }
    };

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
        switch (v.getId()) {
            case R.id.img_right:
                showMenu();
                break;
            case R.id.re_battery:
                if (detailModelTcp != null) {
                    startActivity(new Intent(this, DeviceQiBengBatteryDetailActivity.class));
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
                intent.putExtra("did", did);
                intent.putExtra("version", deviceDetailModel.getVer());
                intent.putExtra("deviceType", "S07");
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
                if (detailModelTcp == null) {
                    return;
                }
                pushCfg = detailModelTcp.getPush_cfg();
                if (isConnect) {
//                    userPresenter.shuibengExtraUpdate(mApp.mDeviceUi.mSelectDeviceInfo.getId(), isYiChangBaoJing ? "0" : "1", -1, -1);
                    userPresenter.deviceSet_qibeng(did, -1, -1, -1, -1, -1, -1, -1, pushCfg ^ (int) Math.pow(2, 1));
                } else {
                    MAlert.alert(getString(R.string.disconnect));
                }
                break;
            case R.id.img_workstatustips:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    pushCfg = detailModelTcp.getPush_cfg();
                    userPresenter.deviceSet_qibeng(did, -1, -1, -1, -1, -1, -1, -1, pushCfg ^ (int) Math.pow(2, 0));
                } else {
                    MAlert.alert(getString(R.string.disconnect));
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.re_chuqiliang_choose:
                if (detailModelTcp == null) {
                    return;
                }
                String[] liuliang = new String[5];
                for (int i = 0; i < liuliang.length; i++) {
                    liuliang[i] = String.format(getString(R.string.dang), i + 1);
                }
                showAlert(txt_chuqiliangchoose, getString(R.string.liuliang_choose), liuliang, detailModelTcp.getGear());
                break;
            case R.id.re_workmode:
                if (detailModelTcp == null) {
                    return;
                }
                String[] woekMode = new String[3];
                woekMode[0] = getString(R.string.mode_normal);
                woekMode[1] = getString(R.string.mode_jianxie);
                woekMode[2] = getString(R.string.mode_yingji);
                showAlert(txt_workmode, "", woekMode, Integer.parseInt(detailModelTcp.getMode()));
                break;
            case R.id.shuibeng_wiget:
                if (detailModelTcp == null) {
                    return;
                }
                userPresenter.deviceSet_qibeng(did, -1, -1, detailModelTcp.getState() == 0 ? 1 : 0, -1, -1, -1, -1, -1);
        }

    }

    private void showAlert(final TextView txt_liuliangchoose, String title, String[] msg, int selectValue) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
//        alert.setTitle(title);
        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setDisplayedValues(msg);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(msg.length - 1);
        numberPicker.setValue(selectValue);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (txt_liuliangchoose.getId()) {
                    case R.id.txt_chuqiliangchoose:
                        //设置档位
                        userPresenter.deviceSet_qibeng(did, -1, -1, -1, numberPicker.getValue(), -1, -1, -1, -1);
                        break;
                    case R.id.txt_workmode:
                        //设置工作模式
                        userPresenter.deviceSet_qibeng(did, -1, numberPicker.getValue(), -1, -1, -1, -1, -1, -1);
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
            } else if (entity.getEventType() == UserPresenter.deviceSet_qibeng_success) {
                MAlert.alert(entity.getData());
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.deviceSet_qibeng_fail) {
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
//        try {
//            JSONObject json = new JSONObject(deviceDetailModel.getExtra());
//            if (json.has("push_cfg")) {
//                isYiChangBaoJing = json.getInt("push_cfg") == 1;
//            }
//            if (json.has("fcd")) {
//                seconds = json.getInt("fcd");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        img_yichangbaojing.setBackgroundResource(isYiChangBaoJing ? R.drawable.kai : R.drawable.guan);
        //运行状态
        if (detailModelTcp != null) {
            state = detailModelTcp.getState();
            int mode = Integer.parseInt(detailModelTcp.getMode());
            switch (mode) {
                case 0:
                    txt_workmode.setText(getString(R.string.mode_normal));
                    break;
                case 1:
                    txt_workmode.setText(getString(R.string.mode_jianxie));
                    break;
                case 2:
                    txt_workmode.setText(getString(R.string.mode_yingji));
                    break;
            }
            int gear = detailModelTcp.getGear();
            if ((state & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
                if ((state & (int) Math.pow(2, 0)) == (int) Math.pow(2, 0)) {
                    txt_current_status_value.setText(getString(R.string.hasbeanuserelectricity));
                } else {
                    txt_current_status_value.setText(String.format(getString(R.string.qibeng_status), gear + 1, txt_workmode.getText(), getString(R.string.qibeng_running)));
                }
                txt_status.setText(Html.fromHtml("<b>" + getString(R.string.stop) + "</b>"));

                Glide.with(getApplicationContext()).load(R.drawable.weishi_running).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shuibeng_wiget);
            } else {
                txt_status.setText(Html.fromHtml("<b>" + getString(R.string.qidong) + "</b>"));
                txt_current_status_value.setText(String.format(getString(R.string.qibeng_status), gear + 1, txt_workmode.getText(), getString(R.string.qibeng_stop)));
                Glide.with(getApplicationContext()).load(R.drawable.weishi_stop).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(shuibeng_wiget);
            }
            txt_chuqiliangchoose.setText(String.format(getString(R.string.dang), gear + 1));
            txt_leijitime.setText(String.format(getString(R.string.leiji_time), detailModelTcp.getWh()));
            int push_cfg = detailModelTcp.getPush_cfg();
            if ((push_cfg & (int) Math.pow(2, 0)) == Math.pow(2, 0)) {
                img_workstatustips.setBackgroundResource(R.drawable.kai);
            } else {
                img_workstatustips.setBackgroundResource(R.drawable.guan);
            }
            if ((push_cfg & (int) Math.pow(2, 1)) == Math.pow(2, 1)) {
                isYiChangBaoJing = true;
            } else {
                isYiChangBaoJing = false;
            }
            img_yichangbaojing.setBackgroundResource(isYiChangBaoJing ? R.drawable.kai : R.drawable.guan);
            if (mApp.deviceQiBengBatteryUI != null) {
                mApp.deviceQiBengBatteryUI.setData();
            }
            if (mApp.updateActivityUI != null) {
                if (mApp.updateActivityUI.smartConfigType == SmartConfigTypeSingle.UPDATE_ING) {//==3时名用户已经点击了开始更新，这里开始更新按钮进度
                    mApp.updateActivityUI.setProgress(detailModelTcp.getUpd_state() + "");
                }
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

}
