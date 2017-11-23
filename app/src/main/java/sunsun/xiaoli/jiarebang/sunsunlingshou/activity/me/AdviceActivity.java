package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.presenter.UserPresenter;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.loadingutil.MAlert;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;
import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class AdviceActivity extends LingShouBaseActivity implements Observer {


    TranslucentActionBar actionBar;
    Button btn_submit;
    @IsNeedClick
    EditText ed_advice, ed_contact;
    UserPresenter userPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advice;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, actionBar, getIntent().getStringExtra("title"), R.mipmap.ic_left_light, "", 0, "");
        userPresenter = new UserPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_submit:
                String advice = ed_advice.getText().toString();
                String contact = ed_contact.getText().toString();
                if (advice.equals("")) {
                    MAlert.alert("请输入意见或者建议");
                    return;
                }
                if (getSp(Const.UID).equals("")) {
                    new Intent(this, LingShouSwitchLoginOrRegisterActivity.class);
                } else {
                    userPresenter.feedback("", "", "", contact, getSp(Const.UID), advice);
                }
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {

    }
}
