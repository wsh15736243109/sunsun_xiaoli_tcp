package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.itboye.pondteam.utils.Const;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.MyPublishBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class MyPublishAdapter extends BaseAdapter {

    public MyPublishAdapter(Context context, List datas) {
        super(context, R.layout.item_mypublish, datas);
    }
    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick){
        this.recycleviewClick=recycleviewClick;
    }

    @Override
    public void convert(ViewHolder holder, Object o,final int position) {
        MyPublishBean.PublishBean publishArray = (MyPublishBean.PublishBean) o;
        holder.setText(R.id.txt_title, publishArray.getTitle());
        holder.setText(R.id.txt_content, publishArray.getDetail());
        holder.setOnclickListener(R.id.re_main, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleviewClick.onItemClick(position);
            }
        });
        GlidHelper.glidLoad((ImageView) holder.getView(R.id.img_publish), Const.imgurl+publishArray.getImg());
    }
}
