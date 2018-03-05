package sunsun.xiaoli.jiarebang.device;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import ChirdSdk.CHD_LocalScan;
import ChirdSdk.CHD_SmartConf;
import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.SearchDeviceInfo;
import sunsun.xiaoli.jiarebang.interfaces.SmartConfigType;
import sunsun.xiaoli.jiarebang.popwindow.AutoDismissDialog;
import sunsun.xiaoli.jiarebang.utils.DeviceType;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.utils.Util.getNickName;


/**
 * Created by Mr.w on 2017/3/4.
 */

public class ActivityStepThree extends BaseActivity implements Observer, OnSmartLinkListener, AutoDismissDialog.ISmartConfigListener, DialogInterface.OnKeyListener {
    TextView btn_next, txt_title;
    String did;
    UserPresenter userPresenter;
    public boolean hasFind = false;
    private DeviceDetailModel model;
    String wifiName = "";
    String wifiPass = "";
    Gson gson = new Gson();
    ProgressDialog loadingDialog = null;
    protected ISmartLinker mSnifferSmartLinker;
    private SearchDeviceInfo searchDeviceInfo;
    App mApp;
    AlertDialog.Builder alert;
    public boolean isBusy = false;
    ImageView img_back;

    SmartConfigType smartConfigType;

    ImageView img_finish_search, img_finish_register, img_finish_add;
    TextView txt_search, txt_register, txt_add;
    ImageView progress_search, progress_register, progress_add;
    RelativeLayout re_search, re_register, re_add;
    boolean isRessBack;
    String type;

    CHD_SmartConf chdSmartConf;
    private AutoDismissDialog autoDismissDialog;
    private CHD_LocalScan mScan;		/* 本地设备搜索 */
    private Handler mTimeHandler;

