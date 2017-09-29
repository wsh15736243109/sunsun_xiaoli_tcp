package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.DeviceTypeModel;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class HomeDeviceAdapter extends BaseAdapter {
    ArrayList<Integer> res = null;

    public HomeDeviceAdapter(Context context, List datas, int res) {
        super(context, res, datas);
    }

    IRecycleviewClick recycleviewClick;

    public void setOnItemListener(IRecycleviewClick recycleviewClick) {
        this.recycleviewClick = recycleviewClick;
    }

    @Override
    public void convert(ViewHolder holder, Object o, final int position) {
        DeviceTypeModel publishArray = (DeviceTypeModel) o;

        holder.setViewBackgroundResource(R.id.img_device, publishArray.getRes());
        holder.setText(R.id.txt_device, publishArray.getName());
//        holder.setOnclickListener(R.id.re_root, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recycleviewClick.onItemClick(position);
//            }
//        });
    }
}
