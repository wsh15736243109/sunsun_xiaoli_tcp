package sunsun.xiaoli.jiarebang.sunsunlingshou.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/30.
 */

public class MyPublishBean {
    private int count;
    private ArrayList<PublishBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<PublishBean> getList() {
        return list;
    }

    public void setList(ArrayList<PublishBean> list) {
        this.list = list;
    }

    /**
     * id : 9
     * uid : 6
     * sid : 1
     * title : 标题1
     * detail : 这是详情
     * img : 3
     * create_time : 1504860784
     * update_time : 1504860784
     * status : 0
     */
    public static class PublishBean {
        private String id;
        private String uid;
        private String sid;
        private String title;
        private String detail;
        private String img;
        private String create_time;
        private String update_time;
        private String status;

        public void setId(String id) {
            this.id = id;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getSid() {
            return sid;
        }

        public String getTitle() {
            return title;
        }

        public String getDetail() {
            return detail;
        }

        public String getImg() {
            return img;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public String getStatus() {
            return status;
        }
    }
}
