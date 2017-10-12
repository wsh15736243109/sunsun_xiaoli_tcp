package com.itboye.pondteam.utils;

import android.annotation.SuppressLint;

import com.itboye.pondteam.R;
import com.itboye.pondteam.app.MyApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static com.itboye.pondteam.R.string.Monday;
import static com.itboye.pondteam.volley.TimesUtils.getCurrentWeek;
import static com.itboye.pondteam.volley.TimesUtils.getNumByWeek;

/**
 * Created by mr.w on 2017/3/27.
 * 计算两个日期相差的天、时、分、秒
 */

public class CaculateDays {
    /**
     * @param str1 现在时间 格式（“week,时间”）（"4,8:00"）
     * @param str2 结束时间 格式（“week,时间”）（"4,8:00"）
     * @return
     */
    public static String caculate(String str1, String str2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeBegin = df.format(System.currentTimeMillis());
        Date d1 = null;
        try {
            d1 = df.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = df.parse(timeBegin);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
        long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
        long dayss = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - dayss * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - dayss * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60) - 1;
        long seconds = (diff - dayss * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000 * 60);
        if (hours < 0) {
            hours = 0;
        }
        if (minutes < 0) {
            minutes = 0;
        }
        System.out.println(dayss + "天" + hours + "时" + minutes + "分" + seconds + "秒");
        return dayss + "," + hours + "," + minutes;
    }


    @SuppressLint("SimpleDateFormat")
    public static int getDays(String week) {
        int num = 1;
        if (!week.contains("、")) {
            num = getNumByWeek(week);
        } else {
            //找出距离今天最近的星期
            String currentWeek = getCurrentWeek(false);
            String[] weeks = week.split("、");
            boolean hasFind = false;
            for (int i = 0; i < weeks.length; i++) {
                if (currentWeek.contains(weeks[i])) {
                    hasFind = true;
                    break;
                } else {
                    hasFind = false;
                }
            }
            if (hasFind == true) {
                num = getNumByWeek(currentWeek);
            } else {
                ArrayList<Integer> ar = new ArrayList<>();
                int currentNum = getNumByWeek(currentWeek);
                for (int i = 0; i < weeks.length; i++) {
                    ar.add(getNumByWeek(weeks[i]));
                }
                int min = ar.get(0);
                int max = ar.get(0);
                for (int i = 0; i < ar.size(); i++) {
                    if (min > (ar.get(i))) {
                        min = (ar.get(i));
                    }
                    if (max < (ar.get(i))) {
                        max = ar.get(i);
                    }
                }
                if (currentNum > max) {
                    num = getNumByWeek(weeks[0]);
                }
                if (currentNum < min) {
                    num = getNumByWeek(weeks[0]);
                }
                if (currentNum > min && currentNum < max) {
                    int tempNum = currentNum;
                    int position = 0;
                    while (true) {
                        position = ar.indexOf(tempNum);
                        if (position != -1) {
                            break;
                        }
                        tempNum++;
                    }
                    num = ar.get(position);
                }
            }
        }
        System.out.println("最近的一天" + num);
        return num;
    }

