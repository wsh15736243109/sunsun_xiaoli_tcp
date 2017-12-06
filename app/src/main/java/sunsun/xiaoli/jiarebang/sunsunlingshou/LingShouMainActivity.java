package sunsun.xiaoli.jiarebang.sunsunlingshou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseActivity;
import com.itboye.pondteam.utils.Const;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.logincontroller.LoginController;
import sunsun.xiaoli.jiarebang.logincontroller.LoginedState;
import sunsun.xiaoli.jiarebang.logincontroller.UnLoginState;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouLoginActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.ShopCartChildFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.ConsultationAndBuyFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.HomeFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.MeFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.OrderFragment;

import static com.itboye.pondteam.utils.Const.RELOGIN;
import static com.itboye.pondteam.utils.EmptyUtil.getSp;

public class LingShouMainActivity extends LingShouBaseActivity {

    RelativeLayout index_layout, order_layout, consultation_layout, shopcart_layout, me_layout;
    private FragmentManager fragmentManager;
    HomeFragment homeFragment = null;
    OrderFragment orderFragment = null;
    ConsultationAndBuyFragment consultationAndBuyFragment = null;
    ShopCartChildFragment shopCartFragment = null;
    MeFragment meFragment = null;

    TextView[] txt_value = new TextView[5];
    ImageView[] img_value = new ImageView[5];
    @IsNeedClick
    TextView index_text, order_text, zixun_buy, shopcart_text, me_text;
    @IsNeedClick
    ImageView index_image, order_image, zixun_image, shopcart_image, me_image;

    int[] selectResource = new int[5];
    int[] unSelectResource = new int[5];
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(LingShouMainActivity.this, LingShouLoginActivity.class);
            startActivity(intent1);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lingshou_main;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initData() {
        if (getSp(Const.UID).equals("")) {
            LoginController.setLoginState(new UnLoginState());
        } else {
            LoginController.setLoginState(new LoginedState());
        }
        fragmentManager = getSupportFragmentManager();
        txt_value[0] = index_text;
        txt_value[1] = order_text;
        txt_value[2] = zixun_buy;
        txt_value[3] = shopcart_text;
        txt_value[4] = me_text;
        img_value[0] = index_image;
        img_value[1] = order_image;
        img_value[2] = zixun_image;
        img_value[3] = shopcart_image;
        img_value[4] = me_image;

        selectResource[0] = R.drawable.home_select;
        selectResource[1] = R.drawable.order_select;
        selectResource[2] = R.drawable.main_zixunbuy;
        selectResource[3] = R.drawable.shopcart_select;
        selectResource[4] = R.drawable.me_select;
        unSelectResource[0] = R.drawable.home_unselect;
        unSelectResource[1] = R.drawable.order_unselect;
        unSelectResource[2] = R.drawable.main_zixunbuy;
        unSelectResource[3] = R.drawable.shopcart_unselect;
        unSelectResource[4] = R.drawable.me_unselect;
        // 第一次启动时选中第0个tab
        setTabSelection(0);

        IntentFilter intentFilter = new IntentFilter(RELOGIN);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        homeFragment = null;
//        orderFragment = null;
//        consultationAndBuyFragment = null;
//        shopCartFragment = null;
//        meFragment = null;
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.index_layout && v.getId() != R.id.me_layout) {
            if (isGoToLogin()) {
                return;
            }
        }
        switch (v.getId()) {
            case R.id.index_layout:
                setTabSelection(0);
                break;
            case R.id.order_layout:
                setTabSelection(1);
                break;
            case R.id.consultation_layout:
                setTabSelection(2);
                break;
            case R.id.shopcart_layout:
                setTabSelection(3);
                break;
            case R.id.me_layout:
                setTabSelection(4);
                break;
        }
    }

    private boolean isGoToLogin() {
        if (getSp(Const.UID).equals("")) {
            Intent intent = new Intent(this, LingShouSwitchLoginOrRegisterActivity.class);
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (shopCartFragment != null) {
            shopCartFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (homeFragment != null) {
            homeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。0表示首页，1表示保险，2名片，3表示商家，3表示我的。
     */
    public void setTabSelection(int index) {
        for (TextView textView : txt_value) {
            textView.setTextColor(getResources().getColor(R.color.main_black));
        }
        for (int i = 0; i < img_value.length; i++) {
            ImageView imageView = img_value[i];
            if (imageView.getId() != R.id.zixun_image) {
                imageView.setBackgroundResource(unSelectResource[i]);
            }
        }
        txt_value[index].setTextColor(getResources().getColor(R.color.main_yellow));
        img_value[index].setBackgroundResource(selectResource[index]);
        // 每次选中之前先清楚掉上次的选中状态
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0://首页
                if (homeFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(homeFragment);
                }
                break;
            case 1://
                if (orderFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    orderFragment = new OrderFragment();
                    transaction.add(R.id.content, orderFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(orderFragment);
                }
                break;
            case 2://
                if (consultationAndBuyFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    consultationAndBuyFragment = new ConsultationAndBuyFragment();
                    transaction.add(R.id.content, consultationAndBuyFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(consultationAndBuyFragment);
                }
                break;
            case 3://
                if (shopCartFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    shopCartFragment = new ShopCartChildFragment(1);
                    transaction.add(R.id.content, shopCartFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(shopCartFragment);
                }
                break;
            case 4://
                if (meFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    meFragment = new MeFragment();
                    transaction.add(R.id.content, meFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(meFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (orderFragment != null) {
            transaction.hide(orderFragment);
        }
        if (consultationAndBuyFragment != null) {
            transaction.hide(consultationAndBuyFragment);
        }
        if (shopCartFragment != null) {
            transaction.hide(shopCartFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }
}
