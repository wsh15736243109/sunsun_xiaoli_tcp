package sunsun.xiaoli.jiarebang.device.aq118;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import sunsun.xiaoli.jiarebang.custom.SpringProgressView;
import sunsun.xiaoli.jiarebang.device.ActivityTemperature;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.utils.ColoTextUtil;
import sunsun.xiaoli.jiarebang.utils.RequestUtil;
import sunsun.xiaoli.jiarebang.utils.TcpUtil;
import sunsun.xiaoli.jiarebang.utils.loadingutil.CameraConsolePopupWindow;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.NumberUtils.getAppointNumber;
import static sunsun.xiaoli.jiarebang.utils.ColoTextUtil.setColorfulValue;


/**
 * Created by Mr.w on 2017/3/4.
 * 2017/5/11 修改：添加设备同步时间
 */

public class Aq118DetailActivity extends BaseActivity implements Observer {
    //    private static final String CONNECTED_TEXT = "数据请求中,请稍后";
//    private static final String DISCONNECTED_TEXT = "当前设备已断开,请稍后重试";
    ImageView img_back, img_right;
    TextView txt_title;
    CameraConsolePopupWindow popupWindow;
    RelativeLayout re_wendu_history;
    RelativeLayout re_settemperature;
    RelativeLayout re_gaowen_sheding, re_diwen_sheding;
    private double mNewTempValue;
    @IsNeedClick
    TextView txt_wendu_sheding_high, txt_wendu_sheding_low, txt_wendu_setting;
    TextView txt_gonglv;
    SpringProgressView img_progress;
    public String did = "";
    UserPresenter userPresenter;
    public DeviceDetailModel deviceDetailModel;
    ImageView wendu_baojing, toggle_exception_warn, toggle_jieshoustatus;
    TextView wendu;
    String id;
    App myApp;
    TextView device_status;
    public boolean isConnect;
    boolean wenDuBaoJingStatus, gongZuoZhuangTaiTongtZhiStatus, yiChangBaoJingStatus;
    ImageView loading;
    PtrFrameLayout ptr;
    private String deviceType;
    TextView txt_status;
    private TcpUtil tcp;
    TextView txt_wendu_warn;
    private int th;
    private int tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        BasePtr.setRefreshOnlyStyle(ptr);
        userPresenter = new UserPresenter(this);
        myApp = (App) getApplication();

