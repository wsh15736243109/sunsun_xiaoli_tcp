package sunsun.xiaoli.jiarebang.interfaces;

/**
 * Created by Administrator on 2017/5/11.
 */

public enum SmartConfigType {
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

    //   ------------------------摄像头一键配置-----------------//
    //  分别为超时，成功，失败
    ,SMARTCONFIG_TIMEOUT,SMARTCONFIG_SUCCESS,SMARTCONFIG_FAIL,


    //------------------摄像头相关------------------//

    //------------------aph 300校准相关------------//
    JIAOZHUN_INIT,JIAOZHUN_ING,JIAOZHUN_FINISH
}
