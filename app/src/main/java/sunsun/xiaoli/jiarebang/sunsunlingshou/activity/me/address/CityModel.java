package sunsun.xiaoli.jiarebang.sunsunlingshou.activity.me.address;


public class CityModel {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private int number;
	private int fatherNum;
	
	public CityModel() {
		super();
	}

	public CityModel(String name, int number, int fatherNum) {
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
	
	public void setFatherNum(int fatherNum) {
		this.fatherNum = fatherNum;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ", number=" + number + ", fatherNum=" + fatherNum + "]";
	}
	
}
