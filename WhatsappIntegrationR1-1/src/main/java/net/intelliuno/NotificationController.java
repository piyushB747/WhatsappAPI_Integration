package net.intelliuno;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import net.intelliuno.dto.notification.DtoNotifcation;

@Controller
public class NotificationController {
	   private final SimpMessagingTemplate messagingTemplate;

	    
	    public NotificationController(SimpMessagingTemplate messagingTemplate) {
	        this.messagingTemplate = messagingTemplate;
	    }

	    public void sendNotification(String message) {
	        messagingTemplate.convertAndSend("/topic/notifications", message);
	    }
	    
	    public void sendNotificationDtoJsonFormat(DtoNotifcation message) {
	        messagingTemplate.convertAndSend("/topic/notifications", message);
	    }
}
