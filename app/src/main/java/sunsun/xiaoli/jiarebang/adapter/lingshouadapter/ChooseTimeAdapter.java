package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.ServiceBean;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class ChooseTimeAdapter extends BaseAdapter {

    public ChooseTimeAdapter(Context context, int layout, List datas) {
        super(context, layout, datas);
    }

    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick) {
        this.recycleviewClick = recycleviewClick;
    }


    @Override
    public void convert(ViewHolder holder, Object o, final int position) {
        String skuDesc = ((ServiceBean.SkuInfoEntity) o).getSku_desc();
        holder.setText(R.id.text, skuDesc);
        holder.setOnclickListener(R.id.text, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recycleviewClick != null) {
                    recycleviewClick.onItemClick(position);
                }
            }
        });
        if (((ServiceBean.SkuInfoEntity) o).isSelect()) {
            holder.setViewBackgroundResource(R.id.text, R.drawable.border_red);
        } else {

            holder.setViewBackgroundResource(R.id.text, R.drawable.border_gray);
        }
    }
}
