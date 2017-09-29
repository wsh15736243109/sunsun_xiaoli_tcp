package sunsun.xiaoli.jiarebang.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itboye.pondteam.R;
import com.itboye.pondteam.bean.WeiShiModel;
import com.itboye.pondteam.custom.swipexpandlistview.expandablelistview.BaseSwipeMenuExpandableListAdapter;
import com.itboye.pondteam.custom.swipexpandlistview.swipemenulistview.ContentViewWrapper;
import com.itboye.pondteam.volley.TimesUtils;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.device.pondteam.ActivityChaZuoBDetail;

import static com.itboye.pondteam.utils.CaculateDays.caculteWeeks;
import static com.itboye.pondteam.utils.StringFormatUtils.getFormatTime;


/**
 * Created by Administrator on 2017/7/12.
 */

public class SwipWeiShiadapter extends BaseSwipeMenuExpandableListAdapter {
    Activity activity;
    ArrayList<WeiShiModel> arrayList;

    public SwipWeiShiadapter(Activity deviceActivity, ArrayList<WeiShiModel> arrayList) {
        this.activity = deviceActivity;
        this.arrayList = arrayList;
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
    public ContentViewWrapper getGroupViewAndReUsable(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        boolean reUseable = true;
        if (convertView == null) {
            convertView = View.inflate(activity,
                    R.layout.item_weishi_father, null);
            convertView.setTag(new ViewHolder(convertView));
            reUseable = false;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.img_add_weishi.setVisibility(View.GONE);
        holder.txt_weishi_name.setVisibility(View.VISIBLE);
                holder.txt_weishi_name.setText(arrayList.get(groupPosition).getNick_name());
        holder.txt_weishi_name.setTag(groupPosition);
        holder.txt_weishi_name.setOnClickListener(((ActivityChaZuoBDetail) activity));
        return new ContentViewWrapper(convertView, reUseable);
    }

    @Override
    public ContentViewWrapper getChildViewAndReUsable(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        boolean reUseable = true;
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(activity,
                    R.layout.item_weishi_child, null);
            holder = new ViewHolder(convertView, 0);
            convertView.setTag(holder);
            reUseable = false;
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_zhouqi_time.setText(arrayList.get(groupPosition).getNick_name());
//        holder.txt_open_time.setText(getFormatNum(arrayList.get(groupPosition).getH0()) + ":" + getFormatNum(arrayList.get(groupPosition).getM0())+ ":" + getFormatNum(arrayList.get(groupPosition).getS0()));
//        holder.txt_close_time.setText(getFormatNum(arrayList.get(groupPosition).getH1()) + ":" + getFormatNum(arrayList.get(groupPosition).getM1())+ ":" + getFormatNum(arrayList.get(groupPosition).getS1()));
        holder.txt_open_time.setText(TimesUtils.utc2Local(getFormatTime(arrayList.get(groupPosition).getSt()),"HH:mm:ss","HH:mm:ss"));
        holder.txt_close_time.setText(TimesUtils.utc2Local(getFormatTime(arrayList.get(groupPosition).getEt()),"HH:mm:ss","HH:mm:ss"));
        if (arrayList.get(groupPosition).getWeek() != 0) {
            String weeks = caculteWeeks(arrayList.get(groupPosition).getWeek() == -1 ? 0 : arrayList.get(groupPosition).getWeek());
            holder.txt_zhouqi_time.setText(weeks.endsWith(",") ? weeks.substring(0, weeks.length() - 1) : weeks);
        } else {
            holder.txt_zhouqi_time.setText("");
        }
        holder.re_weishi_closetime.setTag(groupPosition);
        holder.re_weishi_closetime.setOnClickListener(((ActivityChaZuoBDetail) activity));
        holder.re_weishi_opentime.setTag(groupPosition);
        holder.re_weishi_opentime.setOnClickListener(((ActivityChaZuoBDetail) activity));
        holder.re_weishi_zhouqi.setTag(groupPosition);
        holder.re_weishi_zhouqi.setOnClickListener(((ActivityChaZuoBDetail) activity));
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
        TextView txt_weishi_name, txt_zhouqi_time, txt_open_time, txt_close_time;
        ImageView img_add_weishi;
        RelativeLayout re_weishi_closetime, re_weishi_opentime, re_weishi_zhouqi;

        public ViewHolder(View view) {
            // iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            txt_weishi_name = (TextView) view.findViewById(R.id.txt_weishi_name);
            img_add_weishi = (ImageView) view.findViewById(R.id.img_add_weishi);
            view.setTag(this);
        }

        public ViewHolder(View view, int type) {

            re_weishi_opentime = (RelativeLayout) view.findViewById(R.id.re_weishi_opentime);
            re_weishi_closetime = (RelativeLayout) view.findViewById(R.id.re_weishi_closetime);
            re_weishi_zhouqi = (RelativeLayout) view.findViewById(R.id.re_weishi_zhouqi);
            txt_zhouqi_time = (TextView) view.findViewById(R.id.txt_zhouqi_time);
            txt_open_time = (TextView) view.findViewById(R.id.txt_open_time);
            txt_close_time = (TextView) view.findViewById(R.id.txt_close_time);
            view.setTag(this);
        }
    }
}
