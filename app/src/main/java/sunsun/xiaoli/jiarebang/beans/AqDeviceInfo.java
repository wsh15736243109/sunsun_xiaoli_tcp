package sunsun.xiaoli.jiarebang.beans;

/**
 * Created by 赵武 on 2015/3/9.
 */
public class AqDeviceInfo {

	public String mDeviceName;
	public String mPassword;
	public String mDid;
	public Object mOther;
	public String mLanIp;
	public String mMacAddress;
	public AqDeviceType mDeviceType;
	public int mDeviceVersion;

	public enum AqDeviceType {
		NULL, AQ107, AQ209, AQ208, AQ805,AQ211
	}

	public AqDeviceInfo() {
		mDeviceName = "AQ设备";
		mPassword = "";
		mDid = "0";
		mOther = null;
		mLanIp = "0.0.0.0";
		mMacAddress = "";
		mDeviceType = AqDeviceType.NULL;
		mDeviceVersion = 0x0000;
	}
}
