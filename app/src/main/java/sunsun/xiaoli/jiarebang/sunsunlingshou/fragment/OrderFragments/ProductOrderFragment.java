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

public class ProductOrderFragment extends LingShouBaseFragment {
    TabLayout order_tab_layout;
    ViewPager order_view_pager;

    OrderChildFragment orderChildFragment1 = null;
    OrderChildFragment orderChildFragment2 = null;
    OrderChildFragment orderChildFragment3 = null;
    OrderChildFragment orderChildFragment4 = null;
    OrderChildFragment orderChildFragment5 = null;
    private String[] pageTitles = null;
    Fragment[] fragments = null;
    private BaseFragmentAdapter adapter;
    int index = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_orders;
    }

    @Override
    protected void initData() {
        orderChildFragment1 = new OrderChildFragment(0);
        orderChildFragment2 = new OrderChildFragment(1);
        orderChildFragment3 = new OrderChildFragment(3);
        orderChildFragment4 = new OrderChildFragment(7);
        orderChildFragment5 = new OrderChildFragment(6);
        fragments = new Fragment[]{orderChildFragment1, orderChildFragment2, orderChildFragment3, orderChildFragment4, orderChildFragment5};
        pageTitles = new String[]{getString(R.string.all_order), getString(R.string.obligation_order), getString(R.string.receiving_order), getString(R.string.finished_order), getString(R.string.evaluated_order)};
        adapter = new BaseFragmentAdapter(getChildFragmentManager(), fragments, pageTitles);
        order_view_pager.setAdapter(adapter);
        order_view_pager.setOffscreenPageLimit(fragments.length);
        order_tab_layout.setupWithViewPager(order_view_pager);
        //默认显示热门主播
        order_view_pager.setCurrentItem(index);
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
        order_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
//                ((OrderChildFragment) fragments[position]).refresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        order_tab_layout.setOnClickListener(mTabOnClickListener);
    }

    public void beginRequest() {
        if (fragments!=null) {
            ((OrderChildFragment)fragments[index]).beginRequest();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
