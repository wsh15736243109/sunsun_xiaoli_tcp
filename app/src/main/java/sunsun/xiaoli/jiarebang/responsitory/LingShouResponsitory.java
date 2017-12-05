package sunsun.xiaoli.jiarebang.responsitory;

import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.bean.ArticalBean;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.bean.MessageListBean;
import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.bean.VertifyBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.volley.BaseErrorListener;
import com.itboye.pondteam.volley.BaseNetRepository;
import com.itboye.pondteam.volley.BaseSuccessReqListener;
import com.itboye.pondteam.volley.ByJsonRequest;
import com.itboye.pondteam.volley.ICompleteListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.beans.AppConfigBean;
import sunsun.xiaoli.jiarebang.beans.ClassifyBean;
import sunsun.xiaoli.jiarebang.beans.CreateOrderBean;
import sunsun.xiaoli.jiarebang.beans.FreightPriceBean;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.beans.WxPrePayBean;
import sunsun.xiaoli.jiarebang.beans.XuLieNoModel;
import sunsun.xiaoli.jiarebang.interfaces.ILingShouInterface;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.MyPublishBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.OrderDetailBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.RePayBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.SureReceiveGoodsBean;

import static com.itboye.pondteam.utils.Const.getDeviceToken;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;


/**
 * Created by admin on 2016/11/28.
 */

