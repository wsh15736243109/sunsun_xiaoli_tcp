package sunsun.xiaoli.jiarebang.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */

public class UploadImageBean {

    /**
     * code : 0
     * data : [{"path":"/upload/userPicture/other/20170619/dde77b5e0c8f9cc871294873e45dcf01.jpg","uid":23,"ori_name":"1497855954865.jpg","savename":"dde77b5e0c8f9cc871294873e45dcf01.jpg","size":123324,"url":"","imgurl":"http://api.zw.8raw.com/index.php./upload/userPicture/other","md5":"d64a06e554192826b76483f6a89ca00e","sha1":"b7dd464fe325cf09c66d13cb61d0704079d2e2fe","type":"other","ext":"jpg","id":82,"new":0},{"path":"/upload/userPicture/other/20170619/570f9170b32d485f774d9dc495c58a12.jpg","uid":23,"ori_name":"1497857134780.jpg","savename":"570f9170b32d485f774d9dc495c58a12.jpg","size":93070,"url":"","imgurl":"http://api.zw.8raw.com/index.php./upload/userPicture/other","md5":"8e656eef2b5c3816f961a4d4ade6df99","sha1":"4af6522bd59b1a3e83a224435f40b09193c0a762","type":"other","ext":"jpg","create_time":1497857135,"id":84,"new":1}]
     * notify_id : 1497857135
     */

    private int code;
    private int notify_id;
    /**
     * path : /upload/userPicture/other/20170619/dde77b5e0c8f9cc871294873e45dcf01.jpg
     * uid : 23
     * ori_name : 1497855954865.jpg
     * savename : dde77b5e0c8f9cc871294873e45dcf01.jpg
     * size : 123324
     * url :
     * imgurl : http://api.zw.8raw.com/index.php./upload/userPicture/other
     * md5 : d64a06e554192826b76483f6a89ca00e
     * sha1 : b7dd464fe325cf09c66d13cb61d0704079d2e2fe
     * type : other
     * ext : jpg
     * id : 82
     * new : 0
     */

    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setNotify_id(int notify_id) {
        this.notify_id = notify_id;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public int getNotify_id() {
        return notify_id;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String path;
        private int uid;
        private String ori_name;
        private String savename;
        private int size;
        private String url;
        private String imgurl;
        private String md5;
        private String sha1;
        private String type;
        private String ext;
        private int id;
        @SerializedName("new")
        private int newX;

        public void setPath(String path) {
            this.path = path;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setOri_name(String ori_name) {
            this.ori_name = ori_name;
        }

        public void setSavename(String savename) {
            this.savename = savename;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNewX(int newX) {
            this.newX = newX;
        }

        public String getPath() {
            return path;
        }

        public int getUid() {
            return uid;
        }

        public String getOri_name() {
            return ori_name;
        }

        public String getSavename() {
            return savename;
        }

        public int getSize() {
            return size;
        }

        public String getUrl() {
            return url;
        }

        public String getImgurl() {
            return imgurl;
        }

        public String getMd5() {
            return md5;
        }

        public String getSha1() {
            return sha1;
        }

        public String getType() {
            return type;
        }

        public String getExt() {
            return ext;
        }

        public int getId() {
            return id;
        }

        public int getNewX() {
            return newX;
        }
    }
}
