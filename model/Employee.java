package model;

public class Employee {
	private String name;
	private String id;
	private String unitId;
	
	public Employee(String name, String id, String unitId) {
		super();
		this.name = name;
		this.id = id;
		this.unitId = unitId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}
