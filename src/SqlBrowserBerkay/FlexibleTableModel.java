package SqlBrowserBerkay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class FlexibleTableModel extends AbstractTableModel{
	private Connection conn;
	private Statement stmt;
	private String query;
	private ResultSetMetaData meta;
	private ResultSet rs;
	private boolean connected = false;
	
	public FlexibleTableModel(String server, String url, String username, String password, String query) throws Exception {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ server +"/" + url,username,password);
			stmt = conn.createStatement();
			this.query = query;
			rs = stmt.executeQuery(query);
			meta = rs.getMetaData();
			connected = true;
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("There is a problem with your query");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	@Override
	public int getRowCount() {
		if(connected) {
			try {
				rs.last();
				return rs.getRow();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
		
	}

	@Override
	public int getColumnCount() {
		if(connected) {
			try {
				return  meta.getColumnCount();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(connected) {
			try {
				rs.absolute(rowIndex +1);
				return rs.getObject(columnIndex+1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return null;
	}

	@Override
	public String getColumnName(int column) {
		if(connected) {
			try {
				return meta.getColumnName(column+1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "A";
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		try {
			String name = meta.getColumnClassName(columnIndex+1 );
			return Class.forName(name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Object.class;
	}
	
	public void disconnect() {
		if(connected) {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
