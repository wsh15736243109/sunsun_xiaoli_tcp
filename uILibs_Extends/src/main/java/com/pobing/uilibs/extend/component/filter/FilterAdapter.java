package com.pobing.uilibs.extend.component.filter;

public interface FilterAdapter
{
    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    int getGroupCount();

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *            count should be returned
     * @return the children count in the specified group
     */
    int getChildrenCount(int groupPosition);

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    Filter getGroup(int groupPosition);

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *            children in the group
     * @return the data of the child
     */
    Filter getChild(int groupPosition, int childPosition);
}
