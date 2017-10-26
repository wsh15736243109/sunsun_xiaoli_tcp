package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseFragment;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.utils.SPUtils;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.LingShouSwitchLoginOrRegisterActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.OrderFragments.OrderChildFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.OrderFragments.ProductOrderFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentScrollView;


/**
 * 首页
 */
public class OrderFragment extends LingShouBaseFragment implements TranslucentScrollView.TranslucentChangedListener {

    private TranslucentActionBar actionBar;//可渐变的标题栏
    private TranslucentScrollView pullzoom_scrollview;//添加滑动监听的滑动组件
    TextView txt_product_order, txt_service_order;


    ProductOrderFragment productOrderFragment = null;
    OrderChildFragment serviceOrderFragment = null;
    private FragmentManager fragmentManager;
    FrameLayout content;
    Button btn_login;
    RelativeLayout re_title;
    private IntentFilter intentFilter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {
        fragmentManager = getChildFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
        //初始actionBar
        actionBar.setData("我的订单", 0, "", 0, "", null);
        //开启渐变
//        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());
        actionBar.setBarBackgroundColor(getResources().getColor(R.color.main_yellow));
//        //设置透明度变化监听
//        pullzoom_scrollview.setTranslucentChangedListener(this);
//        //关联需要渐变的视图
//        pullzoom_scrollview.setTransView(actionBar);

        //关联伸缩的视图
//        pullzoom_scrollview.setPullZoomView(btn_zoom);

        txt_product_order.setSelected(true);
        txt_service_order.setSelected(false);
        intentFilter=new IntentFilter(Const.LOGIN_ACTION);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);

        judgeLoginStatus("0");

    }

    BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String code=intent.getStringExtra("code");
            judgeLoginStatus(code);
        }
    };

    private void judgeLoginStatus(String code) {
//        if (code.equals("1111")) {
//            btn_login.setVisibility(View.VISIBLE);
//            re_title.setVisibility(View.GONE);
//            content.setVisibility(View.GONE);
//            return;
//        }
        boolean isLogin= (boolean) SPUtils.get(getActivity(),null, Const.IS_LOGINED,false);
        if (isLogin) {
            btn_login.setVisibility(View.GONE);
            re_title.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
            productOrderFragment.beginRequest();
        }else{
            btn_login.setVisibility(View.VISIBLE);
            re_title.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
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
                    productOrderFragment = new ProductOrderFragment();
                    transaction.add(R.id.content, productOrderFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(productOrderFragment);
                }
                break;
            case 1://
                if (serviceOrderFragment == null) {
                    //如果HomeFragment为空，则创建一个添加到界面
                    serviceOrderFragment = new OrderChildFragment(0);
                    transaction.add(R.id.content, serviceOrderFragment);
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(serviceOrderFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (productOrderFragment!=null) {
            transaction.hide(productOrderFragment);
        }
        if (serviceOrderFragment!=null) {
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
                startActivity(new Intent(getActivity(), LingShouSwitchLoginOrRegisterActivity.class));
                break;
        }
    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }
}
