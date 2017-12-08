package sunsun.xiaoli.jiarebang.device.phdevice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.Gravity;
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
import sunsun.xiaoli.jiarebang.device.ActivityTemperature;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.popwindow.PHUpdate;
import sunsun.xiaoli.jiarebang.utils.RequestUtil;
import sunsun.xiaoli.jiarebang.utils.TcpUtil;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.itboye.pondteam.custom.ptr.BasePtr.setRefreshTime;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class DevicePHDetailActivity extends BaseActivity implements PHUpdate.ClickListenerInterface, Observer {

    RelativeLayout re_phzoushi, re_shuiwenzoushi, re_gaowenshezhi, re_diwenshezhi;
    RelativeLayout re_phgaowei, re_phdiwei, re_dianjijiaozhun;
    ImageView img_back, img_right;
    TextView txt_title, device_status;
    private PHUpdate popupWindow;
    private int mNewTempValue;
    App myApp;
    UserPresenter userPresenter;
    private String id;
    private String did;
    public DeviceDetailModel deviceDetailModel;

    TextView txt_ph, txt_wendu, ph_high, ph_low, wendu_high, wendu_di;
    public boolean isConnect;
    ImageView loading;

    ImageView img_phbaojing, img_wendubaojing;
    private boolean isWenDuBaoJing, isPhBaoJing;
    PtrFrameLayout ptr;
    private double ph_l;
    private double ph_h;
    private double temp_h;
    private double temp_l;
    private TcpUtil tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ph);
        myApp = (App) getApplication();
        myApp.devicePhUI = this;
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (deviceDetailModel != null) {
                    setRefreshTime(deviceDetailModel.getUpdate_time());
                    setLoadingIsVisible(true);
                    userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
                }
            }
        });
        Glide.with(getApplicationContext()).load(R.drawable.smartconfig_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(loading);
        setLoadingIsVisible(true);
        img_right.setBackgroundResource(R.drawable.menu);
        userPresenter = new UserPresenter(this);
        did = getIntent().getStringExtra("did");
        id = getIntent().getStringExtra("id");
        tcp = new TcpUtil(handData, did, getSp(Const.UID), "101");
        tcp.start();
        deviceDetailModel = (DeviceDetailModel) getIntent().getSerializableExtra("detailModel");
        userPresenter.deviceSet_300Ph(did, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        threadStart();
    }

    public DeviceDetailModel detailModelTcp;
    Handler handData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 101:
                    System.out.println("TCP 接收数据 101" + msg.obj);
                    break;
                case 102:
                    detailModelTcp = (DeviceDetailModel) msg.obj;
                    setData();
                    System.out.println("TCP 接收数据 102" + detailModelTcp.getDid());
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

    public void setData() {
        isConnect = deviceDetailModel.getIs_disconnect().equals("0");
        txt_title.setText(deviceDetailModel.getDevice_nickname());
        DeviceStatusShow.setDeviceStatus(device_status, deviceDetailModel.getIs_disconnect());
        String extra = deviceDetailModel.getExtra();
        try {
            JSONObject json = new JSONObject(extra);
            if (json.has("push_cfg")) {
                isWenDuBaoJing = (json.getInt("push_cfg") == 2 || json.getInt("push_cfg") == 3);
                isPhBaoJing = ((json.getInt("push_cfg") == 1 || json.getInt("push_cfg") == 3));
            }
            if (json.has("temp_l")) {
                temp_l = json.getDouble("temp_l");
                wendu_di.setText(String.format("%.1f", temp_l / 10) + "℃");
            }
            if (json.has("temp_h")) {
                temp_h = json.getDouble("temp_h");
                wendu_high.setText(String.format("%.1f", temp_h / 10) + "℃");
            }
            if (json.has("ph_h")) {
                ph_h = json.getDouble("ph_h");
                ph_high.setText(String.format("%.2f", ph_h / 100));
            }
            if (json.has("ph_l")) {
                ph_l = json.getDouble("ph_l");
                ph_low.setText(String.format("%.2f", ph_l / 100));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (detailModelTcp != null) {
            txt_ph.setText(String.format("%.2f ", detailModelTcp.getPh() / 100));
            txt_wendu.setText(String.format("%.1f ", detailModelTcp.getT() / 10) + "℃");
            //设置固件更新UI
            if (myApp.updateActivityUI != null) {
                if (myApp.updateActivityUI.smartConfigType == SmartConfigTypeSingle.UPDATE_ING) {//==3时名用户已经点击了开始更新，这里开始更新按钮进度
                    myApp.updateActivityUI.setProgress(detailModelTcp.getUpd_state() + "");
                }
            }

            if (myApp.phJiaoZhunUI != null) {
                myApp.phJiaoZhunUI.setData();
            }
        }
        img_wendubaojing.setBackgroundResource(isWenDuBaoJing ? R.drawable.kai : R.drawable.guan);
        img_phbaojing.setBackgroundResource(isPhBaoJing ? R.drawable.kai : R.drawable.guan);

    }

    private void beginRequest() {
        userPresenter.getDeviceOnLineState(did, getSp(Const.UID));
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        NumberPicker mPicker = null;
        switch (v.getId()) {
            case R.id.re_dianjijiaozhun:
                if (detailModelTcp == null) {
                    return;
                }
                intent = new Intent(this, PhJiaoZhunActivity.class);
                startActivity(intent);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_right:
                popupWindow = new PHUpdate(this, "", "", "");
                popupWindow.setClicklistener(this);
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.re_phgaowei:
                mNewTempValue = (int) (ph_h / 100);
                mPicker = new NumberPicker(
                        this);
                mPicker.setMinValue(0);
                mPicker.setMaxValue(14);
                mPicker.setValue(mNewTempValue);
                mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        DevicePHDetailActivity.this.mNewTempValue = newVal;
                    }
                });
                showAlertDialog(getString(R.string.ph_high), mPicker, 1, null);
                break;
            case R.id.re_phdiwei:
                mNewTempValue = (int) (ph_l / 100);
                mPicker = new NumberPicker(
                        this);
                mPicker.setMinValue(0);
                mPicker.setMaxValue(14);
                mPicker.setValue(mNewTempValue);
                mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        DevicePHDetailActivity.this.mNewTempValue = newVal;
                    }
                });
                showAlertDialog(getString(R.string.ph_low), mPicker, 2, null);
                break;
            case R.id.re_phzoushi:
                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("isPh", true);
                intent.putExtra("did", did);
                intent.putExtra("topValue", ph_h / 100 + "");
                intent.putExtra("bottomValue", ph_l / 100 + "");
                intent.putExtra("title", getString(R.string.ph_history));
                startActivity(intent);
                break;
            case R.id.re_shuiwenzoushi:
                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("isPh", false);
                intent.putExtra("did", did);
                intent.putExtra("topValue", temp_h + "");
                intent.putExtra("bottomValue", temp_l + "");
                intent.putExtra("title", getString(R.string.lishishuiwen));
                startActivity(intent);
                break;
            case R.id.re_diwenshezhi:
                mNewTempValue = (int) (temp_l / 10);
                mPicker = new NumberPicker(
                        this);
                mPicker.setMinValue(0);
                mPicker.setMaxValue(45);
                mPicker.setValue(mNewTempValue);
                mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        DevicePHDetailActivity.this.mNewTempValue = newVal;
                    }
                });
                showAlertDialog(getString(R.string.wendu_low), mPicker, 6, null);
                break;
            case R.id.re_gaowenshezhi:
                mNewTempValue = (int) (temp_h / 10);
                mPicker = new NumberPicker(
                        this);
                mPicker.setMinValue(0);
                mPicker.setMaxValue(45);
                mPicker.setValue(mNewTempValue);
                mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        DevicePHDetailActivity.this.mNewTempValue = newVal;
                    }
                });
                showAlertDialog(getString(R.string.wendu_high), mPicker, 5, null);
                break;
            case R.id.img_phbaojing:
                userPresenter.updateJiaoZhunTime(deviceDetailModel.getId(), judgeStatus(1), -1, -1, -1, -1, -1, -1);
