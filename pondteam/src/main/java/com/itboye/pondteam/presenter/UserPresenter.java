package com.itboye.pondteam.presenter;

import com.itboye.pondteam.bean.PersonDataBean;
import com.itboye.pondteam.interfaces.IUserInfoInterface;
import com.itboye.pondteam.responsitory.UserResponsitory;
import com.itboye.pondteam.volley.BasePresenter;
import com.itboye.pondteam.volley.ICompleteListener;
import com.itboye.pondteam.volley.ResultEntity;

import java.util.Observer;


/**
 * Created by admin on 2016/11/28.
 */

public class UserPresenter extends BasePresenter implements
        IUserInfoInterface<PersonDataBean> {
    public static String cameraUnBind_success = "_cameraUnBind_success";
    public static String cameraUnBind_fail = "_cameraUnBind_fail";
    public static String deviceSet_shuiBengsuccess = "_deviceSet_shuiBengsuccess";
    public static String deviceSet_shuiBengfail = "_deviceSet_shuiBengfail";
    public static String updateJiaoZhunTime_success = "_updateJiaoZhunTime_success";
    public static String updateJiaoZhunTime_fail = "_updateJiaoZhunTime_fail";
    public static String update806ph_success = "_update806ph_success";
    public static String update806ph_fail = "_update806ph_fail";

    public static String jiaReBangExtraUpdate_success = "_jiaReBangExtraUpdate_success";
    public static String jiaReBangExtraUpdate_fail = "_jiaReBangExtraUpdate_fail";
    public static String adtExtraUpdate_success = "_adtExtraUpdate_success";
    public static String adtExtraUpdate_fail = "_adtExtraUpdate_fail";
    public static String deviceSet_led_success = "_deviceSet_led_success";
    public static String deviceSet_led_fail = "_deviceSet_led_fail";


    // 用户登录
    public static final String login_success = UserPresenter.class.getName()
            + "_User_success";
    public static final String login_fail = UserPresenter.class.getName()
            + "_User_fail";

    //注册
    public static final String register_fail = UserPresenter.class.getName()
            + "_Register_fail";
    public static final String register_success = UserPresenter.class.getName()
            + "_Register_success";
    //发送验证码
    public static String send_code_fail = "_Send_code_fail";
    public static String send_code_success = "_Send_code_success";
    //验证验证码
    public static String yanzheng_code_fail = "_yanzheng_code_fail";
    public static String yanzheng_code_success = "_yanzheng_code_success";
    //修改密码（通过验证码）
    public static String updata_pass_success = "_updata_pass_success";
    public static String updata_pass_fail = "_updata_pass_fail";
    //修改个人信息
    public static String updata_xinxi_success = "_updata_xinxi_success";
    public static String updata_xinxi_fail = "_updata_xinxi_fail";
    //修改密码（通过旧密码）
    public static String modify_pass_success = "_modify_pass_success";
    public static String modify_pass_fail = "_modify_pass_fail";
    //查询收货地址
    public static String query_address_success = "_query_address_success";
    public static String query_address_fail = "_query_address_fail";
    //添加收货地址
    public static String add_address_success = "_add_address_success";
    public static String add_address_fail = "_add_address_fail";
    //删除收货地址
    public static String delete_address_success = "_delete_address_success";
    public static String delete_address_fail = "_delete_address_fail";
    //更新收货地址
    public static String update_address_success = "_update_address_success";
    public static String update_address_fail = "_update_address_fail";

    //获取配置
    public static String config_success = "_config_success";
    public static String config_fail = "_config_fail";


    //批量添加购物车
    public static String addAll_success = "_addAll_success";
    public static String addAll_fail = "_addAll_fail";
    //获取默认收货地址
    public static String moren_address_success = "_moren_address_success";
    public static String moren_address_fail = "_moren_address_fail";

    //获取用户设备列表
    public static String getMyDeviceList_success = "_getMyDeviceList_success";
    public static String getMyDeviceList_fail = "_getMyDeviceList_fail";

    //设备设置
    public static String deviceSet_success = "_deviceSet_success";
    public static String deviceSet_fail = "_deviceSet_fail";

    //设备添加
    public static String adddevice_success = "_adddevice_success";
    public static String adddevice_fail = "_adddevice_fail";

    public static String deleteDevice_success = "_deleteDevice_success";
    public static String deleteDevice_fail = "_deleteDevice_fail";

    public static String update_devicename_success = "_update_devicename_success";
    public static String update_devicename_fail = "_update_devicename_fail";


    public static String getMostNewPondDevice_success = "_getMostNewPondDevice_success";
    public static String getMostNewPondDevice_fail = "_getMostNewPondDevice_fail";


    public static String getdeviceinfosuccess = "getdeviceinfosuccess";
    public static String getdeviceinfofail = "getdeviceinfofail";

    public static String feedBack_success = "feedBack_success";
    public static String feedBack_fail = "feedBack_fail";

    public static String updateJiaReBangVersionSuccess = "_updateJiaReBangVersionSuccess";
    public static String updateJiaReBangVersionFail = "_updateJiaReBangVersionFail";
    public static String getHistoryTemper_success = "_getHistoryTemper_success";
    public static String getHistoryTemper_fail = "getHistoryTemper_fail";
    public static String update_pass_bymobile_success = "_update_pass_bymobile_success";
    public static String update_pass_bymobile_fail = "_update_pass_bymobile_fail";
    public static String registerByPhone_success = "_registerByPhone_success";
    public static String registerByPhone_fail = "_registerByPhone_fail";

    public static String authDevicePwdsuccess = "_authDevicePwdsuccess";
    public static String authDevicePwdfail = "_authDevicePwdfail";
    public static String deviceSet_806success = "_deviceSet_806success";
    public static String deviceSet_806fail = "_deviceSet_806fail";
    public static String deviceSet_806FuWeisuccess = "_deviceSet_806FuWeisuccess";
    public static String deviceSet_806FuWeifail = "_deviceSet_806FuWeifail";
    public static String cameraQuery_success = "_cameraQuery_success";
    public static String cameraQuery_fail = "_cameraQuery_fail";
    public static String cameraBind_success = "_cameraBind_success";
    public static String cameraBind_fail = "_cameraBind_fail";
    public static String deviceSet_300success = "_deviceSet_300success";
    public static String deviceSet_300fail = "_deviceSet_300fail";

    public static String beginUpdatePondTeam_success = "_beginUpdatePondTeam_success";
    public static String beginUpdatePondTeam_fail = "_beginUpdatePondTeam_fail";
    public static String shuibengExtraUpdate_success = "_shuibengExtraUpdate_success";
    public static String shuibengExtraUpdate_fail = "_shuibengExtraUpdate_fail";
    public static String getDeviceOnLineState_success = "_getDeviceOnLineState_success";
    public static String getDeviceOnLineState_fail = "_getDeviceOnLineState_success";
    public static String updateMobileMsg_fail = "_updateMobileMsg_fail";
    public static String updateMobileMsg_success = "_updateMobileMsg_success";
    public static String deviceSet_qibeng_success = "_deviceSet_qibeng_success";
    public static String deviceSet_qibeng_fail = "_deviceSet_qibeng_fail";
    public static String queryProductIndex_success = "_queryProductIndex_success";
    public static String queryProductIndex_fail = "_queryProductIndex_fail";
    public static String queryProductPost_success = "_queryProductPost_success";
    public static String queryProductPost_fail = "_queryProductPost_fail";
    public static String productSearch_success = "_productSearch_success";
    public static String productSearch_fail = "_productSearch_success";
    public static String getBanners_success = "_getBanners_success";
    public static String getBanners_fail = "_getBanners_fail";
    public static String branchSearch_success="_branchSearch_success";
    public static String branchSearch_fail="_branchSearch_fail";


    public UserPresenter(Observer observer) {
        super(observer);
    }

    @Override
    public void getDeviceStatus(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getdeviceinfosuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getdeviceinfofail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }
                });
        user.getDeviceStatus(did);
    }

    @Override
    public void beginUpdatePondTeam(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(beginUpdatePondTeam_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(beginUpdatePondTeam_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.beginUpdatePondTeam(did);
    }

    @Override
    public void deviceSet(String did, String oa_name, String ob_name, String clEn, int clWeek, String clTm, String clDur, String clState, String clCfg, String uvOn, String uvOff, String uvWH, String uvCfg, String uvState, int out_state_a, int out_state_b, String oa_on_tm, String oa_off_tm, String ob_per, String oa_per, int ws_on_tm, int ws_off_tm, final String requestType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        if (!requestType.equals("")) {
                            result.setEventType(requestType);
                        } else {
                            result.setEventType(deviceSet_success);
                        }
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deviceSet_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.deviceSet(did, oa_name, ob_name, clEn, clWeek, clTm, clDur, clState, clCfg, uvOn, uvOff, uvWH, uvCfg, uvState, out_state_a, out_state_b, oa_on_tm, oa_off_tm, ob_per, oa_per, ws_on_tm, ws_off_tm, requestType);
    }


    @Override
    public void getMostNewDevice(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getMostNewPondDevice_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getMostNewPondDevice_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getMostNewDevice(did);
    }

    @Override
    public void login(String country, String username, String pwd, String appType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(login_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(login_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.login(country, username, pwd, appType);
    }


    @Override
    public void registerByEmail(String nickname, String contact, String address, String email, String password, String code) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(register_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(register_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }

                });
        user.registerByEmail(nickname, contact, address, email, password, code);
    }

    @Override
    public void sendVerificationCode(String country, String mobile, String code_type, int appType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(send_code_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(send_code_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.sendVerificationCode(country, mobile, code_type, appType);
    }

    @Override
    public void YanZhengverificationCode(String country, String mobile, String code, String code_type) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(yanzheng_code_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(yanzheng_code_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.YanZhengverificationCode(country, mobile, code, code_type);
    }

    @Override
    public void updatePassByEmail(String country, String code, String mobile, String password) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updata_pass_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updata_pass_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updatePassByEmail(country, code, mobile, password);
    }

    @Override
    public void updatePassByPhone(String sid, String country, String code, String mobile, String password) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(update_pass_bymobile_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(update_pass_bymobile_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updatePassByPhone(sid, country, code, mobile, password);
    }

    @Override
    public void updatePass(String country, String code, String mobile, String password) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updata_pass_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updata_pass_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updatePass(country, code, mobile, password);
    }

    @Override
    public void upadtaInformation(String uid, String types, String content) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updata_xinxi_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updata_xinxi_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.upadtaInformation(uid, types, content);
    }

    @Override
    public void modifyPass(String uid, String password, String new_password) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(modify_pass_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(modify_pass_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.modifyPass(uid, password, new_password);
    }

    @Override
    public void addressAdd(String s_id, String uid, String contactname, String mobile, String postal_code, String province, String city, String area, String detailinfo, String defaults, String country, String country_id) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(add_address_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(add_address_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.addressAdd(s_id, uid, contactname, mobile, postal_code, province, city, area, detailinfo, defaults, country, country_id);
    }

    @Override
    public void updateAddress(String s_id, String id, String uid, String contactname, String mobile, String postal_code, String province, String city, String area, String detailinfo, String defaults, String country, String country_id) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(update_address_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(update_address_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updateAddress(s_id, id, uid, contactname, mobile, postal_code, province, city, area, detailinfo, defaults, country, country_id);
    }

    @Override
    public void getConfigApp() {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(config_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(config_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getConfigApp();
    }


    @Override
    public void updatePwdByPwd(String uid, String oldPass, String newPass) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getdeviceinfosuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getdeviceinfofail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updatePwdByPwd(uid, oldPass, newPass);
    }


    @Override
    public void getDeviceDetailInfo(String did, String uid) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getdeviceinfosuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getdeviceinfofail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getDeviceDetailInfo(did, uid);
    }

    public static String bindDevicesuccess = "bindDeviceSuccess";
    public static String bindDevicefail = "bindDevicefail";

    @Override
    public void bindDevice(String did, String uid) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(bindDevicesuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(bindDevicefail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.bindDevice(did, uid);
    }

    @Override
    public void getMyDeviceList(String uid) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getMyDeviceList_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getMyDeviceList_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getMyDeviceList(uid);
    }

    @Override
    public void deviceSet_jiarebang(String did, String t_set, String t_cyc, String cfg, String dev_lock) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deviceSet_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deviceSet_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.deviceSet_jiarebang(did, t_set, t_cyc, cfg, dev_lock);
    }

    @Override
    public void addDevice(String uid, String did, String device_nickname, String device_type, String extra) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(adddevice_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(adddevice_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.addDevice(uid, did, device_nickname, device_type, extra);
    }

    @Override
    public void deleteDevice(String id, String uid) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deleteDevice_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deleteDevice_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.deleteDevice(id, uid);
    }

    @Override
    public void updateDeviceName(String id, String nickName, String chazuoA, String chazuoB, String temp_min, String temp_max, int temp_alert, int is_state_notify) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(update_devicename_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(update_devicename_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updateDeviceName(id, nickName, chazuoA, chazuoB, temp_min, temp_max, temp_alert, is_state_notify);
    }

    @Override
    public void getMostNewPondDevice(String did, String deviceType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getMostNewPondDevice_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getMostNewPondDevice_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getMostNewPondDevice(did, deviceType);
    }

    @Override
    public void getDeviceGuoLvTongStatus(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getdeviceinfosuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getdeviceinfofail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getDeviceGuoLvTongStatus(did);
    }

    @Override
    public void getDeviceJiaReBangStatus(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getdeviceinfosuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getdeviceinfofail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.getDeviceJiaReBangStatus(did);
    }

    @Override
    public void feedback(String deviceType, String name, String email, String tel, String uid, String text) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(feedBack_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(feedBack_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.feedback(deviceType, name, email, tel, uid, text);
    }

    @Override
    public void updateJiaReBangVersion(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updateJiaReBangVersionSuccess);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updateJiaReBangVersionFail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);

                    }
                });
        user.updateJiaReBangVersion(did);

    }

    @Override
    public void getHistoryTemper(String did, String date_type, boolean isPh) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getHistoryTemper_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getHistoryTemper_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }
                });
        user.getHistoryTemper(did, date_type, isPh);

    }

    @Override
    public void registerByPhone(String s, String username, String code, String password) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(registerByPhone_success);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(registerByPhone_fail);
                        UserPresenter.this.setChanged();
                        UserPresenter.this.notifyObservers(result);
                    }
                });
        user.registerByPhone(s, username, code, password);
    }

    @Override
    public void authDevicePwd(String did, String ctrl_pwd, String deviceType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(authDevicePwdsuccess);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(authDevicePwdfail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.authDevicePwd(did, ctrl_pwd, deviceType);
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
    public void deviceSet_806(String did, String tm_1, String mode, String out_uvc, String out_sp, String out_l, String tMax, String th, String tl, String l_per, String uvc_per, String sp_per, String push_cfg, String dev_lock, int uv_wh, int p_wh, int l_wh, final int ph_cmd, final String requestType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        if (ph_cmd == 1) {
                            result.setEventType(deviceSet_806FuWeisuccess);
                        } else if (!requestType.equals("")) {
                            result.setEventType(requestType);
                        } else {
                            result.setEventType(deviceSet_806success);
                        }
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        if (ph_cmd == 1) {
                            result.setEventType(deviceSet_806FuWeifail);
                        } else {
                            result.setEventType(deviceSet_806fail);
                        }
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deviceSet_806(did, tm_1, mode, out_uvc, out_sp, out_l, tMax, th, tl, l_per, uvc_per, sp_per, push_cfg, dev_lock, uv_wh, p_wh, l_wh, ph_cmd, requestType);
    }

    @Override
    public void deviceSet_300Ph(String did, double th, double tl, double phh, double phl, int push_cfg, int dev_lock, int d_cyc, int ph_dly, int ph_cmd) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deviceSet_300success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deviceSet_300fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deviceSet_300Ph(did, th, tl, phh, phl, push_cfg, dev_lock, d_cyc, ph_dly, ph_cmd);
    }

    @Override
    public void deviceSet_shuiBeng(String did, int devLock, int i_cyc, int gear, int cfg, int state, int fcd, int wh, int wg, int we, int wc, final String requestType) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        if (requestType.equals("")) {
                            result.setEventType(deviceSet_shuiBengsuccess);
                        } else {
                            result.setEventType(requestType);
                        }
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deviceSet_shuiBengfail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deviceSet_shuiBeng(did, devLock, i_cyc, gear, cfg, state, fcd, wh, wg, we, wc, requestType);
    }

    @Override
    public void cameraQuery(String did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(cameraQuery_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(cameraQuery_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.cameraQuery(did);
    }

    @Override
    public void cameraBind(String master_did, String slave_did, String slave_device_type, String slave_name, String slave_pwd) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(cameraBind_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(cameraBind_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.cameraBind(master_did, slave_did, slave_device_type, slave_name, slave_pwd);
    }

    @Override
    public void cameraUnBind(String master_did, String slave_did) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(cameraUnBind_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(cameraUnBind_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.cameraUnBind(master_did, slave_did);
    }

    @Override
    public void updateJiaoZhunTime(String id, int push_cfg, int temp_low, int temp_high, int ph_low, int ph_high, long firstTime, long lastTime) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updateJiaoZhunTime_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updateJiaoZhunTime_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.updateJiaoZhunTime(id, push_cfg, temp_low, temp_high, ph_low, ph_high, firstTime, lastTime);
    }

    public void deviceSet_806Ph(String id, int ph_on, int ph_h, int ph_l, int temp_on, int temp_h, int temp_l) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(update806ph_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(update806ph_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deviceSet_806Ph(id, ph_on, ph_h, ph_l, temp_on, temp_h, temp_l);
    }

    @Override
    public void jiaReBangExtraUpdate(String id, int abnormal) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(jiaReBangExtraUpdate_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(jiaReBangExtraUpdate_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.jiaReBangExtraUpdate(id, abnormal);
    }

    @Override
    public void adtExtraUpdate(String id, String s) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(adtExtraUpdate_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(adtExtraUpdate_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.adtExtraUpdate(id, s);
    }

    @Override
    public void deviceSet_led(String did, int mode, int push_cfg, int dev_lock, String str, int w, int b, int g, int r, int sw) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deviceSet_led_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deviceSet_led_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deviceSet_led(did, mode, push_cfg, dev_lock, str, w, b, g, r, sw);
    }

    @Override
    public void shuibengExtraUpdate(String id, String push_cfg, int fcd, int state) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(shuibengExtraUpdate_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(shuibengExtraUpdate_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.shuibengExtraUpdate(id, push_cfg, fcd, state);
    }

    @Override
    public void sendEmailCode(String customText, int code_type, int send_type) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {

                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(send_code_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(send_code_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.sendEmailCode(customText, code_type, send_type);
    }

    @Override
    public void getDeviceOnLineState(String did, String uid) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getDeviceOnLineState_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getDeviceOnLineState_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getDeviceOnLineState(did, uid);
    }

    @Override
    public void updateMobileMsg(String sp, String device_id, String lang, String timezone) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(updateMobileMsg_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(updateMobileMsg_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.updateMobileMsg(sp, device_id, lang, timezone);
    }

    @Override
    public void deviceSet_qibeng(String did, int dev_lock, int mode, int state, int gear, int wh, int ch_cnt, int b_life, int push_cfg) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(deviceSet_qibeng_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(deviceSet_qibeng_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.deviceSet_qibeng(did, dev_lock, mode, state, gear, wh, ch_cnt, b_life, push_cfg);
    }

    @Override
    public void queryProductIndex(int parent) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(queryProductIndex_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(queryProductIndex_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.queryProductIndex(parent);
    }

    @Override
    public void queryProductPost(int cate_id, int is_video, int page, int size) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(queryProductPost_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(queryProductPost_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.queryProductPost(cate_id, is_video, page, size);
    }

    @Override
    public void productSearch(String name) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(productSearch_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(productSearch_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.productSearch(name);
    }

    @Override
    public void getBanners(int position) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(getBanners_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(getBanners_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.getBanners(position);
    }

    @Override
    public void branchSearch(String city, String area, double longValue, double lati, int page, int size) {
        IUserInfoInterface<PersonDataBean> user = new UserResponsitory(
                new ICompleteListener() {
                    @Override
                    public void success(ResultEntity result) {
                        result.setEventTag(Tag_Success);
                        result.setEventType(branchSearch_success);
                        setChanged();
                        notifyObservers(result);
                    }

                    @Override
                    public void failure(ResultEntity result) {
                        result.setEventTag(Tag_Error);
                        result.setEventType(branchSearch_fail);
                        setChanged();
                        notifyObservers(result);

                    }
                });
        user.branchSearch(city, area, longValue, lati, page, size);
    }
}
