package sunsun.xiaoli.jiarebang.device.pondteam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.R;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.interfaces.SmartConfigTypeSingle;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.DeviceStatusShow;
import com.itboye.pondteam.utils.EmptyUtil;
import com.itboye.pondteam.utils.MyTimeUtil;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.utils.loadingutil.UIAlertView;
import com.itboye.pondteam.utils.popupwindow.SaveAlertView;
import com.itboye.pondteam.volley.ResultEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.utils.RequestUtil;
import sunsun.xiaoli.jiarebang.utils.TcpUtil;

import static com.itboye.pondteam.utils.ColoTextUtil.setColorfulValue;
import static com.itboye.pondteam.utils.ColoTextUtil.setDifferentSizeForTextView;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.volley.TimesUtils.getCurrentWeek;
import static com.itboye.pondteam.volley.TimesUtils.jiSuanNextTime;
import static com.itboye.pondteam.volley.TimesUtils.utc2Local;


/**
 * Created by Administrator on 2017/3/7.
 */

public class ActivityPondDeviceDetail extends BaseActivity implements UIAlertView.ClickListenerInterface, Observer, View.OnLongClickListener {
    RelativeLayout re_chazuo_1, re_chazuo_2, re_shoudong, re_dingshi, re_uvlamp;

    private UIAlertView dialog;

