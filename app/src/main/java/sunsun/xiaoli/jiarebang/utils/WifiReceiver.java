package sunsun.xiaoli.jiarebang.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.itboye.pondteam.app.MyApplication;

import sunsun.xiaoli.jiarebang.app.App;

/**
 * Created by Administrator on 2017/7/27.
 */

public class WifiReceiver extends BroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)){
            //signal strength changed
        }
        else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){//wifi连接上与否
            System.out.println("网络状态改变");
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if(info.getState().equals(NetworkInfo.State.DISCONNECTED)){
                System.out.println("wifi网络连接断开");
                if (App.getInstance().addDeviceInputWifi!=null) {
                    App.getInstance().addDeviceInputWifi.setWIFINameText("");
                }
            }
            else if(info.getState().equals(NetworkInfo.State.CONNECTED)){
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid=wifiInfo.getSSID();
                if (ssid.length() > 2) {
                    String s1 = ssid.substring(0, 1);
                    String s2 = ssid
                            .substring(ssid.length() - 2, ssid.length() - 1);
                    if (s1.compareTo("\"") == 0) {
                        if (s1.compareTo("\"") == 0) {
                            ssid = ssid.substring(1, ssid.length() - 1);
                        }
                    }
                }
                //获取当前wifi名称
                System.out.println("连接到网络 " + ssid);
                if (App.getInstance().addDeviceInputWifi!=null) {
                    App.getInstance().addDeviceInputWifi.setWIFINameText(ssid);
                }
            }

        }
        else if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){//wifi打开与否
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

            if(wifistate == WifiManager.WIFI_STATE_DISABLED){
                System.out.println("系统关闭wifi");
            }
            else if(wifistate == WifiManager.WIFI_STATE_ENABLED){
                System.out.println("系统开启wifi");
            }
        }else if(intent.getAction().equals(Intent.ACTION_LOCALE_CHANGED)||intent.getAction().equals(Intent.ACTION_TIME_CHANGED)){
            //语言切换时
            MyApplication.getInstance().updateMobile();
            Log.v("request_params","语言改变了");
        }
    }

}
