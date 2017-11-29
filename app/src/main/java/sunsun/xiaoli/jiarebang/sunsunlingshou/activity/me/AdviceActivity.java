package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

public class AdviceActivity extends LingShouBaseActivity implements Observer {


    TranslucentActionBar actionBar;
    Button btn_submit;
    @IsNeedClick
    EditText ed_advice, edit_contact;
    LingShouPresenter userPresenter;
    ImageView iv_actionbar_left;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advice;
    }

    @Override
    protected void initData() {
        initTitlebarStyle1(this, actionBar, getIntent().getStringExtra("title"), R.mipmap.ic_left_light, "", 0, "");
        userPresenter = new LingShouPresenter(this);
        edit_contact.setText(getSp(Const.MOBILE));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.btn_submit:
                String advice = ed_advice.getText().toString();
                String contact = edit_contact.getText().toString();
                if (advice.equals("")) {
                    MAlert.alert("请输入意见或者建议");
                    return;
                }
                if (getSp(Const.UID).equals("")) {
                    new Intent(this, LingShouSwitchLoginOrRegisterActivity.class);
                } else {
                    showProgressDialog("正在提交中，请稍后", true);
                    userPresenter.feedback(getSp(Const.NICK), "", contact, getSp(Const.UID), advice);
                }
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        try {
            closeProgressDialog();
        } catch (Exception e) {

        }
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
                MAlert.alert(entity.getMsg());
                return;
            }

            if (entity.getEventType() == LingShouPresenter.feedback_success) {
                MAlert.alert(entity.getData());
                finish();
            } else if (entity.getEventType() == LingShouPresenter.feedback_fail) {
                MAlert.alert(entity.getData());
            }
        }
    }
}
