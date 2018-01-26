package sunsun.xiaoli.jiarebang.shuizuzhijia.me;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.itboye.pondteam.base.LingShouBaseFragment;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.device.jinligang.ForgetPasswordActivity;

/**
 * Created by Administrator on 2018/1/24.
 */

public class AquariumMeFragment extends LingShouBaseFragment {
    RelativeLayout re_my_message, re_password_change, re_notifymessage, re_forum, re_about_xiaoli;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aquarium_me;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_my_message:
                break;
            case R.id.re_password_change:
                startActivity(new Intent(getActivity(),ForgetPasswordActivity.class));
                break;
            case R.id.re_notifymessage:
                break;
            case R.id.re_forum:
                break;
            case R.id.re_about_xiaoli:
                break;
        }
    }
}
