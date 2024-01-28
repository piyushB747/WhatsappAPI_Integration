package net.intelliuno.apicontroller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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

import org.springframework.http.MediaType;
import net.intelliuno.bean.BeanWati;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.watti.DtoJsonResponseWati;
import net.intelliuno.dto.watti.DtoTemplateRequest;
import net.intelliuno.entity.EntityIncidentMaster;
import net.intelliuno.repository.RepositoryEntityEngineerMaster;
import net.intelliuno.service.ServiceChattingOnWhatsappMaster;
import net.intelliuno.service.ServiceEntityIncidentMaster;
import net.intelliuno.service.ServiceMessageBodyObject;
import net.intelliuno.service.ServiceSendWatiMessage;
import net.intelliuno.service.ServiceWhatsappChat;

@Tag(
        name = "APIControlSendTemplateTextToWATI",
        description = "This Java Class will Send Template Message to WATI API"
)
@RestController
@RequestMapping(value="/intelliunoapitosendplanemessagebywati")
@CrossOrigin(origins="*")
public class APIControlSendTemplateTextToWATI {

	
    @Autowired
    private CustomRepoEntityIncidentMaster customRepoEntityIncidentMaster;
    
	@Autowired
	private ServiceEntityIncidentMaster serviceEntityIncidentMaster;
	
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private ServiceChattingOnWhatsappMaster serviceChattingOnWhatsappMaster;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ServiceMessageBodyObject serviceMessageBodyObject;
	
	@Autowired 
	private BeanWati beanWati;
	
	@Autowired
	private ServiceWhatsappChat serviceWhatsappChat;
	
	@Autowired
	private ServiceSendWatiMessage serviceSendWatiMessage;
	
	@Operation(summary = "From servicestable we are going to send template message to WATI API.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success",
	                content = {@Content(mediaType =  "application/json",schema =@Schema(implementation = DtoJsonResponseWati.class))})
	 })
	 @GetMapping
     public ResponseEntity<?> sendMessageToWhatsapp(@RequestParam(name="m_strMessageType",required=true,defaultValue = "") String m_strMessageType,
    		 @RequestParam(name="m_strIncidentId",required=true) String m_strIncidentId,
    		 @RequestParam(name="m_strUserId",required=true) String m_strUserId,
    		 @RequestParam(name="m_strSendByNetbeans",required=false,defaultValue = "YES") String m_strSendByNetbeans){
    	
		 System.out.println(CommonConstants.green+"--------In API intelliunoapitosendplanemessagebywati-------"+CommonConstants.reset);
		 this.beanWati=applicationContext.getBean(BeanWati.class);
		 this.beanWati.setUniqueKeyByPiyush("");
		 this.beanWati.setPhoneNumber("");
		 
		 EntityIncidentMaster entityIncidentMaster=new EntityIncidentMaster();
		 
		 
		  if (m_strIncidentId != null && !m_strIncidentId.trim().isEmpty()) {
			  
			  String m_strEngId="",m_strAssetId="",m_strUrl="";String m_strAging="";
			  Long nLongEngId=0L;String m_strEngineerName="",m_strEngContact="";String m_strfollowupdateIrm="",m_strFollowuptimeIrm="";
			  
			  try {
				  entityIncidentMaster=serviceEntityIncidentMaster.returnIncidentDetailsList(m_strIncidentId);  
			  }catch(Exception ex) { System.out.println("Some error "); ex.printStackTrace(); entityIncidentMaster=null;  }

			  
			  if(entityIncidentMaster!=null) {
			        	  
				  
				  m_strAssetId=entityIncidentMaster.getAssetIdIm(); 
				  m_strEngId=String.valueOf(entityIncidentMaster.getEngineerEngmIm());
				  m_strAging=" "+String.valueOf(entityIncidentMaster.getIncageindaysIm());	  			  
		         
			        	  try {
			        		  nLongEngId=Long.valueOf(m_strEngId);
			        		  m_strEngContact=repositoryEntityEngineerMaster.returnValueForIdForWhatsappContact(nLongEngId, "N");
			        		  m_strEngineerName=repositoryEntityEngineerMaster.returnValueForIdForWhatsapp(nLongEngId, "N");
			        	    } catch(Exception ex) { ex.printStackTrace(); m_strEngContact=""; }
			        	  
			        	  
			        	  
			        	  if(m_strEngContact.trim().isEmpty()==false) {
			        		String modelId=this.sendWhatsappMessageToTechnician(m_strEngineerName,m_strEngContact,m_strIncidentId,
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
		  
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
				 		.contentType(MediaType.APPLICATION_JSON).body("BAD REQUEST");
	 }
	
	 
	 public String sendWhatsappMessageToTechnician(String m_strEngineerName,String m_strEngContact,String m_strIncidentId,
	     String m_strAssetId,String m_strAging,String m_strUrl,String m_strMessageType,String m_strEngId,String m_strUserId,
	     String m_strfollowupdateIrm,String m_strFollowuptimeIrm) {
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
	    		 m_strIdForMessage=serviceSendWatiMessage.sendWatiMessageWithOktaLibrary(dtoTemplateRequest,m_strEngContact);	 
	    	 }catch(Exception ex) {  ex.printStackTrace(); this.beanWati.setTicketidBeanWati(""); }
  
	    	 
	    	 if(m_strIdForMessage.trim().isEmpty()==false) {
	    		 serviceWhatsappChat.insertIntoWhatsappLogWatti(m_strIdForMessage,m_strEngContact,m_strIncidentId,m_strAssetId,m_strAging,m_strMessageType,
		    			 m_strUserId,m_strEngId,m_strTemplateName,m_strEngineerName);
		    	 
	    		 serviceChattingOnWhatsappMaster.insertIntoChatting(m_strIdForMessage,m_strEngContact,m_strIncidentId,m_strAssetId,m_strAging,m_strMessageType,
		    			 m_strUserId,m_strEngId,m_strTemplateName,m_strEngineerName,m_strIdForMessage);
	    	 }
	    	
		 }catch(Exception ex) { ex.printStackTrace(); this.beanWati.setUniqueKeyByPiyush(""); this.beanWati.setPhoneNumber(""); this.beanWati.setTicketidBeanWati(""); }
		 return m_strIdForMessage;
	 }
	 
	

	
}
