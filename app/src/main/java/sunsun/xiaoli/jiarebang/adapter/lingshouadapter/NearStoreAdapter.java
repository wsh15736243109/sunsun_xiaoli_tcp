package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class NearStoreAdapter extends BaseAdapter {

    public NearStoreAdapter(Context context, List datas) {
        super(context, R.layout.item_nearstore, datas);
    }

    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick) {
        this.recycleviewClick = recycleviewClick;
    }

    @Override
    public void convert(ViewHolder holder, Object o, final int position) {
        try {
            StoreListBean.ListEntity publishArray = (StoreListBean.ListEntity) o;
            holder.setText(R.id.txt_storename, publishArray.getName());
            holder.setText(R.id.txt_boda,"配送费 <font color='#ff0000'>￥"+publishArray.getPay()+"</font>");
            holder.setTag(R.id.re_main,-1,position);
            holder.setOnclickListener(R.id.re_main, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleviewClick != null) {
                        recycleviewClick.onItemClick(position);
                    }
                }
            });
        } catch (Exception e) {

        }
    }
}
