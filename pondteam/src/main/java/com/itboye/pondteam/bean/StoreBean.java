package com.itboye.pondteam.bean;

import java.util.ArrayList;

/**
 * StoreBean
 * <p>
 * Created by Mr.w on 2018/3/3.
 * <p>
 * 版本      ${version}
 * <p>
 * 修改时间
 * <p>
 * 修改内容
 */


public class StoreBean {
    private int count;
    private ArrayList<NavigationBean.NavigationDetail> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<NavigationBean.NavigationDetail> getData() {
        return list;
    }

    public void setData(ArrayList<NavigationBean.NavigationDetail> data) {
        this.list = data;
    }
}
