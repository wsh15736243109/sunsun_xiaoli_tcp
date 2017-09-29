package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/21.
 */

public class GoodsListBean implements Serializable{

    /**
     * count : 0
     * list : [{"id":"4","type":"0","uid":"1","name":"ceshi","product_code":"","secondary_headlines":"123","template_id":"0","loc_country":"","loc_province":"","loc_city":"","loc_address":"","cate_id":"24","create_time":"0","update_time":"1500431972","onshelf":"1","status":"1","store_id":"1","detail":"1,","weight":"0.00","synopsis":"","dt_origin_country":"0","dt_goods_unit":"","lang":"","place_origin":"","max_price":"1100","min_price":"100","main_img":"2"},{"id":"12","type":"0","uid":"1","name":"123","product_code":"1","secondary_headlines":"123","template_id":"0","loc_country":"","loc_province":"","loc_city":"","loc_address":"","cate_id":"24","create_time":"0","update_time":"0","onshelf":"1","status":"1","store_id":"1","detail":"2,","weight":"0.00","synopsis":"","dt_origin_country":"0","dt_goods_unit":"","lang":"","place_origin":"","max_price":"100","min_price":"100","main_img":"2"},{"id":"13","type":"0","uid":"1","name":"测试简介","product_code":"1","secondary_headlines":"简介","template_id":"0","loc_country":"","loc_province":"","loc_city":"","loc_address":"","cate_id":"24","create_time":"0","update_time":"1500539748","onshelf":"1","status":"1","store_id":"1","detail":"1,1,","weight":"0.00","synopsis":"简介简介简介","dt_origin_country":"0","dt_goods_unit":"","lang":"","place_origin":"","max_price":"100","min_price":"100","main_img":"1"}]
     */

    private String count;
    /**
     * id : 4
     * type : 0
     * uid : 1
     * name : ceshi
     * product_code :
     * secondary_headlines : 123
     * template_id : 0
     * loc_country :
     * loc_province :
     * loc_city :
     * loc_address :
     * cate_id : 24
     * create_time : 0
     * update_time : 1500431972
     * onshelf : 1
     * status : 1
     * store_id : 1
     * detail : 1,
     * weight : 0.00
     * synopsis :
     * dt_origin_country : 0
     * dt_goods_unit :
     * lang :
     * place_origin :
     * max_price : 1100
     * min_price : 100
     * main_img : 2
     */

    private ArrayList<ListEntity> list;

    public void setCount(String count) {
        this.count = count;
    }

    public void setList(ArrayList<ListEntity> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<ListEntity> getList() {
        return list;
    }

    public static class ListEntity implements Serializable{
        private String id;
        private String type;
        private String uid;
        private String name;
        private String product_code;
        private String secondary_headlines;
        private String template_id;
        private String loc_country;
        private String loc_province;
        private String loc_city;
        private String loc_address;
        private String cate_id;
        private String create_time;
        private String update_time;
        private String onshelf;
        private String status;
        private String store_id;
        private String detail;
        private String weight;
        private String synopsis;
        private String dt_origin_country;
        private String dt_goods_unit;
        private String lang;
        private String place_origin;
        private String max_price;
        private String min_price;
        private String main_img;

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public void setSecondary_headlines(String secondary_headlines) {
            this.secondary_headlines = secondary_headlines;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public void setLoc_country(String loc_country) {
            this.loc_country = loc_country;
        }

        public void setLoc_province(String loc_province) {
            this.loc_province = loc_province;
        }

        public void setLoc_city(String loc_city) {
            this.loc_city = loc_city;
        }

        public void setLoc_address(String loc_address) {
            this.loc_address = loc_address;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setOnshelf(String onshelf) {
            this.onshelf = onshelf;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public void setDt_origin_country(String dt_origin_country) {
            this.dt_origin_country = dt_origin_country;
        }

        public void setDt_goods_unit(String dt_goods_unit) {
            this.dt_goods_unit = dt_goods_unit;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public void setPlace_origin(String place_origin) {
            this.place_origin = place_origin;
        }

        public void setMax_price(String max_price) {
            this.max_price = max_price;
        }

        public void setMin_price(String min_price) {
            this.min_price = min_price;
        }

        public void setMain_img(String main_img) {
            this.main_img = main_img;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getUid() {
            return uid;
        }

        public String getName() {
            return name;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getSecondary_headlines() {
            return secondary_headlines;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public String getLoc_country() {
            return loc_country;
        }

        public String getLoc_province() {
            return loc_province;
        }

        public String getLoc_city() {
            return loc_city;
        }

        public String getLoc_address() {
            return loc_address;
        }

        public String getCate_id() {
            return cate_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getOnshelf() {
            return onshelf;
        }

        public String getStatus() {
            return status;
        }

        public String getStore_id() {
            return store_id;
        }

        public String getDetail() {
            return detail;
        }

        public String getWeight() {
            return weight;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public String getDt_origin_country() {
            return dt_origin_country;
        }

        public String getDt_goods_unit() {
            return dt_goods_unit;
        }

        public String getLang() {
            return lang;
        }

        public String getPlace_origin() {
            return place_origin;
        }

        public String getMax_price() {
            return max_price;
        }

        public String getMin_price() {
            return min_price;
        }

        public String getMain_img() {
            return main_img;
        }
    }
}
