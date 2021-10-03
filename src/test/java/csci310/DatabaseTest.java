package csci310;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseTest {
	@Test
	public void testDatabase() {
		Database testDB = new Database();
	}

	@Test
	public void testRegister() {
		Database testDB = new Database();
		assertTrue(testDB.register("dummy_user", "password"));
	}

	@Test
	public void testLogin() {
		Database testDB = new Database();
		assertTrue(testDB.login("dummy_user", "password"));
	}
	
	@Test
	public void testDeactivate() {
		Database testDB = new Database();
		assertTrue(testDB.deactivate("dummy_user", "password"));
	}
}
