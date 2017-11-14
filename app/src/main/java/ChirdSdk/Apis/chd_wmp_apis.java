package ChirdSdk.Apis;

public class chd_wmp_apis {
	public static final int CHD_POLL_TYPE_VIDEO = 0X00;
	public static final int CHD_POLL_TYPE_PICTURE = 0X01;
	public static final int CHD_POLL_TYPE_AUDIO = 0X02;
	public static final int CHD_POLL_TYPE_SERIAL = 0X03;
	public static final int CHD_POOL_TYPE_CHANGE_VABILITY = 0X04;
	public static final int CHD_POOL_TYPE_CHANGE_VPARAM = 0X05;
	public static final int CHD_POOL_TYPE_CHANGE_VCTRL = 0X06;
	public static final int CHD_POOL_TYPE_CHANGE_AUDEO = 0X07;
	public static final int CHD_POOL_TYPE_CHANGE_SERIAL = 0X08;
	public static final int CHD_POOL_TYPE_CHANGE_GPIO = 0X09;

	public native int CHD_WMP_ScanDevice_Init(int ScanTime);

	public native int CHD_WMP_ScanDevice_UnInit();

	public native int CHD_WMP_Scan_GetDeviceInfo(st_SearchInfo DevInfo);

	public native int CHD_WMP_SmartConfig_Begin(String RouterName,
			String RouterPasswd, String DevId);

	public native int CHD_WMP_SmartConfig_End();

	public native int CHD_WMP_ScanDevice_InitIndex(int ScanTime, int Index);

	public native int CHD_WMP_Scan_GetDeviceInformation(st_SearchInfo DevInfo);

	public native long CHD_WMP_ConnectDeviceUser(String address, int index,
			String param, String passwd);

	public native long CHD_WMP_ConnectDeviceIndex(String address, int index,
			String passwd);

	public native long CHD_WMP_ConnectDevice(String address, String passwd);

	public native int CHD_WMP_Disconnect(long handle);

	public native int CHD_WMP_GetVersion(long handle);

	public static final int CHD_DATA_YTPE_VIDEO = 0x01;
	public static final int CHD_DATA_YTPE_PICTURE = 0x04;
	public static final int CHD_DATA_YTPE_AUDIO = 0x08;
	public static final int CHD_DATA_YTPE_SERIAL = 0x0F;
	public static final int CHD_PARAM_CHANGE = 0x10;

	public native int CHD_WMP_Poll(long handle, int msec);

	public static final int CHD_PARAMCHANGETYPE_VIDEO_ABILITY = 0x00;
	public static final int CHD_PARAMCHANGETYPE_VIDEO_PARAM = 0x01;
	public static final int CHD_PARAMCHANGETYPE_VIDEO_CTRL = 0x02;
	public static final int CHD_PARAMCHANGETYPE_AUDIO_PARAM = 0x03;
	public static final int CHD_PARAMCHANGETYPE_SERIAL_PARAM = 0X04;
	public static final int CHD_PARAMCHANGETYPE_GPIO_STATE = 0X05;

	public native int CHD_WMP_GetParamChangeType(long handle);

	public native int CHD_WMP_Wireless_GetNetType(long handle);

	public native int CHD_WMP_Wireless_SetStaInfo(long handle,
			st_WirelessInfo param);

	public native int CHD_WMP_Wireless_GetStaInfo(long handle,
			st_WirelessInfo param);

	public native int CHD_WMP_Wireless_SetApInfo(long handle,
			st_WirelessInfo param);

	public native int CHD_WMP_Wireless_GetApInfo(long handle,
			st_WirelessInfo param);

	public native int CHD_WMP_Device_GetId(long handle);

	public native int CHD_WMP_Device_SetId(long handle, int id);

	public native String CHD_WMP_Device_GetAlias(long handle);

	public native int CHD_WMP_Device_SetAlias(long handle, String alias);

	public native int CHD_WMP_Device_Reboot(long handle);

	public native int CHD_WMP_Device_Reset(long handle);

	public native int CHD_WMP_Audio_Begin(long handle);

	public native int CHD_WMP_Audio_End(long handle);

	public native int CHD_WMP_Audio_GetParam(long handle,
			st_AudioParamInfo param);

	public native int CHD_WMP_Audio_RequestData(long handle,
			st_AudioFrame audioframe, byte[] data);

	public native int CHD_WMP_Video_GetDeviceIdx(long handle);

	public native int CHD_WMP_Video_SetDeviceIdx(long handle, int idx);

	public native int CHD_WMP_Video_Begin(long handle);

	public native int CHD_WMP_Video_End(long handle);

	public native int CHD_WMP_Video_RequestVideoData(long handle,
			st_VideoFrame videoframe, byte[] data);

	public native int CHD_WMP_Video_RequestVideoDataAddress(long handle,
			st_VideoFrame videoframe);

	public native int CHD_WMP_Video_ReleaseVideoDataAddress(long handle,
			st_VideoFrame videoframe);

	public native int CHD_WMP_Video_CopyVideoDataToByteArray(long handle,
			st_VideoFrame videoframe, byte[] data);

	public native int CHD_WMP_Video_SnapShot(long handle);

	public native int CHD_WMP_Video_SnapShotResolu(long handle, int width,
			int height);

	public native int CHD_WMP_Video_RequestPicData(long handle,
			st_VideoFrame videofram, byte[] data);

	public native int CHD_WMP_Video_GetCurVideoFrameNum(long handle);

