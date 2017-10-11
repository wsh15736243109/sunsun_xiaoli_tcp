package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class GoodsDetailBean implements Serializable {

    /**
     * secondary_headlines : 123
     * main_img : 2
     * has_receipt : 0
     * under_guaranty : 0
     * support_replace : 0
     * total_sales : 0
     * view_cnt : 18
     * consignment_time : 0
     * contact_name :
     * contact_way :
     * expire_time : 3999999999
     * favorite_cnt : 0
     * uid : 1
     * place_origin :
     * product_code :
     * goods_unit_name :
     * sku_pkid : 12
     * dt_goods_unit :
     * id : 4
     * name : ceshi
     * onshelf : 1
     * store_id : 1
     * weight : 0.00
     * synopsis :
     * secondary : 123
     * template_id : 0
     * loc_country :
     * loc_province :
     * loc_city :
     * loc_address :
     * cate_id : 30
     * update_time : 1500431972
     * sku_list : [{"id":"12","sku_id":"3:3;4:5;6:7;","sku_desc":"sasfddfs:1;ceshi:a;颜色:11;","ori_price":"200","price":"100","skuimg":"1","quantity":"4","product_code":"","create_time":"1500360151","product_id":"4","update_time":"1500360151","sku_pkid":"12"},{"id":"13","sku_id":"3:3;4:5;6:8;","sku_desc":"sasfddfs:1;ceshi:a;颜色:22;","ori_price":"3200","price":"1100","skuimg":"1","quantity":"44","product_code":"","create_time":"1500360151","product_id":"4","update_time":"1500360151","sku_pkid":"13"}]
     * sku_info : [{"sku_id":"3","sku_name":"sasfddfs","value_list":[{"value_id":"3","value_name":"1"}]},{"sku_id":"4","sku_name":"ceshi","value_list":[{"value_id":"5","value_name":"a"}]},{"sku_id":"6","sku_name":"颜色","value_list":[{"value_id":"7","value_name":"11"},{"value_id":"8","value_name":"22"}]}]
     * business_status : 0
     * carousel_images : ["2","2"]
     * is_fav : 0
     */

    private String secondary_headlines;
    private String main_img;
    private String has_receipt;
    private String under_guaranty;
    private String support_replace;
    private String total_sales;
    private String view_cnt;
    private String consignment_time;
    private String contact_name;
    private String contact_way;
    private String expire_time;
    private String favorite_cnt;
    private String uid;
    private String place_origin;
    private String product_code;
    private String goods_unit_name;
    private String sku_pkid;
    private String dt_goods_unit;
    private String id;
    private String name;
    private String onshelf;
    private String store_id;
    private String weight;
    private String synopsis;
    private String secondary;
    private String template_id;
    private String loc_country;
    private String loc_province;
    private String loc_city;
    private String loc_address;
    private String cate_id;
    private String update_time;
    private String business_status;
    private String is_fav;
    private int selectPositon;
    private int count;
    private ArrayList<DetailImage> detail_img;

    public ArrayList<DetailImage> getDetail_img() {
        return detail_img;
    }

    public void setDetail_img(ArrayList<DetailImage> detail_img) {
        this.detail_img = detail_img;
    }

    public static class DetailImage implements Serializable{

        /**
         * update_time : 1503022250
         * create_time : 1503022250
         * pid : 23
         * img_id : 1
         * id : 57
         * type : 6216
         */
        private String update_time;
        private String create_time;
        private String pid;
        private String img_id;
        private String id;
        private String type;

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setImg_id(String img_id) {
            this.img_id = img_id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getPid() {
            return pid;
        }

        public String getImg_id() {
            return img_id;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }
    }
    public int getSelectPositon() {
        return selectPositon;
    }

    public void setSelectPositon(int selectPositon) {
        this.selectPositon = selectPositon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**

     * id : 12
     * sku_id : 3:3;4:5;6:7;
     * sku_desc : sasfddfs:1;ceshi:a;颜色:11;
     * ori_price : 200
     * price : 100
     * skuimg : 1
     * quantity : 4
     * product_code :
     * create_time : 1500360151
     * product_id : 4
     * update_time : 1500360151
     * sku_pkid : 12
     */

    private List<SkuListEntity> sku_list;
    /**
     * sku_id : 3
     * sku_name : sasfddfs
     * value_list : [{"value_id":"3","value_name":"1"}]
     */

    private List<SkuInfoEntity> sku_info;
    private List<String> carousel_images;

    public void setSecondary_headlines(String secondary_headlines) {
        this.secondary_headlines = secondary_headlines;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    public void setHas_receipt(String has_receipt) {
        this.has_receipt = has_receipt;
    }

    public void setUnder_guaranty(String under_guaranty) {
        this.under_guaranty = under_guaranty;
    }

    public void setSupport_replace(String support_replace) {
        this.support_replace = support_replace;
    }

    public void setTotal_sales(String total_sales) {
        this.total_sales = total_sales;
    }

    public void setView_cnt(String view_cnt) {
        this.view_cnt = view_cnt;
    }

    public void setConsignment_time(String consignment_time) {
        this.consignment_time = consignment_time;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public void setContact_way(String contact_way) {
        this.contact_way = contact_way;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public void setFavorite_cnt(String favorite_cnt) {
        this.favorite_cnt = favorite_cnt;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPlace_origin(String place_origin) {
        this.place_origin = place_origin;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public void setGoods_unit_name(String goods_unit_name) {
        this.goods_unit_name = goods_unit_name;
    }

    public void setSku_pkid(String sku_pkid) {
        this.sku_pkid = sku_pkid;
    }

    public void setDt_goods_unit(String dt_goods_unit) {
        this.dt_goods_unit = dt_goods_unit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnshelf(String onshelf) {
        this.onshelf = onshelf;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
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

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public void setIs_fav(String is_fav) {
        this.is_fav = is_fav;
    }

    public void setSku_list(List<SkuListEntity> sku_list) {
        this.sku_list = sku_list;
    }

    public void setSku_info(List<SkuInfoEntity> sku_info) {
        this.sku_info = sku_info;
    }

    public void setCarousel_images(List<String> carousel_images) {
        this.carousel_images = carousel_images;
    }

    public String getSecondary_headlines() {
        return secondary_headlines;
    }

    public String getMain_img() {
        return main_img;
    }

    public String getHas_receipt() {
        return has_receipt;
    }

    public String getUnder_guaranty() {
        return under_guaranty;
    }

    public String getSupport_replace() {
        return support_replace;
    }

    public String getTotal_sales() {
        return total_sales;
    }

    public String getView_cnt() {
        return view_cnt;
    }

    public String getConsignment_time() {
        return consignment_time;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getContact_way() {
        return contact_way;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public String getFavorite_cnt() {
        return favorite_cnt;
    }

    public String getUid() {
        return uid;
    }

    public String getPlace_origin() {
        return place_origin;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getGoods_unit_name() {
        return goods_unit_name;
    }

    public String getSku_pkid() {
        return sku_pkid;
    }

    public String getDt_goods_unit() {
        return dt_goods_unit;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOnshelf() {
        return onshelf;
    }

    public String getStore_id() {
        return store_id;
    }

    public String getWeight() {
        return weight;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getSecondary() {
        return secondary;
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

    public String getUpdate_time() {
        return update_time;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public String getIs_fav() {
        return is_fav;
    }

    public List<SkuListEntity> getSku_list() {
        return sku_list;
    }

    public List<SkuInfoEntity> getSku_info() {
        return sku_info;
    }

    public List<String> getCarousel_images() {
        return carousel_images;
    }

    public static class SkuListEntity implements Serializable {
        private String id;
        private String sku_id;
        private String sku_desc;
        private String ori_price;
        private String price;
        private String skuimg;
        private String quantity;
        private String product_code;
        private String create_time;
        private String product_id;
        private String update_time;
        private String sku_pkid;

        public void setId(String id) {
            this.id = id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setSku_desc(String sku_desc) {
            this.sku_desc = sku_desc;
        }

        public void setOri_price(String ori_price) {
            this.ori_price = ori_price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setSkuimg(String skuimg) {
            this.skuimg = skuimg;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setSku_pkid(String sku_pkid) {
            this.sku_pkid = sku_pkid;
        }

        public String getId() {
            return id;
        }

        public String getSku_id() {
            return sku_id;
        }

        public String getSku_desc() {
            return sku_desc;
        }

        public String getOri_price() {
            return ori_price;
        }

        public String getPrice() {
            return price;
        }

        public String getSkuimg() {
            return skuimg;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getSku_pkid() {
            return sku_pkid;
        }
    }

    public static class SkuInfoEntity implements Serializable {
        private String sku_id;
        private String sku_name;
        /**
         * value_id : 3
         * value_name : 1
         */

        private List<ValueListEntity> value_list;

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public void setValue_list(List<ValueListEntity> value_list) {
            this.value_list = value_list;
        }

        public String getSku_id() {
            return sku_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public List<ValueListEntity> getValue_list() {
            return value_list;
        }

        public static class ValueListEntity implements Serializable {
            private String value_id;
            private String value_name;

            public void setValue_id(String value_id) {
                this.value_id = value_id;
            }

            public void setValue_name(String value_name) {
                this.value_name = value_name;
            }

            public String getValue_id() {
                return value_id;
            }

            public String getValue_name() {
                return value_name;
            }
        }
    }
}
