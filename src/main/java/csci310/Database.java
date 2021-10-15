package csci310;
import java.sql.*;
import org.ini4j.Ini;
import java.io.FileReader;
import org.mindrot.jbcrypt.*;
import java.util.*; // for StringBuilder

public class Database {
	private static Connection connection;
	private static String dbName = "project27.db";
	private static String dbConfigFilename = "db_config.ini";
	private static Ini config;
	
	// input: database_name, configfile_name
	public Database(String a, String b) throws Exception{
		// create connection
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + a);
		// load configuration
		String filename = "config/" + b;
		config = new Ini(new FileReader(filename));
		createRequiredTables();
	}

	// input: database_name
	public Database(String a) throws Exception{
		this(a, dbConfigFilename);
	}

	// no input, default initialization for actual implementation
	public Database() throws Exception{
		this(dbName, dbConfigFilename);
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
}
