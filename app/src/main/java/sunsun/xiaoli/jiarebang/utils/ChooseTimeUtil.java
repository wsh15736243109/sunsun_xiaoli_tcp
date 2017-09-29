package sunsun.xiaoli.jiarebang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ChooseTimeUtil {
    static String pattern_mm = "mm";
    static String pattern_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    static String pattern_yyyyMMddHH = "yyyy-MM-dd HH";
    static String pattern_dd = "dd";
    static String pattern_yyyyMMdd = "yyyy-MM-dd";
    static String pattern_eee = "E";
//    static String pattern_mm="mm";
//    static String pattern_mm="mm";

    public static String[] getNearDays(String pattern, long currentTime, int nextDaysCount) {
        String timesAllCurrent = new SimpleDateFormat(pattern).format(currentTime);
        String timesYMDH = new SimpleDateFormat(pattern_yyyyMMddHH).format(currentTime);
        int current_mm = Integer.parseInt(new SimpleDateFormat(pattern_mm).format(currentTime));
        int current_dd = Integer.parseInt(new SimpleDateFormat(pattern_dd).format(currentTime));
        String timesYMD = new SimpleDateFormat(pattern_yyyyMMdd).format(currentTime);
        String week = "";
        System.out.println(timesYMD + "当前时间");
        if (current_mm <= 30) {
            current_mm = 30;
        } else {
            current_mm = 0;
        }
        String[] strings = new String[nextDaysCount];
        for (int i = 0; i < nextDaysCount; i++) {
            current_dd++;
            String afterTime = null;
            try {
                System.out.println(timesYMD + "当前时间fd" + timesYMD + current_dd);
                afterTime = new SimpleDateFormat(pattern).format(new SimpleDateFormat(pattern_yyyyMMdd).parse(timesYMD.substring(0, timesYMD.length() - 2) + current_dd));
                week = new SimpleDateFormat(pattern_eee, Locale.CHINESE).format(new SimpleDateFormat(pattern_yyyyMMdd).parse(timesYMD.substring(0, timesYMD.length() - 2) + current_dd));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (i == 0) {
                afterTime = "明天" + "\n" + afterTime;
            } else {
                afterTime = week + "\n" + afterTime;
            }
            strings[i] = afterTime;
        }
        return strings;
    }

    public static ServiceBean createPeroid() {
        ServiceBean serviceBean=new ServiceBean();
        ArrayList<ServiceBean.SkuInfoEntity> arrayList = new ArrayList<>();
        //8:00~18:00
        int i = 8;
        for (; i <= 18; i++) {
            int count = 0;
            while (count < 2) {
                ServiceBean.SkuInfoEntity testBean = new ServiceBean.SkuInfoEntity();
                testBean.setSelect(false);
                if (count  == 0) {
                    testBean.setSku_desc(i + ":00");
                } else {
                    testBean.setSku_desc(i + ":30");
                }
                arrayList.add(testBean);
                count++;
            }
        }
        serviceBean.setSku_info(arrayList);
        return serviceBean;
    }
}
