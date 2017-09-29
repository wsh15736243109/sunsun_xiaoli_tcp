package com.pobing.uilibs.extend.component.filter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pobing.uilibs.extend.R;

import java.util.ArrayList;

public class XFilterFragment extends Fragment
{
    private ArrayList<FilterTab> mFilterTabs;

    private OnFilterSelectedListener mOnFilterSelectedListener;

    private View mView;
    private LinearLayout mTabLayout;
    private int mCurrentSelectFilterTabIndex = -1;
    private View mBackground;
    private LinearLayout mListLayout;
    private ListView mGroupList;
    private ListView mChildList;
    private groupListAdapter mGroupListAdapter;
    private childListAdapter mChildListAdapter;


    public void addFilterTab(FilterTab filterTab)
    {
        if (mFilterTabs == null)
        {
            mFilterTabs = new ArrayList<FilterTab>();
        }

        mFilterTabs.add(filterTab);
    }

    public void setCurrentSelectedFilterTabLabel(String label)
    {
        LinearLayout filterTab = (LinearLayout) mTabLayout.findViewWithTag(mCurrentSelectFilterTabIndex);
        ((TextView)filterTab.findViewById(R.id.tabLabel)).setText(label);
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener listener)
    {
        mOnFilterSelectedListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.uilibs_fragment_filter, null);
        mTabLayout = (LinearLayout) mView.findViewById(R.id.tabLayout);
        mBackground = mView.findViewById(R.id.backgroundLayout);
        mListLayout = (LinearLayout) mView.findViewById(R.id.listLayout);
        mGroupList = (ListView) mView.findViewById(R.id.groupList);
        mChildList = (ListView) mView.findViewById(R.id.childList);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (mFilterTabs != null)
        {
            filterTabOnClickListener onClickListener = new filterTabOnClickListener();
            for (int i = 0; i < mFilterTabs.size(); i++)
            {
                FilterTab tab = mFilterTabs.get(i);
                LinearLayout filterTab = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.uilibs_filter_tab, null);
                TextView filterLabel = (TextView) filterTab.findViewById(R.id.tabLabel);
                filterLabel.setText(tab.getLabel());
                filterLabel.setCompoundDrawables(tab.getIcon(), null, null, null);
                filterTab.setTag(i);
                filterTab.setOnClickListener(onClickListener);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                mTabLayout.addView(filterTab,params);
            }

