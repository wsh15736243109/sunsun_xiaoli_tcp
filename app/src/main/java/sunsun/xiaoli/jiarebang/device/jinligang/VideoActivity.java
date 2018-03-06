package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseTwoActivity;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.db.DBManager;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import ChirdSdk.CHD_Client;
import ChirdSdk.CHD_LocalScan;
import ChirdSdk.StreamView;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.FileBean;
import sunsun.xiaoli.jiarebang.custom.LoweRelaLayout;
import sunsun.xiaoli.jiarebang.device.ActivityStepFirst;
import sunsun.xiaoli.jiarebang.device.AddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.FeedbackActivity;
import sunsun.xiaoli.jiarebang.device.ManualAddDeviceActivity;
import sunsun.xiaoli.jiarebang.utils.DeviceType;
import sunsun.xiaoli.jiarebang.utils.loadingutil.CameraConsolePopupWindow;
import sunsun.xiaoli.jiarebang.utils.wifiutil.TrafficBean;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.NumberUtils.ByteConversionGBMBKB;
import static com.itboye.pondteam.utils.ScreenUtil.keepScreenOn;
import static sunsun.xiaoli.jiarebang.utils.FileOperateUtil.getFileSavePath;
import static sunsun.xiaoli.jiarebang.utils.FileOperateUtil.getTimesString;
import static sunsun.xiaoli.jiarebang.utils.FileOperateUtil.readfile;


/**
 * Created by Administrator on 2017/3/6.
 * 更改日志
 * 日期 : 2017/11/15
 * 封装VideoHelper类，用来辅助连接管理视频相关方法
 * 日期 : 2017/11/16
 * 内容 : 1、修改视频全屏去掉信息栏恢复竖屏后信息栏消失问题
 * {
 * 设置全屏：
 * Window window = getWindow();
 * WindowManager.LayoutParams winParams = win.getAttributes();
 * winParams.flags=winParams.flags|WindowManager.LayoutParams.FLAG_FULLSCREEN；
 * 或
 * window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
 * 或
 * window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 * 取消全屏
 * Window window = getWindow();
 * winParams.flags=winParams.flags&~WindowManager.LayoutParams.FLAG_FULLSCREEN;
 * 或
 * window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
 * 或
 * window.setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 * }
 * 2、 增加视频连接超时时重连的方法
 * 3、修复视频connectDevice==0时画布不显示bitmap（FrameLayout被隐藏）
 */

public class VideoActivity extends BaseTwoActivity implements Observer, VideoInterface {
    ImageView img_back, img_right, img_camera, img_quanping;
    LinearLayout li_title;
    TextView txt_title, add, txt_wangsu, txt_isOpen, txt_video_status;
    FrameLayout mVideoLayout;
    CameraConsolePopupWindow popupWindow;
    LinearLayout re_hasSheXiangTou, re_noSheXiangTou;
    LinearLayout li;
    LoweRelaLayout re_ratio;
    App app;
    private StreamView mStreamView;
    RelativeLayout re_fenbianlv;
    TextView txt_fenbianlv_zhuangtai;
    TextView txt_albumCount, txt_shuiwei_status;
    /**
     * 流量消息处理器
     */
//    private Handler handler;
    private TrafficBean trafficBean;
    RelativeLayout re_xiangce;
    public ArrayList<FileBean> fileList;
    private AlertDialog.Builder alert;
    private AlertDialog alertDialog;
    private boolean flag;
    String cameraDid = "", cameraPsw = "";
    private String todayTime;
    private int totalFlow = 0;
    ImageView img_liuliang, img_shuicaogang;
    private String yesTerdayTime;
    boolean isMasterDevice;
    UserPresenter userPresenter;
    DeviceListBean deviceListBean;
    String aq_did;
    TextView txt_fenbianlv_value;
    float height, width;
    HashMap<String, String> arrayList = new HashMap<>();
    ArrayList<String> arrayListValue = new ArrayList<>();
    private ListView listView;
    private int[] flow = new int[2];
    TextView txt_video;
    private String nickname;
    boolean clickBack = false;
    private VideoHelper mVideoHelper;
    WindowManager.LayoutParams winParams;
    static Intent intent = null;

