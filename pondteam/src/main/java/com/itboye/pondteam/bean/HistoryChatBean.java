package com.itboye.pondteam.bean;

import com.itboye.pondteam.bean.ChatBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/26.
 */

public class HistoryChatBean {
    private int count;
    private ArrayList<ChatBean.ChatItem> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ChatBean.ChatItem> getList() {
        return list;
    }

    public void setList(ArrayList<ChatBean.ChatItem> list) {
        this.list = list;
    }
}
