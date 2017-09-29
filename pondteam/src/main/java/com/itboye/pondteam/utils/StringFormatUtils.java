package com.itboye.pondteam.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/3.
 */

public class StringFormatUtils {
    public static String getFormatNum(int num) {
        String value = String.format("%1$02d", num);
        return value;
    }

    public static String getFormatNum(String num) {
        String value = String.format("%1$02d", Integer.valueOf(num));
        return value;
    }

    public static String getFormatTime(String num) {
        String value = "";
        try {
            Date mills = new SimpleDateFormat("HHmmss").parse(num);
            value = new SimpleDateFormat("HH:mm:ss").format(mills);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }
}
