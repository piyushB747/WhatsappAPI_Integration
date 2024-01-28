package net.intelliuno.apicontrollerchatboat;
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
import net.intelliuno.service.ServiceChattingOnWhatsappMaster;
import net.intelliuno.jsonChatts.*;

@Tag(  
		name = "APIControllerFetchChatsData",
        description = "This Java Class will Fetch Chat Data"
)
@RestController
@RequestMapping(value="/fetchchatdatafromgateway")
@CrossOrigin(origins="*")
public class APIControllerFetchChatsData {

	
	@Autowired
	private ServiceChattingOnWhatsappMaster serviceChattingOnWhatsappMaster;
	
	@Operation(summary = "IT WILL FETCH CHAT DATAS  ")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Success",
	            content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))})
	})
	@GetMapping
	public ResponseEntity<?> sendChatsData(@RequestParam(name="m_strIncidentId",required = false)String m_strIncidentId,
			@RequestParam(name="phoneNumber",required = true)String m_strPhoneNumber,
			@RequestParam(name="m_strConversationId",required = true)String m_strConversationId,
			@RequestParam(name="m_strEngId",required = true)String m_strEngId,
			@RequestParam(name="userId",required = true)String m_strUserId,
			@RequestParam(name="m_strSelectedDate",required = false)String m_strSelectedDate) {
		 
        System.out.println("\u001B[34mThis is REDBLUE TEXT text\u001B[0m "+m_strSelectedDate);
        
	         System.out.println("GOING TO FETCH CHATS FROM APIControllerFetchChatsData JAVA CLASS STARTS");
		     System.out.println(m_strPhoneNumber+"  PHONE NUMBER GOING "+m_strConversationId+"TO FETCH CHAT DETAILS "+m_strEngId+" "+m_strUserId);
		     
		     if(m_strPhoneNumber!=null && !m_strPhoneNumber.equals("")) {
		        	  
		    	    	 List<JsonChat> l_ObjListJsonChat=serviceChattingOnWhatsappMaster.returnChatDataByPhoneNumber(m_strPhoneNumber,m_strSelectedDate);
			        	 
		    	    	 
		    	    	 System.out.println();
			        	 JsonChatDetails jsonChatDetails=this.chatListDetailsForAll(l_ObjListJsonChat,m_strPhoneNumber);
			        	 return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Send")
			   				  .contentType(MediaType.APPLICATION_JSON).body(jsonChatDetails);  	 	 
		    	     
		    	     
		     }
		     
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
				  .contentType(MediaType.APPLICATION_JSON).body("BAD REQUEST");
	}
	
	
	public  JsonChatDetails chatListDetailsForAll( List<JsonChat> l1,String m_strPhoneNumber) {
		
		JsonChatDetails jsonChatDetails=new JsonChatDetails();
		jsonChatDetails.setL_ObjListJsonChat(l1);
		jsonChatDetails.setPhoneNumber(m_strPhoneNumber);
		jsonChatDetails.setUserName(m_strPhoneNumber);
			
	    return jsonChatDetails;
	    
	}
	
}
