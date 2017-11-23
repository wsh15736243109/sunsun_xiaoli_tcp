package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.OrderDetailGoodsAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.OrderDetailBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.MathUtil;
import sunsun.xiaoli.jiarebang.utils.UIAlertViewTwo;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

/**
 * 确认收货
 */
public class OrderDetailActivity extends LingShouBaseActivity implements Observer {

    ListView list_goods;
    OrderBean.ListEntity orderBean = new OrderBean.ListEntity();
    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    LingShouPresenter lingShouPresenter;
    private OrderDetailBean orderDetailBean;
    TextView txt_name;//订单收货人姓名
    TextView txt_mnoren;//是否是默认收货地址
    TextView txt_phone;//收货人电话
    TextView txt_address;//收货详细地址
    RelativeLayout re_addess;
    ImageView arrow_right;
    TextView btn_contactKeFu,btn_shenqingTuiKuan,btn_sureReceive;
    TextView txt_heji;
    App mApp;
    private UIAlertViewTwo uiAlertView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initData() {
        mApp= (App) getApplication();
        mApp.orderDetailUI=this;
        initTitlebarStyle1(this, actionBar, "确认收货", R.mipmap.ic_left_light, "", 0, "");
        lingShouPresenter = new LingShouPresenter(this);
        orderBean = (OrderBean.ListEntity) getIntent().getSerializableExtra("model");
        lingShouPresenter.getOrderDetail(getSp(Const.UID), orderBean.getOrder_code(), getSp(Const.S_ID));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_contactKeFu:
                uiAlertView = new UIAlertViewTwo(this, "是否拨打客服电话？", "400-863-9156", "是", "取消");
                uiAlertView.show();
                uiAlertView.setClicklistener(new UIAlertViewTwo.ClickListenerInterface() {
                    @Override
                    public void doLeft() {
                        try {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-863-9156"));
                            intent.setAction(Intent.ACTION_CALL);
                            startActivity(intent);
                        }catch (SecurityException e){
                            e.printStackTrace();
                        }
                        uiAlertView.dismiss();
                    }
                    @Override
                    public void doRight() {
                        uiAlertView.dismiss();
                    }
                });
                break;
            case R.id.btn_sureReceive:
                startActivity(new Intent(this,TuiKuanShenQingActivity.class).putExtra("model",orderDetailBean).putExtra("type","SHOUHUO"));
                break;
            case R.id.btn_shenqingTuiKuan:
                startActivity(new Intent(this,TuiKuanShenQingActivity.class).putExtra("model",orderDetailBean).putExtra("type","TUIKUAN"));
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity!=null) {
            if (entity.getCode()!=0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType()== LingShouPresenter.getOrderDetail_success) {
                orderDetailBean= (OrderDetailBean) entity.getData();
                setOrderDetailData();
            }else if (entity.getEventType()== LingShouPresenter.getOrderDetail_fail) {
                MAlert.alert(entity.getData());
            }
        }

    }

    private void setOrderDetailData() {
        //订单相关商品
        list_goods.setAdapter(new OrderDetailGoodsAdapter(this, orderDetailBean));
        TextView txtStoreName=new TextView(this);
        txtStoreName.setPadding(10,10,10,10);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.set(10,10,10,10);
        txtStoreName.setLayoutParams(layoutParams);
        txtStoreName.setText(orderDetailBean.getStores_info().getName());
        list_goods.addHeaderView(txtStoreName);
        //订单收货地址
        txt_name.setText(orderDetailBean.get_address().getContactname());//订单收货人姓名
        txt_mnoren.setVisibility(View.GONE);//
        arrow_right.setVisibility(View.GONE);
        txt_phone.setText(orderDetailBean.get_address().getMobile());//收货人电话
        txt_address.setText(orderDetailBean.get_address().getDetailinfo());
        txt_mnoren.setVisibility(View.GONE);
        txt_heji.setText(Html.fromHtml("合计 <font color='#ff0000'>￥"+ MathUtil.doubleForm(orderDetailBean.getPrice())+"</font>"));
    }
}
