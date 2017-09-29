package com.itboye.pondteam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.bean.PeriodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class PeriodAdapter extends BaseAdapter {

    List<PeriodModel> list = new ArrayList<>();
    private Context context;

    public PeriodAdapter(Context context, List<PeriodModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_peroid, null);
            viewHolder.txt_zhouqi = (TextView) convertView.findViewById(R.id.txt_zhouqi);
            viewHolder.img_select = (ImageView) convertView.findViewById(R.id.img_select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_zhouqi.setText(list.get(position).getWeek());
        viewHolder.img_select.setBackgroundResource(R.drawable.is_peroid_select);
        viewHolder.img_select.setVisibility(list.get(position).isSelect() ? View.VISIBLE : View.INVISIBLE);
        return convertView;
    }

    class ViewHolder {
        TextView txt_zhouqi;
        ImageView img_select;
    }
}
