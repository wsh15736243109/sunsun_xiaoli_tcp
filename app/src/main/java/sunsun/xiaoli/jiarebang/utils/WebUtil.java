package sunsun.xiaoli.jiarebang.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.itboye.pondteam.app.MyApplication;

import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web.WebActivity;

/**
 * Created by Administrator on 2018/1/5.
 */

public class WebUtil {
    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param packageName
     * @return
     */

    public static boolean checkPackage(String packageName)

    {

        if (packageName == null || "".equals(packageName))

            return false;

        try

        {

            MyApplication.getInstance().getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);

            return true;

        } catch (PackageManager.NameNotFoundException e)

        {

            return false;

        }

    }

    public static void startActivityForTaoBao(Activity activity, String url) {
        if ("".equals(url) || url == null) {
            return;
        }
        if (WebUtil.checkPackage("com.taobao.taobao")) {
            if (url.startsWith("https")) {
                url = url.replace("https", "taobao");
            } else {

            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent(activity, WebActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("title", "");
            activity.startActivity(intent);
        }
    }

    public static void startActivityForUrl(Activity activity, String url, String title){
        if ("".equals(url) || url == null) {
            return;
        }
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        activity.startActivity(intent);
    }
}
