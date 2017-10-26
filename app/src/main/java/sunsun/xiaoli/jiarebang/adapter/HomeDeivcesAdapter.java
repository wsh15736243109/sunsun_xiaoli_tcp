package sunsun.xiaoli.jiarebang.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.sunsunlingshou.model.DeviceTypeModel;


public class HomeDeivcesAdapter extends BaseAdapter {
    ArrayList<DeviceTypeModel> deviceTypeArray;
    FragmentActivity context;
    int item_lingshou_device;

    public HomeDeivcesAdapter(FragmentActivity activity, ArrayList<DeviceTypeModel> arDevice, int item_lingshou_device) {
        this.context = activity;
        this.deviceTypeArray = arDevice;
        this.item_lingshou_device = item_lingshou_device;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return deviceTypeArray.size();
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    item_lingshou_device, null);
            holder.txt_device = (TextView) convertView.findViewById(R.id.txt_device);
            holder.img_device = (ImageView) convertView.findViewById(R.id.img_device);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DeviceTypeModel publishArray = deviceTypeArray.get(position);

        holder.img_device.setBackgroundResource(publishArray.getRes());
        holder.txt_device.setText(publishArray.getName());
        return convertView;
    }

    class ViewHolder {
        public TextView txt_device;
        public ImageView img_device;
    }


}
