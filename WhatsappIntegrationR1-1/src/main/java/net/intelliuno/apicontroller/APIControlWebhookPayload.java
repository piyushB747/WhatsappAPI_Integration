package net.intelliuno.apicontroller;

import java.util.ArrayList;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.intelliuno.bean.BeanRMSendService;
//import net.intelliuno.bean.BeanNotification;
import net.intelliuno.bean.BeanWati;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.watti.DtoTemplateMessageEvent;
import net.intelliuno.entity.EntityChatWhatsappMaster;
import net.intelliuno.entity.EntityRmEscalation;

import net.intelliuno.repository.RepoEntityChatWhatsappMaster;
import net.intelliuno.repository.RepoEntityRmEscalation;
import net.intelliuno.service.ServicePayloadExtraction;
import net.intelliuno.service.ServiceWebhookPayload;
import net.intelliuno.serviceimpl.ServiceImplSendNotification;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Tag(
        name = "APIControlWebhookPayload",
        description = "Java Class will receive a Payload sent by the WATI API."
)
@RestController
@RequestMapping("/wareceivemessage")
@CrossOrigin(origins="*")
public class APIControlWebhookPayload {
	
	@Autowired 
	private BeanWati beanWati;
	
	@Autowired
	private BeanRMSendService beanRMSendService;
	
	@Autowired
	private ServiceWebhookPayload serviceWebhookPayload;
	
	//@Autowired
	//private ApplicationContext applicationContext;
	
	//@Autowired 
	//private BeanNotification beanNotification;
	
	@Autowired
	private ServicePayloadExtraction servicePayloadExtraction;

	@Autowired 
	private RepoEntityChatWhatsappMaster repoEntityChatWhatsappMaster;
	
	@Autowired
	private CustomRepoEntityIncidentMaster customRepo;
	
	@Autowired
	private ServiceImplSendNotification serviceImplSendNotification;
	
	@Autowired
	private RepoEntityRmEscalation repoEntityRmEscalation;
	
