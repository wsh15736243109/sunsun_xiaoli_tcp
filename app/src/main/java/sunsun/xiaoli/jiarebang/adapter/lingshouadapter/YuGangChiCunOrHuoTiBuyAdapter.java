package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.content.Context;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class YuGangChiCunOrHuoTiBuyAdapter extends BaseAdapter {

    public YuGangChiCunOrHuoTiBuyAdapter(Context context, int layout, List datas) {
        super(context, layout, datas);
    }

    @Override
    public void convert(ViewHolder holder, Object o,int position) {
        holder.setText(R.id.text,(String)o);
    }
}
