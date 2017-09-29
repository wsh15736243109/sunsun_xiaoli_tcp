package com.itboye.pondteam.app;

import android.app.Activity;
import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itboye.pondteam.bean.DeviceListBean;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2016/12/6.
 */

public class MyApplication extends Application {

    private static RequestQueue queue;
//    private LanguageSettingUtil languageSetting;
//    private SwitchLanguageObservable switchLangObs;
    public List<WeakReference<Activity>> activityList = new LinkedList<>();
    public static MyApplication instance;
//    public AddDeviceNewActivity addDeviceUI;
//    public AddDeviceActivity mAddDeviceUi;
//    public ArrayList<AqDeviceInfo> mMyDeviceList = null;
//    public DeviceActivity mDeviceUi;
//    public Database mDatabase;
//    public VersionUpdateActivity updateActivityUI;//固件更新
//    public ActivityUvLamp uvLampUI;
//    public ActivityShouDong shoudongUI;
//    public EditDeviceActivity mEditDeviceUi;
    public DeviceListBean mEditDeviceInfo;
//    public ActivityChaZuoBDetail chazuoBDetail;

    public List<WeakReference<Activity>> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<WeakReference<Activity>> activityList) {
        this.activityList = activityList;
    }


    public static MyApplication getInstance() {
        return instance;
    }

    public static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

//    AqDeviceFind aqDeviceFind;
    @Override
    public void onCreate() {
        super.onCreate();
//        mLanDeviceList = new ArrayList<AqDeviceInfo>();
        queue = Volley.newRequestQueue(this);
        instance=this;
//        TimeZone tz = TimeZone.getDefault();
//        String s =tz.getDisplayName(false, TimeZone.SHORT);
//        System.out.println("当前时区"+s);
//        s=s.substring(s.indexOf(":")-1,s.indexOf(":"));

//        mDatabase = new Database(this);
//        mMyDeviceList = mDatabase.getAllDeviceInfo();
//        aqDeviceFind=new AqDeviceFind(mHandler);
//        aqDeviceFind.start(AppConfig.Find_Device_Port);
//        Logger.init("down");
//        LanguageSettingUtil.init(this);// 初始化
//        languageSetting = LanguageSettingUtil.get();// 检查是否已经初始化
//        switchLangObs = new SwitchLanguageObservable();
    }

//    public LanguageSettingUtil getLanguageSetting() {
//        return languageSetting;
//    }
//
//    public SwitchLanguageObservable getSwitchLangObs() {
//        return switchLangObs;
//    }

    public void addActivity(Activity activity) {
        activityList.add(new WeakReference<Activity>(activity));
    }

//    public Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            System.out.println(">>>hand");
//            if (msg.what == MessageWhat.DeviceMessage) {
//                AqDeviceMessage msgObj = (AqDeviceMessage) msg.obj;
//                switch (msgObj.mMessageType) {
//                    case FindLanDevice:
//                        // 查找到一个设备
//                        findNewDeviceProcess(msgObj.mDeviceInfo);
//                        return;
//                    default:
//                        break;
//                }
//            }
////            // 以下注意顺序
////            // 交给设备设置界面
////            if (mDeviceOptionUi != null) {
////                mDeviceOptionUi.newMessage(msg);
////            }
////            // 交给设备控制界面
////            else if (mDeviceConsoleUi != null) {
////                mDeviceConsoleUi.newMessage(msg);
////            }
////            // 交给设备界面处理
////            else if (mDeviceUi != null) {
////                mDeviceUi.newMessage(msg);
////            }
//
//        }
//    };
//
//    public ArrayList<AqDeviceInfo> mLanDeviceList = null;
//    private void findNewDeviceProcess(AqDeviceInfo deviceInfo) {
//        System.out.println("找到设备------"+deviceInfo.mDeviceName);
//        // 排除无效信息
//        if (deviceInfo == null) {
//            return;
//        }
//        if (deviceInfo.mPassword.equals("")) {
//            // 屏蔽空密码局域网设备
//            return;
//        }
//        // 局域网设备列表刷新
//        boolean lanIsChange = true;
//        boolean lanIsNewDevice = true;
//        for (int i = 0; i < mLanDeviceList.size(); i++) {
//            AqDeviceInfo d = mLanDeviceList.get(i);
//            if (deviceInfo.mDid.equalsIgnoreCase(d.mDid)) {
//                // 局域网设备列表中已经存在改设备信息
//                if (deviceInfo.mPassword.equals("")) {
//                    // 屏蔽空密码局域网设备
//                    mLanDeviceList.remove(i);
//                    lanIsChange = true;
//                    break;
//                }
//                lanIsNewDevice = false;
//                if (!deviceInfo.mPassword.equals(d.mPassword)) {
//                    // 密码发生改变，更新密码
//                    d.mPassword = deviceInfo.mPassword;
//                    lanIsChange = true;
//                } else {
//                    lanIsChange = false;
//                }
//                break;
//            }
//        }
//        if (lanIsNewDevice) {
//            // 添加新设备信息到局域网设备信息列表
//            mLanDeviceList.add(deviceInfo);
//        }
//        if (lanIsChange) {
////            if (mAddDeviceUi != null) {
////                // 通知添加局域网设备列表界面，局域网设备列表已经发生改变
////                mAddDeviceUi.refreshDeviceList();
////            }
//        }
//        // 已添加设备列表刷新
////        boolean deviceIsChange = false;
////        for (int i = 0; i < mMyDeviceList.size(); i++) {
////            AqDeviceInfo d = mMyDeviceList.get(i);
////            if (deviceInfo.mDid.equalsIgnoreCase(d.mDid)) {
////                if (!deviceInfo.mPassword.equals(d.mPassword)) {
////                    // 密码发生改变，更新密码
////                    d.mPassword = deviceInfo.mPassword;
////                    deviceInfo = d;
////                    // 重新添加设备
////                    addDevice(d);
////                    // 重新存储设备
////                    deviceIsChange = true;
////                }
////                break;
////            }
////        }
////        if ((lanIsChange == true) || (lanIsNewDevice==true) || (deviceIsChange==true)) {
////            if (mSmartConfigUi != null) {
////                // 通知简易配置界面已经获得新设备信息
////                mSmartConfigUi.findNewDeviceInfo(deviceInfo);
////            }
////        }
//    }
//    /**
//     * 添加设备，并存储设备，自动删除重复
//     *
//     * @param deviceInfo 新设备信息
//     */
//    public void addDevice(AqDeviceInfo deviceInfo) {
//        if (deviceInfo != null) {
//            mDatabase.saveDeviceInfo(deviceInfo);
//        }
//        boolean isNew = true;
//        for (int i = 0; i < mMyDeviceList.size(); i++) {
//            AqDeviceInfo d = mMyDeviceList.get(i);
//            if (deviceInfo.mDid.equalsIgnoreCase(d.mDid)) {
//                d.mPassword = deviceInfo.mPassword;
//                d.mDeviceName = deviceInfo.mDeviceName;
//                isNew = false;
//            }
//        }
//        if (isNew) {
//            mMyDeviceList.add(deviceInfo);
//        }
//        if (mDeviceUi != null) {
//            // 刷新已添加设备列表
//            mDeviceUi.refreshDeviceList();
//        }
//    }


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
}
