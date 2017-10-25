package sunsun.xiaoli.jiarebang.beans;

import java.util.List;

/**
 * Created by Mr.w on 2017/10/25.
 */

public class XuLieNoModel {

    /**
     * name : b
     * child : [{"id":"32","model":"b","modelnode":"b1","volume":"","text":"bb","level":"2","parent":"31"},{"id":"33","model":"b","modelnode":"b2","volume":"","text":"bb","level":"2","parent":"31"},{"id":"34","model":"b","modelnode":"b3","volume":"","text":"bb","level":"2","parent":"31"}]
     */

    private String name;
    private List<ChildBean> child;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * id : 32
         * model : b
         * modelnode : b1
         * volume :
         * text : bb
         * level : 2
         * parent : 31
         */

        private String id;
        private String model;
        private String modelnode;
        private String volume;
        private String text;
        private String level;
        private String parent;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getModelnode() {
            return modelnode;
        }

        public void setModelnode(String modelnode) {
            this.modelnode = modelnode;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }
    }
}
