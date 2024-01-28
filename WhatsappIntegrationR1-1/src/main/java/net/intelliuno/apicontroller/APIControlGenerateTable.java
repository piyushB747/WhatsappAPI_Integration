package net.intelliuno.apicontroller;

import java.util.List;

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
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.DtoWhatsappChatting;
import net.intelliuno.dto.watti.DtoJsonResponseWati;
import net.intelliuno.entity.EntityIncidentMaster;
import net.intelliuno.repository.RepositoryEntityEngineerMaster;
import net.intelliuno.service.ServiceChattingOnWhatsappMaster;
import net.intelliuno.service.ServiceEntityIncidentMaster;
@Tag(
        name = "APIControlGenerateTable",
        description = "This Java class is responsible for generating a chat list on the single incident page by sending a list of data."
)
@RestController
@RequestMapping(value="/generatetableforsingleincident")
@CrossOrigin(origins="*")
public class APIControlGenerateTable {

	public static String m_strRM_ID="";
	public static String m_strRM_NUMBER="";
	
	@Autowired
	private ServiceEntityIncidentMaster serviceEntityIncidentMaster;
	
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private ServiceChattingOnWhatsappMaster serviceChattingOnWhatsappMaster;

	@Autowired
	private CustomRepoEntityIncidentMaster customRepoEntityIncidentMaster;
	
	@SuppressWarnings("unused")
	@Operation(summary = "It will accept an incident ID, and using that ID, it will fetch the mobile number and engineer ID. Additionally, it will retrieve the chat list from the 'whatsappchatmaster_wac' table.")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success",
	                content = {@Content(mediaType =  "application/json",schema =@Schema(implementation = DtoJsonResponseWati.class))})
	 })
	 @GetMapping
     public ResponseEntity<?> returnToAPIInteraikt(
    		 @RequestParam(name="m_strIncidentId",required=true) String m_strIncidentId){
		 
		 EntityIncidentMaster entityIncidentMaster=new EntityIncidentMaster();

		 System.out.println(CommonConstants.green+"--------In API APIControlGenerateTable-------"+CommonConstants.reset+" INCIDENT ID="+m_strIncidentId);
		 DtoJsonResponseWati dtoJsonResponse=new DtoJsonResponseWati();
		 
		  if (m_strIncidentId != null && !m_strIncidentId.trim().isEmpty()) {
			  m_strRM_ID="";m_strRM_NUMBER="";
			  String m_strEngId="",m_strAssetId="";
			  Long nLongEngId=0L;String m_strEngineerName="",m_strEngContact="";
			  
			  try {
				  entityIncidentMaster=serviceEntityIncidentMaster.returnIncidentDetailsList(m_strIncidentId);  
			  }catch(Exception ex) { System.out.println("Some error "); ex.printStackTrace(); entityIncidentMaster=null;  }

			  if(entityIncidentMaster!=null) {
				  m_strAssetId=entityIncidentMaster.getAssetIdIm(); 
				  m_strEngId=String.valueOf(entityIncidentMaster.getEngineerEngmIm());
			        	  
			        	  try {
			        		  nLongEngId=Long.valueOf(m_strEngId);
			        		  m_strEngContact=repositoryEntityEngineerMaster.returnValueForIdForWhatsappContact(nLongEngId, "N");
			        		  m_strEngineerName=repositoryEntityEngineerMaster.returnValueForIdForWhatsapp(nLongEngId, "N");
			        	    } catch(Exception ex) { ex.printStackTrace(); m_strEngContact=""; }
			        	  
			        	  System.out.println("m_strEngId "+m_strEngId+"  "+m_strEngContact);
			        	  String m_strRmResponse=this.checkRmAvailability(nLongEngId);
			        	  
			        	  List<DtoWhatsappChatting> l_objList=serviceChattingOnWhatsappMaster.findUsersByIncidentIdAndPhone( m_strIncidentId,  m_strEngContact,
			        			  m_strEngContact) ;
			        	  
			        	  if (l_objList != null && !l_objList.isEmpty()) {
			        		 
			        		  dtoJsonResponse.setStatusOfRM(m_strRmResponse);
			        		  dtoJsonResponse.setTableData(l_objList);
			        		  
			        		  dtoJsonResponse.setRmNumber(m_strRM_NUMBER);
			        		  dtoJsonResponse.setRmId(m_strRM_ID);
			        		  
			        		  
			        		  return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Send")
					  			 		.contentType(MediaType.APPLICATION_JSON).body(dtoJsonResponse);
			        		  
			        		  
			        	  }else {
                              System.out.println("There is no data ");     			   
                              dtoJsonResponse.setTableData(l_objList);
			        		  return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
					  			 		.contentType(MediaType.APPLICATION_JSON).body(dtoJsonResponse);  
			        	  }
			        	  
			        	  
			  }
			  
		  }
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
			 		.contentType(MediaType.APPLICATION_JSON).body("BAD REQUEST");
	 }
	
	
	
	public String checkRmAvailability(Long nLongEngId){
		String m_strAvailability="";String m_strPhoneNumber="";
		try {
			String m_strRmId="";
			try {
				m_strRmId=repositoryEntityEngineerMaster.returnValueOfReportingManager(nLongEngId, "N");	
			}catch(Exception ex) {  System.out.println("ERROR IN MESSAGES"); }
			
			
			if(m_strRmId!=null  &&  m_strRmId.equals("")==false) {
				m_strRM_ID=m_strRmId;	
				String m_strQuery="select message_replied_rsw from  "+CommonConstants.db_Name+".rmescalatewhatsapp_rsw where "
						+ " rm_id_rsw='"+m_strRmId+"' and date(creation_rsw)='"+CommonConstants.todayFormattedDate+"' order by id_rsw limit 1";
				
				try {
					m_strAvailability=customRepoEntityIncidentMaster.getStringValue(m_strQuery);	
				}catch(Exception ex) { System.out.println("ERROR IN MESSAGES IN MESSAGE REPLIED"); }
            	
				String m_strQueryForPhoneNumber="select rm_phonenumber_rsw from  "+CommonConstants.db_Name+".rmescalatewhatsapp_rsw where "
						+ " rm_id_rsw='"+m_strRmId+"' and date(creation_rsw)='"+CommonConstants.todayFormattedDate+"' order by id_rsw limit 1";
				try {
					m_strPhoneNumber=customRepoEntityIncidentMaster.getStringValue(m_strQueryForPhoneNumber);	
				}catch(Exception ex) { System.out.println("ERROR IN MESSAGES IN MESSAGE REPLIED"); }
            	
				
			}
		}catch(Exception ex) { ex.printStackTrace(); }
		if(m_strAvailability.equals("")) {  m_strAvailability="Not Respond";  }
		if(m_strPhoneNumber.equals("")) {  m_strPhoneNumber="";  }
		
		m_strRM_NUMBER=m_strPhoneNumber;
		
		return m_strAvailability;
	}
	
	
}
