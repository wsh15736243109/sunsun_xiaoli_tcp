package sunsun.xiaoli.jiarebang.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ClassifyBean {

    /**
     * id : 23
     * img_id : 0
     * name : 活体
     * parent : 0
     * level : 1
     * display_order : 0
     * prop_name :
     * prop_id :
     * sub_prop : []
     */

    private String id;
    private String img_id;
    private String name;
    private String parent;
    private String level;
    private String display_order;
    private String prop_name;
    private String prop_id;
    private List<?> sub_prop;

    public void setId(String id) {
        this.id = id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDisplay_order(String display_order) {
        this.display_order = display_order;
    }

    public void setProp_name(String prop_name) {
        this.prop_name = prop_name;
    }

    public void setProp_id(String prop_id) {
        this.prop_id = prop_id;
    }

    public void setSub_prop(List<?> sub_prop) {
        this.sub_prop = sub_prop;
    }

    public String getId() {
        return id;
    }

    public String getImg_id() {
        return img_id;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public String getLevel() {
        return level;
    }

    public String getDisplay_order() {
        return display_order;
    }

    public String getProp_name() {
        return prop_name;
    }

    public String getProp_id() {
        return prop_id;
    }

    public List<?> getSub_prop() {
        return sub_prop;
    }
}