//                userPresenter.deviceSet_300Ph(did, -1, -1, -1, -1, deviceDetailModel.getPush_cfg() ^ (int) Math.pow(2, 1), -1, -1, -1, -1);
                break;
            case R.id.img_wendubaojing:
                userPresenter.updateJiaoZhunTime(deviceDetailModel.getId(), judgeStatus(2), -1, -1, -1, -1, -1, -1);
//                userPresenter.deviceSet_300Ph(did, -1, -1, -1, -1, deviceDetailModel.getPush_cfg() ^ (int) Math.pow(2, 0), -1, -1, -1, -1);
                break;
        }

    }

    private int judgeStatus(int option) {
        //（0：全部关闭 1: 仅打开ph，2: 只打开温度，3：都打开）
        int req = -1;
        if (option == 1) {
            //ph选项
            if (isPhBaoJing && isWenDuBaoJing) {
                //关闭掉ph，仅打开温度
                req = 2;
            } else if (isPhBaoJing && !isWenDuBaoJing) {
                //都关闭
                req = 0;
            } else if (!isPhBaoJing && !isWenDuBaoJing) {
                //仅打开Ph
                req = 1;
            } else if (!isPhBaoJing && isWenDuBaoJing) {
                //都打开
                req = 3;
            }
        } else if (option == 2) {
            //温度选项
            if (isPhBaoJing && isWenDuBaoJing) {
                //仅打开ph
                req = 1;
            } else if (isPhBaoJing && !isWenDuBaoJing) {
                req = 3;
            } else if (!isPhBaoJing && !isWenDuBaoJing) {
                req = 2;
            } else if (!isPhBaoJing && isWenDuBaoJing) {
                req = 0;
            }
        }
        return req;
    }

    private void setPh(String title, int mNewTempValue, int type) {
        View view = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
        final EditText edit = (EditText) view.findViewById(R.id.editIntPart);
        edit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        showAlertDialog(title, view, type, edit);
    }

    @SuppressLint("WrongConstant")
    public void setLoadingIsVisible(boolean is) {
        if (is) {
            loading.setVisibility(View.VISIBLE);
        } else {
            ptr.refreshComplete();
            loading.setVisibility(View.GONE);
        }
    }

    public void showAlertDialog(String title, View view, final int type, final EditText edit) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        if (type == 1 || type == 2) {
            alert.setTitle(title);
            alert.setView(view);
        } else if (type == 3) {
            edit.setHint(title);
            alert.setView(view);
        } else if (type == 4) {
            alert.setMessage(getString(R.string.make_sure_delete));
        } else if (type == 5 || type == 6) {
            alert.setView(view);
        }
        alert.setPositiveButton(getString(R.string.ok), null);
        alert.setNegativeButton(getString(R.string.cancel), null);