	@Operation(summary = "The system will receive a payload from WATI, and the WATI API response will indicate whether the message has been sent, received, or read. Additionally, the chat list will be stored in the 'whatsappchatmaster_wac' table.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Success",
	            content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
	})
	@PostMapping
	public ResponseEntity<?> methodSendToWhatsappWatiAPI(@RequestBody String payload){
		
		System.out.println(CommonConstants.green+"-----------------------------------------PAYLOADS STARTS HERE----------------------------------------------\n"+CommonConstants.reset);
		
		System.out.println("PAYLOAD DATA BEGAINS \n ");
		System.out.println(payload+" \n PAYLOAD DATA ENDS \n\n\n");
		
		String m_strTicketId="";
		String eventType="";
		String receivedId="",conversationId="",whatsappMessageId="",waId="",textMessage="",statusString="";
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		/**DEVELOPMENT FOR NOTIFICATION **/
		
		List<String> l_strListOfPhoneNumber=new ArrayList<String>();
		  try {
			  if(this.beanRMSendService.getL_strListOfRm()!=null) {
				  if(this.beanRMSendService.getL_strListOfRm().isEmpty()==false) {
					   for(int i=0;i<this.beanRMSendService.getL_strListOfRm().size();i++) {
						    String m_strRmNumberBean="";
						    m_strRmNumberBean=this.beanRMSendService.getL_strListOfRm().get(i);
						    String[] parts = m_strRmNumberBean.split("\\|"); 
						    String phoneNumber = parts[0]; 
						    l_strListOfPhoneNumber.add(phoneNumber);
						}   
				   }  
			  }
		  }catch(Exception ex){ System.out.println("----Error Here RM Service-----");  }
	
		/**DEVELOPMENT FOR NOTIFICATION **/
		
		/*NOT GOING TO USE DTO WAY STARTS*/
		try {
			DtoTemplateMessageEvent dtoPayload = objectMapper.readValue(payload, DtoTemplateMessageEvent.class);
			@SuppressWarnings("unused")
			String eventType1=dtoPayload.getEventType();
		}catch(Exception ex) { ex.printStackTrace(); }
		/*NOT GOING TO USE DTO WAY ENDS*/
		
		 JSONObject jsonResponse = new JSONObject(payload);
		 try {
			 receivedId = jsonResponse.getString("id");
			 eventType=jsonResponse.getString("eventType");
			 conversationId=jsonResponse.getString("conversationId");
			 whatsappMessageId=jsonResponse.getString("whatsappMessageId");
			 statusString=jsonResponse.getString("statusString");
			 if(statusString.equals("SENT")) {
				 waId=jsonResponse.getString("waId");
			 }
			 try {
				 textMessage=jsonResponse.getString("text");	
				 /*DEVELOPMENT TO HANDLE THE */
				 try {
					 textMessage = textMessage.replace("₹", "Rs");	 
				 }catch(Exception ex) {   }
				 
				 if (textMessage.contains("â‚¹")) {
					  textMessage = textMessage.replace("â‚¹", "Rs");
					  System.out.println("The replace string contains the symbol â‚¹ "+textMessage);
			      }
				
				 /*DEVELOPMENT TO HANDLE THE */
			 }catch(Exception ex) { textMessage=""; }
			 
		 }catch(Exception ex) { ex.printStackTrace(); }
		 
		 
		 System.out.println("ID: " + receivedId);
		 System.out.println("Event Type: " + eventType);
		 System.out.println("Conversation ID: " + conversationId);
		 System.out.println("WhatsApp Message ID: " + whatsappMessageId);
		 System.out.println("Text Message : " + textMessage);
		 System.out.println("WATI: "+waId);
		 
		 try {
		 		 
			 String uniqueKeyByPiyush="";String phoneNumber="";
				if (eventType.equals("message")) {

					this.insertReceivedMessage(uniqueKeyByPiyush, m_strTicketId, eventType, receivedId, conversationId,
							whatsappMessageId, waId, textMessage, phoneNumber, statusString, l_strListOfPhoneNumber);

				} else if (eventType.equals("templateMessageSent_v2")) {

					try {
						m_strTicketId = servicePayloadExtraction.getTicketIdFromtextMessage(textMessage);
					} catch (Exception e) { e.printStackTrace(); }

					uniqueKeyByPiyush = this.beanWati.getUniqueKeyByPiyush();
					phoneNumber = this.beanWati.getPhoneNumber();
					this.updateChatMaster(uniqueKeyByPiyush, m_strTicketId, eventType, receivedId, conversationId,
							whatsappMessageId, waId, textMessage, phoneNumber, statusString);

				} else if (eventType.equals("sentMessageDELIVERED_v2")  ||  eventType.equals("sentMessageREAD_v2")) {

					this.updateChatMaster(uniqueKeyByPiyush, m_strTicketId, eventType, receivedId, conversationId,
							whatsappMessageId, waId, textMessage, phoneNumber, statusString);                     
					//this.updateChatMaster(uniqueKeyByPiyush, m_strTicketId, eventType, receivedId, conversationId,whatsappMessageId, waId, textMessage, phoneNumber, statusString);   //THIS WAS IN   EVENTTYPE sentMessageREAD_v2
					
				 }else if (eventType.equals("sessionMessageSent")  ||  eventType.equals("sentMessageDELIVERED") 
						           ||  eventType.equals("sentMessageREAD") ) {
					try {
						serviceWebhookPayload.updateSessionMessageChat(eventType, receivedId, conversationId,
								whatsappMessageId, waId, textMessage, phoneNumber, statusString);	
					 }catch(Exception ex) { ex.printStackTrace(); }
				  }
				
			     
		 
		 }catch(Exception ex) { ex.printStackTrace(); }
		 
		 System.out.println("\n-----------------------------------------PAYLOADS ENDS HERE----------------------------------------------");
		 
		return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Integration")
		 		.contentType(MediaType.APPLICATION_JSON).body("Hello From WebHook IntelliUno");
	}
	
	public void insertReceivedMessage(String modelId,String m_strTicketId,String eventType,String receivedId,String conversationId,String whatsappMessageId,
			String waId,String textMessage,String phoneNumber,String statusString,List<String> l_strListOfPhoneNumber) {
		
		String m_strTicketIdBeanWati="";
		List<EntityChatWhatsappMaster> l_objEntityList=null;
		
		try {
			m_strTicketIdBeanWati=this.beanWati.getTicketidBeanWati();	
		}catch(Exception ex) { ex.printStackTrace(); }
		
		
		try {
		    try { waId = waId.substring(2);    /* REMOVING 91 From Query*/   }catch(Exception ex)  { ex.printStackTrace(); waId=""; }  
		   
		    try {
				if(m_strTicketIdBeanWati!=null && !m_strTicketIdBeanWati.equals("")) {
					l_objEntityList=repoEntityChatWhatsappMaster.findRepliedUserAll(conversationId,waId);
				}else {
					l_objEntityList=repoEntityChatWhatsappMaster.findRepliedUserAll(conversationId,waId);	
				}
			}catch(Exception ex) { ex.printStackTrace();  l_objEntityList=repoEntityChatWhatsappMaster.findRepliedUserAll(conversationId,waId); }
			
			try {
				if(l_objEntityList.isEmpty()==false) {
					for(int i=0;i<l_objEntityList.size();i++) {
						EntityChatWhatsappMaster e2=l_objEntityList.get(i);
						e2.setRepliedOrNotWac("REPLIED");
						repoEntityChatWhatsappMaster.save(e2);
					  }	
				}
			}catch(Exception ex) { ex.printStackTrace(); }
		
		
		}catch(Exception ex) { ex.printStackTrace(); }
		
		

		
		//Return Engineer Id For Contact
		String m_strEngId="";  int n_intCountOfEngAndTicketId=0; 
		String m_strQuery5="Select typeid_em from "+CommonConstants.db_Name+".engineermst_em where contact_em='"+waId+"' LIMIT  1 ";
		try {
			m_strEngId=customRepo.getStringValue(m_strQuery5);
		}catch(Exception ex) { ex.printStackTrace(); }
		
		String m_strCountOfEngAndTicketId="select count(*) from "+CommonConstants.db_Name+".incidentmaster where engineer_engm_im='"+m_strEngId+"' "
				+ " AND ( ticketid_im='"+m_strTicketIdBeanWati+"' or typeid_im="+m_strTicketIdBeanWati+" )  ";
		System.out.println("QUERY TO CHECK TICKETS IN BEAN WATI= "+m_strCountOfEngAndTicketId);
		try {
			n_intCountOfEngAndTicketId	= customRepo.getOpenCancelledCountForExecutiveWithParameterizedNative(m_strCountOfEngAndTicketId);
		}catch(Exception ex) {  ex.printStackTrace();  n_intCountOfEngAndTicketId=0; }
		
		
		
		
		//INSERTING REPLIED MESSAGES//
		EntityChatWhatsappMaster e1=new EntityChatWhatsappMaster();
		e1.setTextMessageWac(textMessage);
		e1.setWaIdWac(waId);
		e1.setWhatsappMessageIdWac(whatsappMessageId);
		e1.setSendFromTemplateWac("N");
		e1.setEventTypeWac(eventType);
		e1.setConversationIdWac(conversationId);
		e1.setSenderReceiverWac("RECEIVER");
		e1.setRepliedOrNotWac("MESSAGE FROM RECEIVER");
		e1.setSenderIdWac(m_strEngId);
		
		
		
		if(n_intCountOfEngAndTicketId==0) {
			 LocalDate currentDate = LocalDate.now();
		     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	         String formattedDate = currentDate.format(formatter);    //2023-09-16
	         
	         String m_strLatestTicket="";
	         //Development By PIYUSHRAJ//
	         try {
	        	 String l_strQueryForTodaySentTicketIds="Select ticket_id_wac,wa_id_wac "
	 	         		+ "from  "+CommonConstants.db_Name+".whatsappchatmaster_wac where wa_id_wac='"+waId+"' And  Date(createdatetime_wac)='"+formattedDate+"' \r\n"
	 	         		+ "AND conversation_id_wac is NOT NULL  And sender_receiver_wac='SENDER' order by id desc;"
	 	         		+ " ";
	 	         System.out.println(l_strQueryForTodaySentTicketIds);
	 	         List<Object[]> l1;
	 	         l1=customRepo.returnListOfEngineerAttendance(l_strQueryForTodaySentTicketIds);
	 	         String m_strPc="";
	 	         try {
		 	         if(l1.isEmpty()==false) {
		 	        	for(Object[] m_strRmData : l1) {
		 	        		 
						     try {
						    	 if(m_strPc.equals("")==true) {
						    		 m_strLatestTicket=net.intelliuno.commons.CommonUtils.nullToBlank(String.valueOf(m_strRmData[0]),false);
						    		 m_strPc=net.intelliuno.commons.CommonUtils.nullToBlank(String.valueOf(m_strRmData[0]),false); 
						    	 }else {
						    		 m_strPc=m_strPc+","+net.intelliuno.commons.CommonUtils.nullToBlank(String.valueOf(m_strRmData[0]),false);
						    	 }
							             
						     }catch(Exception ex) { m_strPc="--"; }
			 	        }	 
		 	         }
	 	         }catch(Exception ex) { ex.printStackTrace(); }
	 	         System.out.println("Check Today Tickets List= "+m_strPc);
	 	       e1.setListofticketWac(m_strPc);  
	         }catch(Exception ex) { ex.printStackTrace(); }
	         
			e1.setTicketIdWac(""+m_strLatestTicket);
			
		}else {
			e1.setTicketIdWac(m_strTicketIdBeanWati);
		}
		e1.setDeleteflagWac("N");;
		
		repoEntityChatWhatsappMaster.save(e1);
		
		/*PLease Dont Remove It Was done for Previous notification*/
			// this.beanNotification.setNotificationRecevied("TRUE");
			// this.beanNotification.setEngName(""+m_strEngId);
			// this.beanNotification.setMessage("  "+textMessage);
			// this.beanNotification.setPhoneNumber(""+waId);
		   
		/*Code For Checking RM Availability*/
		try {
			this.updateRMAvailabilityForWhatsapp(waId,m_strEngId,textMessage,l_strListOfPhoneNumber);
		}catch(Exception ex) {ex.printStackTrace(); }
		
		 /*PLease Dont Remove It Was done for Previous notification*/
		try {
				serviceImplSendNotification.sendNotificationToScreen(waId,m_strEngId,textMessage,conversationId);	 
			 }catch(Exception ex) {  ex.printStackTrace(); }	
		 
		
		l_strListOfPhoneNumber.clear();
	}
	

	public void updateChatMaster(String modelId,String m_strTicketId,String eventType,String receivedId,String conversationId,String whatsappMessageId,
			String waId,String textMessage,String phoneNumber,String statusString) {
		
		EntityChatWhatsappMaster e1=new EntityChatWhatsappMaster();
		try {
	      if(statusString.equals("SENT")) {
	    	  //System.out.println("modelId= "+modelId+ " Ticket Id= "+m_strTicketId+ " phoneNumber= "+phoneNumber);
	    	  e1=repoEntityChatWhatsappMaster.returnIncidentDetailsForWati(modelId, m_strTicketId,phoneNumber);
	    	  //System.out.println(" IN SENT "+e1);
	      }else if(statusString.equals("Delivered")){
	    	  //System.out.println("Delivered modelId= "+modelId+ " Ticket Id= "+m_strTicketId+ " phoneNumber= "+phoneNumber);
	    	  e1=repoEntityChatWhatsappMaster.returnIncidentDetailsForWatiUpdateDevliveredReaded(receivedId);
	      }	else if(statusString.equals("Read")) {
	    	  //System.out.println("Read modelId= "+modelId+ " Ticket Id= "+m_strTicketId+ " phoneNumber= "+phoneNumber);
	    	  e1=repoEntityChatWhatsappMaster.returnIncidentDetailsForWatiUpdateDevliveredReaded(receivedId);
	      }
					
			
		  
		}catch(Exception ex) { ex.printStackTrace(); }
		
		if(e1!=null) {
			
			
			if(statusString.equals("SENT")) {
				System.out.println(" IN SENT Conversation ID= "+conversationId);
				    e1.setWaIdWac(waId);
					e1.setConversationIdWac(conversationId);
					e1.setEventTypeWac(eventType);
					e1.setTextMessageWac(textMessage);
					e1.setWhatsappMessageIdWac(whatsappMessageId);
					e1.setReceivedIdWac(receivedId);
				    e1.setStatusStringWac(statusString);
				    e1.setDeleteflagWac("N");
			  }else if(statusString.equals("Delivered")){
				    e1.setStatusStringWac(statusString);
				    e1.setConversationIdWac(conversationId);
				    e1.setEventTypeWac(eventType);
			  }else if(statusString.equals("Read")) {
				    e1.setEventTypeWac(eventType);
				    e1.setStatusStringWac(statusString);
				    e1.setConversationIdWac(conversationId);
			  }
			try {
				repoEntityChatWhatsappMaster.save(e1);
			}catch(Exception ex) {	ex.printStackTrace(); }
			
		}
		
		
	}
	
	public void updateRMAvailabilityForWhatsapp(String waId,String m_strEngId,String textMessage,List<String> l_strListOfPhoneNumber) {
		try {
			if(l_strListOfPhoneNumber.contains(waId)) {
			    String m_strQueryForRmCheck="Select role_rm_em from engineermst_em where contact_em='"+waId+"' ";
			    String m_strRoleId=customRepo.getStringValue(m_strQueryForRmCheck);
			    if(m_strRoleId.equals("15")) {
				    if(CommonConstants.currentTime.getHour()<18) {
			        	if(textMessage.equals("Yes") || textMessage.equals("No")) {
			        		EntityRmEscalation l_Obje1RmEscalation=new EntityRmEscalation();
			        		l_Obje1RmEscalation.setDeleteflagRsw("N");
			        		l_Obje1RmEscalation.setMessageRepliedRsw(textMessage);
			        		l_Obje1RmEscalation.setRmPhonenumberRsw(waId);
			        		l_Obje1RmEscalation.setRmIdRsw(m_strEngId);
			        		repoEntityRmEscalation.save(l_Obje1RmEscalation);
			        	}	    	
			        }
			    }
			}
		}catch(Exception ex) {ex.printStackTrace();}
	}
	
}
