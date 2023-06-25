package view;

public class WorkerTimesheet {
	private String date;
	private String workHoursShift1;
	private String workHoursShift2;
	private String workHoursShift3;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWorkHoursShift1() {
		return workHoursShift1;
	}
	public void setWorkHoursShift1(String workHoursShift1) {
		this.workHoursShift1 = workHoursShift1;
	}
	public String getWorkHoursShift2() {
		return workHoursShift2;
	}
	public void setWorkHoursShift2(String workHoursShift2) {
		this.workHoursShift2 = workHoursShift2;
	}
	public String getWorkHoursShift3() {
		return workHoursShift3;
	}
	public void setWorkHoursShift3(String workHoursShift3) {
		this.workHoursShift3 = workHoursShift3;
	}
	public WorkerTimesheet(String date, String workHoursShift1, String workHoursShift2, String workHoursShift3) {
		super();
		this.date = date;
		this.workHoursShift1 = workHoursShift1;
		this.workHoursShift2 = workHoursShift2;
		this.workHoursShift3 = workHoursShift3;
	}
	
}
