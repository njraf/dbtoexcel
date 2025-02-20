import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DBtoExcel {

	private static String database;
	private static String[] data;
	private static String[] colNames;
	
	private static DBHelper dbhelper;
	//private static Connection conn;
	//private static ResultSet rs;
	
	private static POIHelper poi;
	
	//swing variables
	JFrame frame;
	JPanel panel;
	JPanel filePanel;
	JTextField pathText;
	JTextField hostText;
	JTextField databaseText;
	JTextField portText;
	JTextField usernameText;
	JPasswordField passwordText;
	JButton submit;
	JButton browse;
	JFileChooser chooser;
	
	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBtoExcel dbex = new DBtoExcel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	
	public DBtoExcel() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pathText = new JTextField();
		hostText= new JTextField();
		databaseText = new JTextField();
		portText = new JTextField();
		usernameText = new JTextField();
		passwordText = new JPasswordField();
		submit = new JButton("Submit");
		browse = new JButton("Browse...");
		
		panel = new JPanel();
		filePanel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		filePanel.add(new JLabel("File destination:"));
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		browse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					pathText.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		filePanel.add(browse);
		panel.add(filePanel);
		
		panel.add(pathText);
		panel.add(new JLabel("Host:"));
		panel.add(hostText);
		panel.add(new JLabel("Database name:"));
		panel.add(databaseText);
		panel.add(new JLabel("Port number:"));
		panel.add(portText);
		panel.add(new JLabel("Username:"));
		panel.add(usernameText);
		panel.add(new JLabel("Password:"));
		panel.add(passwordText);
		panel.add(submit);
		
		setDefaults();
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String path = pathText.getText();
				if(!path.endsWith("/") && !path.endsWith("\\")) {
					path += "/";
				}
				
				dbhelper = new DBHelper();
				if(!dbhelper.connect(usernameText.getText(), //if not connected
						passwordText.getText(), 
						hostText.getText(), 
						portText.getText(), 
						databaseText.getText())) {
					//dialog
					JOptionPane.showMessageDialog(null, "Could not connect.\nMake sure host name, database name, "
							+ "port number, username, and password are correct");
					
					//setDefaults();
					return;
				}
				poi = new POIHelper(path+databaseText.getText()+".xlsx");
				
				try {
					createSheets();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				int index = 0;
				for(String s : colNames) {
					//System.out.println("table name: "+s);
					poi.changeSheet(index);
					try {
						copyTable(s);
					} catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					index++;
				}
				
				
				dbhelper.close();
				poi.close();
				
				JOptionPane.showMessageDialog(null, "Success! Created " + pathText.getText() + "\\" + databaseText.getText() + ".xlsx");
				
				//setDefaults();
			}
		});
		
		frame.add(panel);
		frame.setVisible(true);
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
	
	public void setDefaults() {
		pathText.setText("");
		hostText.setText("localhost");
		databaseText.setText("");
		portText.setText("3306");
		usernameText.setText("root");
		passwordText.setText("");
	}

}
