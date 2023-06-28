package view;

import java.util.ArrayList;

public class API {
	private static Officer USER;
	private static ArrayList<Unit> ALL_UNITS = new ArrayList<>();
	
	public static void CREATE_UNIT() {
		Unit unit = new Unit("Đơn vị 1","1");
    	unit.getEmployees("src\\data\\employeesUnit1.json");
    	unit.getEmployeesWorksData();
    	Unit unit1 = new Unit("Đơn vị 2","2");
    	unit1.getEmployees("src\\data\\employeesUnit2.json");
    	unit1.getEmployeesWorksData();
    	Unit unit2 = new Unit("Đơn vị 3","3");
    	unit2.getEmployees("src\\data\\employeesUnit3.json");
    	unit2.getEmployeesWorksData();
    	Unit unit3 = new Unit("Đơn vị 4","4");
    	unit3.getEmployees("src\\data\\employeesUnit4.json");
    	unit3.getEmployeesWorksData();
    	Unit unit4 = new Unit("Đơn vị 5","5");
    	unit4.getEmployees("src\\data\\employeesUnit5.json");
    	unit4.getEmployeesWorksData();
    	Unit unit5 = new Unit("Đơn vị 6","6");
    	unit5.getEmployees("src\\data\\employeesUnit6.json");
    	unit5.getEmployeesWorksData();
    	Unit unit6 = new Unit("Đơn vị 7","7");
    	unit6.getEmployees("src\\data\\employeesUnit7.json");
    	unit6.getEmployeesWorksData();
    	Unit unit7 = new Unit("Đơn vị 8","8");
    	unit7.getEmployees("src\\data\\employeesUnit8.json");
    	unit7.getEmployeesWorksData();
    	Unit unit8 = new Unit("Đơn vị 9","9");
    	unit8.getEmployees("src\\data\\employeesUnit9.json");
    	unit8.getEmployeesWorksData();
    	Unit unit9 = new Unit("Đơn vị 10","10");
    	unit9.getEmployees("src\\data\\employeesUnit10.json");
    	unit9.getEmployeesWorksData();
    	ALL_UNITS.add(unit);
    	ALL_UNITS.add(unit1);
    	ALL_UNITS.add(unit2);
    	ALL_UNITS.add(unit3);
    	ALL_UNITS.add(unit4);
    	ALL_UNITS.add(unit5);
    	ALL_UNITS.add(unit6);
    	ALL_UNITS.add(unit7);
    	ALL_UNITS.add(unit8);
    	ALL_UNITS.add(unit9);
	}
	
	public static ArrayList<Unit> GET_ALL_UNITS() {
		return ALL_UNITS;
	}
	
	public static Officer GET_USER() {
		return USER;
	}
	
	public static void SET_USER(Officer user) {
		USER=user;
	}
	
	public static Unit GET_UNIT(String id) {
		for (Unit unit : ALL_UNITS) {
			if(unit.getId().equals(id))
				return unit;
		}
		return null;
	}
	
	public static boolean IMPORT_DATA(ArrayList<AttendanceRecord> attendanceRecords) {
		for (Unit unit : ALL_UNITS) {
			if(!unit.importData(attendanceRecords))
				return false;
		}
		return true;
	}
}
