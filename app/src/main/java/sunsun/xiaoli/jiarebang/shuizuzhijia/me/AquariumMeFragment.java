package sunsun.xiaoli.jiarebang.shuizuzhijia.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.utils.loadingutil.MAlert;
import com.umeng.message.UTrack;

import sunsun.xiaoli.jiarebang.BuildConfig;
import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.device.jinligang.ForgetPasswordActivity;
import sunsun.xiaoli.jiarebang.device.jinligang.LoginActivity;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.logincontroller.UnLoginState;
import sunsun.xiaoli.jiarebang.utils.XGlideLoaderNew;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;

/**
 * Created by Administrator on 2018/1/24.
 */

public class AquariumMeFragment extends LingShouBaseFragment {
    RelativeLayout re_my_message, re_password_change, re_notifymessage, re_forum, re_about_xiaoli;

    ImageView roundview;

    TextView tvname, txt_title, txt_exist, tvaq, txt_right;

    ImageView img_back;
    private AlertDialog.Builder alert;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aquarium_me;
    }

    @Override
    protected void initData() {
        initTop();
    }

    private void setMyData() {
        XGlideLoaderNew.displayImageCircular(getActivity(), getSp(Const.HEAD), roundview);
        tvname.setText(getSp(Const.NICK));
        tvaq.setText(String.format(getString(R.string.device_numer), getSp(Const.USER_DEVICE_NUMBER)));
    }

    private void initTop() {
        txt_title.setText(getString(R.string.aquarium_me));
        txt_title.setTextColor(getActivity().getResources().getColor(R.color.main_green));
        img_back.setVisibility(View.GONE);
        txt_exist.setVisibility(View.GONE);
        txt_right.setVisibility(View.VISIBLE);
        txt_right.setText(getString(R.string.exist_login));
        txt_right.setTextColor(getResources().getColor(R.color.main_green));
        txt_right.setTextSize(16);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMyData();
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
                startActivity(new Intent(getActivity(), MyNotifyMessageActivity.class));
                break;
            case R.id.re_forum:
                MAlert.alert("敬请期待");
                break;
            case R.id.txt_right:
                alert = new AlertDialog.Builder(getActivity());
                alert.setMessage(getString(R.string.make_sure_exit));
                alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUserInfo();
                    }
                });
                alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.create();
                alert.show();
                break;
        }
    }

    private void deleteUserInfo() {
        App.getInstance().mPushAgent.removeAlias(getSp(Const.UID), BuildConfig.UMENG_ALIAS, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                System.out.println(b + "  222  " + s);
            }
        });
        SPUtils.put(getActivity(), null, Const.UID, "");
        SPUtils.put(getActivity(), null, Const.EMAIL, "");
        SPUtils.put(getActivity(), null, Const.PaySecret, "");
        SPUtils.put(getActivity(), null, Const.USERNAME, "");
        SPUtils.put(getActivity(), null, Const.PASSWORD, "");
        SPUtils.put(getActivity(), null, Const.MOBILE, "");
        SPUtils.put(getActivity(), null, Const.IS_LOGINED, false);
        SPUtils.put(getActivity(), null, Const.HEAD, "");
        SPUtils.put(getActivity(), null, Const.NICK, "");
        SPUtils.put(getActivity(), null, Const.USER_DEVICE_NUMBER, "");
        SPUtils.put(getActivity(), null, Const.S_ID, "");
        SPUtils.put(getActivity(), null, Const.IS_STORE, "");
        SPUtils.put(getActivity(), null, Const.USER_DEVICE_NUMBER, "0");
        LoginController.setLoginState(new UnLoginState());
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


}
