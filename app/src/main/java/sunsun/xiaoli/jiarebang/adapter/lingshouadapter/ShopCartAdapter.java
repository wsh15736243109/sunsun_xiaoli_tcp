package sunsun.xiaoli.jiarebang.adapter.lingshouadapter;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.pondteam.custom.swiprecyclerview.SwipeItemLayout;
import com.itboye.pondteam.interfaces.IRecyclerviewclicklistener;
import com.itboye.pondteam.utils.Const;

import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.ShopCartBean;
import sunsun.xiaoli.jiarebang.sunsunlingshou.activity.shopcart.ShopCartChildFragment;
import sunsun.xiaoli.jiarebang.sunsunlingshou.utils.GlidHelper;

/**
 * Created by Mr.w on 2017/5/13.
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.SimpleViewHolder> {

    private IRecyclerviewclicklistener mItemTouchListener;
    private ArrayList<ShopCartBean> mData;
    Fragment activity;

    public ShopCartAdapter(Fragment activity, ArrayList<ShopCartBean> data, IRecyclerviewclicklistener itemTouchListener) {
        this.mData = data;
        this.mItemTouchListener = itemTouchListener;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        @LayoutRes
        int layout = R.layout.item_left_menu_shopcart;
        View rootView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new SimpleViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
//        holder.mContent.setMyText("" + position);
//        if (mItemTouchListener != null) {
        //jdk1.8使用
        holder.txt_price_shopcart.setText("￥" + this.mData.get(position).getPrice());
        holder.txt_name_shopcart.setText("￥" + this.mData.get(position).getName());
        holder.gwc_num.setText(this.mData.get(position).getCount());
        GlidHelper.glidLoad(holder.img_shopcart, Const.imgurl + this.mData.get(position).getIcon_url());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemTouchListener.onItemClick("");
            }
        });

        if (holder.mLeftMenu != null) {
            holder.mLeftMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTouchListener.onLeftMenuClick(position+"");
                    holder.mSwipeItemLayout.close();
                }
            });
        }
        holder.check_img.setTag(R.id.tag_first,mData.get(position).isSelect());
        holder.check_img.setTag(position);
        if (mData.get(position).isSelect()) {
            holder.check_img.setBackgroundResource(R.drawable.tiaojie_red);
        }else{
            holder.check_img.setBackgroundResource(R.drawable.tiaojie_white);
        }
        holder.check_img.setOnClickListener((ShopCartChildFragment) activity);
        holder.gwc_jia.setTag(position);
        holder.gwc_jia.setOnClickListener((ShopCartChildFragment) activity);
        holder.gwc_jian.setTag(position);
        holder.gwc_jian.setOnClickListener((ShopCartChildFragment) activity);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private final View mLeftMenu;
        private final SwipeItemLayout mSwipeItemLayout;
        TextView txt_name_shopcart, txt_price_shopcart, gwc_jian, gwc_jia;
        EditText gwc_num;
        ImageView img_shopcart, check_img;

        SimpleViewHolder(View itemView) {
            super(itemView);
            mSwipeItemLayout = (SwipeItemLayout) itemView.findViewById(R.id.swipe_layout);
            txt_name_shopcart = (TextView) itemView.findViewById(R.id.txt_name_shopcart);
            mLeftMenu = itemView.findViewById(R.id.left_menu);
            img_shopcart = (ImageView) itemView.findViewById(R.id.img_shopcart);
            check_img = (ImageView) itemView.findViewById(R.id.check_img);
            gwc_jian = (TextView) itemView.findViewById(R.id.gwc_jian);
            gwc_jia = (TextView) itemView.findViewById(R.id.gwc_jia);
            txt_price_shopcart = (TextView) itemView.findViewById(R.id.txt_price_shopcart);
            gwc_num = (EditText) itemView.findViewById(R.id.gwc_num);
        }
    }
}
