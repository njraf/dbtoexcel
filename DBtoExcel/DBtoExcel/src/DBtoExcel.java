import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBtoExcel {

	private static String database;
	private static String[] data;
	private static String[] colNames;
	
	private static DBHelper dbhelper;
	//private static Connection conn;
	//private static ResultSet rs;
	
	private static POIHelper poi;
	private static Scanner in;
	
	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		in = new Scanner(System.in);
		
		System.out.println("Copy which database?");
		
		database = in.nextLine();
		dbhelper = new DBHelper();
		poi = new POIHelper("C:/Users/njraf_000/Desktop/"+database+".xlsx");
		
		dbhelper.connect(database, "root", "");
		createSheets();
		
		int index = 0;
		for(String s : colNames) {
			//System.out.println("table name: "+s);
			poi.changeSheet(index);
			copyTable(s);
			index++;
		}
		
		
		dbhelper.close();
		poi.close();
	}
	
	public static void createSheets() throws SQLException, IOException {
		dbhelper.query("SHOW TABLES");
		
		int rows = dbhelper.getNumRows();
		colNames = new String[rows];
		
		for(int x = 0; x < rows; x++) {
			colNames[x] = dbhelper.getRow()[0];
			poi.createSheet(colNames[x]); //create named sheet
			//System.out.println("col: "+dbhelper.getRow()[0]);
		}
	}
	
	public static void copyTable(String table) throws SQLException, IOException {
		dbhelper.query("SELECT * FROM "+table);
		int cols = dbhelper.getNumCols();
		
		//data = new String[cols];
		
		//column names
		poi.writeRow(dbhelper.getColumnNames());
		
		//table data
		int rows = dbhelper.getNumRows();
		for(int a = 0; a < rows; a++) {
			poi.writeRow(dbhelper.getRow());
		}
		
		
	}

}
