package csci310;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseTest {
	@Test
	public void testCheckTableExists(){
		Database testDB = new Database();

		assertTrue(testDB.checkTableExists("users") == false);
		testDB.createRequiredTables();
		assertTrue(testDB.checkTableExists("users"));

		testDB.dropAllTables();
	}

	@Test
	public void testCreateRequiredTables(){
		Database testDB = new Database();

		assertTrue(testDB.checkTableExists("users") == false);
		testDB.createRequiredTables();
		// TODO: check if all required tables exist
		assertTrue(testDB.checkTableExists("users"));

		testDB.dropAllTables();
	}

	@Test
	public void testRegister() {
		Database testDB = new Database();

		assertTrue(testDB.register("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "password"));
		// TODO: test more invalid passwords

		testDB.dropAllTables();
	}

	@Test
	public void testLogin() {
		Database testDB = new Database();

		assertTrue(testDB.register("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "notpassword") == false);
		assertTrue(testDB.login("notdummy_user", "password") == false);

		testDB.dropAllTables();
	}
	
	@Test
	public void testDeactivate() {
		Database testDB = new Database();
		assertTrue(testDB.deactivate("dummy_user", "password"));
	}
}
