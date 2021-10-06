package csci310;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseTest {
	@Test
	public void testCheckTableExists() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		assertTrue(testDB.checkTableExists("users") == false);
		testDB.createRequiredTables();
		assertTrue(testDB.checkTableExists("users"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testCreateRequiredTables() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		assertTrue(testDB.checkTableExists("users") == false);
		testDB.createRequiredTables();
		// TODO: check if all required tables exist
		assertTrue(testDB.checkTableExists("users"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testDropAllTables() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.checkTableExists("users"));
		testDB.dropAllTables();
		assertTrue(testDB.checkTableExists("users") == false);
		testDB.close();
	}

	@Test
	public void testCheckUserExists() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.checkUserExists("randomperson") == false);
		testDB.register("randomperson", "randompassword");
		assertTrue(testDB.checkUserExists("randomperson"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testRegister() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.register("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "badpassword") == false);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testLogin() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.register("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "notpassword") == false);
		assertTrue(testDB.login("notdummy_user", "password") == false);
		testDB.dropAllTables();
		testDB.close();
	}
	
	@Test
	public void testDeactivate() throws Exception{
		Database testDB = new Database();
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.deactivate("dummy_user", "password") == false);
		testDB.register("dummy_user", "password");
		assertTrue(testDB.deactivate("dummy_user", "password"));
		assertTrue(testDB.checkUserExists("dummy_user") == false);
		assertTrue(testDB.deactivate("dummy_user", "password") == false);
		testDB.dropAllTables();
		testDB.close();
	}
}
