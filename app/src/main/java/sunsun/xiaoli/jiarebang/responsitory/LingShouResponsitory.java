package sunsun.xiaoli.jiarebang.responsitory;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.bean.ArticalBean;
import com.itboye.pondteam.bean.BannerBean;
import com.itboye.pondteam.bean.MessageListBean;
import com.itboye.pondteam.bean.PersonDataBean;
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
import sunsun.xiaoli.jiarebang.beans.ClassifyBean;
import sunsun.xiaoli.jiarebang.beans.CreateOrderBean;
import sunsun.xiaoli.jiarebang.beans.FreightPriceBean;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.interfaces.ILingShouInterface;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.MyPublishBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.OrderDetailBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.RePayBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.SureReceiveGoodsBean;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


/**
 * Created by admin on 2016/11/28.
 */

public class LingShouResponsitory extends BaseNetRepository implements
        ILingShouInterface<PersonDataBean> {

    String api="100";
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
    private String getOrderDetail="By_Order_detail";
    private String tuihuo="By_Order_refundApply";//申请退货
    private String shouhuo="By_Order_receiveGoods";//确认收货
    private String storePingJia="By_Order_evaluate";//订单评价
    private String isMerchant="By_Stores_isStores";//是否为商家
    private String getMyPublish="By_Stores_storesArt";//我的发布
    private String addArtical="By_Stores_addArt";
    private String ArticleInfo="By_Stores_ArtDetail";

    public LingShouResponsitory(ICompleteListener iCompleteListener) {
        super(iCompleteListener);
    }

    public String getDeviceToken() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
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
        (new ByJsonRequest.Builder<StoreListBean>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<ArrayList<ClassifyBean>>())
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
        (new ByJsonRequest.Builder<GoodsListBean>())
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
        (new ByJsonRequest.Builder<GoodsListBean>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<GoodsDetailBean>())
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
        (new ByJsonRequest.Builder<OrderBean>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<ArrayList<ShopCartBean>>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<RePayBean>())
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
        (new ByJsonRequest.Builder<CreateOrderBean>())
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
        (new ByJsonRequest.Builder<FreightPriceBean>())
                .setTypeVerParamsAndReturnClass(queryFreightPrice, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<FreightPriceBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addAddress(String uid, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String s_id) {
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
        map.put("lng", lng);
        map.put("lat", lat);
        map.put("default", 0);
        map.put("s_id", s_id);
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<ArrayList<AddressBean>>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<String>())
                .setTypeVerParamsAndReturnClass(updateAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDefaultAddress(String sp, String sp1) {
        Type type = new TypeToken<AddressBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", sp);
        map.put("s_id", sp1);
        (new ByJsonRequest.Builder<AddressBean>())
                .setTypeVerParamsAndReturnClass(getDefaultAddress, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<AddressBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void rePay(String uid, String s_id, String order_code) {
        Type type = new TypeToken<RePayBean>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("s_id", s_id);
        map.put("order_code", order_code);
        (new ByJsonRequest.Builder<RePayBean>())
                .setTypeVerParamsAndReturnClass(rePay, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<RePayBean>(
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
        (new ByJsonRequest.Builder<ArticalBean>())
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
        (new ByJsonRequest.Builder<ArrayList<BannerBean>>())
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
        (new ByJsonRequest.Builder<ArrayList<MessageListBean>>())
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
        (new ByJsonRequest.Builder<ArrayList<MessageListBean>>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<GoodsListBean>())
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
        (new ByJsonRequest.Builder<ArrayList<ServiceBean>>())
                .setTypeVerParamsAndReturnClass(getServiceSku, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<ServiceBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void payTest(String uid,String pay_code, double pay_money, String s_id) {
        Type type = new TypeToken<CreateOrderBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("pay_code", pay_code);
        map.put("money", pay_money);
        map.put("uid", uid);
        map.put("s_id", s_id);
        (new ByJsonRequest.Builder<CreateOrderBean>())
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
        (new ByJsonRequest.Builder<OrderDetailBean>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<SureReceiveGoodsBean>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<String>())
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
        (new ByJsonRequest.Builder<MyPublishBean>())
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
        (new ByJsonRequest.Builder<String>())
                .setTypeVerParamsAndReturnClass(addArtical, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getArticleInfo(String id) {

        Type type =new TypeToken<MyPublishBean.PublishBean>(){
        }.getType();
        String apiVer =api;
        Map<String,Object> map =new HashMap<>();
        map.put("id",id);
        (new ByJsonRequest.Builder<MyPublishBean.PublishBean>())
                .setTypeVerParamsAndReturnClass(ArticleInfo, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<MyPublishBean.PublishBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }
}
