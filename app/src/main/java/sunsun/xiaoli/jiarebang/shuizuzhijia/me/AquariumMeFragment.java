package sunsun.xiaoli.jiarebang.shuizuzhijia.me;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.device.jinligang.ForgetPasswordActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.web.WebActivity;
import sunsun.xiaoli.jiarebang.utils.XGlideLoaderNew;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Administrator on 2018/1/24.
 */

public class AquariumMeFragment extends LingShouBaseFragment {
    RelativeLayout re_my_message, re_password_change, re_notifymessage, re_forum, re_about_xiaoli;

    ImageView roundview;

    TextView tvname, txt_title, txt_exist,tvaq;

    ImageView img_back;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aquarium_me;
    }

    @Override
    protected void initData() {
        initTop();
        setMyData();
    }

    private void setMyData() {
        XGlideLoaderNew.displayImageCircularForUser(getActivity(), getSp(Const.UID), roundview);
        tvname.setText(getSp(Const.NICK));
        tvaq.setText(String.format(getString(R.string.device_numer),getSp(Const.USER_DEVICE_NUMBER)));
    }

    private void initTop() {
        txt_title.setText(getString(R.string.aquarium_me));
        img_back.setVisibility(View.GONE);
        txt_exist.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_my_message:
                Intent intent8 = new Intent();
                intent8.setClass(getActivity(), PersonSettingActivity.class);
                intent8.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent8);
                break;
            case R.id.re_password_change:
                startActivity(new Intent(getActivity(), ForgetPasswordActivity.class));
                break;
            case R.id.re_notifymessage:
                break;
            case R.id.re_forum:
                break;
            case R.id.re_about_xiaoli:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title", "").putExtra("url", "http://dx.bankbaoxian.com/shortX/#/shortXZJ"));

                break;
        }
    }
}
