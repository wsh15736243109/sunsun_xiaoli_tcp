package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.OrderDetailBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.MathUtil;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

/**
 * 退款申请
 */
public class TuiKuanShenQingActivity extends LingShouBaseActivity  implements Observer{
    TranslucentActionBar actionBar;
    TextView txt_totalmoney,txt_no,txt_ok,txt_makesure;
    EditText et_content;
    OrderDetailBean orderBean = new OrderDetailBean();
    LingShouPresenter lingShouPresenter;
    String type;
    ImageView iv_actionbar_left;
    App mApp;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tui_kuan_shen_qing;
    }

    @Override
    protected void initData() {
        mApp= (App) getApplication();
        initTitlebarStyle1(this, actionBar, "退款申请", R.mipmap.ic_left_light, "", 0, "");
        type=getIntent().getStringExtra("type");
        if (type.equals("SHOUHUO")) {
            //确认收货
            et_content.setVisibility(View.GONE);
            txt_makesure.setText(Html.fromHtml("<font color='#F44336'><big>是否确认收货?</big></font><br /><br /><font color='#A5A5A5'>请谨慎查看货物并与配送人员确认</font>"));
        }else{
            //申请退款
            txt_makesure.setText(Html.fromHtml("<font color='#F44336'>是否确认退款?</font><br/ >"));
        }
        orderBean = (OrderDetailBean) getIntent().getSerializableExtra("model");
        lingShouPresenter=new LingShouPresenter(this);
        txt_totalmoney.setText(Html.fromHtml("合计 <font color='#ff0000'>￥"+ MathUtil.doubleForm(orderBean.getPrice())+"</font>"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.txt_no:
                finish();
                break;
            case R.id.txt_ok:
                showProgressDialog("请稍后",true);
                if (type.equals("SHOUHUO")) {
                    lingShouPresenter.querenShouHuo(getSp(Const.UID),orderBean.getOrder_code(),getSp(Const.S_ID));
                }else {
                    lingShouPresenter.tuiHuo(getSp(Const.UID), "1", et_content.getText().toString(), orderBean.getId(), getSp(Const.S_ID));
                }
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity=handlerError(data);
        try {
            closeProgressDialog();
        }catch (Exception e){
        }
        if (entity!=null) {
            if (entity.getEventType()== LingShouPresenter.tuihuo_success) {
                MAlert.alert(entity.getData());
                finish();
            }else if (entity.getEventType()== LingShouPresenter.tuihuo_fail) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType()== LingShouPresenter.shouhuo_success) {
                MAlert.alert(entity.getData());
                if (mApp.orderDetailUI!=null) {
                    mApp.orderDetailUI.finish();
                }
                finish();
            }else if (entity.getEventType()== LingShouPresenter.shouhuo_fail) {
                MAlert.alert(entity.getData());
            }
        }

    }
}
