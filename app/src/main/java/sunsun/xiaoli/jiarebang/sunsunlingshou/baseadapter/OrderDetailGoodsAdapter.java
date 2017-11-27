package sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.utils.Const;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.OrderDetailBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;
import sunsun.xiaoli.jiarebang.utils.MathUtil;

/**
 * Created by Administrator on 2017/9/7.
 */

public class OrderDetailGoodsAdapter extends BaseAdapter {

    Activity activity;
    OrderDetailBean orderBean;

    public OrderDetailGoodsAdapter(Activity activity, OrderDetailBean orderBean) {
        this.activity = activity;
        this.orderBean = orderBean;
    }

    @Override
    public int getCount() {
        return orderBean.getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return orderBean.getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.item_makesure_goods, null);
            viewHolder.txt_count = (TextView) convertView.findViewById(R.id.txt_count);
            viewHolder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
            viewHolder.txt_guige = (TextView) convertView.findViewById(R.id.txt_guige);
            viewHolder.txt_goodsname = (TextView) convertView.findViewById(R.id.txt_goodsname);
            viewHolder.sure_order_icon = (ImageView) convertView.findViewById(R.id.sure_order_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_count.setText("X" + orderBean.getItems().get(position).getCount());
        viewHolder.txt_goodsname.setText(orderBean.getItems().get(position).getName());
        viewHolder.txt_price.setText("￥" + MathUtil.doubleForm(orderBean.getItems().get(position).getPrice()));
        viewHolder.txt_guige.setText(orderBean.getItems().get(position).getSku_desc());
        GlidHelper.glidLoad(viewHolder.sure_order_icon, Const.imgurl + orderBean.getItems().get(position).getImg());
        return convertView;
    }

    class ViewHolder {
        ImageView sure_order_icon;
        TextView txt_goodsname, txt_guige, txt_price, txt_count;
    }
}
