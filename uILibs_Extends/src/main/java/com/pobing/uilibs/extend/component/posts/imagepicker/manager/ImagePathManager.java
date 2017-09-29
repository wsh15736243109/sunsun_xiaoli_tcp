package com.pobing.uilibs.extend.component.posts.imagepicker.manager;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore.Images;


import com.pobing.uilibs.extend.R;
import com.pobing.uilibs.extend.component.posts.imagepicker.model.FolderItem;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理图片路径和列表
 * @author Louis
 */

public class ImagePathManager {
	public static final String CAMERA_IMAGE_BUCKET_NAME =
	        Environment.getExternalStorageDirectory().toString()
	        + "/DCIM/Camera";
	public static final String CAMERA_IMAGE_BUCKET_ID =
	        getBucketId(CAMERA_IMAGE_BUCKET_NAME);
	
	private List<String> mAllImagePathList;
	private List<FolderItem> mFolderItemList = new ArrayList<FolderItem>();
	
	private ImagePathManager(Context context){
		LinkedHashSet<String> folderSet  = new LinkedHashSet<String>(); 
		initCameraImages(context, folderSet);
	}
	
	public static ImagePathManager create(Context context){
		return new ImagePathManager(context);
	}
	
	public List<String> getAllImagePathList(){
		return mAllImagePathList;
	}
	
	public List<FolderItem> getFolderItemList(){
		return mFolderItemList;
	}
	
	public void initCameraImages(Context context, Set<String> folders) {
	    final String[] projection = { Images.Media.DATA};
	    final String selection = Images.Media.BUCKET_ID + " = ?";
	    final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
	    final Cursor cursor = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI,
	            projection,
	            null,
	            null,
	            Images.Media.BUCKET_ID);
	    mAllImagePathList = new ArrayList<String>(cursor.getCount());
	    mFolderItemList = new ArrayList<FolderItem>();
	    mFolderItemList.add(new FolderItem(context.getString(R.string.posts_image_picker_folder_btn), null, null));

	    if (cursor.moveToFirst()) {
	        final int dataColumn = cursor.getColumnIndexOrThrow(Images.Media.DATA);
	        do {
	            final String data = cursor.getString(dataColumn);
	            if(new File(data).exists()){
	            	mAllImagePathList.add(data);
		            appendFolderItem(data);
		            /**
		             * 填充全部图片的icon图
		             */
		            if(mFolderItemList.get(0).getIcon() == null){
		            	mFolderItemList.get(0).setIcon(data);
		            }
	            }
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	}
	
	public static String getBucketId(String path) {
	    return String.valueOf(path.toLowerCase().hashCode());
	}
	
	public void appendFolderItem(String imagePath){
		String folderPath = imagePath.substring(0, imagePath.lastIndexOf('/'));
		FolderItem lastFolderItem = getLastItem();
		if(lastFolderItem != null && folderPath.equals(lastFolderItem.getPath())){
			lastFolderItem.addItem();
		}
		else{
			FolderItem item = new FolderItem(genFolderName(folderPath), imagePath, folderPath);
			mFolderItemList.add(item);
		}
	}
	
	public String genFolderName(String folderPath){
		String name = null;
		int index = folderPath.lastIndexOf("/");
		if(index > 0){
			name = folderPath.substring(index + 1);
		}else{
			name = folderPath;
		}
		if(CAMERA_IMAGE_BUCKET_NAME.equals(folderPath)){
			return "相册";
		}
		else {
			return name;
		}
	}
	
	public FolderItem getLastItem(){
		if(mFolderItemList.size() > 0){
			return mFolderItemList.get(mFolderItemList.size() - 1);
		}
		return null;
	}
}
