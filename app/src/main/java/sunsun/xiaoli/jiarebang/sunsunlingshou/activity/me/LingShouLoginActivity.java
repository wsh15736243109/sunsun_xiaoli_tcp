package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.LingShouPersonDataBean;

public class LingShouLoginActivity extends LingShouBaseActivity implements Observer {


    TextView txt_lingshou_forget_pass;
    EditText ed_pwd,ed_phone;
    Button btn_login;
    String country;
    TextView btn_country;
    App mApp;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ling_shou_login;
    }

    @Override
    protected void initData() {
        mApp= (App) getApplication();
        ed_phone.setText("15736243109");
        ed_pwd.setText("123456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_lingshou_forget_pass:
                startActivity(new Intent(this,LingShouForgetPassActivity.class));
                break;
            case R.id.btn_login:
                country="+86";
                String phone=ed_phone.getText().toString();
                String pwd=ed_pwd.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    MAlert.alert("请输入电话号码");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    MAlert.alert("请输入密码");
                    return;
                }
                UserPresenter userPresenter=new UserPresenter(this);
                userPresenter.login(country,phone,pwd,"森森小鲤智能");
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
            if (entity.getEventType()== UserPresenter.login_success) {
                MAlert.alert("登录成功");
                PersonDataBean personDataBean= (PersonDataBean) entity.getData();
                new LingShouPersonDataBean().setPersonData(personDataBean);
                Intent intent=new Intent();
                intent.setAction(Const.LOGIN_ACTION);
                sendBroadcast(intent);
                if (mApp.lingShouSwitchRL!=null) {
                    mApp.lingShouSwitchRL.finish();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ed_phone,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(ed_phone.getWindowToken(), 0); //强制隐藏键盘
                imm.showSoftInput(ed_pwd,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(ed_pwd.getWindowToken(), 0); //强制隐藏键盘
                finish();
            }else if (entity.getEventType()== UserPresenter.login_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
