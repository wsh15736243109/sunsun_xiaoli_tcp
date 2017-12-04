package sunsun.xiaoli.jiarebang.interfaces;

/**
 * Created by admin on 2016/11/28.
 */

public interface ILingShouInterface<PersonDataBean> {
    /**
     * 获取附近商家
     *
     * @param city_code
     * @param lng
     * @param lat
     * @param name
     * @param maxDistance
     * @param page_index
     * @param page_size
     */
    void getNearStore(String city_code, String lng, String lat, String name, String maxDistance, int page_index, int page_size);

    /**
     * 获取商家详情
     *
     * @param seller_id
     */
    void storeDetail(String... seller_id);

    /**
     * 获取主要商品分类
     */
    void getMainClassify();

    /**
     * 获取二级分类
     *
     * @param cate_id
     */
    void getSeconfClassify(String cate_id);

    /**
     * 获取商品列表
     *
     * @param order
     * @param cate_id
     * @param keyword
     * @param page_index
     * @param page_size
     */
    void getGoodsList(String order, String cate_id, String keyword, int page_index, int page_size);

    /**
     * 获取关键词查询列表
     *
     * @param keyword
     */
    void getKeywordsList(String keyword);


    void getGoodsDetail(String... id);

    /**
     * 查询订单
     *
     * @param query_status
     * @param keyword
     * @param page_index
     * @param page_size
     * @param s_id
     */
    void queryOrder(String uid, int query_status, String keyword, int page_index, int page_size, String s_id);

    /**
     * 加入购物车
     *
     * @param sp
     * @param id
     * @param sku_pkid
     * @param sp1
     */
    void addShopCart(String sp, int count, String id, String sku_pkid, String sp1);

    /**
     * 查询购物车
     *
     * @param sp
     * @param product_type
     * @param sp1
     */
    void getShopCart(String sp, int product_type, String sp1);

    /**
     * 修改购物车
     *
     * @param id
     * @param currrentCount
     */
    void updateShopCart(String uid, String id, int currrentCount, String s_id);

    /**
     * 刪除購物車
     *
     * @param id
     */
    void deleteShopCart(String uid, String id, String s_id);


    /**
     * 购物车生成订单
     *
     * @param uid
     * @param ids
     * @param address_id
     * @param note
     * @param stores_id
     * @param send_type
     * @param send_time
     * @param freight_price
     * @param s_id
     */
    void shopCartOrder(String uid, String ids, String address_id, String note, String stores_id, String send_type, String send_time, String freight_price, String s_id);

    /**
     * 普通商品生成立即购买订单
     *
     * @param uid
     * @param count
     * @param sku_pkid
     * @param address_id
     * @param note
     * @param stores_id
     * @param send_type
     * @param send_time
     * @param freight_price
     * @param s_id
     */
    void goodsOrder(String uid, int count, String sku_pkid, String address_id, String note, String stores_id, String send_type, String send_time, String freight_price, String s_id);

    /**
     * 查询配送费用
     *
     * @param stores_id
     * @param address_id
     * @param sp
     */
    void queryFreightPrice(String stores_id, String address_id, String sp);

    /**
     * 添加收货地址
     *
     * @param uid
     * @param is_default
     * @param province
     * @param provinceId
     * @param city
     * @param area
     * @param cityid
     * @param areaid
     * @param detailAddress
     * @param name
     * @param phone
     * @param country_id
     * @param country
     * @param lng
     * @param lat
     * @param s_id
     */
    void addAddress(String uid, int is_default, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String s_id);

    /**
     * 查询地址
     *
     * @param uid
     * @param s_id
     */
    void queryAddress(String uid, String s_id);

    /**
     * 删除地址
     *
     * @param uid
     * @param id
     * @param s_id
     */
    void deleteAddress(String uid, String id, String s_id);

    /**
     * 检查序列号
     *
     * @param sp
     * @param xuliehao
     * @param sp1
     */
    void checkProductCode(String sp, String xuliehao, String sp1);

