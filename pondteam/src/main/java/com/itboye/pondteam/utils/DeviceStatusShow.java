package com.itboye.pondteam.utils;

import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;

/**
 * Created by Administrator on 2017/6/19.
 */

public class DeviceStatusShow {
    public static void setDeviceStatus(TextView textView, String isConnect){
        int status=Integer.valueOf(isConnect);
        switch (status) {
            case 0:
                textView.setText(MyApplication.getInstance().getString(R.string.online));
                break;
            case 1:
                textView.setText(MyApplication.getInstance().getString(R.string.breakline));
                break;
            case 2:
                textView.setText(MyApplication.getInstance().getString(R.string.offline));
                break;
        }
    }
}
