package net.intelliuno.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.intelliuno.commons.CommonConstants;
import net.intelliuno.commons.CommonUtils;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.DtoWhatsappChatting;
import net.intelliuno.entity.EntityChatWhatsappMaster;
import net.intelliuno.jsonChatts.JsonChat;
import net.intelliuno.repository.RepoEntityChatWhatsappMaster;


@Service
public class ServiceChattingOnWhatsappMaster implements net.intelliuno.service.ServiceChattingOnWhatsappMaster{

	@Autowired
	private RepoEntityChatWhatsappMaster repoChatWhatsappMaster;
	
	@Autowired
	private CustomRepoEntityIncidentMaster custRepo;
	
	@Override
	public List<EntityChatWhatsappMaster> insertIntoChatting(String m_strIdForMessage, String m_strPhoneNumber,
			String m_strIncidentId, String m_strAssetId, String m_strAging, String m_strMessageType, String m_strUserId,
			String m_strEngId, String m_strTemplateName, String m_strEngineerName, String modelId) {

		EntityChatWhatsappMaster e1=new EntityChatWhatsappMaster();
		
		e1.setEngineerIdWac(m_strEngId);
		e1.setTicketIdWac(m_strIncidentId);
		e1.setSendFromTemplateWac("Y");
		e1.setSenderReceiverWac("SENDER");
		e1.setDeleteflagWac("N");
		e1.setSenderIdWac(m_strUserId+ "(TEAM)");
		e1.setModelIdWac(modelId);
		e1.setPhoneNumberWac(m_strPhoneNumber);
		e1.setTemplateNameWac(m_strTemplateName);
		
		repoChatWhatsappMaster.save(e1);
		
		return null;
	}

	
	/** FROM HERE WE ARE FETCHING CHATLIST FOR SINGLE VIEW INCIDENT PAGE**/
	@Override
	public List<DtoWhatsappChatting> findUsersByIncidentIdAndPhone(String m_strIncidentId, String m_strPhoneNumber,
			String waIdWac) {
		List<EntityChatWhatsappMaster> l_objList=new ArrayList<EntityChatWhatsappMaster>();
		List<DtoWhatsappChatting> l_objList2=new ArrayList<DtoWhatsappChatting>();
		
		l_objList=repoChatWhatsappMaster.findUsersByIncidentIdAndPhone(m_strIncidentId, waIdWac);
		
		if(l_objList.isEmpty()==false) {
			
			for(int i=0;i<l_objList.size();i++) {
				EntityChatWhatsappMaster e1=l_objList.get(i);
				
				DtoWhatsappChatting d1=new  DtoWhatsappChatting();
				d1.setDateTime(e1.getCreatedatetimeEsm()+"");
				d1.setSenderReceiverWac(e1.getSenderReceiverWac());
				d1.setStatusString(e1.getStatusStringWac());
				d1.setTextMessageWac(e1.getTextMessageWac());
				l_objList2.add(d1);
				d1=null;
				
			}
			
		}
		
		if(l_objList2.isEmpty()==false) {
			return l_objList2;	
		}else {
			return null;
		}
		
	}