    String aq_did;
    DeviceType deviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three);
        mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        mApp = (App) getApplication();
        mApp.addDeviceThird = this;
        mApp.isStartSearch = true;
        //搜索设备的加载图：progress_search
        Glide.with(getApplicationContext()).load(R.drawable.smartconfig_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(progress_search);//是将图片原尺寸缓存到本地。
        //注册设备的加载图：progress_register
        Glide.with(getApplicationContext()).load(R.drawable.smartconfig_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(progress_register);
        //添加设备的加载图：progress_add
        Glide.with(getApplicationContext()).load(R.drawable.smartconfig_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(progress_add);
        aq_did = getIntent().getStringExtra("aq_did");//得到主设备的did,摄像头才能绑定，如果主设备did为空，则不进行绑定
        alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        smartConfigType = SmartConfigType.SEARCHING;
        setZhuangTai(smartConfigType);
//        type = getIntent().getStringExtra("type");
        deviceType = (DeviceType) getIntent().getSerializableExtra("device");
        wifiName = getIntent().getStringExtra("wifi_name");
        wifiPass = getIntent().getStringExtra("wifi_pass");
//        did = getIntent().getStringExtra("did");
        txt_title.setText(getString(R.string.add_new_device));
        txt_search.setText(getString(R.string.searching) + "");

        userPresenter = new UserPresenter(this);
        isBusy = false;
        chdSmartConf = new CHD_SmartConf();
        //本地搜索摄像头监听
//        locanScanListener();
        mSnifferSmartLinker.setOnSmartLinkListener(this);
        if (deviceType != DeviceType.DEVICE_CAMERA) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage(getString(R.string.peizhiing));
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (smartConfigType != SmartConfigType.REGISTER_FINISH) {
                            smartConfigType = SmartConfigType.RESEARCH;
                            setZhuangTai(smartConfigType);
                        }
                        isRessBack = true;
                        mSnifferSmartLinker.stop();
                    }
                    return true;
                }
            });
            //开始 smartLink
            try {
                mSnifferSmartLinker.start(getApplicationContext(), wifiPass,
                        wifiName);
            } catch (Exception e) {
                closeProgressDialog();
                e.printStackTrace();
            }
        } else {
            int num = chdSmartConf.openSmartConfig(wifiName, wifiPass, "0");
            if (num == 0) {
                try {
                    autoDismissDialog = new AutoDismissDialog(this, getString(R.string.tips), getString(R.string.camera) + getString(R.string.peizhiing), getString(R.string.cancel), getString(R.string.ok), 30000, this);
                    autoDismissDialog.setCanceledOnTouchOutside(false);
                    autoDismissDialog.setOnKeyListener(this);
                    autoDismissDialog.show();
                } catch (Exception e) {

                }
            } else if (num <= 0) {
                smartConfigType = SmartConfigType.RESEARCH;
                setZhuangTai(smartConfigType);
                MAlert.alert(getString(R.string.open_fail));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setZhuangTai(SmartConfigType.RESEARCH);
        mSnifferSmartLinker.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (smartConfigType == SmartConfigType.ADD_FINISH) {
                    mApp.addPondDeviceUI.finish();
                    if (mApp.mXiaoLiUi != null) {
                        mApp.mXiaoLiUi.getDeviceList();
                    } else if (mApp.mDeviceUi != null) {
                        mApp.mDeviceUi.getDeviceList();
                    }
                    finish();
                    return;
                }
                if (smartConfigType == SmartConfigType.RESEARCH) {
                    isBusy = false;
                    smartConfigType = SmartConfigType.SEARCHING;
                    setZhuangTai(smartConfigType);
                    if (deviceType != DeviceType.DEVICE_CAMERA) {
                        loadingDialog = new ProgressDialog(this);
                        loadingDialog.setTitle(getString(R.string.tips));
                        loadingDialog.setCanceledOnTouchOutside(false);
                        loadingDialog.setMessage(getString(R.string.searching));
                        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    if (smartConfigType != SmartConfigType.REGISTER_FINISH) {
                                        smartConfigType = SmartConfigType.RESEARCH;
                                        setZhuangTai(smartConfigType);
                                    }
                                    isRessBack = true;
                                    mSnifferSmartLinker.stop();
                                }
                                return true;
                            }
                        });
                        loadingDialog.show();
                        try {
                            mSnifferSmartLinker.start(getApplicationContext(), wifiPass,
                                    wifiName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        int num = chdSmartConf.openSmartConfig(wifiName, wifiPass, "0");
                        if (num == 0) {
                            autoDismissDialog = new AutoDismissDialog(this, getString(R.string.tips), getString(R.string.camera) + getString(R.string.peizhiing), getString(R.string.cancel), getString(R.string.ok), 30000, this);
                            autoDismissDialog.setCanceledOnTouchOutside(false);
                            autoDismissDialog.setOnKeyListener(this);
                            autoDismissDialog.show();
                        } else if (num <= 0) {
                            smartConfigType = SmartConfigType.RESEARCH;
                            setZhuangTai(smartConfigType);
                            MAlert.alert(getString(R.string.open_fail));
                        }
                    }
                    return;
                }
                if (hasFind == false || searchDeviceInfo == null) {
                    MAlert.alert(getString(R.string.deviceid_error) + hasFind + ":" + searchDeviceInfo);
                    return;
                }
                if (aq_did != null) {
                    if (hasBindAq(searchDeviceInfo.getDid())) {
                        MAlert.alert(getString(R.string.hasBind) + searchDeviceInfo.getDid());
                        return;
                    }
                } else {
                    if (hasAdd(searchDeviceInfo.getDid())) {
                        MAlert.alert(getString(R.string.hasAdd) + searchDeviceInfo.getDid());
                        return;
                    }
                }
                smartConfigType = SmartConfigType.ADDING;
                btn_next.setEnabled(false);
                setZhuangTai(smartConfigType);
                ActivityInputWifiAndPass.getInstance().finish();
                if (ActivityStepFirst.getInstance() != null) {
                    ActivityStepFirst.getInstance().finish();
                }
                if (type.contains("chiniao_wifi_camera")) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("pwd", searchDeviceInfo.getPwd());
                    String extra = gson.toJson(hashMap);
                    userPresenter.addDevice(getSp(Const.UID), searchDeviceInfo.getDid(), BuildConfig.APP_TYPE.equals("小绵羊智能") ? MyApplication.getInstance().getResources().getString(R.string.device_zhinengshexiangtou_yihu) : MyApplication.getInstance().getResources().getString(R.string.device_zhinengshexiangtou), "chiniao_wifi_camera", extra);
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("first_upd", System.currentTimeMillis() + "");
                    if (BuildConfig.APP_TYPE.equals("pondTeam")) {
                        hashMap.put("notify_email", 1);
                    }
                    hashMap.put("pwd", searchDeviceInfo.getPwd());
                    String extra = gson.toJson(hashMap);
                    switch (deviceType) {
                        case DEVICE_AQ500:
                            userPresenter.addDevice(getSp(Const.UID), searchDeviceInfo.getDid(), App.getInstance().getString(R.string.device_zhineng500), type, extra);
                            break;
                        case DEVICE_AQ700:
                            userPresenter.addDevice(getSp(Const.UID), searchDeviceInfo.getDid(), App.getInstance().getString(R.string.device_zhineng700), type, extra);
                            break;
                        case DEVICE_AQ600:
                            userPresenter.addDevice(getSp(Const.UID), searchDeviceInfo.getDid(), App.getInstance().getString(R.string.device_zhineng600), type, extra);
                            break;
                        default:
                            userPresenter.addDevice(getSp(Const.UID), searchDeviceInfo.getDid(), getNickName(searchDeviceInfo.getDid(), type), type, extra);
                            break;
                    }
                }
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 检测是否已经添加过该设备
     *
     * @param did
     * @return
     */
    private boolean hasAdd(String did) {
        boolean isAdd = false;
        if (mApp.mXiaoLiUi != null) {
            for (int i = 0; i < mApp.mXiaoLiUi.arrayList.size(); i++) {
                if (did.equals(mApp.mXiaoLiUi.arrayList.get(i).getDid())) {
                    isAdd = true;
                    break;
                } else {
                    isAdd = false;
                }
            }
        } else if (mApp.mDeviceUi != null) {
            if (mApp.mDeviceUi.arrayList != null) {
                for (int i = 0; i < mApp.mDeviceUi.arrayList.size(); i++) {
                    if (did.equals(mApp.mDeviceUi.arrayList.get(i).getDid())) {
                        isAdd = true;
                        break;
                    } else {
                        isAdd = false;
                    }
                }
            }
        }
        return isAdd;
    }


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        btn_next.setEnabled(true);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                txt_search.setText(getString(R.string.deviceid_error) + entity.getCode());
                return;
            }
            if (entity.getEventType() == UserPresenter.getdeviceinfosuccess) {
                hasFind = true;
                model = (DeviceDetailModel) entity.getData();
//                if (deviceDetailModel.getIs_disconnect().equals("1")) {
//                    txt_search.setText(getString(R.string.disconnect) + "");
////                    img_finish_search.setVisibility(View.VISIBLE);
//                    hasFind = false;
//                } else {
                hasFind = true;
                smartConfigType = SmartConfigType.ADD_FINISH;
                setZhuangTai(smartConfigType);
//                }
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                smartConfigType = SmartConfigType.RESEARCH;
                setZhuangTai(smartConfigType);
                txt_search.setText(getString(R.string.research) + "");
                hasFind = false;
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.adddevice_success) {
                if (mApp.mXiaoLiUi != null) {
                    mApp.mXiaoLiUi.getDeviceList();
                } else if (mApp.mDeviceUi != null) {
                    mApp.mDeviceUi.getDeviceList();//设备劣列表页面应该重新获取数据
                }
                ///////---------------------------------如果是从806过来的，需要绑定摄像头到806上
                if (!"".equals(aq_did) && null != aq_did) {
                    //判断是否已经在绑定之列
                    boolean bindYes = hasBindAq(searchDeviceInfo.getDid());
                    if (!bindYes) {
                        userPresenter.cameraBind(aq_did, searchDeviceInfo.getDid(), "chiniao_wifi_camera", searchDeviceInfo.getDid(), searchDeviceInfo.getPwd());
                    } else {

                    }
                } else {
                    MAlert.alert(entity.getData());
                    if (mApp.addDeviceFirst != null) {
                        mApp.addDeviceFirst.finish();//关闭第一步页面
                    }
                    if (mApp.addDeviceUI != null) {
                        mApp.addDeviceUI.finish();//关闭添加设备类型页面
                    }
                    if (mApp.addPondDeviceUI != null) {
                        mApp.addPondDeviceUI.finish();//关闭添加设备类型页面
                    }

                    if (mApp.addDeviceInputWifi != null) {
                        mApp.addDeviceInputWifi.finish();//关闭输入ssid页面
                        mApp.addDeviceInputWifi = null;
                    }
                    if (mApp.mXiaoLiUi != null) {
                        mApp.mXiaoLiUi.mListView.smoothScrollToPosition(0);
                    } else if (mApp.mDeviceUi != null) {
                        mApp.mDeviceUi.mListView.smoothScrollToPosition(0);
                    }
                    finish();
                }

            } else if (entity.getEventType() == UserPresenter.adddevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraBind_success) {
                if (mApp.addDeviceFirst != null) {
                    mApp.addDeviceFirst.finish();//关闭第一步页面
                }
                if (mApp.addDeviceUI != null) {
                    mApp.addDeviceUI.finish();//关闭添加设备类型页面
                }
                if (mApp.addDeviceInputWifi != null) {
                    mApp.addDeviceInputWifi.finish();//关闭输入ssid页面
                }
                finish();
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraBind_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private boolean hasBindAq(String did) {
        boolean isAdd = false;
        if (mApp.mCameraDevice != null) {
            if (mApp.mCameraDevice.arrayList != null) {
                for (int i = 0; i < mApp.mCameraDevice.arrayList.size(); i++) {
                    if (did.equals(mApp.mCameraDevice.arrayList.get(i).getSlave_did())) {
                        isAdd = true;
                        break;
                    } else {
                        isAdd = false;
                    }
                }
            }
        }
        return isAdd;
    }

    @Override
    public void onLinked(SmartLinkedModule smartLinkedModule) {
//        loadingDialog.setMessage(getString(R.string.get_configing));
        smartConfigType = SmartConfigType.SEARCH_FINISH;
        setZhuangTai(smartConfigType);
//        smartConfigType = SmartConfigType.REGISTERING;
//        setZhuangTai(smartConfigType);
    }

    boolean configSuccess = false;
    int time = 0;

    @Override
    public void onCompleted() {
        isBusy = true;
        smartConfigType = SmartConfigType.REGISTER_FINISH;
        setZhuangTai(smartConfigType);
        loadingDialog.setMessage(getString(R.string.smartconfig_finish));
        configSuccess = true;
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time += 1000;
            handler.sendEmptyMessage(1);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.v(Const.TAG_SUNSUN, time + "");
            if (time >= 30000) {
                configSuccess = false;
                time = 0;
                loadingDialog.dismiss();
                showReason();
                smartConfigType = SmartConfigType.RESEARCH;
                setZhuangTai(smartConfigType);
                handler.removeCallbacks(runnable);
            } else {
                handler.postDelayed(runnable, 1000);
            }
        }
    };


    private void showReason() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        alert.setMessage(Html.fromHtml(getString(R.string.timeout_reason)));
        alert.setPositiveButton(getString(R.string.goto_lan), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ActivityStepThree.this, AddDeviceActivity.class);
                intent.putExtra("device", deviceType);
                intent.putExtra("aq_did", aq_did);
                startActivity(intent);
            }
        });
        alert.setNegativeButton(getString(R.string.goto_device_list), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ActivityStepThree.this, DeviceActivity.class);
                startActivity(intent);
            }
        });
        alert.setNeutralButton(getString(R.string.cancel), null);
        alert.show();
    }


    @SuppressLint("WrongConstant")
    public void setZhuangTai(SmartConfigType smartConfigType) {
        switch (smartConfigType) {
            case SEARCHING://搜索中
                btn_next.setText(getString(R.string.searching));
                txt_search.setText(getString(R.string.searching));
                re_search.setVisibility(View.VISIBLE);
                img_finish_search.setVisibility(View.GONE);
                progress_search.setVisibility(View.VISIBLE);
                break;
            case SEARCH_FINISH://搜索完成
                re_search.setVisibility(View.VISIBLE);
                img_finish_search.setVisibility(View.VISIBLE);
                progress_search.setVisibility(View.GONE);
                break;
            case REGISTERING://注册中
                btn_next.setText(getString(R.string.registering));
                txt_register.setText(getString(R.string.registering));
                re_register.setVisibility(View.VISIBLE);
                img_finish_register.setVisibility(View.GONE);
                progress_register.setVisibility(View.VISIBLE);
                break;
            case REGISTER_FINISH://注册完成
                btn_next.setText(getString(R.string.next));
                txt_register.setText(getString(R.string.registerfinsh));
                re_register.setVisibility(View.VISIBLE);
                img_finish_register.setVisibility(View.VISIBLE);
                progress_register.setVisibility(View.GONE);
                break;
            case ADDING://添加中
                btn_next.setText(getString(R.string.adding));
                txt_add.setText(getString(R.string.adding));
                re_add.setVisibility(View.VISIBLE);
                img_finish_add.setVisibility(View.GONE);
                progress_add.setVisibility(View.VISIBLE);
                break;
            case ADD_FINISH://添加完成
                btn_next.setText(getString(R.string.close));
                txt_add.setText(getString(R.string.addfinsh));
                re_add.setVisibility(View.VISIBLE);
                img_finish_add.setVisibility(View.VISIBLE);
                progress_add.setVisibility(View.GONE);
                break;
            case RESEARCH://重新搜索
                progress_search.setVisibility(View.GONE);
                img_finish_search.setVisibility(View.GONE);
                btn_next.setText(getString(R.string.research));
                re_search.setVisibility(View.VISIBLE);
                re_register.setVisibility(View.GONE);
                re_add.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSnifferSmartLinker.stop();
        mApp.addDeviceThird = null;
        mApp.isStartSearch = false;
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
    }

    @Override
    public void onTimeOut() {
        if (!isRessBack) {
            MAlert.alert(getString(R.string.timeout));
            txt_search.setText(getString(R.string.timeout));
        } else {
            MAlert.alert(getString(R.string.configuration_stop));
            txt_search.setText(getString(R.string.configuration_stop));
            isRessBack = false;
        }
        loadingDialog.dismiss();
        smartConfigType = SmartConfigType.RESEARCH;
        setZhuangTai(smartConfigType);
    }

    Dialog dialog;
    boolean isShow = false;

    @SuppressLint("WrongConstant")
    public void findNewDeviceInfo(final SearchDeviceInfo deviceInfo) {
        // 查空
        if (deviceInfo == null) {
            return;
        }
        did = deviceInfo.getDid();
        Log.v("stepThree", "搜索中");
        switch (deviceType) {
            case DEVICE_AQ806:
                if (!deviceInfo.getType().equalsIgnoreCase("S03")) {
                    return;
                }
                break;
            case DEVICE_AQ500:
                if (!deviceInfo.getType().equalsIgnoreCase("S03-1")) {
                    return;
                }
                break;
            case DEVICE_AQ700:
                if (!deviceInfo.getType().equalsIgnoreCase("S03-2")) {
                    return;
                }
                break;
            case DEVICE_AQ600:
                if (!deviceInfo.getType().equalsIgnoreCase("S03-3")) {
                    return;
                }
                break;
            case DEVICE_AQ118:
                if (!deviceInfo.getType().equalsIgnoreCase("S08")) {
                    return;
                }
                break;
            case DEVICE_JIAREBANG:
                if (!deviceInfo.getType().equalsIgnoreCase("S02")) {
                    return;
                }
                break;
            case DEVICE_PH:
                if (!deviceInfo.getType().equalsIgnoreCase("S04")) {
                    return;
                }
                break;
            case DEVICE_SHUIBENG:
                if (!deviceInfo.getType().startsWith("S05")) {
                    return;
                }
                break;
            case DEVICE_GUOLVTONG:
                if (!deviceInfo.getType().equalsIgnoreCase("S01")) {
                    return;
                }
                break;
            case DEVICE_CAMERA:
                if (!did.startsWith("SCHD")) {
                    return;
                } else {
//                    isBusy = true;
                }
                break;
            case DEVICE_SHUIZUDENG:
                if (!deviceInfo.getType().startsWith("S06")) {
                    //设备类型为S06-1,S06-2
                    return;
                }
                break;
            case DEVICE_QIBENG:
                if (!deviceInfo.getType().equalsIgnoreCase("S07")) {
                    return;
                }
                break;
            case DEVICE_WEISHIQI:
                if (!deviceInfo.getType().equalsIgnoreCase("S08")) {
                    return;
                }
                break;
        }
        if (mApp.mCameraDevice != null) {
            //添加从设备的
            if (hasBindAq(did)) {
//                if (!showHasBind) {
//                    MAlert.alert(getString(R.string.hasBind));
//                    showHasBind=true;
//                }
                return;
            }
        } else {
            //添加主设备的
            if (hasAdd(did)) {
//                if (!showHasBind) {
//                    MAlert.alert(getString(R.string.hasAdd));
//                    showHasAdd=true;
//                }
                return;
            }
        }
        Log.v("stepThree", "还在配置");
        //还未配置成功
        if (!isBusy) {
            return;
        }
        isBusy = false;
        // 停止配置
//        stopConfig();
        // 提示新设备
        img_finish_search.setVisibility(View.VISIBLE);
        hasFind = true;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (autoDismissDialog != null) {
            autoDismissDialog.dismiss();
        }
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        Log.d("stepThree", "配置完成");
        smartConfigType = SmartConfigType.SEARCH_FINISH;
        try {
            setZhuangTai(smartConfigType);
            smartConfigType = SmartConfigType.REGISTER_FINISH;
            setZhuangTai(smartConfigType);
            autoDismissDialog.setMessage(getString(R.string.smartconfig_finish));
            chdSmartConf.closeSmartConfig();
        } catch (Exception e) {

        }
        ActivityStepThree.this.searchDeviceInfo = deviceInfo;
        autoDismissDialog = null;
        alert.setTitle(getString(R.string.tips));
        alert.setMessage(getString(R.string.find_new_device) + " DID : \n" + deviceInfo.getDid());
        alert.setPositiveButton(getString(com.itboye.pondteam.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 停止配置
                isRessBack = true;
                type = searchDeviceInfo.getType();
                if (searchDeviceInfo.getDid().startsWith("SSAQ") || searchDeviceInfo.getDid().startsWith("SCHD")) {
                    type = "chiniao_wifi_camera";
                }
            }
        });
        type = searchDeviceInfo.getType();
        if (searchDeviceInfo.getDid().startsWith("SSAQ") || searchDeviceInfo.getDid().startsWith("SCHD")) {
            type = "chiniao_wifi_camera";
        }
        dialog = alert.create();
        if (!isShow) {
//            if (!dialog.isShowing()) {
            alert.show();
//            } else {
//                dialog.dismiss();
//            }
            isShow = true;
        }
    }

    @Override
    public void cameraConfigTimeOut() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_search.setText(getString(R.string.timeout));
                isBusy = false;
                smartConfigType = SmartConfigType.RESEARCH;
                setZhuangTai(smartConfigType);
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                if (autoDismissDialog != null) {
                    autoDismissDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void cameraConfigCancel() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MAlert.alert(getString(R.string.configuration_stop));
//                chdSmartConf.closeSmartConfig();
                smartConfigType = SmartConfigType.RESEARCH;
                setZhuangTai(smartConfigType);
            }
        });
    }

    @Override
    public void cameraCloseConfig() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isBusy = true;
//                smartConfigType = SmartConfigType.SEARCH_FINISH;
//                setZhuangTai(smartConfigType);
//                smartConfigType = SmartConfigType.REGISTER_FINISH;
//                setZhuangTai(smartConfigType);
//                autoDismissDialog.setMessage(getString(R.string.smartconfig_finish));
//                chdSmartConf.closeSmartConfig();
            }
        });
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            smartConfigType = SmartConfigType.RESEARCH;
            setZhuangTai(smartConfigType);
            autoDismissDialog.dismiss();
        }
        return true;
    }
}
