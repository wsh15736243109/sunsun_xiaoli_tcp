package sunsun.xiaoli.jiarebang.sunsunlingshou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class OrderDetailBean implements Serializable{

    /**
     * city : 三亚市
     * postal_code : 0
     * id_card :
     * detailinfo : 不是就是就是不对不对
     * mobile : 15464976734
     * area : 市辖区
     * province : 海南省
     * contactname : wdh
     * country : 中国
     * id : 12
     * uid : 13
     * order_code : T172470930016165517C
     * price : 1100
     * post_price : 1000
     * note :
     * status : 1
     * pay_status : 1
     * order_status : 4
     * cs_status : 0
     * createtime : 1504603801
     * updatetime : 1504691461
     * comment_status : 0
     * from : 4
     * discount_money : 0
     * storeid : 1
     * goods_amount : 1100
     * pay_type : 3
     * pay_code : PA172470930013564185C
     * pay_balance : 0
     * evaluate : 0
     * publisher_name : itboye
     * query_status : 3
     * _auto_op_time : 0
     * items : [{"id":"14","name":"普通商品测试","img":"2","price":"100.00","ori_price":"200.00","sku_id":"10:19;11:20;","psku_id":"32","sku_desc":"颜色:红色;型号:10厘米长;","count":"1","order_code":"T172470930016165517C","createtime":"1504603801","p_id":"20","dt_goods_unit":"","dt_origin_country":"0","weight":"0.00"}]
     * _address : {"id":"12","contactname":"wdh","uid":"13","country":"中国","province":"海南省","detailinfo":"不是就是就是不对不对","area":"市辖区","mobile":"15464976734","wxno":"","order_code":"T172470930016165517C","city":"三亚市","id_card":"","postal_code":"0","address_id":"","notes":""}
     * stores_info : {"id":"37","stores_id":"1","order_code":"T172470930016165517C","send_type":"1","send_time":"0","status":"0","create_time":"1504603801"}
     * _payinfo : {"id":"12","order_content":"T172470930016165517C","createtime":"1504603801","uid":"13","pay_type":"1","pay_money":"1100","true_pay_money":"1100","pay_code":"PA172470930013564185C","trade_no":"111111111122222222","pay_balance":"0","pay_status":"1","pay_currency":"rmb","update_time":"1504691461","b_status":"0"}
     */

    private String city;
    private String postal_code;
    private String id_card;
    private String detailinfo;
    private String mobile;
    private String area;
    private String province;
    private String contactname;
    private String country;
    private String id;
    private String uid;
    private String order_code;
    private String price;
    private String post_price;
    private String note;
    private String status;
    private String pay_status;
    private String order_status;
    private String cs_status;
    private String createtime;
    private String updatetime;
    private String comment_status;
    private String from;
    private String discount_money;
    private String storeid;
    private String goods_amount;
    private String pay_type;
    private String pay_code;
    private String pay_balance;
    private String evaluate;
    private String publisher_name;
    private String query_status;
    private String _auto_op_time;
    /**
     * id : 12
     * contactname : wdh
     * uid : 13
     * country : 中国
     * province : 海南省
     * detailinfo : 不是就是就是不对不对
     * area : 市辖区
     * mobile : 15464976734
     * wxno :
     * order_code : T172470930016165517C
     * city : 三亚市
     * id_card :
     * postal_code : 0
     * address_id :
     * notes :
     */

    private AddressEntity _address;
    /**
     * id : 37
     * stores_id : 1
     * order_code : T172470930016165517C
     * send_type : 1
     * send_time : 0
     * status : 0
     * create_time : 1504603801
     */

    private StoresInfoEntity stores_info;
    /**
     * id : 12
     * order_content : T172470930016165517C
     * createtime : 1504603801
     * uid : 13
     * pay_type : 1
     * pay_money : 1100
     * true_pay_money : 1100
     * pay_code : PA172470930013564185C
     * trade_no : 111111111122222222
     * pay_balance : 0
     * pay_status : 1
     * pay_currency : rmb
     * update_time : 1504691461
     * b_status : 0
     */

    private PayinfoEntity _payinfo;
    /**
     * id : 14
     * name : 普通商品测试
     * img : 2
     * price : 100.00
     * ori_price : 200.00
     * sku_id : 10:19;11:20;
     * psku_id : 32
     * sku_desc : 颜色:红色;型号:10厘米长;
     * count : 1
     * order_code : T172470930016165517C
     * createtime : 1504603801
     * p_id : 20
     * dt_goods_unit :
     * dt_origin_country : 0
     * weight : 0.00
     */

    private List<ItemsEntity> items;

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public void setDetailinfo(String detailinfo) {
        this.detailinfo = detailinfo;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPost_price(String post_price) {
        this.post_price = post_price;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setCs_status(String cs_status) {
        this.cs_status = cs_status;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setDiscount_money(String discount_money) {
        this.discount_money = discount_money;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public void setPay_balance(String pay_balance) {
        this.pay_balance = pay_balance;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public void setQuery_status(String query_status) {
        this.query_status = query_status;
    }

    public void set_auto_op_time(String _auto_op_time) {
        this._auto_op_time = _auto_op_time;
    }

    public void set_address(AddressEntity _address) {
        this._address = _address;
    }

    public void setStores_info(StoresInfoEntity stores_info) {
        this.stores_info = stores_info;
    }

    public void set_payinfo(PayinfoEntity _payinfo) {
        this._payinfo = _payinfo;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public String getCity() {
        return city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getId_card() {
        return id_card;
    }

    public String getDetailinfo() {
        return detailinfo;
    }

    public String getMobile() {
        return mobile;
    }

    public String getArea() {
        return area;
    }

    public String getProvince() {
        return province;
    }

    public String getContactname() {
        return contactname;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getOrder_code() {
        return order_code;
    }

    public String getPrice() {
        return price;
    }

    public String getPost_price() {
        return post_price;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getCs_status() {
        return cs_status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getComment_status() {
        return comment_status;
    }

    public String getFrom() {
        return from;
    }

    public String getDiscount_money() {
        return discount_money;
    }

    public String getStoreid() {
        return storeid;
    }

    public String getGoods_amount() {
        return goods_amount;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getPay_code() {
        return pay_code;
    }

    public String getPay_balance() {
        return pay_balance;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public String getQuery_status() {
        return query_status;
    }

    public String get_auto_op_time() {
        return _auto_op_time;
    }

    public AddressEntity get_address() {
        return _address;
    }

    public StoresInfoEntity getStores_info() {
        return stores_info;
    }

    public PayinfoEntity get_payinfo() {
        return _payinfo;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public static class AddressEntity implements Serializable{
        private String id;
        private String contactname;
        private String uid;
        private String country;
        private String province;
        private String detailinfo;
        private String area;
        private String mobile;
        private String wxno;
        private String order_code;
        private String city;
        private String id_card;
        private String postal_code;
        private String address_id;
        private String notes;

        public void setId(String id) {
            this.id = id;
        }

        public void setContactname(String contactname) {
            this.contactname = contactname;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setCountry(String country) {
            this.country = country;
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

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setWxno(String wxno) {
            this.wxno = wxno;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getId() {
            return id;
        }

        public String getContactname() {
            return contactname;
        }

        public String getUid() {
            return uid;
        }

        public String getCountry() {
            return country;
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

        public String getMobile() {
            return mobile;
        }

        public String getWxno() {
            return wxno;
        }

        public String getOrder_code() {
            return order_code;
        }

        public String getCity() {
            return city;
        }

        public String getId_card() {
            return id_card;
        }

        public String getPostal_code() {
            return postal_code;
        }

        public String getAddress_id() {
            return address_id;
        }

        public String getNotes() {
            return notes;
        }
    }

    public static class StoresInfoEntity implements Serializable{
        private String id;
        private String name;
        private String stores_id;
        private String order_code;
        private String send_type;
        private String send_time;
        private String status;
        private String create_time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setStores_id(String stores_id) {
            this.stores_id = stores_id;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public void setSend_type(String send_type) {
            this.send_type = send_type;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getId() {
            return id;
        }

        public String getStores_id() {
            return stores_id;
        }

        public String getOrder_code() {
            return order_code;
        }

        public String getSend_type() {
            return send_type;
        }

        public String getSend_time() {
            return send_time;
        }

        public String getStatus() {
            return status;
        }

        public String getCreate_time() {
            return create_time;
        }
    }

    public static class PayinfoEntity implements Serializable{
        private String id;
        private String order_content;
        private String createtime;
        private String uid;
        private String pay_type;
        private String pay_money;
        private String true_pay_money;
        private String pay_code;
        private String trade_no;
        private String pay_balance;
        private String pay_status;
        private String pay_currency;
        private String update_time;
        private String b_status;

        public void setId(String id) {
            this.id = id;
        }

        public void setOrder_content(String order_content) {
            this.order_content = order_content;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public void setTrue_pay_money(String true_pay_money) {
            this.true_pay_money = true_pay_money;
        }

        public void setPay_code(String pay_code) {
            this.pay_code = pay_code;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public void setPay_balance(String pay_balance) {
            this.pay_balance = pay_balance;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public void setPay_currency(String pay_currency) {
            this.pay_currency = pay_currency;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setB_status(String b_status) {
            this.b_status = b_status;
        }

        public String getId() {
            return id;
        }

        public String getOrder_content() {
            return order_content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public String getUid() {
            return uid;
        }

        public String getPay_type() {
            return pay_type;
        }

        public String getPay_money() {
            return pay_money;
        }

        public String getTrue_pay_money() {
            return true_pay_money;
        }

        public String getPay_code() {
            return pay_code;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public String getPay_balance() {
            return pay_balance;
        }

        public String getPay_status() {
            return pay_status;
        }

        public String getPay_currency() {
            return pay_currency;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getB_status() {
            return b_status;
        }
    }

    public static class ItemsEntity implements Serializable{
        private String id;
        private String name;
        private String img;
        private String price;
        private String ori_price;
        private String sku_id;
        private String psku_id;
        private String sku_desc;
        private String count;
        private String order_code;
        private String createtime;
        private String p_id;
        private String dt_goods_unit;
        private String dt_origin_country;
        private String weight;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setOri_price(String ori_price) {
            this.ori_price = ori_price;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public void setPsku_id(String psku_id) {
            this.psku_id = psku_id;
        }

        public void setSku_desc(String sku_desc) {
            this.sku_desc = sku_desc;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public void setDt_goods_unit(String dt_goods_unit) {
            this.dt_goods_unit = dt_goods_unit;
        }

        public void setDt_origin_country(String dt_origin_country) {
            this.dt_origin_country = dt_origin_country;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getImg() {
            return img;
        }

        public String getPrice() {
            return price;
        }

        public String getOri_price() {
            return ori_price;
        }

        public String getSku_id() {
            return sku_id;
        }

        public String getPsku_id() {
            return psku_id;
        }

        public String getSku_desc() {
            return sku_desc;
        }

        public String getCount() {
            return count;
        }

        public String getOrder_code() {
            return order_code;
        }

        public String getCreatetime() {
            return createtime;
        }

        public String getP_id() {
            return p_id;
        }

        public String getDt_goods_unit() {
            return dt_goods_unit;
        }

        public String getDt_origin_country() {
            return dt_origin_country;
        }

        public String getWeight() {
            return weight;
        }
    }
}
