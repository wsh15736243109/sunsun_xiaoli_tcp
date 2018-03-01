package sunsun.xiaoli.jiarebang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.DeviceActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.AddDeviceNewActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.LoginActivity;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.logincontroller.LoginedState;
import sunsun.xiaoli.jiarebang.logincontroller.UnLoginState;
import sunsun.xiaoli.jiarebang.shuizuzhijia.AquariumHomeMainActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.LingShouMainActivity;
import sunsun.xiaoli.jiarebang.utils.SpContants;

/**
 * 日志名  格式   android_用户ID_yyyy-MM-dd
 *
 * @author young
 */
public class SplashActivity extends LingShouBaseActivity {

    Handler handler;
    boolean hasLogined = false;
    ImageView img_splash;
    App mApp;
    private boolean isFirstInstall;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        mApp = (App) getApplication();
//        getAppDetailSettingIntent(this);
        if (SpContants.APP_TYPE.equals("小鲤智能")) {
            img_splash.setBackgroundResource(R.mipmap.splash);
        } else if (SpContants.APP_TYPE.equals("小绵羊智能")) {
            img_splash.setBackgroundResource(R.drawable.splash_leihu);
        } else if (SpContants.APP_TYPE.equals("pondTeam")) {
            img_splash.setBackgroundResource(R.drawable.splash_pondteam);
        } else if (SpContants.APP_TYPE.equals("森森新零售") || SpContants.APP_TYPE.equals("水族之家")) {
//            setStatusBarColor(getResources().getColor(R.color.main_lingshou_orange));
            img_splash.setBackgroundResource(R.drawable.splash_lingshou);
        }
        System.out.println(SpContants.APP_TYPE + "getPackageName()" + getPackageName());
        if (getPackageName().contains("pondlink")) {
            img_splash.setBackgroundResource(R.drawable.pondlink_splash);
        }
//        setStatusBarColor(R.color.gray_eee);
        try {
            isFirstInstall = (boolean) SPUtils.get(this, null, Const.IS_FIRST, true);
            hasLogined = (boolean) SPUtils.get(this, null, Const.IS_LOGINED, false);
        } catch (Exception e) {

        }
    }

//    private void sendNotification() {
//        Notification notification = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.device_002)//设置小图标
//                .setContentTitle("这是标题")
//                .setContentText("这是内容")
//                .build();
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notification);
//    }

    //    以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Build.VERSION.SDK_INT >= 9) {
//            localIntent.setAction("com.android.settings.SubSettings");
//            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
//        } else if (Build.VERSION.SDK_INT <= 8) {
        localIntent.setAction(Intent.ACTION_VIEW);
        localIntent.setClassName("com.android.settings", "com.android.settings.SubSettings");
        localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
//        }
        startActivity(localIntent);
    }

    private String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String times = format.format(new Date(milliseconds));

        return times;
    }

    private void startTimer() {
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (BuildConfig.APP_TYPE.equals("小鲤智能测试版")) {
                    startActivity(new Intent(getApplicationContext(), AddDeviceNewActivity.class));
                } else {
                    if (hasLogined) {
                        LoginController.setLoginState(new LoginedState());
                        if (SpContants.APP_TYPE.equals("森森新零售")) {
                            startActivity(new Intent(getApplicationContext(), LingShouMainActivity.class));
                        } else if (SpContants.APP_TYPE.equals("水族之家")) {
                            startActivity(new Intent(getApplicationContext(), AquariumHomeMainActivity.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), DeviceActivity.class));
                        }
                    } else {
                        LoginController.setLoginState(new UnLoginState());
                        if (SpContants.APP_TYPE.equals("森森新零售")) {
                            startActivity(new Intent(getApplicationContext(), LingShouMainActivity.class));
                        } else if (SpContants.APP_TYPE.equals("水族之家")) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    }
                }
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(123, 2500);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        String appType = getPackageName();
        if (isFirstInstall && appType.toLowerCase().contains("pondteam".toLowerCase())) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(com.itboye.pondteam.R.string.wenxin));
            alert.setMessage(getString(com.itboye.pondteam.R.string.xieyi));
            alert.setPositiveButton(getString(com.itboye.pondteam.R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtils.put(SplashActivity.this, null, Const.IS_FIRST, false);
                    startTimer();
                }
            });
            alert.setNegativeButton(getString(com.itboye.pondteam.R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtils.put(SplashActivity.this, null, Const.IS_FIRST, true);
                    finish();
                }
            });

            alert.create();
            alert.show();
        } else {
            startTimer();
        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (handler != null) {
            handler.removeMessages(123);
        }
        finish();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
//		overridePendingTransition(0, R.anim.anim_falldown);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }
}
