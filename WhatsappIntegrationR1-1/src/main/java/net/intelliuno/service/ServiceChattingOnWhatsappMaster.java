package net.intelliuno.service;
import java.util.List;

import net.intelliuno.dto.DtoWhatsappChatting;
import net.intelliuno.entity.EntityChatWhatsappMaster;
import net.intelliuno.jsonChatts.JsonChat;

public interface ServiceChattingOnWhatsappMaster {

	public List<EntityChatWhatsappMaster> insertIntoChatting(String m_strIdForMessage,String m_strPhoneNumber,String m_strIncidentId,String m_strAssetId,String m_strAging,
			String m_strMessageType,String m_strUserId,String m_strEngId,String m_strTemplateName,String m_strEngineerName,String modelId);

	public List<DtoWhatsappChatting>  findUsersByIncidentIdAndPhone(String m_strIncidentId,String m_strPhoneNumber,String waIdWac);
	
	public List<JsonChat>  returnChatDataByPhoneNumber(String waIdWac,String m_strSelectedDate);
	
	
	public void insertIntoWhatsappWattiForSessionMessages(String m_strMessage,String m_strPhoneNumber,String m_strMessageId,String m_strUserId,String m_strEngineerName, String m_strEngId,String conversationId,String watiTicketId);
	
}
