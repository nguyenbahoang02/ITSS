package testCase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.DetailTabController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AttendanceRecord;

class DetailTest {

	@Test
	void detailTabSEARCH_ATTENDANCE_RECORDTest() {
		ObservableList<AttendanceRecord> searchList = FXCollections.observableArrayList();
		AttendanceRecord attendanceRecord1 = new AttendanceRecord("1","01/06/2023","có","không","0.5","0.8");
		AttendanceRecord attendanceRecord2 = new AttendanceRecord("1","02/06/2023","có","có","0","0");
		AttendanceRecord attendanceRecord3 = new AttendanceRecord("2","07/06/2023","có","có","0","1");
		AttendanceRecord attendanceRecord4 = new AttendanceRecord("2","08/06/2023","có","có","0","0");
		AttendanceRecord attendanceRecord5 = new AttendanceRecord("3","09/06/2023","không","có","0.3","1");
		AttendanceRecord attendanceRecord6 = new AttendanceRecord("3","10/06/2023","không","có","0.4","1");
		
		searchList.add(attendanceRecord1);
		searchList.add(attendanceRecord2);
		searchList.add(attendanceRecord3);
		searchList.add(attendanceRecord4);
		searchList.add(attendanceRecord5);
		searchList.add(attendanceRecord6);
		
		ObservableList<AttendanceRecord> expectedList = FXCollections.observableArrayList();
		expectedList.add(attendanceRecord1);
		expectedList.add(attendanceRecord6);
		
		ObservableList<AttendanceRecord> actualList = DetailTabController.SEARCH_ATTENDANCE_RECORD(expectedList, "1");
		
		for (int i = 0; i < actualList.size(); i++) {
			assertEquals(expectedList.get(i).getDate(), actualList.get(i).getDate());
		}
	}

}
