package com.pobing.uilibs.extend.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 管理拍照相关
 * 
 * @author pengfan.pf
 *
 */
public class ImageManager {
	public static final int MAX_WIDTH = 640;

	public static final int TAKE_PICTURE = 0x100;
	public static final int CHOOSE_PICTURE = 0x101;

	private static File PHOTO_DIR;
	public static File PHOTO_FILE;

	private static final String TAG = "chatImageManager";
	
	public static String PATH="";
	
	private static Context context;
	public static void init(Context conte) {
		context=conte;
//		PATH="photo-cache"+System.currentTimeMillis()+".png";
		PHOTO_DIR = new File(getCaheDir(context), "cachePhoto");
		PHOTO_DIR.mkdirs();
//		PHOTO_FILE = new File(getCaheDir(context), PATH);
	}

	public static File getCaheDir(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return context.getExternalCacheDir();
		} else {
			File cacheDir = new File(Environment.getExternalStorageDirectory()
					+ context.getPackageName() + "/cahce");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			return cacheDir;
		}
	}

	/**
	 * 启动拍照
	 * 
	 * @param activity
	 */
	public static void doTakePhoto(Activity activity) {
		PATH="photo-cache"+System.currentTimeMillis()+".png";
//		PHOTO_DIR = new File(getCaheDir(context), "cachePhoto");
//		PHOTO_DIR.mkdirs();
		PHOTO_FILE = new File(getCaheDir(context), PATH);
		
		
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (PHOTO_FILE.exists()) {
			PHOTO_FILE.delete();
		}
		Uri imageUri = Uri.fromFile(PHOTO_FILE);
		// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		if (activity != null) {
			activity.startActivityForResult(openCameraIntent, TAKE_PICTURE);
		}
	}

	/**
	 * 获取拍照后的图片
	 * 
	 * @param data
	 * @param maxLength
	 * @return
	 */
	public static void getBitmapByTakePhoto(Intent data, int maxLength,
			BitmapSource BitmapResult) {
		if (data != null
				&& !ImageTool.isExistFile(PHOTO_FILE.getAbsolutePath())) {
			Bundle bundle = data.getExtras();
			BitmapResult.bitmap = (Bitmap) bundle.get("data");
		} else {
			BitmapResult.bitmap = ImageTool.getPhotoFromSDCardandZoom(
					PHOTO_FILE.getAbsolutePath(), maxLength);
			BitmapResult.degree = ThumbnailUtil.readRotationDegree(PHOTO_FILE
					.getAbsolutePath());
		}
	}

	/**
	 * degree
	 * 
	 * @param bitmap
	 * @param degree
	 * @return
	 */
	public static File saveRandomBitmapFile(BitmapSource source) {
		if (source.bitmap != null) {
			File tempPhotoFile = genTempFile();
			saveBitmapFile(source, tempPhotoFile);
			return tempPhotoFile;
		}
		return null;
	}

	public static File saveNameKeyBitmapFile(BitmapSource source, String key) {
		File file = new File(PHOTO_DIR, key.hashCode() + "");
		if(file.exists()){
			return file;
		}
		else if (source.bitmap != null) {
			saveBitmapFile(source, file);
			return file;
		}
		return null;
	}

	public static void saveBitmapFile(BitmapSource source, File photoFile) {
		if (source.bitmap != null) {
			if ((source.degree != -1) && (source.degree != 0)) {
				Bitmap b = ThumbnailUtil.rotate(source.bitmap, source.degree);
				ImageTool.savePhotoDirectToSDCard(b, photoFile);
				ThumbnailUtil.writeOrientation(photoFile.getAbsolutePath(), 0);
				if (!b.isRecycled())
					b.recycle();
			} else {
				ImageTool.savePhotoDirectToSDCard(source.bitmap, photoFile);
			}
			if (!source.bitmap.isRecycled()) {
				source.bitmap.recycle();
			}
		}
	}

	public static BitmapSource genBitmapSouce(String filePath, int maxLength) {
		if (TextUtils.isEmpty(filePath) || !ImageTool.isExistFile(filePath))
			return null;
		BitmapSource source = new BitmapSource();
		source.degree = ThumbnailUtil.readRotationDegree(filePath);

		source.bitmap = ImageTool
				.getPhotoFromSDCardandZoom(filePath, maxLength);

		return source;
	}

	@SuppressLint("NewApi") public static long getBitmapsize(Bitmap bitmap) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			if (bitmap != null)
				return bitmap.getByteCount();
			return 0;
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();

	}

	/**
	 * 批量压缩API
	 * 
	 * @param filePathList
	 * @param pix
	 *            : 想要压缩之后的宽度像素值
	 * @return
	 */
	public static ArrayList<String> compressBitmapPatch(
			ArrayList<String> filePathList, int pixWidth) {
		long costTime = System.currentTimeMillis();
		if (pixWidth <= 0)
			pixWidth = MAX_WIDTH;
		ArrayList<String> resultList = new ArrayList<String>();
		for (String filePath : filePathList) {
			BitmapSource res = genBitmapSouce(filePath, pixWidth);
			if (res.bitmap != null) {
				long bitmapSize = getBitmapsize(res.bitmap);
				File file = saveRandomBitmapFile(res);
				if (file != null) {
					resultList.add(file.getAbsolutePath());
				}
			}
		}
		return resultList;
	}

	private static File genTempFile() {
		return new File(PHOTO_DIR, "tempCache" + System.currentTimeMillis()
				+ ".jpg");
	}

	public static class BitmapSource {
		private Bitmap bitmap;
		private int degree;

		public Bitmap getBitmap() {
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		public int getDegree() {
			return degree;
		}

		public void setDegree(int degree) {
			this.degree = degree;
		}
	}
}
