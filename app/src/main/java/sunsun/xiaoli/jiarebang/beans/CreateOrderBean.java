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

    private String id;
    private String order_content;
    private String createtime;
    private String uid;
    private String pay_type;
    private String true_pay_money;
    private String trade_no;
    private String pay_balance;
    private String pay_status;
    private String pay_currency;
    private String update_time;
    private String b_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_content() {
        return order_content;
    }

    public void setOrder_content(String order_content) {
        this.order_content = order_content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTrue_pay_money() {
        return true_pay_money;
    }

    public void setTrue_pay_money(String true_pay_money) {
        this.true_pay_money = true_pay_money;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getPay_balance() {
        return pay_balance;
    }

    public void setPay_balance(String pay_balance) {
        this.pay_balance = pay_balance;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_currency() {
        return pay_currency;
    }

    public void setPay_currency(String pay_currency) {
        this.pay_currency = pay_currency;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getB_status() {
        return b_status;
    }

    public void setB_status(String b_status) {
        this.b_status = b_status;
    }

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
