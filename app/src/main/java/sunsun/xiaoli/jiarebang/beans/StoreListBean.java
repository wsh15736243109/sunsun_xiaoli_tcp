package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class StoreListBean implements Serializable{

    /**
     * count : 3
     * list : [{"id":"1","name":"杭州大鱼","uid":"1","grade":"0","create_time":"1499916729","update_time":"1500617852","province":"330000","city":"330100","area":"330104","mobile":"18358587556","logo":"2","lng":"120.359904","lat":"30.302337","phone":"58587544","address":"江干区学林街515","contacts":"zyh","pay":"","status":"0","grade_count":"0","distance":"2365"},{"id":"2","name":"xzc","uid":"1","grade":"0","create_time":"1500257710","update_time":"1500518256","province":"330000","city":"330100","area":"330104","mobile":"","logo":"1","lng":"120.346681","lat":"30.30215","phone":"465546","address":"南京路xxx","contacts":"小张","pay":"546454","status":"0","grade_count":"0","distance":"3416"},{"id":"3","name":"测试2","uid":"1","grade":"0","create_time":"1500262214","update_time":"1500518219","province":"330000","city":"330100","area":"330104","mobile":"18568545125","logo":"2","lng":"120.208838","lat":"30.264595","phone":"54665465","address":"三新银座","contacts":"小徐","pay":"8446456","status":"0","grade_count":"0","distance":"17259"}]
     */

    private String count;
    /**
     * id : 1
     * name : 杭州大鱼
     * uid : 1
     * grade : 0
     * create_time : 1499916729
     * update_time : 1500617852
     * province : 330000
     * city : 330100
     * area : 330104
     * mobile : 18358587556
     * logo : 2
     * lng : 120.359904
     * lat : 30.302337
     * phone : 58587544
     * address : 江干区学林街515
     * contacts : zyh
     * pay :
     * status : 0
     * grade_count : 0
     * distance : 2365
     */

    private List<ListEntity> list;

    public void setCount(String count) {
        this.count = count;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity implements Serializable{
        private String id;
        private String name;
        private String uid;
        private String grade;
        private String create_time;
        private String update_time;
        private String province;
        private String city;
        private String area;
        private String mobile;
        private String logo;
        private double lng;
        private double lat;
        private String phone;
        private String address;
        private String contacts;
        private String pay;
        private String status;
        private String grade_count;
        private String distance;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setGrade_count(String grade_count) {
            this.grade_count = grade_count;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUid() {
            return uid;
        }

        public String getGrade() {
            return grade;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getArea() {
            return area;
        }

        public String getMobile() {
            return mobile;
        }

        public String getLogo() {
            return logo;
        }

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getContacts() {
            return contacts;
        }

        public String getPay() {
            return pay;
        }

        public String getStatus() {
            return status;
        }

        public String getGrade_count() {
            return grade_count;
        }

        public String getDistance() {
            return distance;
        }
    }
}
