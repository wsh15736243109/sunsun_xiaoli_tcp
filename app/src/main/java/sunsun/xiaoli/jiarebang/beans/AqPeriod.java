package sunsun.xiaoli.jiarebang.beans;

/**
 * Created by 赵武 on 2015/3/3.
 */
public class AqPeriod {

	public class AqPeriodItem {
		public int hourOn;
		public int minOn;
		public int hourOff;
		public int minOff;

		public AqPeriodItem() {
			hourOn = 0;
			minOn = 0;
			hourOff = 0;
			minOff = 0;
		}

		public AqPeriodItem(AqPeriodItem periodItem) {
			hourOn = periodItem.hourOn;
			hourOff = periodItem.hourOff;
			minOn = periodItem.minOn;
			minOff = periodItem.minOff;
		}

		public byte[] getBytes() {
			byte[] b = new byte[2];
			b[0] = (byte) ((((byte) (minOn / 10) & (byte) 0x07) << 5) | (byte) hourOn);
			b[1] = (byte) ((((byte) (minOff / 10) & (byte) 0x07) << 5) | (byte) hourOff);
			return b;
		}

		public void getBytes(byte[] buffer, int start) {
			byte[] b = getBytes();
			buffer[start] = b[0];
			buffer[start + 1] = b[1];
		}

		public void setBytes(byte[] buffer, int start) {
			hourOn = buffer[start] & 0x1f;
			minOn = ((buffer[start] >> 5) & 0x07) * 10;
			hourOff = buffer[start + 1] & 0x1f;
			minOff = ((buffer[start + 1] >> 5) & 0x07) * 10;
		}

		public boolean compare(AqPeriodItem item) {
			if (item.hourOn == hourOn) {
				if (item.minOn == minOn) {
					if (item.hourOff == hourOff) {
						if (item.minOff == minOff) {
							return true;
						}
					}
				}
			}
			return false;
		}

		public void copyFrom(AqPeriodItem item)
		{
			hourOn = item.hourOn;
			hourOff = item.hourOff;
			minOn = item.minOn;
			minOff = item.minOff;
		}
	}

	public AqPeriodItem period1;
	public AqPeriodItem period2;
	public AqPeriodItem period3;

	public AqPeriod() {
		period1 = new AqPeriodItem();
		period2 = new AqPeriodItem();
		period3 = new AqPeriodItem();
	}

	public AqPeriod(AqPeriod period) {
		period1 = new AqPeriodItem(period.period1);
		period2 = new AqPeriodItem(period.period2);
		period3 = new AqPeriodItem(period.period3);
	}

	public void getBytes(byte[] buffer, int start) {
		period1.getBytes(buffer, start);
		period2.getBytes(buffer, start + 2);
		period3.getBytes(buffer, start + 4);
	}

	public void setBytes(byte[] buffer, int start) {
		period1.setBytes(buffer, start);
		period2.setBytes(buffer, start + 2);
		period3.setBytes(buffer, start + 4);
	}

	/**
	 * 判断时段是否为开启状态
	 * @param index 时段序号，1~3
	 * @return 时段无效返回false，时段项有效返回true
	 */
	public boolean isEnable(int index) {
		boolean state = false;

		switch (index) {
			case 1:
				// 第一个时段永远是有效的
				state = true;
				break;
			case 2:
				// 不同于第一个时段就是有效的
				if (period2.compare(period1) == false) {
					state = true;
				}
				break;
			case 3:
				// 不同于第一个和第二个时段就是有效的
				if ((period3.compare(period1) == false) && (period3.compare(period2) == false)) {
					state = true;
				}
				break;
			default:
				break;
		}
		return state;
	}

	public static boolean copy(AqPeriod src, AqPeriod dest) {
		if ((src == null) || (dest == null)) {
			return false;
		}
		byte[] b = new byte[6];
		src.getBytes(b, 0);
		dest.setBytes(b, 0);
		return true;
	}

	public boolean compare(AqPeriod period) {
		if (period.period1.compare(period1)) {
			if (period.period2.compare(period2)) {
				if (period.period3.compare(period3)) {
					return true;
				}
			}
		}
		return false;
	}
}
