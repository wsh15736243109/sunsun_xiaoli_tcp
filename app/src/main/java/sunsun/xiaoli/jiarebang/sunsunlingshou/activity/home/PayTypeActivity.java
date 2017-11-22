package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.alipay.OrderInfoUtil2_0;
import sunsun.xiaoli.jiarebang.alipay.PayResult;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.CreateOrderBean;
import sunsun.xiaoli.jiarebang.beans.GoodsDetailBean;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.beans.WxPrePayBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.RePayBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.BuyType;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class PayTypeActivity extends LingShouBaseActivity implements Observer {

    TranslucentActionBar actionBar;
    CreateOrderBean createOrderBean;
    OrderBean.ListEntity model;
    private OrderBean.ListEntity orderBean;
    LingShouPresenter lingShouPresenter;
    Button btn_sure_pay;
    RadioButton pay_wx, pay_ali;
    TextView txt_price;
    App mApp;
    LinearLayout li_goods;
    BuyType buyType;
    ArrayList<GoodsDetailBean> goodsModel;
    private OrderBean.ListEntity entityTemp;
    RePayBean rePayBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_type;
    }

    @Override
    protected void initData() {
        mApp = (App) getApplication();
        buyType = (BuyType) getIntent().getSerializableExtra("buyType");
        switch (buyType) {
            case Buy_HuoTiGouMai:
                createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
                if (createOrderBean != null) {
                    View view = View.inflate(this, R.layout.item_goods, null);
                    TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                    TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                    txt_product_name.setText("活体购买");
                    txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + createOrderBean.getPay_money() / 100 + "</font>"));
                    li_goods.addView(view);
                }
                break;
            case Buy_LiJiGouMai:
                createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
                goodsModel = (ArrayList<GoodsDetailBean>) getIntent().getSerializableExtra("goodsModel");
                if (goodsModel != null) {
                    View view = View.inflate(this, R.layout.item_goods, null);
                    TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                    TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                    txt_product_name.setText(goodsModel.get(0).getName());
                    txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + goodsModel.get(0).getPrice() / 100 + "</font>"));
                    li_goods.addView(view);
                }
                break;
            case Buy_OrderPay:
                entityTemp = (OrderBean.ListEntity) getIntent().getSerializableExtra("model");
                if (entityTemp != null) {
                    for (int i = 0; i < entityTemp.getItems().size(); i++) {
                        View view = View.inflate(this, R.layout.item_goods, null);
                        TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                        TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                        txt_product_name.setText(entityTemp.getItems().get(i).getName());
                        txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + Double.parseDouble(entityTemp.getItems().get(i).getPrice()) / 100 + "</font>"));
                        li_goods.addView(view);
                    }
                }
                break;
            case Buy_ShopCart:
                ArrayList<ShopCartBean> ar = (ArrayList<ShopCartBean>) getIntent().getSerializableExtra("shopcart_model");
                if (ar != null) {
                    for (int i = 0; i < ar.size(); i++) {
                        View view = View.inflate(this, R.layout.item_goods, null);
                        TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                        TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                        txt_product_name.setText(ar.get(i).getName());
                        txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + (ar.get(0).getPrice()) / 100 + "</font>"));
                        li_goods.addView(view);
                    }
                }
                rePayBean = (RePayBean) getIntent().getSerializableExtra("model");
                //购物车进入
//                rePayBean.get
                break;
            case Buy_YuGangQingLi:
                createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
                if (createOrderBean != null) {
                    View view = View.inflate(this, R.layout.item_goods, null);
                    TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                    TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                    txt_product_name.setText("鱼缸清理");
                    txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + createOrderBean.getPay_money() / 100 + "</font>"));
                    li_goods.addView(view);
                } else {
                    MAlert.alert("订单有误");
                }
                break;
            case Buy_YuYueAnZhuang:
                createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
                if (createOrderBean != null) {
                    View view = View.inflate(this, R.layout.item_goods, null);
                    TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                    TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                    txt_product_name.setText("预约安装");
                    txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + createOrderBean.getPay_money() / 100 + "</font>"));
                    li_goods.addView(view);
                }
                break;
            case Buy_ZaoJingZhuangShi:
                createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
                if (createOrderBean != null) {
                    View view = View.inflate(this, R.layout.item_goods, null);
                    TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                    TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                    txt_product_name.setText("造景装饰");
                    txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + createOrderBean.getPay_money() / 100 + "</font>"));
                    li_goods.addView(view);
                }
                break;
            case Buy_ZiXunGouMai:
                createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
                if (createOrderBean != null) {
                    View view = View.inflate(this, R.layout.item_goods, null);
                    TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
                    TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
                    txt_product_name.setText("咨询购买");
                    txt_price.setText(Html.fromHtml("详情 <font color='red'>￥" + createOrderBean.getPay_money() + "</font>"));
                    li_goods.addView(view);
                }
                break;
        }
        lingShouPresenter = new LingShouPresenter(this);
        initTitlebarStyle1(this, actionBar, "支付方式", R.mipmap.ic_left_light, "", 0, "");
