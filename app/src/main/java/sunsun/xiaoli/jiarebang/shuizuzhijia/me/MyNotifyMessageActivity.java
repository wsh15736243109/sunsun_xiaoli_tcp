package sunsun.xiaoli.jiarebang.shuizuzhijia.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.BaseActivity;
import com.itboye.pondteam.utils.loadingutil.MAlert;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.KeFuMeFragment;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.MessageXiTongFragmet;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.WoDeZhuTiFragment;


public class MyNotifyMessageActivity extends BaseActivity implements
        OnClickListener {


    TextView txt_title;

    // 头部标签切换的Fragment
    private Fragment systemMessageFragment, lunTanFragmet, keFuFragment, currentFragment;

    Button btn_system_message, btn_luntan, btn_kefu;
    LinearLayout li_system_message, li_luntan, li_kefu;
    private View viewright, viewLeift, viewLeiftl;

    ImageView img_back;
    private FragmentTransaction transaction;

    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_mymessage);
        clickTabSystemMessage();
        txt_title.setText("我的消息");
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.li_system_message:
            case R.id.btn_system_message:
                clickTabSystemMessage();
                break;
            case R.id.li_kefu:
            case R.id.btn_kefu:
                clickTabKefuLayout();
                break;
            case R.id.li_luntan:
            case R.id.btn_luntan:
                clickTabLuntanLayout();
                break;
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化头部标签
     */
    @SuppressWarnings("deprecation")
    private void clickTabSystemMessage() {
        transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        if (systemMessageFragment == null) {
            systemMessageFragment = new MessageXiTongFragmet();
            transaction.add(R.id.content, systemMessageFragment);
        } else {
            transaction.show(systemMessageFragment);
        }
        // 设置图片文本的变化
        btn_system_message.setTextColor(getResources().getColor(
                R.color.main_green));
        btn_luntan.setTextColor(getResources()
                .getColor(R.color.gray_6c7bb));
        btn_kefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftl.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));

        viewright.setBackgroundColor(getResources().getColor(
                R.color.main_green));// (R.color.home_blue);
        viewLeift.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));
        transaction.commit();
    }

    private void clickTabLuntanLayout() {
        MAlert.alert("敬请期待");
        transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        if (lunTanFragmet == null) {
            lunTanFragmet = new WoDeZhuTiFragment();
            transaction.add(R.id.content, lunTanFragmet);
        } else {
            transaction.show(lunTanFragmet);
        }
        btn_luntan
                .setTextColor(getResources().getColor(R.color.main_green));
        btn_system_message.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        btn_kefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftl.setBackgroundColor(getResources().getColor(
                R.color.main_green));

        viewright
                .setBackgroundColor(getResources().getColor(R.color.backgroundColor));// (R.color.home_blue);
        viewLeift.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));
        transaction.commit();
    }

    /**
     */
    private void clickTabKefuLayout() {
        transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        if (keFuFragment == null) {
            keFuFragment = new KeFuMeFragment();
            transaction.add(R.id.content, keFuFragment);
        } else {
            transaction.show(keFuFragment);
        }
        btn_kefu.setTextColor(getResources().getColor(
                R.color.main_green));
        btn_system_message.setTextColor(getResources().getColor(R.color.gray_6c7bb));

        btn_luntan.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftl.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));

        viewright.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));// (R.color.home_blue);
        viewLeift
                .setBackgroundColor(getResources().getColor(R.color.main_green));
        transaction.commit();
    }
    private void hideFragment(FragmentTransaction transaction) {
        if (systemMessageFragment != null) {
            transaction.hide(systemMessageFragment);
        }
        if (lunTanFragmet != null) {

            transaction.hide(lunTanFragmet);
        }
        if (keFuFragment != null) {
            transaction.hide(keFuFragment);

        }
    }

}
