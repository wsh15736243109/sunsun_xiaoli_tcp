package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.content.Context;
import android.view.View;

import com.itboye.pondteam.utils.Const;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.GoodsListBean;
import sunsun.xiaoli.jiarebang.custom.RatioImageView;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class HomeShangPinAdapter extends BaseAdapter {

    public HomeShangPinAdapter(Context context, List datas,int res) {
        super(context, res, datas);
    }
    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick){
        this.recycleviewClick=recycleviewClick;
    }

    @Override
    public void convert(ViewHolder holder, Object o,final int position) {
        GoodsListBean.ListEntity publishArray = (GoodsListBean.ListEntity) o;
        holder.setText(R.id.txt_goodsname, publishArray.getName());
        holder.setText(R.id.txt_price,"￥"+publishArray.getMin_price());
        GlidHelper.glidLoad((RatioImageView)holder.getView(R.id.img_goods), Const.imgurl+publishArray.getMain_img());
        holder.setOnclickListener(R.id.re_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleviewClick.onItemClick(position);
            }
        });
    }
}
