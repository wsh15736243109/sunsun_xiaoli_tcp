package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.base.LingShouBaseActivity;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseFragmentAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.widget.TranslucentActionBar;
import sunsun.xiaoli.jiarebang.utils.ChooseTimeUtil;

import static sunsun.xiaoli.jiarebang.sunsunlingshou.utils.UiUtils.initTitlebarStyle1;

public class ChooseTimeActivity extends LingShouBaseActivity {


    TranslucentActionBar actionBar;
    int nearDaysCount = 6;
    TabLayout order_tab_layout;
    ViewPager order_view_pager;
    private BaseFragmentAdapter adapter;
    ImageView iv_actionbar_left;
    App mApp;
    int canPack;
    RelativeLayout re_time_type;
    public TextView txt_noZiTi, txt_ziti;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_time;
    }

    @Override
    protected void initData() {
        mApp = (App) getApplication();
        mApp.chooseTimeActivityUI = this;

        canPack = getIntent().getIntExtra("canPack", canPack);//0可自提，1不可自提
        if (canPack == 0) {
            txt_noZiTi.setSelected(true);
            txt_ziti.setSelected(false);
            re_time_type.setVisibility(View.VISIBLE);
        } else {
            re_time_type.setVisibility(View.GONE);
        }
        initTitlebarStyle1(this, actionBar, "选择时间", R.mipmap.ic_left_light, "", 0, "");
        String[] titles = ChooseTimeUtil.getNearDays("MM-dd", System.currentTimeMillis(), nearDaysCount);
        Fragment[] fragments = new Fragment[nearDaysCount];
        for (int i = 0; i < nearDaysCount; i++) {
            fragments[i] = new ChooseTimeChildFragment();
        }
        adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        order_view_pager.setAdapter(adapter);
        order_view_pager.setOffscreenPageLimit(fragments.length);
        order_tab_layout.setupWithViewPager(order_view_pager);
        order_view_pager.setCurrentItem(1);
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
//                ((OrderChildFragment)fragments[position]).refresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApp.chooseTimeActivityUI = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_actionbar_left:
                finish();
                break;
            case R.id.txt_ziti:
                txt_ziti.setSelected(true);
                txt_noZiTi.setSelected(false);
                break;
            case R.id.txt_noZiTi:
                txt_noZiTi.setSelected(true);
                txt_ziti.setSelected(false);
                break;
        }
    }
}
