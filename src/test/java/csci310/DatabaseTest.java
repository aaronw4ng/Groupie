package csci310;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileReader;
import org.ini4j.Ini;

public class DatabaseTest {

	@Test
	public void testCloseWhenAlreadyClosed() throws Exception {
		Database testDB = new Database("test.db");
		// close connection once
		testDB.close();

		// try to close connection again
		try {
			testDB.close();
		} catch(Exception e) {
			String message = "Invalid operation.";
			assertEquals(message, e.getMessage());
		}
	}

	@Test
	public void testCheckTableExists() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		assertTrue(testDB.checkTableExists("users") == false);
		testDB.createRequiredTables();
		assertTrue(testDB.checkTableExists("users"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testCreateRequiredTables() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		assertTrue(testDB.checkTableExists("users") == false);
		testDB.createRequiredTables();
		// TODO: check if all required tables exist
		Ini testConfig = new Ini(new FileReader("config/db_config.ini"));
		for (String tableName: testConfig.keySet()){
			assertTrue(testDB.checkTableExists(tableName));
		}
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testDropAllTables() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.checkTableExists("users"));
		testDB.dropAllTables();
		assertTrue(testDB.checkTableExists("users") == false);
		testDB.close();
	}

	@Test
	public void testCheckUserExists() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.checkUserExists("randomperson") == false);
		testDB.register("randomperson", "randompassword");
		assertTrue(testDB.checkUserExists("randomperson"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testRegisterAndLogin() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.register("dummy_user", "password"));
		assertTrue(testDB.login("dummy_user", "password"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testRegisterAndLoginCamelcase() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		assertTrue(testDB.register("duMmy_USer", "password"));
		assertTrue(testDB.login("DUMMY_USER", "password"));
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testLoginWrongPassword() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		testDB.register("user", "password");
		Boolean result = testDB.login("user", "wrongpassword");
		assertEquals(false, result);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testLoginWrongUsername() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		testDB.register("user", "password");
		Boolean result = testDB.login("wronguser", "password");
		assertEquals(false, result);
		testDB.dropAllTables();
		testDB.close();
	}
	
	@Test
	public void testDeactivate() throws Exception{
		Database testDB = new Database("test.db");
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
