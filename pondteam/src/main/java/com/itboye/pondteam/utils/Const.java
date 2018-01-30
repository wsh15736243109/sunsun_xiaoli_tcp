package com.itboye.pondteam.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.itboye.pondteam.app.MyApplication;

/**
 * 常量类
 * Created by wsh on 2016/11/28.
 */

public class Const {

    public static final String TAG_SUNSUN = "SUNSUN_XIAOLI";

    public static final String TAG_LINGSHOU = "SUNSUN_XIAOLI";
    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    public static final String ALI_APPID = "2017102409486552";
    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCxTkCQyZn8QtIP\n" +
            "rgDF+jsdG6GoZZaPHFNU1nIV+cqTyku9RjNaqzCQcvSVBapPFjIHMW4+NRdqkJ3I\n" +
            "lvdniFs9Di607VPAbFjEMIXDPafG+jIUS9eNqzSQUoTV+pcfOkQWHB/Br0ilADpK\n" +
            "Ghyo55gu9rK18K73VlgR2pvuSAex8+iVDFMSZEWw0e45xUxSurJ0dVDorRY7u8yu\n" +
            "joQzz4q5iVOi4lRIxs/U4ELnaYqeSHWaxpqc4gfUwsRrYXLTgIEOdwE8hy7uwaNp\n" +
            "g+UsJtQIWlnAQRXWc6TCmL902i2UAUvYUxMVn4/ejyrWD+xTBTzhtcs44jUiqq0O\n" +
            "PaswPQJxAgMBAAECggEATjbEyJoGMX+QhpYthSgDV63OvChEKFFIeU43+XW9ZUCB\n" +
            "9La5BLArtizfs7VSEZ0q6H1nsk1dTQcKc2gVLzi5ds+AzxM6NNIWvkThXOUf0+ie\n" +
            "mtFx/u6dO2ULcBEhBQeA7XfuklQuaYsWK4YlSmUPfvgYkaXgqP8PzqBu1Mg1jMvd\n" +
            "qghYhgSaxOTqrQ/aC4z+SLWT88s7/S5QjD72jTKfLGegqp0aVlCYpEBpJPsquMjy\n" +
            "OAME8XDwUEl7supQInM3wgY7WgaO8UCD+gD7X43i3TJuIRSoHmBpKqeSMlnR2dtd\n" +
            "ll5oJjvt/obFQatianVpM8kXezdMrfOd4tHCxoMu2QKBgQDbn9iHhlvtnKE703bN\n" +
            "F9wJA75lhBC2nQV2Lo//siZXHstDFVAFBbx7FJp0dSTwykMP2mMNAOu6e6VRsqpv\n" +
            "a8x5qH5310tyFz6pY57iekCnubiLQ4LJCjXbE5d9LC8N+NNHG4BwJsEh5aOkO6mN\n" +
            "uOsne6m0BYNHc/QbM+vjBybu+wKBgQDOrBO8HkXcsmFDCdrkpLtU5Q26jdRbb8ug\n" +
            "vGPdmtEQVaFh6UbSEvd/KMngUj7LasFGgJqNaQvpIiB6CETKlY99tW+yLfqArqQ1\n" +
            "K5dJSGOBBdmCzGoPWipqv3N7VDKWJJmBmK0syHj74S6B5/JnHzZ51wR/3WYeo9Gd\n" +
            "UzrMNrSogwKBgQCUeQZ38FQbWYUCnd21nwioq3g+xr5JxfNYRvggYz5aLT46lqDn\n" +
            "FE6sYmxciIk1o3ccs4W817+E7WwAxClGc/ji0g7VU530flRlTSyvbPhiq0JgG37C\n" +
            "DH4GIcOK4qDY8Dtc2fHFX9223bj4IUFpxvZqVcaPJh7u4ivHbB1u+HnQmwKBgEaT\n" +
            "ZLnc7KaNE8G8388sofw/rAyE2nvPFY5yRvgqIjKdy1qQOusKJxJ5b1b3PxJgBlh5\n" +
            "Gk4oDTUzvjtuD2/O2w1mpBoIsX64JMMze5o+6hvpT3nnLGDJvapubpUzD4NORE2J\n" +
            "QboFS6tYHnWmK5Ujh4rb7mFrza+w6af8ABw0okTZAoGAHsbo5eO0luV/cnuf4xZu\n" +
            "wpgZxBkF5GEhKlWlId/mUrM0k+oO34FJESogzQyqkGvGYuXIir0NmK+L6dAwBqd0\n" +
            "y6xyW5tM+NOEwr4XjO6Y9F1X0uOIrMvj8j+gDfbeO/GTJS71fCZ6MRkIpLLTRRUh\n" +
            "yCzq/pyyF1veeTNMk9byXHM=";
    public static final String RELOGIN = "relogin";
    public static final String IS_STORE = "is_stores";
    public static final String STORE_ID = "stores_id";
    public static final String SELECT_ADDRESS = "select_address";
    public static final String ADDRESS_CHANGE = "address_change";
    public static final String SHOPCART_CHANGE = "shopcart_change";
    public static final String ORDER_CHANGE = "order_change";
    public static final String STORE_CHANGE = "store_change";
    public static final String DEVICE_CHANGE = "device_change";
    public static final String USERINFO_CHANGE = "userinfo_change";
    public static final String LOCATION_LAT = "location_lat";
    public static final String LOCATION_LNG = "location_lng";
    public static final String WX_LOGIN = "wx_login";
    public static final String ADDRESS_DELEETE = "address_delete";
    public static final String YOUHUIQUAN_CHANGE = "charge_change";
    public static final String AREA_CODE = "area_code";
    public static final String TAOBAO_TEST_URL = "taobao://sensen.tmall.com/shop/view_shop.htm?spm=a220m.1000862.1000730.2.53db85dfFeR7TR&user_number_id=2090072497&rn=29c3a08e12cbced4c990c69c25037967";

