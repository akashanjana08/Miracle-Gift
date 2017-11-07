package wheretospeach.ndm.com.wheretospeach.general;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalanderFormat {
	
	
	public static String getDateAndMonthCalender()
	  {
		//Calendar c = Calendar.getInstance();
	    return new SimpleDateFormat("MMM dd, yyyy").format(new Date());
	  }
	 public static String getDashCalender()
	  {
		 //Calendar c = Calendar.getInstance();
	    return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	  }
	 public static String getSlashCalender()
	  {
		 //Calendar c = Calendar.getInstance();
	    return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	  }
	 
	 public static String getDateTimeCalender()
	  {
		 //Calendar c = Calendar.getInstance();
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"+".000").format(new Date());
	  }
	 
	 public static String getTimeCalender()
	  {
		 //Calendar c = Calendar.getInstance();
	    return new SimpleDateFormat("HH:mm").format(new Date());
	  }

	public static String getTimeHourCalender()
	{
		//Calendar c = Calendar.getInstance();
		return new SimpleDateFormat("HH").format(new Date());
	}


	public static String getYear()
	{
		//Calendar c = Calendar.getInstance();
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	public static String dateFormatConverter(String date_time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date testDate = null;
		try {
			testDate = sdf.parse(date_time);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy /  hh:mm");
		date_time = formatter.format(testDate);

		return date_time;
	}

}
