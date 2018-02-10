package com.itboye.pondteam.bean;

import java.util.ArrayList;

public class MessageBean {

    int count;

    ArrayList<MessageArrayEntity> list;

    public ArrayList<MessageArrayEntity> getList() {
        return list;
    }

    public void setList(ArrayList<MessageArrayEntity> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class MessageArrayEntity {
        String content;
        String createTime;
        MessageExtraBean extra;
        String fromId;
        String msgId;
        String msgStatus;
        String name;
        long sendTime;
        String summary;
        String title;
        String toId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public MessageExtraBean getExtra() {
            return extra;
        }

        public void setExtra(MessageExtraBean extra) {
            this.extra = extra;
        }

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getMsgStatus() {
            return msgStatus;
        }

        public void setMsgStatus(String msgStatus) {
            this.msgStatus = msgStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSendTime() {
            return sendTime;
        }

        public void setSendTime(long sendTime) {
            this.sendTime = sendTime;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getToId() {
            return toId;
        }

        public void setToId(String toId) {
            this.toId = toId;
        }

        public static class MessageExtraBean {
            String orderCode;
            String image;

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }


        }
    }
}
