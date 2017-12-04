package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.bean.VertifyBean;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.BaseOtherActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class BindPhoneActivity extends BaseOtherActivity implements Observer {


    EditText ed_phone, ed_yzm;
    TranslucentActionBar action_bar;
    ImageView iv_actionbar_left;
    private String phone;
    LingShouPresenter lingShouPresenter;
    TextView txt_sendyzm;
    Button btn_ok;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, action_bar, "绑定手机", R.mipmap.ic_left_light, "", 0, "");
        lingShouPresenter = new LingShouPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_ok:
                phone = ed_phone.getText().toString();
                String yzm = ed_yzm.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(yzm)) {
                    MAlert.alert("请输入验证码");
                    return;
                }
                lingShouPresenter.bindPhone(getSp(Const.UID), phone, yzm);
                break;
            case R.id.txt_sendyzm:
                phone = ed_phone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                lingShouPresenter.sendVerificationCode("+86", phone, "3", 1);
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {

                MAlert.alert(entity.getMsg());
            }
            if (entity.getEventType() == LingShouPresenter.send_code_success) {

                VertifyBean vertifyBean = (VertifyBean) entity.getData();
                if (vertifyBean != null) {
                    MAlert.alert("你的驗證碼是" + vertifyBean.getCode());
                }else
                {
                    MAlert.alert("为空"+vertifyBean);
                }
            } else if (entity.getEventType() == LingShouPresenter.send_code_fail) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.bindPhone_success) {
                MAlert.alert(entity.getData());
            } else if (entity.getEventType() == LingShouPresenter.bindPhone_fail) {
                MAlert.alert(entity.getData());

            }
        }
    }
}
