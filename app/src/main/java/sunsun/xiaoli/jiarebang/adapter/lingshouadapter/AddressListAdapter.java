package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.app.Activity;
import android.view.View;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.AddressBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.AddressListActivity;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.BaseAdapter;
import sunsun.xiaoli.jiarebang.sunsunlingshou.baseadapter.ViewHolder;


/**
 * Created by Kun on 2016/12/14.
 * GitHub: https://github.com/AndroidKun
 * CSDN: http://blog.csdn.net/a1533588867
 * Description:模版
 */

public class AddressListAdapter extends BaseAdapter {
    Activity activity;

    public AddressListAdapter(Activity activity, int layout, List datas) {
        super(activity, layout, datas);
        this.activity = activity;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        AddressBean bean = (AddressBean) o;
        holder.setText(R.id.txt_name, bean.getContactname());
        holder.setText(R.id.txt_phone, bean.getMobile());
        holder.setText(R.id.txt_address, bean.getDetailinfo());
        holder.setViewVisiable(R.id.txt_moren, bean.getIs_default().equals("1") ? View.VISIBLE : View.GONE);
        holder.setViewVisiable(R.id.arrow_right, bean.isShow() ? View.VISIBLE : View.INVISIBLE);
        if (bean.isSelect()) {
            holder.setViewBackgroundResource(R.id.arrow_right, R.drawable.appoint_install_right);
        } else {
            holder.setViewBackgroundResource(R.id.arrow_right, R.drawable.address_unselect);
        }
        holder.getView(R.id.arrow_right).setTag(position);
        holder.setOnclickListener(R.id.arrow_right, (AddressListActivity) activity);
        holder.getView(R.id.txt_update).setTag(position);
        holder.setOnclickListener(R.id.txt_update, (AddressListActivity) activity);
        holder.getView(R.id.rootView).setTag(position);
        holder.setOnclickListener(R.id.rootView, (AddressListActivity) activity);
    }
}
