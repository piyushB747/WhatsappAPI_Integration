package net.intelliuno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.entity.EntityWhatsappChatLogMaster;
import net.intelliuno.repository.RepoEntityWhatsappChatLogMaster;

@Service
public class ServiceWhatsappChat {

	@Autowired
	private RepoEntityWhatsappChatLogMaster reposChatMaster;
	
	@Autowired
    private CustomRepoEntityIncidentMaster customRepoEntityIncidentMaster;
	
	
	/**DEVELOPMENET FOR SESSION CHATTING LOG INSERTION DATE 13 OCTOBER 2023 **/
	public void insertIntoWhatsappLogWattiForSessionMessages(String m_strMessageId,String m_strPhoneNumber,String m_strMessage,String m_strUserId,String m_strEngineerName, String m_strEngId) {
		
		EntityWhatsappChatLogMaster entityWhatsappChatMaster=new EntityWhatsappChatLogMaster();
		
		entityWhatsappChatMaster.setMessageIdWac(m_strMessageId);
		entityWhatsappChatMaster.setMobileNoWac(m_strPhoneNumber);
		entityWhatsappChatMaster.setAssetIdWac("");
		entityWhatsappChatMaster.setIncidentIdWac("");
		entityWhatsappChatMaster.setSiteIdWac("");
		entityWhatsappChatMaster.setMessageSenderWac(m_strUserId+" ");
		entityWhatsappChatMaster.setMessageReceiverWac(m_strEngineerName);
		entityWhatsappChatMaster.setMessageTypeWac("");
		entityWhatsappChatMaster.setMessageTemplateForWac("");
		entityWhatsappChatMaster.setMessageWac(m_strMessage);
		entityWhatsappChatMaster.setEngidWac(m_strEngId);
		entityWhatsappChatMaster.setMessageSentFromWati("Y");
		entityWhatsappChatMaster.setWatiSessionMessage("Y");
	 
		reposChatMaster.save(entityWhatsappChatMaster);
	}
	/**END DEVELOPMENET FOR SESSION CHATTING LOG INSERTION DATE 13 OCTOBER 2023 **/
	
	
	
