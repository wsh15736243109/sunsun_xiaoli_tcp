package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

public class LingShouSwitchLoginOrRegisterActivity extends LingShouBaseActivity {


    Button btn_register, btn_login;
    App mApp;
    TextView txt_guangguang;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ling_shou_switch_login_or_register;
    }

    @Override
    protected void initData() {
        mApp = (App) getApplication();
        mApp.lingShouSwitchRL = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, LingShouRegisterActivity.class));
                break;
            case R.id.btn_login:
                startActivity(new Intent(this, LingShouLoginActivity.class));
                break;
            case R.id.txt_guangguang:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.lingShouSwitchRL = null;
    }
}
