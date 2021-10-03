package csci310;
import java.sql.*;

public class Database {
	private static Connection connection;
	
	public Database(){
		try{
			connection = DriverManager.getConnection("jdbc:mariadb://maria_db:3306/when2meet", "root", "csci310project");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Boolean register(String _us, String _pd) {
		return false;
	}
	
	public Boolean login(String _us, String _pd) {
		return false;
	}
	
	public Boolean deactivate(String _us, String _pd) {
		return false;
	}
}
