package com.itboye.pondteam.bean;

/**
 * Created by Administrator on 2017/8/17.
 */

public class MessageListBean {

    /**
     * nickname :
     * from_id : 0
     * content : 我是消息内容11
     * msg_status : 0
     * list_status : 0
     * send_time : 1501471217
     */

    private String nickname;
    private String from_id;
    private String content;
    private int msg_status;
    private String list_status;
    private String send_time;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMsg_status(int msg_status) {
        this.msg_status = msg_status;
    }

    public void setList_status(String list_status) {
        this.list_status = list_status;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFrom_id() {
        return from_id;
    }

    public String getContent() {
        return content;
    }

    public int getMsg_status() {
        return msg_status;
    }

    public String getList_status() {
        return list_status;
    }

    public String getSend_time() {
        return send_time;
    }
}