    /**
     * 修改地址
     *
     * @param sp
     * @param id
     * @param is_default
     * @param province
     * @param provinceId
     * @param city
     * @param area
     * @param cityid
     * @param areaid
     * @param detailAddress
     * @param name
     * @param phone
     * @param country_id
     * @param country
     * @param lng
     * @param lat
     * @param sp1
     */
    void updateAddress(String sp, String id, int is_default, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String sp1);

    /**
     * 获取默认地址
     *
     * @param sp
     * @param sp1
     */
    void getDefaultAddress(String sp, String sp1);

    /**
     * 订单重新支付
     *
     * @param uid
     * @param s_id
     * @param order_code
     */
    void rePay(String uid, String s_id, String order_code);

    /**
     * @param area
     * @param lng
     * @param lat
     */
    void getVerticalArtical(String area, String lng, String lat);

    /**
     * 获取轮播图
     *
     * @param position
     */
    void getBanner(int position);

    /**
     * 获取留言板消息
     *
     * @param uid
     * @param msgType
     */
    void getMessageList(String uid, String msgType);

    /**
     * @param uid
     * @param from_id
     */
    void getChatDetail(String uid, String from_id);

    /**
     * 发送消息
     *
     * @param uid
     * @param from_id
     * @param msg_type
     * @param title
     * @param summary
     * @param content
     * @param extra
     */
    void addMessage(String uid, String from_id, String msg_type, String title, String summary, String content, String extra);

    /**
     * 获取热门商品
     */
    void getHotSearchGoods();

    /**
     * 获取服务种类列表
     *
     * @param service_type
     */
    void getServiceSku(int service_type);

    /**
     * 测试支付
     *
     * @param pay_code
     * @param pay_money
     * @param sp
     */
    void payTest(String uid, String pay_code, double pay_money, String sp);

    void getOrderDetail(String sp, String order_code, String sp1);

    /**
     * 申请退货
     *
     * @param uid
     * @param refund_type 1全额退款，2只退货款
     * @param reason      退款理由
     * @param order_id
     * @param s_id
     */
    void tuiHuo(String uid, String refund_type, String reason, String order_id, String s_id);

    /**
     * 确认收货
     *
     * @param uid
     * @param order_code
     * @param s_id
     */
    void querenShouHuo(String uid, String order_code, String s_id);

    /**
     * 评价订单
     *
     * @param sp
     * @param order_code
     * @param i
     * @param sp1
     */
    void storePingJia(String sp, String order_code, float i, String sp1);

    /**
     * 是否为商家
     *
     * @param uid
     */
    void isMerchant(String uid);

    /**
     * 获取我的发布
     *
     * @param uid
     */
    void getMyPublish(String uid);

    /**
     * 添加发布
     *
     * @param sid
     * @param uid
     * @param title
     * @param imgId
     * @param content
     */
    void addArtical(String sid, String uid, String title, String imgId, String content);

//    void updateAddress(String uid,String );

    /**
     * 文章详情
     *
     * @param id
     */
    void getArticleInfo(String id);

    void queryProNo(String uid, String s_id);

    void getSkuPidInConsultBuy(String uid, String sId);

    void setDefaultAddress(String sp, String id, String sp1);

    /**
     * 微信预支付
     *
     * @param pay_code
     */
    void wxPrePay(String pay_code);


    /**
     * 修改密码通过手机号
     *
     * @param sid
     * @param country
     * @param code
     * @param mobile
     * @param password
     */
    void updatePassByPhone(String sid, String country, String code, String mobile, String password);

    /**
     * 发送验证码
     *
     * @param country   +86
     * @param mobile
     * @param code_type
     */
    void sendVerificationCode(String country, String mobile, String code_type,int appType);

    void registerByPhone(String s, String phone, String code, String pwd1);

    void login(String country, String phone, String pwd, String appName);

    void updateUserMessage(String sid, String uid, String nickName, int sex, String sign, String email, String weixin, String company, String job_title, String loc_country, String loc_area);

    /**
     * 获取App的某些配置信息
     */
    void getAppConfig();

    void feedback(String name, String email, String tel, String uid, String text);

    void wxLogin(String deviceToken, String android, String code);

    void bindPhone(String uid, String phone, String yzm);
}
