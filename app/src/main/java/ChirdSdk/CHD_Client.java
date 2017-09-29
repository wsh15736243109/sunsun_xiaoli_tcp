package ChirdSdk;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Semaphore;

import ChirdSdk.Apis.chd_wmp_apis;
import ChirdSdk.Apis.st_AudioFrame;
import ChirdSdk.Apis.st_GpioInfo;
import ChirdSdk.Apis.st_VideoAbilityInfo;
import ChirdSdk.Apis.st_VideoCtrlInfo;
import ChirdSdk.Apis.st_VideoFrame;
import ChirdSdk.Apis.st_VideoParamInfo;
import ChirdSdk.Apis.st_WirelessInfo;
import ChirdSdk.ChirdCoder.nativeCoder;

public class CHD_Client {

	public static final int RET_ERROR_SUCCESS = 0;
	public static final int RET_ERROR_RET_FAILED = -1;
	public static final int RET_ERROR_RET_TIMEOUT = -2;
	public static final int RET_ERROR_RET_NET_TIMEOUT = -4;
	public static final int RET_ERROR_RET_DEVICE_OFFLINE = -6;
	public static final int RET_ERROR_RET_INVALID_HANDLE = -7;
	public static final int RET_ERROR_SESSIONID = -8;
	public static final int RET_ERROR_PROTOCOL = -9;
	public static final int RET_ERROR_NET_BIND = -10;
	public static final int RET_ERROR_RET_INVALID_ADDRESS = -11;
	public static final int RET_ERROR_RET_DEVICE_NOT_ONLINE = -12;
	public static final int RET_ERROR_PASSWD = -13;
	public static final int RET_ERROR_INVALID_PARAMETER = -15;
	public static final int RET_ERROR_PARAMETER_LENGHTH_OVERFLOW = -14;
	public static final int RET_ERROR_INITIALIZED_FAIL = -16;

	private static final int SIGNAL_SNAP = 0X04;
	private static final int SIGNAL_DISCONNECT = 0X01;
	private static final int SIGNAL_RECORD_TIME = 0X02;
	private static final int SIGNAL_RECORD_STOP = 0X03;

	private static final int SIGNAL_PARAM_CHANGE = 0X05;

	/* global param */
	private long mHandle = 0;
	private long mSessionID = 0;
	private boolean isConnect = false;
	private nativeCoder mCoder = new nativeCoder();
	private chd_wmp_apis mClient = new chd_wmp_apis();
	private Semaphore decoderSemp = new Semaphore(0);
	private Semaphore displaySemp = new Semaphore(0);

	private Handler mSignalHandler = null;
	private ClientCallBack mClientCallBack = null;

	/* video */
	private long jpegHandle = 0;
	private long h264Handle = 0;
	private Bitmap mBitmap = null;
	public boolean isOpenVideoStream = false;
	private int videoFps = 0, videoBps = 0;
	private Queues videoQueue = new Queues();

	/* snap shot */
	private int mSnapCount = 0;
	private String mSnapPath = null;
	private String mSnapFileName = null;

	/* record */
	private int mRecordTimes = 0;
	private boolean isRecord = false;
	private String mRecordFileName = null;
	private boolean isSupportRecord = false;
	private Handler mRecordTimeHandler = null;
	private Runnable RecordTimeRunnable = null;
	private int mRecordStatue = RECORD_STATUE_NONE;
	private static final int RECORD_STATUE_NONE = 0;
	private static final int RECORD_STATUE_START = 1;
	private static final int RECORD_STATUE_WRITEDATA = 2;
	private static final int RECORD_STATUE_STOP = 3;

	/* audio */
	private boolean isOpenAudio = false;

	/* serial */
	private boolean isOpenSerial = false;

	/* gpio */
	private boolean bGpioget = false;
	private st_GpioInfo mGpioInfo = new st_GpioInfo();;