//        if (getIntent().getSerializableExtra("model") instanceof CreateOrderBean) {
//            createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
//            if (createOrderBean != null) {
//                View view = View.inflate(this, R.layout.item_goods, null);
//                TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
//                TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
//                txt_product_name.setText("");
//                txt_price.setText(Html.fromHtml("详情 ￥<font color='red'>" + createOrderBean.getPay_money() + "</font>"));
//                li_goods.addView(view);
//            }
//        } else {
//            orderBean = (OrderBean.ListEntity) getIntent().getSerializableExtra("model");
//            for (int i = 0; i < orderBean.getItems().size(); i++) {
//                View view = View.inflate(this, R.layout.item_goods, null);
//                TextView txt_product_name = (TextView) view.findViewById(R.id.txt_product_name);
//                TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
//                txt_product_name.setText(orderBean.getItems().get(i).getName());
//                txt_price.setText(Html.fromHtml("详情 ￥<font color='red'>" + orderBean.getItems().get(i).getPrice() + "</font>"));
//                li_goods.addView(view);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_sure_pay:
                if (createOrderBean != null) {
                    //生成订单进行支付
                    //测试支付
//                    lingShouPresenter.payTest(getSp(Const.UID),createOrderBean.getPay_code(),createOrderBean.getPay_money(),getSp(Const.S_ID));
                    if (pay_wx.isChecked()) {
                        //
                        lingShouPresenter.wxPrePay(createOrderBean.getPay_code());
//                        callWxPay(createOrderBean);
                    } else {
                        callAliPay(createOrderBean);
                    }
                } else if (rePayBean != null) {
                    CreateOrderBean bean = new CreateOrderBean();
                    bean.setPay_money(Double.parseDouble(rePayBean.getPay_money()));
                    bean.setPay_code(bean.getPay_code());
                    bean.setCreate_time(bean.getCreate_time());
                    //购物车支付
                    if (pay_wx.isChecked()) {
//                        callWxPay(bean);
                    } else {
                        callAliPay(bean);
                    }
                } else {
                    //待付款订单重新支付
                    lingShouPresenter.rePay(getSp(Const.UID), getSp(Const.S_ID), entityTemp.getOrder_code());
                }
                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;

    private void callWxPay(WxPrePayBean wxPrePayBean) {
        PayReq req = new PayReq();
        req.appId = wxPrePayBean.getAppid();  //
        req.partnerId = wxPrePayBean.getPartnerid();
        req.prepayId = wxPrePayBean.getPrepayid();
        req.nonceStr = wxPrePayBean.getNoncestr();
        req.timeStamp = wxPrePayBean.getTimestamp() + "";
        req.packageValue = wxPrePayBean.getPackageValue();
        req.sign = wxPrePayBean.getSign();
//        req.extData = "app data"; // optional
//        Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        App.getInstance().iwxapi.registerApp(wxPrePayBean.getAppid());
        boolean wx = App.getInstance().iwxapi.sendReq(req);
        Log.v("request_params", "wxPrepayBean  =" + wxPrePayBean.toString() + "   >>" + wx)
        ;
    }

    private void callAliPay(CreateOrderBean createOrderBean) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         * orderInfo的获取必须来自服务端；
         */
        double money = createOrderBean.getPay_money() / 100;
        String content = createOrderBean.getPay_code();
        String code = createOrderBean.getPay_code();
        long time = System.currentTimeMillis();
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Const.ALI_APPID, money + "", content, code, time + "");
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Const.ALI_APPID, true, money, content, code, createOrderBean.getCreate_time() + "");
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, Const.RSA_PRIVATE, true);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayTypeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        MAlert.alert("支付成功");
                        if (mApp.makeSureActivity != null) {
                            mApp.makeSureActivity.finish();
                        }
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        MAlert.alert("支付失败");
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg() + "code!=0");
                return;
            }
            if (entity.getEventType() == LingShouPresenter.payTest_success) {
                createOrderBean = (CreateOrderBean) entity.getData();
                if (pay_wx.isChecked()) {

//                    callWxPay(createOrderBean);
                } else {
                    callAliPay(createOrderBean);
                }
            } else if (entity.getEventType() == LingShouPresenter.payTest_fail) {
                MAlert.alert(entity.getData() + "fail");
            } else if (entity.getEventType() == LingShouPresenter.rePay_success) {
                MAlert.alert(entity.getData() + "success");
                CreateOrderBean createOrderBean = (CreateOrderBean) entity.getData();
//                lingShouPresenter.payTest(getSp(Const.UID),createOrderBean.getPay_code(),Double.parseDouble(createOrderBean.getPay_money()),getSp(Const.S_ID));
                //测试支付
                if (pay_wx.isChecked()) {
                    //调用微信预支付
                    lingShouPresenter.wxPrePay(createOrderBean.getPay_code());
                } else {
                    callAliPay(createOrderBean);
                }
            } else if (entity.getEventType() == LingShouPresenter.rePay_fail) {
                MAlert.alert(entity.getData() + "fail");
            } else if (entity.getEventType() == LingShouPresenter.wxPrePay_success) {
                WxPrePayBean wxPrePayBean = (WxPrePayBean) entity.getData();
                callWxPay(wxPrePayBean);
                MAlert.alert(entity.getData() + "success");
            } else if (entity.getEventType() == LingShouPresenter.wxPrePay_fail) {
                MAlert.alert(entity.getData() + "fail");
            }
        }
    }
}
