package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;

public class DistrictModel {
	private String name;
	private int number;
	private int fatherNum;
	
	public DistrictModel() {
		super();
	}

	public DistrictModel(String name, int number, int fatherNum) {
		super();
		this.name = name;
		this.number = number;
		this.fatherNum = fatherNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getFatherNum() {
		return fatherNum;
	}

	@Override
	public String toString() {
		return "DistrictModel [name=" + name + ", number=" + number + ", fatherNum=" + fatherNum + "]";
	}

}