            mBackground.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    hideFilterList();
                }
            });
            mGroupList.setOnItemClickListener(new groupListOnItemClickListener());
            mChildList.setOnItemClickListener(new childListOnItemClickListener());
        }
    }

    private void selectTab(int index, boolean isOrange)
    {
        LinearLayout filterTab = (LinearLayout) mTabLayout.findViewWithTag(index);
        ImageView filterArrow = (ImageView) filterTab.findViewById(R.id.tabArrow);

        if (isOrange)
        {
            ((TextView)filterTab.findViewById(R.id.tabLabel)).setTextColor(Color.parseColor("#ff5500"));
            filterArrow.setImageResource(R.drawable.uilibs_filter_unfold_orange);
        }
        else
        {
            filterArrow.setImageResource(R.drawable.uilibs_filter_unfold_gray);
        }

        filterArrow.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.uikit_filter_select));
    }

    private void unSelectTab(int index)
    {
        LinearLayout filterTab = (LinearLayout) mTabLayout.findViewWithTag(index);
        ImageView filterArrow = (ImageView) filterTab.findViewById(R.id.tabArrow);
        ((TextView)filterTab.findViewById(R.id.tabLabel)).setTextColor(Color.parseColor("#555555"));
        filterArrow.setImageResource(R.drawable.uilibs_filter_fold);
        filterArrow.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.uikit_filter_unselect));
    }

    private void foldFilterList()
    {
        mListLayout.setVisibility(View.INVISIBLE);
        mListLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.uikit_filter_fold));
    }

    private void unfoldFilterList(int index)
    {
        mListLayout.setVisibility(View.VISIBLE);
        fillFilterList(index);
        mListLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.uikit_filter_unfold));
    }

    private void fadeInFilterListBg()
    {
        mBackground.setVisibility(View.VISIBLE);
        mBackground.setBackgroundColor(Color.parseColor("#88000000"));
        mBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.uikit_filter_fadein));
    }

    private void fadeOutFilterListBg()
    {
        mBackground.setVisibility(View.INVISIBLE);
        mBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.uikit_filter_fadeout));
    }

    private void fillFilterList(int index)
    {
        FilterTab filterTab = mFilterTabs.get(index);
        if (filterTab.getFilterLevel() == FilterTab.FilterLevel.ONLY_CHILD)
        {
            mGroupList.setVisibility(View.GONE);
            mChildListAdapter = new childListAdapter(index);
            if (mChildListAdapter.getCount() > 6)
            {
                mChildList.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 264, getResources().getDisplayMetrics());
            }
            else
            {
                mChildList.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            mChildList.setAdapter(mChildListAdapter);
            mChildListAdapter.notifyDataSetChanged();
        }
        else
        {
            mGroupList.setVisibility(View.VISIBLE);
            mGroupListAdapter = new groupListAdapter(index);
            mChildListAdapter = new childListAdapter(index);

            mGroupList.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mChildList.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroupCount() > 6)
            {
                mGroupList.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 264, getResources().getDisplayMetrics());
                mChildList.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 264, getResources().getDisplayMetrics());
            }

            for (int i = 0; i < mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroupCount(); i++)
            {
                if (mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getChildrenCount(i) > 6)
                {
                    mGroupList.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 264, getResources().getDisplayMetrics());
                    mChildList.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 264, getResources().getDisplayMetrics());
                    break;
                }
            }

            mGroupList.setAdapter(mGroupListAdapter);
            mChildList.setAdapter(mChildListAdapter);
            mGroupListAdapter.notifyDataSetChanged();
            mChildListAdapter.notifyDataSetChanged();
        }
    }

    public void hideFilterList()
    {
        if (mCurrentSelectFilterTabIndex != -1)
        {
            unSelectTab(mCurrentSelectFilterTabIndex);
            foldFilterList();
            fadeOutFilterListBg();
            mCurrentSelectFilterTabIndex = -1;
        }
    }

    class groupListAdapter extends BaseAdapter
    {
        private int mIndex;
        private int mCurrentSelectGroupIndex = -1;

        groupListAdapter(int index)
        {
            mIndex = index;
        }

        public int getCurrentSelectGroupIndex()
        {
            return mCurrentSelectGroupIndex;
        }

        @Override
        public int getCount()
        {
            try
            {
                return mFilterTabs.get(mIndex).getFilterAdapter().getGroupCount();
            }
            catch (Exception e)
            {

            }
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return mFilterTabs.get(mIndex).getFilterAdapter().getGroup(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @SuppressLint("WrongViewCast")
		@Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            groupListViewHolder viewHolder = null;
            if (convertView == null)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.uilibs_listitem_filter_group,null);
                viewHolder = new groupListViewHolder();
                viewHolder.filter_group_text = (TextView) convertView.findViewById(R.id.filter_group_text);
                viewHolder.root = (LinearLayout) convertView.findViewById(R.id.root);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (groupListViewHolder) convertView.getTag();
            }

            Filter filter = (Filter) getItem(position);
            viewHolder.filter_group_text.setText(filter.filterText());
            if (filter.isSelected())
            {
                mCurrentSelectGroupIndex = position;
                viewHolder.root.setBackgroundResource(R.drawable.uilibs_filter_group_dw);
                viewHolder.filter_group_text.setTextColor(Color.parseColor("#ff5000"));
            }
            else
            {
                viewHolder.root.setBackgroundResource(R.drawable.uilibs_filter_group_nm);
                viewHolder.filter_group_text.setTextColor(Color.parseColor("#999999"));
            }

            return convertView;
        }
    }

    class childListAdapter extends BaseAdapter
    {
        private int mIndex;
        private int mGroupIndex;
        private int mCurrentSelectChildIndex = -1;

        childListAdapter(int index)
        {
            mIndex = index;
        }

        public int getGroupIndex()
        {
            return mGroupIndex;
        }

        public int getCurrentSelectChildIndex()
        {
            return mCurrentSelectChildIndex;
        }

        @Override
        public int getCount()
        {
            for (int i = 0; i < mFilterTabs.get(mIndex).getFilterAdapter().getGroupCount(); i++)
            {
                if (mFilterTabs.get(mIndex).getFilterAdapter().getGroup(i).isSelected())
                {
                    mGroupIndex = i;
                    break;
                }
            }

            return mFilterTabs.get(mIndex).getFilterAdapter().getChildrenCount(mGroupIndex);
        }

        @Override
        public Object getItem(int position)
        {
            return mFilterTabs.get(mIndex).getFilterAdapter().getChild(mGroupIndex, position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            childListViewHolder viewHolder = null;
            if (convertView == null)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.uilibs_listitem_filter_child,null);
                viewHolder = new childListViewHolder();
                viewHolder.filter_child_text = (TextView) convertView;
                if (mFilterTabs.get(mIndex).getFilterLevel() == FilterTab.FilterLevel.ONLY_CHILD)
                {
                    viewHolder.filter_child_text.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics()),viewHolder.filter_child_text.getPaddingTop(),viewHolder.filter_child_text.getPaddingRight(),viewHolder.filter_child_text.getPaddingBottom());
                }
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (childListViewHolder) convertView.getTag();
            }

            Filter filter = (Filter) getItem(position);
            viewHolder.filter_child_text.setText(filter.filterText());
            if (filter.isSelected())
            {
                mCurrentSelectChildIndex = position;
                Drawable drawable = getResources().getDrawable(R.drawable.uilibs_icon_selected);
                viewHolder.filter_child_text.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
            else
            {
                viewHolder.filter_child_text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            return convertView;
        }
    }

    class groupListViewHolder
    {
        private TextView filter_group_text;
        private LinearLayout root;
    }

    class childListViewHolder
    {
        public TextView filter_child_text;
    }

    class groupListOnItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (mGroupListAdapter.getCurrentSelectGroupIndex() >= 0)
            {
                mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroup(mGroupListAdapter.getCurrentSelectGroupIndex()).setSelected(false);
            }
            mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroup(position).setSelected(true);
            mGroupListAdapter.notifyDataSetChanged();
            mChildListAdapter.notifyDataSetChanged();
            if (mOnFilterSelectedListener != null && mOnFilterSelectedListener.onFilterGroupSelected(mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroup(position)))
            {
                hideFilterList();
            }
        }
    }

    class childListOnItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterLevel() == FilterTab.FilterLevel.GROUP_AND_CHILD)
            {
                for (int i = 0; i < mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroupCount(); i++)
                {
                    for (int j = 0 ; j < mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getChildrenCount(i) ; j++)
                    {
                        mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getChild(i,j).setSelected(false);
                    }
                }
            }
            else
            {
                mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getChild(0,mChildListAdapter.getCurrentSelectChildIndex()).setSelected(false);
            }

            mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getChild(mChildListAdapter.getGroupIndex(), position).setSelected(true);
            mChildListAdapter.notifyDataSetChanged();
            if (mOnFilterSelectedListener != null && !mOnFilterSelectedListener.onFilterChildSelected(mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getGroup(mChildListAdapter.getGroupIndex()), mFilterTabs.get(mCurrentSelectFilterTabIndex).getFilterAdapter().getChild(mChildListAdapter.getGroupIndex(), position)))
            {
                hideFilterList();
            }
        }
    }

    class filterTabOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //单个筛选项
            if (mFilterTabs.size() == 1)
            {
                //取消选择
                if (mCurrentSelectFilterTabIndex == 0)
                {
                    hideFilterList();
                }
                //选中
                else
                {
                    mCurrentSelectFilterTabIndex = 0;
                    selectTab(0, false);
                    unfoldFilterList(0);
                    fadeInFilterListBg();
                    if (mOnFilterSelectedListener != null)
                    {
                        mOnFilterSelectedListener.onFilterTabSelected(mFilterTabs.get(0).getTag());
                    }
                }
            }
            //多个筛选项
            else
            {
                //取消选中
                if (mCurrentSelectFilterTabIndex == (Integer)v.getTag())
                {
                    hideFilterList();
                }
                //选中
                else
                {
                    //之前有选中的
                    if (mCurrentSelectFilterTabIndex >= 0)
                    {
                        unSelectTab(mCurrentSelectFilterTabIndex);
                    }
                    //之前没有选中的
                    else
                    {
                        fadeInFilterListBg();
                    }

                    mCurrentSelectFilterTabIndex = (Integer) v.getTag();
                    selectTab(mCurrentSelectFilterTabIndex, true);
                    unfoldFilterList(mCurrentSelectFilterTabIndex);
                    if (mOnFilterSelectedListener != null)
                    {
                        mOnFilterSelectedListener.onFilterTabSelected(mFilterTabs.get(mCurrentSelectFilterTabIndex).getTag());
                    }
                }
            }
        }
    }


}
