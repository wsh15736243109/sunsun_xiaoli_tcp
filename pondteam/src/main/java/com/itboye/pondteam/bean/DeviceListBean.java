package com.itboye.pondteam.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/24.
 */

public class DeviceListBean implements Serializable{

    /**
     * id : 1
     * uid : 183
     * did : S0100000000001
     * create_time : 2017-03-21 15:23:01
     * update_time : 2017-03-21 15:23:01
     * device_nickname : S0100000000001
     * device_type : S01
     * "temp_max": "200",
     * "temp_min": "400",
     * "temp_alert": "1"
     */
    /**
     * "id": "1",
     * "master_did": "S03X0000000001",
     * "slave_did": "SCHD-001008-ETVPE",
     * "create_time": "1495953175",
     * "slave_device_type": "ciniao_wifi_camera",
     * "update_time": "1495953175",
     * "uid": "0",
     * "slave_pwd": "sRZrJQ20",
     * "slave_name": "wifi  chiniao"
     * "is_disconnect": "0"
     */
//    -------------摄像头绑定列表
    private String master_did;
    private String slave_did;
    private String create_time;
    private String slave_device_type;
    private String update_time;
    private String slave_pwd;
    private String slave_name;
    private String extra;
    private int is_disconnect;

    public int getIs_disconnect() {
        return is_disconnect;
    }

    public void setIs_disconnect(int is_disconnect) {
        this.is_disconnect = is_disconnect;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMaster_did() {
        return master_did;
    }

    public void setMaster_did(String master_did) {
        this.master_did = master_did;
    }

    public String getSlave_did() {
        return slave_did;
    }

    public void setSlave_did(String slave_did) {
        this.slave_did = slave_did;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSlave_device_type() {
        return slave_device_type;
    }

    public void setSlave_device_type(String slave_device_type) {
        this.slave_device_type = slave_device_type;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSlave_pwd() {
        return slave_pwd;
    }

    public void setSlave_pwd(String slave_pwd) {
        this.slave_pwd = slave_pwd;
    }

    public String getSlave_name() {
        return slave_name;
    }

    public void setSlave_name(String slave_name) {
        this.slave_name = slave_name;
    }
    //    -------------摄像头绑定列表


    private String id;
    private String uid;
    private String did;
    //    private String create_time;
//    private String update_time;
    private String device_nickname;
    private String device_type;
    private String temp_max;
    private String temp_min;
    private String temp_alert;
    private int is_state_notify;

    public int getIs_state_notify() {
        return is_state_notify;
    }

    public void setIs_state_notify(int is_state_notify) {
        this.is_state_notify = is_state_notify;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_alert() {
        return temp_alert;
    }

    public void setTemp_alert(String temp_alert) {
        this.temp_alert = temp_alert;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDid(String did) {
        this.did = did;
    }

//    public void setCreate_time(String create_time) {
//        this.create_time = create_time;
//    }
//
//    public void setUpdate_time(String update_time) {
//        this.update_time = update_time;
//    }

    public void setDevice_nickname(String device_nickname) {
        this.device_nickname = device_nickname;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getDid() {
        return did;
    }

//    public String getCreate_time() {
//        return create_time;
//    }
//
//    public String getUpdate_time() {
//        return update_time;
//    }

    public String getDevice_nickname() {
        return device_nickname;
    }

    public String getDevice_type() {
        return device_type;
    }
}
