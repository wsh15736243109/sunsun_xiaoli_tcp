package com.pobing.uilibs.extend.component.filter;

public interface OnFilterSelectedListener
{
    void onFilterTabSelected(String tag);

    /**
     *FilterGroup Selected
     * @param group
     * @return true,the filter will hide automatic
     */
    boolean onFilterGroupSelected(Filter group);

    /**
     * FilterChild Selected
     * @param group
     * @param child
     * @return false,the filter will hide automatic
     */
    boolean onFilterChildSelected(Filter group, Filter child);
}
