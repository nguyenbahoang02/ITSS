package testCase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Officer;

class UnitTest {

	@Test
	void unitTabControllerSEARCH_OFFICERTest() {
		ObservableList<Officer> searchList = FXCollections.observableArrayList();
		Officer officer1 = new Officer("Nguyễn Thị Thu", "20205030", "VN-03");
		Officer officer2 = new Officer("Nguyễn Bá Hoàng", "20204976", "VN-03");
		Officer officer3 = new Officer("Phạm Vũ Hoàng", "20208000", "VN-03");
		Officer officer4 = new Officer("Phạm Tuấn Anh", "20205054", "VN-03");
		Officer officer5 = new Officer("Đỗ Quang Huy", "20204983", "VN-03");
		
		searchList.add(officer1);
		searchList.add(officer2);
		searchList.add(officer3);
		searchList.add(officer4);
		searchList.add(officer5);
		
		ObservableList<Officer> expectedList = FXCollections.observableArrayList();
		expectedList.add(officer3);
		expectedList.add(officer4);
		
		ObservableList<Officer> actualList = FXCollections.observableArrayList();
		
		for (int i = 0; i < actualList.size(); i++) {
			assertEquals(expectedList.get(i).getId(), actualList.get(i).getId());
		}
	}

}