    public static String CITY_CODE = "330100";
    public static String xiaoli_wrapUrl = "dev.sunsunxiaoli.com";
    public static String lingshou_wrapUrl = "dev.sale.sunsunxiaoli.com";
    //    apisunsun.itboye.com/public => devsunsun.itboye.com=>
//    dev.sale.sunsunxiaoli.com
    public static String URL = "http://" + xiaoli_wrapUrl + "/index.php";
    public static String updaloadURL = "http://" + xiaoli_wrapUrl + "/index.php/file/upload";

    public static String imagePath;
    public static String patten = "yyyyMMdd";
    public static String language = "";

    public Const() {

    }

    public Const(String xiaoli_wrapUrl) {
        this.xiaoli_wrapUrl = xiaoli_wrapUrl;
        this.URL = "http://" + xiaoli_wrapUrl + "/index.php";
        this.webUrl = "http://" + xiaoli_wrapUrl + "/web.php/web/";
        this.aboutMe = webUrl + "/about";
    }

    //头像上传接口
//    public static final String IMAGE_URL = "https://api.ryzcgf.com/public/index.php/file/upload?client_id=by565fa4facdb191";
//
//    public static final String TN_URL_01 = "https://api.ryzcgf.com/public/index.php/upacp/appPreauth";//银联支付
    public static int time = 0;
    //用户头像查看接口
    public static final String IMAGE_HEAD = "http://dev.sale.sunsunxiaoli.com/index.php/picture/avatar?uid=";

    //单图片查看接口
    public static final String imgurl = "http://dev.sale.sunsunxiaoli.com/index.php/picture/index?id=";
    //单图片查看接口
    public static final String imgSunsunUrl = "http://api.sunsunxiaoli.com/index.php/picture/index?id=";
    //注册
    public static String webRegisterUrl = "http://api.ryzcgf.com/public/web.php/registerByEmail/repairer";
    //用户协议
    public static String xieyiUrl = "https://api.ryzcgf.com/public/web.php/web/copyright";
    //帮助中心
    public static String helpUrl = "https://api.ryzcgf.com/public/web.php/web/user_manual";

    public static String webUrl = "http://" + xiaoli_wrapUrl + "/web.php/web";

