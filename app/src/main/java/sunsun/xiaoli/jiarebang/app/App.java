package sunsun.xiaoli.jiarebang.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.CrashHandler;
import com.itboye.pondteam.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.AppConfig;
import sunsun.xiaoli.jiarebang.beans.PushModel;
import sunsun.xiaoli.jiarebang.beans.SearchDeviceInfo;
import sunsun.xiaoli.jiarebang.device.ActivityInputWifiAndPass;
import sunsun.xiaoli.jiarebang.device.ActivityStepFirst;
import sunsun.xiaoli.jiarebang.device.ActivityStepThree;
import sunsun.xiaoli.jiarebang.device.AddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.DeviceActivity;
import sunsun.xiaoli.jiarebang.device.EditDeviceActivity;
import sunsun.xiaoli.jiarebang.device.ManualAddDeviceActivity;
import sunsun.xiaoli.jiarebang.device.VersionUpdateActivity;
import sunsun.xiaoli.jiarebang.device.camera.CameraDeviceListActivity;
import sunsun.xiaoli.jiarebang.device.jiarebang.DeviceJiaReBangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.AddDeviceNewActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.DeviceAq806PhActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.DeviceAq806TemperatureActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.JinLiGangDetailActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.LoginActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.PeriodActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.Ph806JiaoZhunActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.VideoActivity;
import sunsun.xiaoli.jiarebang.device.led.LEDDetailActivity;
import sunsun.xiaoli.jiarebang.device.led.LEDPeriodSettings;
import sunsun.xiaoli.jiarebang.device.led.TiaoGuangActivity;
import sunsun.xiaoli.jiarebang.device.phdevice.DevicePHDetailActivity;
import sunsun.xiaoli.jiarebang.device.phdevice.PhJiaoZhunActivity;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityChaZuoBDetail;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityPondDeviceDetail;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityShouDong;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityUvLamp;
import sunsun.xiaoli.jiarebang.device.pondteam.ActivityZiDong;
import sunsun.xiaoli.jiarebang.device.pondteam.AddPondDevice;
import sunsun.xiaoli.jiarebang.device.qibeng.DeviceQiBengBatteryDetailActivity;
import sunsun.xiaoli.jiarebang.device.qibeng.DeviceQiBengDetailActivity;
import sunsun.xiaoli.jiarebang.device.shuibeng.DeviceShuiBengDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.OrderDetailActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.ChooseTimeActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.YuGangCleanOrHuoTiBuyStepOneActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouLoginActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.MakeSureOrderActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.HomeFragment;
import sunsun.xiaoli.jiarebang.utils.AqDeviceFind;
import sunsun.xiaoli.jiarebang.utils.LocationUtil;
import sunsun.xiaoli.jiarebang.utils.wifiutil.AqSmartConfig;

//import sunsun.xiaoli.jiarebang.device.ActivityStepThree;

//import sunsun.xiaoli.jiarebang.device.ActivityStepThree;

/**
 * Created by admin on 2016/12/6.
 */

