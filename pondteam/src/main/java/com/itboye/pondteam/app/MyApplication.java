package com.itboye.pondteam.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.volley.ResultEntity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static com.itboye.pondteam.utils.MyTimeUtil.getTimeZone;
import static com.itboye.pondteam.utils.ScreenUtil.getIMEI;

/**
 * Created by admin on 2016/12/6.
 */

public class MyApplication extends MultiDexApplication implements Observer {

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
    UserPresenter userPresenter;

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
        instance = this;
        userPresenter = new UserPresenter(this);

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

    public void initLanguage() {
//        Locale.TAIWAN(臺灣繁體)
//        Locale.setDefault(BuildConfig.IS_CHINESE ? Locale.CHINESE : Locale.ENGLISH);
//        if (BuildConfig.APP_TYPE.t.equals("PondTeam".toLowerCase())) {
//
//        }
//        Configuration config = getBaseContext().getResources()
//                .getConfiguration();
//        Locale curLocale = getResources().getConfiguration().locale;
//        config.locale = curLocale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());

        // 本地语言设置
//        Locale myLocale = new Locale(sta);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Const.language = getLanguage();
        //设置成简体中文的时候，getLanguage()返回的是zh,getCountry()返回的是cn.
//        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        updateMobile();
    }

    public void updateMobile() {
        String timezone = getTimeZone();
        String lang = MyApplication.getInstance().getLanguage();
        String device_id = getIMEI(getApplicationContext());
        userPresenter.updateMobileMsg(getSp(Const.UID), device_id, lang, timezone);
    }

    public String getLanguage() {
        //获取系统当前使用的语言
        String lan = Locale.getDefault().getLanguage();
        //获取区域
        String country = Locale.getDefault().getCountry();
        if (lan.startsWith("zh")) {
            return lan + "-" + country;
        } else if (lan.equals("en")) {
            return lan;
        } else {
            return "en";
        }
    }

    public void addActivity(Activity activity) {
        activityList.add(new WeakReference<Activity>(activity));
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
    public void update(Observable o, Object data) {
        ResultEntity result = (ResultEntity) data;
        if (result!=null) {
            if (result.getCode()!=0) {
//                MAlert.alert(result.getMsg()+"");
                return;
            }
            if (result.getEventType()== UserPresenter.updateMobileMsg_success) {
//                MAlert.alert(result.getData());
            }else if (result.getEventType()== UserPresenter.updateMobileMsg_fail) {
//                MAlert.alert(result.getData());
            }
        }
    }
}
