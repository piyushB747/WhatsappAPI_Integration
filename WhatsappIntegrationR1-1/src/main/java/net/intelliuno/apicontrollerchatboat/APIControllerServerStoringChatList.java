package net.intelliuno.apicontrollerchatboat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.DtoRequestChatListObject;
import net.intelliuno.entity.EntityChatList;
import net.intelliuno.repository.RepoEntityChatList;


import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping(value="/apitosavechatlist")
@CrossOrigin(origins="*")
public class APIControllerServerStoringChatList {

	@Autowired
	private RepoEntityChatList repoEntityChatList;
	
	@Autowired
	private CustomRepoEntityIncidentMaster customRepo;
	
	@Autowired(required = true)
	private ModelMapper modelMapper;


	@Operation(summary = "It Will Return List Of ChatList For Chatting Session")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success",
	                content = {@Content(mediaType =  "application/json",schema =@Schema(implementation = DtoRequestChatListObject.class))})
	})
	@GetMapping("/getchatlistforwhatsapp")
    public ResponseEntity<?> returnListOfChatList(@RequestParam(name="m_strIncidentId",required=false) String m_strIncidentId){
		
		//List<DtoRequestChatListObject> dtoRequestChatListObject=new ArrayList<DtoRequestChatListObject>();
		List<DtoRequestChatListObject>  m_ObjListOfIncidentMaster= repoEntityChatList.
				returnListOfTicketCreatedByMobile()
				.stream().map(this::convertEntityToDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Integration")
		 		.contentType(MediaType.APPLICATION_JSON).body(m_ObjListOfIncidentMaster);
	}
	
	@PostMapping
    public ResponseEntity<?> serverStoringChatList(@RequestBody DtoRequestChatListObject requestObject,
    	       	                   @RequestParam(name="m_strUserId",required=false) String m_strUserId,
   	     	                       @RequestParam(name="m_strIncidentId",required=false) String m_strIncidentId){

		
		int n_intPhoneNumberCount=0;
		if(requestObject!=null) {
 
			try {
				String m_strCountOfEngAndTicketId="select count(*) from "+CommonConstants.db_Name+".whatsappchatlist_wcl where phone_number_wcl='"+requestObject.getPhoneNumber()+"' ";
						
				try {
					n_intPhoneNumberCount	= customRepo.getOpenCancelledCountForExecutiveWithParameterizedNative(m_strCountOfEngAndTicketId);
				}catch(Exception ex) {  ex.printStackTrace();  n_intPhoneNumberCount=0; }
			}catch(Exception ex) {  } 
			
			if(n_intPhoneNumberCount==0) {
				System.out.println("Going To ADD FRESH");
				this.saveChatList(requestObject);
			}else {    
				System.out.println("Going To DELETE OLD");
				repoEntityChatList.deleteByPhoneNumberWcl(String.valueOf(requestObject.getPhoneNumber()));    
				this.saveChatList(requestObject);
			}
		}
		return null;
	}
		 
	
	public void saveChatList(DtoRequestChatListObject requestObject) {
	  try {
			EntityChatList e1=new EntityChatList();
			
			e1.setDateAndTimeToShowWcl(requestObject.getDateAndTimeToShow());
			e1.setMessageTextWcl(requestObject.getMessageText());
			e1.setPhoneNumberWcl(requestObject.getPhoneNumber());
			e1.setDeleteflagWcl("N");			
			try {
				repoEntityChatList.save(e1);
			}catch(Exception ex) { ex.printStackTrace(); }
		
		
	  }catch(Exception ex) {ex.printStackTrace(); }
	}
	
	/**Converting Entity Into DTO using Library**/
	public DtoRequestChatListObject convertEntityToDto(EntityChatList user) {		
		DtoRequestChatListObject userLocationDto=new DtoRequestChatListObject();
		userLocationDto=modelMapper.map(user, DtoRequestChatListObject.class);
		return userLocationDto;		
	}
	
}
