package com.itboye.pondteam.bean;

import java.io.Serializable;

/**
 * Created by Mr.w on 2017/3/21.
 */

public class DeviceDetailModel implements Serializable {
/**
 * "id": "1",
 "did": "10000001",
 "ver": "v1.0",
 "pwd": "abcdefgh",
 "ctrl_pwd": "12345678",//设备控制密码
 "last_login_time": "1490002694",//最近设备登录时间
 "hb": "60",//心跳周期（秒）
 "tcp_client_id": "76be2d4d0b5700000015",//TCP通道
 "last_login_ip": "112.16.93.124",//最近登录ip
 "create_time": "1970-01-01 08:00:00",//设备创建记录时间
 "update_time": "2017-03-20 17:38:14",//设备最近更新时间
 "cl_en": "1",//定时清洗开关：0：关 1：开
 "cl_week": "1",//二进制00000000  一比特 Bit0 - Bit6对应周一到周日 Bit设置为1表示使用，为0停止
 "cl_tm": "1200",//格式HHmm，UTC时间 ， 表示每天清洗的时间点
 "cl_dur": "1",//清洗时长设置  单位秒
 "cl_state": "3",//清洗状态  0：停止 1：清洗 2：暂停
 "cl_sche": "1",//清洗进度时间  当前清洗过程已经历的时间，单位秒
 "cl_cfg": "1",//二进制00000000 清洗设置  Bit0：清洗提示设置 1使能，0禁止 Bit1：异常报警设置
 1使能，0禁止
 "uv_on": "1300",//杀菌灯开启时间，格式hhmm，UTC时间
 "uv_off": "1400",//杀菌灯关闭时间，格式hhmm，UTC时间
 "uv_wh": "1",//杀菌灯累计工作时间  单位小时
 "uv_cfg": "1",//杀菌灯设置  Bit0：更换维护提示
 1使能，0禁止
 Bit1：异常报警
 1使能，0禁止
 "out_state_a": "1",备用插座A状态  0：插座断电
 1：插座通电
 "out_state_b": "1",
 备用插座B状态  0：插座断电
 1：插座通电
 "dev_lock": "1",
 //设备锁机状态  0：未锁机，可局域网查找
 1：锁机，局域网隐藏
 "upd_state": "55",
 固件更新状态  0 - 100：更新进度百分比，更新成功为100
 101：更新失败，硬件重启后该字段隐藏
 "is_disconnect": "1",
 //1表示设备可能断开了，这种情况下就需要app再次调用该接口进行获取，如果重试n（一般为3）次，都是1，则设备正式断开了，需要app提示说设备硬件重新连接。
 ""device_state": "999"//设备更新状态999为默认正常 ，
 //
 999: 无
 0：成功启动更新
 1：固件正在更新，该操作忽略
 2：固件信息错误，更新失败
 */
    /**
     * cl_cfg : 0
     * dev_lock : 0
     * uv_off : 0000
     * last_login_time : 1489475315
     * uv_wh : 0
     * cl_week : 0
     * update_time : 2017-03-14 15:08:34
     * tcp_client_id :
     * uv_cfg : 0
     * last_login_ip : 112.16.93.124
     * is_disconnect : 1
     * cl_en : 0
     * uv_on : 0000
     * id : 4
     * upd_state : 0
     * ver : v1.0
     * create_time : 1970-01-01 08:00:00
     * cl_dur : 0
     * ctrl_pwd : 12345678
     * cl_state : 0
     * cl_sche : 0
     * cl_tm : 0000
     * device_state : 999
     * hb : 30
     * out_state_a : 0
     * out_state_b : 0
     * pwd : abcdefgh
     * did : S0100000000001
     */
    //变频水泵在线时间
    private int online_time;

    public void setOa_per(String oa_per) {
        this.oa_per = oa_per;
    }

    private String oa_per;

    public int getOnline_time() {
        return online_time;
    }

    public void setOnline_time(int online_time) {
        this.online_time = online_time;
    }

    private String cl_cfg;
    private String uv_off;
    private String last_login_time;
    private String cl_week;
    private String update_time;
    private String tcp_client_id;
    private String uv_cfg;
    private String last_login_ip;
    private String is_disconnect;
    private String cl_en;
    private String uv_on;
    private String id;
    private String ver;
    private String create_time;
    private int cl_dur;
    private String ctrl_pwd;
    private String cl_state;
    private String cl_sche;
    private String cl_tm;
    private String device_state;
    private String hb;
    private String out_state_a;
    private String out_state_b;
    private String pwd;
    private String did;
    private String device_nickname;
    private String nickname_a;
    private String nickname_b;
    private int pwr;
    private String cfg;
    private String t_set;

