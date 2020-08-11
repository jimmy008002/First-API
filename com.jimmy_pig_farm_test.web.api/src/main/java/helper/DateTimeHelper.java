package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
	private final static SimpleDateFormat ORACLE_DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a"); 
	private final static SimpleDateFormat DATE_TIME_MILLISEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private final static SimpleDateFormat DATE_TIME_SEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat DATE_TIME_1= new SimpleDateFormat("yyyyMMddHHmmssSSSSSSSSS");
	private final static SimpleDateFormat MYSQL_DEFAULT_CURRENT_TIMESTAMP_FORMAT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Date NOW_DATE = new Date();
	private Date transform_date = new Date();
	
	public String getDateTimeMilliSec(){  
	    String strDate = DATE_TIME_MILLISEC.format(new Date());
	    return strDate;
	}
	
	public String milliSecToDateTime(String time) {
		Date date;
		String dateString = "";;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time);
			dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateString;
	}
	
	
	
	public String getOracleCurrentTimeStamp(){  
	    String strDate = MYSQL_DEFAULT_CURRENT_TIMESTAMP_FORMAT.format(new Date());
	    return strDate;
	}
	
	
	
	public String getNewFileNameByDateTime() {
		
		//ZonedDateTime startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
		//long todayMillis1 = startOfToday.toEpochSecond() * 1000;
		
	    String strDate = DATE_TIME_1.format(new Date());
	    //return String.valueOf(todayMillis1);
	    return strDate;
	}
	
	public Date getCurrentDateTime() {
		return NOW_DATE;
	}
	
	public String getDateTimeSec() {
	    String strDate = DATE_TIME_SEC.format(new Date());
	    return strDate;
	}
	
	public void addDate(Date old_date, int count) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(old_date);    
		cal.add(Calendar.MONTH, + count);    
		this.transform_date = cal.getTime();
	}
	
	
	
	public Date getTransform_date() {
		return transform_date;
	}

	public void setTransform_date(SimpleDateFormat format, String date) {
		try {	
			this.transform_date = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toOracleCurrentTimeStamp(String date){ 
		//Date date = 
	    String strDate = "";
		try {
			strDate = ORACLE_DEFAULT_TIMESTAMP_FORMAT.format(DATE_TIME_SEC.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return strDate;
	}
	
	public String toOracleCurrentTimeStamp(Date date){ 
	    String strDate = ORACLE_DEFAULT_TIMESTAMP_FORMAT.format(date);
	    return strDate;
	}
	
	
	public String stringToOracleCurrentTimeStamp(String time){ 
		Date date = null;
	
		try {
			date = DATE_TIME_SEC.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String strDate = "";
		
		if(date != null) {
			strDate = ORACLE_DEFAULT_TIMESTAMP_FORMAT.format(date);
		} 
		
	    return strDate;
	}
	
}
