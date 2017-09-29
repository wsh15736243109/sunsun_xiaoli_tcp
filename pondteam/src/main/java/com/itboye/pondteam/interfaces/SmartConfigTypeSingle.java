package com.itboye.pondteam.interfaces;

/**
 * Created by Administrator on 2017/5/11.
 */

public enum SmartConfigTypeSingle {
    SEARCHING,SEARCH_FINISH,
    REGISTERING,REGISTER_FINISH,
    ADDING,ADD_FINISH,
    RESEARCH,
    //    --------------版本更新相关状态-----------
    UPDATE_INIT,//刚进入UI初始化中
    UPDATE_ING,//正在更新中
    UPDATE_FINISH,//更新完成
    UPDATE_FAIL,//更新失败
    NO_UPDTE//当前已经是最新状态，显示确定按钮
}
