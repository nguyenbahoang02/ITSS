package view;

public class OfficerTimesheet {
	private String id;
	private String date;
	private String morning;
	private String afternoon;
	private String lateHours;
	private String earlyHours;
	private String startTime;
	private String endTime;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMorning() {
		return morning;
	}
	public void setMorning(String morning) {
		this.morning = morning;
	}
	public String getAfternoon() {
		return afternoon;
	}
	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}
	public String getLateHours() {
		return lateHours;
	}
	public void setLateHours(String lateHours) {
		this.lateHours = lateHours;
	}
	public String getSoonHours() {
		return earlyHours;
	}
	public void setSoonHours(String soonHours) {
		this.earlyHours = soonHours;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
