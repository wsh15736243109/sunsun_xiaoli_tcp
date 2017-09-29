package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */

public class AddressBean implements Serializable {

    /**
     * id : 761
     * uid : 5
     * country : 中国
     * country_id : 1
     * city : 温州
     * province : 浙江
     * detailinfo : tt村dy号
     * area : 乐青
     * contactname : 张甲
     * mobile : 11122233344
     * wxno :
     * cityid : 330300
     * provinceid : 330000
     * areaid : 330382
     * postal_code : 0
     * id_card :
     * update_time : 1501489148
     * lng : 1
     * lat : 1
     * geohash : 0
     * is_default : 1
     */
    private boolean isShow;
    private String id;
    private String uid;
    private String country;
    private String country_id;
    private String city;
    private String province;
    private String detailinfo;
    private String area;
    private String contactname;
    private String mobile;
    private String wxno;
    private String cityid;
    private String provinceid;
    private String areaid;
    private String postal_code;
    private String id_card;
    private String update_time;
    private String lng;
    private String lat;
    private String geohash;
    private String is_default;
    private boolean isSelect=false;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDetailinfo(String detailinfo) {
        this.detailinfo = detailinfo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setWxno(String wxno) {
        this.wxno = wxno;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getDetailinfo() {
        return detailinfo;
    }

    public String getArea() {
        return area;
    }

    public String getContactname() {
        return contactname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getWxno() {
        return wxno;
    }

    public String getCityid() {
        return cityid;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public String getAreaid() {
        return areaid;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getId_card() {
        return id_card;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getGeohash() {
        return geohash;
    }

    public String getIs_default() {
        return is_default;
    }
}
