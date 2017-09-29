package sunsun.xiaoli.jiarebang.alipay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化工具
 * Created by itboye on 2016/12/7.
 */

public class TimeFormat {
    static String time;

    /**
     * 获取当前时间戳
     * @return int 类型数据
     */
    public static String getStamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        return ts;
    }
    /**
     * 计算SP的时间戳与当前时间戳的差
     * @param oldTime
     *            旧的时间戳
     * @return 时间差
     */
    public static int getStampDiff(String oldTime) {
        Long tsLong = System.currentTimeMillis() / 1000;
        Long diffLong = tsLong - Long.parseLong(oldTime);
        int diffTime = Integer.parseInt(diffLong.toString());
        return diffTime;
    }

    public static String DateFormat(String s) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long ictiime = Long.valueOf(s);
            time = sdf.format(new Date(ictiime * 1000L));
        } catch (Exception e) {
            // TODO: handle exception
        }

        return time;

    }

    public static String DateFormat01(String s) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long ictiime = Long.valueOf(s);
            time = sdf.format(ictiime);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return time;

    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间
     *
     * @return
     */
//    @SuppressLint("SimpleDateFormat")
    public static String getSimData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm");
        String date = sDateFormat.format(new Date());
        return date;
    }
    public static String getOnData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "ss");
        String date = sDateFormat.format(new Date());
        return date;
    }



    /*public static String onTime(OrderDeltilteBeans beans) {
        String str=null;
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = TimeFormat.DateFormat(beans.get_auto_op_time()+"");
        java.util.Date begin = null;
        try {
            begin = dfs.parse(TimeFormat.getSimData());
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        java.util.Date end = null;
        try {
            end = dfs.parse(time);

        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("PPPPPPPPP"+end.getTime());
        long between = (end.getTime()-begin.getTime()) / 1000;// 除以1000是为了转换成秒

        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
//		long second1 = between % 60 / 60;
        if (day1 <=0) {
            str="交易已关闭" ;
        } else if(hour1<0){
            str="交易已关闭" ;
        }else if(minute1<=0){
            str="交易已关闭" ;
        }else{
            str="还剩"+day1+"天"+ +hour1+"小时"+ minute1+"分自动关闭" ;
        }
        return str;
    }*/


    public static String DateFormatS(String s) {
        DateFormat sdf = new SimpleDateFormat("ss");
        try {
            long ictiime = Long.valueOf(s);
            time = sdf.format(new Date(ictiime * 1000L));
        } catch (Exception e) {
            // TODO: handle exception
        }

        return time;

    }
}
