package net.intelliuno.commons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class CommonConstants {

	public static final String AUTHORIZATION_KEY_FOR_API="454 NjFTRlNSkTGVhNDo=";
	
	public static final String URL_FOR_PLAIN_WHATSAPP_MESSAGE="https://api.interakt.ai/v1/public/message/";
	
	public static final String EMPTY_STRING = "";
	
	public static final String SINGLE_QUOTE = "'";
	
	public static final String db_Name="gitproject_upload";
	
	public static final String AUTHORIZATION_KEY_FOR_API_WATTI="Bearer eyJhbGciOiJIUzI1NiIsI23324bmlxdWVfbmFtZSImVtYWlsIjoic2";
	
	public static final String WATI_URL_FOR_PLAIN_WHATSAPP_MESSAGE="https://live-server-114553.wati.io/api/v1/sendTemplateMessage";
	
	public static final String WATI_URL_FOR_SENT_SESSION_MESSAGE="https://live-server-114553.wati.io/api/v1/sendSessionMessage/";
	
	public static final String OPEN_URL_FOR_WHATSAPP1="https://keysto34ne1.intelli.uno/erprm54wise/";
	
	public static final String CUSTOMER_NAME="Keystone454";
	
	public static LocalTime currentTime = LocalTime.now();           //  16:32:19.319529900
	
    public static LocalDate currentDate = LocalDate.now();           //  2023-10-04

    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String todayFormattedDate = CommonConstants.currentDate.format(CommonConstants.dateFormat);


	public static final String reset = "\u001B[0m";
	public static final String red = "\u001B[31m";
	public static final String green = "\u001B[32m";
	public static final String yellow = "\u001B[33m";
	public static final String blue = "\u001B[34m";
	public static final String purple = "\u001B[35m";
	public static final String cyan = "\u001B[36m";
	public static final String white = "\u001B[37m";

    // ANSI escape codes for background colors
	public static final String redBackground = "\u001B[41m";
	public static final String greenBackground = "\u001B[42m";
	public static final String yellowBackground = "\u001B[43m";
	public static final String blueBackground = "\u001B[44m";
	public static final String purpleBackground = "\u001B[45m";
	public static final String cyanBackground = "\u001B[46m";
	public static final String whiteBackground = "\u001B[47m";

    // ANSI escape codes for text formatting
	public static final String bold = "\u001B[1m";
	public static final String underline = "\u001B[4m";
	public static final String blink = "\u001B[5m";
	public static final String reverseVideo = "\u001B[7m";


}
