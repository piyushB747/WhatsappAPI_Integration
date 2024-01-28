package net.intelliuno.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.intelliuno.commons.CommonConstants;
import net.intelliuno.repository.RepoEntityServiceRunningTimer;
import net.intelliuno.service.ServiceRunScheduledService;

@Service
public class ServiceImplRunScheduledService implements ServiceRunScheduledService {

	LocalDate m_strPreviousDate = null;LocalDate m_strCurrentDate = null;
	private static String m_strTimeToSendMessageToRm="";
   	
	@Autowired
	private RepoEntityServiceRunningTimer repoEntityServiceRunningTimer;


	@Override
	public String checkTimeToSendRmWhatsappMessages() {
        
        if (m_strPreviousDate == null) { 
            m_strPreviousDate = CommonConstants.currentDate.minusDays(1); // 2023-10-04
        }
        if (m_strCurrentDate == null) { 
        	m_strCurrentDate = CommonConstants.currentDate;
        }
        
        //if (m_strCurrentDate.equals(m_strPreviousDate)==false) {
        	
        	m_strPreviousDate=m_strCurrentDate;
    		String m_strHour = "";
    		try {
    			m_strHour = repoEntityServiceRunningTimer.findByServiceNameSrNative("N", "SEND MESSAGE TO RM");
    		} catch (Exception ex) { m_strHour = "09:00"; }

    		String cronExpression = calculateCronExpression(m_strHour);
    		m_strTimeToSendMessageToRm="";
    		m_strTimeToSendMessageToRm=cronExpression;       	
        //}
        
    	return m_strTimeToSendMessageToRm;

	}

	private String calculateCronExpression(String scheduledTime) {
		String[] parts = scheduledTime.split(":");
		//String cronExpression = "0 " + parts[1] + " " + parts[0] + " * * ?";   //REMOVED BECAUSE WE ARE NOT ABLED TO USED DYNAMIC CRON EXPRESSION
		String cronExpression = "" + parts[0] + ":" + parts[1] + "";             //OUTPUT WILL BE 13:13
		return cronExpression;
	}

}

