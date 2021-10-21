package csci310;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.ini4j.Ini;

public class DatabaseTest {

	@Test
	public void testCloseWhenAlreadyClosed() throws Exception {
		// test default constructor since this test doesn't alter db content
		Database testDB = new Database();
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

	@Test
	public void testPersistantDatabase() throws Exception{
		Database testDB1 = new Database("test.db");
		testDB1.dropAllTables();
		testDB1.createRequiredTables();
		testDB1.register("user", "password");
		Boolean result1 = testDB1.login("user", "password");
		assertEquals(true, result1);
		testDB1.close();

		Database testDB2 = new Database("test.db");
		Boolean result2 = testDB2.login("user", "password");
		assertEquals(true, result2);
		testDB2.dropAllTables();
		testDB2.close();
	}
	
	@Test
	public void testSecureDatabase() throws Exception{
		Database testDB1 = new Database("test.db");
		testDB1.close();
		// access the database with wrong password
		Database testDB2 = null;
		Boolean errorFlag = false;
		try {
			testDB2 = new Database("test.db", "db_config.ini", "WrongPassword");
		}
		catch (Exception e){
			// expect error since the password is wrong
			errorFlag = true;
		}
		assertTrue(errorFlag);
		// try again with correct password
		testDB2 = new Database("test.db", "db_config.ini", "ThisProjectIsSoMuchFun");
		testDB2.close();
	}

	// test for querying user_id
	@Test
	public void testQueryUserID() throws Exception{
		Database testDB = new Database("test.db");
		int owner_id;
		try{
			owner_id = testDB.queryUserID("TestUser");
			fail();
		}
		catch (Exception e){
			// expecting an error here
			assertTrue(true);
		}
		testDB.register("TestUser", "TestPassword");
		owner_id = testDB.queryUserID("TestUser");
		assertTrue(owner_id >= 0);
	}

	// test for querying proposal_id
	@Test
	public void testQueryProposalID() throws Exception{
		Database testDB = new Database("test.db");
		testDB.register("Test User", "Test Password");
		int proposal_id;
		try{
			proposal_id = testDB.queryProposalID("Test User", "My Proposal");
			fail();
		}
		catch (Exception e){
			// expecting an error here
			assertEquals("Proposal not found!", e.getMessage());
		}
		List<String> invited = new ArrayList<>();
		invited.add("Invitee 1");
		// register invitee 1 as user
		testDB.register("Invitee 1", "PS1");
		List<String> events = new ArrayList<>();
		events.add("Event 1");
		testDB.createAProposal("Test User", "My Proposal", "This is a description", invited, events, false);
		proposal_id = testDB.queryProposalID("Test User", "My Proposal");
		assertEquals(1, proposal_id);
		testDB.dropAllTables();
		testDB.close();
	}

	// Basic test for creating a proposal that is not a draft --> should successfully add the proposal
	@Test
	public void testCreateAProposal() throws Exception {
		Database testDB = new Database("test.db");
		// add user to database first
		testDB.register("Test User", "Test Password");
		String title = "My Proposal";
		String descript = "This is a test description for a proposal!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		List<String> events = new ArrayList<>();
		events.add("Birthday");
		events.add("BTS Concert");
		Boolean isDraft = false;
		Boolean status = testDB.createAProposal("Test User", title, descript, invitees, events, isDraft);
		assertEquals(true, status);
		testDB.dropAllTables();
		testDB.close();
	}

	// Unable to create a proposal if owner is not an existing user in database
	@Test
	public void testCreateAProposalOwnerDoesNotExist() throws Exception {
		Database testDB = new Database("test.db");
		String title = "My Unsuccessful Proposal";
		String descript = "This is a test description for an unsuccessful proposal!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		List<String> events = new ArrayList<>();
		events.add("Birthday");
		Boolean isDraft = false;
		Boolean status = testDB.createAProposal("Test User", title, descript, invitees, events, isDraft);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddEventsToProposal() throws Exception {
		Database testDB = new Database("test.db");
		List<String> events = new ArrayList<>();
		events.add("Event 1");
		events.add("Event 2");
		Boolean status = testDB.addEventsToProposal(1, events);
		assertEquals(true, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddEventsToProposalEmptyList() throws Exception {
		Database testDB = new Database("test.db");
		List<String> events = new ArrayList<>();
		Boolean status = testDB.addEventsToProposal(1, events);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddInviteesToProposal() throws Exception {
		Database testDB = new Database("test.db");
		List<String> events = new ArrayList<>();
		events.add("Event 1");
		events.add("Event 2");
		List<String> invited = new ArrayList<>();
		invited.add("Invitee 1");
		invited.add("Invitee 2");
		invited.add("Invitee 3");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		testDB.register("Invitee 3", "PS3");
		Boolean status = testDB.addInviteesToProposal(1, invited, events);
		assertEquals(true, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddInviteesToProposalNoInvitees() throws Exception {
		Database testDB = new Database("test.db");
		List<String> events = new ArrayList<>();
		events.add("Event 1");
		events.add("Event 2");
		List<String> invited = new ArrayList<>();
		Boolean status = testDB.addInviteesToProposal(1, invited, events);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddInviteesToProposalNoEvents() throws Exception {
		Database testDB = new Database("test.db");
		List<String> events = new ArrayList<>();
		List<String> invited = new ArrayList<>();
		invited.add("Invitee 1");
		invited.add("Invitee 2");
		Boolean status = testDB.addInviteesToProposal(1, invited, events);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}
}
