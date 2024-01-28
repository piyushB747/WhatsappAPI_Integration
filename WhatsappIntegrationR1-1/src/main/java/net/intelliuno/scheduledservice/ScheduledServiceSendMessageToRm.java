package net.intelliuno.scheduledservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import net.intelliuno.bean.BeanRMSendService;
import net.intelliuno.bean.BeanWati;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.commons.CommonUtils;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.watti.DtoTemplateRequest;
import net.intelliuno.entity.EntityWatiResponseMaster;
import net.intelliuno.entity.EntityWhatsappError;

import net.intelliuno.repository.RepoEntityWatiResponseMaster;
import net.intelliuno.repository.RepositoryEntityEngineerMaster;
import net.intelliuno.repository.RepositoryEntityWhatsappError;
import net.intelliuno.service.ServiceMessageBodyObject;
import net.intelliuno.service.ServiceWhatsappChat;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@EnableScheduling
public class ScheduledServiceSendMessageToRm {
	
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private RepositoryEntityWhatsappError repositoryEntityWhatsappError;
	
	@Autowired 
	private BeanWati beanWati;	

	@Autowired
	private CustomRepoEntityIncidentMaster custRepo;
	
	@Autowired
	private ServiceMessageBodyObject serviceMessageBodyObject;
	
	@Autowired
	private ServiceWhatsappChat serviceWhatsappChat;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired 
	private BeanRMSendService beanRMSendService;
	
	@Autowired 
	private RepoEntityWatiResponseMaster repoEntityWatiResponseMaster;

	@Scheduled(cron = "0 0 09 * * MON-SAT") 
    //@Scheduled(cron = "#{@generateDynamicCronExpression}")
    //@Scheduled(cron = "#{@scheduledTimeService.generateDynamicCronExpression('SEND_MESSAGE_TO_RM')}")
	public void sendMessagesToRmInTheMorining() {
		
		System.out.println("SERVICE  GOING TO START TO SEND RM ESCALATION ");
		this.beanRMSendService=applicationContext.getBean(BeanRMSendService.class);
		
	    String m_strMessageType="RM AVAILABILITY",m_strTemplateName="";
		String m_strIncidentId="",m_strAssetId="",m_strAging="",m_strfollowupdateIrm="",m_strFollowuptimeIrm="";
			 
		String m_strQueryForTemp="Select template_name_wtm from whatsapptemplatemaster_wtm where deleteflag_message_wtm='N' ";
		m_strQueryForTemp=m_strQueryForTemp+" and message_type_wtm='"+m_strMessageType+"' ";
			 
		try {
				 m_strTemplateName=custRepo.getStringValue(m_strQueryForTemp);
		}catch(Exception ex) { ex.printStackTrace(); }
		
		String m_strQuery = "SELECT typeid_em, CONCAT(fname_em, ' ', lname_em)As Name,contact_em  FROM " + CommonConstants.db_Name + ".engineermst_em WHERE deleteflag_em = 'N' AND role_rm_em = 15  "
						+ " And  contact_em is not null and resignedflag_em='N' ";
				
	    List<Object[]> l1;
		try {
			 l1=custRepo.returnListOfSelectStarFromConcept(m_strQuery);
		}catch(Exception ex) { ex.printStackTrace(); l1=null; }
				
        if(!l1.isEmpty()) {
			
			this.setInListOfPhoneNumber();
			
			String m_strEngId="",m_strEngineerName="",m_strEngContact="",m_strUserId="AutomatedMessage";
			try {
				for(Object[] m_strGetValue : l1) {
					
					  
				    try {
				        Thread.sleep(30000);             // Sleep for 1 minute (60000 milliseconds)
				    } catch (InterruptedException e) {
				        Thread.currentThread().interrupt(); // Restore interrupted status
				    }
					
					String m_strIdForMessage="";
					
					m_strEngId=String.valueOf(m_strGetValue[0]);
					m_strEngineerName=String.valueOf(m_strGetValue[1]);
					m_strEngContact=String.valueOf(m_strGetValue[2]);
					
					DtoTemplateRequest dtoTemplateRequest=new DtoTemplateRequest();
					dtoTemplateRequest=serviceMessageBodyObject.returnListOfDtoMessageForRMData(m_strIncidentId,m_strAssetId,m_strTemplateName,
		  		    		  m_strEngContact,m_strMessageType,m_strEngineerName,m_strAging,m_strfollowupdateIrm,m_strFollowuptimeIrm);
			    	
					 try {
			    		// m_strIdForMessage=serviceSendWatiMessage.sendWatiMessageWithOktaLibrary(dtoTemplateRequest,m_strEngContact);	 
			    		 m_strIdForMessage=this.sendWatiMessageWithOktaLibraryInRmServvices(dtoTemplateRequest,m_strEngContact);
			    	 }catch(Exception ex) {  ex.printStackTrace();  }
					
					 if(m_strIdForMessage.trim().isEmpty()==false) {
			    		 
						 serviceWhatsappChat.insertIntoWhatsappLogWatti(m_strIdForMessage,m_strEngContact,m_strIncidentId,m_strAssetId,m_strAging,m_strMessageType,
				    			 m_strUserId,m_strEngId,m_strTemplateName,m_strEngineerName);
						 
			    	 }
					 dtoTemplateRequest=null;
				}
			}catch(Exception ex) { ex.printStackTrace(); } 
		}
        
        
	}
	
	
    public void setInListOfPhoneNumber() {
    	String m_strQuery = "SELECT contact_em  FROM " + CommonConstants.db_Name + ".engineermst_em WHERE deleteflag_em = 'N' AND role_rm_em = 15  "
				+ " And  contact_em is not null and resignedflag_em='N' ";
    	
    	List<String> l1;
		try {
		   l1=custRepo.returnListOfSelectOneColumnFromConcept(m_strQuery);
		}catch(Exception ex) { ex.printStackTrace(); l1=null; }
		
		 if(!l1.isEmpty()) {
			
			List<String> l_strPhoneNumber=new ArrayList<String>();
			
			try {
				for(String m_strGetValue : l1) {
				  l_strPhoneNumber.add(m_strGetValue+"|SendToRm");
				}
			}catch(Exception ex) {  ex.printStackTrace(); }
		
			this.beanRMSendService.setL_strListOfRm(l_strPhoneNumber);
		}
		System.out.println(this.beanRMSendService.getL_strListOfRm()+" List OF EveryThing ");
    }
    
