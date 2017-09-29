package com.pobing.uilibs.extend.feature.features;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.webkit.WebResourceResponse;
 
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.pobing.uilibs.extend.utils.DensityUtil;
//import com.pobing.hichujian.application.Application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImageLoadUtils {
	private static ImageLoadUtils imageLoadUtils;
	private static Object lock = new Object();
	private ImageLoader imageLoader;
	private Context context;
	
	private String filePath;
	
	private ImageLoadUtils(Context context){
		this.context=context;
		imageLoader = ImageLoader.getInstance();
		if(!imageLoader.isInited()){
			init();
		}
		filePath=getCachePath().getAbsolutePath();
	}
	
	private void init(){
		Options options = new Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				/*.showImageOnFail(R.drawable.pic_duefult)
				.showImageForEmptyUri(R.drawable.pic_duefult)
				.showStubImage(R.drawable.pic_duefult)*/
				.cacheInMemory(true)
				.cacheOnDisc(true).decodingOptions(options)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCacheExtraOptions(100, 100, null)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
			.build();
		imageLoader.init(config);
	}

	public static ImageLoadUtils newInstance(Context context) {
		if (imageLoadUtils == null) {
			synchronized (lock) {
				if (imageLoadUtils == null) {
					imageLoadUtils = new ImageLoadUtils(context);
				}
			}
		}
		return imageLoadUtils;
	}
	
	public ImageLoader getImageLoader(){
		return imageLoader;
	}
	
	public File getCachePath(){
		String rootDir="chujian";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//sd卡存储
			String root=Environment.getExternalStorageDirectory().getPath();
			String path=root+"/"+rootDir+"/";
			File file=new File(path);
			if(!file.exists() || !file.isDirectory()){
				file.mkdirs();
			}
			return file;
		}else{
			return context.getCacheDir();
		}
	}
	/**
	 * add by Young
	 * @param url
	 * @param iv
	 */
	public void load(String url,ImageView iv){
//		LayoutParams params=new LayoutParams(DensityUtil.dp2px(300),DensityUtil.dp2px(300));
//		 iv.setLayoutParams(params);
		imageLoader.displayImage(url, iv);
	}
	
	/***
	 * file://
	 * @return String
	 */
	public InputStream loadImageSyn(String url){
		if(url==null||url.length()==0)return null;
		SyncImageLoadingListener listener=new SyncImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
			}
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				super.onLoadingFailed(imageUri, view, failReason);
			}
		};
		imageLoader.loadImage(url,listener);
		return listener.getBitmapInputStream();
	}
	
	public InputStream loadImage(String url){
		if(url==null||url.length()==0)return null;
		File file= DiskCacheUtils.findInCache(url, imageLoader.getDiscCache());
		if(file!=null && file.exists()){
			InputStream in=null;
			try {
				in = new FileInputStream(file);
				return in;
			} catch (Exception e) {
				e.printStackTrace();
				if(in!=null)
					try {
						in.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		}
		imageLoader.loadImage(url,new SimpleImageLoadingListener() {
		    @Override
		    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		       // LogCat.w("onLoadingComplete ImageUri:"+imageUri);
		    }
		});
		return null;
	} 
	
	
	public void loadImage(String url,final WebResourceResponse webResponse){
		/*DiscCacheAware disCacheAware=imageLoader.getDiscCache();
		File file = DiscCacheUtil.findInCache(url, disCacheAware);
		try {
			FileInputStream fins=new FileInputStream(file);
			BufferedInputStream bin = new BufferedInputStream(fins);
			
			webResponse.setData(bin);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
		}*/
		//new ReadNativeFileTask(url,webResponse,disCacheAware).execute();
		imageLoader.loadImage(url,new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
//				ByteArrayOutputStream baos= new ByteArrayOutputStream();
//				loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//				webResponse.setData(new ByteArrayInputStream(baos.toByteArray()));
			}
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				
			}
		});
	} 
	
	
	public void loadImageVoid(String url){
		if(url==null||url.length()==0)return;
		imageLoader.loadImage(url,new SimpleImageLoadingListener() {
		    @Override
		    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//		       Log.WARN("onLoadingComplete ImageUri:",imageUri);
		    }
		});
	} 
	
	private static class SyncImageLoadingListener extends SimpleImageLoadingListener {

		private Bitmap loadedImage;
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			this.loadedImage = loadedImage;
		}
		public InputStream getBitmapInputStream() {
			ByteArrayOutputStream baos=null;
			InputStream ins=null;
			try {
				baos= new ByteArrayOutputStream();  
				 loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); 
				 ins = new ByteArrayInputStream(baos.toByteArray());    
			} catch (Exception e) {
				if(loadedImage!=null)loadedImage.recycle();
				if(baos!=null)
					try {
						baos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
			return ins;
			
		}
	}
	
	public void onDestory() {
		synchronized (lock) {
			imageLoader.destroy();
			imageLoadUtils=null;
		}
	}
	
	
