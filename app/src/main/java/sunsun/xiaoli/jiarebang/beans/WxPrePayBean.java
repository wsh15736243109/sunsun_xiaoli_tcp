package sunsun.xiaoli.jiarebang.beans;

import com.google.gson.annotations.SerializedName;

/**
 * WxPrePayBean
 * <p>
 * Created by Mr.w on 2017/11/22.
 * <p>
 * 版本      ${version}
 * <p>
 * 修改时间
 * <p>
 * 修改内容
 */


public class WxPrePayBean {
    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageName;
    private String partnerid;
    private String prepayid;
    private long timestamp;
    private String sign;
    @SerializedName("packageValue")
    private String packageValue;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    @Override
    public String toString() {
        return "WxPrePayBean{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packageName='" + packageName + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp=" + timestamp +
                ", sign='" + sign + '\'' +
                ", packageValue='" + packageValue + '\'' +
                '}';
    }
}
