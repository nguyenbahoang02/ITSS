package model;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OfficerWorksDetail {
	
	private ArrayList<AttendanceRecord> attendanceRecord = new ArrayList<>();

	public ArrayList<AttendanceRecord> getOfficerTimesheet() {
		return attendanceRecord;
	}

	public boolean importData(AttendanceRecord importedData) {
		String date = importedData.getDate();
		int day = Integer.parseInt(date.split("/")[0]);
		int month = Integer.parseInt(date.split("/")[1]);
		int year = Integer.parseInt(date.split("/")[2]);
		for (AttendanceRecord officerTimesheet2 : attendanceRecord) {
			String date1 = officerTimesheet2.getDate();
			int day1 = Integer.parseInt(date1.split("/")[0]);
			int month1 = Integer.parseInt(date1.split("/")[1]);
			int year1 = Integer.parseInt(date1.split("/")[2]);
			if(day1==day&&month==month1&&year==year1) {
				System.out.println(day + "/"+ month + "/"+ year);
				return false;			
			}
		}
		attendanceRecord.add(importedData);
		return true;
	}
	
	public void setOfficerTimesheet(ArrayList<AttendanceRecord> attendanceRecord) {
		this.attendanceRecord = attendanceRecord;
	}
	
	public void getDataFromFile() {
		try {
			Reader reader = Files.newBufferedReader(Paths.get("src\\data\\officerTimeSheet.json"));
			List<AttendanceRecord> list = new Gson().fromJson(reader,
					new TypeToken<List<AttendanceRecord>>() {
					}.getType());
			reader.close();
			attendanceRecord.addAll(list);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public String getTotalShiftByMonth(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		int totalShift = 0;
		String month = time.split("/")[0];
		String year = time.split("/")[1];
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String monthString = officerTimesheet1.getDate().split("/")[1];
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(month.equals(monthString)&&year.equals(yearString)) {
				if(officerTimesheet1.getMorning().equals("có")) {
					totalShift++;
				}
				if(officerTimesheet1.getAfternoon().equals("có")) {
					totalShift++;
				}
			}
		}
		
		return stringBuffer.toString() + totalShift;
	}
	
	public String getLateHoursByMonth(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		float lateHours = 0;
		String month = time.split("/")[0];
		String year = time.split("/")[1];
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String monthString = officerTimesheet1.getDate().split("/")[1];
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(month.equals(monthString)&&year.equals(yearString)) {
				lateHours+=Float.parseFloat(officerTimesheet1.getLateHours());
			}
		}
		
		return stringBuffer.toString() + lateHours;
	}
	
	public String getEarlyHoursByMonth(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		float earlyHours = 0;
		String month = time.split("/")[0];
		String year = time.split("/")[1];
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String monthString = officerTimesheet1.getDate().split("/")[1];
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(month.equals(monthString)&&year.equals(yearString)) {
				earlyHours+=Float.parseFloat(officerTimesheet1.getEarlyHours());
			}
		}
		
		return stringBuffer.toString() + earlyHours;
	}
	
	public String getTotalShiftByYear(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		int totalShift = 0;
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(yearString.equals(time)) {
				if(officerTimesheet1.getMorning().equals("có")) {
					totalShift++;
				}
				if(officerTimesheet1.getAfternoon().equals("có")) {
					totalShift++;
				}
			}
		}
		
		return stringBuffer.toString() + totalShift;
	}
	
	public String getLateHoursByYear(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		float lateHours = 0;
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(yearString.equals(time)) {
				lateHours+=Float.parseFloat(officerTimesheet1.getLateHours());
			}
		}
		
		return stringBuffer.toString() + lateHours;
	}
	
	public String getEarlyHoursByYear(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		float earlyHours = 0;
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(yearString.equals(time)) {
				earlyHours+=Float.parseFloat(officerTimesheet1.getEarlyHours());
			}
		}
		
		return stringBuffer.toString() + earlyHours;
	}
	
	public String getTotalShiftByQuarter(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		int totalShift = 0;
		int quy = Integer.parseInt(time.split(" ")[1]);
		String year = time.split(" ")[2]; 
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			int monthInt = Integer.parseInt(officerTimesheet1.getDate().split("/")[1]);
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(monthInt<=quy*3 && monthInt >= quy*3-2 && year.equals(yearString)) {
				if(officerTimesheet1.getMorning().equals("có")) {
					totalShift++;
				}
				if(officerTimesheet1.getAfternoon().equals("có")) {
					totalShift++;
				}
			}
		}
		
		return stringBuffer.toString() + totalShift;
	}
	
	public String getLateHoursByQuarter(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		float lateHours = 0;
		int quy = Integer.parseInt(time.split(" ")[1]);
		String year = time.split(" ")[2]; 
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			int monthInt = Integer.parseInt(officerTimesheet1.getDate().split("/")[1]);
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(monthInt<=quy*3 && monthInt >= quy*3-2 && year.equals(yearString)) {
				lateHours+=Float.parseFloat(officerTimesheet1.getLateHours());
			}
		}
		
		return stringBuffer.toString() + lateHours;
	}
	
	public String getEarlyHoursByQuarter(String time) {
		StringBuffer stringBuffer = new StringBuffer("");
		float earlyHours = 0;
		int quy = Integer.parseInt(time.split(" ")[1]);
		String year = time.split(" ")[2]; 
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			int monthInt = Integer.parseInt(officerTimesheet1.getDate().split("/")[1]);
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(monthInt<=quy*3 && monthInt >= quy*3-2 && year.equals(yearString)) {
				earlyHours+=Float.parseFloat(officerTimesheet1.getEarlyHours());
			}
		}
		
		return stringBuffer.toString() + earlyHours;
	}
	
	public ArrayList<AttendanceRecord> getTimesheetByMonth(String time){
		ArrayList<AttendanceRecord> list = new ArrayList<>();
		String month = time.split("/")[0];
		String year = time.split("/")[1];
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String monthString = officerTimesheet1.getDate().split("/")[1];
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(month.equals(monthString)&&year.equals(yearString)) {
				list.add(officerTimesheet1);
			}
		}
		return sortTimesheets(list);
	}
	
	public ArrayList<AttendanceRecord> getTimesheetByYear(String time){
		ArrayList<AttendanceRecord> list = new ArrayList<>();
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(time.equals(yearString)) {
				list.add(officerTimesheet1);
			}
		}
		
		return sortTimesheets(list);
	}
	
	public ArrayList<AttendanceRecord> getTimesheetByQuarter(String time){
		ArrayList<AttendanceRecord> list = new ArrayList<>();
		int quy = Integer.parseInt(time.split(" ")[1]);
		String year = time.split(" ")[2]; 
		for (AttendanceRecord officerTimesheet1 : attendanceRecord) {
			int monthInt = Integer.parseInt(officerTimesheet1.getDate().split("/")[1]);
			String yearString = officerTimesheet1.getDate().split("/")[2];
			if(monthInt<=quy*3 && monthInt >= quy*3-2 && year.equals(yearString)) {
				list.add(officerTimesheet1);
			}
		}
		
		return sortTimesheets(list);
	}
	
	private ArrayList<AttendanceRecord> sortTimesheets(ArrayList<AttendanceRecord> attendanceRecords){
		Collections.sort(attendanceRecords, new Comparator<AttendanceRecord>() {

			@Override
			public int compare(AttendanceRecord o1, AttendanceRecord o2) {
				int o1Day = Integer.parseInt(o1.getDate().split("/")[0]);
				int o1Month = Integer.parseInt(o1.getDate().split("/")[1]);
				int o1Year = Integer.parseInt(o1.getDate().split("/")[2]);
				int o2Day = Integer.parseInt(o2.getDate().split("/")[0]);
				int o2Month = Integer.parseInt(o2.getDate().split("/")[1]);
				int o2Year = Integer.parseInt(o2.getDate().split("/")[2]);
				if(o1Year>o2Year) return 1;
				else if(o1Year<o2Year) return -1;
				else {
					if(o1Month>o2Month) return 1;
					else if(o1Month<o2Month) return -1;
					else return o1Day>o2Day ? 1 : -1;
				}
			}
			
		});
		
		return attendanceRecords;
	}
}