public class App extends MyApplication implements LocationUtil.OnLocationResult {
    //    private LanguageSettingUtil languageSetting;
//    private SwitchLanguageObservable switchLangObs;
//    public List<WeakReference<Activity>> activityList = new LinkedList<>();
    public static App instance;
    public AddDeviceNewActivity addDeviceUI;
    public AddDeviceActivity mAddDeviceUi;
    public ArrayList<SearchDeviceInfo> mMyDeviceList = null;
    public DeviceActivity mDeviceUi;
    public DevicePHDetailActivity devicePhUI = null;
    public DeviceShuiBengDetailActivity deviceShuiBengUI = null;
    public DeviceJiaReBangDetailActivity deviceJiaReBangUI = null;
    public VersionUpdateActivity versionUpdateActivityUI = null;
    public ActivityStepThree addDeviceThird = null;
    //    public Database mDatabase;
    public VersionUpdateActivity updateActivityUI;
    public ManualAddDeviceActivity mManualAddDeviceUi;
    public ActivityStepFirst addDeviceFirst;
    public ActivityInputWifiAndPass addDeviceInputWifi;
    private AqDeviceFind aqDeviceFind;
    public PeriodActivity mPeriodUi;
    public JinLiGangDetailActivity jinLiGangdetailUI;
    public DeviceAq806TemperatureActivity deviceAq806TemperatureUI;
    public VideoActivity videoUI;
    public CameraDeviceListActivity mCameraDevice;
    public PhJiaoZhunActivity phJiaoZhunUI;
    public AddPondDevice addPondDeviceUI;//添加设备第一个界面
    public DeviceAq806PhActivity deviceAq806PhActivity;//806的ph详情界面
    public Ph806JiaoZhunActivity ph806JiaoZhunUI;
    public LEDDetailActivity ledDetailActivity;
    public LEDPeriodSettings ledPeriodSettingsUI;
    public TiaoGuangActivity tiaoGuangUI;
    public DeviceQiBengDetailActivity deviceQiBengUI;
    public DeviceQiBengBatteryDetailActivity deviceQiBengBatteryUI;
    public ActivityPondDeviceDetail pondDeviceDetailUI;//设备详情UI
    public ActivityZiDong ziDongUI;
    public ActivityUvLamp uvLampUI;
    public ActivityShouDong shoudongUI;
    public ActivityChaZuoBDetail chazuoBDetail;
    public EditDeviceActivity mEditDeviceUi;
    ////////////森森零售相关Activity
    public LingShouSwitchLoginOrRegisterActivity lingShouSwitchRL;
    public YuGangCleanOrHuoTiBuyStepOneActivity yuGangCleanOrHuoTiBuyStepOneActivityUI;
    public ChooseTimeActivity chooseTimeActivityUI;
    public OrderDetailActivity orderDetailUI;
    public MakeSureOrderActivity makeSureActivity;
    public LocationUtil locationUtil;
    public LingShouLoginActivity lingshouLogin;

    public List<WeakReference<Activity>> getActivityList() {
        return activityList;
    }

    public AqSmartConfig aqSmartConfig;
    private static RequestQueue queue;

    public void setActivityList(List<WeakReference<Activity>> activityList) {
        this.activityList = activityList;
    }

    public static App getInstance() {
        return instance;
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    public PushAgent mPushAgent;

    public IWXAPI iwxapi;

    public IWXAPI getIwxapi() {
        return iwxapi;
    }

    public void setIwxapi(IWXAPI iwxapi) {
        this.iwxapi = iwxapi;
    }

    Gson gson;

    public String token;
    public HomeFragment homeFragment;
    public boolean isStartSearch;
    public String name[] = null;
    public SQLiteDatabase db;// 数据库
    private final String DB_FILENAME = "itboye.db";
    private String DB_PATH;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MobclickAgent.setCatchUncaughtExceptions(true);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        Logger.init("request_params");
        SDKInitializer.initialize(getApplicationContext());
        //设置默认语言为英文
//        String sta = "en";//这是SharedPreferences工具类，用于保存设置，代码很简单，自己实现吧
//        if (BuildConfig.APP_TYPE.toLowerCase().equals("PondTeam".toLowerCase())) {
//            sta = "en";
//        }

        initDeviceTypeName();
        initLanguage();
        gson = new Gson();//在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常

        aqSmartConfig = new AqSmartConfig(this);
        mLanDeviceList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
//        mDatabase = new Database(this);
//        mMyDeviceList = mDatabase.getAllDeviceInfo();
        mMyDeviceList = new ArrayList<>();
        aqDeviceFind = new AqDeviceFind(mHandler);
        aqDeviceFind.start(AppConfig.Find_Device_Port);
//        Logger.init("down");
//        LanguageSettingUtil.init(this);// 初始化
//        languageSetting = LanguageSettingUtil.get();// 检查是否已经初始化
//        switchLangObs = new SwitchLanguageObservable();
        init(getApplicationContext());
        regToWx();
        initLocation();
        initUmeng();
//        Const mConst=new Const();
//        if (BuildConfig.APP_TYPE.equals("森森新零售")) {
//            mConst=new Const("dev.sale.sunsunxiaoli.com");
//            mConst.setSendCodeType("number");
//        }else{
//            mConst=new Const(Const.xiaoli_wrapUrl);
//            mConst.setSendCodeType("sms");
//        }
    }