    public static Intent createIntent(Context context) {
        if (intent == null) {
            intent = new Intent(context, VideoActivity.class);
        } else {
            return null;
        }
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        Window window = getWindow();
        winParams = window.getAttributes();
        app = (App) getApplication();
        app.videoUI = this;
        keepScreenOn(this, true);
        aq_did = getIntent().getStringExtra("aq_did");
        userPresenter = new UserPresenter(this);
        deviceListBean = (DeviceListBean) getIntent().getSerializableExtra("model");
        mClient = new CHD_Client();
        mVideoHelper = new VideoHelper(this, mClient, this);
        cameraDid = getIntent().getStringExtra("cameraDid");
        cameraPsw = getIntent().getStringExtra("cameraPsw");
        arrayList.put("320x180", getString(R.string.qingxi_pu));
        arrayListValue.add("(" + getString(R.string.qingxi_pu) + ")320x180");
        arrayList.put("640x360", getString(R.string.qingxi_biao));
        arrayListValue.add("(" + getString(R.string.qingxi_biao) + ")640x360");
        arrayList.put("800x480", getString(R.string.qingxi_gao));
        arrayListValue.add("(" + getString(R.string.qingxi_gao) + ")800x480");
        arrayList.put("1280x720", getString(R.string.qingxi_chao));
        arrayListValue.add("(" + getString(R.string.qingxi_chao) + ")1280x720");
        dbManager = new DBManager(this);
        txt_video.setText(deviceListBean.getSlave_name() == null ? deviceListBean.getDevice_nickname() : deviceListBean.getSlave_name());
        isMasterDevice = getIntent().getBooleanExtra("isMasterDevice", true);
        fileList = new ArrayList<>();
        setPhotoCount();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(0xffdcdddd);
        }
        txt_title.setText(txt_video.getText());
        img_right.setBackgroundResource(R.drawable.menu);
        popupWindow = new CameraConsolePopupWindow(
                this, this);
        popupWindow.setShareViewVisible(true);
        popupWindow.setShengJiVisible(false);
        if (!isMasterDevice) {
            popupWindow.setUpdateNickNameVisible(false);
            popupWindow.pick_Delete.setText(getString(R.string.unbind_device));
        }

        // 方法2
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels * dm.density;
        height = dm.heightPixels * dm.density;
        ratioW2H = height / width;
        txt_video_status.setText(getString(R.string.video_connecting));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStreamView = new StreamView(this, null);
        mVideoLayout.addView(mStreamView);

