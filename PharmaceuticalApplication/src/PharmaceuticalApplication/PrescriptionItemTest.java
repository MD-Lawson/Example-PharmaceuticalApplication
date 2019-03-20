package PharmaceuticalApplication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrescriptionItemTest {
	PrescriptionItem testItem;
	@BeforeEach
	void setUp() throws Exception {
		testItem = new PrescriptionItem("Name", 1, 1, 1, 
				true, "description", "additional cooments");
	}

	@Test
	void shouldGetCorrectName() {
		assertEquals(testItem.getName(), "Name");
	}
	@Test
	void shouldSetCorrectName() {
		testItem.setName("New Name");
		assertEquals(testItem.getName(), "New Name");
	}
	@Test
	void shouldGetCorrectDescription() {
		assertEquals(testItem.getDescription(), "description");
	}
	@Test
	void shouldSetCorrectDescription() {
		testItem.setDescription("new description");
		assertEquals(testItem.getDescription(), "new description");
	}
	@Test
	void shouldGetAvailableOverCounter() {
		assertEquals(testItem.isAvailableOverTheCounter(), true);
	}
	@Test
	void shouldSetAvailableOverCounter() {
		testItem.setAvailableOverTheCounter(false);
		assertEquals(testItem.isAvailableOverTheCounter(), false);
	}
	

}
