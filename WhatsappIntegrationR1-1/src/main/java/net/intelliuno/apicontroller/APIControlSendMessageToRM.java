package net.intelliuno.apicontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.intelliuno.bean.BeanWati;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.watti.DtoTemplateRequest;
import net.intelliuno.entity.EntityIncidentMaster;
import net.intelliuno.repository.RepositoryEntityEngineerMaster;
import net.intelliuno.service.ServiceChattingOnWhatsappMaster;
import net.intelliuno.service.ServiceEntityIncidentMaster;
import net.intelliuno.service.ServiceMessageBodyObject;
import net.intelliuno.service.ServiceSendWatiMessage;
import net.intelliuno.service.ServiceWhatsappChat;

@Tag(  
		name = "APIControlSendMessageToRM",
        description = "This Java Class will be responsible for Sending Messages To RM which will be send from Servicestable SingleViewForm Page"
)
@RestController
@RequestMapping(value="/sendmessagetorm")
@CrossOrigin(origins="*")
public class APIControlSendMessageToRM {

    @Autowired
    private CustomRepoEntityIncidentMaster customRepoEntityIncidentMaster;
    
	@Autowired
	private ServiceEntityIncidentMaster serviceEntityIncidentMaster;
	
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private ServiceChattingOnWhatsappMaster serviceChattingOnWhatsappMaster;
	
	@Autowired
	private ServiceMessageBodyObject serviceMessageBodyObject;
	
	@Autowired 
	private BeanWati beanWati;
	
	@Autowired
	private ServiceWhatsappChat serviceWhatsappChat;
	
	@Autowired
	private ServiceSendWatiMessage serviceSendWatiMessage;
	
