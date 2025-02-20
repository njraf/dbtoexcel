import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	
	Connection connection;
	Statement stmt;
	ResultSet rs;

	public void DBHelper() {
		
	}
	
	public Boolean connect(String username, String password, String host, String port, String DBName) {
		String url = "jdbc:mysql://"+host+":"+port+"/"+DBName;
		try {
			connection = DriverManager.getConnection(url, username, password);
			
			if(DBName.isEmpty()) {
				System.out.println("Invalid database name");
				connection.close();
			    return false;
			}
			
			//System.out.println("Database connected!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unknown Error");
			
		    return false;
		}
		
			
		
		return true;		
	}
	
	public boolean validName(String name) throws SQLException {
		// Connection connection = <your java.sql.Connection>
		ResultSet resultSet = connection.getMetaData().getCatalogs();

		//iterate each catalog in the ResultSet
		while (resultSet.next()) {
		  // Get the database name, which is at position 1
		  if(name == resultSet.getString(1)) {
			  resultSet.close();
			  return true;
		  }
		  
		}
		resultSet.close();
		return false;
	}
	
	public ResultSet query(String q) {
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(q);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public String[] getColumnNames() throws SQLException {
		int cols = rs.getMetaData().getColumnCount();
		String[] data = new String[cols];
		
		for(int x = 0; x < cols; x++) {
			//System.out.print(rs.getMetaData().getColumnName(x+1)+", \t");
			data[x] = rs.getMetaData().getColumnName(x+1);
		}
		//System.out.println();
		
		return data;
	}
	
	public String[] getRow() throws SQLException {
		
		
		int cols = rs.getMetaData().getColumnCount();
		String[] data = new String[cols];
		
		for(int x = 0; x < cols; x++) {
			//System.out.print(rs.getString(x+1)+", \t");
			data[x] = rs.getString(x+1);
		}
		//System.out.println("data: "+data[0]);
		
		rs.next();
		
		return data;
	}
	
	public int getNumRows() throws SQLException {
		rs.last();
		int last = rs.getRow();
		rs.first();
		return last;
	}
	
	public int getNumCols() throws SQLException {
		return rs.getMetaData().getColumnCount();
	}
	
	public void close() {
		if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException sqlEx) { } // ignore

	        rs = null;
	    }

	    if (stmt != null) {
	        try {
	            stmt.close();
	        } catch (SQLException sqlEx) { } // ignore

	        stmt = null;
	    }
	}
	
	public void print() throws SQLException {
		int cols = rs.getMetaData().getColumnCount();
		
		System.out.println("---Start print");
		while(rs.next()) {
			for(int x = 0; x < cols; x++) {
				System.out.print(rs.getString(x+1)+",\t");
			}
			System.out.println();
		}
		System.out.println("---End print");
	}
}
