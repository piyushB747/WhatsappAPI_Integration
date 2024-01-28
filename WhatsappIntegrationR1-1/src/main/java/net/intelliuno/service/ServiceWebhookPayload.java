package net.intelliuno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.intelliuno.entity.EntityChatWhatsappMaster;
import net.intelliuno.repository.RepoEntityChatWhatsappMaster;


//CREATED On 14 OCTOBER BY PIYUSHRAJ SINGH
@Service
public class ServiceWebhookPayload {

	 @Autowired
	private RepoEntityChatWhatsappMaster repoEntityChatWhatsappMaster;
     
	
	
	/**   15OCTOBER 2023
	   Here we are going to update eventType of
	   SESSION MESSAGES LIKE
	   1. sessionMessageSent
	   2. sentMessageDELIVERED
	   3. sentMessageREAD   
	 **/
	public void updateSessionMessageChat(String eventType, String receivedId, String conversationId,
			String whatsappMessageId, String waId, String textMessage, String phoneNumber, String statusString) {
		
		EntityChatWhatsappMaster e1=new EntityChatWhatsappMaster();
		try {
			if(statusString.equals("SENT")) {
				e1=repoEntityChatWhatsappMaster.returnSessionMessageIncidentDetailsWithPassingMessage(conversationId,waId,textMessage);
			}else if(statusString.equals("Delivered")){
				e1=repoEntityChatWhatsappMaster.returnIncidentDetailsForWatiUpdateDevliveredReaded(receivedId);
			}else if(statusString.equals("Delivered")){
				e1=repoEntityChatWhatsappMaster.returnIncidentDetailsForWatiUpdateDevliveredReaded(receivedId);
			}	
		}catch(Exception ex) { ex.printStackTrace(); }
		
		if (e1 != null) {
			if (statusString.equals("SENT")) {
				e1.setWaIdWac(waId);
				e1.setEventTypeWac(eventType);
				e1.setStatusStringWac(statusString);
				e1.setReceivedIdWac(receivedId);
				e1.setWhatsappMessageIdWac(whatsappMessageId);
			} else if (statusString.equals("Delivered")) {
				e1.setStatusStringWac(statusString);
				e1.setConversationIdWac(conversationId);
				e1.setEventTypeWac(eventType);
			} else if (statusString.equals("Read")) {
				e1.setStatusStringWac(statusString);
				e1.setConversationIdWac(conversationId);
				e1.setEventTypeWac(eventType);
			}
			try {
				repoEntityChatWhatsappMaster.save(e1);
			} catch (Exception ex) {				ex.printStackTrace();  }

		}
		
		   
	}
	
	
}
