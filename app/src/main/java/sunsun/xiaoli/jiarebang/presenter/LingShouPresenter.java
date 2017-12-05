package sunsun.xiaoli.jiarebang.presenter;

import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.volley.BasePresenter;
import com.itboye.pondteam.volley.ICompleteListener;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observer;

import sunsun.xiaoli.jiarebang.interfaces.ILingShouInterface;
import sunsun.xiaoli.jiarebang.responsitory.LingShouResponsitory;


/**
 * Created by admin on 2016/11/28.
 */

public class LingShouPresenter extends BasePresenter implements
        ILingShouInterface<PersonDataBean> {
    public static String getNearStore_success = "_getNearStore_success";
    public static String getNearStore_fail = "_getNearStore_fail";
    public static String storeDetail_success = "_storeDetail_success";
    public static String storeDetail_fail = "_storeDetail_fail";
    public static String getMainClassify_success = "_getMainClassify_success";
    public static String getMainClassify_fail = "_getMainClassify_fail";
    public static String getSeconfClassify_success = "_getSeconfClassify_success";
    public static String getSeconfClassify_fail = "_getSeconfClassify_fail";
    public static String getGoodsList_success = "_getGoodsList_success";
    public static String getGoodsList_fail = "_getGoodsList_fail";
    public static String getKeywordsList_success = "_getKeywordsList_success";
    public static String getKeywordsList_fail = "_getKeywordsList_fail";
    public static String getGoodsDetail_success = "_getGoodsDetail_success";
    public static String getGoodsDetail_fail = "_getGoodsDetail_fail";
    public static String queryOrder_success = "_queryOrder_success";
    public static String queryOrder_fail = "_queryOrder_fail";
    public static String addShopCart_success = "_addShopCart_success";
    public static String addShopCart_fail = "_addShopCart_fail";
    public static String getShopCart_success = "_getShopCart_success";
    public static String getShopCart_fail = "_getShopCart_fail";

    public static String updateShopCart_success = "_updateShopCart_success";
    public static String updateShopCart_fail = "_updateShopCart_success";
    public static String deleteShopCart_success = "_deleteShopCart_success";
    public static String deleteShopCart_fail = "_deleteShopCart_fail";
    public static String shopCart_fail = "_shopCart_fail";
    public static String shopCart_success = "_shopCart_success";
    public static String goodsOrder_success = "_goodsOrder_success";
    public static String goodsOrder_fail = "_goodsOrder_fail";
    public static String queryFreightPrice_fail = "_queryFreightPrice_fail";
    public static String queryFreightPrice_success = "_queryFreightPrice_success";
    public static String shopCartOrder_success = "_shopCartOrder_success";
    public static String shopCartOrder_fail = "_shopCartOrder_fail";
    public static String deleteAddress_success = "_deleteAddress_success";
    public static String deleteAddress_fail = "_deleteAddress_fail";
    public static String addAddress_success = "_addAddress_success";
    public static String addAddress_fail = "_addAddress_fail";
    public static String queryAddress_success = "_queryAddress_success";
    public static String queryAddress_fail = "_queryAddress_fail";
    public static String checkProductCode_success = "_checkProductCode_success";
    public static String checkProductCode_fail = "_checkProductCode_fail";
    public static String updateAddress_success = "_updateAddress_success";
    public static String updateAddress_fail = "_updateAddress_fail";
    public static String getDefaultAddress_success = "_getDefaultAddress_success";
    public static String getDefaultAddress_fail = "_getDefaultAddress_fail";
    public static String rePay_success = "_rePay_success";
    public static String rePay_fail = "_rePay_fail";
    public static String getVerticalArtical_success = "_getVerticalArtical_success";
    public static String getVerticalArtical_fail = "_getVerticalArtical_fail";
    public static String getBanner_success = "_getBanner_success";
    public static String getBanner_fail = "_getBanner_fail";
    public static String getMessageList_success = "_getMessageList_success";
    public static String getMessageList_fail = "_getMessageList_fail";
    public static String getChatDetail_success = "_getChatDetail_success";
    public static String getChatDetail_fail = "_getChatDetail_fail";
    public static String addMessage_success = "_addMessage_success";
    public static String addMessage_fail = "_addMessage_fail";
    public static String getHotSearchGoods_success = "_getHotSearchGoods_success";
    public static String getHotSearchGoods_fail = "_getHotSearchGoods_fail";
    public static String getServiceSku_success = "_getServiceSku_success";
    public static String getServiceSku_fail = "_getServiceSku_fail";
    public static String payTest_success = "_payTest_success";
    public static String payTest_fail = "_payTest_fail";
    public static String getOrderDetail_success = "_getOrderDetail_success";
    public static String getOrderDetail_fail = "_getOrderDetail_fail";
    //申请退款
    public static String tuihuo_success = "_tuihuo_success";
    public static String tuihuo_fail = "_tuihuo_fail";
    //确认收货
    public static String shouhuo_success = "_shouhuo_success";
    public static String shouhuo_fail = "_shouhuo_fail";
    public static String storePingJia_success = "_storePingJia_success";
    public static String storePingJia_fail = "_storePingJia_fail";
    public static String isMerchant_success = "_isMerchant_success";
    public static String isMerchant_fail = "_isMerchant_fail";
    public static String getMyPublish_success = "_getMyPublish_success";
    public static String getMyPublish_fail = "_getMyPublish_fail";
    public static String addArtical_success = "_addArtical_success";
    public static String addArtical_fail = "_addArtical_fail";
    public static String getArticleInfo_succes = "_getArticleInfo_succes";
    public static String getArticleInfo_fail = "_getArticleInfo_fail";
    public static String queryProNo_success = "_queryProNo_success";
    public static String queryProNo_fail = "_queryProNo_fail";
    public static String getSkuPidInConsultBuy_success = "_getSkuPidInConsultBuy_success";
    public static String getSkuPidInConsultBuy_fail = "_getSkuPidInConsultBuy_fail";
    public static String setDefaultAddress_success = "_setDefaultAddress_success";
    public static String setDefaultAddress_fail = "_setDefaultAddress_fail";
    public static String wxPrePay_success = "_wxPrePay_success";
    public static String wxPrePay_fail = "_wxPrePay_fail";
    public static String updateUserMessage_success = "_updateUserMessage_success";
    public static String updateUserMessage_fail = "_updateUserMessage_success";
    public static String getAppConfig_success = "_getAppConfig_success";
    public static String getAppConfig_fail = "_getAppConfig_fail";
    public static String feedback_success = "_feedback_success";
    public static String feedback_fail = "_feedback_fail";
    public static String wxLogin_success = "_wxLogin_success";
    public static String wxLogin_fail = "_wxLogin_fail";
    public static String bindPhone_success = "_bindPhone_success";
    public static String bindPhone_fail = "_bindPhone_fail";
    public static String addCharge_success="_addCharge_success";
    public static String addCharge_fail="_addCharge_fail";


    public LingShouPresenter(Observer observer) {
        super(observer);
    }


    @Override
    public void getNearStore(String city_code, String lng, String lat, String name, String maxDistance,int bygr, int page_index, int page_size) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getNearStore_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getNearStore_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getNearStore(city_code, lng, lat, name, maxDistance,bygr, page_index, page_size);
    }

    @Override
    public void storeDetail(String... seller_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(storeDetail_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(storeDetail_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.storeDetail(seller_id);
    }

    @Override
    public void getMainClassify() {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getMainClassify_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getMainClassify_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getMainClassify();
    }

    @Override
    public void getSeconfClassify(String cate_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getSeconfClassify_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getSeconfClassify_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getSeconfClassify(cate_id);
    }

    @Override
    public void getGoodsList(String order, String cate_id, String keyword, int page_index, int page_size) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getGoodsList_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getGoodsList_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getGoodsList(order, cate_id, keyword, page_index, page_size);
    }

    @Override
    public void getKeywordsList(String keyword) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getKeywordsList_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getKeywordsList_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getKeywordsList(keyword);
    }

    @Override
    public void getGoodsDetail(String... id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getGoodsDetail_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getGoodsDetail_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getGoodsDetail(id);
    }

    @Override
    public void queryOrder(String uid, int query_status, String keyword, int page_index, int page_size, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(queryOrder_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(queryOrder_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.queryOrder(uid, query_status, keyword, page_index, page_size, s_id);
    }

    @Override
    public void addShopCart(String sp, int count, String id, String sku_pkid, String sp1) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(addShopCart_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(addShopCart_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.addShopCart(sp, count, id, sku_pkid, sp1);
    }

    @Override
    public void getShopCart(String sp, int product_type, String sp1) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getShopCart_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getShopCart_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getShopCart(sp, product_type, sp1);
    }

    @Override
    public void updateShopCart(String uid, String id, int currrentCount, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updateShopCart_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updateShopCart_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.updateShopCart(uid, id, currrentCount, s_id);
    }

    @Override
    public void deleteShopCart(String uid, String id, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deleteShopCart_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deleteShopCart_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deleteShopCart(uid, id, s_id);
    }


    @Override
    public void shopCartOrder(String uid, String ids, String address_id, String note, String stores_id, String send_type, String send_time, String freight_price, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(shopCartOrder_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(shopCartOrder_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.shopCartOrder(uid, ids, address_id, note, stores_id, send_type, send_time, freight_price, s_id);
    }

    @Override
    public void goodsOrder(String uid, int count, String sku_pkid, String address_id, String note, String stores_id, String send_type, String send_time, String freight_price, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(goodsOrder_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(goodsOrder_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.goodsOrder(uid, count, sku_pkid, address_id, note, stores_id, send_type, send_time, freight_price, s_id);
    }

    @Override
    public void queryFreightPrice(String stores_id, String address_id, String sp) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(queryFreightPrice_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(queryFreightPrice_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.queryFreightPrice(stores_id, address_id, sp);
    }

    @Override
    public void addAddress(String uid, int is_default, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(addAddress_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(addAddress_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.addAddress(uid, is_default, province, provinceId, city, area, cityid, areaid, detailAddress, name, phone, country_id, country, lng, lat, s_id);
    }

    @Override
    public void queryAddress(String uid, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(queryAddress_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(queryAddress_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.queryAddress(uid, s_id);
    }

    @Override
    public void deleteAddress(String uid, String id, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deleteAddress_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deleteAddress_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deleteAddress(uid, id, s_id);
    }

    @Override
    public void checkProductCode(String uid, String xuliehao, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(checkProductCode_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(checkProductCode_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.checkProductCode(uid, xuliehao, s_id);
    }

    @Override
    public void updateAddress(String sp, String id, int is_default, String province, String provinceId, String city, String area, String cityid, String areaid, String detailAddress, String name, String phone, String country_id, String country, String lng, String lat, String sp1) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updateAddress_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updateAddress_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.updateAddress(sp, id, is_default, province, provinceId, city, area, cityid, areaid, detailAddress, name, phone, country_id, country, lng, lat, sp1);
    }

    @Override
    public void getDefaultAddress(String sp, String sp1) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getDefaultAddress_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getDefaultAddress_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getDefaultAddress(sp, sp1);
    }

    @Override
    public void rePay(String uid, String s_id, String order_code) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(rePay_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(rePay_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.rePay(uid, s_id, order_code);
    }

    @Override
    public void getVerticalArtical(String area, String lng, String lat) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getVerticalArtical_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getVerticalArtical_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getVerticalArtical(area, lng, lat);
    }

    @Override
    public void getBanner(int position) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getBanner_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getBanner_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getBanner(position);
    }

    @Override
    public void getMessageList(String uid, String msgType) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getMessageList_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getMessageList_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getMessageList(uid, msgType);
    }

    @Override
    public void getChatDetail(String uid, String from_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getChatDetail_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getChatDetail_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getChatDetail(uid, from_id);
    }

    @Override
    public void addMessage(String uid, String from_id, String msg_type, String title, String summary, String content, String extra) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(addMessage_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(addMessage_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.addMessage(uid, from_id, msg_type, title, summary, content, extra);
    }

    @Override
    public void getHotSearchGoods() {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getHotSearchGoods_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getHotSearchGoods_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getHotSearchGoods();
    }

    @Override
    public void getServiceSku(int service_type) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getServiceSku_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getServiceSku_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getServiceSku(service_type);
    }

    @Override
    public void payTest(String uid, String pay_code, double pay_money, String sp) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(payTest_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(payTest_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.payTest(uid, pay_code, pay_money, sp);
    }

    @Override
    public void getOrderDetail(String sp, String order_code, String sp1) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getOrderDetail_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getOrderDetail_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getOrderDetail(sp, order_code, sp1);
    }

    @Override
    public void tuiHuo(String uid, String refund_type, String reason, String order_id, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(tuihuo_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(tuihuo_fail);
                        setChanged();
                        notifyObservers(result);
                    }
                });
        user.tuiHuo(uid, refund_type, reason, order_id, s_id);
    }

    @Override
    public void querenShouHuo(String uid, String order_code, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(shouhuo_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(shouhuo_fail);
                        setChanged();
                        notifyObservers(result);
                    }
                });
        user.querenShouHuo(uid, order_code, s_id);
    }

    @Override
    public void storePingJia(String uid, String order_code, float i, String sp1) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(storePingJia_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(storePingJia_fail);
                        setChanged();
                        notifyObservers(result);
                    }
                });
        user.storePingJia(uid, order_code, i, sp1);
    }

    @Override
    public void isMerchant(String uid) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(isMerchant_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(isMerchant_fail);
                        setChanged();
                        notifyObservers(result);
                    }
                });
        user.isMerchant(uid);
    }

    @Override
    public void getMyPublish(String uid) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getMyPublish_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getMyPublish_fail);
                        setChanged();
                        notifyObservers(result);
                    }
                });
        user.getMyPublish(uid);
    }

    @Override
    public void addArtical(String sid, String uid, String title, String imgId, String content) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(addArtical_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(addArtical_fail);
                        setChanged();
                        notifyObservers(result);
                    }
                });
        user.addArtical(sid, uid, title, imgId, content);
    }

    @Override
    public void getArticleInfo(String id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(new ICompleteListener() {
            @Override
            public void success(ResultEntity result) {
                result.setEventTag(Tag_Success);
                result.setEventType(getArticleInfo_succes);
                setChanged();
                notifyObservers(result);
            }

            @Override
            public void failure(ResultEntity result) {
                result.setEventTag(Tag_Error);
                result.setEventType(getArticleInfo_fail);
                setChanged();
                notifyObservers(result);
            }
        });
        user.getArticleInfo(id);
    }

    @Override
    public void queryProNo(String uid, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(new ICompleteListener() {
            @Override
            public void success(ResultEntity result) {
                result.setEventTag(Tag_Success);
                result.setEventType(queryProNo_success);
                setChanged();
                notifyObservers(result);
            }

            @Override
            public void failure(ResultEntity result) {
                result.setEventTag(Tag_Error);
                result.setEventType(queryProNo_fail);
                setChanged();
                notifyObservers(result);
            }
        });
        user.queryProNo(uid, s_id);
    }

    @Override
    public void getSkuPidInConsultBuy(String uid, String sId) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(new ICompleteListener() {
            @Override
            public void success(ResultEntity result) {
                result.setEventTag(Tag_Success);
                result.setEventType(getSkuPidInConsultBuy_success);
                setChanged();
                notifyObservers(result);
            }

            @Override
            public void failure(ResultEntity result) {
                result.setEventTag(Tag_Error);
                result.setEventType(getSkuPidInConsultBuy_fail);
                setChanged();
                notifyObservers(result);
            }
        });
        user.getSkuPidInConsultBuy(uid, sId);
    }

    @Override
    public void setDefaultAddress(String uid, String id, String sId) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(new ICompleteListener() {
            @Override
            public void success(ResultEntity result) {
                result.setEventTag(Tag_Success);
                result.setEventType(setDefaultAddress_success);
                setChanged();
                notifyObservers(result);
            }

            @Override
            public void failure(ResultEntity result) {
                result.setEventTag(Tag_Error);
                result.setEventType(setDefaultAddress_fail);
                setChanged();
                notifyObservers(result);
            }
        });
        user.setDefaultAddress(uid, id, sId);
    }

    @Override
    public void wxPrePay(String pay_code) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(new ICompleteListener() {
            @Override
            public void success(ResultEntity result) {
                result.setEventTag(Tag_Success);
                result.setEventType(wxPrePay_success);
                setChanged();
                notifyObservers(result);
            }

            @Override
            public void failure(ResultEntity result) {
                result.setEventTag(Tag_Error);
                result.setEventType(wxPrePay_fail);
                setChanged();
                notifyObservers(result);
            }
        });
        user.wxPrePay(pay_code);
    }

    public static String update_pass_bymobile_success = "_update_pass_bymobile_success";
    public static String update_pass_bymobile_fail = "_update_pass_bymobile_fail";

    @Override
    public void updatePassByPhone(String sid, String country, String code, String mobile, String password) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(update_pass_bymobile_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(update_pass_bymobile_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.updatePassByPhone(sid, country, code, mobile, password);
    }

    //发送验证码
    public static String send_code_fail = "_Send_code_fail";
    public static String send_code_success = "_Send_code_success";

    @Override
    public void sendVerificationCode(String country, String mobile, String code_type, int appType) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(send_code_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(send_code_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.sendVerificationCode(country, mobile, code_type, appType);
    }

    public static String registerByPhone_success = "_registerByPhone_success";
    public static String registerByPhone_fail = "_registerByPhone_fail";

    @Override
    public void registerByPhone(String s, String username, String code, String password) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(registerByPhone_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(registerByPhone_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }
                });
        user.registerByPhone(s, username, code, password);
    }

    // 用户登录
    public static final String login_success = UserPresenter.class.getName()
            + "_User_success";
    public static final String login_fail = UserPresenter.class.getName()
            + "_User_fail";

    @Override
    public void login(String country, String username, String pwd, String appType) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(login_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(login_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.login(country, username, pwd, appType);
    }

    @Override
    public void updateUserMessage(String sid, String uid, String nickName, int sex, String sign, String email, String weixin, String company, String job_title, String loc_country, String loc_area) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updateUserMessage_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updateUserMessage_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.updateUserMessage(sid, uid, nickName, sex, sign, email, weixin, company, job_title, loc_country, loc_area);
    }

    @Override
    public void getAppConfig() {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getAppConfig_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getAppConfig_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.getAppConfig();
    }

    @Override
    public void feedback(String name, String email, String tel, String uid, String text) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(feedback_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(feedback_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.feedback(name, email, tel, uid, text);
    }

    @Override
    public void wxLogin(String deviceToken, String android, String code) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(wxLogin_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(wxLogin_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.wxLogin(deviceToken, android, code);
    }

    @Override
    public void bindPhone(String uid, String phone, String yzm) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(bindPhone_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(bindPhone_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.bindPhone(uid, phone, yzm);
    }

    @Override
    public void addCharge(String uid, String s_id) {
        ILingShouInterface<PersonDataBean> user = new LingShouResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(addCharge_success);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(addCharge_fail);
                        LingShouPresenter.this.setChanged();
                        LingShouPresenter.this.notifyObservers(result);

                    }
                });
        user.addCharge(uid, s_id);

    }
}
