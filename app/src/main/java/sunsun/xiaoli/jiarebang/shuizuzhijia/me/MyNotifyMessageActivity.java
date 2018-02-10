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

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.KeFuMeFragment;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.MessageXiTongFragmet;
import sunsun.xiaoli.jiarebang.shuizuzhijia.me.messageFragment.WoDeZhuTiFragment;


public class MyNotifyMessageActivity extends BaseActivity implements
        OnClickListener {

    LinearLayout layoutMycollTiezixitong;
    LinearLayout layoutTieZiHuiFukefu;
    LinearLayout layoutTieluntan;
    LinearLayout content_layoutMycoll;

    TextView txt_title;

    // 头部标签切换的Fragment
    private Fragment XiTongFragmet, payFragment, sendFragment, currentFragment;
    Button btnTiezitongzhi, btnTieziHuifukefu, btnTieziluntan;

    private View viewright, viewLeift, viewLeiftl;

    ImageView img_back;

    @Override
    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_mymessage);
        initTab();
        txt_title.setText("我的消息");
        txt_title.setTextColor(getResources().getColor(R.color.main_green));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.layoutMycollTiezixitong:
                clickTab1Layout();
                break;
            case R.id.layoutTieZiHuiFukefu:
                clickTab2Layout();
                break;
            case R.id.btnTiezitongzhi:
                clickTab1Layout();
                break;
            case R.id.btnTieziHuifukefu:
                clickTab2Layout();
                break;
            case R.id.layoutTieluntan:
                clickTab3Layout();
                break;
            case R.id.btnTieziluntan:
                clickTab3Layout();
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
    private void initTab() {
        if (XiTongFragmet == null) {
            XiTongFragmet = new MessageXiTongFragmet();
        }

        if (!XiTongFragmet.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layoutMycoll, XiTongFragmet).commit();

            // 记录当前Fragment
            currentFragment = XiTongFragmet;
            // 设置图片文本的变化
            btnTiezitongzhi.setTextColor(getResources().getColor(
                    R.color.main_green));
            btnTieziHuifukefu.setTextColor(getResources()
                    .getColor(R.color.gray_6c7bb));
            btnTieziluntan.setTextColor(getResources().getColor(R.color.gray_6c7bb));
            viewLeiftl.setBackgroundColor(getResources().getColor(
                    R.color.backgroundColor));

            viewright.setBackgroundColor(getResources().getColor(
                    R.color.main_green));// (R.color.home_blue);
            viewLeift.setBackgroundColor(getResources().getColor(
                    R.color.backgroundColor));

        }
    }

    private void clickTab1Layout() {
        if (XiTongFragmet == null) {
            XiTongFragmet = new WoDeZhuTiFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                XiTongFragmet);

        // 设置底部tab变化
        btnTiezitongzhi
                .setTextColor(getResources().getColor(R.color.main_green));
        btnTieziHuifukefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        btnTieziluntan.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftl.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));

        viewright
                .setBackgroundColor(getResources().getColor(R.color.main_green));// (R.color.home_blue);
        viewLeift.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (payFragment == null) {
            payFragment = new KeFuMeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                payFragment);

        btnTieziHuifukefu.setTextColor(getResources().getColor(
                R.color.main_green));
        btnTiezitongzhi.setTextColor(getResources().getColor(R.color.gray_6c7bb));

        btnTieziluntan.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewLeiftl.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));

        viewright.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));// (R.color.home_blue);
        viewLeift
                .setBackgroundColor(getResources().getColor(R.color.main_green));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab3Layout() {
        if (sendFragment == null) {
            sendFragment = new KeFuMeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),
                sendFragment);

        btnTieziHuifukefu.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        btnTiezitongzhi.setTextColor(getResources().getColor(R.color.gray_6c7bb));
        viewright.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));// (R.color.home_blue);
        viewLeift.setBackgroundColor(getResources().getColor(
                R.color.backgroundColor));

        btnTieziluntan.setTextColor(getResources().getColor(R.color.main_green));
        viewLeiftl.setBackgroundColor(getResources()
                .getColor(R.color.main_green));

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layoutMycoll, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

}
