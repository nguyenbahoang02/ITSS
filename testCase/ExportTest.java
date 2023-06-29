package testCase;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import controller.ExportTabController;
import model.AttendanceRecord;

class ExportTest {

	@Test
	void exportTabControllerWRITE_DATATest() {
		AttendanceRecord attendanceRecord = new AttendanceRecord("1","20/06/2023","có","không","0.5","0.8");
		Workbook expectedWorkbook = new XSSFWorkbook();
		Sheet sheet = expectedWorkbook.createSheet("Sheet");
		Row row = sheet.createRow(1);
        Cell cell = row.createCell(0);
        cell.setCellValue(attendanceRecord.getId());
 
        cell = row.createCell(1);
        cell.setCellValue(attendanceRecord.getDate());
 
        cell = row.createCell(2);
        cell.setCellValue(attendanceRecord.getMorning());
 
        cell = row.createCell(3);
        cell.setCellValue(attendanceRecord.getAfternoon());
        
        cell = row.createCell(4);
        cell.setCellValue(attendanceRecord.getLateHours());
        
        cell = row.createCell(5);
        cell.setCellValue(attendanceRecord.getEarlyHours());
        
        Workbook actualWorkbook = new XSSFWorkbook();
        Sheet sheet2 = actualWorkbook.getSheet("Sheet");
        Row row2 = sheet2.createRow(1);
        ExportTabController.WRITE_DATA(attendanceRecord, row2, attendanceRecord.getId());
        assertEquals(expectedWorkbook.getSheet("Sheet").getRow(1).getCell(0), actualWorkbook.getSheet("Sheet").getRow(1).getCell(0));
        assertEquals(expectedWorkbook.getSheet("Sheet").getRow(1).getCell(1), actualWorkbook.getSheet("Sheet").getRow(1).getCell(1));
        assertEquals(expectedWorkbook.getSheet("Sheet").getRow(1).getCell(2), actualWorkbook.getSheet("Sheet").getRow(1).getCell(2));
        assertEquals(expectedWorkbook.getSheet("Sheet").getRow(1).getCell(3), actualWorkbook.getSheet("Sheet").getRow(1).getCell(3));
        assertEquals(expectedWorkbook.getSheet("Sheet").getRow(1).getCell(4), actualWorkbook.getSheet("Sheet").getRow(1).getCell(4));
        assertEquals(expectedWorkbook.getSheet("Sheet").getRow(1).getCell(5), actualWorkbook.getSheet("Sheet").getRow(1).getCell(5));
	}

}
