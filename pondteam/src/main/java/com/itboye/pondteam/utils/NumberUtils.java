package com.itboye.pondteam.utils;

import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/3/22.
 */

public class NumberUtils {
    /**
     * 时间选择控件格式化为两位数的样式
     *
     * @param num
     * @return
     */
    public static String parse2Number(String num) {
        String str = "";
        int innerNum = Integer.parseInt(num);
        if (innerNum < 10) {
            str = "0" + num;
        }
        return str;
    }

    public static String parse2Float(String num, boolean is) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        if (is) {
            return decimalFormat.format(Float.parseFloat(num) / 10);
        } else {
            return decimalFormat.format(Float.parseFloat(num));
        }
    }

    public static String binary2Decimal(String num, int digit) {
        //2进制->10进制
        String b = num;//输入数值
        BigInteger src1 = new BigInteger(b, digit);//转换为BigInteger类型
        return src1.toString();
    }

    public static String parse2AnyFloat(String num, int point) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(Float.parseFloat(num) / point);
    }

    public static String getAppointNumber(int num, int digit) {
        String STR_FORMAT = "";
        for (int i = 0; i < digit; i++) {
            STR_FORMAT += 0;
        }
        Integer intHao = Integer.parseInt(num + "");
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(intHao);
    }

    public static String getAppointNumber(String num, int digit) {
        String STR_FORMAT = "";
        for (int i = 0; i < digit; i++) {
            STR_FORMAT += 0;
        }
        Integer intHao = Integer.parseInt(num + "");
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(intHao);
    }

    public static String parseNumberwei(int num, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(num);
    }

    static int GB = 1024 * 1024 * 1024;//定义GB的计算常量
    static int MB = 1024 * 1024;//定义MB的计算常量
    static int KB = 1024;//定义KB的计算常量

    public static String ByteConversionGBMBKB(int KSize) {
        if (KSize / GB >= 1)//如果当前Byte的值大于等于1GB
            return (String.format("%.2f", KSize / (float) GB, 2)) + "GB";//将其转换成GB
        else if (KSize / MB >= 1)//如果当前Byte的值大于等于1MB
            return (String.format("%.2f", KSize / (float) MB, 2)) + "MB";//将其转换成MB
        else if (KSize / KB >= 1)//如果当前Byte的值大于等于1KB
            return (String.format("%.2f", KSize / (float) KB, 2)) + "KB";//将其转换成KB
        else {
            if (KSize <= 0) {
                return "0b";
            }
            return String.format("%.2f", (double) KSize) + "Byte";//显示Byte值
        }
    }
}
