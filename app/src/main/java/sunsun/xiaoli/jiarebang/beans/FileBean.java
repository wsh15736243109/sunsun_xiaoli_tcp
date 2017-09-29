package sunsun.xiaoli.jiarebang.beans;

/**
 * Created by Mr.w on 2017/5/9.
 */

public class FileBean {
    private String imgUrl = "";

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private boolean select = false;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