    //[关于森森]
    public static String aboutMe = "http://" + lingshou_wrapUrl + "/web.php/web/about";
    //[使用条款与协议]
    public static String TIAOKUAN = "http://" + lingshou_wrapUrl + "/web.php/web/agreement";
    //[服务介绍]
    public static String SERVICE = "http://" + lingshou_wrapUrl + "/web.php/web/about";
    //[咨询购买的服务范围]
    public static String SERVICE_FANWEI = "http://" + lingshou_wrapUrl + "/web.php/web/about";
    //银联支付
    public static String zhifu = "http://api.ryzcgf.com/public/index.php/upacp/frontConsume/pay_code/";
    /**
     * --------------------------- 请求接口公用参数------------------------
     */
    public static String APP_VERSION = "app_version";
    public static String APP_TYPE = "from";
    public static String ITBOYE = "itboye";

    public static final String SELECT_CITY = "city";
    public static String TIME = "time";
    public static String SIGN = "sign";
    public static String DATA = "data";
    public static String TYPE = "type";
    public static String ALG = "alg";
    public static String NOTIFY_ID = "notify_id";
    public static String API_VER = "api_ver";
    public static String ALG_VALUE = "md5_v2";
    public static final String CLIENT_ID = "by565fa4facdb191";
    public static final String CLIENT_SECERET = "b6b27d3182d589b92424cac0f2876fcd";

    public static String SELECT_AREA = "select_area";
    public static String SELECT_AREA_CODE = "select_area_code";
    /**
     * sharedpreferences中上次登录用户名字段
     */
    public static final String UID = "id";

    public static String IMAGEPATH = "/AQ806Capture/";
    /**
     * s_id
     */
    public static final String S_ID = "s_id";
    /**
     * 登陆时 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 登陆时 密码
     */
    public static final String PASSWORD = "password";
    /**
     * 手机号
     */
    public static final String MOBILE = "mobile";
    /**
     * 电话号码前缀
     */
    public static final String COUNTRYNO = "country_no";
    /**
     * 邮箱
     */
    public static final String EMAIL = "email";
    /**
     * 性别 0女 1男
     */
    public static final String SEX = "sex";

    /**
     * 昵称
     */
    public static final String NICK = "nickname";
    /**
     * 头像
     */
    public static final String HEAD = "head";
    /**
     * 真实姓名
     */
    public static final String RealName = "realName";

    /**
     * 是否领取过优惠券
     */
    public static final String HAS_CHARGE = "has_charge";

    /**
     * 用户角色信息
     */
    public static final String RELE = "groupId";
    public static final String PaySecret = "paySecret";

    public static final String QIAN = "total";

    //更新固件状态值
    public static final String progressType = "progress";
    /**
     * 认证信息
     */
    public static final String ISATH = "is_auth";

    //高温设置
    public static final String high_temp = "high";
    //低温设置
    public static final String low_temp = "low";

    /**
     * 认证信息
     */
    public static final String ZHIFUMIMA = "pwd";


    public static final String STitle = "showmsg_title";
    public static final String SMessage = "showmsg_message";
    public static final String BAThumbData = "showmsg_thumb_data";


    /**
     * 认证信息
     */
    public static final String ISZID = "ids";
    /**
     * 精度
     */
    public static final String LAT = "lat";
    /**
     * 唯独
     */
    public static final String LNG = "lng";
    public static final String JiaoBiao = "jiaobiao";//特惠角标
    /**
     * 定位地址
     */
    public static final String Andress = "addrstr";

    /**
     * 是否已经登录
     */
    public static final String IS_LOGINED = "is_logined";
    public static final String IS_FIRST = "is_first_install";
    public static final String CITY = "city";
    public static boolean isCloseShop = false;

    public static final String APP_ID = "wx2f783978dc970b72";

    public static final String zhifubao_huidiao = "https://api.ryzcgf.com/public/index.php/Alipay/notify";
    /**
     * ------------------------- 请求接口公用参数---------------------------
     */
    public static int intervalTime = 10000;//定时刷新时间
    public static int getOnlinStateIntervalTime = 10000;//定时刷新时间


    public static int requestTimeInternal = 1000;
    public static int updateUITimeInternal = 5000;

    public static double lat = 30.32094, lng = 120.333082;

    public static float zoom = 12;//地图缩放级别

    public static String sendCodeType;

    public static String articalWeb = "http://api.sunsunxiaoli.com/web.php/productcenter/article?id=";
    public static String videoWeb = "http://api.sunsunxiaoli.com/web.php/productcenter/video?id=";

    public static void setSendCodeType(String sms) {
        sendCodeType = sms;
    }

    public static String getDeviceToken() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }
}