    private void init(Context context) {

        try {
            DB_PATH = context.getCacheDir().getCanonicalPath() + "/address";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db = openDataBase();
//        db.close();
    }

    private SQLiteDatabase openDataBase() {
        try {
            String dbFileName = DB_PATH + "/" + DB_FILENAME;
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!new File(DB_PATH, DB_FILENAME).exists()) {
                new File(DB_PATH, DB_FILENAME).createNewFile();
                InputStream is = getResources().openRawResource(R.raw.itboye);
                FileOutputStream fos = new FileOutputStream(dbFileName);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            db = SQLiteDatabase.openOrCreateDatabase(dbFileName, null);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initDeviceTypeName() {
        name = new String[]{
                getString(R.string.device_zhineng806),
                getString(R.string.device_zhineng228),
                getString(R.string.device_zhineng700),
                getString(R.string.device_zhinengjiarebang),
                getString(R.string.device_yuancheng_ph),
                getString(R.string.device_zhinengbianpinshuibeng),
                getString(R.string.device_chitangguolv),
                BuildConfig.APP_TYPE.equals("小绵羊智能") ? getString(R.string.device_zhinengshexiangtou_yihu) : getString(R.string.device_zhinengshexiangtou),
                getString(R.string.device_shuizudeng),
                getString(R.string.device_zhinengqibeng),
                getString(R.string.device_weishiqing)};
    }

    private void initLocation() {
        locationUtil = new LocationUtil(getApplicationContext(), this);
    }


    private void regToWx() {
        iwxapi = WXAPIFactory.createWXAPI(getApplicationContext(), BuildConfig.WX_APP_ID, false);
        iwxapi.registerApp(BuildConfig.WX_APP_ID);
    }

    private void initUmeng() {
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.onAppStart();
        //巨大的坑
        /**
         * 使用 BuildTypes 修改包名的错误
         * 2017/6/10 23:57 mr.w 修复bug
         *因为友盟使用 ApplicationId 作为包名利用反射获取资源文件，而当 BuildTypes 中的 ApplicationId 改变了导致应用包名和 Java 包名不一致的时候就会导致错误。此时需要在注册推送服务之前重新设置包名：
         * 错误类型为：LogCat中有日志输出，但是通知栏并没有显示！！！
         */
        mPushAgent.setResourcePackageName(R.class.getPackage().getName());
        mPushAgent.setDebugMode(true);
//        参数number可以设置为0~10之间任意整数。当参数为0时，表示不合并通知。
//        该方法可以多次调用，以最后一次调用时的设置为准。
        mPushAgent.setDisplayNotificationNumber(0);
//		 sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
////		 sdk关闭通知声音
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
////		 通知声音由服务端控制
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {


            @Override
            public void onSuccess(String deviceToken) {
                System.out.println(">>>deviceToken成功" + deviceToken + ">>package" + getPackageName());
                token = deviceToken;
//                MAlert.alert(">>>deviceToken"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                System.out.println(">>>deviceToken--" + s + "   s1" + s1);
                token = s + "错误类型" + s1;
//                MAlert.alert(">>>deviceToken--" + s + "   s1" + s1);
            }
        });
        mPushAgent.setNotificationClickHandler(new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                super.dealWithCustomAction(context, msg);
                System.out.println(msg.getRaw() + ">>>推送数据dealWithCustomAction");

            }

            @Override
            public void openActivity(Context arg0, UMessage msg) {
                System.out.println(msg.getRaw() + ">>>推送数据openActivity");
                //找出最上层可见状态的Activity
                Activity activityTop = null;
                for (WeakReference<Activity> activity : activityList) {
                    if (activity.get() != null) {
                        if (isForeground(getApplicationContext(), activity.get().getClass().getName())) {
                            activityTop = activity.get();
                        }
                    }

                }
                PushModel model = gson.fromJson(msg.getRaw().toString(), PushModel.class);
                if (activityTop != null) {
                    showPushMessage(activityTop, model);
                } else {
//                    super.openActivity(arg0,msg);
                    boolean isLogin = (boolean) SPUtils.get(getApplicationContext(), null, Const.IS_LOGINED, false);
                    Intent intent = new Intent();
                    if (isLogin) {
                        intent.setClass(arg0, DeviceActivity.class);
                    } else {
                        intent.setClass(arg0, LoginActivity.class);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ummessage", model);
                    startActivity(intent);
                }
            }
        });

        mPushAgent.setMessageHandler(new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, final UMessage uMessage) {
//                System.out.println(uMessage.getRaw() + ">>>推送数据getNotification");
////                AlertDialog.Builder alert=new AlertDialog.Builder(context);
////                alert.setMessage(uMessage.getRaw()+"");
////                Dialog dialog=alert.create();
////                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
////                alert.show();
////                new Handler().post(new Runnable() {
////                    @Override
////                    public void run() {
////                        MAlert.alert(uMessage.getRaw());
////                    }
////                });
//                return super.getNotification(context, uMessage);
//            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                super.handleMessage(context, uMessage);
                System.out.println(uMessage.getRaw() + ">>>推送数据getNotification");
            }
        });
    }

    public void showPushMessage(Activity activity, PushModel model) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(model.getBody().getTitle());
        alert.setMessage((model.getBody().getText()));
        Dialog dialog = alert.create();
        //dialog.setCancelable(false);//无效
        alert.setCancelable(false);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

