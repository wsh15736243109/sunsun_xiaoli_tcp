package com.pobing.uilibs.extend.component.filter;

/**
 *
 */
public interface Filter
{
    /**
     * @return the text that used for display
     */
    public String filterText();

    /**
     * @return filter select state
     */
    public boolean isSelected();

    public void setSelected(boolean state);
}
