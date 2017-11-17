package com.itboye.pondteam.utils.udp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.itboye.pondteam.app.MyApplication;

/**
 * VersionUtil
 * <p>
 * Created by Mr.w on 2017/11/17.
 * <p>
 * 版本      ${version}
 * <p>
 * 修改时间
 * <p>
 * 修改内容
 */


public class VersionUtil {
    /**
     * 获取App的版本号
     *
     * @return 返回App的版本号
     */
    public static String getVersionCode() {
        int appVersion = 0;
        try {

            PackageManager pm = MyApplication.getInstance()
                    .getPackageManager();
            PackageInfo info = pm.getPackageInfo(MyApplication.getInstance()
                    .getPackageName(), 0);
            appVersion = info.versionCode;
        } catch (Exception e) {

        }
        return "" + appVersion;
    }
}
