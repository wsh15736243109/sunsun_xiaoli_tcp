package sunsun.xiaoli.jiarebang.sunsunlingshou.model;

/**
 * Created by Administrator on 2017/8/23.
 */

public class DeviceTypeModel {
    Integer res;
    String name;
    public DeviceTypeModel(Integer res,String name){
        this.res=res;
        this.name=name;
    }
    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
