package testCase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import controller.ImportTabController;
import model.AttendanceRecord;

class ImportTest {

	@Test
	void importTabControllerReadExcelTest() {
		ArrayList<AttendanceRecord> list = new ArrayList<>();
		AttendanceRecord attendanceRecord1 = new AttendanceRecord("1","01/06/2023","có","không","0.5","0.8");
		AttendanceRecord attendanceRecord2 = new AttendanceRecord("1","02/06/2023","có","có","0","0");
		AttendanceRecord attendanceRecord3 = new AttendanceRecord("2","07/06/2023","có","có","0","1");
		AttendanceRecord attendanceRecord4 = new AttendanceRecord("2","08/06/2023","có","có","0","0");
		AttendanceRecord attendanceRecord5 = new AttendanceRecord("3","09/06/2023","không","có","0.3","1");
		AttendanceRecord attendanceRecord6 = new AttendanceRecord("3","10/06/2023","không","có","0.4","1");
		list.add(attendanceRecord1);
		list.add(attendanceRecord2);
		list.add(attendanceRecord3);
		list.add(attendanceRecord4);
		list.add(attendanceRecord5);
		list.add(attendanceRecord6);
		try {
			ArrayList<AttendanceRecord> checkList = ImportTabController.readExcel(ImportTest.class.getClassLoader().getResource("./data/testImport.xlsx").getFile());
			for (int i = 0; i < checkList.size(); i++) {
				assertEquals(list.get(i).getDate(), checkList.get(i).getDate());
				assertEquals(list.get(i).getId(), checkList.get(i).getId());
				assertEquals(Normalizer.normalize(list.get(i).getMorning(), Normalizer.Form.NFC), Normalizer.normalize(checkList.get(i).getMorning(), Normalizer.Form.NFC));
				assertEquals(Normalizer.normalize(list.get(i).getAfternoon(), Normalizer.Form.NFC), Normalizer.normalize(checkList.get(i).getAfternoon(), Normalizer.Form.NFC));
				assertEquals(list.get(i).getLateHours(), checkList.get(i).getLateHours());
				assertEquals(list.get(i).getEarlyHours(), checkList.get(i).getEarlyHours());
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