    private String oa_on_tm;
    private String per;
    private int w;
    private int g;
    private int r;
    private int b;
    private int uv_state;
    private String oa_per_name;
    private String ob_per_name;
    private String device_type;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getOa_per_name() {
        return oa_per_name;
    }

    public void setOa_per_name(String oa_per_name) {
        this.oa_per_name = oa_per_name;
    }

    public String getOb_per_name() {
        return ob_per_name;
    }

    public void setOb_per_name(String ob_per_name) {
        this.ob_per_name = ob_per_name;
    }

    //节水模式开启/关闭时间
    private int ws_on_tm,ws_off_tm;

    public int getWs_on_tm() {
        return ws_on_tm;
    }

    public void setWs_on_tm(int ws_on_tm) {
        this.ws_on_tm = ws_on_tm;
    }

    public int getWs_off_tm() {
        return ws_off_tm;
    }

    public void setWs_off_tm(int ws_off_tm) {
        this.ws_off_tm = ws_off_tm;
    }

    public int getUv_state() {
        return uv_state;
    }

    public void setUv_state(int uv_state) {
        this.uv_state = uv_state;
    }

    private int temp_max;
    private int temp_min;
    private int temp_alert;
    private int is_state_notify;
    /**
     * 灯光总开关控制
     * 0：关闭
     * 1：打开
     */
    private int sw;
    private int fcd;