	 public void insertIntoWhatsappLogWatti(String m_strIdForMessage,String m_strPhoneNumber,String m_strIncidentId,String m_strAssetId,String m_strAging,
				String m_strMessageType,String m_strUserId,String m_strEngId,String m_strTemplateName,String m_strEngineerName) {
		 
		 String m_strSiteId="";
		 String message=""; 
		 
		 EntityWhatsappChatLogMaster entityWhatsappChatMaster=new EntityWhatsappChatLogMaster();
		 String m_strQuery="Select template_message_wtm from whatsapptemplatemaster_wtm where deleteflag_message_wtm='N' ";
		 
		  
		 m_strQuery=m_strQuery +" and message_type_wtm='"+m_strMessageType+"' ";
		 
			 try {
				 if(m_strMessageType.equals("ETA Update Required") || m_strMessageType.equals("ATA Update Required")) {
					 
					 message=customRepoEntityIncidentMaster.getStringValue(m_strQuery);
					 
					 message=message.replace("AAAA",m_strEngineerName);
					 message=message.replace("BBBB",m_strIncidentId);
					 message=message.replace("CCCC",m_strAssetId);
					 message=message.replace("DDDD",m_strAging);
					 message=message.replace("EEEE",CommonConstants.OPEN_URL_FOR_WHATSAPP1);
					 message=message.replace("FFFF",CommonConstants.CUSTOMER_NAME);
					 
					 entityWhatsappChatMaster.setM_strAgingWac(m_strAging);
					 entityWhatsappChatMaster.setM_strSetUrlForOpenWac(""+CommonConstants.OPEN_URL_FOR_WHATSAPP1);
					 entityWhatsappChatMaster.setMessageSentFromWati("Y");
					 
				 }else if(m_strMessageType.equals("Follow Up Expired")){
                     message=customRepoEntityIncidentMaster.getStringValue(m_strQuery);
					 
					 message=message.replace("AAAA",m_strEngineerName);
					 message=message.replace("BBBB",m_strIncidentId);
					 message=message.replace("CCCC",m_strAssetId);
					 message=message.replace("DDDD",m_strAging);
					 message=message.replace("EEEE",CommonConstants.OPEN_URL_FOR_WHATSAPP1);
					 message=message.replace("FFFF",CommonConstants.CUSTOMER_NAME);
					 
					 entityWhatsappChatMaster.setM_strAgingWac(m_strAging);
					 entityWhatsappChatMaster.setM_strSetUrlForOpenWac(""+CommonConstants.OPEN_URL_FOR_WHATSAPP1);
					 entityWhatsappChatMaster.setMessageSentFromWati("Y");
				 }else if(m_strMessageType.equals("WIP STATUS")){
					  message=customRepoEntityIncidentMaster.getStringValue(m_strQuery);
						 
						 message=message.replace("AAAA",m_strEngineerName);
						 message=message.replace("BBBB",m_strIncidentId);
						 message=message.replace("CCCC",m_strAssetId);
						 message=message.replace("DDDD",CommonConstants.CUSTOMER_NAME);
						 
						 entityWhatsappChatMaster.setM_strAgingWac(m_strAging);
						 entityWhatsappChatMaster.setM_strSetUrlForOpenWac(""+CommonConstants.OPEN_URL_FOR_WHATSAPP1);
						 entityWhatsappChatMaster.setMessageSentFromWati("Y");
				 }
			 }catch(Exception ex) { ex.printStackTrace(); }
		 
			entityWhatsappChatMaster.setMessageIdWac(m_strIdForMessage);
			entityWhatsappChatMaster.setMobileNoWac(m_strPhoneNumber);
			entityWhatsappChatMaster.setAssetIdWac(m_strAssetId);
			entityWhatsappChatMaster.setIncidentIdWac(m_strIncidentId);
			entityWhatsappChatMaster.setSiteIdWac(m_strSiteId);
			entityWhatsappChatMaster.setMessageSenderWac(m_strUserId+" ");
			entityWhatsappChatMaster.setMessageReceiverWac(m_strEngineerName);
			entityWhatsappChatMaster.setMessageTypeWac(m_strMessageType);
			entityWhatsappChatMaster.setMessageTemplateForWac(m_strTemplateName);
			entityWhatsappChatMaster.setMessageWac(message);
			entityWhatsappChatMaster.setEngidWac(m_strEngId);
		 
			reposChatMaster.save(entityWhatsappChatMaster);
	 }
	
    
	//m_strIdForMessage,m_strPhoneNumber,m_strIncidentType,m_strAssetId,m_strSiteId,m_strMessageType,m_strUserId);
	public void insertIntoWhatsappLog(String m_strIdForMessage,String m_strPhoneNumber,String m_strIncidentType,String m_strAssetId,String m_strSiteId,
			String m_strMessageType,String m_strUserId,String m_strEngId,String m_strTemplateName) {
		
		String m_strQuery="Select template_message_wtm from whatsapptemplatemaster_wtm where deleteflag_message_wtm='N' "
				+" and message_type_wtm='"+m_strMessageType+"' "; 
		 
		System.out.println(m_strIncidentType+" Your Incident Type");
		String message="";
		message=customRepoEntityIncidentMaster.getStringValue(m_strQuery);
		message=message.replace("variable {{1}}", m_strIncidentType);
		message=message.replace("variable {{2}}", "UNO-192");
		
		
		
		EntityWhatsappChatLogMaster entityWhatsappChatMaster=new EntityWhatsappChatLogMaster();
		entityWhatsappChatMaster.setMessageIdWac(m_strIdForMessage);
		entityWhatsappChatMaster.setMobileNoWac(m_strPhoneNumber);
		entityWhatsappChatMaster.setAssetIdWac(m_strAssetId);
		entityWhatsappChatMaster.setIncidentIdWac(m_strIncidentType);
		entityWhatsappChatMaster.setSiteIdWac(m_strSiteId);
		entityWhatsappChatMaster.setMessageSenderWac(m_strUserId);
		entityWhatsappChatMaster.setMessageReceiverWac(m_strEngId);
		entityWhatsappChatMaster.setMessageTypeWac(m_strMessageType);
		entityWhatsappChatMaster.setMessageTemplateForWac(m_strTemplateName);
		entityWhatsappChatMaster.setMessageWac(message);
		entityWhatsappChatMaster.setEngidWac(m_strEngId);
		
	    
	
		reposChatMaster.save(entityWhatsappChatMaster);
		
		
	}
	
	
}
