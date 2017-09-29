package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.bean.VertifyBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.TimeCount;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

public class LingShouRegisterActivity extends LingShouBaseActivity implements Observer {


    UserPresenter userPresenter;
    EditText ed_phone, ed_pwd1, ed_pwd2, ed_yanzhengma;
    CheckBox check_xieyi;
    TextView txt_sendyzm;
    private String phone;
    Button btn_register;
    private String code;
    App mApp;
    private int appType=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ling_shou_register;
    }

    @Override
    protected void initData() {
        mApp= (App) getApplication();
        userPresenter = new UserPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                phone = ed_phone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                String code = ed_yanzhengma.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    MAlert.alert("请输入验证码");
                    return;
                }
                if (!this.code.equals(code)) {
                    MAlert.alert("验证码错误");
                    return;
                }
                String pwd1 = ed_pwd1.getText().toString();
                if (TextUtils.isEmpty(pwd1)) {
                    MAlert.alert("请输入密码");
                    return;
                }
                String pwd2 = ed_pwd2.getText().toString();
                if (TextUtils.isEmpty(pwd2)) {
                    MAlert.alert("请确认密码");
                    return;
                }
                if (!pwd1.equals(pwd2)) {
                    MAlert.alert("两次密码不一致");
                    return;
                }
                userPresenter.registerByPhone("+86", phone, code, pwd1);
                break;
            case R.id.txt_sendyzm:
                phone = ed_phone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                userPresenter.sendVerificationCode("+86", phone, "1",appType);
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity=handlerError(data);
        if (entity!=null) {
            if (entity.getCode()!=0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType()== UserPresenter.registerByPhone_success) {
                MAlert.alert(entity.getData());

                if (mApp.lingShouSwitchRL !=null) {
                    mApp.lingShouSwitchRL.finish();
                }
                finish();
            }else if (entity.getEventType()== UserPresenter.registerByPhone_fail) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType()== UserPresenter.send_code_success) {
                VertifyBean bean= (VertifyBean) entity.getData();
                code=bean.getCode();
                MAlert.alert("验证码是："+code);
                TimeCount timeCount=new TimeCount(6000,1000,txt_sendyzm);
                timeCount.start();
            }else if (entity.getEventType()== UserPresenter.send_code_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
