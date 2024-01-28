package net.intelliuno.serviceimpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import net.intelliuno.NotificationController;
import net.intelliuno.dto.notification.DtoNotifcation;
import net.intelliuno.repository.RepositoryEntityEngineerMaster;
import net.intelliuno.service.ServiceSendNotification;


@Service
public class ServiceImplSendNotification implements ServiceSendNotification{
	
	private final NotificationController notificationController;

    
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
    public ServiceImplSendNotification(NotificationController notificationController) {
        this.notificationController = notificationController;
    }
	
    
    
    /**
                                    *IMPORTANT CODE FOR SENDING NOTIFICATION* 
      The Notification is called when WEBHOOK is heated by message, then sendNotificationToScreen function is called and  then class
      NotificationController is called. AND from WEBSOCKET notification is send to ERPRMWISE;
      
     */
	@Override
	public void sendNotificationToScreen(String phoneNumber, String engId, String message,String conversationId) {
		try {
			
			System.out.println("SENDING NOTIFICATION IN ServiceImplSendNotification "+engId);
			
	        Date currentDate = new Date();
            LocalDateTime currentDateTime = LocalDateTime.now();
            
            LocalTime currentTime = LocalTime.now();
	        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = currentTime.format(formatterTime);
			
			 
		     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMM yyyy HH:mm");
		     String formattedDateTime = currentDateTime.format(formatter);
			
		     
			DtoNotifcation dtoNotifcation=new DtoNotifcation();
			String m_strEngName="";
			try {
			 m_strEngName=repositoryEntityEngineerMaster.returnValueForIdIncludedResignedIgnoreDeleteflag(Long.valueOf(engId),"N");
			}catch(Exception ex) { ex.printStackTrace(); }
			
			dtoNotifcation.setEngName(m_strEngName);
			dtoNotifcation.setEngId(engId); 
			dtoNotifcation.setPhoneNumber(phoneNumber);
			dtoNotifcation.setConversationId(conversationId);
			dtoNotifcation.setMessage(message);
			dtoNotifcation.setTime(""+formattedDateTime);
			dtoNotifcation.setDate(""+currentDate);
            dtoNotifcation.setTiming(formattedTime);
			
            
            System.out.println(currentDate+" | "+formattedDateTime);
			    try {
			    	notificationController.sendNotificationDtoJsonFormat(dtoNotifcation); 	
			    }catch(Exception ex) { ex.printStackTrace(); }
			   
		}catch(Exception ex) { }
	}
	
	
	/*
	public void sendNotificationToScreen() {
	        notificationController.sendNotification("A new notification!");
	}
	*/
	
}
