package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itboye.pondteam.base.BaseTwoActivity;
import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.custom.ptr.BasePtr;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.interfaces.SmartConfigTypeSingle;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.DeviceStatusShow;
import com.itboye.pondteam.utils.MyTimeUtil;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ChirdSdk.CHD_Client;
import ChirdSdk.CHD_LocalScan;
import ChirdSdk.ClientCallBack;
import ChirdSdk.StreamView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.custom.LoweLinearLayout;
import sunsun.xiaoli.jiarebang.custom.LoweRelaLayout;
import sunsun.xiaoli.jiarebang.device.ActivityTemperature;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.device.camera.CameraDeviceListActivity;
import sunsun.xiaoli.jiarebang.utils.RequestUtil;
import sunsun.xiaoli.jiarebang.utils.TcpUtil;
import sunsun.xiaoli.jiarebang.utils.loadingutil.CameraConsolePopupWindow;
import sunsun.xiaoli.jiarebang.utils.wifiutil.TrafficBean;

import static com.itboye.pondteam.custom.ptr.BasePtr.setRefreshTime;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.NumberUtils.getAppointNumber;
import static com.itboye.pondteam.utils.ScreenUtil.keepScreenOn;
import static sunsun.xiaoli.jiarebang.utils.FileOperateUtil.getFileSavePath;
import static sunsun.xiaoli.jiarebang.utils.FileOperateUtil.getTimesString;

//import static sunsun.xiaoli.jiarebang.R.id.mVideoLayout;

/**
 * Created by Administrator on 2017/3/6.
 */


public class JinLiGangDetailActivity extends BaseTwoActivity implements Observer {
    RelativeLayout re_wendushezhi, re_dengguangzhaoming, re_shajundeng, re_chonglangbeng, re_shipinguankan, re_shuiphsetting;
    RelativeLayout re_shuiph;//水温ph走势
    RelativeLayout re_shiduan;
    ImageView img_back;
    ImageView img_right;
    CameraConsolePopupWindow popupWindow;
    TextView img_close, img_open;

    UserPresenter userPresenter;
    String did;
    TextView txt_wendu, txt_title, txt_ph, txt_suanjiandu_status, txt_shipin_status, txt_wendustatus;
    ImageView img_shuiweibaojing, img_shebeisuoding;
    public DeviceDetailModel deviceDetailModel;
    Button btn_canshu, btn_vedio;
    LoweLinearLayout li_canshu;
    LoweRelaLayout li_shipin;
    TextView txt_dengguanggonglv;
    TextView txt_txt_shajundeng_status, txt_txt_chonglangbeng_status;
    TextView device_status;
    ImageView loading;
    public boolean isConnect;
    PtrFrameLayout ptr;
    App app;
    TextView txt_moshistatus;
    String id;
    boolean zhangmingdeng_status, shajundeng_status, chonglangbeng_status, mode_status;
    TextView txt_shuiwei_status, txt_shebeisuoding_status, txt_suanjiandu_status_setting;
    private boolean dev_lockStatus;
    private boolean shuiwei_status;
    private boolean ph_status;
    private int ph_status_value = -1;
    private int shuiwen_status_value;
    public char[] pushStrs;
    private boolean isLan;
    float height, width;
    LinearLayout li;
    TextView add;
    private boolean b;
    public StreamView mStreamView;		/* 视频刷新控件 */
    private CHD_Client mClient;		 /* 客户端类 */
    private CHD_LocalScan mScan;
    private Handler mTimeHandler;
    Runnable mTimeRunnable;
    private String imagePath;
    private TrafficBean trafficBean;
    DBManager dbManager;
    private Handler handlerWifi;
    boolean isSetTime = false;//同步时间
    public DeviceDetailModel detailModelTcp = new DeviceDetailModel();
    //摄像头设备列表
    private ArrayList<DeviceListBean> arrayList = new ArrayList<>();
    private String chirdDid;
    private String pass;
    private String todayTime;
    private String yesTerdayTime;
    private int totalFlow;
    private int[] flow;
    boolean hasSyncTime = false;
    @IsNeedClick
    TextView txt_shajundeng;
    private String currentTime;
    private TcpUtil mTcpUtil;

