package sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.OrderFragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.itboye.pondteam.base.LingShouBaseFragment;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseFragmentAdapter;


/**
 * Created by Administrator on 2017/6/23.
 */

public class ServiceOrderFragment extends LingShouBaseFragment {
    TabLayout order_tab_layout;
    ViewPager order_view_pager;

    OrderChildFragment orderChildFragment1 = null;
    private String[] pageTitles = null;
    Fragment[] fragments = null;
    private BaseFragmentAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_orders;
    }

    @Override
    protected void initData() {
        orderChildFragment1 = new OrderChildFragment(0);
        fragments = new Fragment[]{orderChildFragment1};
        pageTitles = new String[]{getString(R.string.all_order)};
        adapter = new BaseFragmentAdapter(getChildFragmentManager(), fragments, pageTitles);
        order_view_pager.setAdapter(adapter);
        order_view_pager.setOffscreenPageLimit(0);
        order_tab_layout.setupWithViewPager(order_view_pager);
        //默认显示热门主播
        order_view_pager.setCurrentItem(0);
        order_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(order_tab_layout));
        order_tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("mrl", "onTabSelected");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("mrl", "onTabUnselected");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        order_tab_layout.setOnClickListener(mTabOnClickListener);
    }

    @Override
    public void onClick(View v) {

    }
}
