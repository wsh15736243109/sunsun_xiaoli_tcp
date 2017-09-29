package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/29.
 */

public class ShopCartBean implements Serializable {

    /**
     * store_name : 新零售
     * store_id : 1
     * store_boss_name : itboye
     * store_boss_uid : 1
     * store_logo :
     * product_code :
     * publisher_name : itboye
     * place_origin :
     * unit_desc :
     * contact_name :
     * expire_time : 3999999999
     * quantity : 4
     * id : 3
     * uid : 4
     * create_time : 1501313632
     * update_time : 1501313632
     * p_id : 4
     * sku_id : 3:3;4:5;6:7;
     * sku_desc : sasfddfs:1;ceshi:a;颜色:11;
     * icon_url : 1
     * count : 1
     * name : ceshi
     * express : 0.00
     * template_id : 0
     * price : 100.00
     * ori_price : 200.00
     * psku_id : 12
     * weight : 0
     * tax_rate : 0
     * group_id : 0
     * package_id : 0
     * item_status : 1
     * onshelf : 1
     * item_status_desc : ok
     */

    private String store_name;
    private String store_id;
    private String store_boss_name;
    private String store_boss_uid;
    private String store_logo;
    private String product_code;
    private String publisher_name;
    private String place_origin;
    private String unit_desc;
    private String contact_name;
    private String expire_time;
    private String quantity;
    private String id;
    private String uid;
    private String create_time;
    private String update_time;
    private String p_id;
    private String sku_id;
    private String sku_desc;
    private String icon_url;
    private String count;
    private String name;
    private String express;
    private String template_id;
    private double price;
    private String ori_price;
    private String psku_id;
    private String weight;
    private String tax_rate;
    private String group_id;
    private String package_id;
    private String item_status;
    private String onshelf;
    private String item_status_desc;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setStore_boss_name(String store_boss_name) {
        this.store_boss_name = store_boss_name;
    }

    public void setStore_boss_uid(String store_boss_uid) {
        this.store_boss_uid = store_boss_uid;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public void setPlace_origin(String place_origin) {
        this.place_origin = place_origin;
    }

    public void setUnit_desc(String unit_desc) {
        this.unit_desc = unit_desc;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public void setSku_desc(String sku_desc) {
        this.sku_desc = sku_desc;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOri_price(String ori_price) {
        this.ori_price = ori_price;
    }

    public void setPsku_id(String psku_id) {
        this.psku_id = psku_id;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }

    public void setOnshelf(String onshelf) {
        this.onshelf = onshelf;
    }

    public void setItem_status_desc(String item_status_desc) {
        this.item_status_desc = item_status_desc;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public String getStore_boss_name() {
        return store_boss_name;
    }

    public String getStore_boss_uid() {
        return store_boss_uid;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public String getPlace_origin() {
        return place_origin;
    }

    public String getUnit_desc() {
        return unit_desc;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getP_id() {
        return p_id;
    }

    public String getSku_id() {
        return sku_id;
    }

    public String getSku_desc() {
        return sku_desc;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public String getExpress() {
        return express;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public double getPrice() {
        return price;
    }

    public String getOri_price() {
        return ori_price;
    }

    public String getPsku_id() {
        return psku_id;
    }

    public String getWeight() {
        return weight;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getItem_status() {
        return item_status;
    }

    public String getOnshelf() {
        return onshelf;
    }

    public String getItem_status_desc() {
        return item_status_desc;
    }
}
