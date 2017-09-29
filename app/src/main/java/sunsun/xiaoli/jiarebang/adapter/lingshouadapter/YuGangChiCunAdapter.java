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

public class YuGangChiCunAdapter extends BaseAdapter {

    Context context;
    public YuGangChiCunAdapter(Context context, int layout, List datas) {
        super(context, layout, datas);
        this.context=context;
    }

    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick) {
        this.recycleviewClick = recycleviewClick;
    }


    @Override
    public void convert(ViewHolder holder, Object o, final int position) {
        String skuDesc = ((ServiceBean.SkuInfoEntity) o).getSku_desc();
        if (skuDesc.indexOf(":") != 0) {
            skuDesc = skuDesc.substring(skuDesc.indexOf(":") + 1, skuDesc.length() - 1);
        }
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
            holder.setTextColor(R.id.text,context.getResources().getColor(R.color.red500));
            holder.setViewBackgroundResource(R.id.text, R.drawable.border_red);
        } else {
            holder.setTextColor(R.id.text,context.getResources().getColor(android.R.color.darker_gray));
            holder.setViewBackgroundResource(R.id.text, R.drawable.border_gray);
        }
    }
}
