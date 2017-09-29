package sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/6/23.
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] pageTitles;

    public BaseFragmentAdapter(FragmentManager fm, Fragment[] fragments, String[] pageTitles) {
        super(fm);
        this.fragments = fragments;
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}