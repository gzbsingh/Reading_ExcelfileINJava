package TimeCard_ExcelFille.TimeCard_ExcelFille;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * @param args
     */
    static List<String> nameList = new ArrayList();
    static List<Long>  date_values= new ArrayList();
    
    public static void main( String[] args )
    {
    	String path="C:\\Users\\Administrator\\Desktop\\Assignment_Timecard.xlsx";
        File file=new File(path);

        try {
    			FileInputStream input=new FileInputStream(file);
    			Workbook wb=WorkbookFactory.create(input);

    			Sheet sheet= wb.getSheetAt(0);
    			 Iterator<Row> rowIterator = sheet.iterator();
    		        
    		        if (rowIterator.hasNext()) {
    		            rowIterator.next();
    		        
    		        }
    		        	double maxHour=14;		        
    		        while (rowIterator.hasNext()) {
    		        	 Row row = rowIterator.next();
    				String positionstatus=row.getCell(1).getStringCellValue();
    				String positionId=row.getCell(0).getStringCellValue();
    				String empName=row.getCell(7).getStringCellValue();
    				String cardHour=row.getCell(4).getStringCellValue();
    		          double cardHourinDouble=parseTimeCardHour(cardHour);
    				Date timeIn = null;
    			 long cc=0;
    				Date timeOut=null;
    				Cell timeInCell = row.getCell(2);
    				Cell timeOutCell=row.getCell(3);
    				if(timeInCell.getCellType()==0) {
    				   double d = timeInCell.getNumericCellValue();
    				    cc=(long) d;
    	
    				}
    				if(timeOutCell.getCellType()==0) {
     				   double d = timeOutCell.getNumericCellValue();
     				timeOut=DateUtil.getJavaDate(d);
     				}
    				
   				if("Active".equals(positionstatus) && timeInCell.getCellType()==0) {
   				
   				   nameList.add(empName);
   				   date_values.add(cc);
   				}
   				if(cardHourinDouble>maxHour) {
   					System.out.println("Employee who has worked for more than 14 hours is--"+empName+"who worked "+cardHour);
   				}

    				
    		
    		}

    		input.close();
          //checking days
    		System.out.println("--------------------");
    		checkConsecutiveDays();
    		
    		
    		} catch (IOException e) {
    			// TODO Auto-generated catch block																																 																																							`
    			e.printStackTrace();
    		} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
    		}
			//	catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}


    }

	private static void checkConsecutiveDays() {
		  Map<String, Map<Long, Long>> consecutiveDaysCount = new HashMap();

	        for (int i = 0; i < date_values.size(); i++) {
	            String currentEmployee = nameList.get(i);
	            long currentDay = date_values.get(i);

	            consecutiveDaysCount.computeIfAbsent(currentEmployee, k -> new HashMap<>());

	            // Increment the count for the current day
	            consecutiveDaysCount.get(currentEmployee).merge(currentDay, (long) 1, Long::sum);


    
	            // Check if the employee worked for 7 consecutive days
	            if (consecutiveDaysCount.get(currentEmployee).values().stream().mapToLong(Long::intValue).sum() == 7) {
	                System.out.println("Employee who worked consecutive 7 days in Active Positions: " + currentEmployee);
	                
	          
	            }


	        }
	    				
		
	}

	

	private static void checkTimeBetweenShifts(Sheet sheet, String empName, Date timeIn, Date timeOut) {
		// TODO Auto-generated method stub
		
	}

	private static double parseTimeCardHour(String cardHour) {
		// TODO Auto-generated method stub
		if(cardHour.isBlank())
			return 0;
		String[] arr=cardHour.split(":");
		int  hours=Integer.parseInt(arr[0]);
		int minutes=Integer.parseInt(arr[1]);

		return hours+(double)minutes/60;
		
	}
}
