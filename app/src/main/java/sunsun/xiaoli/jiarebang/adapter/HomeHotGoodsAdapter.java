package sunsun.xiaoli.jiarebang.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itboye.pondteam.utils.Const;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;


public class HomeHotGoodsAdapter extends BaseAdapter {
    ArrayList<GoodsListBean.ListEntity> benas;
    FragmentActivity context;
    int item_home_shangpin;
    public HomeHotGoodsAdapter(FragmentActivity activity, ArrayList<GoodsListBean.ListEntity> arTemp, int item_home_shangpin) {
        this.benas = arTemp;
        this.context=activity;
        this.item_home_shangpin=item_home_shangpin;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return benas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_home_shangpin, null);
            holder.txt_goodsname = (TextView) convertView.findViewById(R.id.txt_goodsname);
            holder.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
            holder.img_goods = (RatioImageView) convertView.findViewById(R.id.img_goods);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsListBean.ListEntity publishArray = benas.get(position);
        holder.txt_goodsname.setText(publishArray.getName());
        holder.txt_price.setText("￥" + publishArray.getMin_price());
        GlidHelper.glidLoad(holder.img_goods, Const.imgurl + publishArray.getMain_img());
        return convertView;
    }

    class ViewHolder {
        public TextView txt_goodsname;
        public TextView txt_price;
        public RatioImageView img_goods;
    }


}
