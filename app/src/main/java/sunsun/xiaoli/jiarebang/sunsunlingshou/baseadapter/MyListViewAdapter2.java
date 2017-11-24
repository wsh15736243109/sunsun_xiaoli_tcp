package sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;


/**
 * Created by a on 2016/5/13.
 */
public class MyListViewAdapter2 extends BaseAdapter {
    private ArrayList<GoodsListBean.ListEntity> allData;
    private Context context;
    private int selectIndex;

    public MyListViewAdapter2(ArrayList<GoodsListBean.ListEntity> allData, Context context, int selectIndex) {
        this.allData = allData;
        this.context = context;
        this.selectIndex = selectIndex;
    }

    @Override
    public int getCount() {
        return allData.size();
    }

    @Override
    public Object getItem(int position) {
        return allData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_listview_2, null);
            vh = new ViewHolder();
            vh.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
            vh.img_goods = (ImageView) convertView.findViewById(R.id.img_goods);
            vh.txt_goodsname = (TextView) convertView.findViewById(R.id.txt_goodsname);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txt_price.setText("￥" + Double.parseDouble(allData.get(position).getMin_price()) / 100);
        vh.txt_goodsname.setText(allData.get(position).getName());
        XGlideLoader.displayImage(context, allData.get(position).getMain_img(), vh.img_goods);
        return convertView;
    }

    public void setIndex(int index) {
        selectIndex = index;
    }

    class ViewHolder {
        TextView txt_price;
        ImageView img_goods;
        TextView txt_goodsname;
    }
}
