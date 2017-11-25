package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.kevin.wraprecyclerview.WrapRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.adapter.lingshouadapter.MakeSureOrderAdapter;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.beans.CreateOrderBean;
import sunsun.xiaoli.jiarebang.beans.FreightPriceBean;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.ChooseStoreActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.ChooseTimeActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home.PayTypeActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressListActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.RePayBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

/**
 * 预约安装
 * 鱼缸清理
 * 活体购买
 * 造景装饰
 * 咨询购买
 * 单商品立即购买
 * 购物车商品（多商品购买）
 * 订单重新支付
 */
public class MakeSureOrderActivity extends LingShouBaseActivity implements Observer {

    WrapRecyclerView order_goods_list;
    TranslucentActionBar actionBar;
    //    ArrayList<GoodsDetailBean> model;
    Button btn_sure_pay, btn_addshopcart;
    TextView btn_contacttime, txt_choosestore;
    LingShouPresenter lingShouPresenter;
    RelativeLayout re_addess;
    boolean isShopCart;
    private String address_id = "";
    private ArrayList<AddressBean> addressBean = new ArrayList<>();
    private AddressBean selectAddressBean = new AddressBean();
    TextView txt_name, txt_phone, txt_address, txt_mnoren;
    private StoreListBean.ListEntity storeBean;
    ImageView iv_actionbar_left;
    boolean isGoodsBuy;
    BuyType buyType;
    private ServiceBean serviceBean;
    private MakeSureOrderAdapter adapter;
    private ServiceBean.SkuInfoEntity skuInfoSelect;
    private ArrayList<GoodsDetailBean> goodsDetailBeanArray;
    List<ServiceBean.SkuInfoEntity> skuInfoEntityList;
    private App mApp;
    int canPack;
    private String skuPid = "";
    RelativeLayout re_note;
    EditText edt_note;
    private TextView txt_peisong;
    private OrderBean.ListEntity entityTemp;
    ArrayList<ShopCartBean> ar;
    TextView txt_totalprice;
    double totalPrice = 0;//总金额，包括所有费用

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make_sure_order;
    }

    @Override
    protected void initData() {
        mApp = (App) getApplication();
        mApp.makeSureActivity = this;
        lingShouPresenter = new LingShouPresenter(this);
        lingShouPresenter.getDefaultAddress(getSp(Const.UID), getSp(Const.S_ID));
        initTitlebarStyle1(this, actionBar, "确认订单", R.mipmap.ic_left_light, "", 0, "");
        goodsDetailBeanArray = (ArrayList<GoodsDetailBean>) getIntent().getSerializableExtra("model");
        isShopCart = getIntent().getBooleanExtra("isShopCart", false);
        if (isShopCart) {
            btn_addshopcart.setVisibility(View.GONE);
        } else {
            btn_addshopcart.setVisibility(View.VISIBLE);
        }
        buyType = (BuyType) getIntent().getSerializableExtra("type");
        storeBean = (StoreListBean.ListEntity) getIntent().getSerializableExtra("zixunModel");
        canPack = getIntent().getIntExtra("canPack", 0);
        send_time = getIntent().getStringExtra("send_time");
        isGoodsBuy = getIntent().getBooleanExtra("isGoodsBuy", false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        order_goods_list.setLayoutManager(layoutManager);


        View footView = LayoutInflater.from(this).inflate(R.layout.make_sure_order_footer, null);
        btn_contacttime = (TextView) footView.findViewById(R.id.btn_contacttime);
        btn_contacttime.setOnClickListener(this);
        txt_choosestore = (TextView) footView.findViewById(R.id.txt_choosestore);
        txt_choosestore.setOnClickListener(this);
        txt_peisong = (TextView) footView.findViewById(R.id.txt_peisong);
        re_note = (RelativeLayout) footView.findViewById(R.id.re_note);
        edt_note = (EditText) footView.findViewById(R.id.edt_note);
        switch (buyType) {
            case Buy_YuYueAnZhuang://预约购买
                serviceBean = (ServiceBean) getIntent().getSerializableExtra("selectValue");
                store_id = serviceBean.getProduct_info().getStore_id();

                skuInfoSelect = new ServiceBean.SkuInfoEntity();
                if (serviceBean != null) {
                    if (serviceBean.getSku_info() != null) {
                        if (serviceBean.getSku_info().size() > 0) {
                            skuInfoSelect = serviceBean.getSku_info().get(0);
                            skuPid = serviceBean.getSku_info().get(0).getId();
                            totalPrice = Double.parseDouble(serviceBean.getSku_info().get(0).getPrice());
//                            skuInfoSelect.set;
                        }
                    }
                }
                skuInfoEntityList = new ArrayList<>();
                skuInfoEntityList.add(skuInfoSelect);
                adapter = new MakeSureOrderAdapter(this, skuInfoEntityList);
                adapter.setExtraModel(serviceBean);
                order_goods_list.setAdapter(adapter);
                break;
            case Buy_YuGangQingLi: //鱼缸清理
                serviceBean = (ServiceBean) getIntent().getSerializableExtra("selectValue");
                skuInfoSelect = new ServiceBean.SkuInfoEntity();
                if (serviceBean != null) {
                    for (ServiceBean.SkuInfoEntity skuInfoEntity : serviceBean.getSku_info()) {
                        if (skuInfoEntity.isSelect()) {
                            skuInfoSelect = skuInfoEntity;
                            totalPrice = Double.parseDouble(skuInfoSelect.getPrice());
                            break;
                        }
                    }
                }
                skuInfoEntityList = new ArrayList<>();
                skuInfoEntityList.add(skuInfoSelect);
                adapter = new MakeSureOrderAdapter(this, skuInfoEntityList);
                adapter.setExtraModel(serviceBean);
                order_goods_list.setAdapter(adapter);
                break;
            case Buy_HuoTiGouMai: //活体购买
                serviceBean = (ServiceBean) getIntent().getSerializableExtra("selectValue");
                skuInfoSelect = new ServiceBean.SkuInfoEntity();
                if (serviceBean != null) {
                    for (ServiceBean.SkuInfoEntity skuInfoEntity : serviceBean.getSku_info()) {
                        if (skuInfoEntity.isSelect()) {
                            skuInfoSelect = skuInfoEntity;
                            totalPrice = Double.parseDouble(skuInfoSelect.getPrice());
                            break;
                        }
                    }
                }
                skuInfoEntityList = new ArrayList<>();
                skuInfoEntityList.add(skuInfoSelect);
                adapter = new MakeSureOrderAdapter(this, skuInfoEntityList);
                adapter.setExtraModel(serviceBean);
                order_goods_list.setAdapter(adapter);
                break;
            case Buy_ZaoJingZhuangShi://造景装饰
                serviceBean = (ServiceBean) getIntent().getSerializableExtra("selectValue");
                skuInfoSelect = new ServiceBean.SkuInfoEntity();
                if (serviceBean != null) {
                    for (ServiceBean.SkuInfoEntity skuInfoEntity : serviceBean.getSku_info()) {
                        if (skuInfoEntity.isSelect()) {
                            skuInfoSelect = skuInfoEntity;
                            totalPrice = Double.parseDouble(skuInfoSelect.getPrice());
                            break;
                        }
                    }
                }
                skuInfoEntityList = new ArrayList<>();
                skuInfoEntityList.add(skuInfoSelect);
                adapter = new MakeSureOrderAdapter(this, skuInfoEntityList);
                adapter.setExtraModel(serviceBean);
                order_goods_list.setAdapter(adapter);
                break;
            case Buy_ShopCart: //添加购物车进入确认订单
                ar = (ArrayList<ShopCartBean>) getIntent().getSerializableExtra("model");
                adapter = new MakeSureOrderAdapter(this, ar);
                order_goods_list.setAdapter(adapter);
                for (ShopCartBean shopCartBean : ar) {
                    totalPrice += shopCartBean.getPrice() * Integer.parseInt(shopCartBean.getCount());
                }
                break;
            case Buy_OrderPay: //订单重新支付进入确认订单

                break;
            case Buy_LiJiGouMai://立即购买进入确认订单
                store_id = getIntent().getStringExtra("store_id");
                goodsDetailBeanArray = (ArrayList<GoodsDetailBean>) getIntent().getSerializableExtra("model");
                adapter = new MakeSureOrderAdapter(this, goodsDetailBeanArray);
                order_goods_list.setAdapter(adapter);
                for (GoodsDetailBean goodsDetailBean : goodsDetailBeanArray) {
                    totalPrice += (goodsDetailBean.getPrice());
                }
                break;
            case Buy_ZiXunGouMai:
                re_note.setVisibility(View.VISIBLE);
                serviceBean = (ServiceBean) getIntent().getSerializableExtra("selectValue");
                if (serviceBean != null) {
                    for (ServiceBean.SkuInfoEntity skuInfoEntity : serviceBean.getSku_info()) {
                        if (skuInfoEntity.isSelect()) {
                            skuInfoSelect = skuInfoEntity;
                            totalPrice = Double.parseDouble(skuInfoSelect.getPrice());
                            break;
                        }
                    }
                }
                btn_contacttime.setText(send_time);
                txt_choosestore.setText(storeBean.getName());
                setDrawable();
                store_id = storeBean.getId();
                skuPid = skuInfoSelect.getId();
                btn_addshopcart.setVisibility(View.GONE);
                adapter = new MakeSureOrderAdapter(this, skuInfoEntityList);
                order_goods_list.setAdapter(adapter);
                //获取咨询购买的skuPid
                lingShouPresenter.getSkuPidInConsultBuy(getSp(Const.UID), getSp(Const.S_ID));
                break;
        }
        order_goods_list.addFooterView(footView);
        txt_totalprice.setText(Html.fromHtml("合计： <font color='red'>￥" + totalPrice / 100 + "</font>"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.makeSureActivity = null;
    }

    String note = "";
    String store_id = "";
    String send_type = "1";
    String send_time = "";
    String freight_price = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_sure_pay:
                switch (buyType) {
                    case Buy_YuYueAnZhuang://预约购买
                        if (address_id.equals("")) {
                            MAlert.alert("请选择收货地址");
                            return;
                        }
                        if (send_time == null || "".equals(send_time)) {
                            MAlert.alert("请选择配送时间");
                            return;
                        }
                        if (store_id == null || "".equals(store_id)) {
                            MAlert.alert("请选择商家");
                            return;
                        }
                        if (skuPid == null || "".equals(skuPid)) {
                            MAlert.alert("商品规格有误");
                            return;
                        }
                        lingShouPresenter.goodsOrder(getSp(Const.UID), 1, skuInfoSelect.getId(), address_id, note, store_id, send_type, send_time, freight_price, getSp(Const.S_ID));
                        break;
                    case Buy_YuGangQingLi: //鱼缸清理
                        if (address_id.equals("")) {
                            MAlert.alert("请选择收货地址");
                            return;
                        }
                        if (send_time == null || "".equals(send_time)) {
                            MAlert.alert("请选择配送时间");
                            return;
                        }
                        if (store_id == null || "".equals(store_id)) {
                            MAlert.alert("请选择商家");
                            return;
                        }
                        lingShouPresenter.goodsOrder(getSp(Const.UID), 1, skuInfoSelect.getId(), address_id, note, store_id, send_type, send_time, freight_price, getSp(Const.S_ID));
                        break;
                    case Buy_HuoTiGouMai: //活体购买
                        if (address_id.equals("")) {
                            MAlert.alert("请选择收货地址");
                            return;
                        }
                        if (send_time == null || "".equals(send_time)) {
                            MAlert.alert("请选择配送时间");
                            return;
                        }
                        if (store_id == null || "".equals(store_id)) {
                            MAlert.alert("请选择商家");
                            return;
                        }

                        lingShouPresenter.goodsOrder(getSp(Const.UID), 1, skuInfoSelect.getId(), address_id, note, store_id, send_type, send_time, freight_price, getSp(Const.S_ID));
                        break;
                    case Buy_ZaoJingZhuangShi://造景装饰
                        if (address_id.equals("")) {
                            MAlert.alert("请选择收货地址");
                            return;
                        }
                        if (send_time == null || "".equals(send_time)) {
                            MAlert.alert("请选择配送时间");
                            return;
                        }
                        if (store_id == null || "".equals(store_id)) {
                            MAlert.alert("请选择商家");
                            return;
                        }

                        lingShouPresenter.goodsOrder(getSp(Const.UID), 1, skuInfoSelect.getId(), address_id, note, store_id, send_type, send_time, freight_price, getSp(Const.S_ID));
                        break;
                    case Buy_ShopCart: //添加购物车进入确认订单
                        if (address_id.equals("")) {
                            MAlert.alert("请选择收货地址");
                            return;
                        }
                        if (send_time == null || "".equals(send_time)) {
                            MAlert.alert("请选择配送时间");
                            return;
                        }
                        if (store_id == null || "".equals(store_id)) {
                            MAlert.alert("请选择商家");
                            return;
                        }
                        StringBuffer ids = new StringBuffer();
                        ArrayList<ShopCartBean> ar = (ArrayList<ShopCartBean>) getIntent().getSerializableExtra("model");
                        for (int i = 0; i < ar.size(); i++) {
                            if (ar.get(i).isSelect()) {
                                ids.append(ar.get(i).getId() + ",");
                            }
                        }
                        if (ids.toString().endsWith(",")) {
                            ids = new StringBuffer(ids.toString().substring(0, ids.length() - 1));
                        }
                        lingShouPresenter.shopCartOrder(getSp(Const.UID), ids.toString(), address_id, note, store_id, send_type, send_time, freight_price, getSp(Const.S_ID));
                        break;
                    case Buy_OrderPay: //订单重新支付进入确认订单
                        break;
                    case Buy_LiJiGouMai://立即购买进入确认订单
//                        if (address_id.equals("")) {
//                            MAlert.alert("请选择收货地址");
//                            return;
//                        }
//                        if (send_time == null || "".equals(send_time)) {
//                            MAlert.alert("请选择配送时间");
//                            return;
//                        }
//                        if (store_id == null || "".equals(store_id)) {
//                            MAlert.alert("请选择商家");
//                            return;
//                        }
                        lingShouPresenter.goodsOrder(getSp(Const.UID), goodsDetailBeanArray.get(0).getCount(), goodsDetailBeanArray.get(0).getSku_list().get(goodsDetailBeanArray.get(0).getSelectPositon()).getSku_pkid(), address_id, note, store_id, send_type, send_time, Double.parseDouble(freight_price) * 100 + "", getSp(Const.S_ID));
                        break;
                    case Buy_ZiXunGouMai:
                        if (address_id.equals("")) {
                            MAlert.alert("请选择收货地址");
                            return;
                        }
                        if (send_time == null || "".equals(send_time)) {
                            MAlert.alert("请选择配送时间");
                            return;
                        }
                        if (store_id == null || "".equals(store_id)) {
                            MAlert.alert("请选择商家");
                            return;
                        }
                        if (skuPid == null || "".equals(skuPid)) {
                            MAlert.alert("商品规格有误");
                            return;
                        }
                        if (freight_price.equals("")) {
                            MAlert.alert("运费有误，请重新选择地址");
                            return;
                        }
                        note = edt_note.getText().toString();
                        lingShouPresenter.goodsOrder(getSp(Const.UID), 1, skuPid, address_id, note, store_id, send_type, send_time, freight_price, getSp(Const.S_ID));
                        break;
                }
//                lingShouPresenter.buyLiJi(getSp(Const.UID),count,model.get(0).getSku_list().get(model.get(0).getSelectPositon()).getSku_pkid(),address_id,note,store_id,send_type,send_time);
                break;
            case R.id.btn_addshopcart:
                if (goodsDetailBeanArray != null) {
                    //商品详情进入加入购物车
                    lingShouPresenter.addShopCart(getSp(Const.UID), goodsDetailBeanArray.get(0).getCount(), goodsDetailBeanArray.get(0).getId(), goodsDetailBeanArray.get(0).getSku_list().get(goodsDetailBeanArray.get(0).getSelectPositon()).getSku_pkid(), getSp(Const.S_ID));
                } else {
                    //预约购买加入购物车
                    lingShouPresenter.addShopCart(getSp(Const.UID), 1, serviceBean.getProduct_info().getId(), skuInfoSelect.getId(), getSp(Const.S_ID));
                }
                break;
            case R.id.re_addess:
                startActivityForResult(new Intent(this, AddressListActivity.class).putExtra("title", getString(R.string.choose_address)).putExtra("action", "choose_address"), 101);
                break;
            case R.id.txt_choosestore:
                if (address_id.equals("")) {
                    MAlert.alert(getString(R.string.choose_address_at_first));
                    return;
                }
                startActivityForResult(new Intent(this, ChooseStoreActivity.class).putExtra("address_id", address_id).putExtra("model", selectAddressBean), 201);
                break;
            case R.id.btn_contacttime:
                Intent intent = new Intent(this, ChooseTimeActivity.class);
                intent.putExtra("canPack", canPack);
                startActivityForResult(intent, 301);
                break;
        }
    }

    private void setDefaultAddress() {
        txt_name.setText(selectAddressBean.getContactname());
        txt_phone.setText(selectAddressBean.getMobile());
        txt_address.setText(selectAddressBean.getProvince() + selectAddressBean.getCity() + selectAddressBean.getArea() + selectAddressBean.getDetailinfo());
        address_id = selectAddressBean.getId();
        if (!store_id.equals("")) {
            lingShouPresenter.queryFreightPrice(store_id, address_id, getSp(Const.S_ID));
        } else {
//            MAlert.alert("地址选择有误");
        }
        txt_mnoren.setVisibility((selectAddressBean.getIs_default() == null ? 1 : Integer.parseInt(selectAddressBean.getIs_default())) == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            //选择收货地址
            if (addressBean.size() > 0) {
                addressBean.set(0, (AddressBean) data.getSerializableExtra("model"));
            } else {
                addressBean.add((AddressBean) data.getSerializableExtra("model"));
            }
            selectAddressBean = addressBean.get(0);
            address_id = selectAddressBean.getId();
            setDefaultAddress();
        }
        if (requestCode == 201 && resultCode == 202) {
            //选择商家
            storeBean = (StoreListBean.ListEntity) data.getSerializableExtra("model");
            store_id = storeBean.getId();
            txt_choosestore.setText(storeBean.getName());
            setDrawable();
            //查询配送费用
            lingShouPresenter.queryFreightPrice(store_id, address_id, getSp(Const.S_ID));
        }
        if (requestCode == 301 && resultCode == 302) {
            //选择配送时间
            send_time = data.getStringExtra("send_time");
            btn_contacttime.setText(send_time);
        }
    }

    private void setDrawable() {
        Drawable drawableRight=getResources().getDrawable(R.drawable.img_location_green);
        drawableRight.setBounds(0,0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
        txt_choosestore.setCompoundDrawables(null,null,drawableRight,null);
    }


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType() == LingShouPresenter.addShopCart_success) {
                //发送广播个更新UI
                beginSendBroadCast();
                MAlert.alert(entity.getData());
                if (mApp.chooseTimeActivityUI != null) {
                    mApp.chooseTimeActivityUI.finish();
                }
                if (mApp.yuGangCleanOrHuoTiBuyStepOneActivityUI != null) {
                    mApp.yuGangCleanOrHuoTiBuyStepOneActivityUI.finish();
                }
                finish();
            } else if (entity.getEventType() == LingShouPresenter.addShopCart_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.queryFreightPrice_success) {
                FreightPriceBean freightPriceBean = (FreightPriceBean) entity.getData();
                freight_price = freightPriceBean.getFreight_price() + "";
                txt_peisong.setText(Html.fromHtml("配送费<font color='red'>￥" + freightPriceBean.getFreight_price() / 100 + "</font>"));
//                totalPrice += Double.parseDouble(freight_price);
                txt_totalprice.setText(Html.fromHtml("合计： <font color='red'>￥" + (totalPrice + Double.parseDouble(freight_price))/ 100 + "</font>"));
            } else if (entity.getEventType() == LingShouPresenter.queryFreightPrice_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.shopCartOrder_success) {
                RePayBean createOrderBean = (RePayBean) entity.getData();
                startActivity(new Intent(MakeSureOrderActivity.this, PayTypeActivity.class).putExtra("model", createOrderBean).putExtra("buyType", buyType).putExtra("shopcart_model", ar));
                //发送广播个更新UI
                beginSendBroadCast();
                finish();
            } else if (entity.getEventType() == LingShouPresenter.shopCartOrder_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.goodsOrder_success) {
                if (mApp.yuGangCleanOrHuoTiBuyStepOneActivityUI != null) {
                    mApp.yuGangCleanOrHuoTiBuyStepOneActivityUI.finish();
                }
                CreateOrderBean createOrderBean = (CreateOrderBean) entity.getData();
                //发送广播个更新UI
                beginSendBroadCast();
                startActivity(new Intent(MakeSureOrderActivity.this, PayTypeActivity.class).putExtra("model", createOrderBean).putExtra("buyType", buyType).putExtra("goodsModel", goodsDetailBeanArray));
                finish();
            } else if (entity.getEventType() == LingShouPresenter.goodsOrder_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getDefaultAddress_success) {
                addressBean = (ArrayList<AddressBean>) entity.getData();
                if (addressBean != null) {
                    if (addressBean.size() > 0) {
                        selectAddressBean = addressBean.get(0);
                        setDefaultAddress();
                    }
                }
            } else if (entity.getEventType() == LingShouPresenter.getDefaultAddress_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.getSkuPidInConsultBuy_success) {
                try {
                    JSONObject jsonObject = new JSONObject(((String) entity.getData()).toString());
                    if (jsonObject.has("sku_pid")) {
                        skuPid = jsonObject.getString("skuPid");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (entity.getEventType() == LingShouPresenter.getSkuPidInConsultBuy_fail) {
                MAlert.alert(getString(R.string.getSkuPid_Error));
            }
        }
    }

    private void beginSendBroadCast() {
//        Intent intent = new Intent(Const.LOGIN_ACTION);
//        sendBroadcast(intent);

        //订单
        Intent intentOrder = new Intent(Const.ORDER_CHANGE);
        sendBroadcast(intentOrder);

        //更新购物车广播
        Intent intentShopcart = new Intent(Const.SHOPCART_CHANGE);
        sendBroadcast(intentShopcart);
    }
}