    public String sendWatiMessageWithOktaLibraryInRmServvices(DtoTemplateRequest dtoTemplateRequest,String m_strEngContact) {
		   String m_strEngName="";
		   try {
			   m_strEngName=repositoryEntityEngineerMaster.returnEngineerFromContactNumber(m_strEngContact);   
		   }catch (Exception ee) { ee.printStackTrace(); m_strEngName="";  }
		   
		 
		 String responseBody="";  String modelId="";
		 
		 @SuppressWarnings("unused")
		 int httpStatusCode=0;
		 String urlForSendingWati=CommonConstants.WATI_URL_FOR_PLAIN_WHATSAPP_MESSAGE+"?whatsappNumber="+m_strEngContact+"";
		 System.out.println(urlForSendingWati+ "   URL FOR WATI SENDING");
		
		 OkHttpClient client = new OkHttpClient();
	    	
		     Gson gson = new Gson();
	    	 String jsonRequestBody = gson.toJson(dtoTemplateRequest);
	    	 MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	    	    @SuppressWarnings("deprecation")
				RequestBody requestBody = RequestBody.create(JSON, jsonRequestBody);

	    	    Request request = new Request.Builder()
	    	   .url(urlForSendingWati)
	    	   .post(requestBody)
	    	   .addHeader("content-type", "text/json")
	           .addHeader("Authorization",CommonConstants.AUTHORIZATION_KEY_FOR_API_WATTI) // Add any other headers you need
	    	   .build();

	    	    
	    	    try {
					Response response1 = client.newCall(request).execute();
					if (response1.isSuccessful()) {
					    
						responseBody = response1.body().string();
					    
					    JSONObject jsonResponse = new JSONObject(responseBody);
				        
				        
				        try {
				        	JSONObject modelObject = jsonResponse.getJSONObject("model");
				        	modelId = modelObject.getJSONArray("ids").getString(0);	
				        }catch(Exception ex) { System.out.println("org.json.JSONException: JSONObject['model'] not found."); }
				        
				        System.out.println("Response Body: \n" + responseBody+" \n");
					    this.saveWatiResponse(responseBody,m_strEngContact,CommonUtils.nullToBlank(m_strEngName, false),modelId);   //ADDED ON 4OCTOBER
					   
				        
				        httpStatusCode = response1.code();
					    response1.close();
                        
					    
                        
					} else {
					
						  System.out.println("Request failed with HTTP code: " + response1.code());
					      System.out.println("Error message: " + response1.message());
					       EntityWhatsappError l_objErrorLog=new EntityWhatsappError();
						   l_objErrorLog.setErrorMessageWe("RM ERROR MESSAGE:=" +response1.message()+" "+response1.code());
						   l_objErrorLog.setPhoneEng(m_strEngContact);
						   l_objErrorLog.setEngNameWe(CommonUtils.nullToBlank(m_strEngName, false));
						   repositoryEntityWhatsappError.save(l_objErrorLog);
						
						   this.saveWatiResponse(response1.body().string(),m_strEngContact,CommonUtils.nullToBlank(m_strEngName, false),modelId);   //ADDED ON 4OCTOBER
					}
					
				} catch (IOException e) {e.printStackTrace(); this.beanWati.setUniqueKeyByPiyush(""); 
				   
				   System.out.println("There is an error in sending message");
				   EntityWhatsappError l_objErrorLog=new EntityWhatsappError();
				   l_objErrorLog.setErrorMessageWe("RM ERROR MESSAGE:= "+e.getMessage()+" "+e.getLocalizedMessage()+ " "+e.getCause()+" "+e.getStackTrace());
				   l_objErrorLog.setPhoneEng(m_strEngContact);
				   l_objErrorLog.setEngNameWe(CommonUtils.nullToBlank(m_strEngName, false));
				   repositoryEntityWhatsappError.save(l_objErrorLog);
				   
				}
	    	    
			return modelId;
		}
	 
    //SAVING WATI RESPONSE  04 OCTOBER 2023
     public void saveWatiResponse(String response1, String m_strEngContact, String engName,String modelId) {
    	try {
    	   EntityWatiResponseMaster entityWatiResponseMaster=new EntityWatiResponseMaster();
    	   entityWatiResponseMaster.setEngnameWr(engName);
    	   entityWatiResponseMaster.setEngphoneWr(m_strEngContact);
    	   entityWatiResponseMaster.setResponseWr("SENDIND RM= "+response1);
    	   entityWatiResponseMaster.setModelidWr(modelId);
    	   repoEntityWatiResponseMaster.save(entityWatiResponseMaster);
       }catch(Exception ex) {  System.out.println("ERROR IN SAVING WATI RESPONSE IN TABLE watiresponse_wr"); }
    }
    
}