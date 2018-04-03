package com.lxr.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static final String Y ="yyyy";
	
	public static final String YMD = "yyyyMMdd";
	
	public static final String YMDHMS = "yyyyMMdd HH:mm:ss";
	
	public static final String Y_M_D = "yyyy-MM-dd";
	
	public static final String Y_M_DHMS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YMDHMSS="yyyyMMddhhmmssSSS";
	
	
	
	/**
	* Description:格式化日期成字符串    
	* @Title: formatDate  
	 */
	public static String formatDate(Date date,String pattern)
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	* Description:将格式日期的字符串解析成Date    
	* @Title: parseDate  
	 */
	public static Date parseDate(String dateStr,String pattern)
	{
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* Description:计算两个时间的时间差    
	* @Title: getIntervalForTwoDate  
	 */
	public static String getIntervalForTwoDate(Date startDate,Date endDate)
	{
		if(null!=startDate&&null!=endDate)
		{
			long t1 = startDate.getTime();
			long t2 = endDate.getTime();
			int hours=(int) ((t2 - t1)/3600000);
	        int minutes=(int) (((t2 - t1)/1000-hours*3600)/60);
	        int second=(int) ((t2 - t1)/1000-hours*3600-minutes*60);
	        return ""+hours+"小时"+minutes+"分"+second+"秒";
		}
		return "";
	}

	/**
	* Description:按calendar的类型获取时间数    
	* @Title: getDateField  
	 */
	public static int getDateField(Date date,int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * 计算两个时间的相差年数
	* Description:    
	* @Title: getYearsBetweenDate  
	 */
	public static int getYearsBetweenDate(Date begin,Date end){
		int bYear = getDateField(begin,Calendar.YEAR);
		int eYear = getDateField(end,Calendar.YEAR);
		return eYear - bYear;
	}
	
	/**
	* Description:计算两个时间相差的天数    
	* @Title: getDaysBetweenDate  
	 */
	public static int getDaysBetweenDate(Date begin,Date end){
		int bDay = getDateField(begin,Calendar.DAY_OF_YEAR);
		int eDay = getDateField(end,Calendar.DAY_OF_YEAR);
		return eDay - bDay;
	}



	/**
	* Description:获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）    
	* @Title: getSpecficWeekEnd  

	 */
	public static Date getSpecficWeekEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getDayEnd(cal.getTime());
	}



	/**
	* Description:拿到当前月的最后一天    
	* @Title: getLastDayOfMonth  

	 */
	public static Date getLastDayOfMonth(){
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  
		 return calendar.getTime();
	}
	
	/**
	* Description:拿到当前月的第一天    
	* @Title: getLastDayOfMonth  

	 */
	public static Date getFirstDayOfMonth(){
		Calendar cale = Calendar.getInstance();
	    cale.add(Calendar.MONTH, 0);
	    cale.set(Calendar.DAY_OF_MONTH, 1);
	    
		 return cale.getTime();
	}
	
	
	/**
	* Description:判断传入的日期是否是周末    
	* @Title: isWeek  
	 */
	public static boolean isWeek(Date currentDate) {  
		int currentDay = 9  ;  
		if(null!=currentDate){
 			currentDay = currentDate.getDay();
		}  
		if(currentDay==0||currentDay==6)  {  
			return true ;  
		}  
		return false; 
	} 
	
	/**
	 * 将未指定格式的日期字符串转化成
	* Description:    
	* @Title: parseStringToDate  
	 */
    public static Date parseStringToDate(String date) {  
        Date result = null;  
        String parse = date;  
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");  
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");  
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");  
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");  
        DateFormat format = new SimpleDateFormat(parse);  
        try {
			result = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        return result;  
    } 

    public  static long getNextTime(int timeUnit, int interval, long timeMill)
      {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(timeMill);
        switch (timeUnit) {
        case 0:
          ca.add(13, interval);
          break;
        case 1:
          ca.add(12, interval);
          break;
        case 2:
          ca.add(10, interval);
          break;
        case 3:
          ca.add(5, interval);
          break;
        case 4:
          ca.add(2, interval);
          break;
        default:
          return 0L;
        }
        return ca.getTimeInMillis();
      }
    
    public static Date getMillis(Date startTime,int timeMill){
		 Calendar cal = Calendar.getInstance();
		 cal.setTimeInMillis(startTime.getTime());
		 cal.add(Calendar.MINUTE,timeMill);
		return cal.getTime();
    }
    
    /**
    * Description:获得指定日期的几天     
    * @Title: getSpecifiedDayAfter  

     */
    public static Date getSpecifiedDayAfter(Date specifiedDate,int number){ 
		Calendar c = Calendar.getInstance();     
		c.setTime(specifiedDate); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+number); 
		
		return c.getTime(); 
    } 
 
    /**
    * Description:两个时间相差的秒数    
    * @Title: getSecondDiff  

     */
    public static Long getSecondDiff(Date startTime,Date endTime){
        long start = startTime.getTime();
        long end = endTime.getTime();
        return end - start;

    }
    
	/**
	* Description:获取date年后(前)的amount年的第一天的开始时间    
	* @Title: getSpecficYearStart  
	 */
	public static Date getYearStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getDayStart(cal.getTime());
	}

	/**
	* Description:获取date年后的amount年的最后一天的终止时间    
	* @Title: getSpecficYearEnd  
	 */
	public static Date getYearEnd(Date date,int amount) {
		Date temp = getDayStart(getYearStart(date,amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getDayEnd(cal.getTime());
	}

	/**
	* 获取date月后的amount月的第一天的开始时间     
	 */
	public static Date getMonthStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getDayStart(cal.getTime());
	}

	/**
	* Description:获取当前自然月后的amount月的最后一天的终止时间    
	* @Title: getSpecficMonthEnd  

	 */
	public static Date getMonthEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getMonthStart(date,amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getDayEnd(cal.getTime());
	}


    
	/**
	* Description:得到指定日期的一天的的最后时刻23:59:59    
	* @Title: getDayEnd  

	 */
	public static Date getDayEnd(Date date) {
		String temp = formatDate(date,YMD);
		temp += " 23:59:59";
		return parseDate(temp,YMDHMS);
	}

	/**
	* Description:得到指定日期的一天的开始时刻00:00:00    
	* @Title: getDayStart  

	 */
	public static Date getDayStart(Date date) {
		String temp = formatDate(date,YMD);
		temp += " 00:00:00";
		return parseDate(temp, YMDHMS);
	}
  
	
	/**
	 * 
	* Description:获取date周后的第amount周的开始时间（这里星期一为一周的开始）    
	* @Title: getSpecficWeekStart  

	 */
	public static Date getWeekStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getDayStart(cal.getTime());
	}
    
}