	public native int CHD_WMP_Video_GetCurPictureFrameNum(long handle);

	public native int CHD_WMP_Video_GetPeerMaxFrameNum(long handle);

	public native int CHD_WMP_Video_GetLocalMaxFrameNum(long handle);

	public native int CHD_WMP_Video_SetPeerMaxFrameNum(long handle, int num);

	public native int CHD_WMP_Video_SetLocalMaxFrameNum(long handle, int num);

	public native int CHD_WMP_Video_GetAbility(long handle,
			st_VideoAbilityInfo abi);

	public native int CHD_WMP_Video_GetVideoCtrl(long handle, int type,
			st_VideoCtrlInfo vctrl);

	public native int CHD_WMP_Video_GetParam(long handle,
			st_VideoParamInfo param);

	public native int CHD_WMP_Video_GetFormat(long handle);

	public native int CHD_WMP_Video_GetResolu(long handle,
			st_VideoParamInfo param);

	public native int CHD_WMP_Video_GetFPS(long handle);

	public native int CHD_WMP_Video_SetVideoCtrl(long handle, int type,
			st_VideoCtrlInfo vctrl);

	public native int CHD_WMP_Video_ResetVCtrl(long handle);

	public native int CHD_WMP_Video_SetFormat(long handle, int format);

	public native int CHD_WMP_Video_SetResolu(long handle, int width, int height);

	public native int CHD_WMP_Video_SetFPS(long handle, int fps);

	public native int CHD_WMP_Video_GetH264KeyInter(long handle);

	public native int CHD_WMP_Video_GetH264QpValue(long handle);

	public native int CHD_WMP_Video_GetH264Stream(long handle);

	public native int CHD_WMP_Video_GetQuality(long handle);

	public native int CHD_WMP_Video_SetH264KeyInter(long handle, int cnt);

	public native int CHD_WMP_Video_SetH264QpValue(long handle, int value);

	public native int CHD_WMP_Video_SetH264Stream(long handle, int value);

	public native int CHD_WMP_Video_SetQuality(long handle, int quality);

	public native int CHD_WMP_Video_SetForceI(long handle);

	public native int CHD_WMP_Video_SetFpsLimit(long handle, int limit);

	public native int CHD_WMP_Serial_Begin(long handle);

	public native int CHD_WMP_Serial_End(long handle);

	public native int CHD_WMP_Serial_SendData(long handle, byte[] data,
			int datalen);

	public native int CHD_WMP_Serial_RequestData(long handle, byte[] data);

	public native int CHD_WMP_Serial_GetCurRxCacheSize(long handle);

	public native int CHD_WMP_Serial_GetRxTotalNum(long handle);

	public native int CHD_WMP_Serial_GetTxTotalNum(long handle);

	public native int CHD_WMP_Serial_GetParam(long handle, st_SerialInfo param);

	public native int CHD_WMP_Serial_GetSpeed(long handle);

	public native int CHD_WMP_Serial_GetDataBit(long handle);

	public native int CHD_WMP_Serial_GetStopBit(long handle);

	public native int CHD_WMP_Serial_GetParity(long handle);

	public native int CHD_WMP_Serial_GetTimeout(long handle);

	public native int CHD_WMP_Serial_SetSpeed(long handle, int speed);

	public native int CHD_WMP_Serial_SetDataBit(long handle, int databit);

	public native int CHD_WMP_Serial_SetStopBit(long handle, int stopbit);

	public native int CHD_WMP_Serial_SetParity(long handle, int parity);

	public native int CHD_WMP_Serial_SetTimeout(long handle, int timeout);

	public native int CHD_WMP_Gpio_GetAll(long handle, st_GpioInfo param);

	public native int CHD_WMP_Gpio_SetAll(long handle, st_GpioInfo param);

	public native int CHD_WMP_Gpio_GetStatus(long handle, int gpio,
			st_GpioInfo param);

	public native int CHD_WMP_Gpio_SetStatus(long handle, int gpio, int dir,
			int state);

	public native int CHD_WMP_I2C_GetValue(long handle, st_I2CInfo data);

	public native int CHD_WMP_I2C_SetValue(long handle, st_I2CInfo data);

	public native String CHD_WMP_GetEncrypt(long handle);

	public native int CHD_WMP_SetEncrypt(long handle, String passwd);

	public native int CHD_WMP_GetDeviceInfo(long handle, st_DeviceInfo dev);

	public native String CHD_WMP_GetMac(long handle);

	public native String CHD_WMP_GetCompany(long handle);

	public native int CHD_WMP_GetSystemTime(long handle, st_SystimeInfo stime);

	public native int CHD_WMP_SetSystemTime(long handle, st_SystimeInfo stime);

	public static final int CHD_TRANSMODE_TCP = 1;
	public static final int CHD_TRANSMODE_P2P = 2;
	public static final int CHD_TRANSMODE_RLY = 3;

	public native int CHD_WMP_GetTransMode(long handle);

	public native int CHD_WMP_File_Save(String filename, int datalen,
			byte[] data);

	public native long CHD_WMP_File_GetSize(String filename);

	public native int CHD_WMP_File_Copy(String srcfilename, String destfilename);

	public native long CHD_WMP_Folder_GetSize(String dirname);

	public native int CHD_WMP_Folder_Copy(String srcdir, String destdir);

	static {
		System.loadLibrary("nativeChdWmp");
	}
}
