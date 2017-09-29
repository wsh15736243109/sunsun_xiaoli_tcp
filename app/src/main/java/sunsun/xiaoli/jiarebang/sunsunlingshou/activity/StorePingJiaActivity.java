package sunsun.xiaoli.jiarebang.sunsunlingshou.activity;

import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class StorePingJiaActivity extends LingShouBaseActivity implements Observer {

    OrderBean.ListEntity entityTemp;
    TextView txt_storeName;
    TranslucentActionBar actionBar;
    LingShouPresenter lingShouPresenter;
    Button btn_ok,btn_cancel;
    RatingBar zhiliang,taidu,sudu;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_ping_jia;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, actionBar, "商家评价", R.mipmap.ic_left_light, "", 0, "");
        lingShouPresenter=new LingShouPresenter(this);
        entityTemp= (OrderBean.ListEntity) getIntent().getSerializableExtra("model");
        txt_storeName.setText(entityTemp.getStores_name());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_ok:
                float score=(zhiliang.getRating()+taidu.getRating()+sudu.getRating())/3;
                lingShouPresenter.storePingJia(getSp(Const.UID),entityTemp.getOrder_code(), score,getSp(Const.S_ID));
                break;
        }
    }


    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity=handlerError(data);
        if (entity!=null) {
            if (entity.getEventType()== LingShouPresenter.storePingJia_success) {
                MAlert.alert(entity.getData());
                finish();
            }else if (entity.getEventType()== LingShouPresenter.storePingJia_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
