package com.itboye.pondteam.responsitory;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.gson.reflect.TypeToken;
import com.itboye.pondteam.app.MyApplication;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.bean.DeviceListBean;
import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.bean.PondTeamMostNewModel;
import com.itboye.pondteam.bean.ProductBean;
import com.itboye.pondteam.bean.TemperatureHistoryBean;
import com.itboye.pondteam.bean.VertifyBean;
import com.itboye.pondteam.interfaces.IUserInfoInterface;
import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.volley.BaseErrorListener;
import com.itboye.pondteam.volley.BaseNetRepository;
import com.itboye.pondteam.volley.BaseSuccessReqListener;
import com.itboye.pondteam.volley.ByJsonRequest;
import com.itboye.pondteam.volley.ICompleteListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.itboye.pondteam.utils.MyTimeUtil.getTimeZone;


/**
 * Created by admin on 2016/11/28.
 */

public class UserResponsitory extends BaseNetRepository implements
        IUserInfoInterface<PersonDataBean> {


    private ByJsonRequest.Builder byjsonRequest;
    private String getMostNewWaterPumpDevice = "By_SunsunWaterPump_queryLatest";
    private String cameraUnBind = "By_SunsunSlaveDevice_unbind";//从设备解除绑定
    String api = "103";//接口版本号
    private String loginTypeKey = "By_User_login";// 用户登录
    private String registerByEmail = "By_User_registerByEmail";//用户注册(通过邮箱)
    private String sendVertificationCode = "By_SecurityCode_send";//发送验证码
    private String yanzhengVertificationCode = "By_SecurityCode_verify";//验证验证码
    private String updatePassByEmail = "By_User_updatePwdByEmail";//用户修改密码
    private String updateXinxi = "By_User_update";//修改个人信息
    private String modify_pass = "By_User_updatePwdByOldPwd";//用户修改密码（通过旧密码）
    private String address_pass = "By_Address_add";//添加收货地址
    private String address_query = "By_Address_query";//查询收货地址
    private String delete_address = "By_Address_delete";//删除收货地址
    private String update_address = "By_Address_update";//更新收货地址
    private String renzheng = "By_Worker_apply";//司机认证
    private String jigongRenZheng = "By_Worker_setSkill";//技工认证
    private String weizhiUpdate = "By_User_updateLatLng";//地理位置更新

    private String configApp = "By_Config_app";//获取全局配置
    private String allAll = "By_ShoppingCart_bulkAdd";//批量添加购物车
    private String moren_address = "By_Address_getDefault";//获取默认收货地址
    private String getDeviceJiaReBangDetail = "By_SunsunHeatingRod_deviceInfo";//加热棒获取设备信息
    private String bindDevice = "By_SunsunFilterVat_bindUid";//绑定设备
    private String devidceList = "By_SunsunUserDevice_query";//设备列表
    //---------------------------------------设备设置相关---------------------------------        |
    private String devidceSet_jiarebang = "By_SunsunHeatingRod_devicesCtrl";//加热棒设置接口      |
    private String deviceSet_shuiBeng = "By_SunsunWaterPump_devicesCtrl";//水泵设置接口             |
    private String deviceSet_806 = "By_SunsunAq806_devicesCtrl";//806设置接口                    |
    private String deviceSet_300 = "By_SunsunAph300_devicesCtrl";//Aph300参数设置                |
    private String devidceSet = "By_SunsunFilterVat_devicesCtrl";//过滤桶设备设置
    private String deviceSet_led = "By_SunsunAdt_devicesCtrl";//adt是设备设置                       |
    //----------------------------------------------------------------------------------------

    private String addDevice = "By_SunsunUserDevice_add";//设备添加
    private String devidceDelete = "By_SunsunUserDevice_del";//设备删除
    private String deviceUpdate = "By_SunsunUserDevice_change";//设备修改
    private String update806ph = "By_SunsunUserDevice_updateAq806Extra";//806中的ph设备信息修改
    private String getMostNewJiaReBangDevice = "By_SunsunHeatingRod_queryLatest";//获取最新的加熱棒版本
    private String getMostNewPondDevice = "By_SunsunFilterVat_queryLatest";//获取最新的过滤桶版本
    private String beginUpdateVersionOfJiaReBang = "By_SunsunHeatingRod_updateFirmware";//开始更新加热棒

    //---------------------------------------获取设备信息相关--------------------------------------- /
    private String getDeviceGuoLvTongDeviceDetail = "By_SunsunFilterVat_deviceInfo";//过滤桶获取设备信息
    private String getDeviceWaterPunmpDetail = "By_SunsunWaterPump_deviceInfo";//获取变频水泵
    private String getDeviceJiaReBangStatus = "By_SunsunHeatingRod_deviceInfo";//加热棒获取设备信息
    private String getDevicePhdetail = "By_SunsunAph300_deviceInfo";//300Ph获取设备信息
    private String getDeviceStatus = "By_SunsunFilterVat_deviceInfo";//过滤桶获取设备信息
    private String getDeviceLedLightDetail = "By_SunsunAdt_deviceInfo";//ADT设备详情

    private String feedBack = "By_Suggest_add";//用户反馈
    private String getHistoryTemper_aq806 = "By_SunsunAq806_queryHistoryTemp";//获取加热棒的历史水温
    private String getHistoryTemper_jiarebang = "By_SunsunHeatingRod_queryHistoryTemp";//获取加热棒的历史水温
    private String getHistoryPh_aq806 = "By_SunsunAq806_queryHistoryPh";//获取806ph的历史
    private String updatePassByPhone = "By_User_updatePwd";//通过手机号码改密码
    private String registerByPhone = "By_User_register";
    private String getMostNewAq = "By_SunsunAq806_queryLatest";//獲取806最新


    private String authDevicePwd_pondteam = "By_SunsunFilterVat_auth";//验证设备
    private String authDevicePwd_HeatingRod = "By_SunsunHeatingRod_auth";//验证设备
    private String authDevicePwd_aq806 = "By_SunsunAq806_auth";//验证设备
    private String authDevicePwd_ph300 = "By_SunsunAph300_auth";//验证设备
    private String authDevicePwd_WaterPump = "By_SunsunWaterPump_auth";//验证设备
    private String authDevicePwd_Adt = "By_SunsunAdt_auth";//验证设备
    private String getJinLiGangDetail = "By_SunsunAq806_deviceInfo";//
    private String beginUpdateVersionOfAq806 = "By_SunsunAq806_updateFirmware";//开始更新806
    private String cameraQuery = "By_SunsunSlaveDevice_query";//从设备查询
    private String cameraBind = "By_SunsunSlaveDevice_bind";//从设备绑定
    private String getHistoryPh_300Ph = "By_SunsunAph300_queryHistoryPh";
    private String getHistoryTemper_300Ph = "By_SunsunAph300_queryHistoryTemp";//
    private String getMostNewjiarebangDevice = "By_SunsunFilterVat_queryLatest";//获取最新的加热棒版本
    private String getMostNew806Device = "By_SunsunFilterVat_queryLatest";//获取最新的806版本
    private String getMostNewph300Device = "By_SunsunFilterVat_queryLatest";//获取最新的aph300版本

    private String beginUpdatePondTeam = "By_SunsunFilterVat_updateFirmware";//开始更新过滤桶
    private String beginUpdateJiaRebang = "By_SunsunHeatingRod_updateFirmware";//开始更新加热棒
    private String beginUpdateaq806 = "By_SunsunAq806_updateFirmware";//开始更新aq806
    private String beginUpdateaph300 = "By_SunsunAph300_updateFirmware";//开始更新aph300
    private String beginUpdateShuiBeng = "By_SunsunWaterPump_updateFirmware";//开始更新aph300

    private String updatePass = "By_User_updatePwdByEmail";//用户修改密码
    private String updateJiaoZhunTime = "By_SunsunUserDevice_updateAph300Extra";//修改校准时间

    private String jiaReBangExtraUpdate = "By_SunsunUserDevice_updateHeatingExtra";//加热棒额外信息修改
    private String beginUpdateADT = "By_SunsunAdt_updateFirmware";
    private String getMostNewADT = "By_SunsunAdt_queryLatest";
    private String adtExtraUpdate = "By_SunsunUserDevice_updateAdtExtra";
    private String shuibengExtraUpdate = "By_SunsunUserDevice_updateWaterPumpExtra";
    private String sendEmailCode = "By_SecurityCode_sendEmail";
    private String authDevicePwd_CP = "By_SunsunCp1000_auth";//CP1000验证接口
    private String getDeviceCP1000Detail = "By_SunsunCp1000_deviceInfo";//获取CP1000设备信息接口
    private String getDeviceWeishiQi = "By_SunsunWeiShiQi_deviceInfo";//获取喂食器设备信息接口
    private String updateMobileMsg = "By_SunsunUserDevice_update";//更改设备信息
    private String deviceSet_qibeng = "By_SunsunCp1000_devicesCtrl";//CP1000設置接口


    //---------------------------------------------------------森森2.0版本 接口-----------------------------
    private String BY_ProductCenter_cate = "BY_ProductCenter_cate";//产品
    private String BY_ProductCenter_post = "BY_ProductCenter_post";//产品/视频列表
    private String BY_ProductCenter_search = "BY_ProductCenter_search";//搜索

    public UserResponsitory(ICompleteListener iCompleteListener) {
        super(iCompleteListener);
        byjsonRequest = new ByJsonRequest.Builder();
        byjsonRequest.setBaseWrapUrl(Const.xiaoli_wrapUrl);
    }

    @Override
    public void deviceSet(String did, String oa_name, String ob_name, String clEn, int clWeek, String clTm, String clDur, String clState, String clCfg, String uvOn, String uvOff, String uvWH, String uvCfg, String uvState, int out_state_a, int out_state_b, String oa_on_tm, String oa_off_tm, String ob_per, String oa_per, int ws_on_tm, int ws_off_tm, String requestType) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (!clEn.equals("")) {
            map.put("cl_en", clEn);
        }
        if (oa_name != null) {
            map.put("oa_name", oa_name);
        }
        if (ob_name != null) {
            map.put("ob_name", ob_name);
        }
        if (clWeek != -1) {
            map.put("cl_week", clWeek);
        }
        if (!clTm.equals("")) {
            map.put("cl_tm", clTm);
        }
        if (!clDur.equals("")) {
            map.put("cl_dur", clDur);
        }
        if (!clState.equals("")) {
            map.put("cl_state", clState);
        }
        if (!clCfg.equals("")) {
            map.put("cl_cfg", clCfg);
        }
        if (!uvOn.equals("")) {
            map.put("uv_on", uvOn);
        }
        if (!uvOff.equals("")) {
            map.put("uv_off", uvOff);
        }

        if (!uvWH.equals("")) {
            map.put("uv_wh", uvWH);
        }
        if (!uvCfg.equals("")) {
            map.put("uv_cfg", uvCfg);
        }
        if (!uvState.equals("")) {
            map.put("uv_state", uvState);
        }
        if (out_state_a != -1) {
            map.put("out_state_a", out_state_a);
        }
        if (out_state_b != -1) {
            map.put("out_state_b", out_state_b);
        }
        if (!oa_on_tm.equals("")) {
            map.put("oa_on_tm", oa_on_tm);
        }
        if (!oa_off_tm.equals("")) {
            map.put("oa_off_tm", oa_off_tm);
        }
        if (!ob_per.equals("")) {
            map.put("ob_per", ob_per);
        }
        if (!oa_per.equals("")) {
            map.put("oa_per", oa_per);
        }
        if (ws_on_tm != -1) {
            map.put("ws_on_tm", ws_on_tm);
        }
        if (ws_off_tm != -1) {
            map.put("ws_off_tm", ws_off_tm);
        }
        map.put("debug", "0");
        byjsonRequest
                .setTypeVerParamsAndReturnClass(devidceSet, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    /**
     * 修改密码
     *
     * @param sid
     * @param code     验证码[必须](itboye是万能验证码，测试其它请调用验证码接口获取)
     * @param mobile   邮箱
     * @param password 新密码[必须]（6-32位）
     */
    @Override
    public void updatePass(String sid, String code, String mobile, String password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sid);
        map.put("code", code);
        map.put("email", mobile);
        map.put("password", password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updatePass, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDeviceStatus(String did) {
        Type type = new TypeToken<DeviceDetailModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getDeviceStatus, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<DeviceDetailModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getMostNewDevice(String did) {
        Type type = new TypeToken<PondTeamMostNewModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        String typeKey = getMostNewPondDevice;
        if (did.startsWith("S01")) {
            typeKey = getMostNewPondDevice;
        } else if (did.startsWith("S02")) {
            typeKey = getMostNewjiarebangDevice;
        } else if (did.startsWith("S03")) {
            typeKey = getMostNew806Device;
        } else if (did.startsWith("S04")) {
            typeKey = getMostNewph300Device;
        } else if (did.startsWith("S05")) {
            typeKey = getMostNewWaterPumpDevice;
        } else if (did.startsWith("S06")) {
            typeKey = getMostNewADT;
        }
        map.put("did", did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typeKey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<PondTeamMostNewModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void beginUpdatePondTeam(String did) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        String typeKey = beginUpdatePondTeam;
        if (did.startsWith("S01")) {
            typeKey = beginUpdatePondTeam;
        } else if (did.startsWith("S02")) {
            typeKey = beginUpdateJiaRebang;
        } else if (did.startsWith("S03")) {
            typeKey = beginUpdateaq806;
        } else if (did.startsWith("S04")) {
            typeKey = beginUpdateaph300;
        } else if (did.startsWith("S05")) {
            typeKey = beginUpdateShuiBeng;
        } else if (did.startsWith("S06")) {
            typeKey = beginUpdateADT;
        }
        map.put("did", did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typeKey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void login(String country, String username, String pwd, String appType) {
        Type type = new TypeToken<PersonDataBean>() {
        }.getType();
        String apiVer = api;
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", pwd);
        map.put("device_type", "android");
        map.put("country", country);
//        map.put("role","role_skilled_worker");
        map.put("device_token", getDeviceToken());
        String key = loginTypeKey;
        if (appType.equals("森森新零售")) {
            key = loginTypeKey;
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(key, "104", map, type)
                .requestListener(
                        new BaseSuccessReqListener<PersonDataBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    @Override
    public void registerByEmail(String nickname, String contact, String address, String email, String password, String code) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("contact", contact);
        map.put("address", address);
        map.put("email", email);
        map.put("password", password);
        map.put("from", "0");
        map.put("code", code);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(registerByEmail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
     * 发送验证码
     *
     * @param country   +86
     * @param mobile
     * @param code_type
     */

    @Override
    public void sendVerificationCode(String country, String mobile, String code_type, int appType) {
//        if (appType == 1) {
//            s = VertifyBean.class;
//        }
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("country", country);
        map.put("mobile", mobile);
        map.put("code_type", code_type);
        /*appType==1：零售   appType==0 小鲤*/
        map.put("send_type", appType == 1 ? "1" : "sms");
        Type type = new TypeToken<String>() {
        }.getType();
        if (appType == 1) {
            type = new TypeToken<VertifyBean>() {
            }.getType();
            byjsonRequest
                    .setTypeVerParamsAndReturnClass(sendVertificationCode, apiVer, map, type)
                    .requestListener(
                            new BaseSuccessReqListener<VertifyBean>(
                                    getListener()))
                    .errorListener(new BaseErrorListener(getListener()))
                    .desEncodeThenBuildAndSend();
        } else {
            byjsonRequest
                    .setTypeVerParamsAndReturnClass(sendVertificationCode, apiVer, map, type)
                    .requestListener(
                            new BaseSuccessReqListener<String>(
                                    getListener()))
                    .errorListener(new BaseErrorListener(getListener()))
                    .desEncodeThenBuildAndSend();
        }
    }

    /**
     * 验证验证码
     *
     * @param country
     * @param mobile
     * @param code
     * @param code_type
     */
    @Override
    public void YanZhengverificationCode(String country, String mobile, String code, String code_type) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("country", country);
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("code_type", code_type);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(yanzhengVertificationCode, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    /**
     * 修改密码
     *
     * @param code     验证码[必须](itboye是万能验证码，测试其它请调用验证码接口获取)
     * @param mobile   邮箱
     * @param password 新密码[必须]（6-32位）
     */
    @Override
    public void updatePassByEmail(String sid, String code, String mobile, String password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sid);
        map.put("code", code);
        map.put("email", mobile);
        map.put("password", password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updatePassByEmail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updatePassByPhone(String sid, String country, String code, String mobile, String password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", sid);
        map.put("country", country);
        map.put("code", code);
        map.put("mobile", mobile);
        map.put("password", password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updatePassByPhone, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
     * 修改个人信息
     *
     * @param uid
     * @param types   (nickname:昵称;  sex:性别(0,1); sign:个性签名; email:邮箱； weixin：微信； company：公司； job_title：岗位；loc_country：所在国家编码； loc_area：地区)
     * @param content
     */
    @Override
    public void upadtaInformation(String uid, String types, String content) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put(types, content);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updateXinxi, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
     * 修改密码（通过旧密码）
     *
     * @param uid
     * @param password
     * @param new_password
     */
    @Override
    public void modifyPass(String uid, String password, String new_password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("password", password);
        map.put("new_password", new_password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(modify_pass, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addressAdd(String s_id, String uid, String contactname, String mobile, String postal_code, String province, String city, String area, String detailinfo, String defaults, String country, String country_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "102";
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", s_id);
        map.put("uid", uid);
        map.put("contactname", contactname);
        map.put("mobile", mobile);
        map.put("postal_code", postal_code);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("detailinfo", detailinfo);
        map.put("default", defaults);
        map.put("country", country);
        map.put("country_id", country_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(address_pass, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
     * 更新收货地址
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
    @Override
    public void updateAddress(String s_id, String id, String uid, String contactname, String mobile, String postal_code, String province, String city, String area, String detailinfo, String defaults, String country, String country_id) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("s_id", s_id);
        map.put("id", id);
        map.put("uid", uid);
        map.put("contactname", contactname);
        map.put("mobile", mobile);
        map.put("postal_code", postal_code);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("detailinfo", detailinfo);
        map.put("default", defaults);
        map.put("country", country);
        map.put("country_id", country_id);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(update_address, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    @Override
    public void getConfigApp() {
//        Type type = new TypeToken<ConfigBean>() {
//        }.getType();
//        String apiVer = "100";
//        Map<String, Object> map = new HashMap<>();
//        (new ByJsonRequest.Builder<ConfigBean>())
//                .setTypeVerParamsAndReturnClass(configApp, apiVer, map, type)
//                .requestListener(
//                        new BaseSuccessReqListener<ConfigBean>(
//                                getListener()))
//                .errorListener(new BaseErrorListener(getListener()))
//                .desEncodeThenBuildAndSend();
    }

    String updatePwdByPwd = "By_User_updatePwdByOldPwd";//通过旧密码修改密码

    @Override
    public void updatePwdByPwd(String uid, String oldPass, String newPass) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";

        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("password", oldPass);
        map.put("new_password", newPass);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updatePwdByPwd, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDeviceDetailInfo(String did, String uid) {
        Type type = new TypeToken<DeviceDetailModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        map.put("uid", uid);
        String typeKey = getDeviceJiaReBangDetail;
        if (did.startsWith("S03")) {
            //806获取设备详情
            typeKey = getJinLiGangDetail;
        }
        if (did.startsWith("S01")) {
            //806获取设备详情
            typeKey = getDeviceGuoLvTongDeviceDetail;
        }
        if (did.startsWith("S04")) {
            typeKey = getDevicePhdetail;
        }
        if (did.startsWith("S05")) {
            typeKey = getDeviceWaterPunmpDetail;
        }
        if (did.startsWith("S06")) {
            typeKey = getDeviceLedLightDetail;
        }
        if (did.startsWith("S07")) {
            typeKey = getDeviceCP1000Detail;
        }
        if (did.startsWith("S08")) {
            typeKey = getDeviceWeishiQi;
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typeKey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<DeviceDetailModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void bindDevice(String did, String uid) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";

        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(bindDevice, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getMyDeviceList(String uid) {
        Type type = new TypeToken<ArrayList<DeviceListBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(devidceList, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<DeviceListBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deviceSet_jiarebang(String did, String t_set, String t_cyc, String cfg, String dev_lock) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (!t_set.equals("")) {
            map.put("t_set", t_set);
        }
        if (!t_cyc.equals("")) {
            map.put("t_cyc", t_cyc);
        }
        if (!cfg.equals("")) {
            map.put("cfg", cfg);
        }
        if (!dev_lock.equals("")) {
            map.put("dev_lock", dev_lock);
        }

        byjsonRequest
                .setTypeVerParamsAndReturnClass(devidceSet_jiarebang, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void addDevice(String uid, String did, String device_nickname, String device_type, String extra) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        map.put("uid", uid);
        map.put("device_nickname", device_nickname);
        map.put("device_type", device_type);
        int s = getTimeZone();
        map.put("timezone", s);
        map.put("lang", "zh-cn");
        map.put("extra", extra);
        String typeKey = addDevice;
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typeKey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deleteDevice(String id, String uid) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(devidceDelete, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateDeviceName(String id, String nickName, String chazuoA, String chazuoB, String temp_min, String temp_max, int temp_alert, int is_state_notify) {

        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "103";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (!nickName.equals("")) {
            map.put("device_nickname", nickName);
        }
        if (!chazuoA.equals("")) {
            map.put("nickname_a", chazuoA);
        }
        if (!chazuoB.equals("")) {
            map.put("nickname_b", chazuoB);

        }
        if (!temp_min.equals("")) {
            map.put("temp_min", temp_min);

        }
        if (!temp_max.equals("")) {
            map.put("temp_max", temp_max);

        }
        if (temp_alert != -1) {
            map.put("temp_alert", temp_alert);

        }
        if (is_state_notify != -1) {
            map.put("is_state_notify", is_state_notify);

        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deviceUpdate, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getMostNewPondDevice(String did, String deviceType) {
        Type type = new TypeToken<PondTeamMostNewModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        String str = getMostNewPondDevice;
        if (type.equals("S01")) {
            str = getMostNewPondDevice;
        } else if (type.equals("S02")) {
            str = getMostNewJiaReBangDevice;
        }
        if (type.equals("S03")) {
            str = getMostNewAq;
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(str, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<PondTeamMostNewModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDeviceGuoLvTongStatus(String did) {
        Type type = new TypeToken<DeviceDetailModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getDeviceGuoLvTongDeviceDetail, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<DeviceDetailModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDeviceJiaReBangStatus(String did) {
        Type type = new TypeToken<DeviceDetailModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(getDeviceJiaReBangStatus, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<DeviceDetailModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void feedback(String deviceType, String name, String email, String tel, String uid, String text) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("device_type", deviceType);
        map.put("name", name);
        map.put("email", email);
        map.put("tel", tel);
        map.put("uid", uid);
        map.put("text", text);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(feedBack, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateJiaReBangVersion(String did) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        String typeKey = beginUpdateVersionOfJiaReBang;
        if (did.startsWith("S02")) {
            typeKey = beginUpdateVersionOfJiaReBang;
        } else if (did.startsWith("S03")) {
            typeKey = beginUpdateVersionOfAq806;
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(beginUpdateVersionOfJiaReBang, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }


    @Override
    public void getHistoryTemper(String did, String date_type, boolean isPh) {
        Type type = new TypeToken<ArrayList<TemperatureHistoryBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        map.put("date_type", date_type);
        String typeKey = getHistoryTemper_aq806;
        if (isPh) {
            if (did.startsWith("S03")) {
                typeKey = getHistoryPh_aq806;
            } else if (did.startsWith("S04")) {
                typeKey = getHistoryPh_300Ph;
            }
        } else {
            if (did.startsWith("S02")) {
                typeKey = getHistoryTemper_jiarebang;
            } else if (did.startsWith("S03")) {
                typeKey = getHistoryTemper_aq806;
            } else if (did.startsWith("S04")) {
                typeKey = getHistoryTemper_300Ph;
            }
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typeKey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<TemperatureHistoryBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void registerByPhone(String s, String username, String code, String password) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "102";
        Map<String, Object> map = new HashMap<>();
        map.put("country", s);
        map.put("username", username);
        map.put("code", code);
        map.put("password", password);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(registerByPhone, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void authDevicePwd(String did, String ctrl_pwd, String deviceType) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        map.put("ctrl_pwd", ctrl_pwd);
        String typekey = authDevicePwd_pondteam;
        if (deviceType.startsWith("S01")) {
            //过滤桶
            typekey = authDevicePwd_pondteam;
        } else if (deviceType.startsWith("S02")) {
            //加热棒
            typekey = authDevicePwd_HeatingRod;
        } else if (deviceType.equals("S03") || deviceType.equals("S03-1") || deviceType.equals("S03-2")) {
            //806
            typekey = authDevicePwd_aq806;
        } else if (deviceType.startsWith("S04")) {
            //300 PH
            typekey = authDevicePwd_ph300;
        } else if (deviceType.startsWith("S05")) {
            //变频水泵
            typekey = authDevicePwd_WaterPump;
        } else if (deviceType.startsWith("S06")) {
            //adt验证
            typekey = authDevicePwd_Adt;
        } else if (deviceType.startsWith("S07")) {
            //CP1000验证
            typekey = authDevicePwd_CP;
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typekey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    /**
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
    @Override
    public void deviceSet_806(String did, String tm_1, String mode, String out_uvc, String out_sp, String out_l, String tMax, String th, String tl, String l_per, String uvc_per, String sp_per, String push_cfg, String dev_lock, int uv_wh, int p_wh, int l_wh, int ph_cmd, String requestType) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (!tm_1.equals("")) {
            map.put("tm_l", (tm_1));
        }
        if (!mode.equals("")) {
            map.put("mode", mode);
        }
        if (!out_uvc.equals("")) {
            map.put("out_uvc", out_uvc);
        }
        if (!out_sp.equals("")) {
            map.put("out_sp", out_sp);
        }
        if (!out_l.equals("")) {
            map.put("out_l", out_l);
        }
        if (!tMax.equals("")) {
            map.put("tMax", tMax);
        }
        if (!th.equals("")) {
            map.put("th", th);
        }
        if (!tl.equals("")) {
            map.put("tl", tl);
        }
        if (!l_per.equals("")) {
            map.put("l_per", l_per);
        }
        if (!uvc_per.equals("")) {
            map.put("uvc_per", uvc_per);
        }
        if (!sp_per.equals("")) {
            map.put("sp_per", sp_per);
        }
        if (!push_cfg.equals("")) {
            map.put("push_cfg", push_cfg);
        }
        if (!dev_lock.equals("")) {
            map.put("dev_lock", dev_lock);
        }
        // uv_wh,int p_wh,int l_wh
        if (uv_wh != -1) {
            map.put("uv_wh", uv_wh);
        }
        if (p_wh != -1) {
            map.put("p_wh", p_wh);
        }
        if (l_wh != -1) {
            map.put("l_wh", l_wh);
        }
        if (ph_cmd != -1) {
            map.put("ph_cmd", ph_cmd);
        }
        map.put("debug", "0");
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deviceSet_806, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deviceSet_300Ph(String did, double th, double tl, double phh, double phl, int push_cfg, int dev_lock, int d_cyc, int ph_dly, int ph_cmd) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (th != -1) {
            map.put("th", th);
        }
        if (tl != -1) {
            map.put("tl", tl);
        }
        if (phh != -1) {
            map.put("phh", phh);
        }
        if (phl != -1) {
            map.put("phl", phl);
        }
        if (push_cfg != -1) {
            map.put("push_cfg", push_cfg);

        }
        if (dev_lock != -1) {
            map.put("dev_lock", dev_lock);
        }
        if (d_cyc != -1) {
            map.put("d_cyc", d_cyc);
        }

        if (ph_dly != -1) {
            map.put("ph_dly", ph_dly);
        }
        if (ph_cmd != -1) {
            map.put("ph_cmd", ph_cmd);
        }
        map.put("debug", "1");
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deviceSet_300, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deviceSet_shuiBeng(String did, int devLock, int i_cyc, int gear, int cfg, int state, int fcd, int wh, int wg, int we, int wc, String requestType) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (i_cyc != -1) {
            map.put("i_cyc", i_cyc);
        }
        if (gear != -1) {
            map.put("gear", gear);
        }
        if (cfg != -1) {
            map.put("cfg", cfg);
        }
        if (devLock != -1) {
            map.put("dev_lock", devLock);
        }
        if (state != -1) {
            map.put("state", state);
        }
        if (fcd != -1) {
            map.put("fcd", fcd);
        }

        if (wh != -1) {
            map.put("wh", wh);
        }
        if (wg != -1) {
            map.put("wg", wg);
        }
        if (we != -1) {
            map.put("we", we);
        }
        if (wc != -1) {
            map.put("wc", wc);
        }
        map.put("debug", "0");
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deviceSet_shuiBeng, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void cameraQuery(String did) {
        Type type = new TypeToken<ArrayList<DeviceListBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("master_did", did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(cameraQuery, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<DeviceListBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void cameraBind(String master_did, String slave_did, String slave_device_type, String slave_name, String slave_pwd) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("master_did", master_did);
        map.put("slave_did", slave_did);
        //（炽鸟摄像头：ciniao_wifi_camera）暂时只有这一个
        map.put("slave_device_type", slave_device_type);
        map.put("slave_name", slave_name);
        map.put("slave_pwd", slave_pwd);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(cameraBind, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void cameraUnBind(String master_did, String slave_did) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("master_did", master_did);
        map.put("slave_did", slave_did);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(cameraUnBind, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateJiaoZhunTime(String id, int push_cfg, int temp_low, int temp_high, int ph_low, int ph_high, long first_upd, long last_upd) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (push_cfg != -1) {
            map.put("push_cfg", push_cfg);
        }
        if (temp_low != -1) {
            map.put("temp_l", temp_low);
        }
        if (temp_high != -1) {
            map.put("temp_h", temp_high);
        }
        if (ph_low != -1) {
            map.put("ph_l", ph_low);
        }
        if (ph_high != -1) {
            map.put("ph_h", ph_high);
        }
        if (first_upd != -1) {
            map.put("first_update_time", first_upd);
        }
        if (last_upd != -1) {
            map.put("last_update_time", last_upd);
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updateJiaoZhunTime, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deviceSet_806Ph(String id, int ph_on, int ph_h, int ph_l, int temp_on, int temp_h, int temp_l) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (ph_on != -1) {
            map.put("ph_on", ph_on);
        }
        if (ph_h != -1) {
            map.put("ph_h", ph_h);
        }
        if (ph_l != -1) {
            map.put("ph_l", ph_l);
        }
        if (temp_on != -1) {
            map.put("temp_on", temp_on);
        }
        if (temp_h != -1) {
            map.put("temp_h", temp_h);
        }
        if (temp_l != -1) {
            map.put("temp_l", temp_l);
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(update806ph, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void jiaReBangExtraUpdate(String id, int abnormal) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("abnormal", abnormal);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(jiaReBangExtraUpdate, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void adtExtraUpdate(String id, String s) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("push", s);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(adtExtraUpdate, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deviceSet_led(String did, int mode, int push_cfg, int dev_lock, String str, int w, int b, int g, int r, int sw) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (mode != -1) {
            map.put("mode", mode);
        }
        if (push_cfg != -1) {
            map.put("push_cfg", push_cfg);
        }
        if (dev_lock != -1) {
            map.put("dev_lock", dev_lock);
        }
        if (w != -1) {
            map.put("w", w);
        }
        if (b != -1) {
            map.put("b", b);
        }
        if (g != -1) {
            map.put("g", g);
        }
        if (r != -1) {
            map.put("r", r);
        }
        if (sw != -1) {
            map.put("sw", sw);
        }
        if (!str.equals("")) {
            map.put("per", str);
        }
        map.put("debug", "0");
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deviceSet_led, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void shuibengExtraUpdate(String id, String push, int fcd, int state) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (!push.equals("")) {
            map.put("push_cfg", push);
        }
        if (fcd != -1) {
            map.put("fcd", fcd);
        }
        if (state != -1) {
            map.put("state", state);
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(shuibengExtraUpdate, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void sendEmailCode(String email, int code_type, int send_type) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("code_type", code_type);
        map.put("send_type", send_type);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(sendEmailCode, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void getDeviceOnLineState(String did, String uid) {
        Type type = new TypeToken<DeviceDetailModel>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        String typeKey = getJinLiGangDetail;
        if (did.startsWith("S03")) {
            //806获取设备详情
            typeKey = getJinLiGangDetail;
        }
        if (did.startsWith("S02")) {
            //806获取设备详情
            typeKey = getDeviceJiaReBangDetail;
        }
        if (did.startsWith("S01")) {
            //806获取设备详情
            typeKey = getDeviceGuoLvTongDeviceDetail;
        }
        if (did.startsWith("S04")) {
            typeKey = getDevicePhdetail;
        }
        if (did.startsWith("S05")) {
            typeKey = getDeviceWaterPunmpDetail;
        }
        if (did.startsWith("S06")) {
            typeKey = getDeviceLedLightDetail;
        }
        if (did.startsWith("S07")) {
            typeKey = getDeviceCP1000Detail;
        }
        map.put("did", did);
        map.put("uid", uid);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(typeKey, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<DeviceDetailModel>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void updateMobileMsg(String uid, String device_id, String lang, String timezone) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "101";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("device_id", device_id);
        map.put("lang", lang);
        map.put("timezone", timezone);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(updateMobileMsg, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void deviceSet_qibeng(String did, int dev_lock, int mode, int state, int gear, int wh, int ch_cnt, int b_life, int push_cfg) {
        Type type = new TypeToken<String>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("did", did);
        if (dev_lock != -1) {
            map.put("dev_lock", dev_lock);
        }
        if (mode != -1) {
            map.put("mode", mode);
        }
        if (state != -1) {
            map.put("state", state + "");
        }
        if (gear != -1) {
            map.put("gear", gear);
        }
        if (wh != -1) {
            map.put("wh", wh);
        }
        if (ch_cnt != -1) {
            map.put("ch_cnt", ch_cnt);
        }
        if (b_life != -1) {
            map.put("b_life", b_life);
        }
        if (push_cfg != -1) {
            map.put("push_cfg", push_cfg);
        }
        byjsonRequest
                .setTypeVerParamsAndReturnClass(deviceSet_qibeng, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<String>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void queryProductIndex(int parent) {
        Type type = new TypeToken<ArrayList<ProductBean.HomeListBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("parent", parent);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(BY_ProductCenter_cate, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<ProductBean.HomeListBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void queryProductPost(int cate_id, int is_video, int page, int size) {
        Type type = new TypeToken<ProductBean>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("cate_id", cate_id);
        map.put("is_video", is_video);
        map.put("page", page);
        map.put("size", size);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(BY_ProductCenter_post, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ProductBean>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    @Override
    public void productSearch(String name) {
        Type type = new TypeToken<ArrayList<ProductBean.HomeListBean>>() {
        }.getType();
        String apiVer = "100";
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        byjsonRequest
                .setTypeVerParamsAndReturnClass(BY_ProductCenter_search, apiVer, map, type)
                .requestListener(
                        new BaseSuccessReqListener<ArrayList<ProductBean.HomeListBean>>(
                                getListener()))
                .errorListener(new BaseErrorListener(getListener()))
                .desEncodeThenBuildAndSend();
    }

    public String getDeviceToken() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }
}
