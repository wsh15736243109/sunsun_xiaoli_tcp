package sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itboye.pondteam.utils.Const;
import com.itboye.pondteam.volley.TimesUtils;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;
import sunsun.xiaoli.jiarebang.beans.OrderBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.OrderFragments.OrderChildFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;

import static sunsun.xiaoli.jiarebang.R.id.order_cancel;
import static sunsun.xiaoli.jiarebang.R.id.order_pro_status;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class ModeAdapter extends BaseAdapter {

    Fragment activity;

    public ModeAdapter(Fragment activity, int layout, List datas) {
        super(activity.getActivity(), layout, datas);
        this.activity = activity;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        OrderBean.ListEntity entityList = (OrderBean.ListEntity) o;
        // 0=>全部,1=>待付款,2=>待发货,3=>待收货,4=>已收货,5=>退款/售后,6=>待评价,7=>已完成
        // pay_status:0、待付款，1、已付款
        holder.setTextColor(R.id.order_pro_status,activity.getResources().getColor(R.color.main_lingshou_orange));
        holder.setViewBackgroundDrawable(R.id.order_pro_status,activity.getResources().getDrawable(R.drawable.daifukuan_bg));
        switch (entityList.getPay_status()) {
            case 0:
                //未付款
                holder.setText(order_pro_status, App.getInstance().getString(R.string.obligation_order));
                holder.setViewVisiable(order_cancel, View.GONE);
                break;
            case 1:
                //已付款
                switch (entityList.getOrder_status()) {
                    case 2:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.toBeConfirmed_order));
                        break;
                    case 3:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.toSend_order));
                        break;
                    case 4:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.waitForReceiving_order));
                        switch (entityList.getCs_status()) {
                            case 0:
                                break;
                            case 2:
                                holder.setText(order_pro_status, App.getInstance().getString(R.string.toHandle));
                                break;
                            case 3:
                                break;
                        }
                        break;
                    case 5:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.evaluated_order));
                        break;
                    case 6:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.hasSalesReturn));
                        break;
                    case 7:
                        holder.setViewBackgroundDrawable(R.id.order_pro_status,activity.getResources().getDrawable(R.drawable.yiwancheng_bg));
                        holder.setTextColor(R.id.order_pro_status,activity.getResources().getColor(R.color.gray_c9));
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.finished_order));
                        break;
                    case 8:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.hasClosed));
                        break;
                    case 12:
                        holder.setText(order_pro_status, App.getInstance().getString(R.string.hasOrderReturned));
                        break;
                }
//                holder.setText(order_pro_status, App.getInstance().getString(R.string.obligation_order));
                holder.setViewVisiable(order_cancel, View.GONE);
                break;
            case 2:
                break;
        }
        LinearLayout li_goodsContainer = holder.getView(R.id.li_goodsContainer);
        li_goodsContainer.removeAllViews();
        int totoalCount = 0;
        double totoalMoney = 0;
        if (entityList.getItems() != null) {
            for (int i = 0; i < entityList.getItems().size(); i++) {
                OrderBean.ListEntity.ItemsEntity model = entityList.getItems().get(i);
                View viewGoods = View.inflate(activity.getContext(), R.layout.item_orderinnergoods, null);
                TextView order_pro_name = (TextView) viewGoods.findViewById(R.id.order_pro_name);
                TextView order_pro_time = (TextView) viewGoods.findViewById(R.id.order_pro_time);
                TextView order_pro_num = (TextView) viewGoods.findViewById(R.id.order_pro_num);
                TextView order_pro_money = (TextView) viewGoods.findViewById(R.id.order_pro_money);
                ImageView order_pro_img = (ImageView) viewGoods.findViewById(R.id.order_pro_img);
                order_pro_name.setText(model.getName());
                order_pro_time.setText("时间:" + TimesUtils.getStringTime(model.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
                order_pro_num.setText("共" + model.getCount() + "件商品");
                totoalCount += model.getCount();
                order_pro_money.setText("￥" + Double.parseDouble(model.getPrice()) / 100);
                totoalMoney += Double.parseDouble(entityList.getPrice()) / 100 * model.getCount();
                GlidHelper.glidLoad(order_pro_img, Const.imgurl + model.getImg());
                li_goodsContainer.addView(viewGoods);
            }
        }
        holder.setText(R.id.txt_detail, "共" + totoalCount + "件商品  总计￥" + totoalMoney);
        holder.setText(R.id.storeName, entityList.getStores_name());
        holder.setTag(R.id.order_cancel, -1, entityList);
        holder.setTag(R.id.order_pro_status, -1, entityList);
        holder.setTag(R.id.activity_main, -1, entityList);
        holder.setOnclickListener(R.id.activity_main, (OrderChildFragment) activity);
        holder.setOnclickListener(order_cancel, (OrderChildFragment) activity);//取消订单
        holder.setOnclickListener(R.id.order_pro_status, (OrderChildFragment) activity);//已经支付状态下的操作
    }
}
