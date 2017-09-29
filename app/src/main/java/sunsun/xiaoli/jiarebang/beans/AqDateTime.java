package sunsun.xiaoli.jiarebang.beans;

import android.text.format.Time;

/**
 * Created by 赵武 on 2015/3/3.
 */
public class AqDateTime {
	public int mYear;
	public int mMon;
	public int mDay;
	public int mWeek;
	public int mHour;
	public int mMin;
	public int mSec;

	public AqDateTime() {
		//Time timeNow = new Time("Asia/Shanghai");
		Time timeNow = new Time();
		timeNow.set(System.currentTimeMillis());
		mYear = timeNow.year;
		mMon = timeNow.month + 1;
		mDay = timeNow.monthDay;
		mWeek = timeNow.weekDay;
		mHour = timeNow.hour;
		mMin = timeNow.minute;
		mSec = timeNow.second;
	}

	public byte[] getBytes() {
		byte[] b = new byte[7];
		b[0] = (byte)(mYear % 100);
		b[1] = (byte)mMon;
		b[2] = (byte)mDay;
		b[3] = (byte)mWeek;
		b[4] = (byte)mHour;
		b[5] = (byte)mMin;
		b[6] = (byte)mSec;
		return b;
	}

	public void getBytes(byte[] buffer, int start) {
		byte[] b = getBytes();
		System.arraycopy(b, 0, buffer, start, b.length);
	}

	public void setBytes(byte[] buffer, int start) {
		mYear = buffer[start] + 2000;
		mMon = buffer[start + 1];
		mDay = buffer[start + 2];
		mWeek = buffer[start + 3];
		mHour = buffer[start + 4];
		mMin = buffer[start + 5];
		mSec = buffer[start + 6];
	}
}
