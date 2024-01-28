package net.intelliuno.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ServicePayloadExtraction {

	
	/*FIND FOR TID*/
	public String getTicketIdFromtextMessage(String textMessage) {
		String m_strTicketId="";
        String pattern = "TID: (\\d+)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher m = r.matcher(textMessage);

        // Check if a match is found
        if (m.find()) {
        	m_strTicketId = m.group(1);
            System.out.println("TID: " + m_strTicketId);
        } else {
        	m_strTicketId = getTicketIdFromtextMessageWhenTidIsNull(textMessage);
            System.out.println("TID not found in the text.");
        }
		return m_strTicketId;
	}
	
	/*FOR TICKET ID*/
	public String getTicketIdFromtextMessageWhenTidIsNull(String textMessage) {
		String m_strTicketId="";
        String pattern = "Ticket ID: (\\d+)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher m = r.matcher(textMessage);

        // Check if a match is found
        if (m.find()) {
        	m_strTicketId = m.group(1);
            System.out.println("Ticket ID: " + m_strTicketId);
        } else {
        	  
            System.out.println("TICKET ID not found in the text.");
        }
		return m_strTicketId;
	}
	
}
