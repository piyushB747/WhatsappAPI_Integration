package net.intelliuno.apicontrollerchatboat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.intelliuno.bean.BeanSessionMessageWati;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.service.ServiceChattingOnWhatsappMaster;
import net.intelliuno.service.ServiceWhatsappChat;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Tag(name = "APIControllerSendSessionMessages", description = "This Java Class will Send Session Wati Messages To User")
@RestController
@RequestMapping(value = "/sendsessionwhatsappmessages")
@CrossOrigin(origins = "*")
public class APIControllerSendSessionMessages {

	public static String ERROR_MESSAGE="";
	
	@Autowired 
	private BeanSessionMessageWati beanSessionWati;
	
	@Autowired
	private ServiceChattingOnWhatsappMaster serviceChattingOnWhatsappMaster;
	
	@Autowired
	private ServiceWhatsappChat serviceWhatsappChat;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Operation(summary = "The class will send session messages to user when ticket is not expired ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success", content = {
			@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }) })
	@GetMapping
	public ResponseEntity<?> sendSessionWhatsappMessages(
			@RequestParam(name = "m_strUserId", required = false) String m_strUserId,
			@RequestParam(name = "m_strPhoneNumber", required = false) String m_strPhoneNumber,
			@RequestParam(name = "m_strEngId", required = false) String m_strEngId,
			@RequestParam(name = "m_strEngName", required = false) String m_strEngName,
			@RequestParam(name = "m_strMessage", required = false) String m_strMessage) {

		System.out.println("/**CONCEPT STATRT \n USER ID= " + m_strUserId + " \n PHONE NUMBER=  " + m_strPhoneNumber
				+ " \n MESSAGE= " + m_strMessage + " \n ENGNAME= "+m_strEngId+ " "+m_strEngName);

		this.beanSessionWati=applicationContext.getBean(BeanSessionMessageWati.class);
		this.beanSessionWati.setConverstationId("");
		this.beanSessionWati.setIsSendBySessionWatiMessage("");
		this.beanSessionWati.setUniqueIdBySessionMessage("");
		String uniqueIdBySessionMessage="";
		if (m_strPhoneNumber != null && !m_strPhoneNumber.equals("") && m_strMessage != null
				&& !m_strMessage.equals("")) {
		
			uniqueIdBySessionMessage=this.sendMessageToWatiAPI(m_strPhoneNumber, m_strMessage, m_strUserId,m_strEngName,m_strEngId);
			
			if(uniqueIdBySessionMessage.trim().isEmpty()==false) {
				return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Send")
						.contentType(MediaType.APPLICATION_JSON).body(uniqueIdBySessionMessage);
			}
			
		}

		System.out.println(ERROR_MESSAGE);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
				.contentType(MediaType.APPLICATION_JSON).body(ERROR_MESSAGE);
	}

	public String sendMessageToWatiAPI(String m_strPhoneNumber, String m_strMessage, String m_strUserId,String m_strEngName,String m_strEngId) {
		String uniqueIdBySessionMessage="";
		
		try {
			
			uniqueIdBySessionMessage=this.sendSessionWhatsappMessageToWatiAPI(m_strPhoneNumber, m_strMessage, m_strUserId,m_strEngName,m_strEngId);
		}catch(Exception ex) {  ex.printStackTrace(); }
		  
		   // System.out.println("uniqueIdBySessionMessage= "+uniqueIdBySessionMessage);
			if(uniqueIdBySessionMessage.trim().isEmpty()==false && !uniqueIdBySessionMessage.equals("")) {
				
				serviceWhatsappChat.insertIntoWhatsappLogWattiForSessionMessages(uniqueIdBySessionMessage,m_strPhoneNumber,m_strMessage,m_strUserId,m_strEngName,m_strEngId);
				
			}
		
		
		return uniqueIdBySessionMessage;
	}
	
	private String sendSessionWhatsappMessageToWatiAPI(String m_strPhoneNumber, String m_strMessage, String m_strUserId,String m_strEngName,String m_strEngId) {
      
		String m_strEngContact="+91"+m_strPhoneNumber;
		
		String watiTicketId="";
		String uniqueIdBySessionMessage="";String responseBody="";String watiResult="";String conversationId="";
		try {
			
			
			@SuppressWarnings("deprecation")
			RequestBody requestBody = RequestBody.create(null, new byte[0]);

			
			 @SuppressWarnings("unused")
			 int httpStatusCode=0;
			 String urlForSendingSessionMessageWati=CommonConstants.WATI_URL_FOR_SENT_SESSION_MESSAGE+""+m_strEngContact+"?messageText="+m_strMessage;
			 System.out.println(urlForSendingSessionMessageWati+ "   URL FOR WATI SENDING");
			
         		  OkHttpClient client = new OkHttpClient();

		    	    Request request = new Request.Builder()
		    	   .url(urlForSendingSessionMessageWati)
		    	   .post(requestBody)
		           .addHeader("Authorization",CommonConstants.AUTHORIZATION_KEY_FOR_API_WATTI) // Add any other headers you need
		    	   .build();
		    	    try {
						Response response1 = client.newCall(request).execute();
						if (response1.isSuccessful()) {
							responseBody = response1.body().string();
				          
							ObjectMapper mapper = new ObjectMapper();
				            
							JsonNode rootNode = mapper.readTree(responseBody);
				            watiResult = rootNode.get("result").asText();
				           
				            if(watiResult.equals("success")) {
				            	
				            	uniqueIdBySessionMessage = rootNode.get("message").get("id").asText();
					            conversationId= rootNode.get("message").get("conversationId").asText();
					            watiTicketId=rootNode.get("message").get("ticketId").asText();  
					            if(uniqueIdBySessionMessage.trim().isEmpty()==false && !uniqueIdBySessionMessage.equals("")) {
					            	serviceChattingOnWhatsappMaster.insertIntoWhatsappWattiForSessionMessages(m_strMessage, m_strPhoneNumber, uniqueIdBySessionMessage, m_strUserId, m_strEngId, m_strEngId,conversationId,watiTicketId);
					            }
					            
					            this.beanSessionWati.setIsSendBySessionWatiMessage("TRUE");
								this.beanSessionWati.setUniqueIdBySessionMessage(" "+uniqueIdBySessionMessage);
								this.beanSessionWati.setM_strPhoneNumber(m_strPhoneNumber);
								this.beanSessionWati.setConverstationId(conversationId);
					            
				            }else if(watiResult.equals("false")) {
				            	this.beanSessionWati.setIsSendBySessionWatiMessage("");
								this.beanSessionWati.setUniqueIdBySessionMessage("");
								this.beanSessionWati.setM_strPhoneNumber("");
								this.beanSessionWati.setConverstationId("");
								  try { ERROR_MESSAGE= rootNode.get("message").asText();
								  }catch(Exception ex) {  ERROR_MESSAGE= rootNode.get("info").asText();  }
				            }
				            
						}
			
		    	    }catch(Exception ex) { ex.printStackTrace(); }

			
		}catch(Exception ex) {  ex.printStackTrace(); }
		System.out.println("uniqueIdBySessionMessage= "+uniqueIdBySessionMessage);
      return uniqueIdBySessionMessage;
	}

}