//        final AlertDialog alertDialog = alert.create();
        final AlertDialog alertDialog = alert.show();
        alertDialog.getButton(BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 1:
                        //设置ph高位
                        if (mNewTempValue * 100 < ph_l) {
                            MAlert.alert(getString(R.string.ph_high_value_error));
                            return;
                        }
                        userPresenter.updateJiaoZhunTime(deviceDetailModel.getId(), -1, -1, -1, -1, DevicePHDetailActivity.this.mNewTempValue * 100, -1, -1);
//                        userPresenter.deviceSet_300Ph(did, -1, -1, DevicePHDetailActivity.this.mNewTempValue * 100, -1, -1, -1, -1, -1, -1);
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 2:
                        //设置ph低位
                        if (mNewTempValue * 100 > ph_h) {
                            MAlert.alert(getString(R.string.ph_high_value_error));
                            return;
                        }
                        userPresenter.updateJiaoZhunTime(deviceDetailModel.getId(), -1, -1, -1, DevicePHDetailActivity.this.mNewTempValue * 100, -1, -1, -1);
//                        userPresenter.deviceSet_300Ph(did, -1, -1, -1, DevicePHDetailActivity.this.mNewTempValue * 100, -1, -1, -1, -1, -1);
//                        ((TextView)textView).setText(edit.getText());
                        break;
                    case 3:
                        //修改设备名称
                        String nickname = edit.getText().toString();
                        if (nickname.equals("")) {
                            MAlert.alert(getString(R.string.device_name_empty));
                            return;
                        }
                        userPresenter.updateDeviceName(id, nickname, "", "", "", "", -1, -1);
                        break;
                    case 4:
                        //删除设备
                        userPresenter.deleteDevice(id, getSp(Const.UID));
                        break;
                    case 5:
                        //高温报警
                        if (mNewTempValue * 10 < temp_l) {
                            MAlert.alert(getString(R.string.high_tem_error));
                            return;
                        }
                        userPresenter.updateJiaoZhunTime(deviceDetailModel.getId(), -1, -1, DevicePHDetailActivity.this.mNewTempValue * 10, -1, -1, -1, -1);
//                        userPresenter.deviceSet_300Ph(did, DevicePHDetailActivity.this.mNewTempValue * 10, -1, -1, -1, -1, -1, -1, -1, -1);
                        break;
                    case 6:
                        //低温报警
                        if (mNewTempValue * 10 > temp_h) {
                            MAlert.alert(getString(R.string.low_tem_error));
                            return;
                        }
                        userPresenter.updateJiaoZhunTime(deviceDetailModel.getId(), -1, DevicePHDetailActivity.this.mNewTempValue * 10, -1, -1, -1, -1, -1);
//                        userPresenter.deviceSet_300Ph(did, -1, DevicePHDetailActivity.this.mNewTempValue * 10, -1, -1, -1, -1, -1, -1, -1);
                        break;
                }

                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void doUpdateDevice(String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
        EditText edit = (EditText) view.findViewById(R.id.editIntPart);
        showAlertDialog(getString(R.string.nickname), view, 3, edit);
    }

    @Override
    public void doDeleteDevice() {
        showAlertDialog(getString(R.string.tips), null, 4, null);
    }

    @Override
    public void fankui() {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    @Override
    public void gujiangengxin() {
        //固件
        Intent intent = new Intent(this,
                VersionUpdateActivity.class);
        intent.putExtra("did", did);
        intent.putExtra("version", deviceDetailModel.getVer());
        intent.putExtra("deviceType", "S06");
        startActivity(intent);
    }

    public void threadStart() {
        RequestUtil.threadStart(handler, runnable);
    }

    @Override
    public void update(Observable o, Object data) {
        setLoadingIsVisible(false);
        ResultEntity entity = handlerError(data);
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
            } else if (entity.getEventType() == UserPresenter.deviceSet_300success) {
                MAlert.alert(entity.getData());
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            } else if (entity.getEventType() == UserPresenter.deviceSet_300fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update_devicename_success) {
                MAlert.alert(entity.getData());
                myApp.mDeviceUi.getDeviceList();
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.updateJiaoZhunTime_success) {
                MAlert.alert(entity.getData());
                myApp.mDeviceUi.getDeviceList();
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            } else if (entity.getEventType() == UserPresenter.updateJiaoZhunTime_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getDeviceOnLineState_success) {
                DeviceDetailModel detailModel = (DeviceDetailModel) entity.getData();
                isConnect = detailModel.getIs_disconnect().equals("0");
                DeviceStatusShow.setDeviceStatus(device_status, detailModel.getIs_disconnect());
            } else if (entity.getEventType() == UserPresenter.getDeviceOnLineState_fail) {
                isConnect = false;
                DeviceStatusShow.setDeviceStatus(device_status, "2");
            }
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        try {
            if (tcp != null) {
                tcp.releaseTcp();
            }
        } catch (Exception e) {

        }
        super.onDestroy();
    }
}
