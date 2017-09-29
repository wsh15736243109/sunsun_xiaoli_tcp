package com.itboye.pondteam.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ArticalBean implements Serializable {

    /**
     * count : 1
     * list : [{"id":"5","nickname":"18358587556","uid":"6","shopname":"杭州大鱼","sid":"1","lat":"30.302337","lng":"120.359904","city":"330100","area":"330104","logo":"2","title":"标题1","create_time":"1502937152","status":"0","img":"3","detail":"这是详情","distance":"7142775"}]
     */

    private String count;
    /**
     * id : 5
     * nickname : 18358587556
     * uid : 6
     * shopname : 杭州大鱼
     * sid : 1
     * lat : 30.302337
     * lng : 120.359904
     * city : 330100
     * area : 330104
     * logo : 2
     * title : 标题1
     * create_time : 1502937152
     * status : 0
     * img : 3
     * detail : 这是详情
     * distance : 7142775
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
        private String nickname;
        private String uid;
        private String shopname;
        private String sid;
        private String lat;
        private String lng;
        private String city;
        private String area;
        private String logo;
        private String title;
        private String create_time;
        private String status;
        private String img;
        private String detail;
        private String distance;

        public void setId(String id) {
            this.id = id;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getId() {
            return id;
        }

        public String getNickname() {
            return nickname;
        }

        public String getUid() {
            return uid;
        }

        public String getShopname() {
            return shopname;
        }

        public String getSid() {
            return sid;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }

        public String getCity() {
            return city;
        }

        public String getArea() {
            return area;
        }

        public String getLogo() {
            return logo;
        }

        public String getTitle() {
            return title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getStatus() {
            return status;
        }

        public String getImg() {
            return img;
        }

        public String getDetail() {
            return detail;
        }

        public String getDistance() {
            return distance;
        }
    }
}
