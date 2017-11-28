package sunsun.xiaoli.jiarebang.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/11/28.
 */

public class AppConfigBean {
// "CUSTOMER_PHONE": {
//        "tel": "400-863-9156",
//                "name": "3"
//    },
//            "IOS_VERSION": "1.0.0",
//            "ANDROID_VERSION": "1000",
//            "APP_DOWNLOAD_URL": "https:\/\/apidev.itboye.com\/appdownload.php",
//            "IOS_UPDATE_LOG": "ios_v1.0.0<br\/>性能优化",
//            "ANDROID_UPDATE_LOG": "最新版本 1.0.0\r\n1. 性能优化      \r\n2. bug修复      \r\n     ",
//            "IOS_PAY_TYPE": {
//        "1": "支付宝支付",
//                "2": "微信支付",
//                "3": "余额支付"
//    },
//            "ANDROID_PAY_TYPE": {
//        "1": "支付宝支付",
//                "2": "微信支付",
//                "3": "余额支付"
//    }
    /**
     * tel : 400-863-9156
     * name : 3
     */
    @SerializedName("CUSTOMER_PHONE")
    CustomerPhone customer_phone;
    @SerializedName("IOS_VERSION")
    String ios_version;
    @SerializedName("ANDROID_VERSION")
    String androidVersion;
    @SerializedName("IOS_UPDATE_LOG")
    String ios_update_log;
    @SerializedName("ANDROID_UPDATE_LOG")
    String android_update_log;

    public CustomerPhone getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(CustomerPhone customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getIos_version() {
        return ios_version;
    }

    public void setIos_version(String ios_version) {
        this.ios_version = ios_version;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getIos_update_log() {
        return ios_update_log;
    }

    public void setIos_update_log(String ios_update_log) {
        this.ios_update_log = ios_update_log;
    }

    public String getAndroid_update_log() {
        return android_update_log;
    }

    public void setAndroid_update_log(String android_update_log) {
        this.android_update_log = android_update_log;
    }

    //    @SerializedName("IOS_PAY_TYPE")
//    String ios_pay_type;
    public static class CustomerPhone{
        private String tel;
        private String name;

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public String getName() {
            return name;
        }

    }
}
