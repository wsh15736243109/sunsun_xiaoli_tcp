package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.IsNeedClick;
import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouLoginActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.ShopCartChildFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;


/**
 * 首页
 */
public class ShopCartFragment extends LingShouBaseFragment implements TranslucentScrollView.TranslucentChangedListener {

    private TranslucentActionBar actionBar;//可渐变的标题栏
    private TranslucentScrollView pullzoom_scrollview;//添加滑动监听的滑动组件
    TextView txt_product_order, txt_service_order;


    ShopCartChildFragment productOrderFragment = null;
    ShopCartChildFragment serviceOrderFragment = null;
    private FragmentManager fragmentManager;
    Button btn_login;
    @IsNeedClick
    RelativeLayout re_title;
    @IsNeedClick
    FrameLayout content;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopcart;
    }

    @Override
    protected void initData() {
        fragmentManager = getChildFragmentManager();
        initTitlebarStyle1(getActivity(), actionBar, "购物车", 0, "", 0, "");
        // 第一次启动时选中第0个tab
        setTabSelection(0);
        IntentFilter intentFilter = new IntentFilter(Const.LOGIN_ACTION);
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                judgeLoginStatus();
            }
        }, intentFilter);
        judgeLoginStatus();
        txt_product_order.setSelected(true);
        txt_service_order.setSelected(false);
    }

    private void judgeLoginStatus() {
        boolean isLogin = (boolean) SPUtils.get(getActivity(), null, Const.IS_LOGINED, false);
        if (isLogin) {
            btn_login.setVisibility(View.GONE);
            re_title.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
//            setTabSelection(0);
            productOrderFragment.beginRequest();
        } else {
            btn_login.setVisibility(View.VISIBLE);
            re_title.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。0表示首页，1表示保险，2名片，3表示商家，3表示我的。
     */
    public void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0://首页
                if (productOrderFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    productOrderFragment = new ShopCartChildFragment(1);
                    transaction.add(R.id.content, productOrderFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(productOrderFragment);
                }
                break;
            case 1://
                if (serviceOrderFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    serviceOrderFragment = new ShopCartChildFragment(2);
                    transaction.add(R.id.content, serviceOrderFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(serviceOrderFragment);
                }
                break;
        }
        transaction.commit();
    }
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (productOrderFragment != null) {
            transaction.hide(productOrderFragment);
        }
        if (serviceOrderFragment != null) {
            transaction.hide(serviceOrderFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_product_order:
                txt_product_order.setSelected(true);
                txt_service_order.setSelected(false);
                setTabSelection(0);
                break;
            case R.id.txt_service_order:
                txt_product_order.setSelected(false);
                txt_service_order.setSelected(true);
                setTabSelection(1);
                break;
            case R.id.btn_login:
                getActivity().startActivityForResult(new Intent(getActivity(), LingShouLoginActivity.class), 101);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            productOrderFragment.beginRequest();
            boolean isLogin = (boolean) SPUtils.get(getActivity(), null, Const.IS_LOGINED, false);
            if (isLogin) {
                btn_login.setVisibility(View.GONE);
                re_title.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
            } else {
                btn_login.setVisibility(View.VISIBLE);
                re_title.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }
}
