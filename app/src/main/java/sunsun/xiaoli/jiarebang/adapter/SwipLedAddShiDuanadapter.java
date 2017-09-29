package sunsun.xiaoli.jiarebang.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.bean.DeviceDetailModel;
import com.itboye.pondteam.custom.swipexpandlistview.expandablelistview.BaseSwipeMenuExpandableListAdapter;
import com.itboye.pondteam.custom.swipexpandlistview.swipemenulistview.ContentViewWrapper;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.device.led.LEDPeriodSettings;

import static com.itboye.pondteam.utils.StringFormatUtils.getFormatNum;


/**
 * Created by Administrator on 2017/7/12.
 */

public class SwipLedAddShiDuanadapter extends BaseSwipeMenuExpandableListAdapter {
    Activity activity;
    ArrayList<DeviceDetailModel.TimePeriod> arrayList;
    private ISwitchClickListenter switchClickListenter;

    public SwipLedAddShiDuanadapter(Activity deviceActivity, ArrayList<DeviceDetailModel.TimePeriod> arrayList) {
        this.activity = deviceActivity;
        this.arrayList = arrayList;
    }

    public void setSwitchClick(ISwitchClickListenter switchClickListenter) {
        this.switchClickListenter = switchClickListenter;
    }

    @Override
    public boolean isGroupSwipable(int groupPosition) {
        return true;
    }

    @Override
    public boolean isChildSwipable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public ContentViewWrapper getGroupViewAndReUsable(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        boolean reUseable = true;
        if (convertView == null) {
            convertView = View.inflate(activity,
                    R.layout.item_weishi_father, null);
            convertView.setTag(new ViewHolder(convertView));
            reUseable = false;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.txt_weishi_name.setVisibility(View.GONE);
        holder.img_add_weishi.setVisibility(View.VISIBLE);
        if (arrayList.get(groupPosition).getEn() == 1) {
            holder.img_add_weishi.setBackgroundResource(R.drawable.kai);
        } else {
            holder.img_add_weishi.setBackgroundResource(R.drawable.guan);
        }
        holder.img_add_weishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchClickListenter.switchClick(groupPosition);
            }
        });
        holder.txt_weishi.setText("第" + (groupPosition + 1) + "时间段");
        return new ContentViewWrapper(convertView, reUseable);
    }

    @Override
    public ContentViewWrapper getChildViewAndReUsable(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        boolean reUseable = true;
        if (convertView == null) {
            convertView = View.inflate(activity,
                    R.layout.item_adtperiodsetting_child, null);
            convertView.setTag(new ViewHolder(convertView, 0));
            reUseable = false;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.re_weishi_opentime.setTag(groupPosition);
        holder.re_weishi_closetime.setTag(groupPosition);
        holder.txt_open_time.setText(getFormatNum(arrayList.get(groupPosition).getH0()) + ":" + getFormatNum(arrayList.get(groupPosition).getM0()));
        holder.txt_close_time.setText(getFormatNum(arrayList.get(groupPosition).getH1()) + ":" + getFormatNum(arrayList.get(groupPosition).getM1()));
        holder.re_tiaose.setTag(groupPosition);
        holder.re_tiaose.setOnClickListener((LEDPeriodSettings) activity);
        holder.re_weishi_opentime.setOnClickListener((LEDPeriodSettings) activity);
        holder.re_weishi_closetime.setOnClickListener((LEDPeriodSettings) activity);

        return new ContentViewWrapper(convertView, reUseable);
    }

    @Override
    public int getGroupCount() {
        return arrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        TextView txt_weishi_name, txt_open_time, txt_close_time, txt_weishi;
        ImageView img_add_weishi;
        RelativeLayout re_tiaose,re_weishi_opentime,re_weishi_closetime;

        public ViewHolder(View view) {
            txt_weishi_name = (TextView) view.findViewById(R.id.txt_weishi_name);
            img_add_weishi = (ImageView) view.findViewById(R.id.img_add_weishi);
            txt_weishi = (TextView) view.findViewById(R.id.txt_weishi);
            view.setTag(this);
        }

        public ViewHolder(View view, int type) {
            txt_open_time = (TextView) view.findViewById(R.id.txt_open_time);
            txt_close_time = (TextView) view.findViewById(R.id.txt_close_time);
            re_tiaose = (RelativeLayout) view.findViewById(R.id.re_tiaose);
            re_weishi_opentime=(RelativeLayout) view.findViewById(R.id.re_weishi_opentime);
            re_weishi_closetime=(RelativeLayout) view.findViewById(R.id.re_weishi_closetime);
            view.setTag(this);
        }
    }

    public interface ISwitchClickListenter {
        void switchClick(int position);
    }
}
