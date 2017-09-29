package sunsun.xiaoli.jiarebang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import sunsun.xiaoli.jiarebang.R;
import sunsun.xiaoli.jiarebang.beans.FileBean;
import sunsun.xiaoli.jiarebang.interfaces.IRecycleviewClick;

/**
 * Created by Mr.w on 2017/5/9.
 */

public class PhotoAdapter extends RecyclerView.Adapter {
    ArrayList<FileBean> fileList;
    Context context;
    IRecycleviewClick iRecycleviewClick;
    private boolean isVisible;

    public PhotoAdapter(Context context, ArrayList<FileBean> fileList, IRecycleviewClick iRecycleviewClick) {
        this.fileList = fileList;
        this.context = context;
        this.iRecycleviewClick = iRecycleviewClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BodyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, null));
    }

    public void setSelectVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean getSelectVisible() {
        return this.isVisible;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        System.out.println(">>>>>>>>图片路径" + fileList.get(position).getImgUrl());
        //加载资源图片
        Glide.with(context).load(new File(fileList.get(position).getImgUrl())).error(R.drawable.icon).centerCrop().placeholder(R.drawable.placeholder_gray).into(((BodyViewHolder) holder).getImageView());
        ((BodyViewHolder) holder).getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRecycleviewClick.onItemClick(position);
            }
        });
        ((BodyViewHolder) holder).getImageView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iRecycleviewClick.onItemLongClick(position);
                return true;
            }
        });
        if (this.getSelectVisible()) {
            ((BodyViewHolder) holder).getImageViewSelect().setVisibility(View.VISIBLE);
        }else{
            fileList.get(position).setSelect(false);
            ((BodyViewHolder) holder).getImageViewSelect().setVisibility(View.GONE);
        }
        if (fileList.get(position).isSelect()) {
            ((BodyViewHolder) holder).getImageViewSelect().setBackgroundResource(R.drawable.is_check);
        } else {
            ((BodyViewHolder) holder).getImageViewSelect().setBackgroundResource(R.drawable.is_checkbox);
        }
    }

    @Override
    public int getItemCount() {
        return fileList == null ? 0 : fileList.size();
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个ImageView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgageView;
        private ImageView img_select;

        public BodyViewHolder(View itemView) {
            super(itemView);
            imgageView = (ImageView) itemView.findViewById(R.id.img);
            img_select = (ImageView) itemView.findViewById(R.id.img_select);
            imgageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        public ImageView getImageView() {
            return imgageView;
        }

        public ImageView getImageViewSelect() {
            return img_select;
        }
    }

}
