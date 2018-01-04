package com.itboye.pondteam.volley;

import android.annotation.SuppressLint;

import com.itboye.pondteam.bean.WeekModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class TimesUtils {

    static HashMap<String, Integer> hashMap = new HashMap<>();
    static SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
    static SimpleDateFormat simpleWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    static SimpleDateFormat simpleYDate = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat simpleYear = new SimpleDateFormat("yyyy");
    static SimpleDateFormat simpleMonth = new SimpleDateFormat("MM");
    static SimpleDateFormat simpleDay = new SimpleDateFormat("dd");
    static SimpleDateFormat simpleHourMinute = new SimpleDateFormat("HHmm");

    public TimesUtils() {
        /**
         * 实例化失败
         */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取当前时间戳
     *
     * @return int 类型数据
     */
    public static String getStamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        return ts;
    }

    /**
     * 计算SP的时间戳与当前时间戳的差
     *
     * @param oldTime 旧的时间戳
     * @return 时间差
     */
    public static int getStampDiff(String oldTime) {
        Long tsLong = System.currentTimeMillis() / 1000;
        Long diffLong = tsLong - Long.parseLong(oldTime);
        int diffTime = Integer.parseInt(diffLong.toString());
        return diffTime;
    }

//	public static String getCurrentWeekEnglish() {
//		SimpleDateFormat date = new SimpleDateFormat("EEEE", Locale.ENGLISH);
//		return date.format(System.currentTimeMillis());
//	}

    /**
     * Long.parseLong(timeStamp)
     *
     * @param timeStamp 1451750400
     * @return 2016
     */
    public static int getYearByTimeStamp(long timeStamp) {
        String year = timeStamp2Date(timeStamp);
        return Integer.parseInt(year);
    }

    /**
     * Long.parseLong(timeStamp)
     *
     * @param timeStamp 1451750400
     * @return 01
     */
    public static int getMonthByTimeStamp(long timeStamp) {
        String date = timeStamp2Date(timeStamp);
        String month = date.substring(5, 7);
        return Integer.parseInt(month);
    }

    /**
     * Long.parseLong(timeStamp)
     *
     * @param timeStamp 1451750400
     * @return 03
     */
    public static int getDayByTimeStamp(long timeStamp) {
        String date = timeStamp2Date(timeStamp);
        String day = date.substring(8, 10);
        return Integer.parseInt(day);
    }

    /**
     * Long.parseLong(timeStamp)
     *
     * @param timeStamp 1451750400
     * @return 00
     */
    public static int getHourByTimeStamp(long timeStamp) {
        String date = timeStamp2Date(timeStamp);
        String hour = date.substring(11, 13);
        return Integer.parseInt(hour);
    }

    /**
     * Long.parseLong(timeStamp)
     *
     * @param timeStamp 1451750400
     * @return 00
     */
    public static int getMinuteByTimeStamp(long timeStamp) {
        String date = timeStamp2Date(timeStamp);
        String minute = date.substring(14, 16);
        return Integer.parseInt(minute);
    }

    /**
     * Long.parseLong(timeStamp)
     *
     * @param timeStamp 1451750400
     * @return 00
     */
    public static int getSecondByTimeStamp(long timeStamp) {
        String date = timeStamp2Date(timeStamp);
        String second = date.substring(17, 19);
        return Integer.parseInt(second);
    }

    /**
     * @param timeStamp 1451750400
     * @return 2016/01/03 00:00:00
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeStamp2Date(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * @param timeStamp 1451750400
     * @return 2016/01/03 00:00:00
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeStamp2DateNo1000(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * @param timeStamp
     * @return @return 2016/01/03
     */
    @SuppressLint("SimpleDateFormat")
    public static String timeDate(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /****
     * mm:ss
     *
     * @return
     */
    public static String getRefreshTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(new Date());
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time
     * @return
     */
    public static String getStringToDateThree(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf.format(Long.parseLong(time) * 1000);
        return date;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time
     * @return
     */
    public static String getStringTime(String time, String patten) {
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        String date = sf.format(Long.parseLong(time) * 1000);
        return date;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time
     * @return
     */
    public static long getStringToDateTwo(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getStringToDateTwo02(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getCurrentWeek(boolean isEN) {

        String mWay = "";
        if (isEN) {
            SimpleDateFormat date = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            mWay = date.format(System.currentTimeMillis());
            return mWay;
        } else {
            final Calendar c = Calendar.getInstance();
//			c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//		mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
//		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
//		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
            mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
            if ("1".equals(mWay)) {
                mWay = "日";
            } else if ("2".equals(mWay)) {
                mWay = "一";
            } else if ("3".equals(mWay)) {
                mWay = "二";
            } else if ("4".equals(mWay)) {
                mWay = "三";
            } else if ("5".equals(mWay)) {
                mWay = "四";
            } else if ("6".equals(mWay)) {
                mWay = "五";
            } else if ("7".equals(mWay)) {
                mWay = "六";
            }
        }
        return "星期" + mWay;
    }

    public static int getNumByWeek(String week) {
        // TODO Auto-generated method stub
        int num = 0;
        if (week.equals("星期一") || week.equals("Mon")) {
            num = 1;
        } else if (week.equals("星期二") || week.equals("Tue")) {
            num = 2;
        } else if (week.equals("星期三") || week.equals("Wed")) {
            num = 3;
        } else if (week.equals("星期四") || week.equals("Thurs")) {
            num = 4;
        } else if (week.equals("星期五") || week.equals("Fri")) {
            num = 5;
        } else if (week.equals("星期六") || week.equals("Sat")) {
            num = 6;
        } else if (week.equals("星期日") || week.equals("Sun")) {
            num = 7;
        }
        return num;
    }

    /**
     * UTC时间 ---> 当地时间
     *
     * @param utcTime         UTC时间
     * @param utcTimePatten   UTC时间格式
     * @param localTimePatten 当地时间格式
     * @return
     */
    public static String utc2Local(String utcTime, String utcTimePatten,
                                   String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }
    /**
     *
     * @param time         时间
     * @param inTimePatten   传入时间格式
     * @param outTimePatten 输出时间格式
     * @return
     */
    public static String parseTime(String time, String inTimePatten,
                                   String outTimePatten) {
        SimpleDateFormat inFormater = new SimpleDateFormat(inTimePatten);
        Date inDate = null;
        try {
            inDate = inFormater.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormater = new SimpleDateFormat(outTimePatten);
        String outTime = outFormater.format(inDate.getTime());
        return outTime;
    }
    // 取得本地时间：
    private static Calendar cal = Calendar.getInstance();
    // 取得时间偏移量：
    private static int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
    // 取得夏令时差：
    private static int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

    public static String localToUTC(String localTime, String localTimePattern, String utcTimePattern) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(localTimePattern);
        utcFormater.setTimeZone(TimeZone.getDefault());
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(utcTimePattern);
        localFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = localFormater.format(gpsUTCDate.getTime());
        return utcTime;
    }

    public static String jiSuanNextTime(int weekBinary, String currentWeek, String hour) {
        String weeks = getWeeksBinary(weekBinary);
        hashMap = new HashMap<>();
        hashMap.put("Monday", 1);
        hashMap.put("Tuesday", 2);
        hashMap.put("Wednesday", 3);
        hashMap.put("Thursday", 4);
        hashMap.put("Friday", 5);
        hashMap.put("Saturday", 6);
        hashMap.put("Sunday", 7);
        String str = caculateWeeks2(weeks, currentWeek, hour);
        return str;
    }

    private static String caculateWeeks2(String weeks, String currentWeek, String hour) {
        // TODO Auto-generated method stub
        boolean isChaoGuo = false;
        try {
            isChaoGuo = ((Integer.valueOf(utc2Local(simpleHourMinute.format(simpleHourMinute.parse(hour)),"HHmm","HHmm")) < Integer.valueOf(simpleHourMinute.format(System.currentTimeMillis()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String currentDate = simple.format(new Date());
        String currentYear = (simpleYear.format(new Date()));
        String currentMonth = (simpleMonth.format(new Date()));
        int currentDay = Integer.valueOf(simpleDay.format(new Date()));
        String weekBinary[] = weeks.split(",");
        ArrayList<WeekModel> weekList = new ArrayList<>();
        WeekModel model = new WeekModel();
        int currentNum = hashMap.get(currentWeek);
        model.setId(currentNum);
        model.setWeek(currentWeek);
        weekList.add(model);
        for (int i = 0; i < weekBinary.length; i++) {
            int num = hashMap.get(weekBinary[i]);
            int cha = num - currentNum;
            model = new WeekModel();
            model.setId(num);
            String str = currentYear + currentMonth + (currentDay + cha);
            System.out.println("字符串" + str);
            try {
                model.setDate(simpleYDate.format(simpleYDate.parse(str)));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            model.setWeek(weekBinary[i]);
            weekList.add(model);
        }
        Collections.sort(weekList);
        int position = 0;
        for (int i = 0; i < weekList.size(); i++) {
            if (currentWeek.contains(weekList.get(i).getWeek())) {
//                if (isChaoGuo) {
//                    position = i + 1;
//                } else {
                    position = i;
//                }
                break;
            }

        }
        String nextTime = "";
        if (position + 1 >= weekList.size() || (position + 1 >= weekList.size() && !isChaoGuo)) {
            position = -1;
            try {
                String str = "";
                while (!weekList.get(0).getWeek().equals(str)) {
                    currentDay++;
                    String leijiaShijian = currentYear + currentMonth + currentDay;
                    nextTime = simpleYDate.format(simpleYDate.parse(leijiaShijian));
                    str = simpleWeek.format(simpleYDate.parse(leijiaShijian));
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                System.out.println("错误日志" + e.getMessage());
                e.printStackTrace();
            }
        } else {
            nextTime = weekList.get(position + 1).getDate();
        }
        System.out.println("最近的星期"+"<"+nextTime+">"+ weekList.get(position + 1).getWeek() + ":" + nextTime + "isChaGuo" + isChaoGuo);
//        String times=nextTime+utc2Local(hour, "HHmm", "HHmm");
//        String times=utc2Local(nextTime+hour, "yyyyMMddHHmm", "yyyy-MM-dd HH:mm");
        String time="";
        try {
            time=parseTime(nextTime+utc2Local(simpleHourMinute.format(simpleHourMinute.parse(hour)),"HHmm","HHmm"),"yyyyMMddHHmm","yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("最近的星期<转换后>"+time);
        return time;
    }


    public static String getWeeksBinary(int weekNum) {
        StringBuffer ar = new StringBuffer();
        if ((weekNum & (int) Math.pow(2, 0)) == Math.pow(2, 0)) {
            //星期一
            ar.append("Monday,");
        }
        if ((weekNum & (int) Math.pow(2, 1)) == Math.pow(2, 1)) {
            //星期一
            ar.append("Tuesday,");
        }
        if ((weekNum & (int) Math.pow(2, 2)) == Math.pow(2, 2)) {
            //星期一
            ar.append("Wednesday,");
        }
        if ((weekNum & (int) Math.pow(2, 3)) == Math.pow(2, 3)) {
            //星期一
            ar.append("Thursday,");
        }
        if ((weekNum & (int) Math.pow(2, 4)) == Math.pow(2, 4)) {
            //星期一
            ar.append("Friday,");
        }
        if ((weekNum & (int) Math.pow(2, 5)) == Math.pow(2, 5)) {
            //星期一
            ar.append("Saturday,");
        }
        if ((weekNum & (int) Math.pow(2, 6)) == Math.pow(2, 6)) {
            //星期一
            ar.append("Sunday,");
        }
        return ar.toString().contains(",") ? ar.toString().substring(0, ar.toString().length() - 1) : ar.toString();
    }
}
