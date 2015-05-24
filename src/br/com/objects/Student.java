package br.com.objects;

public class Student {

	private String name;
	private String phone;
	private int baseId;

	public void setBaseId(int baseId){
		this.baseId = baseId;
	}
	
	public int getBaseId() {
		return baseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String toString() {
		return String.format("\n(ID %d)\nStudent name: %s.\nStudent phone: %s", getBaseId(), getName(), getPhone());
	}

}