    public void setFcd(int fcd) {
        this.fcd = fcd;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public int getTemp_alert() {
        return temp_alert;
    }

    public void setTemp_alert(int temp_alert) {
        this.temp_alert = temp_alert;
    }

    public int getIs_state_notify() {
        return is_state_notify;
    }

    public void setIs_state_notify(int is_state_notify) {
        this.is_state_notify = is_state_notify;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getOa_on_tm() {
        return oa_on_tm;
    }

    public void setOa_on_tm(String oa_on_tm) {
        this.oa_on_tm = oa_on_tm;
    }

    public String getOa_off_tm() {
        return oa_off_tm;
    }

    public void setOa_off_tm(String oa_off_tm) {
        this.oa_off_tm = oa_off_tm;
    }

    public String getOb_per() {
        return ob_per;
    }

    public void setOb_per(String ob_per) {
        this.ob_per = ob_per;
    }

    private String oa_off_tm;
    private String ob_per;

    public String getCfg() {
        return cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg;
    }

    public String getT_set() {
        return t_set;
    }

    public void setT_set(String t_set) {
        this.t_set = t_set;
    }

    public int getPwr() {
        return pwr;
    }

    public void setPwr(int pwr) {
        this.pwr = pwr;
    }

    /**
     * ph : 0
     * out_ctrl : 0
     * t_max : 0
     * th : 0
     * tl : 0
     * p_p : 0
     * uvc_p : 0
     * sp_p : 0
     * l_p : 0
     * l_per : {"h0":0,"m0":0,"h1":0,"m1":0}
     * uvc_per : {"h0":0,"m0":0,"h1":0,"m1":0}
     * sp_per : {"h0":0,"m0":0,"h1":0,"m1":0}
     * ex_dev :
     * mode : 1
     * tm_l :
     */
    private double t;//实时温度	温度值的10倍值
    private double ph;//实时PH值	数值为实际PH值的100倍
    /**
     * 输出继电器控制值	Bit0：灯光继电器状态
     * 0：关闭，1：打开
     * Bit1：冲浪水泵继电器状态
     * 0：关闭，1：打开
     * Bit4：杀菌灯继电器状态
     * 0：关闭，1：打开
     * Bit7：手动和自动模式状态
     * 0：手动模式，1：自动模式
     */
    private int out_ctrl;
    //温控加热上限温度	温度值的10倍值
    private int t_max;

    private int fault;
    /**
     * (fault)
     * 故障状态值	Bit1 - 0：水温状态
     * 00：正常，01：过低，10：过高
     * Bit2：杀菌灯故障状态
     * 0：正常，1：故障
     * Bit3：冲浪水泵故障状态
     * 0：正常，1：故障
     * Bit4：照明灯故障状态
     * 0：正常，1：故障
     * Bit5：备用电源（循环水泵）故障状态
     * 0：正常，1：故障
     * Bit4：水位状态
     * 0：正常，1：过低
     * Bit9 - 8：PH状态
     * 00：正常，01：过低，10：过高
     */

    //水温异常高温阈值	温度值的10倍值
    private double th;
    //水温异常低温阈值	温度值的10倍值
    private double tl;
    //循环水泵功率	单位瓦特
    private String p_p;
    //杀菌灯功率	单位瓦特
    private String uvc_p;
    //冲浪水泵功率	单位瓦特
    private String sp_p;
    //照明灯功率	单位瓦特
    private String l_p;
    //照明灯时间段	一天共三个时间段
    private String l_per;
    //杀菌灯时间段	一天共三个时间段
    private String uvc_per;
    //冲浪水泵时间段	一天共三个时间段
    private String sp_per;
    //杀菌灯累计使用时间
    private int uv_wh;
    //照明灯累计使用时间
    private int l_wh;
    //冲浪泵累计使用时间
    private int p_wh;
    //推送请求值
    private int push_cfg;
    //报警PH上限值	PH的100倍值
    private double phh;
    //报警PH下限值	PH的100倍值
    private double phl;
    //数据推送周期	单位：分钟
    //范围：1 - 1440
    private int d_cyc;
    //上一次校准时间
    private int last_ph_upd;
    //首次校准时间
    private int first_ph_upd;
    /**
     * 当前PH校准命令	校准过程中该数据响应。
     * 400：PH4.00校准
     * 401：PH4.01校准
     * 686：PH6.86校准
     * 700：PH7.00校准
     */
    private int ph_cmd;
    /**
     * 当前PH校准进度	单位秒。
     * 校准启动后该数据响应，时间到达ph_dly后进度完成，重启后该数据隐藏
     */
    private int ph_sche;

    /**
     * PH校准延时时间	单位秒。
     * PH校准命令发送后，等待该延迟后完成校准转换
     */
    private int ph_dly;

    /**
     * 设备锁机状态	0：未锁机，可局域网查找
     * 1：锁机，局域网隐藏
     */
    private int dev_lock;

    private String extra;

    /**
     * 充电次数
     */
    private int ch_cnt;

    /**
     * 电池寿命
     */
    private int b_life;

    /**
     * 剩余充电时间
     */
    private int rct;
    private int wh;//当气泵使用使用时间
    private int batt;//当前电量

    public int getBatt() {
        return batt;
    }

    public void setBatt(int batt) {
        this.batt = batt;
    }

    public int getWh() {
        return wh;
    }

    public void setWh(int wh) {
        this.wh = wh;
    }

    public int getCh_cnt() {
        return ch_cnt;
    }

    public void setCh_cnt(int ch_cnt) {
        this.ch_cnt = ch_cnt;
    }

    public int getB_life() {
        return b_life;
    }

    public void setB_life(int b_life) {
        this.b_life = b_life;
    }

    public int getRct() {
        return rct;
    }

    public void setRct(int rct) {
        this.rct = rct;
    }

    /**
     * 固件更新状态	0 - 100：更新进度百分比，更新成功为100
     * 101：更新失败，硬件重启后该字段隐藏
     */
    private int upd_state;

    public int getLast_ph_upd() {
        return last_ph_upd;
    }

    public void setLast_ph_upd(int last_ph_upd) {
        this.last_ph_upd = last_ph_upd;
    }

    public int getFirst_ph_upd() {
        return first_ph_upd;
    }

    public void setFirst_ph_upd(int first_ph_upd) {
        this.first_ph_upd = first_ph_upd;
    }

    public double getPhh() {
        return phh;
    }

    public void setPhh(double phh) {
        this.phh = phh;
    }

    public double getPhl() {
        return phl;
    }

    public void setPhl(int phl) {
        this.phl = phl;
    }

    public int getD_cyc() {
        return d_cyc;
    }

    public void setD_cyc(int d_cyc) {
        this.d_cyc = d_cyc;
    }

    public int getPh_cmd() {
        return ph_cmd;
    }

    public void setPh_cmd(int ph_cmd) {
        this.ph_cmd = ph_cmd;
    }

    public int getPh_sche() {
        return ph_sche;
    }

    public void setPh_sche(int ph_sche) {
        this.ph_sche = ph_sche;
    }

    public int getPh_dly() {
        return ph_dly;
    }

    public void setPh_dly(int ph_dly) {
        this.ph_dly = ph_dly;
    }

    public int getDev_lock() {
        return dev_lock;
    }

    public void setDev_lock(int dev_lock) {
        this.dev_lock = dev_lock;
    }

    public int getPush_cfg() {
        return push_cfg;
    }

    public void setPush_cfg(int push_cfg) {
        this.push_cfg = push_cfg;
    }

    public int getL_wh() {
        return l_wh;
    }

    public void setL_wh(int l_wh) {
        this.l_wh = l_wh;
    }

    public int getP_wh() {
        return p_wh;
    }

    public void setP_wh(int p_wh) {
        this.p_wh = p_wh;
    }

    public int getUv_wh() {
        return uv_wh;
    }

    public void setUv_wh(int uv_wh) {
        this.uv_wh = uv_wh;
    }

    public String getL_per() {
        return l_per;
    }

    public void setL_per(String l_per) {
        this.l_per = l_per;
    }

    public String getUvc_per() {
        return uvc_per;
    }

    public void setUvc_per(String uvc_per) {
        this.uvc_per = uvc_per;
    }

    public String getSp_per() {
        return sp_per;
    }

    public void setSp_per(String sp_per) {
        this.sp_per = sp_per;
    }

    /**
     * 外扩挂载设备标识	AQ107：AQ107底板
     * AQ209：AQ209底板
     * AQ805：AQ805底板
     * AQ806：AQ806底板
     * AQ211：AQ211底板
     * AQ210：AQ210底板
     */
    private String ex_dev;
    //设置手动自动模式	0：手动，1：自动
    private String mode;
    //设置本地时间	本地时间，格式yyyyMMddHHmmss
    private String tm_l;

    /**
     * 设备类型描述
     * 0：JDP-6000水泵
     * 1：JDP-10000水泵
     */
    private String type;
    /**
     * 当前工作状态	0：停机
     * 1：运行
     * 2：故障
     */
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFault() {
        return fault;
    }

    public void setFault(int fault) {
        this.fault = fault;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public int getOut_ctrl() {
        return out_ctrl;
    }

    public void setOut_ctrl(int out_ctrl) {
        this.out_ctrl = out_ctrl;
    }

    public int getT_max() {
        return t_max;
    }

    public void setT_max(int t_max) {
        this.t_max = t_max;
    }

    public double getTh() {
        return th;
    }

    public void setTh(double th) {
        this.th = th;
    }

    public double getTl() {
        return tl;
    }

    public void setTl(double tl) {
        this.tl = tl;
    }

    public String getP_p() {
        return p_p;
    }

    public void setP_p(String p_p) {
        this.p_p = p_p;
    }

    public String getUvc_p() {
        return uvc_p;
    }

    public void setUvc_p(String uvc_p) {
        this.uvc_p = uvc_p;
    }

    public String getSp_p() {
        return sp_p;
    }

    public void setSp_p(String sp_p) {
        this.sp_p = sp_p;
    }

    public String getL_p() {
        return l_p;
    }

    public void setL_p(String l_p) {
        this.l_p = l_p;
    }


    public String getEx_dev() {
        return ex_dev;
    }

    public void setEx_dev(String ex_dev) {
        this.ex_dev = ex_dev;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTm_l() {
        return tm_l;
    }

    public void setTm_l(String tm_l) {
        this.tm_l = tm_l;
    }

    public String getNickname_a() {
        return nickname_a;
    }

    public void setNickname_a(String nickname_a) {
        this.nickname_a = nickname_a;
    }

    public String getNickname_b() {
        return nickname_b;
    }

    public void setNickname_b(String nickname_b) {
        this.nickname_b = nickname_b;
    }

    public String getDevice_nickname() {
        return device_nickname;
    }

    public void setDevice_nickname(String device_nickname) {
        this.device_nickname = device_nickname;
    }

    public void setCl_cfg(String cl_cfg) {
        this.cl_cfg = cl_cfg;
    }


    public void setUv_off(String uv_off) {
        this.uv_off = uv_off;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }


    public void setCl_week(String cl_week) {
        this.cl_week = cl_week;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setTcp_client_id(String tcp_client_id) {
        this.tcp_client_id = tcp_client_id;
    }

    public void setUv_cfg(String uv_cfg) {
        this.uv_cfg = uv_cfg;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public void setIs_disconnect(String is_disconnect) {
        this.is_disconnect = is_disconnect;
    }

    public void setCl_en(String cl_en) {
        this.cl_en = cl_en;
    }

    public void setUv_on(String uv_on) {
        this.uv_on = uv_on;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpd_state(int upd_state) {
        this.upd_state = upd_state;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setCl_dur(int cl_dur) {
        this.cl_dur = cl_dur;
    }

    public void setCtrl_pwd(String ctrl_pwd) {
        this.ctrl_pwd = ctrl_pwd;
    }

    public void setCl_state(String cl_state) {
        this.cl_state = cl_state;
    }

    public void setCl_sche(String cl_sche) {
        this.cl_sche = cl_sche;
    }

    public void setCl_tm(String cl_tm) {
        this.cl_tm = cl_tm;
    }

    public void setDevice_state(String device_state) {
        this.device_state = device_state;
    }

    public void setHb(String hb) {
        this.hb = hb;
    }

    public void setOut_state_a(String out_state_a) {
        this.out_state_a = out_state_a;
    }

    public void setOut_state_b(String out_state_b) {
        this.out_state_b = out_state_b;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getCl_cfg() {
        return cl_cfg;
    }


    public String getUv_off() {
        return uv_off;
    }

    public String getLast_login_time() {
        return last_login_time;
    }


    public String getCl_week() {
        return cl_week;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getTcp_client_id() {
        return tcp_client_id;
    }

    public String getUv_cfg() {
        return uv_cfg;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public String getIs_disconnect() {
        return is_disconnect;
    }

    public String getCl_en() {
        return cl_en;
    }

    public String getUv_on() {
        return uv_on;
    }

    public String getId() {
        return id;
    }

    public int getUpd_state() {
        return upd_state;
    }

    public String getVer() {
        return ver;
    }

    public String getCreate_time() {
        return create_time;
    }

    public int getCl_dur() {
        return cl_dur;
    }

    public String getCtrl_pwd() {
        return ctrl_pwd;
    }

    public String getCl_state() {
        return cl_state;
    }

    public String getCl_sche() {
        return cl_sche;
    }

    public String getCl_tm() {
        return cl_tm;
    }

    public String getDevice_state() {
        return device_state;
    }

    public String getHb() {
        return hb;
    }

    public String getOut_state_a() {
        return out_state_a;
    }

    public String getOut_state_b() {
        return out_state_b;
    }

    public String getPwd() {
        return pwd;
    }

    public String getDid() {
        return did;
    }

//    ---------------------------------水泵相关属性
    /**
     * 当前功率档位	0：停机
     * 1 - 5：最小到最大档位
     */
    private int gear;
    /**
     * 实时信息推送周期	单位：分钟
     * 范围：1 - 1440
     */
    private int iCyc;
    /**
     * 实时转速	转/分钟
     */
    private int spd;

    public void setPhl(double phl) {
        this.phl = phl;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public int getiCyc() {
        return iCyc;
    }

    public void setiCyc(int iCyc) {
        this.iCyc = iCyc;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getFcd() {
        return fcd;
    }

    public String getOa_per() {
        return oa_per;
    }

    public static class TimePeriod implements Serializable {
        //        "{\"h0\":0,\"m0\":0,\"h1\":0,\"m1\":0}
        private int h0;
        private int m0;
        private int h1;
        private int m1;

        //adt设备相关字段
        private int en;
        private int w;
        private int b;
        private int g;
        private int r;

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getEn() {
            return en;
        }

        public void setEn(int en) {
            this.en = en;
        }

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int getH0() {
            return h0;
//            return parseNumberwei(h0,"00");
        }

        public void setH0(int h0) {
            this.h0 = h0;
        }

        public int getM0() {
            return m0;
//            return parseNumberwei(m0,"00");
        }

        public void setM0(int m0) {
            this.m0 = m0;
        }

        public int getH1() {
            return h1;
//            return parseNumberwei(h1,"00");
        }

        public void setH1(int h1) {
            this.h1 = h1;
        }

        public int getM1() {
            return m1;
//            return parseNumberwei(m1,"00");
        }

        public void setM1(int m1) {
            this.m1 = m1;
        }
    }
}
