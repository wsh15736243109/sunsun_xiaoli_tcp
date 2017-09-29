package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderBean implements Serializable {

    /**
     * count : 4
     * list : [{"id":"27","uid":"5","order_code":"T1721109134053265294","price":"1100","post_price":"1000","note":"","status":"1","pay_status":"0","order_status":"2","cs_status":"0","createtime":"1501492420","updatetime":"1501492420","comment_status":"0","from":"4","discount_money":"0","storeid":"1","goods_amount":"1100","pay_type":"0","pay_code":"","pay_balance":"0","publisher_name":"itboye","stores_id":"1","send_type":"1","send_time":"13:00","send_status":"0","items":[{"id":"24","name":"ceshi","img":"1","price":"100.00","ori_price":"200.00","sku_id":"3:3;4:5;6:7;","psku_id":"12","sku_desc":"sasfddfs:1;ceshi:a;颜色:11;","count":"1","order_code":"T1721109134053265294","createtime":"1501492420","p_id":"4","dt_goods_unit":"","dt_origin_country":"0","weight":"0.00"}],"query_status":"1"},{"id":"29","uid":"5","order_code":"T1722301550752278404","price":"3200","post_price":"1000","note":"222222","status":"1","pay_status":"0","order_status":"2","cs_status":"0","createtime":"1502502907","updatetime":"1502502907","comment_status":"0","from":"4","discount_money":"0","storeid":"1","goods_amount":"3200","pay_type":"0","pay_code":"","pay_balance":"0","publisher_name":"itboye","stores_id":"2","send_type":"1","send_time":"2017-7-25-12：00","send_status":"0","items":[{"id":"26","name":"ceshi","img":"2","price":"1100.00","ori_price":"3200.00","sku_id":"3:3;4:5;6:8;","psku_id":"13","sku_desc":"sasfddfs:1;ceshi:a;颜色:22;","count":"2","order_code":"T1722301550752278404","createtime":"1502502907","p_id":"4","dt_goods_unit":"","dt_origin_country":"0","weight":"0.00"}],"query_status":"1"},{"id":"30","uid":"5","order_code":"T1722306355332405634","price":"1100","post_price":"1000","note":"","status":"1","pay_status":"0","order_status":"2","cs_status":"0","createtime":"1502519753","updatetime":"1502519753","comment_status":"0","from":"4","discount_money":"0","storeid":"1","goods_amount":"1100","pay_type":"0","pay_code":"","pay_balance":"0","publisher_name":"itboye","stores_id":"2","send_type":"1","send_time":"0","send_status":"0","items":[{"id":"27","name":"普通商品测试","img":"2","price":"100.00","ori_price":"200.00","sku_id":"10:19;11:20;","psku_id":"32","sku_desc":"颜色:红色;型号:10厘米长;","count":"1","order_code":"T1722306355332405634","createtime":"1502519753","p_id":"20","dt_goods_unit":"","dt_origin_country":"0","weight":"0.00"}],"query_status":"1"},{"id":"31","uid":"5","order_code":"T1722306362478557984","price":"1100","post_price":"1000","note":"","status":"1","pay_status":"0","order_status":"2","cs_status":"0","createtime":"1502519784","updatetime":"1502519784","comment_status":"0","from":"4","discount_money":"0","storeid":"1","goods_amount":"1100","pay_type":"0","pay_code":"","pay_balance":"0","publisher_name":"itboye","stores_id":"2","send_type":"1","send_time":"0","send_status":"0","items":[{"id":"28","name":"普通商品测试","img":"2","price":"100.00","ori_price":"200.00","sku_id":"10:19;11:20;","psku_id":"32","sku_desc":"颜色:红色;型号:10厘米长;","count":"1","order_code":"T1722306362478557984","createtime":"1502519784","p_id":"20","dt_goods_unit":"","dt_origin_country":"0","weight":"0.00"}],"query_status":"1"}]
     */

    private String count;
    /**
     * id : 27
     * uid : 5
     * order_code : T1721109134053265294
     * price : 1100
     * post_price : 1000
     * note :
     * status : 1
     * pay_status : 0
     * order_status : 2
     * cs_status : 0
     * createtime : 1501492420
     * updatetime : 1501492420
     * comment_status : 0
     * from : 4
     * discount_money : 0
     * storeid : 1
     * goods_amount : 1100
     * pay_type : 0
     * pay_code :
     * pay_balance : 0
     * publisher_name : itboye
     * stores_id : 1
     * send_type : 1
     * send_time : 13:00
     * send_status : 0
     * items : [{"id":"24","name":"ceshi","img":"1","price":"100.00","ori_price":"200.00","sku_id":"3:3;4:5;6:7;","psku_id":"12","sku_desc":"sasfddfs:1;ceshi:a;颜色:11;","count":"1","order_code":"T1721109134053265294","createtime":"1501492420","p_id":"4","dt_goods_unit":"","dt_origin_country":"0","weight":"0.00"}]
     * query_status : 1
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
        private String uid;
        private String order_code;
        private String price;
        private String post_price;
        private String note;
        private int status;
        private int pay_status;
        private int order_status;
        private int cs_status;
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
        private String publisher_name;
        private String stores_id;
        private String stores_name;
        private String send_type;
        private String send_time;
        private String send_status;
        private String query_status;

        public String getStores_name() {
            return stores_name;
        }

        public void setStores_name(String stores_name) {
            this.stores_name = stores_name;
        }

        /**
         * id : 24
         * name : ceshi
         * img : 1
         * price : 100.00
         * ori_price : 200.00
         * sku_id : 3:3;4:5;6:7;
         * psku_id : 12
         * sku_desc : sasfddfs:1;ceshi:a;颜色:11;
         * count : 1
         * order_code : T1721109134053265294
         * createtime : 1501492420
         * p_id : 4
         * dt_goods_unit :
         * dt_origin_country : 0
         * weight : 0.00
         */

        private List<ItemsEntity> items;

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

        public void setStatus(int status) {
            this.status = status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public void setCs_status(int cs_status) {
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

        public void setPublisher_name(String publisher_name) {
            this.publisher_name = publisher_name;
        }

        public void setStores_id(String stores_id) {
            this.stores_id = stores_id;
        }

        public void setSend_type(String send_type) {
            this.send_type = send_type;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public void setSend_status(String send_status) {
            this.send_status = send_status;
        }

        public void setQuery_status(String query_status) {
            this.query_status = query_status;
        }

        public void setItems(List<ItemsEntity> items) {
            this.items = items;
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

        public int getStatus() {
            return status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public int getOrder_status() {
            return order_status;
        }

        public int getCs_status() {
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

        public String getPublisher_name() {
            return publisher_name;
        }

        public String getStores_id() {
            return stores_id;
        }

        public String getSend_type() {
            return send_type;
        }

        public String getSend_time() {
            return send_time;
        }

        public String getSend_status() {
            return send_status;
        }

        public String getQuery_status() {
            return query_status;
        }

        public List<ItemsEntity> getItems() {
            return items;
        }

        public static class ItemsEntity  implements Serializable{
            private String id;
            private String name;
            private String img;
            private String price;
            private String ori_price;
            private String sku_id;
            private String psku_id;
            private String sku_desc;
            private int count;
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

            public void setCount(int count) {
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

            public int getCount() {
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
}