	@Operation(summary = "The System will send messages to rm when from singleviewincident form when sd team send from rm messages ")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Success",
	            content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
	})
	@GetMapping("/send")
	public ResponseEntity<?> sendMessageToRm(@RequestParam(name="m_strIncidentId",required = false)String m_strIncidentId,
			@RequestParam(name="message",required = false)String m_strMessage,
			@RequestParam(name="rmnumber",required = false)String m_strRmNumber,
			@RequestParam(name="rmId",required = false)String m_strRmId,
			@RequestParam(name="userId",required = false)String m_strUserId) {
     
		System.out.println(CommonConstants.red+"m_strMessage= "+m_strMessage+"  AND RM ID= "+m_strRmId+ " "+CommonConstants.reset);
		
	
		//ETA Update Required
		
	   String m_strMessageType="";
	   if(m_strMessage.contains("ATA Update Required")) {
		   m_strMessageType="ATA Update Required";
	   }else  if(m_strMessage.contains("ETA Update Required")) {
		   m_strMessageType="ETA Update Required";
	   }else  if(m_strMessage.contains("Follow-up  Time Expired") || m_strMessage.contains("Follow-up Time Expired")) {
		   m_strMessageType="Follow Up Expired";
	   }else  if(m_strMessage.contains("Work In Progress")) {
		   m_strMessageType="WIP STATUS";
	   }
		
		EntityIncidentMaster entityIncidentMaster = new EntityIncidentMaster();

		if (m_strIncidentId != null && !m_strIncidentId.trim().isEmpty()) {
			String m_strEngId = "", m_strAssetId = "", m_strUrl = "";
			String m_strAging = "";
			Long nLongEngId = 0L;
			@SuppressWarnings("unused")
			Long nLongRMEngId=0L;
			String m_strEngineerName = "", m_strEngContact = "";
			String m_strfollowupdateIrm = "", m_strFollowuptimeIrm = "";
            String m_strRMName="";
            
			try {
				entityIncidentMaster = serviceEntityIncidentMaster.returnIncidentDetailsList(m_strIncidentId);
			} catch (Exception ex) { System.out.println("Some error "); ex.printStackTrace(); entityIncidentMaster = null; }

			if (entityIncidentMaster != null) {

				m_strAssetId = entityIncidentMaster.getAssetIdIm();
				m_strEngId = String.valueOf(entityIncidentMaster.getEngineerEngmIm());
				m_strAging = " " + String.valueOf(entityIncidentMaster.getIncageindaysIm());

				try {
					nLongEngId = Long.valueOf(m_strEngId);
					nLongRMEngId=Long.valueOf(m_strRmId);
					m_strEngContact = repositoryEntityEngineerMaster.returnValueForIdForWhatsappContact(nLongEngId,"N");
					m_strEngineerName = repositoryEntityEngineerMaster.returnValueForIdForWhatsapp(nLongEngId, "N");
					m_strRMName=repositoryEntityEngineerMaster.returnValueForIdForWhatsapp(nLongRMEngId, "N");
					if(m_strRmNumber==null || m_strRmNumber.equals("")) {
						m_strRmNumber=repositoryEntityEngineerMaster.returnValueForIdForWhatsappContact(nLongRMEngId,"N");
					}
				} catch (Exception ex) {ex.printStackTrace(); m_strEngContact = ""; }

				
				 
	        	  if(m_strRmNumber.trim().isEmpty()==false) {
	        		String modelId=this.sendWhatsappMessageToRM(m_strRmId,m_strRmNumber,m_strRMName,m_strEngineerName,m_strEngContact,m_strIncidentId,
	        				  m_strAssetId,m_strAging,m_strUrl,m_strMessageType,m_strEngId,m_strUserId,m_strfollowupdateIrm,m_strFollowuptimeIrm);
	        		
	        		  
	        		 if(modelId.trim().isEmpty()==false) {
	        			  this.beanWati.setUniqueKeyByPiyush(modelId);
	        			  this.beanWati.setPhoneNumber(m_strEngContact);
	        			  this.beanWati.setTicketidBeanWati(m_strIncidentId);
	        			  return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Send")
	      				 		.contentType(MediaType.TEXT_PLAIN).body(modelId+" =Success");
	        		  }else { 
	        			  this.beanWati.setUniqueKeyByPiyush("");
	        			  this.beanWati.setPhoneNumber("");
	        			  this.beanWati.setTicketidBeanWati("");
	        			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
	        					  .contentType(MediaType.APPLICATION_JSON).body("BAD REQUEST");
	        		  }
	        		 
	        		  
	        	  }
				
			}
		}
		  
		return null;
	}
	
	public String sendWhatsappMessageToRM(String m_strRmId,String m_strRmNumber,String m_strRMName,String m_strEngineerName,String m_strEngContact,String m_strIncidentId,
		     String m_strAssetId,String m_strAging,String m_strUrl,String m_strMessageType,String m_strEngId,String m_strUserId,
		     String m_strfollowupdateIrm,String m_strFollowuptimeIrm) {
		  
		     System.out.println("RM NUMBER= "+m_strRmNumber);
			 String m_strIdForMessage="";
			 try {
				 
				 String m_strTemplateName="";
				 DtoTemplateRequest dtoTemplateRequest=new DtoTemplateRequest();
				 String m_strQuery="Select template_name_wtm from whatsapptemplatemaster_wtm where deleteflag_message_wtm='N' ";
				 
				 m_strQuery=m_strQuery+" and message_type_wtm='"+m_strMessageType+"' ";
		    	 
				 try {
					 m_strTemplateName=customRepoEntityIncidentMaster.getStringValue(m_strQuery);
				 }catch(Exception ex) { ex.printStackTrace(); }
				 
		    	 dtoTemplateRequest=serviceMessageBodyObject.returnListOfDtoMessageForEtaExpireWati(m_strIncidentId,m_strAssetId,m_strTemplateName,
	  		    		  m_strEngContact,m_strMessageType,m_strEngineerName,m_strAging,m_strEngId);
		    	 
		    	 try {
		    		 m_strIdForMessage=serviceSendWatiMessage.sendWatiMessageWithOktaLibrary(dtoTemplateRequest,m_strRmNumber);	 
		    	 }catch(Exception ex) {  ex.printStackTrace(); this.beanWati.setTicketidBeanWati(""); }
	  
		    	 if(m_strIdForMessage.trim().isEmpty()==false) {
		    		 m_strEngId=m_strRmId;
		    		 
		    		 serviceWhatsappChat.insertIntoWhatsappLogWatti(m_strIdForMessage,m_strRmNumber,m_strIncidentId,m_strAssetId,m_strAging,m_strMessageType,
			    			 m_strUserId,m_strEngId,m_strTemplateName,m_strEngineerName);
			    	 
		    		 serviceChattingOnWhatsappMaster.insertIntoChatting(m_strIdForMessage,m_strRmNumber,m_strIncidentId,m_strAssetId,m_strAging,m_strMessageType,
			    			 m_strUserId,m_strEngId,m_strTemplateName,m_strEngineerName,m_strIdForMessage);
		    	 }
		    	
			 }catch(Exception ex) { ex.printStackTrace(); this.beanWati.setUniqueKeyByPiyush(""); this.beanWati.setPhoneNumber(""); this.beanWati.setTicketidBeanWati(""); }
			 return m_strIdForMessage;
		
	}
	
}
