package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;


public class ProvinceModel {
	private String name;
	private int number;
	
	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name, int number) {
		super();
		this.name = name;
		this.number = number;
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

	@Override
	public String toString() {
		return "ProvinceModel [name=" + name + ", number=" + number + "]";
	}
	
}