//    public LanguageSettingUtil getLanguageSetting() {
//        return languageSetting;
//    }
//
//    public SwitchLanguageObservable getSwitchLangObs() {
//        return switchLangObs;
//    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        @SuppressLint("WrongConstant") ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }

    public void addActivity(Activity activity) {
        activityList.add(new WeakReference<Activity>(activity));
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SearchDeviceInfo msgObj = (SearchDeviceInfo) msg.obj;
            // 查找到一个设备
            findNewDeviceProcess(msgObj);
            return;

        }
    };

    public ArrayList<SearchDeviceInfo> mLanDeviceList = null;
    ArrayList<String> arrayListDid = new ArrayList<>();

    private void findNewDeviceProcess(SearchDeviceInfo deviceInfo) {
        // 排除无效信息
        if (deviceInfo == null) {
            return;
        }
        if (deviceInfo.getDid() == null) {
            return;
        }
        if (deviceInfo.getDid().equals("")) {
            return;
        }
        if (deviceInfo.getPwd() == null) {
            return;
        }
        if (deviceInfo.getPwd().equals("")) {
            // 屏蔽空密码局域网设备
            return;
        }
        //排除设备类型为空
        if (deviceInfo.getType() == null) {
            return;
        }
        //排除设备类型为空
        if (deviceInfo.getType().equals("")) {
            return;
        }
        // 局域网设备列表刷新
        boolean lanIsChange = true;
        boolean lanIsNewDevice = true;
        for (int i = 0; i < mLanDeviceList.size(); i++) {
            SearchDeviceInfo d = mLanDeviceList.get(i);
            if (d.getDid().equals(deviceInfo.getDid())) {
                lanIsNewDevice = false;
                if (!d.getPwd().equals(deviceInfo.getPwd())) {
                    //设备密码有变更
                    d.setPwd(deviceInfo.getPwd());
                    arrayListDid.remove(d.getDid());
                    mLanDeviceList.remove(i);
                    break;
                }
//                continue;
            } else {
//                if(!d.getPwd().equals(deviceInfo.getPwd())){
//                    //设备密码有变更
//                    deviceInfo.setPwd(d.getPwd());
//                }
                lanIsNewDevice = true;
            }
        }
        int position = arrayListDid.indexOf(deviceInfo.getDid());
        if (addDeviceThird != null) {
//            if (lanIsNewDevice) {
            addDeviceThird.findNewDeviceInfo(deviceInfo);
//            }
        }
        if (position == -1) {
            // 添加新设备信息到局域网设备信息列表
            arrayListDid.add(deviceInfo.getDid());
            mLanDeviceList.add(deviceInfo);
        }
        if (mAddDeviceUi != null && position == -1) {
            mAddDeviceUi.refreshDeviceList();
        }
        //提示发现新的摄像头设备

    }


    // 遍历所有Activity并finish
    public void exit() {
        for (WeakReference<Activity> activity : activityList) {
            if (activity.get() != null) {
                activity.get().finish();
            }

        }
        System.exit(0);
    }

    /**
     * @return the queue
     */
    public static RequestQueue getQueue() {
        return queue;
    }

    //
    public static <T> void addRequest(Request<T> request) {
        request.addMarker("itboye");
        getQueue().add(request);
    }


    @Override
    public void getLatAndLng(String cityName, double lat, double lng) {
        try {
            homeFragment.setCityName("", cityName, lat, lng);
        } catch (Exception e) {

        }
    }
}
