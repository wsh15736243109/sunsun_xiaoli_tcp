package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.TimeCount;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class LingShouForgetPassActivity extends LingShouBaseActivity implements Observer {


    LingShouPresenter userPresenter;
    EditText ed_phone, ed_yzm, ed_pwd;
    Button btn_ok;
    private String phone;
    private TimeCount time;// 倒计时
    TextView txt_sendyzm;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ling_shou_forgetpass;
    }

    @Override
    protected void initData() {
        userPresenter = new LingShouPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                phone = ed_phone.getText().toString();
                String yzm = ed_yzm.getText().toString();
                String pwd = ed_pwd.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(yzm)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                userPresenter.updatePassByPhone(getSp(Const.S_ID), "+86", yzm, phone, pwd);
                break;
            case R.id.txt_sendyzm:

                phone = ed_phone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入手机号码");
                    return;
                }
                userPresenter.sendVerificationCode("+86",phone,"2",1);
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
            if (entity.getEventType()== UserPresenter.update_pass_bymobile_success) {
                MAlert.alert(entity.getData());
                finish();
            }else if (entity.getEventType()== UserPresenter.update_pass_bymobile_fail) {
                MAlert.alert(entity.getData());
            }else if (entity.getEventType()== UserPresenter.send_code_success) {
                MAlert.alert(getString(R.string.send_code_success));
                time = new TimeCount(60000, 1000, txt_sendyzm);// 构造CountDownTimer对象
                time.start();
            }else if (entity.getEventType()== UserPresenter.send_code_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
