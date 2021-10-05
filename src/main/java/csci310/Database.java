package csci310;
import java.sql.*;
import org.ini4j.Ini;
import java.io.FileReader;
import org.mindrot.jbcrypt.*;

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
		return false;
	}

	// initialize all required tables in the database
	public Boolean createRequiredTables(){
		return false;
	}

	// drop all tables
	public Boolean dropAllTables(){
		return false;
	}
	
	// add user and hashed password to the table
	public Boolean register(String _us, String _pd) {
		return false;
	}
	
	// check if hashed password matches with stored hashed password in the DB
	public Boolean login(String _us, String _pd) {
		return false;
	}
	
	// remove the according user from table
	public Boolean deactivate(String _us, String _pd) {
		return false;
	}
}