	public CHD_Client() {

		jpegHandle = mCoder.chird_vdec_create(mCoder.CODE_FMT_MJPEG,
				mCoder.CODE_PIXEL_FMT_RGB565);
		h264Handle = mCoder.chird_vdec_create(mCoder.CODE_FMT_H264,
				mCoder.CODE_PIXEL_FMT_RGB565);

		mRecordTimeHandler = new Handler();
		RecordTimeRunnable = new Runnable() {
			public void run() {
				SendMessage(SIGNAL_RECORD_TIME, mRecordTimes++, 0);
				mRecordTimeHandler.postDelayed(this, 1000);
			}
		};/* end RecordTimeRunnable */

		mSignalHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (mClientCallBack != null) {
					switch (msg.what) {
					case SIGNAL_DISCONNECT:
						mClientCallBack.disConnectCallBack();
					case SIGNAL_PARAM_CHANGE:
						mClientCallBack.paramChangeCallBack(msg.arg1);
						break;
					case SIGNAL_RECORD_STOP:
						mRecordTimes = 0;
						/* record thumbnail Bitmap CallBack */
						synchronized (this) {
							if (mBitmap != null) {
								mClientCallBack
										.recordStopBitmapCallBack(mBitmap);
							}
						}

						break;
					case SIGNAL_RECORD_TIME:
						mClientCallBack
								.recordTimeCountCallBack(getRecordTimerString(msg.arg1));
						break;
					case SIGNAL_SNAP:
						/* snap thumbnail Bitmap CallBack */
						synchronized (this) {
							if (mBitmap != null) {
								mClientCallBack.snapBitmapCallBack(mBitmap);
							}
						}
						break;
					}
				}
			}
		}; /* end mSignalHandler */

	}

	private void SendMessage(int signal, int arg1, int arg2) {
		Message message = new Message();
		message.what = signal;
		message.arg1 = arg1;
		message.arg2 = arg2;
		mSignalHandler.sendMessage(message);
	}

	public void setClientCallBack(ClientCallBack callback) {
		mClientCallBack = callback;
	}

	public boolean isConnect() {
		return isConnect;
	}

	public int connectDevice(String Address, String Passwd) {
		if (isConnect) {
			return RET_ERROR_SUCCESS;
		}

		Address = Address.replace("", "");
		Passwd = Passwd.replace("", "");
		mHandle = mClient.CHD_WMP_ConnectDevice(Address, Passwd);
		Log.v("test", "Handle:" + mHandle + "Address[" + Address + "]Passwd["
				+ Passwd + "]");

		if (mHandle < 0) {
			return (int) mHandle;
		}
		mSessionID++;
		isConnect = true;
		/* Data listener thread */
		DataPollThread thread = new DataPollThread();
		thread.start();
		/* Video decode thread */
		videoDecoderThread videoDecThread = new videoDecoderThread();
		videoDecThread.start();
		/* Video display thread */
		displayThread displayThread = new displayThread();
		displayThread.start();

		return RET_ERROR_SUCCESS;
	}

	public int disconnectDevice() {
		if (!isConnect) {
			return RET_ERROR_SUCCESS;
		}
		mClient.CHD_WMP_Disconnect(mHandle);
		mHandle = 0;
		isConnect = false;
		isRecord = false;
		isOpenAudio = false;

		return RET_ERROR_SUCCESS;
	}

	public boolean isOpenVideoStream() {
		if (!isConnect) {
			return false;
		}
		return isOpenVideoStream;
	}

	public int openVideoStream() {
		if (!isConnect) {
			return -1;
		}

		int ret = mClient.CHD_WMP_Video_Begin(mHandle);
		if (ret < 0) {
			return ret;
		}

		mSnapCount = 0;
		isOpenVideoStream = true;
		mRecordStatue = RECORD_STATUE_NONE;

		return 0;
	}

	public int closeVideoStream() {
		if (!isConnect) {
			return -1;
		}

		int ret = mClient.CHD_WMP_Video_End(mHandle);
		if (ret < 0) {
			return ret;
		}

		/* if in the record, you must stop record */
		if (isRecord) {
			stopRecord();
		}

		isOpenVideoStream = false;
		return 0;
	}

	/* setting Device hardware photo storage photo path */
	public void setSnapSavePath(String path) {
		mSnapPath = path;
	}

	public int snapShot(String filename) {
		if (!isConnect || filename == null) {
			return -1;
		}

		mSnapFileName = filename;
		mSnapCount++;

		/*
		 * int format = mClient.CHD_WMP_Video_GetFormat(mHandle); if (format ==
		 * VIDEO_FORMAT_MJPEG) { return mClient.CHD_WMP_Video_SnapShot(mHandle);
		 * } else { mSnapCount++; }
		 */
		return 0;
	}

	public boolean isRecord() {
		return isRecord;
	}

	public int startRecord(String filename) {
		if (isRecord) {
			return 0;
		}
		if (!isConnect || !isOpenVideoStream || !isSupportRecord) {
			return -1;
		}

		isRecord = true;
		mRecordTimes = 0;
		mRecordFileName = filename;
		mRecordStatue = RECORD_STATUE_START;

		/* h264 video stream record I must be the first frame */
		int format = mClient.CHD_WMP_Video_GetFormat(mHandle);
		if (format == VIDEO_FORMAT_H264) {
			setVideoH264ForceI();
		}

		/* open record timer */
		mRecordTimeHandler.postDelayed(RecordTimeRunnable, 1000);
		if (mClientCallBack != null) {
			mClientCallBack.recordTimeCountCallBack("00:00");
		}

		return 0;
	}

	public int stopRecord() {
		if (!isRecord || !isConnect)
			return 0;

		isRecord = false;
		mRecordStatue = RECORD_STATUE_STOP;

		/* close record timer */
		mRecordTimeHandler.removeCallbacks(RecordTimeRunnable);
		SendMessage(SIGNAL_RECORD_STOP, 0, 0);

		return 0;
	}

	public String getConnectPasswd() {
		if (!isConnect)
			return null;

		return mClient.CHD_WMP_GetEncrypt(mHandle);
	}

	public static final int NET_TRANSMODE_TCP = 1;
	public static final int NET_TRANSMODE_P2P = 2;
	public static final int NET_TRANSMODE_RLY = 3;

	public int getTransMode() {
		if (!isConnect) {
			return -1;
		}
		return mClient.CHD_WMP_GetTransMode(mHandle);
	}

	public int setConnectPasswd(String passwd) {
		if (!isConnect)
			return -1;
		return mClient.CHD_WMP_SetEncrypt(mHandle, passwd);
	}

	public String getDeviceVersion() {
		if (!isConnect) {
			return null;
		}
		int version = mClient.CHD_WMP_GetVersion(mHandle);

		return Long.toString(version >> 16) + "."
				+ Long.toString((version & 0x0000ff00) >> 8)
				+ Long.toString(version & 0x000000ff);
	}

	public int getDeviceId() {
		if (!isConnect)
			return -1;
		return mClient.CHD_WMP_Device_GetId(mHandle);
	}

	public String getDeviceAlias() {
		if (!isConnect)
			return null;

		return mClient.CHD_WMP_Device_GetAlias(mHandle);
	}

	public int setDeviceId(int id) {
		if (!isConnect)
			return -1;
		return mClient.CHD_WMP_Device_SetId(mHandle, id);
	}

	public int setDeviceAlias(String alias) {
		if (!isConnect)
			return -1;
		return mClient.CHD_WMP_Device_SetAlias(mHandle, alias);
	}

	public int rebootDevice() {
		if (!isConnect)
			return -1;
		int ret = mClient.CHD_WMP_Device_Reboot(mHandle);
		if (ret < 0)
			return -1;

		disconnectDevice();

		return 0;
	}

	public int resetDevice() {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Device_Reset(mHandle);
	}

	public String getMacAddress() {
		if (!isConnect)
			return null;

		return mClient.CHD_WMP_GetMac(mHandle);
	}

	public String getCompany() {
		if (!isConnect)
			return null;

		return mClient.CHD_WMP_GetCompany(mHandle);
	}

	public static final int DEVICE_WIRELESS_MODE_AP = 0;
	public static final int DEVICE_WIRELESS_MODE_STA = 1;

	public int getDeviceWirelessMode() {
		if (!isConnect)
			return -1;
		return mClient.CHD_WMP_Wireless_GetNetType(mHandle);
	}

	public String getApName() {
		if (!isConnect)
			return null;
		st_WirelessInfo param = new st_WirelessInfo();

		int ret = mClient.CHD_WMP_Wireless_GetApInfo(mHandle, param);
		if (ret < 0)
			return null;

		return param.apssid;
	}

	public String getApPasswd() {
		if (!isConnect)
			return null;
		st_WirelessInfo param = new st_WirelessInfo();

		int ret = mClient.CHD_WMP_Wireless_GetApInfo(mHandle, param);
		if (ret < 0)
			return null;

		return param.apkey;
	}

	public int setApInfo(String name, String passwd) {
		if (!isConnect)
			return -1;
		st_WirelessInfo param = new st_WirelessInfo();

		param.apssid = name;
		param.apkey = passwd;

		int ret = mClient.CHD_WMP_Wireless_SetApInfo(mHandle, param);
		if (ret < 0)
			return ret;

		return 0;
	}

	public String getStaName() {
		if (!isConnect)
			return null;
		st_WirelessInfo param = new st_WirelessInfo();

		int ret = mClient.CHD_WMP_Wireless_GetStaInfo(mHandle, param);
		if (ret < 0)
			return null;

		return param.stassid;
	}

	public String getStaPasswd() {
		if (!isConnect)
			return null;
		st_WirelessInfo param = new st_WirelessInfo();

		int ret = mClient.CHD_WMP_Wireless_GetStaInfo(mHandle, param);
		if (ret < 0)
			return null;

		return param.stakey;
	}

	public int setStaInfo(String name, String passwd) {
		if (!isConnect)
			return -1;
		st_WirelessInfo param = new st_WirelessInfo();

		param.stassid = name;
		param.stakey = passwd;

		int ret = mClient.CHD_WMP_Wireless_SetStaInfo(mHandle, param);
		if (ret < 0)
			return ret;

		return 0;
	}

	public int Video_getAbiFormatNum() {
		if (!isConnect)
			return -1;
		st_VideoAbilityInfo abi = new st_VideoAbilityInfo();
		int ret = mClient.CHD_WMP_Video_GetAbility(mHandle, abi);
		if (ret < 0)
			return ret;

		return abi.FormatNum;
	}

	public String Video_getAbiFormat(int Cnt) {
		if (!isConnect)
			return null;
		st_VideoAbilityInfo abi = new st_VideoAbilityInfo();
		int ret = mClient.CHD_WMP_Video_GetAbility(mHandle, abi);
		if (ret < 0 || abi.FormatNum < Cnt)
			return null;

		return abi.GetFormatString(abi.format[Cnt]);
	}

	public int Video_getAbiResoluNum() {
		if (!isConnect)
			return -1;
		st_VideoAbilityInfo abi = new st_VideoAbilityInfo();
		int ret = mClient.CHD_WMP_Video_GetAbility(mHandle, abi);
		if (ret < 0)
			return ret;

		return abi.ResoluNum;
	}

	public String Video_getAbiResolu(int Cnt) {
		if (!isConnect)
			return null;
		st_VideoAbilityInfo abi = new st_VideoAbilityInfo();
		int ret = mClient.CHD_WMP_Video_GetAbility(mHandle, abi);
		if (ret < 0 || abi.ResoluNum < Cnt)
			return null;

		return abi.GetResoluString(abi.width[Cnt], abi.height[Cnt]);
	}

	/* 视频参数（格式、分辨率、帧率） */
	public static int VIDEO_FORMAT_YUYV = 0X01;
	public static int VIDEO_FORMAT_MJPEG = 0X02;
	public static int VIDEO_FORMAT_H264 = 0X03;
	public static int VIDEO_FORMAT_YUV420SP = 0X04;

	public int Video_getFormat() {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Video_GetFormat(mHandle);
	}

	public int Video_getResoluWidth() {
		if (!isConnect)
			return -1;

		st_VideoParamInfo param = new st_VideoParamInfo();
		if (mClient.CHD_WMP_Video_GetResolu(mHandle, param) < 0)
			return -1;

		return param.width;
	}

	public int Video_getResoluHeight() {
		if (!isConnect)
			return -1;

		st_VideoParamInfo param = new st_VideoParamInfo();
		if (mClient.CHD_WMP_Video_GetResolu(mHandle, param) < 0)
			return -1;

		return param.height;
	}

	public int Video_getFps() {
		return mClient.CHD_WMP_Video_GetFPS(mHandle);
	}

	public int Video_getMaxFps() {
		int maxfps = -1;
		st_VideoParamInfo param = new st_VideoParamInfo();
		int ret = mClient.CHD_WMP_Video_GetParam(mHandle, param);
		if (ret >= 0) {
			maxfps = param.maxfps;
		}
		return maxfps;
	}

	public int Video_setFormat(int format) {
		return mClient.CHD_WMP_Video_SetFormat(mHandle, format);
	}

	public int Video_setResolu(int width, int height) {
		return mClient.CHD_WMP_Video_SetResolu(mHandle, width, height);
	}

	public int Video_setFps(int fps) {
		return mClient.CHD_WMP_Video_SetFPS(mHandle, fps);
	}

	/* camera control param type */
	public static int VIDEO_CTRL_TYPE_BRIGHTNESS = 0; // 亮度
	public static int VIDEO_CTRL_TYPE_CONTRAST = 1; // 对比度
	public static int VIDEO_CTRL_TYPE_SATURATION = 2; // 饱和度
	public static int VIDEO_CTRL_TYPE_HUE = 3; // 色调
	public static int VIDEO_CTRL_TYPE_WHITE_BALANCE = 4; // 白平衡
	public static int VIDEO_CTRL_TYPE_GAMMA = 5; // 伽马
	public static int VIDEO_CTRL_TYPE_GAIN = 6; // 增益
	public static int VIDEO_CTRL_TYPE_SHARPNESS = 7; // 清晰度
	public static int VIDEO_CTRL_TYPE_BACKLIGH = 8; // 背光补偿
	public static int VIDEO_CTRL_TYPE_EXPOSURE = 9; // 曝光值

	public boolean VCtrl_isSupportAutoCtrl(int CtrlType) {
		if (!isConnect) {
			return false;
		}

		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);
		if (ret < 0) {
			return false;
		}

		if (vctrl.auto_valid == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean VCtrl_isAutoCtrl(int CtrlType) {
		if (!isConnect) {
			return false;
		}
		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);
		if (ret < 0) {
			return false;
		}
		if (vctrl.auto_valid == 0) {
			return false;
		}
		if (vctrl.autoval == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean VCtrl_isSupportValueCtrl(int CtrlType) {
		if (!isConnect) {
			return false;
		}
		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);
		if (ret < 0) {
			return false;
		}
		if (vctrl.val_valid == 1) {
			return true;
		} else {
			return false;
		}
	}

	public int VCtrl_getMaxValue(int CtrlType) {
		if (!isConnect) {
			return -1;
		}
		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);
		if (ret < 0)
			return -1;

		return vctrl.maxval;
	}

	public int VCtrl_getMinValue(int CtrlType) {
		if (!isConnect) {
			return -1;
		}
		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);
		if (ret < 0)
			return -1;

		return vctrl.minval;
	}

	public int VCtrl_getCurValue(int CtrlType) {
		if (!isConnect) {
			return -1;
		}
		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);
		if (ret < 0)
			return -1;

		return vctrl.curval;
	}

	public int VCtrl_setAutoCtrl(int CtrlType, int Auto) {
		if (!isConnect) {
			return -1;
		}

		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);

		if (ret < 0 || vctrl.auto_valid == 0)
			return -1;

		vctrl.autoval = Auto;
		return mClient.CHD_WMP_Video_SetVideoCtrl(mHandle, CtrlType, vctrl);
	}

	public int VCtrl_setCtrlValue(int CtrlType, int Value) {
		if (!isConnect) {
			return -1;
		}

		st_VideoCtrlInfo vctrl = new st_VideoCtrlInfo();
		int ret = mClient.CHD_WMP_Video_GetVideoCtrl(mHandle, CtrlType, vctrl);

		if (ret < 0 || vctrl.val_valid == 0)
			return -1;

		if (Value > vctrl.maxval) {
			Value = vctrl.maxval;
		} else if (Value < vctrl.minval) {
			Value = vctrl.minval;
		}

		vctrl.curval = Value;
		return mClient.CHD_WMP_Video_SetVideoCtrl(mHandle, CtrlType, vctrl);
	}

	public int VCtrl_Reset() {
		if (!isConnect) {
			return -1;
		}
		return mClient.CHD_WMP_Video_ResetVCtrl(mHandle);
	}

	/* H264传输相关函数 I帧间隔(10~240) */
	public int getVideoH264StreamGop() {
		if (!isConnect) {
			return -1;
		}

		return mClient.CHD_WMP_Video_GetH264KeyInter(mHandle);
	}

	public int getVideoH264StreamQpValue() {
		if (!isConnect) {
			return -1;
		}

		return mClient.CHD_WMP_Video_GetH264QpValue(mHandle);
	}

	public int setVideoH264StreamGop(int value) {
		if (!isConnect) {
			return -1;
		}

		return mClient.CHD_WMP_Video_SetH264KeyInter(mHandle, value);
	}

	/* QP值(1~100) */
	public int setVideoH264StreamQpValue(int value) {
		if (!isConnect) {
			return -1;
		}

		return mClient.CHD_WMP_Video_SetH264QpValue(mHandle, value);
	}

	public int setVideoH264ForceI() {
		if (!isConnect) {
			return -1;
		}

		return mClient.CHD_WMP_Video_SetForceI(mHandle);
	}

	public String getVideoFrameFps() {

		return String.valueOf(videoFps);
	}

	public String getVideoFrameBps() {
		return SizeLongToString(videoBps) + "/s";
	}

	public boolean isOpenSerial() {
		if (!isConnect)
			return false;

		return isOpenSerial;
	}

	public int openSerial() {
		if (!isConnect) {
			return -1;
		}

		Log.v("test", "=====================");
		int ret = mClient.CHD_WMP_Serial_Begin(mHandle);
		Log.v("test", "open serial:" + ret);
		if (ret < 0) {
			return ret;
		}

		isOpenSerial = true;

		return 0;
	}

	public int closeSerial() {
		if (!isConnect)
			return -1;

		int ret = mClient.CHD_WMP_Serial_End(mHandle);
		if (ret < 0)
			return ret;

		isOpenSerial = false;

		return 0;
	}

	/* 串口相关 */
	public static int SERIAL_SPEED_BS300 = 300;
	public static int SERIAL_SPEED_BS1200 = 1200;
	public static int SERIAL_SPEED_BS2400 = 2400;
	public static int SERIAL_SPEED_BS4800 = 4800;
	public static int SERIAL_SPEED_BS9600 = 9600;
	public static int SERIAL_SPEED_BS19200 = 19200;
	public static int SERIAL_SPEED_BS38400 = 38400;
	public static int SERIAL_SPEED_BS57600 = 57600;
	public static int SERIAL_SPEED_BS115200 = 115200;
	public static int SERIAL_SPEED_BS230400 = 230400;

	public int getSerialSpeed() {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_GetSpeed(mHandle);
	}

	public int setSerialSpeed(int value) {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_SetSpeed(mHandle, value);
	}

	public static int SERIAL_DATABIT_7 = 7;
	public static int SERIAL_DATABIT_8 = 8;

	public int getSerialDataBit() {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_GetDataBit(mHandle);
	}

	public int setSerialDataBit(int value) {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_SetDataBit(mHandle, value);
	}

	public static int SERIAL_STOPBIT_1 = 1;
	public static int SERIAL_STOPBIT_0 = 0;

	public int getSerialStopBit() {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_GetStopBit(mHandle);
	}

	public int setSerialStopBit(int value) {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_SetStopBit(mHandle, value);
	}

	public static int SERIAL_PARITY_EVEN = 69;
	public static int SERIAL_PARITY_NONE = 78;
	public static int SERIAL_PARITY_ODD = 79;
	public static int SERIAL_PARITY_SPACE = 83;

	public int getSerialParityBit() {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_GetParity(mHandle);
	}

	public int setSerialParity(int value) {
		if (!isConnect)
			return -1;

		return mClient.CHD_WMP_Serial_SetParity(mHandle, value);
	}

	public int sendSerialData(byte[] data, int datalen) {
		if (!isConnect || !isOpenSerial)
			return -1;

		return mClient.CHD_WMP_Serial_SendData(mHandle, data, datalen);
	}

	public boolean isOpenAudio() {
		if (!isConnect)
			return false;

		return isOpenAudio;
	}

	public int openAudioStream() {
		if (!isConnect)
			return -1;

		if (mClient.CHD_WMP_Audio_Begin(mHandle) < 0) {
			return -1;
		}
		isOpenAudio = true;

		return 0;
	}

	public int closeAudioStream() {
		if (!isConnect)
			return -1;

		if (mClient.CHD_WMP_Audio_End(mHandle) < 0) {
			return -1;
		}
		isOpenAudio = false;

		return 0;
	}

	public int getGpioNum() {
		if (!isConnect)
			return -1;

		if (!bGpioget) {
			bGpioget = true;
			mClient.CHD_WMP_Gpio_GetAll(mHandle, mGpioInfo);
		}

		return mGpioInfo.number;
	}

	public static int GPIO_DIR_IN = 1;
	public static int GPIO_DIR_OUT = 0;

	public int getGpioDir(int gpio) {
		if (!isConnect)
			return -1;

		if (!bGpioget) {
			bGpioget = true;
			mClient.CHD_WMP_Gpio_GetAll(mHandle, mGpioInfo);
		}

		if (gpio > mGpioInfo.number)
			return -1;

		if (mClient.CHD_WMP_Gpio_GetStatus(mHandle, gpio, mGpioInfo) < 0) {
			return -1;
		}

		return mGpioInfo.dir[gpio];
	}

	public int setGpioDir(int gpio, int dir) {
		if (!isConnect)
			return -1;

		if (!bGpioget) {
			bGpioget = true;
			mClient.CHD_WMP_Gpio_GetAll(mHandle, mGpioInfo);
		}
		if (gpio > mGpioInfo.number)
			return -1;

		return mClient.CHD_WMP_Gpio_SetStatus(mHandle, gpio, dir,
				mGpioInfo.state[gpio]);
	}

	public static int GPIO_VALUE_HIGH = 1;
	public static int GPIO_VALUE_LOW = 0;

	public int getGpioState(int gpio) {
		if (!isConnect)
			return -1;

		if (!bGpioget) {
			bGpioget = true;
			mClient.CHD_WMP_Gpio_GetAll(mHandle, mGpioInfo);
		}
		if (gpio > mGpioInfo.number)
			return -1;

		int ret = mClient.CHD_WMP_Gpio_GetStatus(mHandle, gpio, mGpioInfo);
		if (ret < 0) {
			return ret;
		}

		return mGpioInfo.state[gpio];
	}

	public int setGpioValue(int gpio, int state) {
		if (!isConnect) {
			return -1;
		}

		if (!bGpioget) {
			bGpioget = true;
			mClient.CHD_WMP_Gpio_GetAll(mHandle, mGpioInfo);
		}
		if (gpio > mGpioInfo.number)
			return -1;

		return mClient.CHD_WMP_Gpio_SetStatus(mHandle, gpio,
				mGpioInfo.dir[gpio], state);
	}

	class DataPollThread extends Thread {
		private int dataType;
		private long sessionId = 0;

		/* video */
		st_VideoFrame vframe = new st_VideoFrame();
		/* record */
		private long recordHandle = 0;
		private int recordStatue = RECORD_STATUE_NONE;
		/* picture data save */
		private boolean bNewPicFlag = false;
		private byte[] pictureBuffer;
		/* audio */
		private int audioRate = 0;
		private long audioDecHandle = 0;
		private boolean isAudioInit = false;
		private boolean bNewAudioFlag = false;
		private byte[] audioBuffer, aDestBuffer;
		private st_AudioFrame stAudioInfo = new st_AudioFrame();
		/* serial data process */
		private Boolean bNewSerialFlag = false;
		private byte[] serialBuffer;

		private long mSequence = 0;

		public void run() {
			sessionId = mSessionID;
			while (isConnect) {

				dataType = mClient.CHD_WMP_Poll(mHandle, 500);
				if (sessionId != mSessionID) {
					break;
				}

				/* Devcice Disconnect */
				if (dataType == RET_ERROR_RET_DEVICE_OFFLINE
						|| dataType == RET_ERROR_RET_INVALID_HANDLE) {
					break;
				}
				/* Receive Timeout */
				if (dataType == RET_ERROR_RET_TIMEOUT) {
					continue;
				}

				if (dataType < RET_ERROR_SUCCESS) {
					continue;
				}

				/* Video Data */
				if ((dataType & mClient.CHD_DATA_YTPE_VIDEO) == mClient.CHD_DATA_YTPE_VIDEO) {

					int ret = mClient.CHD_WMP_Video_RequestVideoDataAddress(
							mHandle, vframe);
					if (ret >= 0) {
						videoFps = vframe.fps;
						videoBps = vframe.BPS;

						/* recording */
						if (vframe.bexist == 0
								|| vframe.format == vframe.CHD_FMT_YUYV) {
							isSupportRecord = false;
						} else {
							isSupportRecord = true;
						}
						if (mRecordStatue == RECORD_STATUE_START
								&& recordStatue == RECORD_STATUE_NONE) {
							recordStatue = mRecordStatue;
						} else if (mRecordStatue == RECORD_STATUE_STOP
								&& recordStatue != RECORD_STATUE_NONE
								&& recordStatue != RECORD_STATUE_START) {
							recordStatue = mRecordStatue;
						}
						/* start record */
						if (isSupportRecord && isRecord
								&& mRecordFileName != null
								&& recordStatue == RECORD_STATUE_START) {
							int srcvideofmt = mCoder.CODE_FMT_MJPEG;
							if (vframe.format == vframe.CHD_FMT_H264) {
								srcvideofmt = mCoder.CODE_FMT_H264;
							}
							recordHandle = mCoder.chird_mixer_create(
									mRecordFileName, vframe.width,
									vframe.height, vframe.fps, srcvideofmt, 0);
							recordStatue = RECORD_STATUE_WRITEDATA;
						}
						/* write video data */
						if (isRecord && recordStatue == RECORD_STATUE_WRITEDATA) {
							mCoder.chird_mixer_processbyaddress(recordHandle,
									mCoder.MIXER_TYPE_VIDEO,
									vframe.pDataAddress, vframe.datalen, 0);
						}
						/* stop record */
						if (recordStatue == RECORD_STATUE_STOP) {
							mCoder.chird_mixer_destory(recordHandle);
							recordStatue = RECORD_STATUE_NONE;
						}

						if (vframe.iflag == 0) {
							if ((mSequence + 1) != vframe.sequence) {
								Log.v("lostVideo", "sequence 不一致, mSequence+1:"
										+ (mSequence + 1) + " vsequence:"
										+ vframe.sequence + " sdk video queue num:"+vframe.queueNum);
							} else {
								mSequence = vframe.sequence;
							}
						} else {
							mSequence = vframe.sequence;
						}

						/*
						 * Put Data To Queue And Send Signal Notification thread
						 * decoding
						 */
						videoQueue.putQueue(vframe);
						decoderSemp.release();

					}

				}/* end video data */

				/* Picture Data */
				if ((dataType & mClient.CHD_DATA_YTPE_PICTURE) == mClient.CHD_DATA_YTPE_PICTURE) {
					if (!bNewPicFlag) {
						bNewPicFlag = true;
						pictureBuffer = new byte[1024 * 1024 * 3];
					}

					st_VideoFrame pframe = new st_VideoFrame();
					int ret = mClient.CHD_WMP_Video_RequestPicData(mHandle,
							pframe, pictureBuffer);
					if (ret >= 0 && pframe.format == VIDEO_FORMAT_MJPEG
							&& mSnapPath != null) {
						String filename = mSnapPath + "/" + getTimesString()
								+ ".jpg";
						mClient.CHD_WMP_File_Save(filename, pframe.datalen,
								pictureBuffer);
						SendMessage(SIGNAL_SNAP, 0, 0);
					}
				}/* end picture data */

				/* Audio Data */
				if ((dataType & mClient.CHD_DATA_YTPE_AUDIO) == mClient.CHD_DATA_YTPE_AUDIO) {
					if (!bNewAudioFlag) {
						audioBuffer = new byte[1024 * 20];
						aDestBuffer = new byte[1024 * 30];
					}

					st_AudioFrame stAudioFrame = new st_AudioFrame();
					int ret = mClient.CHD_WMP_Audio_RequestData(mHandle,
							stAudioFrame, audioBuffer);
					if (ret >= 0) {
						/* audio init */
						if (!isAudioInit) {
							if (stAudioFrame.rate < 65536
									&& stAudioFrame.rate > 0
									&& audioRate != stAudioFrame.rate) {
								audioRate = stAudioFrame.rate;
								audioInit(stAudioFrame);

								if (stAudioFrame.EncodeType == stAudioFrame.G726) {
									audioDecHandle = mCoder.chird_adec_create(
											mCoder.CODE_FMT_G726,
											stAudioFrame.rate);
								} else {
									audioDecHandle = mCoder.chird_adec_create(
											mCoder.CODE_FMT_G711U,
											stAudioFrame.rate);
								}
								isAudioInit = true;
							}
						} else {
							/* audio decoder to pcm */
							int rn = 0;
							if (stAudioFrame.EncodeType == stAudioFrame.G726) {
								rn = mCoder.chird_adec_process(audioDecHandle,
										audioBuffer, stAudioFrame.datalen,
										aDestBuffer);
								rn = rn * 2;
							} else {
								rn = mCoder.chird_adec_process(audioDecHandle,
										audioBuffer, stAudioFrame.datalen,
										aDestBuffer);
							}

							/* get audio Frame pcm Data CallBack */
							if (mClientCallBack != null && rn > 0) {
								mClientCallBack.audioDataCallBack(
										stAudioFrame.datalen, aDestBuffer);
							}

							/* write pcm data to audiotrack playing */
							if (rn > 0 && mAudioTrack != null) {
								mAudioTrack.write(aDestBuffer, 0, rn);
							}
						}/* end if (!isAudioInit) */
					} /* ebd if (ret >= 0) */
				}/* end audio data */

				/* Serial Data */
				if ((dataType & mClient.CHD_DATA_YTPE_SERIAL) == mClient.CHD_DATA_YTPE_SERIAL) {
					if (!bNewSerialFlag) {
						bNewSerialFlag = true;
						serialBuffer = new byte[1024 * 300];
					}

					int len = mClient.CHD_WMP_Serial_RequestData(mHandle,
							serialBuffer);

					if (mClientCallBack != null && len > 0) {

						String aa = null;
						try {
							aa = new String(serialBuffer, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.v("test", "Serial Len:" + len);

						mClientCallBack.serialDataCallBack(len, serialBuffer);
					}
				}

				/* Device Param Changes */
				if ((dataType & mClient.CHD_PARAM_CHANGE) == mClient.CHD_PARAM_CHANGE) {
					int changeType = mClient
							.CHD_WMP_GetParamChangeType(mHandle);

					if (changeType == mClient.CHD_PARAMCHANGETYPE_VIDEO_ABILITY
							|| changeType == mClient.CHD_PARAMCHANGETYPE_VIDEO_PARAM
							|| changeType == mClient.CHD_PARAMCHANGETYPE_VIDEO_CTRL) {
						cleanVideoQueue();
						if (vframe.format == vframe.CHD_FMT_H264) {
							setVideoH264ForceI();
						}
					}
					SendMessage(SIGNAL_PARAM_CHANGE, changeType, 0);
				}

			}/* end while(isConnect) */

			/* audio uninit */
			if (isAudioInit) {
				mCoder.chird_adec_destory(audioDecHandle);
				if (mAudioTrack != null
						&& mAudioTrack.getPlayState() == mAudioTrack.PLAYSTATE_PLAYING) {
					mAudioTrack.stop();
					mAudioTrack.release();
					mAudioTrack = null;
				}
			}

			/* if the record is not closed, turn off the record */
			if (recordStatue == RECORD_STATUE_WRITEDATA
					|| recordStatue == RECORD_STATUE_STOP) {
				recordStatue = RECORD_STATUE_NONE;
				SendMessage(SIGNAL_RECORD_STOP, 0, 0);
				mCoder.chird_mixer_destory(recordHandle);
				mRecordTimeHandler.removeCallbacks(RecordTimeRunnable);
			}

			/* unexpected exit, reclaim space */
			if (sessionId == mSessionID && isConnect) {
				mClient.CHD_WMP_Disconnect(mHandle);
			}

			cleanVideoQueue();
			decoderSemp.release();
			SendMessage(SIGNAL_DISCONNECT, 0, 0);

			isConnect = false;
			isRecord = false;
			isOpenAudio = false;
			isOpenVideoStream = false;
		}/* end void run() */

		private AudioTrack mAudioTrack = null;

		private int audioInit(st_AudioFrame stAudioFrame) {
			if (mAudioTrack != null
					&& mAudioTrack.getPlayState() == mAudioTrack.PLAYSTATE_PLAYING) {
				mAudioTrack.stop();
			}
			int frequency = stAudioFrame.rate;
			int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
			int channel = AudioFormat.CHANNEL_OUT_MONO;
			int plyBufSize = AudioTrack.getMinBufferSize(frequency, channel,
					audioEncoding);
			mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,
					channel, audioEncoding, plyBufSize * 2,
					AudioTrack.MODE_STREAM);
			mAudioTrack.play();
			return 0;
		}/* end audioInit() */
	} /* end DataPollThread */

	private void cleanVideoQueue() {
		if (!isConnect) {
			return;
		}
		st_VideoFrame videoframe = null;
		while (videoQueue.getLength() > 0) {
			videoframe = videoQueue.getQueue();
			mClient.CHD_WMP_Video_ReleaseVideoDataAddress(mHandle, videoframe);
		}
	}

	private static final int VIDEO_DECODER_CACHE_NUMBER = 2;

	class videoDecoderThread extends Thread {
		private int ret;
		private long sessionId = 0;
		private byte[] videoDataBuf;
		private Bitmap bitmap = null;
		private long old_times = 0;
		private boolean geIflag = false;
		private long mSequence = 0;
		private int width = 0, height = 0;
		private Boolean bNewBufFlag = false;

		private int lostFrameCnt = 0;

		public void run() {
			sessionId = mSessionID;
			while (isConnect) {

				if (videoQueue.getLength() <= 5) {
					try {
						decoderSemp.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
				}

				if (sessionId != mSessionID) {
					break;
				}

				st_VideoFrame videoframe = videoQueue.getQueue();
				if (videoframe == null) {
					continue;
				}

				/*
				 * WIFI received video original data callback function,
				 * temporary shielding
				 */
				// if (!bNewBufFlag || videoDataBufLen < videoframe.datalen) {
				// bNewBufFlag = true;
				// videoDataBufLen = videoframe.datalen;
				// videoDataBuf = new byte[videoDataBufLen];
				// }
				// mClient.CHD_WMP_Video_CopyVideoDataToByteArray(mHandle,
				// videoframe, videoDataBuf);
				// /* get Video Frame Data CallBack */
				// if (mClientCallBack != null) {
				// mClientCallBack.videoStreamDataCallBack(videoframe.format,
				// videoframe.width, videoframe.height,
				// videoframe.datalen, videoDataBuf);
				// }

				/* Resolu Change, recreate bitmap to display */
				if (width != videoframe.width || height != videoframe.height) {
					cleanVideoQueue();
					if (reCreateBitmap(videoframe.width, videoframe.height) >= 0) {
						width = videoframe.width;
						height = videoframe.height;
					}
					/* H264 video stream, must be forced to get I frame */
					if (videoframe.format == videoframe.CHD_FMT_H264) {

						if (videoframe.iflag != 0) {
							geIflag = false;
						} else {
							geIflag = true;
							Log.v("test", "resolu is change, get iflag");
						}
					} else {
						continue;
					}

				}

				boolean isShowFlag = false;
				/*
				 * Yuyv Mjpeg data, video Queue cache is greater than the
				 * maximum set value is not decoded
				 */
				if (videoframe.format != VIDEO_FORMAT_H264) {
					// long curtime = System.currentTimeMillis();
					// if ((curtime - old_times) > 40 || mSnapCount > 0) {
					if (videoQueue.getLength() < VIDEO_DECODER_CACHE_NUMBER
							|| mSnapCount > 0) {
						if (videoframe.format == videoframe.CHD_FMT_YUYV) {
							ret = mCoder.chird_sws_processbyaddress(
									mCoder.CODE_PIXEL_FMT_YUYV422,
									videoframe.pDataAddress,
									mCoder.CODE_PIXEL_FMT_RGB565, bitmap,
									videoframe.width, videoframe.height);
						} else if (videoframe.format == videoframe.CHD_FMT_MJPEG) {
							ret = mCoder.chird_vdec_processbyaddress(
									jpegHandle, videoframe.width,
									videoframe.height, videoframe.datalen,
									videoframe.pDataAddress, bitmap);
						}
						if (ret >= 0) {
							isShowFlag = true;
							// old_times = curtime;
						}
					}

				} else {
					/*
					 * H264 data decoding, but only 1 seconds to display 25
					 * frames
					 */
					long timems = System.currentTimeMillis();

					// if (videoframe.iflag == 1){
					//
					// }
					//

					if ((videoframe.sequence - mSequence) != 1) {
						lostFrameCnt++;
						Log.v("test", "vsequence error :" + videoframe.sequence
								+ " mSequence:" + mSequence + " iflag:"
								+ videoframe.iflag);
						Log.v("test", "lostFrameCnt:" + lostFrameCnt
								+ " queueCnt:" + videoQueue.getLength());
						if (lostFrameCnt >= 15) {
							setVideoH264ForceI();
							lostFrameCnt = 0;
							Log.v("test", " force i frame");
						}
						geIflag = true;
					}

					// Log.v("test", "sequence:"+videoframe.sequence);

					if (geIflag && videoframe.iflag == 1) {
						geIflag = false;
					}

					if (!geIflag) {
						lostFrameCnt = 0;
						mSequence = videoframe.sequence;
						ret = mCoder.chird_vdec_processbyaddress(h264Handle,
								videoframe.width, videoframe.height,
								videoframe.datalen, videoframe.pDataAddress,
								bitmap);
						if (ret < 0) {
							geIflag = true;
							setVideoH264ForceI();
							Log.v("test", " decoder fail, force i frame");
						}
					}

					long curtime = System.currentTimeMillis();
					if (ret >= 0 && (curtime - old_times) > 40) {
						isShowFlag = true;
						old_times = curtime;
					}
				}

				/* save picture and send return thumbnail signal */
				if (mSnapCount > 0 && ret >= 0) {
					mSnapCount--;
					SendMessage(SIGNAL_SNAP, 0, 0);
					if (mSnapFileName != null) {
						SaveBitmap(mSnapFileName, mBitmap);
					}
				}

				/* copy bitmap to global bitmap display */
				if (isShowFlag) {
					synchronized (this) {
						if (mCoder.chird_vdec_bitmapcopy(bitmap, mBitmap) >= 0) {
							displaySemp.release();
						}
					}
				}

				mClient.CHD_WMP_Video_ReleaseVideoDataAddress(mHandle,
						videoframe);
			}/* end while (isConnect) */

			/* Recovery Bitmap Space */
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
				System.gc();
			}

			displaySemp.release();
		}/* end run() */

		private int reCreateBitmap(int width, int height) {
			if (bitmap == null || mBitmap == null || bitmap.getWidth() != width
					|| bitmap.getHeight() != height) {
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
					System.gc();
				}
				try {
					bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
				} catch (OutOfMemoryError e) {
					return -1;
				}
				synchronized (this) {
					if (mBitmap != null && !mBitmap.isRecycled()) {
						mBitmap.recycle();
						mBitmap = null;
						System.gc();
					}
					try {
						mBitmap = Bitmap.createBitmap(width, height,
								Config.RGB_565);
					} catch (OutOfMemoryError e) {
						return -1;
					}
				}
			}
			return 0;
		}/* end reCreateBitmap() */
	}/* end videoDecoderThread */

	class displayThread extends Thread {
		private long sessionId = 0;

		public void run() {
			sessionId = mSessionID;
			while (isConnect) {
				/* Wait Decoding Successful Signal */
				try {
					displaySemp.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
					continue;
				}
				if (sessionId != mSessionID) {
					break;
				}
				synchronized (this) {
					/* Display Bitmap CallBack */
					if (mClientCallBack != null && mBitmap != null) {
						mClientCallBack.videoStreamBitmapCallBack(mBitmap);
					}
				}

			}/* end while (isConnect) */
		}/* end void run() */
	}/* end displayThread */

	protected void finalize() {
		if (isConnect) {
			isConnect = false;
			mClient.CHD_WMP_Disconnect(mHandle);
		}
		mCoder.chird_vdec_destory(jpegHandle);
		mCoder.chird_vdec_destory(h264Handle);
	}

	public int SaveBitmap(String filename, Bitmap mBitmap) {
		File file = new File(filename);
		try {
			file.createNewFile();
		} catch (IOException e) {
			return -1;
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		}

		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	private String getRecordTimerString(int timer) {
		String Stimer;
		if ((timer / 60) >= 10) {
			Stimer = String.valueOf(timer / 60) + ":";
		} else {
			Stimer = "0" + String.valueOf(timer / 60) + ":";
		}
		if ((timer % 60) >= 10) {
			Stimer += String.valueOf(timer % 60);
		} else {
			Stimer += "0" + String.valueOf(timer % 60);
		}

		return Stimer;
	}

	public String SizeLongToString(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS - 1024.0 < 0) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS - 1048576.0 < 0) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS - 1073741824.0 < 0) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	private String getTimesString() {
		SimpleDateFormat dateFormat24 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SS");
		String time = dateFormat24.format(Calendar.getInstance().getTime());
		time = time.replace(" ", "_");
		return time.replace(":", "-");
	}

}
