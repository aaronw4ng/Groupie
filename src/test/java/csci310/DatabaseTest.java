package csci310;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
	public void testRegisterUserAlreadyExists() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		testDB.register("user", "password");
		Boolean result = testDB.register("user", "password");
		assertEquals(false, result);

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
			assertEquals("User not found!", e.getMessage());
		}
		testDB.register("TestUser", "TestPassword");
		owner_id = testDB.queryUserID("TestUser");
		assertEquals(1, owner_id);
		testDB.dropAllTables();
		testDB.close();
	}

	// test for querying proposal_id
	@Test
	public void testQueryProposalID() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
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
		List<Event> events = new ArrayList<>();
		List<Venue> venues = new ArrayList<>();
		venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
		testDB.savesDraftProposal("Test User", "My Proposal", "This is a description", invited, events, true, -1);
		proposal_id = testDB.queryProposalID("Test User", "My Proposal");
		assertEquals(1, proposal_id);
		testDB.dropAllTables();
		testDB.close();
	}

	// Basic test for saving a draft proposal
	@Test
	public void testSavesDraftProposal() throws Exception {
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
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);
		testDB.dropAllTables();
		testDB.close();
	}

	// Unable to add a draft proposal if owner is not an existing user in database
	@Test
	public void testSavesDraftProposalOwnerDoesNotExist() throws Exception {
		Database testDB = new Database("test.db");
		String title = "My Unsuccessful Proposal";
		String descript = "This is a test description for an unsuccessful proposal!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		List<Event> events = new ArrayList<>();
		List<Venue> venues = new ArrayList<>();
		venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(-1, newProposalId);
		testDB.dropAllTables();
		testDB.close();
	}

	// Updates an existing proposal
	@Test
	public void testSavesDraftExistingProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.register("Test User", "Test Password");
		testDB.register("Invitee 1", "PS 1");
		testDB.register("Invitee 2", "PS 2");
		String oldTitle = "My Old Proposal";
		String oldDescript = "This is a test description for an old version of proposal!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		List<Event> events = new ArrayList<>();
		List<Venue> venues = new ArrayList<>();
		venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
		// Add a draft proposal
		int oldProposalId = testDB.savesDraftProposal("Test User", oldTitle, oldDescript, invitees, events, true, -1);
		assertEquals(1, oldProposalId);

		// Now update that proposal with new info
		String newTitle = "My New Proposal";
		String newDescript = "This is a test description for an new version of proposal!";
		// Save the draft proposal
		int newProposalId = testDB.savesDraftProposal("Test User", newTitle, newDescript, invitees, events, false, oldProposalId);
		assertEquals(2, newProposalId);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddEventsToProposal() throws Exception {
		Database testDB = new Database("test.db");
		List<Event> events = new ArrayList<>();
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("Venue1Name", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent1", "TestURL", "TestStartDate", venues1));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("Venue2Name", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent2", "TestURL", "TestStartDate", venues2));
		Boolean status = testDB.addEventsToProposal(1, events);
		assertEquals(true, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddEventsToProposalEmptyList() throws Exception {
		Database testDB = new Database("test.db");
		// empty list of events
		List<Event> events = new ArrayList<>();
		Boolean status = testDB.addEventsToProposal(1, events);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddInviteesToProposal() throws Exception {
		Database testDB = new Database("test.db");
		List<Event> events = new ArrayList<>();
		List<Venue> venues = new ArrayList<>();
		venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
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
		List<Event> events = new ArrayList<>();
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("Venue1Name", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent1", "TestURL", "TestStartDate", venues1));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("Venue2Name", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		events.add(new Event("TestEvent2", "TestURL", "TestStartDate", venues2));
		List<String> invited = new ArrayList<>();
		Boolean status = testDB.addInviteesToProposal(1, invited, events);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testAddInviteesToProposalNoEvents() throws Exception {
		Database testDB = new Database("test.db");
		List<Event> events = new ArrayList<>();
		List<String> invited = new ArrayList<>();
		invited.add("Invitee 1");
		invited.add("Invitee 2");
		Boolean status = testDB.addInviteesToProposal(1, invited, events);
		assertEquals(false, status);
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testSendProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password");
		String title = "My Sent Proposal";
		String descript = "This is a test description for sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testSendProposalFail() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Try to send a non existing proposal
		Boolean sentStatus = testDB.sendProposal(1);
		assertEquals(false, sentStatus);
		testDB.dropAllTables();
		testDB.close();
	}

	// Delete proposal that has already been sent
	@Test
	public void testDeleteProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password");
		String title = "My Sent Proposal";
		String descript = "This is a test description for deleting a sent proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1,newProposalId);
		// Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		// Remove everything associated with this proposal
		Boolean deleteStatus = testDB.deleteProposal(newProposalId);
		assertEquals(true, deleteStatus);

		testDB.dropAllTables();
		testDB.close();
	}

	// Delete draft proposal
	@Test
	public void testDeleteProposalDraft() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password");
		String title = "My Draft Proposal";
		String descript = "This is a test description for deleting draft proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Remove everything associated with this proposal
		Boolean deleteStatus = testDB.deleteProposal(newProposalId);
		assertEquals(true, deleteStatus);

		testDB.dropAllTables();
		testDB.close();
	}

	// Try to delete a proposal that does not exist
	@Test
	public void testDeleteProposalDoesNotExist() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		// Try to remove everything associated with this proposal
		Boolean deleteStatus = testDB.deleteProposal(1);
		assertEquals(false, deleteStatus);

		testDB.dropAllTables();
		testDB.close();
	}

	// Returns true because we do not send the proposal
	@Test
	public void testIsDraft() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password");
		String title = "My Draft Proposal";
		String descript = "This is a test description for checking draft proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Confirm that this proposal is a draft
		Boolean draftStatus = testDB.isDraft(newProposalId);
		assertEquals(true, draftStatus);

		testDB.dropAllTables();
		testDB.close();
	}

	// Returns false because we do send the proposal
	@Test
	public void testIsDraftSentProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password");
		String title = "My Draft Proposal";
		String descript = "This is a test description for checking draft proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1");
		testDB.register("Invitee 2", "PS2");
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		// Confirm that this proposal is a not draft
		Boolean draftStatus = testDB.isDraft(newProposalId);
		assertEquals(false, draftStatus);

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testIsDraftProposalDoesNotExist() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Empty proposal table, so shouldn't be able to delete anything
		try {
			Boolean draftStatus = testDB.isDraft(1);
			fail();
		} catch (Exception e) {
			// expecting an error here
			assertEquals("Proposal not found!", e.getMessage());
		}
		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testGetEventsFromProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password"); // userId = 1
		String title = "Remove Invitee from Sent Proposal";
		String descript = "This is a test description for removing an invitee after sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1"); // userId = 2
		testDB.register("Invitee 2", "PS2"); // userId = 3
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		List<Event> result = testDB.getEventsFromProposal(1);
		assertNotNull(result);
		assertEquals(2, result.size());

		assertEquals(result.get(0).eventName, "Birthday");
		assertEquals(result.get(0).url, "TestURL");
		assertEquals(result.get(0).startDateTime, "TestStartDate");

		assertEquals(result.get(1).eventName, "BTS Concert");
		assertEquals(result.get(1).url, "TestURL");
		assertEquals(result.get(1).startDateTime, "TestStartDate");

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testGetInviteesFromProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password"); // userId = 1
		String title = "Remove Invitee from Sent Proposal";
		String descript = "This is a test description for removing an invitee after sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1"); // userId = 2
		testDB.register("Invitee 2", "PS2"); // userId = 3
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		List<User> result = testDB.getInviteesFromProposal(1);
		assertNotNull(result);
		assertEquals(2, result.size());

		assertEquals(result.get(0).username, "invitee 1");
		assertEquals(result.get(1).username, "invitee 2");

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testGetAllDraftProposals() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password"); // userId = 1
		String title = "Remove Invitee from Sent Proposal";
		String descript = "This is a test description for removing an invitee after sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1"); // userId = 2
		testDB.register("Invitee 2", "PS2"); // userId = 3
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		List<Proposal> result = testDB.getAllDraftProposals(1);
		assertNotNull(result);
		assertEquals(1, result.size());

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testGetAllNonDraftProposals() throws Exception{
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		testDB.register("user1", "ps1");
		testDB.register("user2", "ps2");
		testDB.register("user3", "ps3");

		// TODO

		List<Proposal> result = testDB.getAllNonDraftProposals(1);
		assertNotNull(result);
		assertEquals(0, result.size());

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testRefreshUsersAvailability() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		testDB.register("user1", "ps1");
		testDB.register("user2", "ps2");
		testDB.register("user3", "ps3");

		Date currentDate = new Date(System.currentTimeMillis());
		String currentDateString = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(currentDate);
		// set user1 to unavailable until now
		testDB.setUserAvailability(1, false, currentDateString);

		Date nextDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
		String nextDateString = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(nextDate);
		// set user2 to unavailable until next day
		testDB.setUserAvailability(2, false, nextDateString);

		Thread.sleep(100);
		List<UserAvailability> availabilities = testDB.getAllUsers(3);
		assertEquals(true, availabilities.get(0).isAvailable);
		assertEquals(false, availabilities.get(1).isAvailable);

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testSetUsersAvailability() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();

		testDB.register("user1", "ps1");
		testDB.register("user2", "ps2");
		testDB.register("user3", "ps3");

		// check if updates are successful
		Date nextDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
		String nextDateString = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(nextDate);
		assertEquals(true, testDB.setUserAvailability(1, false, nextDateString));
		assertEquals(true, testDB.setUserAvailability(2, true, ""));
		assertEquals(false, testDB.setUserAvailability(4, false, ""));

		// check availabilities changed
		List<UserAvailability> availabilities = testDB.getAllUsers(3);
		assertEquals(2, availabilities.size());
		assertEquals(1, availabilities.get(0).userId);
		assertEquals(false, availabilities.get(0).isAvailable);
		assertEquals(2, availabilities.get(1).userId);
		assertEquals(true, availabilities.get(1).isAvailable);

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testGetAllUsers() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		
		testDB.register("user1", "ps1");
		testDB.register("user2", "ps2");
		testDB.register("user3", "ps3");
		testDB.register("user4", "ps4");
		testDB.register("user5", "ps5");

		List<UserAvailability> userList = testDB.getAllUsers(1);
		assertEquals(4, userList.size());
		for (UserAvailability user : userList) {
			assertTrue(user.isAvailable);
		}

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testRemoveInviteeFromSentProposal() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password"); // userId = 1
		String title = "Remove Invitee from Sent Proposal";
		String descript = "This is a test description for removing an invitee after sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1"); // userId = 2
		testDB.register("Invitee 2", "PS2"); // userId = 3
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		// Remove the Invitee2 from the sent proposal
		assertEquals(3, testDB.queryUserID("Invitee 2"));
		Boolean status = testDB.removeInviteeFromSentProposal(1, 3);
		assertEquals(true, status);

		testDB.dropAllTables();
		testDB.close();
	}

	@Test
	public void testRemoveInviteeFromSentProposalUserDNE() throws Exception {
		Database testDB = new Database("test.db");
		testDB.dropAllTables();
		testDB.createRequiredTables();
		// Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password"); // userId = 1
		String title = "Remove Invitee from Sent Proposal";
		String descript = "This is a test description for removing an invitee after sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1"); // userId = 2
		testDB.register("Invitee 2", "PS2"); // userId = 3
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

		// Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

		// Try to remove the nonexisting user from the sent proposal
		assertEquals(3, testDB.queryUserID("Invitee 2"));
		Boolean status = testDB.removeInviteeFromSentProposal(1, 4);
		assertEquals(false, status);

		testDB.dropAllTables();
		testDB.close();
	}
}
