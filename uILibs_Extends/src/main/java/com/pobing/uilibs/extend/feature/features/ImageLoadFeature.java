package com.pobing.uilibs.extend.feature.features;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pobing.uilibs.extend.events.DownloadEvent;
import com.pobing.uilibs.extend.events.IUILibsListener;
import com.pobing.uilibs.extend.utils.Reflect;
import com.pobing.uilibs.feature.callback.DrawableStateCallback;
import com.pobing.uilibs.feature.callback.LayoutCallback;
import com.pobing.uilibs.feature.features.AbsFeature;

import java.lang.reflect.Method;

/**
 * Guide:
 * List滚动时的优化：兼容RichListView的实现方式
 * 利用SmoothScrollFeature兼容非UIKit的ListView 
 * 例子：
 * Step only one:
 *      mListView.setOnScrollListener(new SmoothScrollFeature());
 */

/**
 *
 * ImageLoadFeature support the image view to load the remote image with
 * download url.
 *
 */
public class ImageLoadFeature extends AbsFeature<ImageView> implements LayoutCallback, DrawableStateCallback
{

	private ImageView mOriginView;
	private Context mContext;
    private String mUrl;
    private boolean mNoRepeatOnError = false;
    private int mPlaceholdResourceId;
    private Drawable mPlaceholdForground;
    private int mErrorImageId;
//    private PhenixTicket mTicket;

    private final static int S_INIT = 0;
    private final static int S_LOADING = 1;
    private final static int S_DONE_SUC = 2;
    private final static int S_DONE_FAIL = 2;
    
    private int mState = S_INIT;
    		
    private boolean mSkipAutoSize = false;
    /**
     * 是否启用自动适应大小模式
     *
     * */
    public boolean skipAutoSize( boolean s)
    {
    	mSkipAutoSize = s;
    	return mSkipAutoSize;
    }
    @Override
	public void setHost(ImageView host) {
		super.setHost(host);
		mOriginView = host;
		mContext = host.getContext();
		if(!TextUtils.isEmpty(mUrl)){
			loadImageIfNecessary(false);
		}
	}

    IUILibsListener<DownloadEvent.SuccessEvent> mSucclistener;

    /**
     * 设置下载图片成功时的回调
     *
     * */
    public ImageLoadFeature succListener( IUILibsListener<DownloadEvent.SuccessEvent> listener)
    {
    	mSucclistener = listener;
    	return this;
    }

    /**
     * 设置下载图片失败时的回调
     *
     * */
    IUILibsListener<DownloadEvent.FailureEvent> mFaillistener;
    public ImageLoadFeature failListener( IUILibsListener<DownloadEvent.FailureEvent> listener)
    {
    	mFaillistener = listener;
    	return this;
    }

	/**set download url of the image
	 * @param url
	 */
	public void setImageUrl(String url) {

		
		this.mUrl = url;
		mNoRepeatOnError = false;
		mState = S_INIT;
		Log.v("phenix", "!!!setImageUrl mstate"+ mState + "url:" +url );
        // The URL has potentially changed. See if we need to load it.
        if(this.getHost() != null){
        	loadImageIfNecessary(false);
        }
    }


    /**
     * Sets the default image resource ID to be used for this view until the attempt to load it
     * completes.
     * @param id
     */
    public void setPlaceHoldImageResId(int id) {
    	mPlaceholdResourceId = id;
    }
    
    public void setPlaceHoldForground(Drawable d)
    {
    	mPlaceholdForground = d;
    }
    

    /**
     * Sets the error image resource ID to be used for this view in the event that the image
     * requested fails to load.
     * @param id
     */
    public void setErrorImageResId(int id) {
        mErrorImageId = id;
    }

