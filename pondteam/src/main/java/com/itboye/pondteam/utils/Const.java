package com.itboye.pondteam.utils;

/**
 * 常量类
 * Created by wsh on 2016/11/28.
 */

public class Const {

    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    public static final String ALI_APPID = "2017102409486552";
    public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDrcWACw7S3+9DC\n" +
            "qdwpozo+stNt/v41pyOmOTWK0yxcXOe+UYXARQyWfRYmWG6rXP+90BddhiF8eAW0\n" +
            "mAfsZ1M2NkFTpJiZ/SQMyczFyTLB6mjtUEFKPlNk3LQw/UIP4rHc6CNzaEk9woai\n" +
            "F0SqUFooojUKeHQf10O9/v90/TA5dQwWcZa/8O9uHSEgLciOfzuD23cHxHx+PLeY\n" +
            "kyktuZDUXzUcN1tMCPR3QnMJpUp2p0a/6IoCK6KVm9cj68Fszkt9YPZI4JYSHY50\n" +
            "zFvxzUw8vyQO40gxV2A4pNpZ11rFw+j70vkc5XsAIx3+Er9E5E/kyrvyL1PjKO3T\n" +
            "NmqpIr0rAgMBAAECggEALnrhY2pTYQb7fZKe/jFVLE+NYee3M8xdQ1GD+nYaCdnl\n" +
            "fjMF3qFDDOqA1u0aXAYuDsDn+/Sg47umFLL2zXrQeKFZYDPcVHofBc9I97rypEms\n" +
            "kT0IxqlrOTxA7nLw+cXdKylA5mWuH90pSxKg4aafRepP4lNJSYfrvbR7phLd1Fmg\n" +
            "KihpNXk6qRMzQKTpJ2IveT5VdZsAL9D9hbULHRxriNAVWcZu+rvv81raqQp+xZxk\n" +
            "O+wOh7RdjOBWL7SZM49tQ/Om6C1oJMtt30eKsL3fJFddl7hnqeHStMVgK14nFYjI\n" +
            "lIcADBEVMPgWUr/3GT6zxwydyUoqpX2KqM+3/WuZsQKBgQD4ebdmB3Ewf1ZBwizk\n" +
            "zOgUtVZAWRuYGOgQGyWi+vn3CN5dEvI1DmKPAZpNoaw2QXqsBiSm5/cF+ZKCAFfI\n" +
            "IR4swOtLtc4NeP/wEvn0g85AHuQ63FMPjeZLSgWFr3BB4eBoMxynqtBjyV2x5F1q\n" +
            "IXYqrvO6TTl5/RfbOUnVToHzjwKBgQDykp/tF5XJGNiYS+pMGvXLSOyvGuxebIJ+\n" +
            "R8FTBt7uGfH7GdsJzdZOXN4z+KFZOLZ7I9Dl1nAvNEcBo2yKcpN+AGOQdZ+lUwos\n" +
            "yuaWR2axNmFsC4tJaW/2SmQwhTyqaFfi0iORnMybY04zpvq/Si0vbNbKwgw2HIQR\n" +
            "4cgo+soepQKBgHna10JGLBBoolilbXbXtolRVPdBu/KfZE9hwNdo1eV8h/CFNhsm\n" +
            "7tbLJm0j+FyzSAHNEXR5Ff/sIRl4KZSQCUhRZBNBGcgpqvqFk+eCLEft3mevXVyn\n" +
            "bmVF6+df3mGw8GipiEgB0SjevABdyiIePuE343deH+3fXS+5rKfbVIepAoGAFbCO\n" +
            "pECLoCOcmmGPXaa7e6XVpAKgSAEj2e7Ilofyvw+K140ETOge1XrwU21T4Ap2JqSZ\n" +
            "NTPrbA0cnETkm6yLC6U1QeGCmOHH8yFhZOaVhBr8sxCFL9dBr6APPVdsiUAzO5DW\n" +
            "pVqD4REMGQpBOYGEcM0ml5KqTNxX4tFxeFiUYhECgYBr9EgoUSaeGC4wPWutU41K\n" +
            "p7OVdJpS/X4vXy6eLaEKCSpmz+OS0jUmw+S9J4qEhI0/4Mlzn86i18rJQSh1OTiX\n" +
            "hKe1GcpuHv8pgLRBMNoklM4QGCfekQdiMyf4uCJR4VV17zispXSSGsvpGnW+DAk8\n" +
            "VChqvDmqBPihQgXQS+nUHA==";
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
