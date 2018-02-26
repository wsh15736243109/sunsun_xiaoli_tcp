package com.itboye.pondteam.bean;

import android.text.Spanned;


import java.io.Serializable;
import java.util.ArrayList;

public class ChatBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ["id"] => string(2) "43" ["msg_owner"] => string(1) "1" ["msg_content"]
     * => string(28) "大师的三分出手大方v" //信息内容 ["create_time"] => string(10)
     * "1473736689" ["update_time"] => string(10) "1473736689" ["read_status"]
     * => string(1) "0" ["read_time"] => string(1) "0" ["queue_id"] => string(1)
     * "2" ["msg_type"] => string(1) "1" //1表示纯文本 2表示图片 ["owner_type"] =>
     * string(1) "2"
     */
    String createTime;
    String have, servicerStatus;


    Spanned content;

    public Spanned getContent() {
        return content;
    }

    public void setContent(Spanned content) {
        this.content = content;
    }

    KefuBeans.CounBeans counBeans;

    public KefuBeans.CounBeans getCounBeans() {
        return counBeans;
    }

    public void setCounBeans(KefuBeans.CounBeans counBeans) {
        this.counBeans = counBeans;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getServicerStatus() {
        return servicerStatus;
    }

    public void setServicerStatus(String servicerStatus) {
        this.servicerStatus = servicerStatus;
    }

    public String getHave() {
        return have;
    }

    public void setHave(String have) {
        this.have = have;
    }

    private ArrayList<ChatItem> list;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ArrayList<ChatItem> getList() {
        return list;
    }

    public void setList(ArrayList<ChatItem> list) {
        this.list = list;
    }

    public static class ChatItem implements Serializable {
        /**
         *
         */


        private static final long serialVersionUID = 1L;
        private String id, msgOwner, create_time, updateTime, readStatus,
                readTime, queueId, msgType, ownerType, nickname, head;


        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public static long getSerialversionuid() {
            return serialVersionUID;
        }

        private Spanned msgContent;
        private String servicerStatus;

        public String getReadTime() {
            return readTime;
        }

        public void setReadTime(String readTime) {
            this.readTime = readTime;
        }

        public String getServicerStatus() {
            return servicerStatus;
        }

        public void setServicerStatus(String servicerStatus) {
            this.servicerStatus = servicerStatus;
        }

        public String getQueueId() {
            return queueId;
        }

        public Spanned getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(Spanned msgContent) {
            this.msgContent = msgContent;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsgOwner() {
            return msgOwner;
        }

        public void setMsgOwner(String msgOwner) {
            this.msgOwner = msgOwner;
        }

        public String getCreateTime() {
            return create_time;
        }

        public void setCreateTime(String createTime) {
            this.create_time = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(String readStatus) {
            this.readStatus = readStatus;
        }

        public String getRead_time() {
            return readTime;
        }

        public void setRead_time(String readTime) {
            this.readTime = readTime;
        }

        public String getQueue_id() {
            return queueId;
        }

        public void setQueueId(String queueId) {
            this.queueId = queueId;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(String ownerType) {
            this.ownerType = ownerType;
        }

        @Override
        public String toString() {
            return "ChatItem{" +
                    "id='" + id + '\'' +
                    ", msgOwner='" + msgOwner + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", readStatus='" + readStatus + '\'' +
                    ", readTime='" + readTime + '\'' +
                    ", queueId='" + queueId + '\'' +
                    ", msgType='" + msgType + '\'' +
                    ", ownerType='" + ownerType + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", head='" + head + '\'' +
                    ", msgContent=" + msgContent +
                    ", servicerStatus='" + servicerStatus + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChatBean{" +
                "createTime='" + createTime + '\'' +
                ", have='" + have + '\'' +
                ", servicerStatus='" + servicerStatus + '\'' +
                ", content=" + content +
                ", counBeans=" + counBeans +
                ", list=" + list +
                '}';
    }
}