public class LingShouResponsitory extends BaseNetRepository implements
        ILingShouInterface<PersonDataBean> {

    String api = "100";
    private String getNearStore = "By_Stores_queryNearby";// 附近商家
    private String storeDetail = "By_Seller_detail";//商家详情
    private String getMainClassify = "By_Category_queryMainCategory";//获取主要商品分类
    private String getSeconfClassify = "By_Category_querySubCategory";//获取二级分类
    private String getGoodsList = "By_Product_search";//获取商品列表
    private String getKeywordsList = "By_Product_searchKeywords";//获取关键词列表
    private String getGoodsDetail = "By_Product_detail";//商品详情
    private String queryOrder = "By_Order_query";//
    private String addShopCart = "By_ShoppingCart_add";//加入购物车
    private String getShopCart = "By_ShoppingCart_query";//查询购物车
    private String updateShopCart = "By_ShoppingCart_update";//修改购物车
    private String deleteShopCart = "By_ShoppingCart_delete";//刪除购物车
    private String shopCartOrder = "By_Order_create";//购物车生成订单
    private String goodsOrder = "By_Order_createNow";//立即购买生成订单
    private String queryFreightPrice = "By_Order_freightPrice";//计算运费
    private String addAddress = "By_Address_add";//添加地址
    private String queryAddress = "By_Address_query";//查询地址
    private String deleteAddress = "By_Address_delete";//删除地址
    private String checkProductCode = "By_Order_checkProductCode";//验证序列号
    private String updateAddress = "By_Address_update";//修改地址
    private String getDefaultAddress = "By_Address_getDefault";//获取默认地址
    private String rePay = "By_Order_OrdertoPayCode";//订单重新支付
    private String getVerticalArtical = "By_Stores_getArt";//获取垂直滚动文章
    private String getBanner = "By_Banners_query";//查询轮播图
    private String getMessageList = "By_Message_messageList";//查询留言板信息
    private String getChatDetail = "By_Message_messageToUser";
    private String addMessage = "By_Message_add";//添加消息
    private String getHotSearchGoods = "By_Product_hotsearch";
    private String getServiceSku = "By_Product_serviceSku";
    private String payTest = "By_Order_payOrder";
    private String getOrderDetail = "By_Order_detail";
    private String tuihuo = "By_Order_refundApply";//申请退货
    private String shouhuo = "By_Order_receiveGoods";//确认收货
    private String storePingJia = "By_Order_evaluate";//订单评价
    private String isMerchant = "By_Stores_isStores";//是否为商家
    private String getMyPublish = "By_Stores_storesArt";//我的发布
    private String addArtical = "By_Stores_addArt";
    private String ArticleInfo = "By_Stores_ArtDetail";
    private String queryProNo = "By_Order_modellist";
    private String getSkuPidInConsultBuy = "By_Order_consultBuy";//咨询购买前置
    private String setDefaultAddress = "By_Address_setDefault";//设置默认地址
    private String wxPrePay = "By_Wxpay_prePay";//微信预支付

    private final ByJsonRequest.Builder byjsonRequest;
    private String updateUserMessage = "By_User_update";
    private String getAppConfig = "By_Config_app";
    private String feedback = "By_Suggest_add";
    private String wxLogin = "By_Weixin_login";
    private String bindPhone = "By_User_bind";
    private String addCharge = "By_Order_addCharge";

    public LingShouResponsitory(ICompleteListener iCompleteListener) {
        super(iCompleteListener);
        byjsonRequest = new ByJsonRequest.Builder();
        byjsonRequest.setBaseWrapUrl(Const.lingshou_wrapUrl);
    }


    @Override
    public void getNearStore(String city_code, String lng, String lat, String name, String maxDistance, int page_index, int page_size) {
        Type type = new TypeToken<StoreListBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("city_code", city_code);
        map.put("lng", lng);
        map.put("lat", lat);
        map.put("name", name);
        map.put("maxDistance", maxDistance);
        map.put("page_index", page_index);
        map.put("page_size", page_size);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getNearStore, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<StoreListBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void storeDetail(String... seller_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("seller_id", seller_id[0]);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(storeDetail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getMainClassify() {
        Type type = new TypeToken<ArrayList<ClassifyBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getMainClassify, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<ClassifyBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getSeconfClassify(String cate_id) {
        Type type = new TypeToken<GoodsListBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("cate_id", cate_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getSeconfClassify, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<GoodsListBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getGoodsList(String order, String cate_id, String keyword, int page_index, int page_size) {
        Type type = new TypeToken<GoodsListBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("order", order);
        map.put("cate_id", cate_id);
        map.put("keyword", keyword);
        map.put("page_index", page_index);
        map.put("page_size", page_size);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getGoodsList, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<GoodsListBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getKeywordsList(String keyword) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getKeywordsList, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getGoodsDetail(String... id) {
        Type type = new TypeToken<GoodsDetailBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id[0]);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getGoodsDetail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<GoodsDetailBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void queryOrder(String uid, int query_status, String keyword, int page_index, int page_size, String s_id) {
        Type type = new TypeToken<OrderBean>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("query_status", query_status);
        map.put("keyword", keyword);
        map.put("page_index", page_index);
        map.put("page_size", page_size);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(queryOrder, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<OrderBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addShopCart(String uid, int count, String id, String sku_pkid, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("count", count);
        map.put("id", id);
        map.put("sku_pkid", sku_pkid);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(addShopCart, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getShopCart(String uid, int product_type, String s_id) {
        Type type = new TypeToken<ArrayList<ShopCartBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("product_type", product_type);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getShopCart, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<ShopCartBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateShopCart(String uid, String id, int currrentCount, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("id", id);
        map.put("count", currrentCount);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updateShopCart, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deleteShopCart(String uid, String id, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("id", id);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deleteShopCart, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    @Override
    public void shopCartOrder(String uid, String ids, String address_id, String note, String stores_id, String send_type, String send_time, String freight_price, String s_id) {
        Type type = new TypeToken<RePayBean>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("ids", ids);
        map.put("address_id", address_id);
        map.put("note", note);
        map.put("stores_id", stores_id);
        map.put("send_type", send_type);
        map.put("send_time", send_time);
        map.put("freight_price", freight_price);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(shopCartOrder, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<RePayBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void goodsOrder(String uid, int count, String sku_pkid, String address_id, String note, String stores_id, String send_type, String send_time, String freight_price, String s_id) {
        Type type = new TypeToken<CreateOrderBean>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("count", count);
        map.put("sku_pkid", sku_pkid);
        map.put("address_id", address_id);
        map.put("note", note);
        map.put("stores_id", stores_id);
        map.put("send_type", send_type);
        map.put("send_time", send_time);
        map.put("freight_price", freight_price);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(goodsOrder, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<CreateOrderBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void queryFreightPrice(String stores_id, String address_id, String s_id) {
        Type type = new TypeToken<FreightPriceBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", getSp(Const.UID));
        map.put("stores_id", stores_id);
        map.put("s_id", s_id);
        map.put("address_id", address_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(queryFreightPrice, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<FreightPriceBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addAddress(String uid, int is_default, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("province", province);
        map.put("provinceid", provinceId);
        map.put("city", city);
        map.put("area", area);
        map.put("cityid", cityid);
        map.put("areaid", areaid);
        map.put("detailinfo", detailAddress);
        map.put("contactname", name);
        map.put("mobile", phone);
        map.put("country_id", country_id);
        map.put("country", country);
//        map.put("lng", lng);
//        map.put("lat", lat);
        map.put("default", is_default);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(addAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void queryAddress(String uid, String s_id) {
        Type type = new TypeToken<ArrayList<AddressBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(queryAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<AddressBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deleteAddress(String uid, String id, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("id", id);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deleteAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void checkProductCode(String uid, String xuliehao, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("product_code", xuliehao);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(checkProductCode, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateAddress(String sp, String id, int is_default, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String sp1) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", sp);
        map.put("id", id);
        map.put("default", is_default);
        map.put("province", province);
        map.put("provinceid", provinceId);
        map.put("city", city);
        map.put("area", area);
        map.put("cityid", cityid);
        map.put("area", area);
        map.put("detailinfo", detailAddress);
        map.put("contactname", name);
        map.put("mobile", phone);
        map.put("country_id", country_id);
        map.put("country", country);
        map.put("lng", lng);
        map.put("lat", lat);
        map.put("s_id", sp1);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updateAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDefaultAddress(String sp, String sp1) {
        Type type = new TypeToken<ArrayList<AddressBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", sp);
        map.put("s_id", sp1);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getDefaultAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<AddressBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void rePay(String uid, String s_id, String order_code) {
        Type type = new TypeToken<CreateOrderBean>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("s_id", s_id);
        map.put("order_code", order_code);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(rePay, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<CreateOrderBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getVerticalArtical(String area, String lng, String lat) {
        Type type = new TypeToken<ArticalBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("area", area);
        map.put("lng", lng);
        map.put("lat", lat);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getVerticalArtical, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArticalBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getBanner(int position) {
        Type type = new TypeToken<ArrayList<BannerBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("position", position);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getBanner, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<BannerBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getMessageList(String uid, String msgType) {
        Type type = new TypeToken<ArrayList<MessageListBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("msg_type", msgType);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getMessageList, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<MessageListBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getChatDetail(String uid, String from_id) {
        Type type = new TypeToken<ArrayList<MessageListBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("from_id", from_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getChatDetail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<MessageListBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addMessage(String uid, String from_id, String msg_type, String title, String summary, String content, String extra) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("to_uid", from_id);
        map.put("msg_type", msg_type);
        map.put("title", title);
        map.put("summary", summary);
        map.put("content", content);
        map.put("extra", extra);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(addMessage, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getHotSearchGoods() {
        Type type = new TypeToken<GoodsListBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getHotSearchGoods, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<GoodsListBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getServiceSku(int service_type) {
        Type type = new TypeToken<ArrayList<ServiceBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("service_type", service_type);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getServiceSku, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<ServiceBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void payTest(String uid, String pay_code, double pay_money, String s_id) {
        Type type = new TypeToken<CreateOrderBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("pay_code", pay_code);
        map.put("money", pay_money);
        map.put("uid", uid);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(payTest, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<CreateOrderBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getOrderDetail(String sp, String order_code, String sp1) {
        Type type = new TypeToken<OrderDetailBean>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", sp);
        map.put("order_code", order_code);
        map.put("s_id", sp1);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getOrderDetail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<OrderDetailBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void tuiHuo(String uid, String refund_type, String reason, String order_id, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("refund_type", refund_type);
        map.put("reason", reason);
        map.put("order_id", order_id);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(tuihuo, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
     * 确认收货
     */
    @Override
    public void querenShouHuo(String uid, String order_code, String s_id) {
        Type type = new TypeToken<SureReceiveGoodsBean>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("order_code", order_code);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(shouhuo, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<SureReceiveGoodsBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void storePingJia(String uid, String order_code, float evaluate, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("order_code", order_code);
        map.put("evaluate", evaluate);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(storePingJia, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void isMerchant(String uid) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(isMerchant, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getMyPublish(String uid) {
        Type type = new TypeToken<MyPublishBean>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getMyPublish, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<MyPublishBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addArtical(String sid, String uid, String title, String imgId, String content) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("sid", sid);
        map.put("title", title);
        map.put("img", imgId);
        map.put("detail", content);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(addArtical, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getArticleInfo(String id) {

        Type type = new TypeToken<MyPublishBean.PublishBean>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(ArticleInfo, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<MyPublishBean.PublishBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void queryProNo(String uid, String s_id) {
        Type type = new TypeToken<ArrayList<XuLieNoModel>>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", s_id);
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(queryProNo, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<XuLieNoModel>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getSkuPidInConsultBuy(String uid, String sId) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sId);
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getSkuPidInConsultBuy, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void setDefaultAddress(String sp, String id, String sp1) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sp1);
        map.put("uid", sp);
        map.put("id", id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(setDefaultAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void wxPrePay(String pay_code) {
        Type type = new TypeToken<WxPrePayBean>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("pay_code", pay_code);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(wxPrePay, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<WxPrePayBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    private String updatePassByPhone = "By_User_updatePwd";//通过手机号码改密码

    @Override
    public void updatePassByPhone(String sid, String country, String code, String mobile, String password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sid);
        map.put("country", country);
        map.put("code", code);
        map.put("mobile", mobile);
        map.put("password", password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updatePassByPhone, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
     * 发送验证码
     *
     * @param country   +86
     * @param mobile
     * @param code_type
     */

    private String sendVertificationCode = "By_SecurityCode_send";//发送验证码

    @Override
    public void sendVerificationCode(String country, String mobile, String code_type, int appType) {
//        if (appType == 1) {
//            s = VertifyBean.class;
//        }
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("country", country);
        map.put("mobile", mobile);
        map.put("code_type", code_type);
        /*appType==1：零售   appType==0 小鲤*/
        map.put("send_type", code_type);
        Type type = new TypeToken<String>() {
        }.getType();
        if (appType == 1) {
            type = new TypeToken<VertifyBean>() {
            }.getType();
            byjsonRequest
                    .setTypeVerParamsAndReturnClass(sendVertificationCode, apiVer, map, type)
                    .requestListener(
                            new BaseSuccessReqListener<VertifyBean>(
                                    getListener()))
                    .errorListener(new BaseErrorListener(getListener()))
                    .desEncodeThenBuildAndSend();
        } else {
            byjsonRequest
                    .setTypeVerParamsAndReturnClass(sendVertificationCode, apiVer, map, type)
                    .requestListener(
                            new BaseSuccessReqListener<String>(
                                    getListener()))
                    .errorListener(new BaseErrorListener(getListener()))
                    .desEncodeThenBuildAndSend();
        }
    }

    private String registerByPhone = "By_User_register";

    @Override
    public void registerByPhone(String s, String username, String code, String password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "102";
        Map<String, Object> map = new HashMap<>();
        map.put("country", s);
        map.put("username", username);
        map.put("code", code);
        map.put("password", password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(registerByPhone, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    private String loginTypeKey = "By_User_login";// 用户登录

    @Override
    public void login(String country, String username, String pwd, String appType) {
        Type type = new TypeToken<PersonDataBean>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", pwd);
        map.put("device_type", "android");
        map.put("country", country);
//        map.put("role","role_skilled_worker");
        map.put("device_token", getDeviceToken());


        String key = loginTypeKey;
        if (appType.equals("森森新零售")) {
            key = loginTypeKey;
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(key, "104", map, type)
                .requestListener(
                        new BaseSuccessReqListener<PersonDataBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateUserMessage(String sid, String uid, String nickName, int sex, String sign, String email, String weixin, String company, String job_title, String loc_country, String loc_area) {
        Type type = new TypeToken<String>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sid);
        map.put("uid", uid);
        map.put("nickname", nickName);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updateUserMessage, "100", map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getAppConfig() {
        Type type = new TypeToken<AppConfigBean>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getAppConfig, "100", map, type)
                .requestListener(
                        new BaseSuccessReqListener<AppConfigBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void feedback(String name, String email, String tel, String uid, String text) {
        Type type = new TypeToken<String>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("tel", tel);
        map.put("uid", uid);
        map.put("text", text);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(feedback, "100", map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void wxLogin(String deviceToken, String android, String code) {
        Type type = new TypeToken<PersonDataBean>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        map.put("device_token", deviceToken);
        map.put("device_type", android);
        map.put("code", code);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(wxLogin, "100", map, type)
                .requestListener(
                        new BaseSuccessReqListener<PersonDataBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void bindPhone(String uid, String phone, String yzm) {
        Type type = new TypeToken<String>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("mobile", phone);
        map.put("code", yzm);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(bindPhone, "100", map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addCharge(String uid, String s_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("s_id", s_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(addCharge, "101", map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }
}
