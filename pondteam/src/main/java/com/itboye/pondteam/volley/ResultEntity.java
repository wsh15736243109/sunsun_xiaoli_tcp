/**
 *
 */
package com.itboye.pondteam.volley;

/**
 * 网络数据统一返回对象
 *
 * @author hebidu
 */
public class ResultEntity {

    private String eventType;//类型是指特定的事件，比如 登录失败事件

    private String eventTag;//tag是标签，某一类事件， 比如 错误事件

    /**
     * 1111:需要重新登录
     * 0：正常
     * else不需要处理，弹出提示
     */
    private int code;

    private String msg;

    private Object data;

    public ResultEntity(int code, String msg, Object data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    /**
     * @return the eventTag
     */
    public String getEventTag() {
        return eventTag;
    }

    /**
     * @param eventTag the eventTag to set
     */
    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * true 有错误 false 没错误
     *
     * @return boolean
     * 2016-4-20 hebidu
     */
    public Boolean hasError() {
        return this.getCode() != 0;
    }

    /**
     * code = 0,未出错
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * msg 字符串消息描述
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 数据
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }


}