        mVideoHelper.connectDevice(cameraDid, cameraPsw);
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == UserPresenter.deleteDevice_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.deleteDevice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.cameraUnBind_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == UserPresenter.cameraUnBind_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == UserPresenter.update_devicename_success) {
                refreshDeviceList();
                MAlert.alert(entity.getData());
                txt_video.setText(nickname);
            } else if (entity.getEventType() == UserPresenter.update_devicename_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }

    private void refreshDeviceList() {
        if (app.mDeviceUi == null) {
            app.mXiaoLiUi.getDeviceList();
        } else {
            app.mDeviceUi.getDeviceList();
        }
    }


    DBManager dbManager;
    String patten = "yyyyMMdd";

    private void initFlowPlus() {
        todayTime = new SimpleDateFormat(patten).format(new Date());
        try {
            yesTerdayTime = new SimpleDateFormat(patten).format(new SimpleDateFormat(patten).parse(Long.parseLong(todayTime) - 1 + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if (mClient.isConnect()) {
        new Thread(runnable).start();
//        }
        //查询昨日使用的流量
        flow = dbManager.queryFlow(cameraDid, getSp(Const.UID), yesTerdayTime);
        if (flow == null) {
            flow = new int[1];
        }
        try {
            if (flow[1] <= 0) {
                flow[1] = 0;
            }
            txt_shuiwei_status.setText(getString(R.string.total_flow) + ByteConversionGBMBKB(flow[1] <= -1 ? 0 : flow[1]));
        } catch (Exception e) {

        }

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
            String wangsu = mClient.getVideoFrameBps();
            try {
                if (wangsu.toLowerCase().endsWith("Kb/s".toLowerCase())) {
                    totalFlow += (Double.parseDouble(wangsu.substring(0, wangsu.length() - 4)) * 1024);
                } else if (wangsu.toLowerCase().endsWith("Mb/s".toLowerCase())) {
                    totalFlow += (Double.parseDouble(wangsu.substring(0, wangsu.length() - 4)) * 1024 * 1024);
                } else if (wangsu.toLowerCase().endsWith("b/s".toLowerCase())) {
                    totalFlow += (Double.parseDouble(wangsu.substring(0, wangsu.length() - 4)));
                }
                txt_shuiwei_status.setText(getString(R.string.total_flow) + ByteConversionGBMBKB((flow[1] + totalFlow) == -1 ? 0 : (flow[1] + totalFlow)));
            } catch (Exception e) {

            }
            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        //先查询数据库里面是否已经有流量数据
        if (dbManager != null && flow != null) {
            int[] re = dbManager.queryFlow(cameraDid, getSp(Const.UID), todayTime + "");
            if (re[0] <= 0) {
                long con = dbManager.insertFlowData(cameraDid, getSp(Const.UID), todayTime, flow[1] + totalFlow + "");
//                MAlert.alert(con + "插入结果1>>>>" + totalFlow);
            } else {
                long con = dbManager.updateFlowData(cameraDid, getSp(Const.UID), todayTime, (flow[1] + totalFlow) + "");
//                MAlert.alert(con + "修改结果2>>>" + (re[1] + totalFlow) + "");
            }
        }
        try {
            if (mClient != null) {
                clickBack = true;
                mVideoHelper.closeVideo();
            }
        } catch (Exception e) {

        }
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        app.videoUI = null;
        intent = null;
        keepScreenOn(this, false);
        super.onDestroy();
    }

    public void setPhotoCount() {
        //获取相册中的图片
        try {
            fileList = readfile(getFileSavePath());
        } catch (IOException e) {
            fileList = new ArrayList<>();
            e.printStackTrace();
        }
        txt_albumCount.setText(String.format(getString(R.string.current_album), fileList.size()));
    }

    /**
     * 计算网速的方法
     */
//    private void caculateWifiSudu() {
//        try {
//            handler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    if (msg.what == 1) {
//                        txt_wangsu.setText(msg.obj + "kb/s");
//                    }
//                    super.handleMessage(msg);
//                }
//            };
//            trafficBean = new TrafficBean(this, handler, 12580);
//            trafficBean.startCalculateNetSpeed();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private CHD_Client mClient;		 /* 客户端类 */
    private CHD_LocalScan mScan;
    private Handler mTimeHandler;
    Runnable mTimeRunnable;
    private String imagePath;


    private void setCameraOpen(final boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b) {
                    txt_isOpen.setVisibility(View.GONE);
                    mVideoLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
                    txt_wangsu.setVisibility(View.VISIBLE);
                    img_camera.setVisibility(View.VISIBLE);
                    img_quanping.setVisibility(View.VISIBLE);
                    txt_shuiwei_status.setVisibility(View.VISIBLE);
                    mVideoLayout.setVisibility(View.VISIBLE);
                    txt_fenbianlv_value.setVisibility(View.VISIBLE);
                    txt_fenbianlv_zhuangtai.setVisibility(View.VISIBLE);
                } else {
                    mVideoLayout.removeAllViews();
                    txt_isOpen.setVisibility(View.VISIBLE);
                    mVideoLayout.setBackgroundColor(Color.parseColor("#000000"));
                    txt_wangsu.setVisibility(View.GONE);
                    img_camera.setVisibility(View.GONE);
                    img_quanping.setVisibility(View.GONE);
                    txt_shuiwei_status.setVisibility(View.GONE);
                    txt_fenbianlv_value.setVisibility(View.GONE);
                    txt_fenbianlv_zhuangtai.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setTextValue(final TextView textView, final String value) {
//        textView.post
        textView.post(new Runnable() {

            @Override
            public void run() {
                textView.setText(value);
            }
        });
    }

    @Override
    public void videoConnectInit() {
        setTextValue(txt_video_status, getString(R.string.video_connecting));
    }

    @Override
    public void videoConnectStatus(int result) {
        Log.v("tes", ">>>videoConnectStatus" + result);
        setTextValue(txt_video_status, mVideoHelper.getVideoStatus(result));
        if (result == 0) {
            setCameraOpen(true);
            initFlowPlus();
        } else {
            if (handler != null && runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String msg = getString(R.string.video) + txt_video_status.getText().toString();
                    mVideoHelper.showVideoMessage(VideoActivity.this, msg + "," + getString(R.string.makesure_retry));
                }
            });
        }
    }

    @Override
    public void videoStreamBitmapCallBack(@NotNull Bitmap bitmap) {
        Log.v("tes", ">>>videoStreamBitmapCallBack");
        setCameraOpen(true);
        if (mStreamView == null) {
            mStreamView = new StreamView(this, null);
        }
        mStreamView.showBitmap(bitmap);
        setTextValue(txt_wangsu, mClient.getVideoFrameBps());
        getQingXiZhuangTai();
    }

    @Override
    public void snapBitmapCallBack(@NotNull Bitmap bitmap) {
        Log.v("tes", ">>>snapBitmapCallBack");
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        setPhotoCount();
        if (flag) {
            flag = false;
//            MAlert.alert("正在保存图片");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    Intent intent = new Intent(VideoActivity.this, ImageDetailActivity.class);
                    intent.putExtra("img", imagePath);
                    startActivity(intent);
                }
            });

        } else {
            MAlert.alert(getString(R.string.caturesuccess) + imagePath, Gravity.BOTTOM);
        }
    }

    @Override
    public void disConnectCallBack() {
        setCameraOpen(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoHelper.showVideoMessage(VideoActivity.this, getString(R.string.video) + getString(R.string.current_status) + getString(R.string.video_disconnect) + "," + getString(R.string.makesure_retry));
            }
        });
    }

    @Override
    public void paramChangeCallBack(int changeType) {
//        if (changeType == ClientCallBack.PARAMCHANGE_TYPE_VIDEO_PARAME) {
        txt_fenbianlv_zhuangtai.setVisibility(View.VISIBLE);
        getQingXiZhuangTai();
//        }
    }
