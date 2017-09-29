package sunsun.xiaoli.jiarebang.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/31.
 */

public class CreateOrderBean implements Serializable
{

    /**
     * pay_money : 1100
     * pay_code : PA1721109134098190964
     * create_time : 1501492420
     * sign : d16af2203a669219497dacb38efe6841
     */

    private double pay_money;
    private String pay_code;
    private String create_time;
    private String sign;

    public void setPay_money(double pay_money) {
        this.pay_money = pay_money;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public double getPay_money() {
        return pay_money;
    }

    public String getPay_code() {
        return pay_code;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getSign() {
        return sign;
    }
}