    /**
     * 获取当前日期最近的一个星期几的所在的日期
     *
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String caculateNextday(String strStart, String strEnd) {
        int startIndex = getDays(strStart);
        int endIndex = getDays(strEnd);
        int num = endIndex - startIndex;
        if (num == 0) {
            // 当天
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(System.currentTimeMillis());
        } else {
            String years = new SimpleDateFormat("yyyy").format(System.currentTimeMillis());//獲取當前日期
            String month = new SimpleDateFormat("MM").format(System.currentTimeMillis());//獲取當前日期
            String days = new SimpleDateFormat("dd").format(System.currentTimeMillis());//獲取當前日期
            try {
                System.out.println("num" + num);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(years + "-" + month + "-" + (Integer.parseInt(days) + num) + " 15:15:15"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return "";
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

//  String pTime = "20120312";
    public static String getWeek(String pTime) {
        String Week = "";
        Calendar c = Calendar.getInstance();
        int currentTime = 0;
        try {
            currentTime = (c.get(Calendar.DAY_OF_WEEK));
            c.setTime(getDateFormat().parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//            Week += "日";
            Week = MyApplication.getInstance().getString(R.string.Sunday);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week = MyApplication.getInstance().getString(R.string.Monday);
//            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
//            Week += "二";
            Week = MyApplication.getInstance().getString(R.string.Tuesday);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
//            Week += "三";
            Week = MyApplication.getInstance().getString(R.string.Wednesday);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
//            Week += "四";
            Week = MyApplication.getInstance().getString(R.string.Thursday);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
//            Week += "五";
            Week = MyApplication.getInstance().getString(R.string.Friday);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
//            Week += "六";
            Week = MyApplication.getInstance().getString(R.string.Saturday);
        }
        try {
            if (IsToday(pTime)) {
                Week = MyApplication.getInstance().getString(R.string.today);
            }
            if (IsYesterday(pTime)) {
                Week = MyApplication.getInstance().getString(R.string.yesterday);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Week;
    }

    public static String judgeIsToday2(String time) {
        String week = time;
        try {
            if (IsToday2(time)) {
                week = MyApplication.getInstance().getString(R.string.today);
            } else {
                week = MyApplication.getInstance().getString(R.string.yesterday);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        try {
//            if (IsYesterday(time)) {
//                week = "昨天";
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return week;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday2(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();

        Date date = getDateFormat2().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();

        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }


    private static SimpleDateFormat getDateFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return format;
    }

    private static SimpleDateFormat getDateFormat2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return format;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date = format.parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static String formatTimesYMD2MD(String times) {
        try {
            return new SimpleDateFormat("MM/dd").format(getDateFormat().parse(times));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00/00";
    }

    public static String formatTimesYMDH2H(String times) {
        try {
            return new SimpleDateFormat("HH:mm").format(getDateFormat2().parse(times));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00/00";
    }

    public static String caculateWeek(String binaryWeek, String currentWeek) {
        int bytenum = 0;
        stringWeek = new StringBuffer();
        char[] chars = binaryWeek.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = i;
            char string = chars[i];
            if (string == '1' || (string + "").equals("1")) {
                bytenum += Math.pow(2, (chars.length - 1 - i));
                getWeek(index);
            }
        }
        System.out.println(stringWeek.toString() + "z");
        return caculateWeeks(stringWeek.toString().contains("、") ? stringWeek.toString().substring(0, stringWeek.length() - 1) : stringWeek.toString(), currentWeek);
    }

    //    ------------------------------找出最近的星期数---------------------------------//
    public static String caculateWeek(String binaryWeek) {
        int bytenum = 0;
        stringWeek = new StringBuffer();
        char[] chars = binaryWeek.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int index = i;
            char string = chars[i];
            if (string == '1' || (string + "").equals("1")) {
                bytenum += Math.pow(2, (chars.length - 1 - i));
                getWeek(index);
            }
        }
        System.out.println(stringWeek.toString() + "我是星期");
        return caculateWeeks(stringWeek.toString().contains("、") ? stringWeek.toString().substring(0, stringWeek.length() - 1) : stringWeek.toString(), "星期一");
    }

    static StringBuffer stringWeek = new StringBuffer();

    public static String getWeek(int position) {

        switch (position) {
            case 0:
                stringWeek.insert(0, "星期日、");
                break;
            case 1:
                stringWeek.insert(0, "星期六、");
                break;
            case 2:
                stringWeek.insert(0, "星期五、");
                break;
            case 3:
                stringWeek.insert(0, "星期四、");
                break;
            case 4:
                stringWeek.insert(0, "星期三、");
                break;
            case 5:
                stringWeek.insert(0, "星期二、");
                break;
            case 6:
                stringWeek.insert(0, "星期一、");
                break;
            default:
                break;
        }

        return stringWeek.toString().contains("、") ? stringWeek.toString().substring(0, stringWeek.toString().length() - 1) : stringWeek.toString();
    }


    public static String getWeekByIndex(int position) {

        switch (position) {
            case 0:
                stringWeek.insert(0, "星期日");
                break;
            case 1:
                stringWeek.insert(0, "星期六");
                break;
            case 2:
                stringWeek.insert(0, "星期五");
                break;
            case 3:
                stringWeek.insert(0, "星期四");
                break;
            case 4:
                stringWeek.insert(0, "星期三");
                break;
            case 5:
                stringWeek.insert(0, "星期二");
                break;
            case 6:
                stringWeek.insert(0, "星期一");
                break;
            default:
                break;
        }
        return stringWeek.toString().contains("、") ? stringWeek.toString().substring(0, stringWeek.toString().length() - 1) : stringWeek.toString();
    }

    private static String caculateWeeks(String weeks, String currentWeek) {
        String[] weekBinay = null;
        if (weeks.contains("、")) {
            weekBinay = weeks.split("、");
        } else {
            weekBinay = new String[]{weeks};
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("星期一", 1);
        hashMap.put("星期二", 2);
        hashMap.put("星期三", 3);
        hashMap.put("星期四", 4);
        hashMap.put("星期五", 5);
        hashMap.put("星期六", 6);
        hashMap.put("星期日", 7);
        int currentIndex = hashMap.get(currentWeek);
        ArrayList<Integer> ar = new ArrayList<>();
        for (int i = 0; i < weekBinay.length; i++) {
            String string = weekBinay[i];
            ar.add(hashMap.get(string));
        }
        int min = ar.get(0);
        int minPosition = 0;
        int max = ar.get(0);
        int maxPosition = 0;
        for (int i = 0; i < ar.size(); i++) {
            int string = Math.abs(ar.get(i));
            if (min >= string) {
                min = string;
                minPosition = i;
            }
            if (max <= string) {
                max = string;
                maxPosition = i;
            }
        }
        int num = 0;
        int startIndex = hashMap.get(weekBinay[0]);
        int endIndex = hashMap.get(weekBinay[weekBinay.length - 1]);
        if (currentIndex >= endIndex || currentIndex <= startIndex) {
            minPosition = 0;
        } else {
            int tempNum = currentIndex;
            int tempPosition = 0;
            while (true) {
                minPosition = ar.indexOf(tempNum);
                if (minPosition != -1) {
                    break;
                }
                tempNum++;
            }
        }
        num = hashMap.get(weekBinay[minPosition]) - currentIndex;
        String currentYear = new SimpleDateFormat("yyyy").format(System.currentTimeMillis());
        String currentMonth = new SimpleDateFormat("MM").format(System.currentTimeMillis());
        String currentDay = new SimpleDateFormat("dd").format(System.currentTimeMillis());
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(currentYear + "-" + currentMonth + "-" + (Integer.parseInt(currentDay) + num + "")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 计算两时间的小时差值 如2006072612
     *
     * @param endTime   2006072612
     * @param startTime 2006072612
     * @return
     */
    public static int cacaulateHours(String endTime, String startTime) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHH");
        try {
            Date d1 = df.parse(endTime);
            Date d2 = df.parse(startTime);
            //Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return Integer.parseInt((days * 24 + hours) + "");
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 计算两时间的天数差 如20060726
     *
     * @param endTime   20060726
     * @param startTime 20060726
     * @return
     */
    public static int cacaulateTotalDays(String endTime, String startTime) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d1 = df.parse(endTime);
            Date d2 = df.parse(startTime);
            //Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            return Integer.parseInt((days) + "");
        } catch (Exception e) {
        }
        return 0;
    }

    public static String caculteWeeks(int binary) {
        if (binary == -1) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if ((binary & (int) Math.pow(2, 0)) == Math.pow(2, 0)) {
            //星期一
            stringBuffer.append(MyApplication.getInstance().getString(Monday)+",");
        }
        if ((binary & (int) Math.pow(2, 1)) == Math.pow(2, 1)) {
            //星期二
            stringBuffer.append(MyApplication.getInstance().getString(R.string.Tuesday)+",");
        }
        if ((binary & (int) Math.pow(2, 2)) == Math.pow(2, 2)) {
            //星期三
            stringBuffer.append(MyApplication.getInstance().getString(R.string.Wednesday)+",");
        }
        if ((binary & (int) Math.pow(2, 3)) == Math.pow(2, 3)) {
            //星期四
            stringBuffer.append(MyApplication.getInstance().getString(R.string.Thursday)+",");
        }
        if ((binary & (int) Math.pow(2, 4)) == Math.pow(2, 4)) {
            //星期五
            stringBuffer.append(MyApplication.getInstance().getString(R.string.Friday)+",");
        }
        if ((binary & (int) Math.pow(2, 5)) == Math.pow(2, 5)) {
            //星期六
            stringBuffer.append(MyApplication.getInstance().getString(R.string.Saturday)+",");
        }
        if ((binary & (int) Math.pow(2, 6)) == Math.pow(2, 6)) {
            //星期日
            stringBuffer.append(MyApplication.getInstance().getString(R.string.Sunday)+",");
        }
        return stringBuffer.toString();
    }
}
