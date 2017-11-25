package com.itboye.pondteam.interfaces;

/**
 * Created by admin on 2016/11/28.
 */

public interface IUserInfoInterface<PersonDataBean> {


    /**
     * 设备设置
     * @param did 设备did
     * @param clEn 定时清洗使能
     * @param clWeek 周清洗设置
     * @param clTm 清洗时间点设置
     * @param clDur 清洗时长设置
     * @param clState 清洗状态
     * @param clCfg 清洗设置
     * @param uvOn 杀菌灯开启时间
     * @param uvOff 杀菌灯关闭时间
     * @param uvWH 杀菌灯累计工作时间
     * @param uvCfg 杀菌灯设置
     */
    void deviceSet(String did,String oa_name,String ob_name,String clEn,int clWeek,String clTm,String clDur,String clState,String clCfg,String uvOn,String uvOff,String uvWH,String uvCfg,String uvState,int out_state_a,int out_state_b,String oa_on_tm,String oa_off_tm,String ob_per,String oa_per,int ws_on_tm,int ws_off_tm);

    /**
     * 用户登录（账户密码）
     *
     * @param username
     * @param pwd
     */
    void login(String country,String username, String pwd,String appType);

    /**
     * 用户注册
     *
     * @param nickname
     * @param contact
     * @param address
     * @param code
     * @param from
     */
    void registerByEmail(String nickname, String contact, String address, String email, String from,String code);
    /**
     * 修改密码（手机号+验证码）
     * @param country   国家[必须]
     * @param code      验证码[必须](itboye是万能验证码，测试其它请调用验证码接口获取)
     * @param mobile    手机号
     * @param password  新密码[必须]（6-32位）
     */
    void updatePass(String country, String code, String mobile, String password);

    /**
     * 获取设备的状态 如：设备信息不存在等
     * @param did
     */
    void getDeviceStatus(String did);
    /**
     * 开始更新过滤桶
     * @param did
     */
    void beginUpdatePondTeam(String did);

    /**
     * 发送验证码
     *
     * @param country   +86
     * @param mobile
     * @param code_type
     */
    void sendVerificationCode(String country, String mobile, String code_type,int appType);

    /**
     * 验证验证码
     *
     * @param country
     * @param mobile
     * @param code
     * @param code_type
     */
    void YanZhengverificationCode(String country, String mobile, String code, String code_type);


    /**
     * 修改密码（邮箱+验证码）
     *
     * @param country  国家[必须]
     * @param code     验证码[必须](itboye是万能验证码，测试其它请调用验证码接口获取)
     * @param mobile   手机号
     * @param password 新密码[必须]（6-32位）
     */
    void updatePassByEmail(String country, String code, String mobile, String password);

    /**
     * 修改密码通过手机号
     *
     * @param sid
     * @param country
     * @param code
     * @param mobile
     * @param password
     */
    void updatePassByPhone(String sid, String country, String code, String mobile, String password);

    /**
     * 修改个人信息
     *
     * @param uid
     * @param types   (nickname:昵称;  sex:性别(0,1); sign:个性签名; email:邮箱； weixin：微信； company：公司； job_title：岗位；loc_country：所在国家编码； loc_area：地区)
     * @param content
     */
    void upadtaInformation(String uid, String types, String content);

    /**
     * 修改密码（通过旧密码）
     *
     * @param uid
     * @param password
     * @param new_password
     */
    void modifyPass(String uid, String password, String new_password);


    /**
     * 添加收货地址
     *
     * @param uid
     * @param contactname 联系人
     * @param mobile      手机号
     * @param postal_code 邮编
     * @param province    省份
     * @param city        城市
     * @param area        街道
     * @param detailinfo  详细地址
     * @param defaults    是否默认
     * @param country
     * @param country_id
     */
    void addressAdd(String s_id, String uid, String contactname, String mobile, String postal_code, String province, String city, String area, String detailinfo, String defaults, String country, String country_id);

    /**
     * 收货地址更新
     *
     * @param id
     * @param uid
     * @param contactname
     * @param mobile
     * @param postal_code
     * @param province
     * @param city
     * @param area
     * @param detailinfo
     * @param defaults
     * @param country
     * @param country_id
     */
    void updateAddress(String s_id, String id, String uid, String contactname, String mobile, String postal_code, String province, String city, String area, String detailinfo, String defaults, String country, String country_id);


    /**
     * 获取全局配置
     */
    void getConfigApp();


    void updatePwdByPwd(String uid, String oldPass, String newPass);


    /**
     * 获取设备信息
     *
     * @param did
     */
    void getDeviceDetailInfo(String did, String uid);

    void bindDevice(String did, String uid);

    /**
     * 获取用户的设备列表
     *
     * @param uid
     */
    void getMyDeviceList(String uid);

    /**
     * @param did
     * @param t_set    温度设定 温度的十倍，范围：200 - 350（20 - 35℃
     * @param t_cyc    实时温度推送周期 单位：分钟范围：1 - 1440
     * @param cfg      异常报警推送开关(Bit0：异常报警推送 0: 关 1:开)
     * @param dev_lock 设备锁机状态
     */
    void deviceSet_jiarebang(String did, String t_set, String t_cyc, String cfg, String dev_lock);

    /**
     * 用户添加设备
     *
     * @param uid
     * @param did
     * @param device_nickname
     */
    void addDevice(String uid, String did, String device_nickname,String device_type,String extra);

    /**
     * 用户删除设备
     *
     * @param id
     */
    void deleteDevice(String id,String uid);

    /**
     * 用户更改设备名字
     *
     * @param id
     * @param nickName
     * @param is_state_notify
     */
    void updateDeviceName(String id, String nickName, String chazuoA, String chazuoB, String temp_min, String temp_max, int temp_alert, int is_state_notify);

