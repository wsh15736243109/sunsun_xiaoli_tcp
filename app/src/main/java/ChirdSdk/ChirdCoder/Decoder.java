package ChirdSdk.ChirdCoder;


import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build;
import android.text.TextUtils;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;


public class Decoder {
    private MediaCodec mCodec = null;
    private Surface mSurface = null;
    private MediaFormat mFormat;
    private boolean isStart = false;
    
    private boolean isConfigure = false;
    
    private int mWidth, mHeight;
    
    public Decoder(int width, int height, Surface surface) {
    	mFormat = MediaFormat.createVideoFormat("video/avc", width, height);
        try {
            mCodec = MediaCodec.createDecoderByType("video/avc");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWidth = width;
        mHeight= height;
        
        if (surface != null){
        	mCodec.configure(mFormat, surface,null,0);
        	mSurface = surface;
        	
        	isConfigure = true;
        }
        
        isStart = false;       
    }
    
    public boolean isConfigure(){
    	return isConfigure;
    }
    
    public int setConfigure(Surface surface, int width, int height){
    	if (surface == null)	return -1;
    	
    	stop();
    	if (mCodec != null){
    		mCodec.release();
    	}

    	mFormat = MediaFormat.createVideoFormat("video/avc", width, height);

        try {
            mCodec = MediaCodec.createDecoderByType("video/avc");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCodec.configure(mFormat, surface, null,0); 
        mSurface = surface;
        mWidth = width;
        mHeight= height;
        
        isConfigure = true;
        isStart = false;  
  
        return 0; 	
    }
    
    public void releaseConfigure(){
    	if (isConfigure){
	    	stop();
	    	mCodec.release();    	
	    	mCodec = null;
	    	isConfigure = false;
	    	isStart = false;
    	}
    }
    
    public int setSurface(Surface surface){ 	
    	if (surface == null)	return -1;
    	
    	mSurface = surface;

    	return 0;
    }
    
    public int getWidth(){
    	return mWidth;
    }
    
    public int getHeight(){
    	return mHeight;
    }
    
    public int setWidthAndHeight(int width, int height) throws IOException {
       	stop();
    	mCodec.release();

    	mFormat = MediaFormat.createVideoFormat("video/avc", width, height);
    	mCodec = MediaCodec.createDecoderByType("video/avc");
        mCodec.configure(mFormat, mSurface, null,0);
        
        mWidth = width;
        mHeight= height;
        
        isConfigure = true;
        isStart = false;     

    	return 0;
    }
    
    
    
    /* The content is scaled to the surface dimensions */
    public static final int VIDEO_SCALING_Mode_FIT               = 1;   
    /*The content is scaled, maintaining its aspect ratio, the whole
    surface area is used, content may be cropped*/  
    public static final int VIDEO_SCALING_Mode_FIT_WITH_CROPPING = 2;
    
    public void setVideoScalingMode(int mode){
    	mCodec.setVideoScalingMode(mode);
    }
    
    public int getOutputFormat() {
        if (mCodec == null) {
            return -1;
        }

        return mCodec.getOutputFormat().getInteger(MediaFormat.KEY_COLOR_FORMAT);
    }
    
    public static boolean isH264CodecSupport() {
    	if (Build.VERSION.SDK_INT < 16) {
    		return false;
    	}
		int count = MediaCodecList.getCodecCount();
		for (int i = 0; i < count; i++) {
			MediaCodecInfo info1 = MediaCodecList.getCodecInfoAt(i);
			String[] ns = info1.getSupportedTypes();
			for (String n : ns) {
			    if (TextUtils.equals(n, "video/avc")) {
			        return true;
			    }
			}
		}

        return false;
    }
    
    public boolean isStart(){
    	return isStart;
    }

    public int  start() {
    	if (isStart)	return 0;
    	
    	if (mCodec == null || mSurface == null){
    		isStart = false;
    		return -1;
    	}
    	
    	mCodec.start();
        isStart = true;
        
        return 0;
    }
    
    public int stop() {
    	if (!isStart)  return 0;
    	
    	if (mCodec == null || mSurface == null){
    		return -1;
    	}
    	
    	mCodec.stop();
    	isStart = false;
        
    	return 0;
    }

   
    public int decodeToSurface(byte[] indata, int length) {
      	
        if (mCodec == null) {
            return -1;
        }
        
        if (mSurface == null){
        	return -2;
        }
        
        if (!isStart){
        	start();
        }

        int inindex = mCodec.dequeueInputBuffer(10000);
        if (inindex < 0) {
        	return -3;
        }

        ByteBuffer[] byteBuffer = mCodec.getInputBuffers();      
        byteBuffer[inindex].put(indata);
        mCodec.queueInputBuffer(inindex, 0, length, System.currentTimeMillis(), 0);
        
        /* 释放解码缓存区 */
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 0);
        
        while(outputBufferIndex >= 0){
        	mCodec.releaseOutputBuffer(outputBufferIndex, true);
        	outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 0);
        }
        
        return 0;
    }

    
}
