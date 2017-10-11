package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observable;
import java.util.Observer;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.presenter.LingShouPresenter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.SettingActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AdviceActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;

import static com.itboye.pondteam.utils.EmptyUtil.getSp;


/**
 * 首页
 */
public class MeFragment extends LingShouBaseFragment implements Observer {

    RelativeLayout re_settings, re_mymessage, re_mypublish, re_advice;
    TextView txt_nickname;
    ImageView img_head;
    LingShouPresenter lingShouPresenter;
    boolean isStore = false;
    @IsNeedClick
    TextView txt_storeOrNormal;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {
        lingShouPresenter = new LingShouPresenter(this);

        IntentFilter intentFilter = new IntentFilter(Const.LOGIN_ACTION);
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                judgeLoginStatus();
            }
        }, intentFilter);
        judgeLoginStatus();
    }

    private void judgeLoginStatus() {
        boolean isLogin = (boolean) SPUtils.get(getActivity(), null, Const.IS_LOGINED, false);
        if (isLogin) {
            txt_nickname.setText(getSp(Const.NICK));
            GlidHelper.glidLoad(img_head, Const.imgurl + getSp(Const.HEAD));
        } else {
            txt_nickname.setText("未登录");
            img_head.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.lingshou_logo));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String is_stores = SPUtils.get(getActivity(), null, Const.IS_STORE, "0") + "";
        if (is_stores.equals("1")) {
            //是商家
            isStore = true;
            txt_storeOrNormal.setText("我的发布");
            Drawable drawable = getResources().getDrawable(R.drawable.me_mypublish);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            txt_storeOrNormal.setCompoundDrawables(null, drawable, null, null);
        } else {
            //不是商家
            isStore = false;
            txt_storeOrNormal.setText("反馈建议");
            Drawable drawable = getResources().getDrawable(R.drawable.me_suggestion);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            txt_storeOrNormal.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.re_settings:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.re_mymessage:
                if (!isStore) {
                    LoginController.goToMyMessage(getActivity(), null);
                } else {
                    LoginController.goToPublish(getActivity(), null);
                }
                break;
            case R.id.re_advice:
                startActivity(new Intent(getActivity(), AdviceActivity.class));
                break;
            case R.id.img_head:
            case R.id.txt_nickname:
                if (getSp(Const.UID).equals("")) {
                    startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                }
                break;
        }
    }

    @Override
    public void update(Observable o, Object data) {
        ResultEntity entity = handlerError(data);
        if (entity != null) {
            if (entity.getCode() != 0) {
//              MAlert.alert(entity.getMsg());
                return;
            }
        }
    }
}