    /**
     * 获取固件的最新版本
     *
     * @param did
     */
    void getMostNewPondDevice(String did, String deviceType);


    /**
     * 获取过滤桶设备的状态 如：设备信息不存在等
     *
     * @param did
     */
    void getDeviceGuoLvTongStatus(String did);

    /**
     * 获取加热棒的设备状态
     *
     * @param did
     */
    void getDeviceJiaReBangStatus(String did);

    /**
     * 用户反馈
     *
     * @param deviceType
     * @param name
     * @param email
     * @param tel
     * @param uid
     * @param text
     */
    void feedback(String deviceType, String name, String email, String tel, String uid, String text);

    /**
     * 更新过滤桶固件
     *
     * @param did
     */
    void updateJiaReBangVersion(String did);

    /**
     * 查询历史水温或者ph
     *
     * @param did
     * @param date_type
     */
    void getHistoryTemper(String did, String date_type, boolean isPh);

    /**
     * 注册通过手机
     *
     * @param s
     * @param username
     * @param code
     * @param password
     */
    void registerByPhone(String s, String username, String code, String password);

    /**
     * 验证设备
     *
     * @param did
     * @param ctrl_pwd
     * @param deviceType
     */
    void authDevicePwd(String did, String ctrl_pwd, String deviceType);

    /**
     * aq806设备设置
     *
     * @param did
     * @param tm_1
     * @param mode
     * @param out_uvc
     * @param out_sp
     * @param out_l
     * @param tMax
     * @param th
     * @param tl
     * @param l_per
     * @param uvc_per
     * @param sp_per
     * @param push_cfg Bit0：冲浪水泵异常推送设置
     *                 0：关闭，1：开启
     *                 Bit1：备用电源异常推送设置
     *                 0：关闭，1：开启
     *                 Bit2：照明灯异常推送设置
     *                 0：关闭，1：开启
     *                 Bit3：杀菌灯异常推送设置
     *                 0：关闭，1：开启
     *                 Bit4：水位异常推送设置
     *                 0：关闭，1：开启
     *                 Bit5：水温异常推送设置
     *                 0：关闭，1：开启
     * @param dev_lock
     */
    void deviceSet_806(String did, String tm_1, String mode, String out_uvc, String out_sp, String out_l, String tMax, String th, String tl, String l_per, String uvc_per, String sp_per, String push_cfg, String dev_lock, int uv_wh, int p_wh, int l_wh,int ph_cmd);
    /**
     * 获取当前设备的最新版本
     * @param did
     */
    void getMostNewDevice(String did);

    /**
     * Aph300设备设置接口
     * @param did
     * @param th
     * @param tl
     * @param phh
     * @param phl
     * @param push_cfg
     * @param dev_lock
     * @param d_cyc
     * @param ph_dly
     * @param ph_cmd
     */
    void deviceSet_300Ph(String did,double th,double tl,double phh,double phl,int push_cfg,int dev_lock,int d_cyc,int ph_dly,int ph_cmd);

    /**
     * 变频水泵设置接口
     * @param did
     * @param devLock
     * @param i_cyc
     * @param gear
     * @param cfg
     */
    void deviceSet_shuiBeng(String did,int devLock,int i_cyc,int gear,int cfg,int state,int fcd);

    /**
     * 主设备did
     * @param did
     */
    void cameraQuery(String did);

    /**
     * 从设备绑定接口
     * @param master_did
     * @param slave_did
     * @param slave_device_type
     * @param slave_name
     * @param slave_pwd
     */
    void cameraBind(String master_did,String slave_did,String slave_device_type,String slave_name,String slave_pwd);

    /**
     * 解除从设备
     * @param master_did
     * @param slave_did
     */
    void cameraUnBind(String master_did,String slave_did);

    /**
     * 修改校准时间
     * @param id
     * @param firstTime
     * @param lastTime
     */
    void updateJiaoZhunTime(String id,int push_cfg,int temp_low,int temp_high,int ph_low,int ph_high,long firstTime,long lastTime);

    /**
     * 806中的ph设置
     * @param id
     * @param ph_on
     * @param ph_h
     * @param ph_l
     * @param temp_on
     * @param temp_h
     * @param temp_l
     */
    void deviceSet_806Ph(String id, int ph_on, int ph_h, int ph_l, int temp_on, int temp_h, int temp_l);

    /**
     * 用户加热棒额外信息修改
     * @param id
     * @param abnormal
     */
    void jiaReBangExtraUpdate(String id,int abnormal);

    /**
     * adt设备额外信息修改
     * @param id
     * @param s
     */
    void adtExtraUpdate(String id, String s);

    /**
     * adt设备信息修改
     * @param did
     * @param mode
     * @param push_cfg
     * @param dev_lock
     * @param str
     */
    void deviceSet_led(String did, int mode , int push_cfg, int dev_lock, String str,int w,int b,int g,int r,int sw);

    /**
     * 变频水泵额外信息修改
     * @param id
     * @param push_cfg
     */
    void shuibengExtraUpdate(String id, String push_cfg,int fcd,int state);

    /**
     * 发送邮箱验证码
     * @param customText
     * @param code_type
     * @param send_type
     */
    void sendEmailCode(String customText, int code_type, int send_type);

    /**
     * 仅获取设备在线状态
     * @param did
     */
    void getDeviceOnLineState(String did,String uid);

    /**
     * 更新设备信息
     * @param sp
     * @param device_id
     * @param lang
     * @param timezone
     */
    void updateMobileMsg(String sp, String device_id, String lang, String timezone);

    void deviceSet_qibeng(String did, int dev_lock, int mode, int state, int gear, int wh, int ch_cnt, int b_life, int push_cfg);
}