//
//    @SuppressLint("StaticFieldLeak")
//    class ColledctTask extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            clientCallBackListener();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            //统计流量
//            initFlowPlus();
////            setViewVisible(mClient.isConnect());
//        }
//
//
//    }

//    /**
//     * 摄像头设备相关回掉
//     */
//    private void clientCallBackListener() {
//
//        mClient.setClientCallBack(new ClientCallBack() {
//            public void paramChangeCallBack(int changeType) {
//                Log.v("tes", ">>>paramChangeCallBack");
//                switch (changeType) {
//                    case ClientCallBack.PARAMCHANGE_TYPE_VIDEO_ABILITY:
//                        //摄像头性能
////                        MAlert.alert("分辨率设置成功1");
//                        break;
//                    case ClientCallBack.PARAMCHANGE_TYPE_VIDEO_PARAME:
//                        //格式、分辨率、帧率
////                        MAlert.alert("分辨率设置成功");
//                        img_fenbianlv.setVisibility(View.GONE);
//                        txt_fenbianlv_zhuangtai.setVisibility(View.VISIBLE);
//                        txt_fenbianlv_zhuangtai.setText(mClient.Video_getResoluWidth() + "x" + mClient.Video_getResoluHeight());
//                        getQingXiZhuangTai();
//                        break;
//                    case ClientCallBack.PARAMCHANGE_TYPE_VIDEO_CTRL:
////                        MAlert.alert("分辨率设置成功3");
//                        //摄像头控制参数
//                        break;
//                    case ClientCallBack.PARAMCHANGE_TYPE_AUDIO_PARAM:
////                        MAlert.alert("分辨率设置成功4");
//                        //音频参数
//                        break;
//                    case ClientCallBack.PARAMCHANGE_TYPE_SERIAL_PARAM:
////                        MAlert.alert("分辨率设置成功5");
//                        //串口参数
//                        break;
//                    case ClientCallBack.PARAMCHANGE_TYPE_GPIO_STATUS:
////                        MAlert.alert("分辨率设置成功6");
//                        //GPIO状态
//                        break;
//                    case ClientCallBack.PARAMCHANGE_TYPE_VIDEO_ALLCTRL:
////                        MAlert.alert("分辨率设置成功7");
//                        //摄像头全部控制参数
//                        break;
//                }
//            }
//
//            public void disConnectCallBack() {
//                Log.v("tes", ">>>disConnectCallBack");
//                if (clickBack) {
//
//                } else {
//                    MAlert.alert(getString(R.string.shexiangtou) + getString(R.string.video_disconnect));
//                }
//                clickBack = false;
//                //视频连接状态
//                txt_video_status.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //视频连接状态
//                        txt_video_status.setText(mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect));
////
//                    }
//                });
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mClient.isConnect()) {
//                            setCameraOpen(true);
//                        } else {
//                            setCameraOpen(false);
//                        }
//                    }
//                });
////                mClient.connectDevice(cameraDid, cameraPsw);
////                mClient.openVideoStream();
//            }
//
//            public void snapBitmapCallBack(Bitmap bitmap) {
//                Log.v("tes", ">>>snapBitmapCallBack");
//                try {
//                    closeProgressDialog();
//                } catch (Exception e) {
//
//                }
//                setPhotoCount();
//                if (flag) {
//                    flag = false;
//                    MAlert.alert("正在保存图片");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            SystemClock.sleep(2000);
//                            Intent intent = new Intent(VideoActivity.this, ImageDetailActivity.class);
//                            intent.putExtra("img", imagePath);
//                            startActivityForTaoBao(intent);
//                        }
//                    });
//
//                } else {
//                    MAlert.alert(getString(R.string.caturesuccess) + imagePath, Gravity.BOTTOM);
//                }
//            }
//
//            public void recordTimeCountCallBack(String times) {
//                Log.v("tes", ">>>recordTimeCountCallBack");
//            }
//
//            public void recordStopBitmapCallBack(Bitmap bitmap) {
//                Log.v("tes", ">>>recordStopBitmapCallBack");
//            }
//
//            public void videoStreamBitmapCallBack(final Bitmap bitmap) {
//                Log.v("tes", ">>>videoStreamBitmapCallBack");
//                //视频连接状态
//                txt_fenbianlv_zhuangtai.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (bitmap != null) {
//                            setCameraOpen(true);
//                            txt_video_status.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
////                            txt_fenbianlv_zhuangtai.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
//                            txt_wangsu.setText(mClient.getVideoFrameBps());
//                            getQingXiZhuangTai();
//                        } else {
//                            //视频连接状态
//                            setCameraOpen(false);
//                            txt_fenbianlv_zhuangtai.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
//                        }
//                    }
//                });
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        txt_video_status.setText(getString(R.string.current_status) + (mClient.isConnect() ? getString(R.string.video_connect) : getString(R.string.video_disconnect)));
////                        txt_fenbianlv_zhuangtai.setText(mClient.Video_getResoluWidth() + "x" + mClient.Video_getResoluHeight());
////                        getQingXiZhuangTai();
////                        if (mClient.isConnect()) {
////                            img_shuicaogang.setBackgroundResource(R.drawable.kai);
////                        } else {
////                            img_shuicaogang.setBackgroundResource(R.drawable.guan);
////                        }
////                        txt_wangsu.setText(mClient.getVideoFrameBps());
////                        if (mClient.isConnect()) {
////                            setCameraOpen(true);
////                        } else {
////                            setCameraOpen(false);
////                        }
////                    }
////                });
//
//                mStreamView.showBitmap(bitmap);
//            }
//
//            public void videoStreamDataCallBack(int format, int width, int height, int datalen, byte[] data) {
//                Log.v("tes", ">>>videoStreamDataCallBack");
//            }
//
//            public void serialDataCallBack(int datalen, byte[] data) {
//                Log.v("tes", ">>>serialDataCallBack");
//            }
//
//            public void audioDataCallBack(int datalen, byte[] data) {
//                Log.v("tes", ">>>audioDataCallBack");
//            }
//        });
////        cameraDid="SCHD-001009-ZWXGR";
////        cameraPsw="PCYkQXQg";
//        int re = mClient.connectDevice(cameraDid, cameraPsw);
////        final String videoStatus = new VideoHelper().getVideoStatus(re);
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                txt_video_status.setText(videoStatus);
////                txt_fenbianlv_zhuangtai.setText(videoStatus);
////            }
////        });
//        mClient.openVideoStream();
//        Log.v("test", "did=" + cameraDid + "   re=" + re);
////        Log.v("test", "passwd=" + cameraPsw);
//
//    }


    private void getQingXiZhuangTai() {
        txt_fenbianlv_zhuangtai.setVisibility(View.VISIBLE);
        setTextValue(txt_fenbianlv_zhuangtai, getString(R.string.current_resolution) + (mClient.Video_getResoluWidth() + "x" + mClient.Video_getResoluHeight()));
        txt_fenbianlv_value.setVisibility(View.VISIBLE);
        setTextValue(txt_fenbianlv_value, arrayList.get(mClient.Video_getResoluWidth() + "x" + mClient.Video_getResoluHeight()));
    }


    float ratioW2H = 1.5f;

    /**
     * 没有摄像头调用
     */
    private void setViewVisible(boolean hasAddSheXiangtou) {
//        txt_wangsu.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);
//        img_camera.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//截屏按钮
//        img_quanping.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//全屏按钮
        add.setVisibility(hasAddSheXiangtou ? View.GONE : View.VISIBLE);//添加摄像头按钮
        mVideoLayout.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//视频所在画布按钮
        re_hasSheXiangTou.setVisibility(hasAddSheXiangtou ? View.VISIBLE : View.GONE);//视频相关数据布局
        re_noSheXiangTou.setVisibility(hasAddSheXiangtou ? View.GONE : View.VISIBLE);//没有摄像头提示添加摄像头按钮
    }

    boolean isLan = true;
    String value = null;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        flag = false;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_shuiwei_status:
                MAlert.alert(txt_shuiwei_status.getText());

                break;
            case R.id.txt_fenbianlv_zhuangtai:
//                MAlert.alert(txt_fenbianlv_zhuangtai.getText());
                break;
            case R.id.add:
                showPopwindow(8);
//                intent = new Intent(this, ActivityStepFirst.class);
//                intent.putExtra("device_type", "摄像头");
//                startActivityForTaoBao(intent);
                break;
            case R.id.img_right:
                popupWindow.showAtLocation(v, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.img_quanping:
                if (mClient.isConnect()) {
                    if (isLan == true) {
                        setLandScape();
                    } else {
                        setPortrat();
                    }
                    isLan = !isLan;
                }
                break;
//            case R.id.txt_video_status:
//                if (!txt_video_status.getText().toString().contains(getString(R.string.video_connect))) {
//
//                }
//                break;
            case R.id.re_fenbianlv:
                if (!mClient.isConnect()) {
                    MAlert.alert(getString(R.string.shexiangtou) + getString(R.string.video_disconnect));
                    return;
                }
                //选择分辨率
                showResolutionRatio();
                break;
            case R.id.re_xiangce:
                //跳转查看相册
                intent = new Intent(this, PhotoAlbumActivity.class);
                startActivity(intent);
                break;
            case R.id.img_camera:
                beginScreenCapture();
                break;
            case R.id.tvUpdate:
                //修改设备名称
                View view = LayoutInflater.from(this).inflate(R.layout.edit_view, null);
                EditText edit = (EditText) view.findViewById(R.id.editIntPart);
                showAlertDialog(getString(R.string.rename), view, 3, edit);
                popupWindow.dismiss();
                break;
            case R.id.pick_upgrade:
                //检查固件升级
                popupWindow.dismiss();
                break;
            case R.id.pick_Delete:
                //删除设备
                showAlertDialog("", null, 4, null);
                popupWindow.dismiss();
                break;
            case R.id.pick_feedback:
                //反馈
                intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
                break;
            case R.id.camera_cancel:
                //取消
                popupWindow.dismiss();
                break;
            case R.id.pick_share:
                if (!mClient.isConnect()) {
                    MAlert.alert(getString(R.string.shexiangtou) + getString(R.string.video_disconnect));
                    return;
                }
                if (!mClient.isOpenVideoStream()) {
                    MAlert.alert(getString(R.string.video_disconnect) + "...");
                    return;
                }
//                mClient.getVideoFrameFps();//获取视频流每秒传输帧数
                flag = true;
                showProgressDialog(getString(R.string.get_imging), true);
                //直接分享：
                //步骤：先截屏，待截屏成功后进行跳转分享
                beginScreenCapture();
                break;
            case R.id.img_liuliang:
                //流量清零
                img_liuliang.setEnabled(false);
                img_liuliang.setBackgroundResource(R.drawable.kai);
                long re = dbManager.updateFlowData(cameraDid, getSp(Const.UID), yesTerdayTime, "0");
                try {
                    flow[1] = 0;
                    totalFlow = 0;
                } catch (Exception e) {

                }
                MAlert.alert("视频累计流量已清零");
                txt_shuiwei_status.setText("");
//                SystemClock.sleep(2000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img_liuliang.setEnabled(true);
                        img_liuliang.setBackgroundResource(R.drawable.guan);
                    }
                }, 3000);
