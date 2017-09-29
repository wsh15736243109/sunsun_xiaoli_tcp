package sunsun.xiaoli.jiarebang.beans;

import android.graphics.Bitmap;

/**
 * Created by 赵武 on 2015/3/12.
 */
public class AqDeviceMessage {
	public enum AqDeviceMessageType {
		Null, FindLanDevice, NewLanDevice, NewVideoFrame,
		ConnectOk, ConnectFailed,
		GetStateOk, GetStateFailed,
		SetDatetimeOk, SetDatetimeFailed,
		GetSubscribeTokenOk, GetSubscribeTokenFailed,
		SetSubscribeTempOk, SetSubscribeTempFailed,
		SetSubscribeLevelOk, SetSubscribeLevelFailed,
		SetSubscribeLoadOk, SetSubscribeLoadFailed,
		SetRelaysOk, SetRelaysFailed,
		SetPeriodOk, SetPeriodFailed,
		SetLockOk, SetLockFailed,
		SetMaxTempOk, SetMaxTempFailed,
		SetFaultTempOk, SetFaultTempFailed,
		VideoStopOk, VideoStopFailed,
		VideoStartOk, VideoStartFailed,
		GetVersionOk, GetVersionFailed,
		GetSoftVersionOk, GetSoftVersionFailed,
		LinuxRebootOk, LinuxRebootFailed,
	}

	public AqDevice mDevice;
	public Bitmap mFrame;
	public AqDeviceMessageType mMessageType;
	public AqDeviceInfo mDeviceInfo;
	public AqDevice.CommandItem mCommandItem;

	public AqDeviceMessage() {
		mDevice = null;
		mFrame = null;
		mMessageType = AqDeviceMessageType.Null;
		mDeviceInfo = null;
		mCommandItem = null;
	}
}
