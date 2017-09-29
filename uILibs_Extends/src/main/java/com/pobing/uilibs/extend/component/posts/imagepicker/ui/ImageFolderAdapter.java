package com.pobing.uilibs.extend.component.posts.imagepicker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pobing.uilibs.extend.R;
import com.pobing.uilibs.extend.component.posts.imagepicker.model.FolderItem;

import java.util.ArrayList;
import java.util.List;

public class ImageFolderAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<FolderItem> mFolderItemList = new ArrayList<FolderItem>();
	
	public ImageFolderAdapter(Context context){
		mInflater = LayoutInflater.from(context);
	}
	
	public void setData(List<FolderItem> folderItemList){
		mFolderItemList.addAll(folderItemList);
	}

	@Override
	public int getCount() {
		return mFolderItemList.size();
	}

	@Override
	public FolderItem getItem(int position) {
		return mFolderItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.posts_view_imagepicker_folder_item, parent, false);
			FolderHolder holder = new FolderHolder();
			holder.imageView = (ImageView)convertView.findViewById(R.id.folder_preview);
			holder.title = (TextView)convertView.findViewById(R.id.folder_title);
			convertView.setTag(holder);
		}
		FolderItem item = getItem(position);
		FolderHolder holder = (FolderHolder)convertView.getTag();
		holder.title.setText(item.getName());
		ImageLoader.getInstance().displayImage("file://" + item.getIcon(), holder.imageView);
		return convertView;
	}
	
	private static class FolderHolder{
		ImageView imageView;
		TextView title;
	}

}