//	public class ReadNativeFileTask extends AsyncTask<Integer, Integer, Object>{
//		private String url;
//		private WebResourceResponse webResponse;
//		private DiscCacheAware disCacheAware;
//		public ReadNativeFileTask(String url,WebResourceResponse webResponse,DiscCacheAware disCacheAware){
//			if(webResponse==null){
//				webResponse=new WebResourceResponse("image/*", "UTF-8", null);
//			}
//			this.webResponse=webResponse;
//			this.disCacheAware=disCacheAware;
//			this.url=url;
//		}
//
//		@Override
//		protected void onPreExecute() {
//
//		}
//
//		@Override
//		protected void onPostExecute(Object result) {
//			super.onPostExecute(result);
//		}
//
//		@Override
//		protected void onCancelled() {
//			super.onCancelled();
//		}
//
//		@Override
//		protected Object doInBackground(Integer... params) {
//			File file=DiscCacheUtil.findInCache(url, disCacheAware);
//			if(file!=null && file.exists()){
////				LogCat.e("file:"+file.getAbsolutePath());
//				InputStream in=null;
//				try {
//					in = new FileInputStream(file);
//					webResponse.setData(in);
//				} catch (Exception e) {
//					e.printStackTrace();
//					if(in!=null)
//						try {
//							in.close();
//						} catch (Exception e1) {
//							e1.printStackTrace();
//						}
//					webResponse=null;
//				}
//			}
//			return null;
//		}
//
//	}
	
	
	static ImageLoader imageLoder;
	/**
	 * 
	 * @param imageUrl
	 * @param imgView
	 * @param bar
	 * @param Flag 1表示自己的头像，需要去下载保存到本地 ,0表示只要存在内存缓存里面就好了
	 */
	public static void loadImage(String imageUrl, final ImageView imgView, final View bar,final int Flag) {
		//if(defImgResId<=0)defImgResId=R.drawable.fault_photo;
		if(imageLoder == null){
 			imageLoder=ImageLoadUtils.newInstance(imgView.getContext()).getImageLoader();
 		}
 		if(bar!=null)bar.setVisibility(View.VISIBLE);
 		imageLoder.loadImage(imageUrl, new SimpleImageLoadingListener(){

			@Override
			public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
//				imgView.setImageBitmap(loadedImage);
//				String path = DataKeeper.getpicPortraitPath1(DataKeeper.getAccount(Application.getContext()));
//				if(Flag == 1 ){
//					Utils.savePhotoToSDCard(path, "personal_pic.jpg",loadedImage);
//				}
//				if(bar!=null)bar.setVisibility(View.GONE);
			}
 		});
		
	}
	
	
	/**
	 * 
	 * @param imageUrl
	 * @param imgView
	 * @param bar
	 * @param Flag 1表示自己的头像，需要去下载保存到本地 ,0表示只要存在内存缓存里面就好了
	 */
//	public static void loadImagePic(String imageUrl, final ImageView imgView, final View bar,final int Flag,final List list) {
//		//if(defImgResId<=0)defImgResId=R.drawable.fault_photo;
//		if(imageLoder==null){
// 			imageLoder=ImageLoadUtils.newInstance(imgView.getContext()).getImageLoader();
// 		}
// 		if(bar!=null)bar.setVisibility(View.VISIBLE);
// 		imageLoder.loadImage(imageUrl, new SimpleImageLoadingListener(){
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
//				list.add(loadedImage);
//				imgView.setImageBitmap(loadedImage);
//				String path = DataKeeper.getpicPortraitPath1(DataKeeper.getCurrentUserDO(Application.getContext()).getPhoneNumber());
//				if(Flag == 1 ){
//					Utils.savePhotoToSDCard(path, "personal_pic.jpg",loadedImage);
//				}
//				if(bar!=null)bar.setVisibility(View.GONE);
//			}
// 		});
//
//	}

	
	
//	public static void loadImageSinaHeader(String imageUrl, final ImageView imgView  ) {
//		//if(defImgResId<=0)defImgResId=R.drawable.fault_photo;
//		if(imageLoder==null){
//			imageLoder=ImageLoadUtils.newInstance(imgView.getContext()).getImageLoader();
//		}
//		imageLoder.loadImage(imageUrl, new SimpleImageLoadingListener(){
//			@Override
//			public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
//				imgView.setImageBitmap(loadedImage);
//				String headPath = DataKeeper.getHeadPortraitPath(DataKeeper.getAccount(Application.getContext()));
//				Utils.savePhotoToSDCard(headPath.substring(0, headPath.lastIndexOf("/")), headPath.substring(headPath.lastIndexOf("/") + 1), loadedImage);
//			}
//		});
//	}

	
}
