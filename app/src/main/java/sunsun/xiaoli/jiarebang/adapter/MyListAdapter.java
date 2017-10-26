package sunsun.xiaoli.jiarebang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.AddBenas;


public class MyListAdapter extends BaseAdapter {
	List<AddBenas> benas;
	Context context;

	public MyListAdapter(List<AddBenas> benas, Context context) {
		// TODO Auto-generated constructor stub
		this.benas = benas;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return benas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder houder;
		if (convertView == null) {
			houder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_add_device, null);
			houder.name = (TextView) convertView.findViewById(R.id.name);
			houder.img = (ImageView) convertView.findViewById(R.id.img1);
			houder.bitmp = (ImageView) convertView.findViewById(R.id.img2);
			convertView.setTag(houder);
		} else {
			houder = (ViewHolder) convertView.getTag();
		}
		houder.name.setText(benas.get(position).getName());
		houder.img.setImageResource(benas.get(position).getImg());
		houder.bitmp.setImageResource(benas.get(position).getBitmp());
		return convertView;
	}

	class ViewHolder {
		TextView name;
		ImageView img, bitmp;
	}

	

}