//
                break;
//            case R.id.img_shuicaogang:
//                if (mClient.isConnect()) {
//                    img_shuicaogang.setBackgroundResource(R.drawable.guan);
//                    mClient.disconnectDevice();
//                    txt_video_status.setText(getString(R.string.current_status) + getString(R.string.video_connect));
//                } else {
//                    img_shuicaogang.setBackgroundResource(R.drawable.kai);
//                    mStreamView = new StreamView(this, null);
//                    mVideoLayout.addView(mStreamView);
//                    mClient.connectDevice(cameraDid, cameraPsw);
//                    mClient.openVideoStream();
//                    txt_video_status.setText(getString(R.string.current_status) + getString(R.string.video_disconnect));
//                }
//                break;
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
                Intent intent = new Intent(VideoActivity.this,
                        ActivityStepFirst.class);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("device", DeviceType.DEVICE_CAMERA);
                startActivity(intent);
                finish();
            }
        });
        open_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 添加局域网设备
                popWindow.dismiss();
                Intent intent = new Intent(VideoActivity.this,
                        AddDeviceActivity.class);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("device", DeviceType.DEVICE_CAMERA);
                startActivity(intent);
                finish();
            }
        });
        pick_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 手动添加设备
                popWindow.dismiss();
                Intent intent = new Intent(VideoActivity.this,
                        ManualAddDeviceActivity.class);
                intent.putExtra("aq_did", aq_did);
                intent.putExtra("device", DeviceType.DEVICE_CAMERA);
                startActivity(intent);
                finish();
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
                        nickname = edit.getText().toString();
                        if (nickname.equals("")) {
                            MAlert.alert(getString(R.string.device_name_empty));
                            return;
                        }
                        //修改设备名称
                        userPresenter.updateDeviceName(deviceListBean.getId(), nickname, "", "", "", "", -1, -1);
                        break;
                    case 4:
                        //删除设备
                        if (isMasterDevice) {
                            userPresenter.deleteDevice(deviceListBean.getId(), getSp(Const.UID));
                        } else {
                            userPresenter.cameraUnBind(deviceListBean.getMaster_did(), deviceListBean.getSlave_did());
                        }
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

    private void beginScreenCapture() {
        imagePath = getFileSavePath() + getTimesString() + ".jpg";
        mClient.snapShot(imagePath);
    }

    private void showResolutionRatio() {
        value = null;
        alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.choose_fenbianlv));
        listView = new ListView(this);
        listView.setSelector(getResources().getDrawable(R.drawable.listview_select));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setItemChecked(position, true);
                value = arrayListValue.get(position).substring(4, arrayListValue.get(position).length());
                if (value != null) {
                    mClient.Video_setResolu(Integer.parseInt(value.split("x")[0]), Integer.parseInt(value.split("x")[1]));
                }
                alertDialog.dismiss();
