package net.intelliuno.service;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import net.intelliuno.bean.BeanNotification;

@Service
@EnableScheduling
public class ServiceNotificationOfWhatsapp {
	   
	@Autowired 
	private BeanNotification beanNotification;
	
        @Scheduled(fixedRate = 1000) // Run every 2 seconds (2000 milliseconds)
	    public void myScheduledMethod() {

	        
	        String m_strNotifiationReceived="";
	        try {
	        	m_strNotifiationReceived=this.beanNotification.getNotificationRecevied();
	        }catch(Exception ex) {   m_strNotifiationReceived="FALSE"; }
	        
	        if(m_strNotifiationReceived!=null  && m_strNotifiationReceived.equals("")==false) {
	        	if(m_strNotifiationReceived.equals("TRUE")) {
                      
	        		this.sendNotification();
	        		
		        }	
	        }
			   this.beanNotification.setNotificationRecevied("");
			   this.beanNotification.setEngName("");
			   this.beanNotification.setMessage("");
			   this.beanNotification.setPhoneNumber("");
	    }
        
        public void sendNotification() {
        	  try {
        	        HttpHeaders headers = new HttpHeaders();
        	        headers.set("custom-header", "YourCustomHeaderValue"); // Set your custom header value here

        	        RestTemplate restTemplate = new RestTemplate();
        	        HttpEntity<String> entity = new HttpEntity<>(headers);

        	        ResponseEntity<String> response = restTemplate.exchange(
        	            "http://localhost:8080/api", 
        	            HttpMethod.GET,
        	            entity,
        	            String.class
        	        );

        	        String responseBody = response.getBody();
        	        System.out.println("Response: " + responseBody);

        	    } catch (Exception ex) {
        	        //ex.printStackTrace();
        	    }
        }
        
}
*/