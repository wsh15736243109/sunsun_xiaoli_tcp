package sunsun.xiaoli.jiarebang.adapter.sunsun_2_0_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itboye.pondteam.bean.ProductBean;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.app.App;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ProductSearchAdapter extends BaseAdapter {
    List<ProductBean.HomeListBean> bean;

    public ProductSearchAdapter(List<ProductBean.HomeListBean> bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return bean.size();
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
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHouder holder;
        if (convertView == null) {
            holder = new ViewHouder();
            convertView = LayoutInflater.from(App.getInstance()).inflate(
                    R.layout.item_product_search, null);
            holder.tv_search = (TextView) convertView
                    .findViewById(R.id.tv_search);
            convertView.setTag(holder);
        } else {
            holder = (ViewHouder) convertView.getTag();
        }
        holder.tv_search.setText(bean.get(position).getName());

//        if (bean.get(position).getLevel().equals("1")) {
//            holder.realyout.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    Intent intent = new Intent(
//                            ProducenterSeaerActivity.this,
//                            ProducenterGridviewActivity.class);
//                    intent.putExtra("parentid", bean.get(position).getId());
//                    intent.putExtra("title", bean.get(position).getName());
//                    System.out.println("LLLLLLLLLLLLLLL"
//                            + bean.get(position).getId());
//                    startActivity(intent);
//                }
//            });
//        } else if (bean.get(position).getLevel().equals("2")) {
//            holder.realyout.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    Intent intent = new Intent(
//                            ProducenterSeaerActivity.this,
//                            ProductCenterMyKefuActivity.class);
//                    intent.putExtra("id", bean.get(position).getId());
//                    System.out.println("LLLLLLLLLLLLLLL"
//                            + bean.get(position).getId());
//                    startActivity(intent);
//                }
//            });
//        }

        return convertView;
    }

    class ViewHouder {
        TextView tv_search;
    }
}
