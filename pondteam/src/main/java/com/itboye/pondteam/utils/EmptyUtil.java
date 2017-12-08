package com.itboye.pondteam.utils;

import android.widget.EditText;
import android.widget.TextView;

import com.itboye.pondteam.app.MyApplication;

/**
 * Created by Mr.w on 2017/3/21.
 */

public class EmptyUtil {
    public static boolean isEmpty(Object object) {
        if (object instanceof EditText) {
            EditText editText = (EditText) object;
            if (editText.getText().toString().equals("")) {
                return true;
            } else {
                return false;
            }
        } else if (object instanceof TextView) {
            TextView textView = (TextView) object;
            if (textView.getText().toString().equals("")) {
                return true;
            } else {
                return false;
            }
        } else if (object instanceof String) {
            String str = object + "";
            if (str.equals("")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getCustomText(Object object) {
        if (object instanceof EditText) {
            EditText editText = (EditText) object;
            return editText.getText().toString();
        } else if (object instanceof TextView) {
            TextView textView = (TextView) object;
            return textView.getText().toString();
        }
        return "";
    }

    public static String getSp(Object object) {
        return SPUtils.get(MyApplication.getInstance(), null, object + "", "") + "";
    }
}
