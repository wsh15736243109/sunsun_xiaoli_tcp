package sunsun.xiaoli.jiarebang.beans;

import sunsun.xiaoli.jiarebang.utils.DeviceType;

public class AddBenas {
	private String name;
	private int img;
	private int bitmp;
	private DeviceType deviceType;

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public int getBitmp() {
		return bitmp;
	}

	public void setBitmp(int bitmp) {
		this.bitmp = bitmp;
	}

}
