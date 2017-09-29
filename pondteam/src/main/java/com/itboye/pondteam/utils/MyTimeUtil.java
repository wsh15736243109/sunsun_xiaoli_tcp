package com.itboye.pondteam.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MyTimeUtil {
    public static String parseTime1(String time) {
        long innerTime = Long.parseLong(time) * 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd,HH:mm");
        return simpleDateFormat.format(innerTime);
    }
    public static String getCurrentTime(String time,String pattern) {
        long innerTime = Long.parseLong(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(innerTime);
    }
    public static String parseTime2(String time) {
        long innerTime = Long.parseLong(time) * 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(innerTime);
    }

    public static String parseTime3(String time) {
        long innerTime = Long.parseLong(time) * 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(innerTime);
    }

    public static String getWeek(long time){
        return new SimpleDateFormat("HH:mm:ss:EEEE").format(time);
    }


}
