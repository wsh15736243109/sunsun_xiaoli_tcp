package sunsun.xiaoli.jiarebang.sunsunlingshou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

public class ServiceBean implements Serializable {
    /**
     * id : 22
     * type : 0
     * uid : 1
     * name : 造景装饰
     * product_code :
     * secondary_headlines : 造景装饰副标题
     * template_id : 0
     * loc_country :
     * loc_province :
     * loc_city :
     * loc_address :
     * cate_id : 35
     * create_time : 0
     * update_time : 0
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
     */

    private ProductInfoEntity product_info;
    /**
     * pid : 22
     * id : 52
     * img_id : 2
     * type : 6015
     * create_time : 1502848926
     * update_time : 1502848926
     */

    private ImgEntity img;
    /**
     * id : 36
     * sku_desc : 造景装饰规格:1米造景;
     * sku_id : 65:151;
     * price : 0
     */

    private List<SkuInfoEntity> sku_info;



//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setSku_id(String sku_id) {
//        this.sku_id = sku_id;
//    }
//
//    public void setSku_pid(String sku_pid) {
//        this.sku_pid = sku_pid;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getSku_id() {
//        return sku_id;
//    }
//
//    public String getSku_pid() {
//        return sku_pid;
//    }

    public void setProduct_info(ProductInfoEntity product_info) {
        this.product_info = product_info;
    }

    public void setImg(ImgEntity img) {
        this.img = img;
    }

    public void setSku_info(List<SkuInfoEntity> sku_info) {
        this.sku_info = sku_info;
    }

    public ProductInfoEntity getProduct_info() {
        return product_info;
    }

    public ImgEntity getImg() {
        return img;
    }

    public List<SkuInfoEntity> getSku_info() {
        return sku_info;
    }


    public static class ProductInfoEntity implements Serializable{
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
        private int canpack;

        public int getCanpack() {
            return canpack;
        }

        public void setCanpack(int canpack) {
            this.canpack = canpack;
        }

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
    }

    public static class ImgEntity implements Serializable{
        private String pid;
        private String id;
        private String img_id;
        private String type;
        private String create_time;
        private String update_time;

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setImg_id(String img_id) {
            this.img_id = img_id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getPid() {
            return pid;
        }

        public String getId() {
            return id;
        }

        public String getImg_id() {
            return img_id;
        }

        public String getType() {
            return type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }
    }

    public static class SkuInfoEntity implements Serializable{
        private String id;
        private String sku_desc;
        private String sku_id;
        private String price;
        private boolean select;



        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
        public void setId(String id) {
            this.id = id;
        }

        public void setSku_desc(String sku_desc) {
            this.sku_desc = sku_desc;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public String getSku_desc() {
            return sku_desc;
        }

        public String getSku_id() {
            return sku_id;
        }

        public String getPrice() {
            return price;
        }
    }
}