    private void clientCallBackListener() {

        mClient.setClientCallBack(new ClientCallBack() {

            public void paramChangeCallBack(int changeType) {
            }

            public void disConnectCallBack() {
                //视频连接状态
                txt_shipin_status.post(new Runnable() {
                    @Override
                    public void run() {
                        //视频连接状态
                        txt_shipin_status.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setCameraOpen(mClient.isConnect());
                    }
                });
            }

            public void snapBitmapCallBack(Bitmap bitmap) {
                MAlert.alert(getString(R.string.caturesuccess) + imagePath);
            }

            public void recordTimeCountCallBack(String times) {
            }

            public void recordStopBitmapCallBack(Bitmap bitmap) {
            }

            public void videoStreamBitmapCallBack(final Bitmap bitmap) {
                //视频连接状态
                txt_shipin_status.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            setCameraOpen(true);
                            txt_shipin_status.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
                        } else {
                            //视频连接状态
                            setCameraOpen(false);
                            txt_shipin_status.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
                        }
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mClient.isConnect()) {
                            txt_wangsu.setText(mClient.getVideoFrameBps());
                        } else {
//                            setCameraOpen(false);
                        }
                    }
                });
                mStreamView.showBitmap(bitmap);
            }

            public void videoStreamDataCallBack(int format, int width, int height, int datalen, byte[] data) {
            }

            public void serialDataCallBack(int datalen, byte[] data) {
                Log.v("test", "RecvSerialDataLen:" + datalen);
            }

            public void audioDataCallBack(int datalen, byte[] data) {
            }
        });
        mClient.connectDevice(chirdDid, pass);
        mClient.openVideoStream();
    }

    /**
     * 设摄像头状态
     *
     * @param b true:打开  false:关闭
     */
    private void setCameraOpen(boolean b) {
        if (b) {
//            txt_isOpen.setVisibility(View.GONE);
            mVideoLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
            txt_wangsu.setVisibility(View.VISIBLE);
            img_camera.setVisibility(View.VISIBLE);
            img_quanping.setVisibility(View.VISIBLE);
        } else {
            mVideoLayout.removeAllViews();
//            txt_isOpen.setVisibility(View.VISIBLE);
            mVideoLayout.setBackgroundColor(Color.parseColor("#000000"));
            txt_wangsu.setVisibility(View.GONE);
            img_camera.setVisibility(View.GONE);
            img_quanping.setVisibility(View.GONE);
        }
    }

    PtrDefaultHandler ptrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            if (deviceDetailModel != null) {
                setRefreshTime(deviceDetailModel.getUpdate_time());
                setLoadingIsVisible(true);
                userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiligang);
        BasePtr.setRefreshOnlyStyle(ptr);
        dbManager = new DBManager(this);
        app = (App) getApplication();
        mClient = new CHD_Client();
        keepScreenOn(this, true);
        app.jinLiGangdetailUI = this;
        userPresenter = new UserPresenter(this);
        currentTime = MyTimeUtil.getCurrentTime(System.currentTimeMillis() + "", "yyyyMMddHHmmss");
        txt_shipin_status.setText(getString(R.string.current_status) + getString(R.string.video_connecting));
        did = getIntent().getStringExtra("did");
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(0xffdcdddd);
        }
        id = getIntent().getStringExtra("id");
        deviceDetailModel = (DeviceDetailModel) getIntent().getSerializableExtra("detailModel");
        if (deviceDetailModel.getEx_dev().equalsIgnoreCase("AQ500")) {
            //强制关闭AQ500、AQ700的水位异常推送、冲浪泵提示
            int push = deviceDetailModel.getPush_cfg();
            if ((push & (int) Math.pow(2, 4)) == (int) Math.pow(2, 4)) {
                push = push ^ (int) Math.pow(2, 4);
            }
            if ((push & (int) Math.pow(2, 8)) == (int) Math.pow(2, 8)) {
                push = push ^ (int) Math.pow(2, 8);
            }
            /**
             * currentTime:同时同步设备时间
             */
            userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", push + "", "", -1, -1, -1, -1);
        } else if (deviceDetailModel.getEx_dev().equalsIgnoreCase("AQ700")) {
            int push = deviceDetailModel.getPush_cfg();
            //仅强制关闭水位报警提示
            if ((push & (int) Math.pow(2, 4)) == (int) Math.pow(2, 4)) {
                push = push ^ (int) Math.pow(2, 4);
            }
            /**
             * currentTime:同时同步设备时间
             */
            userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", push + "", "", -1, -1, -1, -1);
        } else if (deviceDetailModel.getEx_dev().equalsIgnoreCase("AQ806")) {
            userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
        }
        ptr.setPtrHandler(ptrHandler);
        img_right.setBackgroundResource(R.drawable.menu);
        Glide.with(getApplicationContext()).load(R.drawable.smartconfig_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(loading);
        popupWindow = new CameraConsolePopupWindow(
                this, this);
        // 方法2
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels * dm.density;
        height = dm.heightPixels * dm.density;
        ratioW2H = height / width;
        setLoadingIsVisible(true);
        new Thread(runnable).start();
        getDeviceList();
        mTcpUtil = new TcpUtil(handData, did, getSp(Const.UID), "101");
        mTcpUtil.start();
    }

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

    private void setDeviceDataByTcp(DeviceDetailModel detailModelTcp) {

    }

    public void beginRequest() {
        did = getIntent().getStringExtra("did");
        userPresenter.getDeviceDetailInfo(did, getSp(Const.UID));
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("request_params", Const.getOnlinStateIntervalTime + "间隔时间");
            userPresenter.getDeviceOnLineState(did);
            String wangsu = mClient == null ? "0" : mClient.getVideoFrameBps();
            try {
                if (wangsu.toLowerCase().endsWith("Kb/s".toLowerCase())) {
                    totalFlow += (Double.parseDouble(wangsu.substring(0, wangsu.length() - 4)) * 1024);
                } else if (wangsu.toLowerCase().endsWith("Mb/s".toLowerCase())) {
                    totalFlow += (Double.parseDouble(wangsu.substring(0, wangsu.length() - 4)) * 1024 * 1024);
                } else if (wangsu.toLowerCase().endsWith("b/s".toLowerCase())) {
                    totalFlow += (Double.parseDouble(wangsu.substring(0, wangsu.length() - 4)));
                }
            } catch (Exception e) {

            }
            handler.postDelayed(runnable, Const.getOnlinStateIntervalTime);
        }
    };

    public void setLoadingIsVisible(boolean is) {
        if (is) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        if (chirdDid != null && flow != null) {
            int[] re = dbManager.queryFlow(chirdDid, getSp(Const.UID), todayTime + "");
            if (re[0] <= 0) {
                long con = dbManager.insertFlowData(chirdDid, getSp(Const.UID), todayTime, (flow[1] + totalFlow) + "");
            } else {
                long con = dbManager.updateFlowData(chirdDid, getSp(Const.UID), todayTime, (flow[1] + totalFlow) + "");
            }
        }
        try {
            if (mClient != null) {
                mClient.disconnectDevice();
                mClient.closeVideoStream();
            }
        } catch (Exception e) {

        }
        keepScreenOn(this, false);
        app.jinLiGangdetailUI = null;
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.re_shuiphsetting:
                if (detailModelTcp == null) {
                    MAlert.alert(getString(R.string.device_error));
                    return;
                }
                intent = new Intent(this, DeviceAq806PhActivity.class);
                intent.putExtra("did", did);
                startActivity(intent);
                break;
            case R.id.re_wendushezhi:
                intent = new Intent(this, DeviceAq806TemperatureActivity.class);
//                intent.putExtra("isPh", false);
//                intent.putExtra("did", did);
//                intent.putExtra("topValue", "35");
//                intent.putExtra("bottomValue", "20");
//                intent.putExtra("title", getString(R.string.lishishuiwen));
                startActivity(intent);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_right:
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.re_shiduan:
                if (deviceDetailModel == null) {
                    if (!isConnect) {
                        MAlert.alert(getString(R.string.disconnect));
                        return;
                    }
                    MAlert.alert(getString(R.string.deviceid_error));
                    return;
                }
                intent = new Intent(this, PeriodActivity.class);
                intent.putExtra("title", getString(R.string.light_zhaoming));
                startActivity(intent);
                break;
            case R.id.btn_canshu:
                setCanShuOrVedioData(true);
                break;
            case R.id.btn_vedio:
                setCanShuOrVedioData(false);
                break;
            case R.id.re_shipinguankan:
                intent = new Intent(this, CameraDeviceListActivity.class);
                intent.putExtra("title", getString(R.string.shipinguankan));
                intent.putExtra("did", did);
                startActivity(intent);
                break;
            case R.id.img_open:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                if ((Boolean) img_open.getTag()) {
                    MAlert.alert(getString(R.string.mode_ismanual));
                } else {
                    showProgressDialog(getString(R.string.posting), true);
                    userPresenter.deviceSet_806(did, "", "0", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
                }
                break;
            case R.id.re_shuiph:
                if (detailModelTcp == null) {
                    MAlert.alert(getString(R.string.device_error));
                    return;
                }
                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("isPh", true);
                intent.putExtra("did", did);
                intent.putExtra("topValue", "8");
                intent.putExtra("bottomValue", "5");
                intent.putExtra("title", getString(R.string.ph_history));
                startActivity(intent);
                break;
            case R.id.img_close:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                if ((Boolean) img_close.getTag()) {
                    MAlert.alert(getString(R.string.mode_isauto));
                } else {
                    showProgressDialog(getString(R.string.posting), true);
                    userPresenter.deviceSet_806(did, "", "1", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
                }
                break;
            case R.id.re_shuiwenzoushi:
                intent = new Intent(this, ActivityTemperature.class);
                intent.putExtra("title", getString(R.string.shuizhisuanjianqushi));
                startActivity(intent);
                break;
            case R.id.tvUpdate:
//                if (isConnect == true) {
//                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
//                    return;
//                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                View view = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
                EditText edit = (EditText) view.findViewById(R.id.editIntPart);
                showAlertDialog(getString(R.string.nickname), view, 3, edit);
                break;
            case R.id.pick_upgrade:
//                if (isConnect == true) {
//                    MAlert.alert(DISCONNECTED_TEXT, Gravity.CENTER);
//                    return;
//                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                //固件
                intent = new Intent(this,
                        VersionUpdateActivity.class);
                intent.putExtra("version", deviceDetailModel.getVer());
                intent.putExtra("did", did);
                intent.putExtra("deviceType", "S03");
                startActivity(intent);
                break;
            case R.id.pick_Delete:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                showAlertDialog(getString(R.string.tips), null, 4, null);
                break;
            case R.id.pick_share:
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
                intent.putExtra("device_type", 3);
                startActivity(intent);
                break;
            case R.id.camera_cancel:
//				取消
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.re_chonglangbeng:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                if (mode_status) {
                    MAlert.alert(getString(R.string.changeshodongatfirst));
                    return;
                }
                showProgressDialog(getString(R.string.posting), true);
                userPresenter.deviceSet_806(did, "", "", "", chonglangbeng_status ? "0" : "1", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
                break;
            case R.id.re_dengguangzhaoming:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                if (mode_status) {
                    MAlert.alert(getString(R.string.changeshodongatfirst));
                    return;
                }
                showProgressDialog(getString(R.string.posting), true);
                userPresenter.deviceSet_806(did, "", "", "", "", zhangmingdeng_status ? "0" : "1", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
                break;
            case R.id.re_shajundeng:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                if (mode_status) {
                    MAlert.alert(getString(R.string.changeshodongatfirst));
                    return;
                }
                showProgressDialog(getString(R.string.posting), true);
                userPresenter.deviceSet_806(did, "", "", shajundeng_status ? "0" : "1", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
                break;
            case R.id.img_shuiweibaojing:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
//                pushStrs[4] = (shuiwei_status ? '0' : '1');
                //水位报警
                showProgressDialog(getString(R.string.posting), true);
                userPresenter.deviceSet_806(did, "", "", "", "", "", "", "", "", "", "", "", (detailModelTcp.getPush_cfg() ^ 16) + "", "", -1, -1, -1, -1);
                break;
            case R.id.img_shebeisuoding:
                if (!isConnect) {
                    MAlert.alert(getString(R.string.disconnect));
                    return;
                }
                showProgressDialog(getString(R.string.posting), true);
                //设备锁定
                userPresenter.deviceSet_806(did, "", "", "", "", "", "", "", "", "", "", "", "", dev_lockStatus ? "0" : "1", -1, -1, -1, -1);
                break;
            case R.id.img_quanping:
                if (isLan == false) {
                    setLandScape();
                } else {
                    setPortrat();
                }
                isLan = !isLan;
                break;
            case R.id.add:
//                intent = new Intent(this, ActivityStepFirst.class);
//                intent.putExtra("aq_did", did);
//                intent.putExtra("device_type", "摄像头");
//                startActivity(intent);
                break;
            case R.id.img_camera:
                imagePath = getFileSavePath() + getTimesString() + ".jpg";
                mClient.snapShot(imagePath);
                break;
        }
    }

    public void getDeviceList() {
        userPresenter.cameraQuery(getIntent().getStringExtra("did"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setPortrat();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }


    private void setPortrat() {
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//
//        this.getWindow().setFlags(WindowManager.LayoutParams.de,FEATURE_OPTIONS_PANEL);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置为竖屏
    }

    LinearLayout other_view, li_header;

    private void setLandScape() {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 设置为横屏
    }

    private void setSelect() {
        if (zhangmingdeng_status) {
            ((ImageView) findViewById(R.id.img_dengguang)).setBackgroundResource(R.drawable.light_select);
        } else {
            ((ImageView) findViewById(R.id.img_dengguang)).setBackgroundResource(R.drawable.light_unselect);
        }
        if (detailModelTcp.getEx_dev().equalsIgnoreCase("AQ500")) {
            if (shajundeng_status) {
                ((ImageView) findViewById(R.id.img_shajundeng)).setBackgroundResource(R.drawable.aq500_select2);
            } else {
                ((ImageView) findViewById(R.id.img_shajundeng)).setBackgroundResource(R.drawable.aq500_unselect2);
            }

            txt_shajundeng.setText(getString(R.string.shaju_chonglang));
            findViewById(R.id.re_shuiweibaojing).setVisibility(View.GONE);
            findViewById(R.id.re_chonglangbeng).setVisibility(View.GONE);
        } else if (detailModelTcp.getEx_dev().equalsIgnoreCase("AQ700")) {
            findViewById(R.id.re_shuiweibaojing).setVisibility(View.GONE);
        } else {
            findViewById(R.id.re_chonglangbeng).setVisibility(View.VISIBLE);
            if (shajundeng_status) {
                ((ImageView) findViewById(R.id.img_shajundeng)).setBackgroundResource(R.drawable.uv_select);
            } else {
                ((ImageView) findViewById(R.id.img_shajundeng)).setBackgroundResource(R.drawable.uv_unselect);
            }
            if (chonglangbeng_status) {
                ((ImageView) findViewById(R.id.img_chonglangbeng)).setBackgroundResource(R.drawable.chonglangbeng_select);
            } else {
                ((ImageView) findViewById(R.id.img_chonglangbeng)).setBackgroundResource(R.drawable.chonglangbeng_unselect);
            }
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

    /**
     * @param b true:当前参数页面显示   false：显示视频观看页面
     */
    private void setCanShuOrVedioData(boolean b) {
        this.b = b;
        if (b) {
            li_canshu.setVisibility(View.VISIBLE);
            li_shipin.setVisibility(View.GONE);
            btn_canshu.setBackgroundResource(R.drawable.bg_change_green);
            btn_vedio.setBackgroundResource(R.drawable.border_gray);
            btn_canshu.setTextColor(getResources().getColor(R.color.white));
            btn_vedio.setTextColor(getResources().getColor(R.color.black));
//            setViewVisible(false);
        } else {
            setViewVisible(mClient == null ? false : mClient.isConnect());
//            setViewVisible(true);
            li_canshu.setVisibility(View.GONE);
            li_shipin.setVisibility(View.VISIBLE);
            btn_vedio.setBackgroundResource(R.drawable.bg_change_green);
            btn_canshu.setBackgroundResource(R.drawable.border_gray);
            btn_vedio.setTextColor(getResources().getColor(R.color.white));
            btn_canshu.setTextColor(getResources().getColor(R.color.black));
        }
    }

    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            clientCallBackListener();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //统计流量
            initFlowPlus();
            setViewVisible(mClient.isConnect());
        }

    }

    String patten = "yyyyMMdd";

    private void initFlowPlus() {
        todayTime = new SimpleDateFormat(patten).format(new Date());
        try {
            yesTerdayTime = new SimpleDateFormat(patten).format(new SimpleDateFormat(patten).parse(Long.parseLong(todayTime) - 1 + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dbManager = new DBManager(this);
        //查询昨日使用的流量
        flow = dbManager.queryFlow(chirdDid, getSp(Const.UID), todayTime);
//        txt_shuiwei_status.setText("昨日视频累计流量" + flow / 1024 + "Kb");
    }

    TextView txt_wangsu;
    ImageView img_camera, img_quanping;
    FrameLayout mVideoLayout;
    LinearLayout li_title;

    /**
     * 没有摄像头调用
     */
    private void setViewVisible(boolean hasAddSheXiangtou) {
        txt_wangsu.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);
        img_camera.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//截屏按钮
        img_quanping.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//全屏按钮
        add.setVisibility(hasAddSheXiangtou ? View.GONE : View.VISIBLE);//添加摄像头按钮
        mVideoLayout.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//视频所在画布按钮
    }

    float ratioW2H = 1.5f;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 当新设置中，屏幕布局模式为横排时
        if (newConfig.orientation == 2) {
            BasePtr.setNone(ptr);
            ratioW2H = height / width;
            li_shipin.setmRatio(ratioW2H, (int) height, (int) width);
            int innerwidth = getWindowManager().getDefaultDisplay().getWidth() / 2;
            int innerheight = getWindowManager().getDefaultDisplay().getHeight() / 2;
//            //md 设置为全屏为什么底部会有条间隔线
//            FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            if (mStreamView != null) {
                mStreamView.getHolder().setFixedSize(innerwidth, innerheight);
            }
            other_view.setVisibility(View.GONE);
            li_header.setVisibility(View.GONE);
            img_quanping.setVisibility(View.VISIBLE);
            img_quanping.setBackgroundResource(R.drawable.xiaoping);
            isLan = true;
            li_canshu.setVisibility(View.GONE);
//            li.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            li_shipin.setVisibility(View.VISIBLE);
            li_title.setVisibility(View.GONE);
        } else if (newConfig.orientation == 1) {
            BasePtr.setRefreshOnlyStyle(ptr);
            isLan = false;
            li_title.setVisibility(View.VISIBLE);
            if (!b) {
                li_canshu.setVisibility(View.GONE);
                li_shipin.setVisibility(View.VISIBLE);
            } else {
                li_canshu.setVisibility(View.VISIBLE);
                li_shipin.setVisibility(View.GONE);
            }
            other_view.setVisibility(View.VISIBLE);
            li_header.setVisibility(View.VISIBLE);
//            li_title.setVisibility(View.VISIBLE);
            img_quanping.setVisibility(View.VISIBLE);
            li.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            img_quanping.setBackgroundResource(R.drawable.quanping);
        }
    }

    /**
     * 计算网速的方法
     */
//    private void caculateWifiSudu() {
//        try {
//            txt_wangsu.setVisibility(View.VISIBLE);
//            handlerWifi = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    if (msg.what == 1) {
//                        txt_wangsu.setText(msg.obj + "kb/s");
//                    }
//                    super.handleMessage(msg);
//                }
//            };
//            trafficBean = new TrafficBean(this, handlerWifi, 12580);
//            trafficBean.startCalculateNetSpeed();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void threadStart() {
        RequestUtil.threadStart(handler, runnable);
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
                setData();
            } else if (entity.getEventType() == UserPresenter.getdeviceinfofail) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deviceSet_806success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deviceSet_806fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update_devicename_success) {
                MAlert.alert(entity.getData());
                app.mDeviceUi.getDeviceList();
                beginRequest();
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraQuery_success) {
                //查询到了摄像头设备
                arrayList = (ArrayList<DeviceListBean>) entity.getData();
                if (arrayList.size() > 0) {
                    chirdDid = arrayList.get(0).getSlave_did();
                    pass = arrayList.get(0).getSlave_pwd();
                    if (mClient != null) {
                        mStreamView = new StreamView(this, null);
                        mVideoLayout.addView(mStreamView);
                        if (chirdDid != null) {
                            if (!chirdDid.equals("")) {
                                new MyTask().execute();
                            } else {
                                txt_shipin_status.setText(getString(R.string.current_status) + getString(R.string.video_disconnect));
                            }
                        } else {
                            txt_shipin_status.setText(getString(R.string.current_status) + getString(R.string.video_disconnect));
                        }
                    } else {
                        txt_shipin_status.setText(getString(R.string.current_status) + getString(R.string.video_disconnect));
                    }
                } else {
                    txt_shipin_status.setText(getString(R.string.current_status) + getString(R.string.video_disconnect));
                }
            } else if (entity.getEventType() == UserPresenter.cameraQuery_fail) {
                //摄像头设备查询失败
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                dbManager.deleteDeviceDataByDid(did, getSp(Const.UID));
                app.mDeviceUi.getDeviceList();
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.getDeviceOnLineState_success) {
                DeviceDetailModel detailModel = (DeviceDetailModel) entity.getData();
                isConnect = detailModel.getIs_disconnect().equals("0");
                DeviceStatusShow.setDeviceStatus(device_status, detailModel.getIs_disconnect());
            } else if (entity.getEventType() == UserPresenter.getDeviceOnLineState_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    /**
     * 2017/09/29
     * 使用两种model进行UI更新
     * deviceDetailModel 服务器保存的数据model
     * detailModelTcp    硬件保存的数据model
     */
    public void setData() {
        isConnect = deviceDetailModel.getIs_disconnect().equals("0");
        DeviceStatusShow.setDeviceStatus(device_status, deviceDetailModel.getIs_disconnect());
        //温度
        txt_wendu.setText(detailModelTcp.getT() / 10 + "℃");
        //标题
        setDeviceName(deviceDetailModel.getDevice_nickname());
        //PH
        txt_ph.setText("pH " + String.format("%.1f", detailModelTcp.getPh() / 100));
        /**
         * 灯光照明功率
         */
        txt_dengguanggonglv.setText(detailModelTcp.getL_p() + "W");
        /**
         * out_ctr
         * Bit0：灯光继电器状态
         *   0：关闭，1：打开
         *Bit1：冲浪水泵继电器状态
         **  0：关闭，1：打开
         *Bit4：杀菌灯继电器状态
         *   0：关闭，1：打开
         *Bit7：手动和自动模式状态
         *   0：手动模式，1：自动模式
         */
        zhangmingdeng_status = (((detailModelTcp.getOut_ctrl()) & (int) Math.pow(2, 0)) == Math.pow(2, 0));
        shajundeng_status = ((detailModelTcp.getOut_ctrl() & (int) Math.pow(2, 4)) == Math.pow(2, 4));
        chonglangbeng_status = ((detailModelTcp.getOut_ctrl() & (int) Math.pow(2, 1)) == Math.pow(2, 1));
        mode_status = ((detailModelTcp.getOut_ctrl() & (int) Math.pow(2, 7)) == Math.pow(2, 7));
        int fault = detailModelTcp.getFault();
        /**
         *  Bit1 - 0：水温状态
         00：正常，01：过低，10：过高

         Bit2：杀菌灯故障状态
         0：正常，1：故障

         Bit3：冲浪水泵故障状态
         0：正常，1：故障

         Bit4：照明灯故障状态
         0：正常，1：故障
         Bit5：备用电源（循环水泵）故障状态
         0：正常，1：故障
         Bit6：水位状态
         0：正常，1：过低
         Bit9 - 8：PH状态
         00：正常，01：过低，10：过高
         */
        boolean guzhang_dengguang, guzhang_shajundeng, guzhang_chonglangbeng;
        //杀菌灯状态
        if ((fault & (int) Math.pow(2, 2)) == Math.pow(2, 2)) {
            guzhang_shajundeng = true;
        } else {
            guzhang_shajundeng = false;
        }
        //灯光照明
        if ((fault & (int) Math.pow(2, 4)) == Math.pow(2, 4)) {
            guzhang_dengguang = true;
        } else {
            guzhang_dengguang = false;
        }
        //冲浪水泵
        if ((fault & (int) Math.pow(2, 3)) == Math.pow(2, 3)) {
            guzhang_chonglangbeng = true;
        } else {
            guzhang_chonglangbeng = false;
        }
        if (guzhang_dengguang) {
            txt_dengguanggonglv.setText(getString(R.string.guzhang));
        } else {
            if (zhangmingdeng_status) {
//                if (Integer.parseInt(deviceDetailModel.getL_p()) <= 0) {
//                    txt_dengguanggonglv.setText(Html.fromHtml(getString(R.string.gonglv) + deviceDetailModel.getL_p() + "W"));
//                } else {
//                    txt_dengguanggonglv.setText(Html.fromHtml(getString(R.string.gonglv) + deviceDetailModel.getL_p() + "W"));
//                }
                txt_dengguanggonglv.setText(Html.fromHtml(getString(R.string.open)));
            } else {
                txt_dengguanggonglv.setText(Html.fromHtml(getString(R.string.alClose)));
            }
        }
        if (guzhang_shajundeng) {
            txt_txt_shajundeng_status.setText(getString(R.string.guzhang));
        } else {
            if (shajundeng_status) {
//                if (Integer.parseInt(deviceDetailModel.getUvc_p()) <= 0) {
//                    txt_txt_shajundeng_status.setText(Html.fromHtml(getString(R.string.gonglv) + deviceDetailModel.getUvc_p() + "W"));
//                } else {
//                    txt_txt_shajundeng_status.setText(Html.fromHtml(getString(R.string.gonglv) + deviceDetailModel.getUvc_p() + "W"));
//                }
                txt_txt_shajundeng_status.setText(getString(R.string.open));
            } else {
                txt_txt_shajundeng_status.setText(getString(R.string.alClose));
            }
        }
        if (guzhang_chonglangbeng) {
            txt_txt_chonglangbeng_status.setText(getString(R.string.guzhang));
        } else {
            if (chonglangbeng_status) {
//                if (Integer.parseInt(deviceDetailModel.getSp_p()) <= 0) {
//                    txt_txt_chonglangbeng_status.setText(Html.fromHtml(getString(R.string.gonglv) + deviceDetailModel.getSp_p() + "W"));
//                } else {
//                    txt_txt_chonglangbeng_status.setText(Html.fromHtml(getString(R.string.gonglv) + deviceDetailModel.getSp_p() + "W"));
//                }
                txt_txt_chonglangbeng_status.setText(getString(R.string.open));
            } else {
                txt_txt_chonglangbeng_status.setText(getString(R.string.alClose));
            }
        }
        setSelect();
        setIsOpen(mode_status);
////////////////////////////////////////////////////////根据fault字段判断的正常异常/////////////////////////////////
//        if ((fault & (int) Math.pow(2, 0)) == 0 && (fault & (int) Math.pow(2, 1)) == 0) {
//            shuiwen_status_value = 0;//正常状态
//        }
//        if ((fault & (int) Math.pow(2, 0)) == 0 && (fault & (int) Math.pow(2, 1)) == 2) {
//            shuiwen_status_value = 2;//过高状态
//        }
//        if ((fault & (int) Math.pow(2, 0)) == 1 && (fault & (int) Math.pow(2, 1)) == 0) {
//            shuiwen_status_value = 1;//过低状态
//        }
//
//        if ((fault & (int) Math.pow(2, 9)) == 0 && (fault & (int) Math.pow(2, 8)) == 0) {
//            ph_status_value = 0;//正常状态
//        }
//        if ((fault & (int) Math.pow(2, 9)) == 0 && (fault & (int) Math.pow(2, 8)) == Math.pow(2, 8)) {
//            ph_status_value = 1;//过低状态
//        }
//        if ((fault & (int) Math.pow(2, 9)) == Math.pow(2, 9) && (fault & (int) Math.pow(2, 8)) == 0) {
//            ph_status_value = 2;//过高状态
//        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////直接根据值来判断正常异常////////////////////////////////////////
        int phh = 0, phl = 0, th = 0, tl = 0;
        try {
            JSONObject jsonObject = new JSONObject(deviceDetailModel.getExtra());
            if (jsonObject.has("ph_h")) {
                phh = jsonObject.getInt("ph_h");
            }
            if (jsonObject.has("ph_h")) {
                phl = jsonObject.getInt("ph_l");
            }
            if (jsonObject.has("temp_h")) {
                th = jsonObject.getInt("temp_h");
            }
            if (jsonObject.has("temp_l")) {
                tl = jsonObject.getInt("temp_l");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (th > detailModelTcp.getT() && tl < detailModelTcp.getT()) {
            shuiwen_status_value = 0;//正常状态
        } else if (th <= detailModelTcp.getT()) {
            shuiwen_status_value = 2;//过高状态
        } else if (tl >= detailModelTcp.getT()) {
            shuiwen_status_value = 1;//过低状态
        }

        if (phh > detailModelTcp.getPh() && phl < detailModelTcp.getPh()) {
            ph_status_value = 0;//正常状态
        } else if (phh <= detailModelTcp.getPh()) {
            ph_status_value = 2;//过高状态
        } else if (phl >= detailModelTcp.getPh()) {
            ph_status_value = 1;//过低状态
        }
        //酸碱度走势状态
        txt_suanjiandu_status.setText(getString(R.string.current_status));
        //
        //温度状态
        switch (shuiwen_status_value) {
            case 0:
                txt_wendustatus.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getT() != 0) ? getString(R.string.normal) : getString(R.string.video_disconnect)));
                break;
            case 1:
                txt_wendustatus.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getT() != 0) ? getString(R.string.guodi) : getString(R.string.video_disconnect)));
                break;
            case 2:
                txt_wendustatus.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getT() != 0) ? getString(R.string.guogao) : getString(R.string.video_disconnect)));
                break;
        }
        switch (ph_status_value) {
            case 0:
                txt_suanjiandu_status.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getPh() != 0) ? getString(R.string.normal) : getString(R.string.video_disconnect)));
                txt_suanjiandu_status_setting.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getPh() != 0) ? getString(R.string.normal) : getString(R.string.video_disconnect)));
                break;
            case 1:
                txt_suanjiandu_status.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getPh() != 0) ? getString(R.string.guodi) : getString(R.string.video_disconnect)));
                txt_suanjiandu_status_setting.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getPh() != 0) ? getString(R.string.guodi) : getString(R.string.video_disconnect)));
                break;
            case 2:
                txt_suanjiandu_status.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getPh() != 0) ? getString(R.string.guogao) : getString(R.string.video_disconnect)));
                txt_suanjiandu_status_setting.setText(getString(R.string.current_status) + ((isConnect && detailModelTcp.getPh() != 0) ? getString(R.string.guogao) : getString(R.string.video_disconnect)));
                break;
        }
        //水位报警设定
        /**
         * Bit0：冲浪水泵异常推送设置
         0：关闭，1：开启
         Bit1：备用电源异常推送设置
         0：关闭，1：开启
         Bit2：照明灯异常推送设置
         0：关闭，1：开启
         Bit3：杀菌灯异常推送设置
         0：关闭，1：开启
         Bit4：水位异常推送设置
         0：关闭，1：开启
         Bit5：水温异常推送设置
         0：关闭，1：开启
         */
        if ((fault & (int) Math.pow(2, 5)) == 1) {
            txt_shuiwei_status.setText(getString(R.string.current_status) + (isConnect ? getString(R.string.guodi) : getText(R.string.video_disconnect)));
        } else {
            txt_shuiwei_status.setText(getString(R.string.current_status) + (isConnect ? getString(R.string.normal) : getText(R.string.video_disconnect)));
        }
        String pushCfg = getAppointNumber(Integer.toBinaryString(detailModelTcp.getPush_cfg()), 9);
        pushStrs = pushCfg.toCharArray();
        if ((detailModelTcp.getPush_cfg() & 16) == 16) {
            shuiwei_status = true;
            img_shuiweibaojing.setBackgroundResource(R.drawable.kai);
        } else {
            shuiwei_status = false;
            img_shuiweibaojing.setBackgroundResource(R.drawable.guan);
        }

