package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.HomeFragment;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class HomeNearShangJiaAdapter extends BaseAdapter {
    Fragment context;
    public HomeNearShangJiaAdapter(Fragment context, List datas, int res) {
        super(context.getActivity(), res, datas);
        this.context=context;
    }
    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick){
        this.recycleviewClick=recycleviewClick;
    }

    @Override
    public void convert(ViewHolder holder, Object o,final int position) {
        StoreListBean.ListEntity list= (StoreListBean.ListEntity) o;
        holder.setText(R.id.txt_shangjianame, list.getName());
        holder.setText(R.id.txt_people,"联系人:"+list.getContacts());
        holder.setText(R.id.txt_juli,"距离:"+list.getDistance()+"m");
        holder.setText(R.id.txt_phone,"手机:"+list.getPhone());
        holder.setText(R.id.txt_mobile,"电话:"+list.getMobile());
        holder.setText(R.id.txt_addr,"地址:"+list.getAddress());
        XGlideLoader.displayImageCircular(context.getActivity(),list.getLogo(), (ImageView) holder.getView(R.id.img_shangjia));
        holder.setOnclickListener(R.id.re_root, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleviewClick.onItemClick(position);
            }
        });
        RatingBar ratingBar=holder.getView(R.id.score);
        ratingBar.setNumStars(5);
        ratingBar.setRating(Float.parseFloat(list.getGrade()));
        holder.setTag(R.id.txt_boda,-1,list);
        holder.setOnclickListener(R.id.txt_boda,(HomeFragment)context);
    }
}
