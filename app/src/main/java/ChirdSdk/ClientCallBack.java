package ChirdSdk;

import android.graphics.Bitmap;

public interface ClientCallBack {
	
	public static final int     PARAMCHANGE_TYPE_VIDEO_ABILITY	=  0;
	public static final int     PARAMCHANGE_TYPE_VIDEO_PARAME	=  1;
	public static final int     PARAMCHANGE_TYPE_VIDEO_CTRL  	=  2;
	public static final int     PARAMCHANGE_TYPE_AUDIO_PARAM 	=  3;
	public static final int     PARAMCHANGE_TYPE_SERIAL_PARAM 	=  4;
	public static final int     PARAMCHANGE_TYPE_GPIO_STATUS  	=  5;
	public static final int     PARAMCHANGE_TYPE_VIDEO_ALLCTRL  =  6;

	void paramChangeCallBack(int changeType);

	void disConnectCallBack();

	void snapBitmapCallBack(Bitmap bitmap);
	
	void recordTimeCountCallBack(String times);
	void recordStopBitmapCallBack(Bitmap bitmap);
	
	/* 视频参数（格式、分辨率、帧率） */
	public static int VIDEO_FORMAT_YUYV     = 0X01;
	public static int VIDEO_FORMAT_MJPEG    = 0X02;
	public static int VIDEO_FORMAT_H264     = 0X03;
	public static int VIDEO_FORMAT_YUV420SP = 0X04;
	void videoStreamBitmapCallBack(Bitmap bitmap);
	void videoStreamDataCallBack(int format, int width, int height, int datalen, byte[] data);
	
	void serialDataCallBack(int datalen, byte[] data);
	
	void audioDataCallBack(int datalen, byte[] data);

}
