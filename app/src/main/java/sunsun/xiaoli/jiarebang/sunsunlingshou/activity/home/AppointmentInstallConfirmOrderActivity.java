package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.content.Intent;
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

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressListActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AppointmentInstallConfirmOrderActivity extends LingShouBaseActivity implements Observer {

    TranslucentActionBar actionBar;
    ImageView tv_actionbar_left;
    Button btn_sure_order,btn_addshopcart;
    RelativeLayout re_addess;
    String title;
    String selectValue;
    TextView txt_xuliehao;
    EditText ed_xuliehao;
    ImageView img_xlh_right,iv_actionbar_left;
    private AddressBean addressBean;
    LingShouPresenter lingShouPresenter;
    Button btn_contacttime,btn_store;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_install_confirm_order;
    }

    @Override
    protected void initData() {
        title=getIntent().getStringExtra("title");
        lingShouPresenter=new LingShouPresenter(this);
        lingShouPresenter.getDefaultAddress(getSp(Const.UID),getSp(Const.S_ID));
        selectValue=getIntent().getStringExtra("selectValue");
        initTitlebarStyle1(this,actionBar,title,R.mipmap.ic_left_light,"",0,"");
        if (title.equals("鱼缸清理") || title.equals("造景装饰")|| title.equals("活体购买")) {
            txt_xuliehao.setText(selectValue);
            ed_xuliehao.setVisibility(View.GONE);
            img_xlh_right.setVisibility(View.GONE);
            if (title.equals("活体购买")) {
                btn_addshopcart.setVisibility(View.GONE);
            }else{
                btn_addshopcart.setVisibility(View.VISIBLE);
            }
        }else{
            ed_xuliehao.setVisibility(View.VISIBLE);
            ed_xuliehao.setText(selectValue);
            img_xlh_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_sure_order:
                startActivity(new Intent(this, PayTypeActivity.class));
                break;
            case R.id.re_addess:
                startActivityForResult(new Intent(this, AddressListActivity.class).putExtra("title","").putExtra("action","chooo"),101);
                break;
            case R.id.btn_store:
                break;
            case R.id.btn_contacttime:
                break;
        }
    }

    TextView txt_name,txt_phone,txt_address,txt_mnoren;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101&&resultCode==102) {
            addressBean=(AddressBean)data.getSerializableExtra("model");
          setDefaultAddress();
//            txt_mnoren.setText(addressBean.getContactname());
        }

    }

    private void setDefaultAddress() {
        txt_name.setText(addressBean.getContactname());
        txt_phone.setText(addressBean.getMobile());
        txt_address.setText(addressBean.getDetailinfo());
    }

    @Override
    public void update(Observable o, Object data) {

        ResultEntity entity=handlerError(data);
        if (entity!=null) {
            if (entity.getCode()!=0) {
                MAlert.alert(entity.getMsg());
                return;
            }

            if (entity.getEventType()== LingShouPresenter.getDefaultAddress_success) {
                addressBean= (AddressBean) entity.getData();
                setDefaultAddress();
            }else if (entity.getEventType()== LingShouPresenter.getDefaultAddress_success) {
                MAlert.alert(entity.getData());
            }

        }
    }
}
