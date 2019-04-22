package SqlBrowserBerkay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class DbFunctions {

	public static List<String> getAllTables(String server,String db,String password, String user) throws Exception{
		List<String> dbs = new ArrayList<>();
		System.out.println("getting tables..");
		try{
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" +db ,user,password);
			System.out.println("Connected...");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("show tables;");
			System.out.println("Getting data...");
			while(rs.next()) {
				dbs.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof CommunicationsException) {
				throw new Exception("Cannot connect to server!");
			}else {
				throw new Exception("Credentials are wrong");
			}
			
			
		}
		return dbs;
	}
	
	public static List<String> getAllDbs(String server,String password, String user) throws Exception{
		List<String> dbs = new ArrayList<>();
		System.out.println("connecting");
		try{
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + server,user,password);
			System.out.println("Connected...");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("show schemas;");
			System.out.println("Getting data...");
			while(rs.next()) {
				dbs.add(rs.getString("Database"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof CommunicationsException) {
				throw new Exception("Cannot connect to server!");
			}else {
				throw new Exception("Credentials are wrong");
			}
			
			
		}
		
		return dbs;
	}
	
	public static int updateQuery(String server,String db,String password, String user,String query) throws Exception {
		
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + db,user,password);
				Statement stmt = conn.createStatement();

				){
			
			System.out.println("Connected...");
			
			return  stmt.executeUpdate(query);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("There is a problem with your query");
			
			
		}
		
		
	}
	
}