    //static IImageDecideUrl sDefaultDecideUrl = null;
    static boolean sBLoadDecider = false;
    static Method mMethod = null;
    static Object mDecideIns = null;
    boolean _tryLoad()
    {
    	//sDefaultDecideUrl

    	//this.mContext.getResources().getString(id);
    	if(mMethod !=null && mDecideIns !=null)
    		return true;

        if(null == mContext)
        	return false;

        Context context = this.mContext;
        Resources resources = context.getResources();
        if(resources == null)
        	return false;
        String path = context.getPackageName()+":string/MIDDLEWARE_CONFIG_UIKIT_DECIDEURL";

        int indentify = resources.getIdentifier(path, null, null);
        if(indentify>0){
        	String str = resources.getString(indentify);
        	Log.v("ConfigLoad", "resource:" + str);

        	@SuppressWarnings("rawtypes")
			Class c = null;
			try {
				c = Class.forName(str);

			} catch (ClassNotFoundException e) {

				e.printStackTrace();
				return false;
			}

			Method m = Reflect.getMethod(c, "onDecide", new Class[]{String.class,Integer.class,Integer.class});
			if( null == m)
			{
				return false;
			}
			Object invokeInstance;
			try {
				invokeInstance = c.newInstance();
			} catch (InstantiationException e) {

				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {

				e.printStackTrace();
				return false;
			}
			mMethod = m;
			mDecideIns = invokeInstance;
			return true;

        }
        return false;
    }


    String _onDecide(String o, Integer  width, Integer height )
    {
    	String result = (String) Reflect.invokeMethod(mMethod, mDecideIns, new Object[]{ o,width,height});
		if( result != null)
		{
			Log.v("Reflect", "!!!Success:" +result);
		}
		else
			result = o;
		return result;
    }
    
    /*
    public static void setDefaultDecideUrl( IImageDecideUrl in)
    {
    	sDefaultDecideUrl = in;
    }
    */

    private static final int S_SHOWING  = 0;
    private static final int S_SCROLLING  = 1;

    private int scrollState = S_SHOWING;

    void pause()
    {
    	scrollState = S_SCROLLING;
    	//Phenix.instance().pauseDispatch();
//    	Log.v(Phenix.LOG_TAG, "scrollBegin");
    }

    void resume()
    {
//    	Log.v(Phenix.LOG_TAG, "scrollEnd");
    	if( scrollState == S_SCROLLING)
    	{
    		scrollState = S_SHOWING;
    		mState = S_INIT;
    		Log.v("phenix", "resume reset to init mState" + mState + ",url:" + mUrl );
    		//Phenix.instance().resumeDispatch();
    		loadImageIfNecessary(false);
    	}
    }

    private void loadImageIfNecessary(final boolean isInLayoutPass) {
    	boolean placehold = __loadImageIfNecessary(isInLayoutPass);
    	if(placehold)
    	{
    		
    		
    		if (mPlaceholdResourceId != 0 && null != mOriginView) {
            	Log.v("PHENIX", "loadImageIfNecessary returned set placehold:" + mUrl);
            	 mOriginView.setImageDrawable(null); //去掉前景
                 mOriginView.setBackgroundResource(mPlaceholdResourceId);            	
            }
    		else if( mPlaceholdForground != null &&  null != mOriginView)
    		{
    			Log.v("PHENIX", "loadImageIfNecessary returned set placehold for forground:" + mUrl);
                mOriginView.setImageDrawable(mPlaceholdForground); 
    		}
            
    	}


    }
    private boolean __loadImageIfNecessary(final boolean isInLayoutPass) {

        ImageLoadUtils.newInstance(mOriginView.getContext()).getImageLoader().loadImage(mUrl, new SimpleImageLoadingListener(){

            @Override
            public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
                super.onLoadingComplete(imageUri,view,loadedImage);
                mOriginView.setImageBitmap(loadedImage);
                if (null != mSucclistener)
                    mSucclistener.onHappen(new DownloadEvent.SuccessEvent());
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                try {
					
                	mFaillistener.onHappen(new DownloadEvent.FailureEvent());
				} catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
return true;
    }

    /**
     * Loads the image for the view if it isn't already loaded.
     * @param isInLayoutPass True if this was invoked from a layout pass, false otherwise.
     * @return true should show the place hold image
     *
     */
//    private boolean _loadImageIfNecessary(final boolean isInLayoutPass) {
//        int width = mOriginView.getWidth();
//        int height = mOriginView.getHeight();
//
//        boolean isFullyWrapContent = mOriginView.getLayoutParams() != null
//                && mOriginView.getLayoutParams().height == LayoutParams.WRAP_CONTENT
//                && mOriginView.getLayoutParams().width == LayoutParams.WRAP_CONTENT;
//        // if the view's bounds aren't known yet, and this is not a wrap-content/wrap-content
//        // view, hold off on loading the image.
//        if (width == 0 && height == 0 && !isFullyWrapContent) {
//            return false;
//        }
//
//        // if the URL to be loaded in this view is empty, cancel any old requests and clear the
//        // currently loaded image.
//        if (TextUtils.isEmpty(mUrl)) {
//            Phenix.instance().cancel(mTicket);
//            mOriginView.setImageBitmap(null);
//            return false;
//        }
//
//
//        //TODO:对相同的Image进行判断
//       /* if( mTicket != null && mTicket.theSame(mUrl) && mTicket.isDone )
//        {
//        	Log.v(Phenix.LOG_TAG, "already done!");
//        	return;
//        }*/
//
//
//        //取消上一把正在处理的图片
//        if( null != mTicket && !mTicket.theSame(mUrl) )
//        {
//        	mTicket.cancel();
//        }
//
//        if(mNoRepeatOnError)
//        {
//        	Log.v(Phenix.LOG_TAG, "meet unprepare error so not repeat mUrl:" + mUrl);
//
//        	return false;
//        }
//
//        boolean memOnly = false;
//        if(scrollState == S_SCROLLING)
//        {
//        	Log.v(Phenix.LOG_TAG, "scrolling so don't download url" + mUrl);
//        	memOnly = true;
//
//        }
//
//        //here to adjust to width, height
//        String decideUrl = mUrl;
//
//        if(!mSkipAutoSize)
//        {
//	        if( !sBLoadDecider)
//	        {
//	        	sBLoadDecider = _tryLoad();
//	        	//sBLoadDecider = true;
//	        }
//
//	        if(sBLoadDecider)
//	        {
//	        	decideUrl = _onDecide( mUrl, this.mOriginView.getWidth(), this.mOriginView.getHeight());
//	        }
//        }
//
//
//        if( mState != S_INIT)
//        {
//        	Log.v(Phenix.LOG_TAG, "duplicated loading:" + mUrl);
//        	//return false;
//        }
//        mState = S_LOADING;
//        Log.v("phenix", "!!!! mstate S_LOADING" + mOriginView.hashCode()  + "url:" + mUrl );
//
//
//        final String realUrl = decideUrl;
//        mTicket = Phenix.instance().with(mContext).load(decideUrl).memOnly(memOnly).succListener(
//        			new IPhenixListener<SuccPhenixEvent>(){
//
//        				@Override
//						public boolean onHappen(SuccPhenixEvent event) {
//
//        					return _onHappen(event,event.isImmediate());
//        				}
//
//        				public boolean _onHappen(final SuccPhenixEvent event, boolean isImmediate)
//        				{
//        					String targetUrl = event.getUrl();
//        					Log.v("phenix", "!!!! _onHappen view:"+ mOriginView.hashCode() + "targeturl:" + targetUrl );
//        					Log.v("phenix", "!!!! _onHappen view: "+ mOriginView.hashCode() + "realurl:" + realUrl );
//
//        					mState = S_DONE_FAIL;
//        					if(targetUrl != null &&  realUrl != null )
//        					{
//        						//Log.v(Phenix.LOG_TAG, " mUrl：" + mUrl + ",target:" + targetUrl );
//
//
//        						if( !targetUrl.startsWith(realUrl))
//        						{
//        							Log.i(Phenix.LOG_TAG, "mUrl isn't the same! realUrl：" + realUrl + ",target:" + targetUrl );
//        							return true;
//
//        						}
//        					}
//
//	                        if (isImmediate && isInLayoutPass) {
//	                        	mOriginView.post(new Runnable() {
//	                                @Override
//	                                public void run() {
//	                                    _onHappen(event,false);
//	                                }
//	                            });
//	                            return true;
//
//	                        }
//	                        Log.v("PHENIX", "image load feature load success:" + mUrl);
//
//
//
//	                        BitmapDrawable d = event.getDrawable();
//	                        if (d != null && d.getBitmap() != null)
//	                        {
//
//	                        	mOriginView.setImageBitmap(d.getBitmap());
//	                        	//mOriginView.setImageDrawable(d);
//	                        	event.getTicket().setDone(true);
//
//	                        	mState = S_DONE_SUC;
//	                        	Log.v("phenix", "!!! mstate S_DONE_SUC"+ mState + "url:" + mUrl );
//	                        	//给UI回调
//		                        if(null != mSucclistener)
//		                        {
//		                        	mSucclistener.onHappen(event);
//		                        }
//
//
//	                        } else {
//                                if (mPlaceholdResourceId != 0) {
//                                    Log.v("PHENIX", "image load feature load success, but drawable is null" + mUrl);
//                                    mOriginView.setBackgroundResource(mPlaceholdResourceId);
//                                    mOriginView.setImageBitmap(null);
//                                }
//                                else if( mPlaceholdForground != null )
//                                {
//                                	mOriginView.setImageDrawable(mPlaceholdForground);
//                                }
//                                else
//                                {
//                                	mOriginView.setImageBitmap(null);
//                                }
//
//
//                            }
//
//	                        return true;
//
//
//						}
//
//        			}
//        		).memCacheMissListener( new IPhenixListener<MemCacheMissPhenixEvent>(){
//
//					@Override
//					public boolean onHappen(MemCacheMissPhenixEvent e) {
//
//						Log.v("phenix", "!!! MemCacheMissPhenixEvent _onHappen"+ mState + "url:" + mUrl );
//
//						if (mPlaceholdResourceId != 0) {
//                        	Log.v("PHENIX", "image load feature mem cache miss show placehold:" + mUrl);
//                        	mOriginView.setImageBitmap(null); //清空前景
//                        	mOriginView.setBackgroundResource(mPlaceholdResourceId);
//                        }
//						else if( mPlaceholdForground != null )
//						{
//							Log.v("PHENIX", "image load feature mem cache miss show placehold forground:" + mUrl);
//                        	mOriginView.setImageDrawable(mPlaceholdForground);
//						}
//						else
//                        {
//                        	mOriginView.setImageBitmap(null);
//                        }
//						return false;
//					}
//
//        		}).failListener(
//        			new IPhenixListener<FailPhenixEvent>(){
//
//						@Override
//						public boolean onHappen(FailPhenixEvent event) {
//
//							 Log.v("phenix", "!!! FailPhenixEvent _onHappen"+ mState + "url:" + mUrl );
//							 switch( event.getResultCode() )
//							 {
//							 //控制出错情况下，不重试
//							 case -1: //无网络错误
//							 case 404: //URL有误
//								 mNoRepeatOnError = true;
//								 break;
//						     default:
//						    	 mNoRepeatOnError = false;
//							 }
//
//                            event.getTicket().setDone(true);
//                            mOriginView.setImageBitmap(null);
//                            if (mErrorImageId != 0) {
//                                mOriginView.setBackgroundResource(mErrorImageId);
//                            }
//                        	mState = S_DONE_FAIL;
//                        	Log.v("phenix", "!!! mstate S_DONE_FAIL"+ mState + "url:" + mUrl );
//                            //给UI回调
//                            if(null != mFaillistener)
//		                    {
//								 mFaillistener.onHappen(event);
//		                    }
//							return true;
//						}
//
//        			}
//
//        		).fetch();
//        mTicket.setUrl(mUrl);
//        return false;
//    }

    @Override
	public void constructor(Context context, AttributeSet attrs, int defStyle) {
		// TODO Auto-generated method stub

	}


	@Override
	public void beforeOnLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub

	}


	@Override
	public void afterOnLayout(boolean changed, int left, int top, int right,
			int bottom) {
		loadImageIfNecessary(true);

	}


	@Override
	public void beforeDrawableState() {
		// TODO Auto-generated method stub

	}


	@Override
	public void afterDrawableState() {
		mOriginView.invalidate();
	}
	
	public void clearCache()
	{
		if( null == this.mUrl )
			return;
		String decideUrl = mUrl;
		if(sBLoadDecider)
        {
        	decideUrl = _onDecide( mUrl, this.mOriginView.getWidth(), this.mOriginView.getHeight());
        }
//		Phenix.instance().clearCache(decideUrl);
	}

}