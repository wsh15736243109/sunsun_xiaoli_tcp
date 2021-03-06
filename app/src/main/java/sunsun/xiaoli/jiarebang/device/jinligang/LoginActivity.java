package sunsun.xiaoli.jiarebang.device.jinligang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.utils.udp.VersionUtil;
import com.itboye.pondteam.volley.ResultEntity;

import org.jetbrains.annotations.NotNull;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.ClearEditText;
import sunsun.xiaoli.jiarebang.device.DeviceActivity;
import sunsun.xiaoli.jiarebang.device.pondteam.PondTeamRegisterActivity;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.logincontroller.LoginedState;
import sunsun.xiaoli.jiarebang.shuizuzhijia.AquariumHomeMainActivity;
import sunsun.xiaoli.jiarebang.utils.AreaCodeSelectHelper;
import sunsun.xiaoli.jiarebang.utils.IAreaCodeSelect;
import sunsun.xiaoli.jiarebang.utils.SpContants;


/**
 * Created by itboye on 2017/2/24.
 */

public class LoginActivity extends BaseActivity implements Observer, IAreaCodeSelect {
    private TextView btn_login, btn_register;//denglu
    String email, password;
    ClearEditText editextUsetName, editextPassword;
    String userName = "";
    String userPass = "";
    UserPresenter userPresenter;
    TextView txt_forget_pass, title_login, btn_country;
    TextView bottom_icon;
    String country = "+86";
    IAreaCodeSelect iAreaCodeSelect;
    TextView txt_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPresenter = new UserPresenter(this);
        String appType = BuildConfig.APP_TYPE;
        if (appType.toLowerCase().contains("pondteam".toLowerCase())) {
            title_login.setText("PondLinkByPondteam");
            bottom_icon.setVisibility(View.VISIBLE);
            btn_country.setVisibility(View.INVISIBLE);
            editextUsetName.setHint(getString(R.string.email));
        } else {
            title_login.setText(getString(R.string.login_sunsun));
            btn_country.setVisibility(View.VISIBLE);
            editextUsetName.setHint(getString(R.string.user_name));
            bottom_icon.setVisibility(View.INVISIBLE);
        }
        if (getPackageName().contains("pondlink")) {
            title_login.setText("PondLinkByPondteam");
            bottom_icon.setVisibility(View.VISIBLE);
            bottom_icon.setBackgroundColor(getResources().getColor(R.color.login_color));
            bottom_icon.setText("pondLink");
            bottom_icon.setTextSize(20);
        } else if (getPackageName().contains("xiaomianyang")) {
            title_login.setText(getString(R.string.login_yihu));
            btn_country.setVisibility(View.VISIBLE);
            editextUsetName.setHint(getString(R.string.user_name));
            bottom_icon.setVisibility(View.INVISIBLE);
        }
        txt_version.setText(getString(R.string.current_version) + VersionUtil.getVersionName());
    }


    @Override
    public void onClick(View view) {
        userName = editextUsetName.getText().toString();
        userPass = editextPassword.getText().toString();
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_register:
                if (BuildConfig.APP_TYPE.equals("pondTeam")) {
                    intent = new Intent(LoginActivity.this, PondTeamRegisterActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                }
                startActivity(intent);
//                onUIAlertView(view);
                break;
            case R.id.btn_login:
                if (userName.equals("") || userName.equals("")) {
                    MAlert.alert(getString(R.string.username_empty));
                    return;
                }
                showProgressDialog(getString(R.string.requesting), true);
                userPresenter.login(country, userName, userPass, "");
//                intent = new Intent(LoginActivity.this, AddPondDevice.class);
//                startActivityForTaoBao(intent);
//                onShowDlog();
                break;
            case R.id.txt_forget_pass:
                intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_country:
                new AreaCodeSelectHelper().showAreaCode(this, R.layout.item_choose_code, btn_country, this);
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity resultEntity = handlerError(data);
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.login_success) {
                PersonDataBean personDataBean = (PersonDataBean) resultEntity.getData();
                if (personDataBean != null) {
                    setMyData(personDataBean);
                    MAlert.alert(getString(R.string.login_success));
                    if (SpContants.APP_TYPE.equals("水族之家")) {
                        Intent intent = new Intent(this, AquariumHomeMainActivity.class);
                        startActivity(intent);
                    }else  if (SpContants.APP_TYPE.equals("小鲤智能测试版")) {
                        Intent intent = new Intent(this, AddDeviceNewActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, DeviceActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
            } else if (resultEntity.getEventType() == UserPresenter.login_fail) {
                MAlert.alert(resultEntity.getData());
            }

        }
    }

    public void setMyData(PersonDataBean bean) {
        SPUtils.put(LoginActivity.this, null,
                Const.UID, bean.getId());
        SPUtils.put(LoginActivity.this, null,
                Const.PaySecret, bean.getPaySecret());
        SPUtils.put(LoginActivity.this, null,
                Const.RELE, "6");
        if (bean.getIs_stores() != null) {
            SPUtils.put(LoginActivity.this, null,
                    Const.IS_STORE, bean.getIs_stores());
        }
        SPUtils.put(LoginActivity.this, null,
                Const.EMAIL, bean.getEmail());
        SPUtils.put(LoginActivity.this, null,
                Const.USERNAME, bean.getUsername());
        SPUtils.put(LoginActivity.this, null,
                Const.PASSWORD, bean.getPassword());
        SPUtils.put(LoginActivity.this, null,
                Const.MOBILE, bean.getMobile());
        SPUtils.put(LoginActivity.this, null,
                Const.IS_LOGINED, true);
        SPUtils.put(LoginActivity.this, null,
                Const.HEAD, bean.getHead());
        SPUtils.put(LoginActivity.this, null,
                Const.NICK, bean.getNickname());
        SPUtils.put(LoginActivity.this, null,
                Const.USER_DEVICE_NUMBER, bean.getUser_device_number() + "");

//        SPUtils.put(LoginActivity.this, null,
//                Const.ISAUTH, bean.getRolesInfo().get(0).getIsAuth());
        SPUtils.put(LoginActivity.this, null,
                Const.S_ID, bean.getAutoLoginCode());
        LoginController.setLoginState(new LoginedState());
    }

    @Override
    public void selectFinish(@NotNull String code) {
        country = code;
    }
}
