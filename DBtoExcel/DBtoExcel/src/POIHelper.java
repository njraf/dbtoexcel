import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIHelper {

	Workbook wb;
	Sheet sheet;
	Row row;
	Cell cell;
	FileOutputStream os;
	
	public POIHelper() {
		
	}
	
	public POIHelper(String name) {
		open(name);
	}
	
	public void open(String name) {
		try {
			os = new FileOutputStream(name);
			wb = new XSSFWorkbook();
			//wb.write(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			wb.write(os);
			row = null;
			sheet = null;
			os.close();
			wb.close();
			wb = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeRow(String[] data) throws IOException {
		//search for empty row
		row = sheet.createRow(sheet.getLastRowNum()+1);
		
		//write to row
		int index = 0;
		for(String d : data) {
			cell = row.createCell(index);
			if(isInt(d)) {
				cell.setCellValue(Integer.parseInt(d));
			} else if(isFloat(d)) {
				cell.setCellValue(Float.parseFloat(d));
			} else {
				cell.setCellValue(d);
			}
			index++;
		}
	}
	
	public void createSheet() {
		sheet = wb.createSheet();
	}
	
	public void createSheet(String s) {
		sheet = wb.createSheet(s);
	}
	
	public void changeSheet(int s) {
		sheet = wb.getSheetAt(s);
	}
	
	public boolean isInt(String s) {
		   try
		   {
		      Integer.parseInt(s);
		      return true;
		   }
		   catch(Exception e)
		   {
			   return false;
		   }
	}
	
	public boolean isFloat(String s) {
		   try
		   {
		      Float.parseFloat(s);
		      return true;
		   }
		   catch(Exception e)
		   {
			   return false;
		   }
	}
	
}
