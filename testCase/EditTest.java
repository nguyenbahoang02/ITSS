package testCase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.EditScreenController;

class EditTest {

	@Test
	void editScreenControllerCHECK_MORNING_AFTERNOON_FORMAT_TRUE() {
		assertEquals(true, EditScreenController.CHECK_MORNING_AFTERNOON_FORMAT("có"));
	}

	@Test
	void editScreenControllerCHECK_MORNING_AFTERNOON_FORMAT_FALSE() {
		assertEquals(false, EditScreenController.CHECK_MORNING_AFTERNOON_FORMAT("c"));
	}
	
	@Test
	void editScreenControllerCHECK_DATE_FORMAT_TRUE() {
		assertEquals(true, EditScreenController.CHECK_DATE_FORMAT("20/06/2030"));
	}
	
	@Test
	void editScreenControllerCHECK_DATE_FORMAT_FALSE() {
		assertEquals(false, EditScreenController.CHECK_DATE_FORMAT("20062030"));
	}
}
