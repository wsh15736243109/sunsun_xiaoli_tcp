package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AppointmentInstallStepOneActivity extends LingShouBaseActivity implements Observer {

    TranslucentActionBar actionBar;
    ImageView iv_actionbar_left;
    Button btn_next;
    @IsNeedClick
    TextView ed_xuliehao, ed_xilie;
    LingShouPresenter lingShouPresenter;
    ProgressDialog progressDialog;
    private String xuliehao;
    RelativeLayout re_xilie, re_xuliehao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_install;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, actionBar, "预约安装", R.mipmap.ic_left_light, "", 0, "");
        lingShouPresenter = new LingShouPresenter(this);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_next:
                xuliehao = ed_xuliehao.getText().toString();
                if (xuliehao.equals("")) {
                    MAlert.alert("请输入序列号");
                    return;
                }
                progressDialog.setMessage("正在验证序列号可用性");
                progressDialog.show();
                lingShouPresenter.checkProductCode(getSp(Const.UID), xuliehao, getSp(Const.S_ID));
                break;
            case R.id.re_xilie:
                //选择序列
                break;
            case R.id.re_xuliehao:
                //选择序列号
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                if (progressDialog.isShowing()) {
                    progressDialog.setMessage(entity.getMsg());
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }, 1000);
                return;
            }
            if (entity.getEventType() == LingShouPresenter.checkProductCode_success) {
                progressDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        progressDialog.dismiss();
                        progressDialog.setMessage("正在获取商品信息");
                        lingShouPresenter.getServiceSku(5);
//                        Intent intent = new Intent(AppointmentInstallStepOneActivity.this, MakeSureOrderActivity.class);
//                        intent.putExtra("title", "预约安装");
//                        intent.putExtra("selectValue", xuliehao);
//                        intent.putExtra("type", BuyType.Buy_YuYueAnZhuang);
//                        startActivity(intent);
                    }
                }, 1000);

            } else if (entity.getEventType() == LingShouPresenter.checkProductCode_fail) {
                progressDialog.setMessage(entity.getData() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1000);
            } else if (entity.getEventType() == LingShouPresenter.getServiceSku_success) {

            } else if (entity.getEventType() == LingShouPresenter.getServiceSku_fail) {

            }
        }
    }
}
