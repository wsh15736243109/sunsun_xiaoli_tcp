package sunsun.xiaoli.jiarebang.device.pondteam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import static com.itboye.pondteam.utils.EmptyUtil.getCustomText;
import static com.itboye.pondteam.utils.EmptyUtil.isEmpty;


/**
 * Created by itboye on 2017/2/24.
 */

public class PondTeamRegisterActivity extends BaseActivity implements Observer {

    ClearEditText cleEdEmail;//邮箱
    ClearEditText clearPassword;//登录密码
    ClearEditText clearUserName;//用户呢称
    ClearEditText clearAddress;//地址信息
    ClearEditText clearPhone;//联系电话
    ClearEditText edi_code;//有验证码
    private Button btnDetermine,btncancel;
    UserPresenter userPresenter;
    TextView txt_title;
    ImageView img_back;
    TextView bottom_icon,txt_getYzm;
    private TimeCount time;// 倒计时
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pondteam_register);
        userPresenter=new UserPresenter(this);
        txt_title.setText(getString(R.string.newuser_register));
        if (getPackageName().contains("pondlink")) {
            bottom_icon.setVisibility(View.VISIBLE);
            bottom_icon.setBackgroundColor(getResources().getColor(R.color.login_color));
            bottom_icon.setText("pondLink");
            bottom_icon.setTextSize(20);
        }
    }

    @Override
    public void onClick(View view) {
        if (R.id.img_back==view.getId()||R.id.btncancel==view.getId()) {
            finish();
        }else if(view.getId()==R.id.txt_getYzm){
            if (isEmpty(cleEdEmail)) {
                MAlert.alert(getString(R.string.email_empty));
                return;
            }
            showProgressDialog(getString(R.string.requesting),true);
            userPresenter.sendEmailCode(getCustomText(cleEdEmail),1,1);
        } else if (R.id.btnDetermine==view.getId()) {
            if (isEmpty(cleEdEmail)) {
                MAlert.alert(getString(R.string.email_empty));
                return;
            }
            if (isEmpty(clearPassword)) {
                MAlert.alert(getString(R.string.password_empty));
                return;
            }if (isEmpty(edi_code)) {
                MAlert.alert(getString(R.string.empty_code));
                return;
            }
//                if (isEmpty(clearUserName)) {
//                    MAlert.alert(getString(R.string.email_empty));
//                    return;
//                }
//                if (isEmpty(clearAddress)) {
//                    MAlert.alert(getString(R.string.email_empty));
//                    return;
//                }
//                if (isEmpty(clearPhone)) {
//                    MAlert.alert(getString(R.string.email_empty));
//                    return;
//                }

//            if (EmailUtil.emailValidation(cleEdEmail.getText().toString()) != true) {
//                MAlert.alert("邮箱格式不对");
//                return;
//            }
            String email=getCustomText(cleEdEmail);
            String password=getCustomText(clearPassword);
            String nickName=getCustomText(clearUserName);
            String address=getCustomText(clearAddress);
            String phone=getCustomText(clearPhone);
            String code=getCustomText(edi_code);
            userPresenter.registerByEmail(nickName,phone,address,email,password,code);
        }

    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity resultEntity = handlerError(data);
        try {
            closeProgressDialog();
        }catch (Exception w){

        }

        if (resultEntity != null) {
            if (resultEntity.getCode() != 0) {
                MAlert.alert(resultEntity.getMsg());
                return;
            }
            if(resultEntity.getEventType()==UserPresenter.register_success){
                MAlert.alert(resultEntity.getData());
                finish();
            }else if(resultEntity.getEventType()==UserPresenter.register_success){
                MAlert.alert(resultEntity.getData());
            }else if(resultEntity.getEventType()==UserPresenter.send_code_success){
                MAlert.alert(resultEntity.getData());
                time = new TimeCount(60000, 1000, txt_getYzm);// 构造CountDownTimer对象
                time.start();
            }else if(resultEntity.getEventType()==UserPresenter.send_code_fail){
                MAlert.alert(resultEntity.getData());
            }
        }
    }
}
