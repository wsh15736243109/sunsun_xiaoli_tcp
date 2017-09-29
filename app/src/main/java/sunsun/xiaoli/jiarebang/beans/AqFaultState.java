package sunsun.xiaoli.jiarebang.beans;

/**
 * Created by 赵武 on 2015/3/11.
 */
public class AqFaultState {

	public enum PhFaultState {
		NORMAL, LOW, HIGH
	}

	public enum TempFaultState {
		NORMAL, LOW, HIGH
	}

	public boolean mLamp;
	public boolean mPump;
	public boolean mUvLamp;
	public boolean mStandbyPower;

	/**
	 * 状态取决于mLamp、mPump、mUvLamp、mStandbyPower
	 */
	public boolean mLoad;

	public boolean mLevel;
	public PhFaultState mPh;
	public TempFaultState mTemp;

	public AqFaultState() {
		mLamp = false;
		mPump = false;
		mUvLamp = false;
		mLevel = false;
		mLoad = false;
		mStandbyPower = false;
		mPh = PhFaultState.NORMAL;
		mTemp = TempFaultState.NORMAL;
	}

	public void setBytes(byte[] buffer, int start) {
		int x = ((buffer[start] << 8) & 0xff00) | (buffer[start + 1] & 0xff);
		if ((x & 0x0004) != 0) {
			mUvLamp = true;
		} else {
			mUvLamp = false;
		}
		if ((x & 0x0008) != 0) {
			mPump = true;
		} else {
			mPump = false;
		}
		if ((x & 0x0010) != 0) {
			mLamp = true;
		} else {
			mLamp = false;
		}
		if ((x & 0x0020) != 0) {
			mStandbyPower = true;
		} else {
			mStandbyPower = false;
		}
		if ((x & 0x0040) != 0) {
			mLevel = true;
		} else {
			mLevel = false;
		}
		switch (x & 0x0003) {
			case 0x00:
				mTemp = TempFaultState.NORMAL;
				break;
			case 0x01:
				mTemp = TempFaultState.LOW;
				break;
			case 0x02:
				mTemp = TempFaultState.HIGH;
				break;
			default:
				break;
		}
		switch ((x >> 8) & 0x0003) {
			case 0x00:
				mPh = PhFaultState.NORMAL;
				break;
			case 0x01:
				mPh = PhFaultState.LOW;
				break;
			case 0x02:
				mPh = PhFaultState.HIGH;
				break;
			default:
				break;
		}
	}
}