//                mClient.Video_setResolu(Integer.parseInt(value.split("x")[0]), Integer.parseInt(value.split("x")[1]));
//                txt_fenbianlv_value.setText(fenBianLvValue);
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(layoutParams);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListValue));
//        alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        alert.setView(listView);
        alert.create();
        alertDialog = alert.show();
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

    /*
         * 横屏显示方法
         */
    public void setLandScape() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 设置为横屏
    }


    /**
     * 竖屏显示方法
     */
    public void setPortrat() {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置为竖屏
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 当新设置中，屏幕布局模式为横排时
        if (newConfig.orientation == 2) {
            //当横屏时
            ratioW2H = height / width;
            System.out.println("比例" + ratioW2H + "高度：" + height + "宽度：" + width);
            re_ratio.setmRatio(ratioW2H, (int) height, (int) width);
            int innerwidth = getWindowManager().getDefaultDisplay().getWidth() / 2;
            int innerheight = getWindowManager().getDefaultDisplay().getHeight() / 2;
//            //md 设置为全屏为什么底部会有条间隔线
//            FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            mStreamView.getHolder().setFixedSize(innerwidth, innerheight);
            li_title.setVisibility(View.GONE);
//            li.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            img_quanping.setBackgroundResource(R.drawable.xiaoping);
            isLan = false;
        } else if (newConfig.orientation == 1) {
            //当竖屏时
            li_title.setVisibility(View.VISIBLE);
            isLan = true;
            img_quanping.setBackgroundResource(R.drawable.quanping);
        }
    }


}