    ImageView img_back, img_right;
    String did;
    UserPresenter userPresenter;
    public DeviceDetailModel deviceDetailModel;
    TextView txt_title;
    TextView txt_dingshi_status, txt_last_cleantime, txt_next_cleantime;
    TextView txt_shoudong_clean_time;
    TextView txt_uv_open_time;
    TextView txt_uv_total_time;
    TextView chazuo_A_power;
    TextView chazuo_A_total_power;
    TextView chazuo_B_power;
    TextView chazuo_B_total_power;
    ImageView icon_setting_A;
    ImageView icon_setting_B;
    String id;
    private String newDeviceName = "";
    App myApp;
    String uid;
    TextView txt_chazuoA_name;
    TextView txt_chazuoB_name, txt_wendu, txt, device_status;
    boolean socketA = false, socketB = false;
    PtrFrameLayout ptr;
    ImageView loading, icon_setting_2, icon_setting_1, icon_setting_3;
    public boolean isConnect = false;
    DBManager dbManager;
    public String tempTime;
    TcpUtil mTcpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_detail);
        myApp = (App) getApplication();
        myApp.pondDeviceDetailUI = this;
        setCurrentTitle(getIntent().getStringExtra("device"));
        did = getIntent().getStringExtra("did");
        id = getIntent().getStringExtra("id");
        dbManager = new DBManager(this);
        userPresenter = new UserPresenter(this);
        uid = getSp(Const.UID);
        deviceDetailModel = (DeviceDetailModel) getIntent().getSerializableExtra("detailModel");
        setDefaultJieNengMode();
        BasePtr.setRefreshOnlyStyle(ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (deviceDetailModel != null) {
//                    setRefreshTime(deviceDetailModel.getUpdate_time());
                    setLoadingIsVisible(true);
                } else {
                    ptr.refreshComplete();
                }
            }
        });
        img_right.setImageResource(R.drawable.menu);
        Glide.with(this).load(R.drawable.loading).asGif().into(loading);
        chazuo_A_power.setOnLongClickListener(this);
        chazuo_B_power.setOnLongClickListener(this);
        chazuo_A_total_power.setOnLongClickListener(this);
        chazuo_B_total_power.setOnLongClickListener(this);
        re_chazuo_1.setOnLongClickListener(this);
        re_chazuo_2.setOnLongClickListener(this);
        threadStart();
        mTcpUtil = new TcpUtil(handData, did, uid, "101");
        mTcpUtil.start();
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println(Const.intervalTime + "间隔时间");
            handler.sendEmptyMessage(1);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            userPresenter.getDeviceOnLineState(did,getSp(Const.UID));
            handler.postDelayed(runnable, Const.getOnlinStateIntervalTime);
        }
    };

    public void beginRequst() {
        userPresenter.getDeviceDetailInfo(did, uid);
    }

    private void setCurrentTitle(String device) {
        txt_title.setText(device);
    }

    private void onShowDlog() {
        if (dialog == null) {
            dialog = new UIAlertView(this, getString(R.string.update_device), getString(R.string.delete_device), getString(R.string.cancel));
            dialog.setClicklistener(this);
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mTcpUtil != null) {
                mTcpUtil.releaseTcp();
            }
        } catch (Exception e) {

        }

        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.re_uvlamp || v.getId() == R.id.txt_uv_open_time || v.getId() == R.id.txt_uv_total_time || v.getId() == R.id.icon_setting_3) {
            intent = new Intent(this, ActivityUvLamp.class);
            intent.putExtra("model", deviceDetailModel);
            intent.putExtra("title", getString(R.string.UVLANMP));
            intent.putExtra("did", did);
            startActivity(intent);
        } else if (v.getId() == R.id.re_shoudong || v.getId() == R.id.txt_shoudong_clean_time || v.getId() == R.id.icon_setting_2) {
            intent = new Intent(this, ActivityShouDong.class);
            intent.putExtra("model", deviceDetailModel);
            intent.putExtra("title", getString(R.string.shoudong));
            startActivity(intent);
        } else if (v.getId() == R.id.re_dingshi || v.getId() == R.id.txt_dingshi_status || v.getId() == R.id.txt_last_cleantime || v.getId() == R.id.txt_next_cleantime || v.getId() == R.id.icon_setting_1) {
            intent = new Intent(this, ActivityZiDong.class);
            intent.putExtra("model", deviceDetailModel);
            intent.putExtra("title", getString(R.string.zidongqingxi));
            startActivity(intent);
        } else if (v.getId() == R.id.img_right) {
            onShowDlog();
        } else if (v.getId() == R.id.icon_setting_A) {
            if (!isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            userPresenter.deviceSet(did, null, null, "", -1, "", "", "", "", "", "", "", "", "", socketA ? 0 : 1, -1, "", "", "", "", -1, -1,"");
        } else if (v.getId() == R.id.icon_setting_B) {
            if (!isConnect) {
                MAlert.alert(getString(R.string.disconnect));
                return;
            }
            showProgressDialog(getString(R.string.posting), true);
            userPresenter.deviceSet(did, null, null, "", -1, "", "", "", "", "", "", "", "", "", -1, socketB ? 0 : 1, "", "", "", "", -1, -1,"");
        } else if (v.getId() == R.id.chazuo_A_power || v.getId() == R.id.chazuo_A_total_power || v.getId() == R.id.icon_setting_A || v.getId() == R.id.re_chazuo_1) {
            intent = new Intent(this, ActivityChaZuoBDetail.class);
            intent.putExtra("title", txt_chazuoA_name.getText().toString());
            intent.putExtra("chazuo_type", "A");
            startActivity(intent);
        } else if (v.getId() == R.id.chazuo_B_power || v.getId() == R.id.chazuo_B_total_power || v.getId() == R.id.icon_setting_B || v.getId() == R.id.re_chazuo_2) {
            intent = new Intent(this, ActivityChaZuoBDetail.class);
            intent.putExtra("title", txt_chazuoB_name.getText().toString());
            intent.putExtra("chazuo_type", "B");
            startActivity(intent);
        }
    }

    @Override
    public void doUpdateDevice(final String title) {
        if (dialog != null) {
            dialog.dismiss();
        }
        saveDialog = new SaveAlertView(this, title, "", getString(R.string.cancel), getString(R.string.ok), 2);
        saveDialog.show();
        saveDialog.setClicklistener(new SaveAlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                saveDialog.dismiss();
            }

            @Override
            public void doRight() {
                EditText edit = saveDialog.getEditTextView();
                boolean isEmpty = EmptyUtil.isEmpty(edit);

                if (isEmpty) {
                    MAlert.alert(getString(R.string.device_name_empty));
                } else {
                    newDeviceName = EmptyUtil.getCustomText(edit);
                    if (title.contains(getString(R.string.chazuo))) {
                        if (title.contains(getString(R.string.update_chazuoA))) {
                            //修改插座A操作
                            userPresenter.updateDeviceName(id, "", EmptyUtil.getCustomText(edit), "", "", "", -1, -1);
                        } else if (title.contains(getString(R.string.update_chazuoB))) {
                            //修改插座B操作
                            userPresenter.updateDeviceName(id, "", "", EmptyUtil.getCustomText(edit), "", "", -1, -1);
                        }
                    } else {
                        //修改设备昵称操作
                        userPresenter.updateDeviceName(id, EmptyUtil.getCustomText(edit), "", "", "", "", -1, -1);
                    }
                }
                saveDialog.dismiss();
            }
        });
    }

    @Override
    public void doDeleteDevice() {
        dialog.dismiss();
        showMakeSureDialog();
    }

    SaveAlertView saveDialog = null;

    private void showMakeSureDialog() {
        saveDialog = new SaveAlertView(this, getString(R.string.tips), getString(R.string.make_sure_delete), getString(R.string.cancel), getString(R.string.ok), 1);
        saveDialog.show();
        saveDialog.setClicklistener(new SaveAlertView.ClickListenerInterface() {
            @Override
            public void doLeft() {
                saveDialog.dismiss();
            }

            @Override
            public void doRight() {
                saveDialog.dismiss();
                //删除设备操作
                userPresenter.deleteDevice(id, getSp(Const.UID));
            }
        });
    }

    @Override
    public void doCancel() {
        dialog.dismiss();
    }

    @Override
    public void gujiangengxin() {
        if (dialog != null) {
            dialog.dismiss();
        }
        Intent intent = new Intent(this, VersionUpdateActivity.class);
        intent.putExtra("did", did);
        intent.putExtra("version", deviceDetailModel.getVer());
        intent.putExtra("deviceType", "S01");
        startActivity(intent);
    }

    @Override
    public void feedBack() {
        //        反馈
        if (dialog != null) {
            dialog.dismiss();
        }
        Intent intent = new Intent(this, FeedbackActivity.class);
        intent.putExtra("device_type", 1);
        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object data) {
        setLoadingIsVisible(false);
        ptr.refreshComplete();
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity resultEntity = handlerError(data);
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                finish();
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                deviceDetailModel = (DeviceDetailModel) resultEntity.getData();
                if (deviceDetailModel != null) {
                    setDeviceData();
                }
            } else if (resultEntity.getEventType() == UserPresenter.getdeviceinfofail) {
                finish();
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.update_devicename_success) {//修改成功
                userPresenter.getDeviceDetailInfo(did, uid);
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.update_devicename_fail) {//修改失败
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.deleteDevice_success) {//删除成功
                dbManager.deleteDeviceDataByDid(did, getSp(Const.UID));
                MAlert.alert(resultEntity.getData());
                finish();
            } else if (resultEntity.getEventType() == UserPresenter.deleteDevice_fail) {//删除失败
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.deviceSet_success) {//设置成功
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.deviceSet_fail) {//设置失败
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.getDeviceOnLineState_success) {
                deviceDetailModel = (DeviceDetailModel) resultEntity.getData();
                isConnect = deviceDetailModel.getIs_disconnect().equals("0");
                DeviceStatusShow.setDeviceStatus(device_status, deviceDetailModel.getIs_disconnect());
            } else if (resultEntity.getEventType() == UserPresenter.getDeviceOnLineState_fail) {
                isConnect = false;
                DeviceStatusShow.setDeviceStatus(device_status, "2");
            }
        }
    }

    int count = 0;

    private void setDeviceData() {
        count++;
        isConnect = deviceDetailModel.getIs_disconnect().equals("0");
        DeviceStatusShow.setDeviceStatus(device_status, deviceDetailModel.getIs_disconnect());
        device_status.setTextColor(isConnect ? getResources().getColor(R.color.main_green) : getResources().getColor(R.color.shen_red));
        if (isConnect) {
            txt.setVisibility(View.VISIBLE);
            String str = detailModelTcp.getT() / 10 + "℃";
            int startPo = (detailModelTcp.getT() / 10 + "").length();
            int endPo = str.length();
            setDifferentSizeForTextView(startPo, endPo, str, txt_wendu);
        } else {
            txt.setVisibility(View.GONE);
            txt_wendu.setText(getString(R.string.device) + getString(R.string.offline));
        }
        //定时清洗数据
        setDingShiQingXi(detailModelTcp);
        //手动清洗数据
        setShouDongQingXi(detailModelTcp);
        //UV LAMP数据
        setUVLamp(detailModelTcp);
        //插座A数据
        setChaZuoA(detailModelTcp);
        //插座B数据
        setChaZuoB(detailModelTcp);


        setCurrentTitle(deviceDetailModel.getDevice_nickname());

        //设置固件更新UI
        if (myApp.updateActivityUI != null) {
            if (myApp.updateActivityUI.smartConfigType == SmartConfigTypeSingle.UPDATE_ING) {//==3时名用户已经点击了开始更新，这里开始更新按钮进度
                myApp.updateActivityUI.setProgress(detailModelTcp.getUpd_state() + "");
            }
        }
        //设置自动清洗UI;
        if (myApp.ziDongUI != null) {
            myApp.ziDongUI.setZiDongData();
        }
        //设置UV LAMP UI;
        if (myApp.uvLampUI != null) {
            myApp.uvLampUI.setLampData();
        }
        //设置手动清洗数据
        if (myApp.shoudongUI != null) {
            myApp.shoudongUI.setShouDongData();
        }
        if (myApp.chazuoBDetail != null) {
            myApp.chazuoBDetail.setChaZuoData();
        }
    }

    private void setDefaultJieNengMode() {
        int outA = Integer.parseInt(deviceDetailModel.getOut_state_a());
        int outB = Integer.parseInt(deviceDetailModel.getOut_state_b());
        //设置初始状态下的插座A和插座B的节能模式
        if ((outA & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2)) {

        } else {
            //插座A默认为节能使能
            outA = outA ^ (int) Math.pow(2, 2);
        }

        if ((outB & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2)) {
            //插座B默认为节能禁止
            outB = outB ^ (int) Math.pow(2, 2);
        } else {

        }
        userPresenter.deviceSet(did, null, null, "", -1, "", "", "", "", "", "", "", "", "", outA, outB, "", "", "", "", -1, -1,"");
    }

    private void setChaZuoB(DeviceDetailModel deviceDetailModel) {
        socketB = deviceDetailModel.getOut_state_b().equals("0") ? false : true;
        icon_setting_B.setBackgroundResource(socketB ? R.drawable.kai : R.drawable.guan);
        txt_chazuoB_name.setText(this.deviceDetailModel.getNickname_b());
        setMode("B", chazuo_B_total_power);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> arTemp = new Gson().fromJson(this.deviceDetailModel.getOb_per_name(), type);
        if (myApp.chazuoBDetail != null) {
            myApp.chazuoBDetail.arB = arTemp;
        }
    }

    private void setChaZuoA(DeviceDetailModel deviceDetailModel) {
        socketA = deviceDetailModel.getOut_state_a().equals("0") ? false : true;
        icon_setting_A.setBackgroundResource(socketA ? R.drawable.kai : R.drawable.guan);
        txt_chazuoA_name.setText(this.deviceDetailModel.getNickname_a());
        setMode("A", chazuo_A_total_power);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> arTemp = new Gson().fromJson(this.deviceDetailModel.getOa_per_name(), type);
        if (myApp.chazuoBDetail != null) {
            myApp.chazuoBDetail.arA = arTemp;
        }
    }

    private void setUVLamp(DeviceDetailModel deviceDetailModel) {
        int startPo = getString(R.string.open_time).length();
        String timeStr = utc2Local(deviceDetailModel.getUv_on(), "HHmm", "HH:mm") + "-" + utc2Local(deviceDetailModel.getUv_off(), "HHmm", "HH:mm");
        int endPo = (getString(R.string.open_time) + timeStr).length();
        setColorfulValue(startPo, endPo, R.color.device_blue, (getString(R.string.open_time) + timeStr), txt_uv_open_time);

        startPo = getString(R.string.total_time).length();
        endPo = ((getString(R.string.total_time) + deviceDetailModel.getUv_wh()) + getString(R.string.hour)).length();
//        setColorfulValue(startPo, endPo, R.color.device_blue, getString(R.string.total_time) + deviceDetailModel.getUv_wh() + getString(R.string.hour), txt_uv_total_time);
        setMode();
    }

    private void setMode() {
        int uvState = detailModelTcp.getUv_state();
        int uvCfg = Integer.parseInt(detailModelTcp.getUv_cfg());
        if ((uvCfg & (int) Math.pow(2, 2)) == (int) Math.pow(2, 2)) {
            //手动模式
            if (uvState == 0) {
                //0关闭
                txt_uv_total_time.setText(Html.fromHtml(getString(R.string.status) + "<font color='#45B380'>" + getString(R.string.mode_shoudong_close) + "</font>"));
            } else {
                //1打开
                txt_uv_total_time.setText(Html.fromHtml(getString(R.string.status) + "<font color='#45B380'>" + getString(R.string.mode_shoudong_open) + "</font>"));
            }
        } else {
            //自动模式
            txt_uv_total_time.setText(Html.fromHtml(getString(R.string.status) + "<font color='#45B380'>" + getString(R.string.mode_zidong) + "</font>"));
        }
    }

    public void setShouDongQingXi(DeviceDetailModel deviceDetailModel) {
        txt_shoudong_clean_time.setText(String.format(getString(R.string.per_hour), deviceDetailModel.getCl_dur() / 60));//每次清洗时长
    }

    private void setDingShiQingXi(DeviceDetailModel deviceDetailModel) {
        int startPo = getString(R.string.dingshi).length();
        int endPo = startPo + ((deviceDetailModel.getCl_en().equals("0") ? getString(R.string.close) : getString(R.string.open))).length();
        setColorfulValue(startPo, endPo, R.color.main_green, getString(R.string.dingshi) + (deviceDetailModel.getCl_en().equals("0") ? getString(R.string.close) : getString(R.string.open)), txt_dingshi_status);

        //上次时间
        startPo = getString(R.string.last_clean_time).length();
        endPo = (getString(R.string.last_clean_time) + MyTimeUtil.parseTime1(deviceDetailModel.getCl_tm())).length();
        setColorfulValue(startPo, endPo, R.color.main_green, getString(R.string.last_clean_time) + MyTimeUtil.parseTime1(deviceDetailModel.getCl_tm()), txt_last_cleantime);


        //下次时间
        if (deviceDetailModel.getCl_week().equals("0")) {
            txt_next_cleantime.setVisibility(View.GONE);
        } else {
            txt_next_cleantime.setVisibility(View.GONE);
            tempTime = jiSuanNextTime(Integer.valueOf(deviceDetailModel.getCl_week()), getCurrentWeek(true), deviceDetailModel.getCl_tm());
            startPo = getString(R.string.next_clean_time).length();
            endPo = (getString(R.string.next_clean_time) + tempTime).length();
            setColorfulValue(startPo, endPo, R.color.device_blue, getString(R.string.next_clean_time) + tempTime, txt_next_cleantime);
        }

    }

    private void setMode(String chazuo_type, TextView textView) {
        int chaZuoBModeValue = 0;
        if (chazuo_type.equals("A")) {
            chaZuoBModeValue = Integer.parseInt(detailModelTcp.getOut_state_a());
        } else {
            chaZuoBModeValue = Integer.parseInt(detailModelTcp.getOut_state_b());
        }

        if ((chaZuoBModeValue & (int) Math.pow(2, 1)) == (int) Math.pow(2, 1)) {
            //自动模式
            textView.setText(Html.fromHtml(getString(R.string.status) + "<font color='#45B380'>" + getString(R.string.mode_zidong) + "</font>"));
        } else {
            //手动模式
            if ((chaZuoBModeValue & (int) Math.pow(2, 0)) == (int) Math.pow(2, 0)) {
                //通电即为开
                textView.setText(Html.fromHtml(getString(R.string.status) + "<font color='#45B380'>" + getString(R.string.mode_shoudong_open) + "</font>"));
            } else {
                //断电即为关
                textView.setText(Html.fromHtml(getString(R.string.status) + "<font color='#45B380'>" + getString(R.string.mode_shoudong_close) + "</font>"));
            }
        }
    }


    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.chazuo_A_power || v.getId() == R.id.chazuo_A_total_power || v.getId() == R.id.icon_setting_A || v.getId() == R.id.re_chazuo_1) {
            doUpdateDevice(getString(R.string.update_chazuoA));
        } else if (v.getId() == R.id.chazuo_B_power || v.getId() == R.id.chazuo_B_total_power || v.getId() == R.id.icon_setting_B || v.getId() == R.id.re_chazuo_2) {
            doUpdateDevice(getString(R.string.update_chazuoB));
        }
        return true;
    }
}
