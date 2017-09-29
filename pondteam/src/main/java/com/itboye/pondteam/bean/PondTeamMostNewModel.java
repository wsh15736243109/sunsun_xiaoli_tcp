package com.itboye.pondteam.bean;

/**
 * Created by Administrator on 2017/3/30.
 */

public class PondTeamMostNewModel {

    /**
     * id : 3
     * device_type : S01
     * device_name : 过滤桶
     * url : #
     * bytes : 524288
     * version : v1.1
     * is_latest : 1
     * version_desc : 过滤桶第一版本，最新版本
     * create_time : 2017-03-18 18:31:59
     * update_time : 2017-03-18 18:31:59
     */

    private String id;
    private String device_type;
    private String device_name;
    private String url;
    private String bytes;
    private String version;
    private String is_latest;
    private String version_desc;
    private String create_time;
    private String update_time;

    public void setId(String id) {
        this.id = id;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setIs_latest(String is_latest) {
        this.is_latest = is_latest;
    }

    public void setVersion_desc(String version_desc) {
        this.version_desc = version_desc;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getUrl() {
        return url;
    }

    public String getBytes() {
        return bytes;
    }

    public String getVersion() {
        return version;
    }

    public String getIs_latest() {
        return is_latest;
    }

    public String getVersion_desc() {
        return version_desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }
}
