package csci310;
import java.sql.*;
import org.ini4j.Ini;
import java.io.FileReader;
import org.mindrot.jbcrypt.*;
import java.util.*; // for StringBuilder

public class Database {
	private static Connection connection;
	public static String dbConfigFilename = "db_config.ini";
	private static Ini config;
	
	public Database(String f){
		try{
			// create connection
			connection = DriverManager.getConnection("jdbc:mariadb://maria_db:3306/project27", "root", "csci310project");
			// load configuration
			String filename = "config/" + f;
			config = new Ini(new FileReader(filename));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public Database(){
		this(dbConfigFilename);
	}

	// check if a particular table exists in the database
	public Boolean checkTableExists(String tableName) {
		try{
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	// initialize all required tables in the database
	public Boolean createRequiredTables(){
		try{
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
				// System.out.println(sql.toString());
				stmt.executeUpdate(sql.toString());
				sql.setLength(0);
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	// drop all tables
	public Boolean dropAllTables(){
		try{
			Statement stmt = connection.createStatement();
			for (String tableName : config.keySet()){
				stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			}
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	// check if user exists in the database
	public Boolean checkUserExists(String _us) {
		return false;
	}
	
	// add user and hashed password to the table
	public Boolean register(String _us, String _pd) {
		try{
			Statement stmt = connection.createStatement();
			StringBuilder sql = new StringBuilder();
			String hashed = BCrypt.hashpw(_pd, BCrypt.gensalt());
			sql.append("INSERT INTO users (username, password) VALUES ('" + _us.toLowerCase() + "', '" + hashed + "')");
			stmt.executeUpdate(sql.toString());
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	// check if hashed password matches with stored hashed password in the DB
	public Boolean login(String _us, String _pd) {
		try{

		}
		catch(Exception e){
			
		}
		return false;
	}
	
	// remove the according user from table
	public Boolean deactivate(String _us, String _pd) {
		try{

		}
		catch(Exception e){
			
		}
		return false;
	}
}