        deviceDetailModel = (DeviceDetailModel) getIntent().getSerializableExtra("detailModel");
        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (myApp.deviceJiaReBangUI.deviceDetailModel != null) {
//                    setRefreshTime(myApp.deviceJiaReBangUI.deviceDetailModel.getUpdate_time());
                }
                setLoadingIsVisible(true);
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            }
        });
        deviceType = "S08";
        Glide.with(this).load(R.drawable.loading).asGif().into(loading);
        myApp.aq118DetailActivityUI = this;
        img_right.setBackgroundResource(R.drawable.menu);
        popupWindow = new CameraConsolePopupWindow(
                Aq118DetailActivity.this, this);
        did = getIntent().getStringExtra("did");
        id = getIntent().getStringExtra("id");
        userPresenter.deviceSet_jiarebang(did, "", "", "", "");
        setDeviceTitle(getIntent().getStringExtra("title"));
        threadStart();
        tcp = new TcpUtil(handData, did, getSp(Const.UID), "101");
        tcp.start();
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
                    setDeviceData();
                    System.out.println("TCP 接收数据 102" + detailModelTcp.getDid());
                    break;
            }
        }
    };

    public void threadStart() {
        RequestUtil.threadStart(handler, runnable);
    }

    public void setLoadingIsVisible(boolean is) {
        if (is) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    public void setDeviceTitle(String text) {
        txt_title.setText(text);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println(Const.intervalTime + "间隔时间");
            handler.sendMessage(new Message());
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            userPresenter.getDeviceOnLineState(did, getSp(Const.UID));
            handler.postDelayed(runnable, Const.getOnlinStateIntervalTime);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        myApp.deviceJiaReBangUI = null;
        try {
            if (tcp != null) {
                tcp.releaseTcp();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_right:
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.re_wendu_history:
                if (detailModelTcp == null) {
                    return;
                }
                if (txt_wendu_sheding_high.getText().toString().equals("") || txt_wendu_sheding_low.getText().toString().equals("")) {
                    return;
                }
                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("did", did);
                intent.putExtra("isPh", false);
                intent.putExtra("title", getString(R.string.lishishuiwen));
                intent.putExtra("topValue", th + "");
                intent.putExtra("bottomValue", tl + "");
                startActivity(intent);
                break;
            case R.id.tvUpdate:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    View view = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
                    EditText edit = (EditText) view.findViewById(R.id.editIntPart);
                    showAlertDialog(getString(R.string.nickname), view, 3, edit);
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.pick_upgrade:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    //固件
                    intent = new Intent(this,
                            VersionUpdateActivity.class);
                    intent.putExtra("did", did);
                    intent.putExtra("version", deviceDetailModel.getVer());
                    intent.putExtra("deviceType", "S02");
                    startActivity(intent);
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.pick_Delete:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                showAlertDialog(getString(R.string.tips), null, 4, null);
                break;
            case R.id.pick_share:
                if (detailModelTcp == null) {
                    return;
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.pick_feedback:
//				反馈
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                intent = new Intent(this, FeedbackActivity.class);
                intent.putExtra("device_type", 2);
                startActivity(intent);
                break;
            case R.id.camera_cancel:
//				取消
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.re_gaowen_sheding:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    try {
                        mNewTempValue = Double.parseDouble((txt_wendu_sheding_high.getText().toString().substring(0, txt_wendu_sheding_high.getText().toString().length() - 1)));
                        setWenDu(getString(R.string.wendu_high), txt_wendu_sheding_high, mNewTempValue);
                    } catch (Exception e) {

                    }


                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.re_diwen_sheding:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    try {
                        mNewTempValue = Double.parseDouble((txt_wendu_sheding_low.getText().toString().substring(0, txt_wendu_sheding_low.getText().toString().length() - 1)));
                        setWenDu(getString(R.string.wendu_low), txt_wendu_sheding_low, mNewTempValue);
                    } catch (Exception e) {

                    }
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.re_settemperature:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {

                    try {
                        mNewTempValue = Double.parseDouble((txt_wendu_setting.getText().toString().substring(0, txt_wendu_setting.getText().toString().length() - 1)));
                        setWenDu(getString(R.string.wendu), txt_wendu_setting, mNewTempValue);
                    } catch (Exception e) {

                    }
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.toggle_exception_warn:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    showProgressDialog(getString(R.string.requesting), false);
                    setCheckOrUnCheck(toggle_exception_warn, !yiChangBaoJingStatus);
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.wendu_baojing:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    showProgressDialog(getString(R.string.requesting), false);
                    setCheckOrUnCheck(wendu_baojing, !wenDuBaoJingStatus);
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
            case R.id.toggle_jieshoustatus:
                if (detailModelTcp == null) {
                    return;
                }
                if (isConnect) {
                    showProgressDialog(getString(R.string.requesting), false);
                    setCheckOrUnCheck(toggle_jieshoustatus, !gongZuoZhuangTaiTongtZhiStatus);
                } else {
                    MAlert.alert(getString(R.string.disconnect), Gravity.CENTER);
                }
                break;
        }
    }

    private void setCheckOrUnCheck(ImageView toggle_exception_warn, boolean checked) {
        if (toggle_exception_warn.getId() == R.id.toggle_exception_warn) {
            //设置异常报警开关
//            原有的设置
//            userPresenter.deviceSet_jiarebang(did, "", "", checked ? "1" : "0", "");
//            现有的配置
            userPresenter.jiaReBangExtraUpdate(id, checked ? 1 : 0);
        } else if (toggle_exception_warn.getId() == R.id.wendu_baojing) {
            //温度报警
            userPresenter.updateDeviceName(id, "", "", "", "", "", checked ? 1 : 0, -1);
        } else if (toggle_exception_warn.getId() == R.id.toggle_jieshoustatus) {
            //接收工作状态通知
            userPresenter.updateDeviceName(id, "", "", "", "", "", -1, checked ? 1 : 0);
        }
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
                        if (edit.getText().toString().equals("")) {
                            MAlert.alert(getString(R.string.device_name_empty));
                            return;
                        }
                        //修改设备名称
                        userPresenter.updateDeviceName(id, edit.getText().toString(), "", "", "", "", -1, -1);
                        break;
                    case 4:
                        //删除设备
                        userPresenter.deleteDevice(id, getSp(Const.UID));
                        break;
                    case 5:
                        //高温报警
                        break;
                    case 6:
                        //低温报警
                        break;
                }
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

    float temp = 0;

    private void setWenDu(String title, final TextView textView, double mNewTempValue) {
        this.mNewTempValue = mNewTempValue;
        temp = (float) mNewTempValue;
        NumberPicker mPicker = new NumberPicker(
                this);

        if (textView.getId() == R.id.txt_wendu_setting) {
            mPicker.setMinValue(20);
            mPicker.setMaxValue(35);
        } else {
            mPicker.setMinValue(0);
            mPicker.setMaxValue(45);
        }
        mPicker.setValue((int) mNewTempValue);
        mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                temp = newVal;
            }
        });
        final AlertDialog mAlertDialog = new AlertDialog.Builder(
                this)
                .setTitle(title)
                .setView(mPicker)
                .setPositiveButton(getString(R.string.ok), null)
                .setNegativeButton(getString(R.string.cancel), null).create();
        mAlertDialog.show();
        mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (textView.getId()) {
                    case R.id.txt_wendu_setting:
                        userPresenter.deviceSet_jiarebang(did, temp * 10 + "", "", "", "");
                        break;
                    case R.id.txt_wendu_sheding_low:
                        if (temp * 10 >= deviceDetailModel.getTemp_max()) {
                            MAlert.alert(getString(R.string.low_tem_error));
                            return;
                        }
                        setHighOrLow(false, temp);
                        break;
                    case R.id.txt_wendu_sheding_high:
                        if (temp * 10 <= deviceDetailModel.getTemp_min()) {
                            MAlert.alert(getString(R.string.high_tem_error));
                            return;
                        }
                        setHighOrLow(true, temp);
                        break;
                }
                mAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void update(Observable o, Object data) {
        setLoadingIsVisible(false);
        try {
            ptr.refreshComplete();
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                finish();
                return;
            }
            if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                deviceDetailModel = (DeviceDetailModel) entity.getData();
                if (deviceDetailModel != null) {
                    setDeviceData();
                }
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deviceSet_success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deviceSet_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update_devicename_success) {
                MAlert.alert(entity.getData());
                if (myApp.mDeviceUi==null) {
                    myApp.mXiaoLiUi.getDeviceList();
                }else {
                    myApp.mDeviceUi.getDeviceList();
                }
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                if (myApp.mDeviceUi==null) {
                    myApp.mXiaoLiUi.getDeviceList();
                }else {
                    myApp.mDeviceUi.getDeviceList();
                }
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.jiaReBangExtraUpdate_success) {
                MAlert.alert(entity.getData());
                if (myApp.mDeviceUi==null) {
                    myApp.mXiaoLiUi.getDeviceList();
                }else {
                    myApp.mDeviceUi.getDeviceList();
                }
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            } else if (entity.getEventType() == UserPresenter.jiaReBangExtraUpdate_fail) {
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

    private void setDeviceData() {
        isConnect = deviceDetailModel.getIs_disconnect().equals("0");
        DeviceStatusShow.setDeviceStatus(device_status, deviceDetailModel.getIs_disconnect());
        setDeviceTitle(deviceDetailModel.getDevice_nickname());
        double wenduValue = 0;
        if (detailModelTcp != null) {
            mNewTempValue = Double.parseDouble(detailModelTcp.getT_set()) / 10;
            wenduValue = detailModelTcp.getT() / 10;
            int startPo1 = ("" + wenduValue).length();
            int endPo1 = (wenduValue + "℃").length();
            ColoTextUtil.setDifferentSizeForTextView(startPo1, endPo1, (wenduValue + "℃"), wendu);
            int fault = Integer.parseInt(Integer.toBinaryString(detailModelTcp.getFault()));
            String strFault = getAppointNumber(fault, 4);
            char[] faultBinary = strFault.toCharArray();
            StringBuffer str = new StringBuffer();
            boolean hasError = false;
            for (int i = 0; i < faultBinary.length; i++) {
                if (faultBinary[i] == '1') {
                    hasError = true;
                    break;
                } else {
                    hasError = false;
                }
            }
            String strTemp = "";
            if (hasError) {
                strTemp = getString(R.string.run_error);
            } else {
                int startPo3 = getString(R.string.total_power).length(), endPo3 = 0;
                endPo3 = getString(R.string.total_power).length() + (detailModelTcp.getPwr() + "").length();
                if (detailModelTcp.getPwr() == 0) {
                    //恒温状态
                    strTemp = getString(R.string.run_hengwen) + " ";
                } else {
                    //加热棒运行正常
                    strTemp = getString(R.string.run_normal) + " ";
                }
//                setColorfulValue(startPo3, endPo3, R.color.aq_orange,, txt_gonglv);
            }
            int startPo2 = getString(R.string.jiarebang).length();
            int endPo2 = (getString(R.string.jiarebang) + (strTemp)).length();
            setColorfulValue(startPo2, endPo2, R.color.aq_orange, getString(R.string.jiarebang) + strTemp + (hasError ? getString(R.string.paichu) : getString(R.string.total_power) + detailModelTcp.getPwr() + "W"), txt_status);
            img_progress.setMaxCount(35);
            if (wenduValue < 20) {
                img_progress.setCurrentCount(20);
            } else if (wenduValue > 35) {
                img_progress.setCurrentCount(35);
            } else {
                img_progress.setCurrentCount((float) wenduValue);
            }

            img_progress.invalidate();
            //设置固件更新UI
            if (myApp.updateActivityUI != null) {
                if (myApp.updateActivityUI.smartConfigType == SmartConfigTypeSingle.UPDATE_ING) {//==3时名用户已经点击了开始更新，这里开始更新按钮进度
                    myApp.updateActivityUI.setProgress(detailModelTcp.getUpd_state() + "");
                }
            }
        }

//        Bit0：加热棒过温异常
//        Bit1：温度传感器1异常
//        Bit2：温度传感器2异常
//        Bit3：加热丝开路异常
        wenDuBaoJingStatus = (deviceDetailModel.getTemp_alert() == 1 ? true : false);
        gongZuoZhuangTaiTongtZhiStatus = (deviceDetailModel.getIs_state_notify() == 1 ? true : false);
        try {
            JSONObject jsonObject = new JSONObject(deviceDetailModel.getExtra());
            if (jsonObject.has("abnormal")) {
                int exceptionValue = jsonObject.getInt("abnormal");
                yiChangBaoJingStatus = (exceptionValue == 1 ? true : false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        th = deviceDetailModel.getTemp_max();
        tl = deviceDetailModel.getTemp_min();
        if ((wenduValue < th / 10 && wenduValue > tl / 10)) {
            txt_wendu_warn.setCompoundDrawables(null, null, null, null);
        } else if ((wenduValue > th / 10 || wenduValue < tl / 10) && wenDuBaoJingStatus) {
            Drawable drawable_n = getResources().getDrawable(R.drawable.ic_warn);
            drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());
            txt_wendu_warn.setCompoundDrawables(null, null, drawable_n, null);
        }
        setImageViewCheckOrUnCheck(toggle_exception_warn, wendu_baojing, toggle_jieshoustatus);

        txt_wendu_setting.setText(mNewTempValue + "℃");
        txt_wendu_sheding_high.setText(Float.parseFloat(th + "") / 10 + "℃");
        txt_wendu_sheding_low.setText(Float.parseFloat(tl + "") / 10 + "℃");
    }

    private void setImageViewCheckOrUnCheck(ImageView toggle_exception_warn, ImageView wendu_baojing, ImageView toggle_jieshoustatus) {
        if (yiChangBaoJingStatus) {
            toggle_exception_warn.setBackgroundResource(R.drawable.kai);
        } else {
            toggle_exception_warn.setBackgroundResource(R.drawable.guan);
        }
        if (wenDuBaoJingStatus) {
            wendu_baojing.setBackgroundResource(R.drawable.kai);
        } else {
            wendu_baojing.setBackgroundResource(R.drawable.guan);
        }
        if (gongZuoZhuangTaiTongtZhiStatus) {
            toggle_jieshoustatus.setBackgroundResource(R.drawable.kai);
        } else {
            toggle_jieshoustatus.setBackgroundResource(R.drawable.guan);
        }
    }

    private void setHighOrLow(boolean isHigh, float temp) {
        if (isHigh) {
            //设置高温
            userPresenter.updateDeviceName(id, "", "", "", "", temp * 10 + "", -1, -1);
        } else {
            //设置低温
            userPresenter.updateDeviceName(id, "", "", "", temp * 10 + "", "", -1, -1);
        }
//        String high = getSp(Const.high_temp);
//        String low = getSp(Const.low_temp);
//        if (!high.equals("")) {
//            txt_wendu_sheding_high.setText(high + "℃");
//        }
//        if (!low.equals("")) {
//            txt_wendu_sheding_low.setText(low + "℃");
//        }
    }

}
