package net.intelliuno.commons;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class CommonUtils {

	public static String nullToBlank(String f_strInputValue, boolean f_blnQuotesFlag) {
        if (f_strInputValue == null) {
            return CommonConstants.EMPTY_STRING;
        }
        if (f_blnQuotesFlag) {
            if (f_strInputValue.startsWith(CommonConstants.SINGLE_QUOTE) && f_strInputValue.endsWith(CommonConstants.SINGLE_QUOTE)) {
                f_strInputValue = f_strInputValue.substring(1, f_strInputValue.length() - 1);
            }
        }
        return f_strInputValue;
    }
	
	
	 public static int getNoOfDaysInMonthCurrentMonthWithMonthNo(int m_strMonth, int year) {
	        int l_intdays = 0;
	        try {
	            int[] monthDays = {31, 29, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	            if (m_strMonth == 1) {
	                l_intdays = monthDays[0];
	            } else if (m_strMonth == 2) {

	                if (year % 4 == 0) {
	                    if (year % 400 == 0 || (year % 100 != 0)) {
	                        l_intdays = monthDays[1];
	                    }
	                } else {
	                    l_intdays = monthDays[2];
	                }
	            } else if (m_strMonth == 3) {
	                l_intdays = monthDays[3];
	            } else if (m_strMonth == 4) {
	                l_intdays = monthDays[4];
	            } else if (m_strMonth == 5) {
	                l_intdays = monthDays[5];
	            } else if (m_strMonth == 6) {
	                l_intdays = monthDays[6];
	            } else if (m_strMonth == 7) {
	                l_intdays = monthDays[7];
	            } else if (m_strMonth == 8) {
	                l_intdays = monthDays[8];
	            } else if (m_strMonth == 9) {
	                l_intdays = monthDays[9];
	            } else if (m_strMonth == 10) {
	                l_intdays = monthDays[10];
	            } else if (m_strMonth == 11) {
	                l_intdays = monthDays[11];
	            } else if (m_strMonth == 12) {
	                l_intdays = monthDays[12];
	            }
	            return l_intdays;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return l_intdays;
	    }
	 
       
	 
	    /*
	     It will convert 2023-09-06T19:41:44 this time format in this  08 oct,2023 19:20
	     */
	    public static String getFormatedDate(String m_strDateTime) {
	    	String m_strFormatedDateTime="";
	    	try {
	    	    try {
			        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			        LocalDateTime localDateTime = LocalDateTime.parse(m_strDateTime, inputFormatter);
		
			        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM,yyyy hh:mm a");
			        String formattedDateTime = localDateTime.format(outputFormatter);
			        m_strFormatedDateTime=String.valueOf(formattedDateTime);
			        
	        }catch(Exception ex) {
	        	 	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        	 	LocalDateTime localDateTime = LocalDateTime.parse(m_strDateTime, inputFormatter); 

	 	        	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM,yyyy hh:mm a");
	 	        	String formattedDateTime = localDateTime.format(outputFormatter);
	 	        	m_strFormatedDateTime=String.valueOf(formattedDateTime);
	 	        	
	 	        	
	        }
	    	}catch(Exception ex) { ex.printStackTrace(); }
	    	return m_strFormatedDateTime;
	    }

	      
	    //OUTPUT:=  It is the best website to learn technology.
	    public static final String capitalize(String str)   
	      {  
	      if (str == null || str.length() == 0) return str;  
	      return str.substring(0, 1).toUpperCase() + str.substring(1);  
	    }  
	    
	    //IT WILL CONVERT 10/18/2023  this date format into 2023-09-23 format
	    public static String functionDateFormatNeeded(String m_strSelectedDate) {
	    	String outputDateFormat = "yyyy-MM-dd";
	    	String outputDateString="";
	        // Define the input date format
	        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	        try {
	            // Parse the input date string into a Date object
	            Date date = inputDateFormat.parse(m_strSelectedDate);

	            // Define the desired output date format
	            SimpleDateFormat outputDateFormatObj = new SimpleDateFormat(outputDateFormat);

	            // Format the Date object into the desired output format
	            outputDateString = outputDateFormatObj.format(date);

	            System.out.println("Input Date String: " + m_strSelectedDate);
	            System.out.println("Formatted Date: " + outputDateString);
	        } catch (Exception ex) { ex.printStackTrace();  }
	        return outputDateString;
	    }
	    
	    
	    /*
	     It will convert 2023-09-06T19:41:44 this time format in this  08 oct,2023 19:20
	     */
	    public static String getFormatedDate2(String m_strDateTime) {
	    	String m_strFormatedDateTime="";
	    	try {
	    	    try {
	    	    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

	    	    	
			        LocalDateTime localDateTime = LocalDateTime.parse(m_strDateTime, inputFormatter);
		
			        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM,yyyy hh:mm a");
			        String formattedDateTime = localDateTime.format(outputFormatter);
			        m_strFormatedDateTime=String.valueOf(formattedDateTime);
			        
			        }catch(Exception ex) {
			        	try {
			        	 	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			        	 	LocalDateTime localDateTime = LocalDateTime.parse(m_strDateTime, inputFormatter); 
		
			 	        	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM,yyyy hh:mm a");
			 	        	String formattedDateTime = localDateTime.format(outputFormatter);
			 	        	m_strFormatedDateTime=String.valueOf(formattedDateTime);
			 	        		        		
			        	}catch(Exception ex2) {
			        		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

				    		LocalDateTime localDateTime = LocalDateTime.parse(m_strDateTime, inputFormatter); 

				        	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM,yyyy hh:mm a");
				        	String formattedDateTime = localDateTime.format(outputFormatter);
				        	m_strFormatedDateTime=String.valueOf(formattedDateTime);
				    	
				    	
			        	}
			     
			      }
	    	}catch(Exception ex) { ex.printStackTrace();
	    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	    		LocalDateTime localDateTime = LocalDateTime.parse(m_strDateTime, inputFormatter); 

	        	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMM,yyyy hh:mm a");
	        	String formattedDateTime = localDateTime.format(outputFormatter);
	        	m_strFormatedDateTime=String.valueOf(formattedDateTime);
	    	
	    	
	    	}
	    	return m_strFormatedDateTime;
	    }
}
