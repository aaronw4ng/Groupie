package csci310;
import java.sql.*;
import java.text.SimpleDateFormat;

import org.ini4j.Ini;
import java.io.FileReader;
import org.mindrot.jbcrypt.*;
import org.sqlite.mc.SQLiteMCChacha20Config;

import java.util.*; // for StringBuilder
import java.util.Date;

public class Database {
	private static Connection connection;
	private String dbName;
	private String dbConfigFilename;
	private String dbKey;
	private Ini config;
	
	// input: database_name, configfile_name
	public Database(String a, String b, String k) throws Exception{
		dbName = a;
		dbConfigFilename = b;
		dbKey = k;
		// create connection
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbName, 
				SQLiteMCChacha20Config.getDefault().withKey(dbKey).toProperties());
		// load configuration
		String filename = "config/" + dbConfigFilename;
		config = new Ini(new FileReader(filename));
		createRequiredTables();
	}

	// input: database_name
	public Database(String a) throws Exception{
		this(a, "db_config.ini", "ThisProjectIsSoMuchFun");
	}

	// no input, default initialization for actual implementation
	public Database() throws Exception{
		this("project27.db", "db_config.ini", "ThisProjectIsSoMuchFun");
	}

	public void close() throws Exception {
		if (!connection.isClosed())
			connection.close();
		else
			throw new Exception("Invalid operation.");
	}

	// check if a particular table exists in the database
	public Boolean checkTableExists(String tableName) throws Exception{
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getTables(null, null, tableName, null);
		if (rs.next()) {
			rs.close();
			return true;
		}
		rs.close();
		return false;
	}

	// initialize all required tables in the database
	public void createRequiredTables() throws Exception{
		Statement stmt = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		for (String tableName: config.keySet()){
			sql.append("CREATE TABLE IF NOT EXISTS " + tableName + " (\n");
			Map<String, String> tableDef = config.get(tableName);
			int count = 0;
			for (String columnName: tableDef.keySet()){
				String columnSpec = tableDef.get(columnName);
				sql.append(columnName + " " + columnSpec);
				count++;
				if (count < tableDef.keySet().size()){
					sql.append(",\n");
				}
			}
			sql.append(")");
			stmt.executeUpdate(sql.toString());
			sql.setLength(0);
		}
		stmt.close();
	}

	// drop all tables
	public void dropAllTables() throws Exception{
		Statement stmt = connection.createStatement();
		for (String tableName : config.keySet()){
			stmt.executeUpdate("DROP TABLE IF EXISTS '" + tableName + "'");
		}
		stmt.close();
	}

	// check if user exists in the database
	public Boolean checkUserExists(String _us) throws Exception{
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?" );
		stmt.setString(1, _us.toLowerCase());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			stmt.close();
			rs.close();
			return true;
		}
		stmt.close();
		rs.close();
		return false;
	}
	
	// add user and hashed password to the table
	public Boolean register(String _us, String _pd) throws Exception{
		String hashed = BCrypt.hashpw(_pd, BCrypt.gensalt());
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (username, password, availability, until) VALUES(?, ?, ?, ?)");
		stmt.setString(1, _us.toLowerCase());
		stmt.setString(2, hashed);
		stmt.setBoolean(3, true);
		stmt.setString(4, "null");
		try {
			stmt.executeUpdate();
			stmt.close();
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	// check if hashed password matches with stored hashed password in the DB
	public Boolean login(String _us, String _pd) throws Exception{
		PreparedStatement stmt = connection.prepareStatement("SELECT password FROM users where username = ?");
		stmt.setString(1, _us.toLowerCase());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			String rs_password = rs.getString("password");
			stmt.close();
			rs.close();
			return BCrypt.checkpw(_pd, rs_password);
		}
		stmt.close();
		rs.close();
		return false;
	}
	
	// remove the according user from table
	public Boolean deactivate(String _us, String _pd) throws Exception{
		if (login(_us, _pd)){
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE username = ?");
			stmt.setString(1, _us.toLowerCase());
			stmt.executeUpdate();
			stmt.close();
			return true;
		}
		return false;
	}

	public int queryUserID(String owner) throws Exception{
		PreparedStatement stmt = connection.prepareStatement("SELECT user_id FROM users where username = ?");
		stmt.setString(1, owner.toLowerCase());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			int userID = rs.getInt("user_id");
			rs.close();
			stmt.close();
			return userID;
		}
		else{
			rs.close();
			stmt.close();
			throw new Exception("User not found!");
		}
	}

	public int queryProposalID(String owner, String title) throws Exception {
		int userID = queryUserID(owner);
		PreparedStatement stmt = connection.prepareStatement("SELECT proposal_id FROM proposals WHERE owner_id = ? AND title = ?");
		stmt.setInt(1, userID);
		stmt.setString(2, title);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			int proposalID = rs.getInt("proposal_id");
			rs.close();
			stmt.close();
			return proposalID;
		}
		else{
			rs.close();
			stmt.close();
			throw new Exception("Proposal not found!");
		}
	}

	// Adds a draft proposal to database without sending
	// Will delete the old version of proposal if isNew is false
	// Returns the proposalId of the newly added proposal
	public int savesDraftProposal(String owner, String title, String descript, List<String> invited, List<Event> events, Boolean isNew, int proposalId) throws Exception {
		// if this proposal is not new aka there's an older version of it, then delete that proposal
		if (!isNew) {
			deleteProposal(proposalId);
		}
		int userID;
		// if the owner exists, then try to create a proposal by using owner's user_id
		try{
			userID = queryUserID(owner);
		}
		catch (Exception e){
			// else owner does not exist, then cannot create a proposal
			System.out.println("Unable to add following proposal: " + owner + " " + title + " " + descript);
			return -1;
		}

		// insert proposal into proposals table
		String query = "INSERT INTO proposals (owner_id, is_draft, title, description) VALUES(?,?,?,?)";
		PreparedStatement pst;
		pst = connection.prepareStatement(query);
		pst.setInt(1, userID);
		pst.setString(2, "1"); // default value for is_draft is true
		pst.setString(3, title);
		pst.setString(4, descript);
		pst.executeUpdate();
		// successful add to proposal table
		System.out.println("Added proposal: " + owner + " " + title + " " + descript);

		// Try to fetch the proposal id
		int proposalID = queryProposalID(owner, title);
		// Add events to the proposal
		addEventsToProposal(proposalID, events);
		// Add invitees to proposal
		addInviteesToProposal(proposalID, invited, events);
		pst.close();
		return proposalID;
	}

	// Add Event(s) to an existing proposal
	public Boolean addEventsToProposal(int proposalId, List<Event> events) throws Exception {
		// empty list, then return false
		if (events.isEmpty()) {
			return false;
		}
		// Add list of events associated with this proposal to the events table
		for (Event e: events) {
			String query = "INSERT INTO events (proposal_id, event_name, event_link, start_date_time, venue_name, venue_address, venue_city, venue_state, venue_country) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, proposalId);
			pst.setString(2, e.getEventName());
			pst.setString(3, e.getUrl());
			pst.setString(4, e.getStartDateTime());
			// use the first venue
			List<Venue> venues = e.getVenues();
			pst.setString(5, venues.get(0).name);
			pst.setString(6, venues.get(0).address);
			pst.setString(7, venues.get(0).city);
			pst.setString(8, venues.get(0).state);
			pst.setString(9, venues.get(0).country);
			pst.executeUpdate();
			System.out.println("Add event: " + e.getEventName() + " for proposalID: " + proposalId);
			pst.close();
		}
		return true;
	}

	// Add Invitees to an existing proposal
	public Boolean addInviteesToProposal(int proposalId, List<String> invited, List<Event> events) throws Exception {
		// if events or invited are empty, then return false because nothing added to invitees table
		if (invited.isEmpty() || events.isEmpty()) {
			System.out.println("No one is invited or no events");
			return false;
		}

		// find the invitee's user id and then insert the invitee into table
		for (String invitee: invited) {
			int inviteeID = queryUserID(invitee);
			String insert = "INSERT INTO invitees (proposal_id, invitee_id) VALUES(?,?)";
			PreparedStatement pst2 = connection.prepareStatement(insert);
			pst2.setInt(1, proposalId);
			pst2.setInt(2, inviteeID);
			pst2.executeUpdate();
			System.out.println("Adding invitee: " + invitee + " for Proposal Id: " + proposalId);
			pst2.close();
		}

		return true;
	}

	// Sends proposal out to the invitees by marking it as not a draft and initializing responses for each invitee for each event
	// Assumes that the proposal already exists in the database
	public Boolean sendProposal(int proposalId) throws Exception {
		// update is draft attribute to false
		PreparedStatement stmt1 = connection.prepareStatement("UPDATE proposals SET is_draft = 0 where proposal_id = ?");
		stmt1.setInt(1, proposalId);;
		int rowsAffected = stmt1.executeUpdate();
		stmt1.close();
		// should only affect one row; otherwise, did not successfully send proposal
		if (rowsAffected != 1) {
			return false;
		}
		// Get all the events associated with this proposal
		PreparedStatement stmt2 = connection.prepareStatement("SELECT event_id FROM events WHERE proposal_id = ?");
		stmt2.setInt(1, proposalId);;
		ResultSet eventsRS = stmt2.executeQuery();
		List<Integer> eventIDs = new ArrayList<>();
		// Get list of event ids
		while (eventsRS.next()) {
			eventIDs.add(eventsRS.getInt("event_id"));
		}
		eventsRS.close();
		stmt2.close();
		// Get all invitees associated with this proposal
		PreparedStatement stmt3 = connection.prepareStatement("SELECT invitee_id FROM invitees WHERE proposal_id = ?");
		stmt3.setInt(1, proposalId);
		ResultSet inviteesRS = stmt3.executeQuery();
		// Get list of invitee ids
		List<Integer> inviteesIDs = new ArrayList<>();
		while (inviteesRS.next()) {
			inviteesIDs.add(inviteesRS.getInt("invitee_id"));
		}
		inviteesRS.close();
		stmt3.close();
		// initialize a response for each event, invitee combination in the responses table
		// Note: availability and excitement will be NULL
		for (int event: eventIDs) {
			for (int invitee: inviteesIDs) {
				PreparedStatement stmt4 = connection.prepareStatement("INSERT INTO responses (proposal_id, event_id, user_id) VALUES(?, ?,?)");
				stmt4.setInt(1, proposalId);
				stmt4.setInt(2, event);
				stmt4.setInt(3, invitee);
				stmt4.executeUpdate();
				stmt4.close();
				System.out.println("Initialize response for event " + event + " for invitee " + invitee);
			}
		}
		System.out.println("Sent proposal: " + proposalId);
		return true;
	}

	// Returns the status of proposal being a draft or not
	public Boolean isDraft(int proposalId) throws Exception {
		PreparedStatement stmt = connection.prepareStatement("SELECT is_draft FROM proposals where proposal_id = ?");
		stmt.setInt(1, proposalId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			Boolean isDraft = rs.getBoolean("is_draft");
			rs.close();
			stmt.close();
			return isDraft;
		}
		else {
			rs.close();
			stmt.close();
			System.out.println("isDraft failed");
			throw new Exception("Proposal not found!");
		}
	}

	// Should delete anything related to the proposal in the database
	// If draft, should delete items in following tables: proposals, events, invitees
	// If sent, should delete the items in above tables and responses
	public Boolean deleteProposal(int proposalId) throws Exception {
		System.out.println("Deleting proposal: " + proposalId);
		// Delete invitees
		PreparedStatement inviteesStmt = connection.prepareStatement("DELETE FROM invitees WHERE proposal_id = ?");
		inviteesStmt.setInt(1, proposalId);
		inviteesStmt.executeUpdate();

		// Delete events
		PreparedStatement eventsStmt = connection.prepareStatement("DELETE FROM events WHERE proposal_id = ?");
		eventsStmt.setInt(1, proposalId);
		eventsStmt.executeUpdate();

		// Delete responses if not draft; note that this is ok to do even if no responses for proposal exists
		PreparedStatement responsesStmt = connection.prepareStatement("DELETE FROM responses WHERE proposal_id = ?");
		responsesStmt.setInt(1, proposalId);
		responsesStmt.executeUpdate();

		// Delete proposals
		PreparedStatement proposalsStmt = connection.prepareStatement("DELETE FROM proposals WHERE proposal_id = ?");
		proposalsStmt.setInt(1, proposalId);
		int rowsAffected = proposalsStmt.executeUpdate();
		// Check that one proposal was deleted from the database
		if (rowsAffected == 1) {
			return true;
		}
		// Otherwise, something went wrong (e.g. none were deleted, more than one proposal deleted)
		return false;
	}

	// Returns a list of events associated with the proposalId
	public List<Event> getEventsFromProposal(int proposalId) throws Exception {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM events WHERE proposal_id = ?");
		stmt.setString(1, String.valueOf(proposalId));
		ResultSet rs = stmt.executeQuery();
		List<Event> events = new ArrayList<>();
		while (rs.next()) {
			Event event = new Event();
			event.eventId = rs.getInt("event_id");
			event.eventName = rs.getString("event_name");
			event.url = rs.getString("event_link");
			event.startDateTime = rs.getString("start_date_time");
			// get venue
			List<Venue> venues = new ArrayList<Venue>();
			Venue venue = new Venue(
					rs.getString("venue_name"),
					rs.getString("venue_address"),
					rs.getString("venue_city"),
					rs.getString("venue_state"),
					rs.getString("venue_country")
			);
			venues.add(venue);
			event.venues = venues;
			events.add(event);

			// test print 
			System.out.println("Event: " + event.eventName + " " + event.startDateTime);
		}
		rs.close();
		stmt.close();
		return events;
	}

	// Returns a list of invitees associated with the proposalId
	List<User> getInviteesFromProposal(int proposalId) throws Exception {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM invitees WHERE proposal_id = ?");
		stmt.setString(1, String.valueOf(proposalId));
		ResultSet rs = stmt.executeQuery();
		List<User> invitees = new ArrayList<>();
		while (rs.next()) {
			User invitee = new User();
			invitee.userId = rs.getInt("invitee_id");
			invitees.add(invitee);
			// test print
			System.out.println("Invitee ID: " + invitee.userId);
		}
		rs.close();
		stmt.close();
		for (User invitee: invitees) {
			PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
			stmt2.setString(1, String.valueOf(invitee.userId));
			ResultSet rs2 = stmt2.executeQuery();
			invitee.username = rs2.getString("username");

			rs2.close();
			stmt2.close();

			// test print
			System.out.println("Invitee Username: " + invitee.username);
		}
		return invitees;
	}

	// Returns a list of all draft proposals that belongs to the user
	public List<Proposal> getAllDraftProposals(int userId) throws Exception {
		List<Proposal> proposals = new ArrayList<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM proposals WHERE owner_id = ? AND is_draft = 1");
		stmt.setString(1, String.valueOf(userId));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Proposal proposal = new Proposal();
			proposal.proposalId = rs.getInt("proposal_id");
			proposal.title = rs.getString("title");
			proposal.description = rs.getString("description");
			proposal.isDraft = rs.getBoolean("is_draft");
			proposals.add(proposal);

			// test print
			System.out.println("Proposal ID: " + proposal.proposalId);
			System.out.println("Proposal Title: " + proposal.title);
		}
		rs.close();
		stmt.close();
		// add details for each events
		for (Proposal proposal: proposals) {
			proposal.events = getEventsFromProposal(proposal.proposalId);
			proposal.invitees = getInviteesFromProposal(proposal.proposalId);
		}
		return proposals;
	}

	// Returns a list of all non-draft proposals
	public List<Proposal> getAllNonDraftProposals(int userId, Boolean isOwner) throws Exception {
		List<Proposal> proposals = new ArrayList<>();
		if (isOwner){
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM proposals WHERE owner_id = ? AND is_draft = 0");
			stmt.setString(1, String.valueOf(userId));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Proposal proposal = new Proposal();
				proposal.proposalId = rs.getInt("proposal_id");
				proposal.title = rs.getString("title");
				proposal.description = rs.getString("description");
				proposal.isDraft = rs.getBoolean("is_draft");
				proposals.add(proposal);

				// test print
				System.out.println("Proposal ID: " + proposal.proposalId);
				System.out.println("Proposal Title: " + proposal.title);
			}
			rs.close();
			stmt.close();
		}
		else{
			// get all proposals that the user is invited to
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM invitees WHERE invitee_id = ?");
			stmt.setString(1, String.valueOf(userId));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Proposal proposal = new Proposal(new User(""));
				proposal.proposalId = rs.getInt("proposal_id");
				proposals.add(proposal);
			}
			rs.close();
			stmt.close();

			// get more details for each proposal
			for (Proposal proposal: proposals) {
				PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM proposals WHERE proposal_id = ?");
				stmt2.setString(1, String.valueOf(proposal.proposalId));
				ResultSet rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					proposal.title = rs2.getString("title");
					proposal.description = rs2.getString("description");
					proposal.isDraft = rs2.getBoolean("is_draft");
				}
				rs2.close();
				stmt2.close();

				// test print
				System.out.println("Proposal ID: " + proposal.proposalId);
				System.out.println("Proposal Title: " + proposal.title);
			}
		}

		// add details for each events
		for (Proposal proposal: proposals) {
			proposal.events = getEventsFromProposal(proposal.proposalId);
			proposal.invitees = getInviteesFromProposal(proposal.proposalId);
		}

		// TODO: check for finalized proposals and return declined & accepted results
		return proposals;
	}

	// block or unblock blocked_user_id by user_id
	public Boolean setBlockUser(boolean block, int userId, int blockedUserId) throws Exception {
		try{
			PreparedStatement stmt = block ? connection.prepareStatement("INSERT INTO blocklist (user_id, blocked_user_id) VALUES (?, ?)") :
					connection.prepareStatement("DELETE FROM blocklist WHERE user_id = ? AND blocked_user_id = ?");
			stmt.setInt(1, userId);
			stmt.setInt(2, blockedUserId);
			int rowsAffected = stmt.executeUpdate();
			stmt.close();
			System.out.println("Set Block Status: " + userId + " - " + blockedUserId + " block:" + block);
			return rowsAffected == 1;
		}
		catch (Exception e){
			// update failed, potentially due to duplicate entry
			if (e.getMessage().contains("constraint")){
				return false;
			}
			else{
				throw e;
			}
		}
	}

	// all user/availibility related functions
	// returns if the any changes were made to the database
	// if setting availability to true, the "until" field doesn't matter
	public Boolean setUserAvailability(int userId, Boolean availability, String until) throws Exception {
		// update availability
		PreparedStatement stmt1 = connection.prepareStatement("UPDATE users SET availability = ?, until = ? WHERE user_id = ?");
		stmt1.setBoolean(1, availability);
		stmt1.setString(2, until);
		stmt1.setInt(3, userId);
		int rowsAffected = stmt1.executeUpdate();
		stmt1.close();
		// should only affect one row; otherwise, did not successfully update availability
		if (rowsAffected != 1) {
			return false;
		}
		System.out.println("Updated availability for user: " + userId + " to " + availability + " until " + until);
		return true;
	}

	// refresh all users' availability by checking for until and current timestamp
	public void refreshUsersAvailability() throws Exception {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE availability = ?");
		stmt.setBoolean(1, false);
		ResultSet rs = stmt.executeQuery();
		// Get a buffer list of user ids whose unavailability has expired
		List<Integer> expired = new ArrayList<Integer>();
		while (rs.next()) {
			String until = rs.getString("until");
			// if until is not null, check if current time is after until
			if (new Date(System.currentTimeMillis()).after(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(until))) {
				// if current time is after until, add user to expired availability list
				expired.add(rs.getInt("user_id"));
			}
		}
		rs.close();
		stmt.close();
		// Go through the buffer list and mark the users as available
		for (int user:expired) {
			PreparedStatement stmt2 = connection.prepareStatement("UPDATE users SET availability = ? WHERE user_id = ?");
			stmt2.setBoolean(1, true);
			stmt2.setInt(2, user);
			stmt2.executeUpdate();
			stmt2.close();
		}
	}

	// returns a list of all the users in the database
	public List<UserAvailability> getAllUsers(int myId) throws Exception {
		// refresh users' availability before getting all users
		refreshUsersAvailability();

		List<UserAvailability> users = new ArrayList<UserAvailability>();
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			int userId = rs.getInt("user_id");
			// skip your own user
			if (userId == myId) {
				continue;
			}
			String userName = rs.getString("username");
			boolean isAvailable = rs.getBoolean("availability");
			UserAvailability u = new UserAvailability(userName, userId, isAvailable);
			users.add(u);
			// System.out.println(userId + " " + userName + " " + isAvailable + " ");
		}
		rs.close();
		stmt.close();
		// set whoever blocked me as unavailable
		System.out.println("Showing UserList based on UserID " + myId);
		for (UserAvailability u:users) {
			PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM blocklist WHERE user_id = ? AND blocked_user_id = ?");
			stmt2.setInt(1, u.userId);
			stmt2.setInt(2, myId);
			ResultSet rs2 = stmt2.executeQuery();
			if (rs2.next()) {
				u.isAvailable = false;
			}
			rs2.close();
			stmt2.close();

			// test print
			System.out.println("Availability: " + u.userId + " - " + u.isAvailable);
		}
		return users;
  }
  
	// Removes an invitee from a sent proposal
	public Boolean removeInviteeFromSentProposal(int proposalId, int userId) throws Exception {
		System.out.println("Trying to remove " + userId + " from proposal id " + proposalId);
		// Remove user ID from invitee list
		PreparedStatement inviteesStmt = connection.prepareStatement("DELETE FROM invitees WHERE proposal_id = ? AND invitee_id = ?");
		inviteesStmt.setInt(1, proposalId);
		inviteesStmt.setInt(2, userId);
		int inviteesRowsAffected = inviteesStmt.executeUpdate();
		System.out.println("Rows affected from removing invitee from invitees: " + inviteesRowsAffected);
		// Check that only one invitee was deleted from the database
		if (inviteesRowsAffected != 1) {
			return false;
		}

		// Remove user responses that correspond to that proposal ID and user ID
		PreparedStatement responsesStmt = connection.prepareStatement("DELETE FROM responses WHERE proposal_id = ? AND user_id = ?");
		responsesStmt.setInt(1, proposalId);
		responsesStmt.setInt(2, userId);
		int rows = responsesStmt.executeUpdate();
		System.out.println("Rows affected from removing responses: " + rows);

		return true;
	}
}
