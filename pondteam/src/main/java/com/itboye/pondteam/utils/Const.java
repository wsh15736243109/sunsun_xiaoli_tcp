package com.itboye.pondteam.utils;

/**
 * 常量类
 * Created by wsh on 2016/11/28.
 */

public class Const {

    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    public static final String ALI_APPID = "";
    public static final String RSA_PRIVATE = "";
    public static final String RELOGIN = "relogin";
    public static final String IS_STORE = "is_stores";
    public static final String STORE_ID = "stores_id";
    public static String CITY_CODE = "330104";
    public static String wrapUrl = "dev.sunsunxiaoli.com";
    //    apisunsun.itboye.com/public => devsunsun.itboye.com=>
//    dev.sale.sunsunxiaoli.com
    public static String URL = "http://" + wrapUrl + "/index.php";
    public static String updaloadURL = "http://" + wrapUrl + "/index.php/file/upload";

    public Const(String wrapUrl) {
        this.wrapUrl = wrapUrl;
        this.URL = "http://" + wrapUrl + "/index.php";
    }

    //头像上传接口
//    public static final String IMAGE_URL = "https://api.ryzcgf.com/public/index.php/file/upload?client_id=by565fa4facdb191";
//
//    public static final String TN_URL_01 = "https://api.ryzcgf.com/public/index.php/upacp/appPreauth";//银联支付
    public static int time = 0;
    //单列图片查看接口
    public static final String IMAGE_HEAD = "https://api.ryzcgf.com/public/index.php/picture/index?id=";

    public static final String imgurl = "http://dev.sale.sunsunxiaoli.com/index.php/picture/index?id=";
    //注册
    public static String webRegisterUrl = "http://api.ryzcgf.com/public/web.php/registerByEmail/repairer";
    //用户协议
    public static String xieyiUrl = "https://api.ryzcgf.com/public/web.php/web/copyright";
    //帮助中心
    public static String helpUrl = "https://api.ryzcgf.com/public/web.php/web/user_manual";

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
    public static int intervalTime = 3000;//定时刷新时间
    public static int getOnlinStateIntervalTime = 10000;//定时刷新时间

    public static double lat = 30.32094, lng = 120.333082;
}
