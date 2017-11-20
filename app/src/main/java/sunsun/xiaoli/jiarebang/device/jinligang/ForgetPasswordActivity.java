package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.EmptyUtil;
import com.itboye.pondteam.utils.TimeCount;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.ClearEditText;
import sunsun.xiaoli.jiarebang.utils.AreaCodeSelectHelper;
import sunsun.xiaoli.jiarebang.utils.IAreaCodeSelect;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


public class ForgetPasswordActivity extends BaseActivity implements Observer, IAreaCodeSelect {

    ImageView img_back;
    TextView txt_title, btn_ok, btn_cancel, btn_get_yzm;
    ClearEditText edit_make_sure_password, edit_username, edit_password, edit_yzm;
    UserPresenter userPresenter;
    private TimeCount time;// 倒计时
    TextView btn_country;
    String country = "+86";
    private AlertDialog dialog;
    private ListView listView;
    private String[] strings;
    private ArrayList<Map<String, Object>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        txt_title.setText(getString(R.string.forget_pass_title));
        userPresenter = new UserPresenter(this);
        if (BuildConfig.APP_TYPE.toLowerCase().equals("pondteam".toLowerCase())) {
            edit_username.setHint(getString(R.string.email));
            btn_country.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_ok:
                if (EmptyUtil.isEmpty(edit_username)) {
                    MAlert.alert(getString(R.string.username_empty));
                    return;
                }
                if (EmptyUtil.isEmpty(edit_yzm)) {
                    MAlert.alert(getString(R.string.yzm_empty));
                    return;
                }
                if (EmptyUtil.isEmpty(edit_password)) {
                    MAlert.alert(getString(R.string.pass_empty));
                    return;
                }
                if (!EmptyUtil.getCustomText(edit_password).equals(EmptyUtil.getCustomText(edit_make_sure_password))) {
                    MAlert.alert(getString(R.string.different_password));
                    return;
                }
                showProgressDialog(getString(R.string.posting), true);
                if (BuildConfig.APP_TYPE.toLowerCase().equals("pondteam".toLowerCase())) {
                    userPresenter.updatePassByEmail(getSp(Const.S_ID), EmptyUtil.getCustomText(edit_yzm), EmptyUtil.getCustomText(edit_username), EmptyUtil.getCustomText(edit_password));
                } else {
                    userPresenter.updatePassByPhone(getSp(Const.S_ID), country, EmptyUtil.getCustomText(edit_yzm), EmptyUtil.getCustomText(edit_username), EmptyUtil.getCustomText(edit_password));
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_get_yzm:
                if (EmptyUtil.isEmpty(edit_username)) {
                    MAlert.alert(getString(R.string.username_empty));
                    return;
                }
                if (BuildConfig.APP_TYPE.toLowerCase().equals("pondteam".toLowerCase())) {
                    userPresenter.sendEmailCode(EmptyUtil.getCustomText(edit_username), 2, 2);
                } else {
                    userPresenter.sendVerificationCode(country, EmptyUtil.getCustomText(edit_username), "2", 0);
                }
                break;
            case R.id.btn_country:
                new AreaCodeSelectHelper().showAreaCode(this, R.layout.item_choose_code, btn_country, this);
                break;
        }
    }


    @Override
    public void update(Observable o, Object data) {
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity resultEntity = handlerError(data);
        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if (resultEntity.getEventType() == UserPresenter.update_pass_bymobile_success) {
                MAlert.alert(resultEntity.getData());
                finish();
            } else if (resultEntity.getEventType() == UserPresenter.update_pass_bymobile_fail) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.send_code_success) {
                MAlert.alert(resultEntity.getData());
                time = new TimeCount(60000, 1000, btn_get_yzm);// 构造CountDownTimer对象
                time.start();
            } else if (resultEntity.getEventType() == UserPresenter.send_code_fail) {
                MAlert.alert(resultEntity.getData());
            } else if (resultEntity.getEventType() == UserPresenter.updata_pass_success) {
                MAlert.alert(resultEntity.getData());
                finish();
            } else if (resultEntity.getEventType() == UserPresenter.updata_pass_fail) {
                MAlert.alert(resultEntity.getData());
            }
        }
    }

    @Override
    public void selectFinish(@NotNull String code) {
        country = code;
    }
}
