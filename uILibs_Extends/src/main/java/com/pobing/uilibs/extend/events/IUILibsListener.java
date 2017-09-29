package com.pobing.uilibs.extend.events;

/**
 * Created by Louis Jin on 14-10-4.
 * Description : 定义了事件机制的监听接口
 */
public interface IUILibsListener<T extends DownloadEvent>  {

    public boolean onHappen(T event);
}
