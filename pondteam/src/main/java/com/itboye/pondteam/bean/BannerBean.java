package com.itboye.pondteam.bean;

/**
 * Created by Administrator on 2017/8/17.
 */

public class BannerBean {

    /**
     * url : https://market.m.taobao.com/markets/quality/zaqrj
     * url_type : 6070
     * notes :
     * img : 3
     * title : 1
     */

    private String url;
    private String url_type;
    private String notes;
    private String img;
    private String title;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrl_type(String url_type) {
        this.url_type = url_type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrl_type() {
        return url_type;
    }

    public String getNotes() {
        return notes;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }
}
