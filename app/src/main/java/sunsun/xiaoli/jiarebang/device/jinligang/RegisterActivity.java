package sunsun.xiaoli.jiarebang.device.jinligang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.TimeCount;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.custom.ClearEditText;


/**
 * Created by itboye on 2017/2/24.
 */

public class RegisterActivity extends BaseActivity implements Observer {

    ClearEditText cleMobile;//电话
    ClearEditText edtPassword;//登录密码
    //    ClearEditText clearUserName;//用户呢称
    ClearEditText cleYzm;//地址信息
    ClearEditText clearPhone;//联系电话
    private TextView btnOk, btnCancel, txt_getYzm;
    TextView txt_title;
    UserPresenter userPresenter;
    TimeCount timeCount;
    ImageView img_back;
    TextView btn_country;
    String country="+86";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_title.setText(getString(R.string.register));
        userPresenter = new UserPresenter(this);
        timeCount = new TimeCount(60000, 1000, txt_getYzm);// 构造CountDownTimer对象
    }

    String username = "";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                if (cleMobile.getText().toString().equals("")) {
                    MAlert.alert(getString(R.string.phone_empty));
                    return;
                }
//                if (EmailUtil.emailValidation(cleMobile.getText().toString()) != true) {
//                    MAlert.alert("邮箱格式不对");
//                } else {
//                    MAlert.alert("输入邮箱地址正确");
//                }
                username = cleMobile.getText().toString();
                String password = edtPassword.getText().toString();
                String code = cleYzm.getText().toString();
                if (code.equals("")) {
                    MAlert.alert(getString(R.string.empty_code));
                    return;
                }
                userPresenter.registerByPhone(country, username, code, password);
                break;
            case R.id.btnCancel:
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_getYzm:
                username = cleMobile.getText().toString();
                if (username.equals("")) {
                    MAlert.alert(getString(R.string.phone_empty));
                    return;
                }
                userPresenter.sendVerificationCode(country, username, "1",0);
                break;
            case  R.id.btn_country:
                new AlertDialog.Builder(this).setTitle(getString(R.string.select_country))
                        .setItems(R.array.country_code_list, new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                country=getResources().getStringArray(R.array.country_code_list)[which];
                                country=country.substring(country.indexOf("*")+1,country.length());
                                btn_country.setText(country);
                                //获取数组资源的方法  getResources().getStringArray(R.array.array_name)[item_number]
                            }
                        }).show();
                break;
        }

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }
            if (entity.getEventType()== UserPresenter.send_code_success) {
                MAlert.alert(entity.getData());
                timeCount.start();
            }else if (entity.getEventType()== UserPresenter.send_code_fail) {
                MAlert.alert(entity.getData());
            }if (entity.getEventType()== UserPresenter.registerByPhone_success) {
                MAlert.alert(entity.getData());
                finish();
            }if (entity.getEventType()== UserPresenter.registerByPhone_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
