package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.alipay.OrderInfoUtil2_0;
import sunsun.xiaoli.jiarebang.alipay.PayResult;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.CreateOrderBean;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_type;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);
        initTitlebarStyle1(this, actionBar, "支付方式", R.mipmap.ic_left_light, "", 0, "");
        if (getIntent().getSerializableExtra("model") instanceof CreateOrderBean) {
            createOrderBean = (CreateOrderBean) getIntent().getSerializableExtra("model");
            txt_price.setText("详情 ￥" + createOrderBean.getPay_money() / 100);
        } else {
            orderBean = (OrderBean.ListEntity) getIntent().getSerializableExtra("model");
            txt_price.setText("详情 ￥" + Double.parseDouble(orderBean.getPrice()) / 100);
        }
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
                        callWxPay(createOrderBean);
                    } else {
                        callAliPay(createOrderBean);
                    }
                } else {
                    //待付款订单重新支付
                    lingShouPresenter.rePay(getSp(Const.UID), getSp(Const.S_ID), orderBean.getOrder_code());
                }
                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;

    private void callWxPay(CreateOrderBean createOrderBean) {
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
//        req.appId			= json.getString("appid");
//        req.partnerId		= json.getString("partnerid");
//        req.prepayId		= json.getString("prepayid");
//        req.nonceStr		= json.getString("noncestr");
//        req.timeStamp		= json.getString("timestamp");
//        req.packageValue	= json.getString("package");
//        req.sign			= json.getString("sign");
//        req.extData			= "app data"; // optional
//        Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        App.getInstance().iwxapi.sendReq(req);
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

                    callWxPay(createOrderBean);
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

                    callWxPay(createOrderBean);
                } else {
                    callAliPay(createOrderBean);
                }
            } else if (entity.getEventType() == LingShouPresenter.rePay_fail) {
                MAlert.alert(entity.getData() + "fail");
            }
        }
    }
}
