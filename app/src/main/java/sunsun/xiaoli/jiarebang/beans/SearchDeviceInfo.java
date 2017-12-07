package sunsun.xiaoli.jiarebang.beans;

/**
 * Created by Mr.w on 2017/4/7.
 */

public class SearchDeviceInfo {
    //{"res":"device_info","type":"s01","did":"S0200000000002","pwd":"FR7NhhHp","ver":"v1.0"}

    private String res;
    private String type;
    private String did;
    private String pwd;
    private String ver;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    @Override
    public String toString() {
        return "SearchDeviceInfo{" +
                "res='" + res + '\'' +
                ", type='" + type + '\'' +
                ", did='" + did + '\'' +
                ", pwd='" + pwd + '\'' +
                ", ver='" + ver + '\'' +
                '}';
    }
}