//        ///////////AQ-806 AQ-500
//        String dev = detailModelTcp.getEx_dev();
//        if (dev.equalsIgnoreCase("AQ500")) {
//            //强制关闭AQ500、AQ700的水位异常推送、冲浪泵提示
//            int push = detailModelTcp.getPush_cfg();
//            if ((push & (int) Math.pow(2, 4)) == (int) Math.pow(2, 4)) {
//                push = push ^ (int) Math.pow(2, 4);
//            }
//            if ((push & (int) Math.pow(2, 8)) == (int) Math.pow(2, 8)) {
//                push = push ^ (int) Math.pow(2, 8);
//            }
//            if (hasSyncTime == false) {
//                /**
//                 * currentTime:同时同步设备时间
//                 */
//                userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", push + "", "", -1, -1, -1, -1);
//                hasSyncTime = true;
//            } else {
//                //仅仅同步设备时间
//                if (false == isSetTime) {
//                    userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
//                    isSetTime = true;
//                }
//
//            }
//
//        } else if (dev.equalsIgnoreCase("AQ700")) {
//            int push = detailModelTcp.getPush_cfg();
//            //仅强制关闭水位报警提示
//            if ((push & (int) Math.pow(2, 4)) == (int) Math.pow(2, 4)) {
//                push = push ^ (int) Math.pow(2, 4);
//            }
//            if (hasSyncTime == false) {
//                /**
//                 * currentTime:同时同步设备时间
//                 */
//                userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", push + "", "", -1, -1, -1, -1);
//                hasSyncTime = true;
//            } else {
//                //仅仅同步设备时间
//                if (false == isSetTime) {
//                    userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
//                    isSetTime = true;
//                }
//
//            }
//        } else {
//            if (false == isSetTime) {
//                userPresenter.deviceSet_806(did, currentTime, "", "", "", "", "", "", "", "", "", "", "", "", -1, -1, -1, -1);
//                isSetTime = true;
//            }
//        }


        //设备锁定状态
        //0：未锁机，可局域网查找
        //1：锁机，局域网隐藏
        dev_lockStatus = detailModelTcp.getDev_lock() == 1;
        img_shebeisuoding.setBackgroundResource(dev_lockStatus ? R.drawable.kai : R.drawable.guan);
        //设置固件更新UI
        if (app.updateActivityUI != null) {
            if (app.updateActivityUI.smartConfigType == SmartConfigTypeSingle.UPDATE_ING) {//==3时名用户已经点击了开始更新，这里开始更新按钮进度
                app.updateActivityUI.setProgress(detailModelTcp.getUpd_state() + "");
            }
        }
        //时段设置更新UI
        if (app.mPeriodUi != null && app.mPeriodUi.yanChiFinish) {
//            app.mPeriodUi.yanChiFinish=false;
            app.mPeriodUi.setData();
        }
        setTemperature();//温度设置UI
        //806的ph详情
        if (app.deviceAq806PhActivity != null) {
            app.deviceAq806PhActivity.set806pHData();
        }
        if (app.ph806JiaoZhunUI != null) {
            app.ph806JiaoZhunUI.setJiaoZhunTimes();
        }
        //设置时段
        setNewTimer();
    }

    private void setDeviceName(String device_nickname) {
        txt_title.setText(device_nickname);
    }

    private void setIsOpen(boolean b) {
        mode_status = b;
        //0：手动，1：自动
        //false  true
        img_open.setTag(false);
        img_close.setTag(false);
        Drawable topDrawableZiDong = null;
        Drawable topDrawableShouDong = null;
        img_open.setText(getString(R.string.mode_shoudong));//手动开关
        img_close.setText(getString(R.string.mode_zidong));//自动开关
        if (b) {
            topDrawableZiDong = getResources().getDrawable(R.drawable.mode_zidong_kai);
            topDrawableShouDong = getResources().getDrawable(R.drawable.mode_shoudong_guan);
            topDrawableZiDong.setBounds(0, 0, topDrawableZiDong.getMinimumWidth(), topDrawableZiDong.getMinimumHeight());
            topDrawableShouDong.setBounds(0, 0, topDrawableShouDong.getMinimumWidth(), topDrawableShouDong.getMinimumHeight());
            img_open.setCompoundDrawables(null, topDrawableShouDong, null, null);
            img_close.setCompoundDrawables(null, topDrawableZiDong, null, null);
            img_close.setTag(true);
            txt_moshistatus.setText(getString(R.string.current_mode) + getString(R.string.auto));
        } else {
            img_open.setTag(true);
            topDrawableZiDong = getResources().getDrawable(R.drawable.mode_zidong_guan);
            topDrawableShouDong = getResources().getDrawable(R.drawable.mode_shoudong_kai);
            topDrawableZiDong.setBounds(0, 0, topDrawableZiDong.getMinimumWidth(), topDrawableZiDong.getMinimumHeight());
            topDrawableShouDong.setBounds(0, 0, topDrawableShouDong.getMinimumWidth(), topDrawableShouDong.getMinimumHeight());
            img_open.setCompoundDrawables(null, topDrawableShouDong, null, null);
            img_close.setCompoundDrawables(null, topDrawableZiDong, null, null);
            txt_moshistatus.setText(getString(R.string.current_mode) + getString(R.string.manual));
        }


    }

    public void setPeroidData() {
        if (app.mPeriodUi != null) {
            app.mPeriodUi.setCurrentItem(1);
        }
    }

    public void setTemperature() {
        if (app.deviceAq806TemperatureUI != null) {
            app.deviceAq806TemperatureUI.setDeviceData();
        }
    }

    public void setNewTimer() {
        if (app.mPeriodUi != null) {
            app.mPeriodUi.setNewTimer();
        }
    }
}
