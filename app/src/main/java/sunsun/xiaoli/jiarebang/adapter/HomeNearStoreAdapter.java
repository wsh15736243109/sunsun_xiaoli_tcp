package sunsun.xiaoli.jiarebang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.StoreListBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.fragment.HomeFragment;
import sunsun.xiaoli.jiarebang.utils.XGlideLoader;


public class HomeNearStoreAdapter extends BaseAdapter {
    private int item_home_nearshangjia;
    List<StoreListBean.ListEntity> benas;
    HomeFragment context;

    public HomeNearStoreAdapter(HomeFragment homeFragment, List<StoreListBean.ListEntity> list, int item_home_nearshangjia) {
        this.benas = list;
        this.context = homeFragment;
        this.item_home_nearshangjia = item_home_nearshangjia;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context.getActivity()).inflate(
                    R.layout.item_home_nearshangjia, null);
            holder.txt_shangjianame = (TextView) convertView.findViewById(R.id.txt_shangjianame);
            holder.img_shangjia = (ImageView) convertView.findViewById(R.id.img_shangjia);
            holder.txt_people = (TextView) convertView.findViewById(R.id.txt_people);
            holder.txt_juli = (TextView) convertView.findViewById(R.id.txt_juli);
            holder.txt_phone = (TextView) convertView.findViewById(R.id.txt_phone);
            holder.txt_mobile = (TextView) convertView.findViewById(R.id.txt_mobile);
            holder.txt_addr = (TextView) convertView.findViewById(R.id.txt_addr);
            holder.txt_boda = (TextView) convertView.findViewById(R.id.txt_boda);
            holder.score = (RatingBar) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StoreListBean.ListEntity list = benas.get(position);
        holder.txt_shangjianame.setText(list.getName());
        holder.txt_people.setText("联系人:" + list.getContacts());
        holder.txt_juli.setText("距离:" + list.getDistance() + "m");
        holder.txt_phone.setText("手机:" + list.getPhone());
        holder.txt_mobile.setText("电话:" + list.getMobile());
        holder.txt_addr.setText("地址:" + list.getAddress());
        holder.score.setNumStars(5);
        holder.score.setRating(Integer.parseInt(list.getGrade()));
        holder.txt_boda.setTag(list);
        XGlideLoader.displayImageCircular(context.getActivity(), list.getLogo(), holder.img_shangjia);
//		holder.setOnclickListener(R.id.re_root, new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				recycleviewClick.onItemClick(position);
//			}
//		});
//		RatingBar ratingBar=holder.getView(R.id.score);
//		ratingBar.setNumStars(5);
//		ratingBar.setRating(Float.parseFloat(list.getGrade()));
//		holder.setTag(R.id.txt_boda,-1,list);
//		holder.setOnclickListener(R.id.txt_boda,(HomeFragment)context);

        return convertView;
    }

    class ViewHolder {
        TextView txt_shangjianame;
        ImageView img_shangjia, bitmp;
        public TextView txt_people;
        public TextView txt_juli;
        public TextView txt_phone;
        public TextView txt_mobile;
        public TextView txt_addr, txt_boda;
        public RatingBar score;
    }


}