	/*DEVELOPMENT FOR CHATTING TO GET  AND DISPLAY ON CHATBOAT**/
	@Override
	public List<JsonChat> returnChatDataByPhoneNumber(String waIdWac,String m_strSelectedDate) {
		List<JsonChat> l_objListJsonChat=new ArrayList<JsonChat>();
		
		
		System.out.println("Phone Number+ "+"PHONE ");
		List<Object[]> l_objListOfObject;
		
		if(m_strSelectedDate!=null && m_strSelectedDate.equals("")==false) {
			
			String m_strDateFormatNeeded=" ";
			m_strDateFormatNeeded=CommonUtils.functionDateFormatNeeded(m_strSelectedDate);
			l_objListOfObject=this.generateChatByDate(waIdWac,m_strDateFormatNeeded);
			
		}else {
			l_objListOfObject = repoChatWhatsappMaster.findUsersChatsBy_Phone(waIdWac);
				
		}
		
		if(l_objListOfObject!=null && l_objListOfObject.isEmpty()==false) {
			for(Object[] customerData : l_objListOfObject) {
				JsonChat customer = new JsonChat();
		        customer.setM_strMessageText((String) customerData[0]);
		        customer.setM_strSenderReceiver((String) customerData[1]);
		         
		        String m_strFormatedDateTime="";
		
		        if(m_strSelectedDate!=null && m_strSelectedDate.equals("")==false) {
		        	try {
		        		m_strFormatedDateTime=CommonUtils.getFormatedDate2(customerData[2].toString());	
		        	}catch(Exception ex) {  }
		        	
		        }else {

			        LocalDateTime localDateTime1 = (LocalDateTime) customerData[2];
			         m_strFormatedDateTime=CommonUtils.getFormatedDate(localDateTime1.toString());	
		        }
		        
	            customer.setM_strChatTime(m_strFormatedDateTime);
	            customer.setM_strStatusString((String) customerData[3]);
		        l_objListJsonChat.add(customer);
		    }
		}
		
		
		return l_objListJsonChat;
	}

	

	
	
	


	/**SAVING SESSION MESSAGES UNIQUE ID  DEVELOPMENT BY PIYUSHRAJ 13 OCTOBER 2023**/
	@Override
	public void insertIntoWhatsappWattiForSessionMessages(String m_strMessage, String m_strPhoneNumber,
			String m_strMessageId, String m_strUserId, String m_strEngineerName, String m_strEngId,String conversationId,String watiTicketId) {
        try {
    		EntityChatWhatsappMaster e1=new EntityChatWhatsappMaster();
    		e1.setEngineerIdWac(m_strEngId);
    		e1.setTicketIdWac(" ");
    		e1.setSendFromTemplateWac("");
    		e1.setSendFromWatiSessionWac("SESSION MESSAGING");
    		e1.setSenderReceiverWac("SENDER");
    		e1.setDeleteflagWac("N");
    		e1.setSenderIdWac(m_strUserId+ "(TEAM)");
    		e1.setModelIdWac(m_strMessageId);
    		e1.setPhoneNumberWac(m_strPhoneNumber);
    		e1.setTemplateNameWac("");
    		e1.setWaIdWac(m_strPhoneNumber);
    		e1.setTextMessageWac(m_strMessage);
    		e1.setConversationIdWac(""+conversationId);
    		e1.setTicketIdFromWati(""+watiTicketId);
    		
    		
    		repoChatWhatsappMaster.save(e1);
        	
        }catch(Exception ex) { ex.printStackTrace(); }
	}
	
	
	
	private List<Object[]> generateChatByDate(String waIdWac, String m_strDateFormatNeeded) {
		List<Object[]> l_objListOfObject;
		String m_strQuery="Select text_message_wac,sender_receiver_wac,createdatetime_wac,status_string_wac "
				+ " from whatsappchatmaster_wac where DATE(createdatetime_wac) = '"+m_strDateFormatNeeded+"' and conversation_id_wac is NOT NULL AND "
				+ " ( wa_id_wac='"+waIdWac+"' OR phone_number_wac='"+waIdWac+"' ) order by id asc";
		
		System.out.println("QUERY FOR CHAT LIST BY DATE= "+m_strQuery);
		l_objListOfObject=custRepo.returnListOfSelectStarFromConcept(m_strQuery);
		if(l_objListOfObject==null || l_objListOfObject.isEmpty()) {
			System.out.println("There is no data by filter of date "+m_strDateFormatNeeded);
			   Object[] data = new Object[4]; // Change the size based on your data
		        data[0] = "SORRY THERE IS NO CHAT ON DATE "+m_strDateFormatNeeded; // Example data
		        data[1] = "SENDER";
		        data[2] =  CommonConstants.currentDate+" "+CommonConstants.currentTime;
		        data[3] = "Read"; 
		        l_objListOfObject.add(data);


		}
		return l_objListOfObject;
	}
}
