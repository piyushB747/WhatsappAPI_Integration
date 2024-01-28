package net.intelliuno.service;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.intelliuno.bean.BeanWati;
import net.intelliuno.commons.CommonConstants;
import net.intelliuno.commons.CommonUtils;
import net.intelliuno.dto.watti.DtoTemplateRequest;
import net.intelliuno.entity.EntityWatiResponseMaster;
import net.intelliuno.entity.EntityWhatsappError;
import net.intelliuno.repository.RepoEntityWatiResponseMaster;
import net.intelliuno.repository.RepositoryEntityEngineerMaster;
import net.intelliuno.repository.RepositoryEntityWhatsappError;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@Service
public class ServiceSendWatiMessage {

	
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private RepositoryEntityWhatsappError repositoryEntityWhatsappError;
	
	@Autowired 
	private RepoEntityWatiResponseMaster repoEntityWatiResponseMaster;

	
	@Autowired 
	private BeanWati beanWati;	
	
	 public String sendWatiMessageWithOktaLibrary(DtoTemplateRequest dtoTemplateRequest,String m_strEngContact) {
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

           //System.out.println(jsonRequestBody);

	    	 
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
						   l_objErrorLog.setErrorMessageWe(response1.message()+" "+response1.code());
						   l_objErrorLog.setPhoneEng(m_strEngContact);
						   l_objErrorLog.setEngNameWe(CommonUtils.nullToBlank(m_strEngName, false));
						   repositoryEntityWhatsappError.save(l_objErrorLog);
						
						   this.saveWatiResponse(response1.body().string(),m_strEngContact,CommonUtils.nullToBlank(m_strEngName, false),modelId);   //ADDED ON 4OCTOBER
						   
					}
					
				} catch (IOException e) {e.printStackTrace(); this.beanWati.setUniqueKeyByPiyush(""); 
				   System.out.println("There is an error in sending message");   
				   
				   EntityWhatsappError l_objErrorLog=new EntityWhatsappError();
				   l_objErrorLog.setErrorMessageWe(e.getMessage()+" "+e.getLocalizedMessage()+ " "+e.getCause()+" "+e.getStackTrace());
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
	    	   entityWatiResponseMaster.setResponseWr(response1);
	    	   entityWatiResponseMaster.setModelidWr(modelId);
	    	   repoEntityWatiResponseMaster.save(entityWatiResponseMaster);
	       }catch(Exception ex) {  System.out.println("ERROR IN SAVING WATI RESPONSE IN TABLE watiresponse_wr"); }
	    }
	 
}
