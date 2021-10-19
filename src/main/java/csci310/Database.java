package csci310;
import java.sql.*;
import org.ini4j.Ini;
import java.io.FileReader;
import org.mindrot.jbcrypt.*;
import org.sqlite.mc.SQLiteMCChacha20Config;

import java.util.*; // for StringBuilder

public class Database {
	private static Connection connection;
	private static String dbName = "project27.db";
	private static String dbConfigFilename = "db_config.ini";
	private static String dbKey = "ThisProjectIsSoMuchFun";
	private static Ini config;
	
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
		this(a, dbConfigFilename, dbKey);
	}

	// no input, default initialization for actual implementation
	public Database() throws Exception{
		this(dbName, dbConfigFilename, dbKey);
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
	public Boolean createRequiredTables() throws Exception{
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
		return true;
	}

	// drop all tables
	public Boolean dropAllTables() throws Exception{
		Statement stmt = connection.createStatement();
		for (String tableName : config.keySet()){
			stmt.executeUpdate("DROP TABLE IF EXISTS '" + tableName + "'");
		}
		stmt.close();
		return true;
	}

	// check if user exists in the database
	public Boolean checkUserExists(String _us) throws Exception{
		Statement stmt = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM users WHERE username = '" + _us.toLowerCase() + "'");
		ResultSet rs = stmt.executeQuery(sql.toString());
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
		Statement stmt = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		String hashed = BCrypt.hashpw(_pd, BCrypt.gensalt());
		sql.append("INSERT INTO users (username, password) VALUES ('" + _us.toLowerCase() + "', '" + hashed + "')");
		stmt.executeUpdate(sql.toString());
		stmt.close();
		return true;
	}
	
	// check if hashed password matches with stored hashed password in the DB
	public Boolean login(String _us, String _pd) throws Exception{
		Statement stmt = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT password FROM users where username = '" + _us.toLowerCase() + "'");
		ResultSet rs = stmt.executeQuery(sql.toString());
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
			Statement stmt = connection.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM users WHERE username = '" + _us.toLowerCase() + "'");
			stmt.executeUpdate(sql.toString());
			stmt.close();
			return true;
		}
		return false;
	}

	// create a proposal (note: draft proposal will have default values)
	// returns true if proposal was successfully added; otherwise, returns false
	public Boolean createAProposal(String owner, String title, String descript, List<String> invited, List<String> events, Boolean is_Draft) throws  Exception {
		Statement stmt = connection.createStatement();
		StringBuilder sql = new StringBuilder();

		// find the owner's user_id from users table
		sql.append("SELECT user_id FROM users where username = '" + owner.toLowerCase() + "'");
		ResultSet rs = stmt.executeQuery(sql.toString());
		int userID;
		// if the owner exists, then try to create a proposal by using owner's user_id
		if (rs.next()){
			userID = rs.getInt("user_id");
		}
		// else owner does not exist, then cannot create a proposal
		else {
			rs.close();
			stmt.close();
			System.out.println("Unable to add following proposal: " + owner + " " + title + " " + descript);
			return false;
		}

		// insert proposal into proposals table
		String query = "INSERT INTO proposals (owner_id, is_draft, title, description) VALUES(?,?,?,?)";
		PreparedStatement pst;
		pst = connection.prepareStatement(query);
		pst.setString(1, String.valueOf(userID));
		pst.setString(2, String.valueOf(is_Draft));
		pst.setString(3, title);
		pst.setString(4, descript);
		pst.executeUpdate();
		// successful add to proposal table
		System.out.println("Added proposal: " + owner + " " + title + " " + descript);

		// Try to fetch the proposal id
		String fetchProposalID = "SELECT proposal_id FROM proposals WHERE owner_id = " + userID + " AND title = '" + title + "'";
		pst = connection.prepareStatement(fetchProposalID);
		rs = pst.executeQuery();

		// TODO
		int proposalID = 0;
		// found the proposal id
		if (rs.next()) {
			proposalID = rs.getInt("proposal_id");
		}
		// Add events to the proposal
		addEventsToProposal(proposalID, events);

		// TODO
		// Add invitees to proposal
		addInviteesToProposal(proposalID, invited, events);

		rs.close();
		pst.close();
		stmt.close();
		return true;
	}

	// Add Event(s) to an existing proposal
	public Boolean addEventsToProposal(int proposalId, List<String> events) throws Exception {
		// empty list, then return false
		if (events.isEmpty()) {
			return false;
		}
		// Add list of events associated with this proposal to the events table
		for (String e: events) {
			String query = "INSERT INTO events (proposal_id, event_link) VALUES (?,?)";
			PreparedStatement pst1 = connection.prepareStatement(query);
			pst1.setString(1, String.valueOf(proposalId));
			pst1.setString(2, e);
			pst1.executeUpdate();
			System.out.println("Add event: " + e + " for proposalID: " + proposalId);
			pst1.close();
		}
		return true;
	}

	// Add Invitees to an existing proposal
	public Boolean addInviteesToProposal(int proposalId, List<String> invited, List<String> events) throws Exception {
		// if events or invited are empty, then return false because nothing added to invitees table
		if (invited.isEmpty() || events.isEmpty()) {
			System.out.println("No one is invited or no events");
			return false;
		}
		for (String e: events) {
			// find event id
			String query = "SELECT event_id FROM events WHERE event_link = '" + e + "'";
			PreparedStatement pst2 = connection.prepareStatement(query);
			ResultSet rs = pst2.executeQuery();
			int eventID = 0;
			if (rs.next()) {
				eventID = rs.getInt("event_id");
			}
			rs.close();
			pst2.close();

			// find the invitee's user id
			for (String invitee: invited) {
				query = "SELECT user_id FROM users WHERE username = '" + invitee.toLowerCase() + "'";
				PreparedStatement pst3 = connection.prepareStatement(query);
				ResultSet rs2 = pst3.executeQuery();
				int inviteeID = 0;
				if (rs2.next()) {
					inviteeID = rs2.getInt("user_id");
				}
				rs2.close();
				pst3.close();
				String insert = "INSERT INTO invitees (proposal_id, invitee_id, event_id) VALUES(?,?,?)";
				PreparedStatement pst4 = connection.prepareStatement(insert);
				pst4.setString(1, String.valueOf(proposalId));
				pst4.setString(2, String.valueOf(inviteeID));
				pst4.setString(3, String.valueOf(eventID));
				pst4.executeUpdate();
				System.out.println("Adding invitee: " + invitee + " for Event: " + e + " for Proposal Id: " + proposalId);
				pst4.close();
			}
		}

		return true;
	}
}
