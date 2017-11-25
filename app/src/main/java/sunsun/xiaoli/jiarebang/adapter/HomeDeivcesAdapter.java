package sunsun.xiaoli.jiarebang.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itboye.pondteam.bean.DeviceListBean;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;


public class HomeDeivcesAdapter extends BaseAdapter {
    ArrayList<DeviceListBean> deviceTypeArray;
    FragmentActivity context;
    int item_lingshou_device;

    public HomeDeivcesAdapter(FragmentActivity activity, ArrayList<DeviceListBean> arDevice, int item_lingshou_device) {
        this.context = activity;
        this.deviceTypeArray = arDevice;
        this.item_lingshou_device = item_lingshou_device;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return deviceTypeArray.size() < 7 ? deviceTypeArray.size() + 1 : 9;
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
//            holder.img_device = (ImageView) convertView.findViewById(R.id.img_device);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Drawable drawableTop = null;
        if (position == 8 && deviceTypeArray.size() >= 8) {
            holder.txt_device.setText("更多");
        } else if (deviceTypeArray.size() < 8 && position == deviceTypeArray.size()) {
            holder.txt_device.setText("添加设备");
            drawableTop = context.getResources().getDrawable(R.drawable.add_device_90);
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
            holder.txt_device.setCompoundDrawables(null, drawableTop, null, null);
        } else if (position <= deviceTypeArray.size() - 2) {
            DeviceListBean deviceListBean = deviceTypeArray.get(position);
            if (deviceListBean.getDid().startsWith("S01")) {
                //过滤桶
                drawableTop = context.getResources().getDrawable(R.drawable.device_chitangguolv);
            } else if (deviceListBean.getDevice_type().startsWith("S02")) {
                //加热棒

                drawableTop = context.getResources().getDrawable(R.drawable.device_jiarebang);
            } else if (deviceListBean.getDevice_type().equalsIgnoreCase("S03")) {
                //806
                drawableTop = context.getResources().getDrawable(R.drawable.device_aq);
            } else if (deviceListBean.getDevice_type().equalsIgnoreCase("S03-1")) {
                //500
                drawableTop = context.getResources().getDrawable(R.drawable.device_500);
            } else if (deviceListBean.getDevice_type().equalsIgnoreCase("S03-2")) {
                //700
                drawableTop = context.getResources().getDrawable(R.drawable.device_700);
            } else if (deviceListBean.getDevice_type().startsWith("S04")) {
                drawableTop = context.getResources().getDrawable(R.drawable.device_ph);
            } else if (deviceListBean.getDevice_type().startsWith("S05")) {
                drawableTop = context.getResources().getDrawable(R.drawable.device_shuibeng);
            } else if (deviceListBean.getDevice_type().startsWith("S06")) {
                drawableTop = context.getResources().getDrawable(R.drawable.device_shuizudeng);
            } else if (deviceListBean.getDevice_type().equalsIgnoreCase("S07")) {
                drawableTop = context.getResources().getDrawable(R.drawable.device_jiaozhiliubeng);
            } else if (deviceListBean.getDevice_type().startsWith("chiniao_wifi_camera")) {
                drawableTop = context.getResources().getDrawable(R.drawable.device_shexiangtou);
            } else if (deviceListBean.getDevice_type().startsWith("S08")) {
                drawableTop = context.getResources().getDrawable(R.drawable.device_weishiqi);
            } else {
//                holder.img_device.setVisibility(View.INVISIBLE);
            }
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
            holder.txt_device.setCompoundDrawables(null, drawableTop, null, null);
            holder.txt_device.setText(deviceListBean.getDevice_nickname());
        } else {

        }
        return convertView;
    }

    class ViewHolder {
        public TextView txt_device;
//        public ImageView img_device;
    }


}
