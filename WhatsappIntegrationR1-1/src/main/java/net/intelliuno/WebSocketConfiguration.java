package net.intelliuno;




import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{

	  @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/stomp-endpoint")
	           .setAllowedOrigins("https://keystone1.intelli.uno", "https://sales.intelli.uno:1943","http://localhost:8084")
               .withSockJS();
	        
	           // .setAllowedOrigins("http://localhost:8084") // Allow connections from the client's domain
	           // .setAllowedOrigins("https://keystone1.intelli.uno") // Allow connections from the client's domain
	          //.setAllowedOrigins("https://sales.intelli.uno:1943") // Allow connections from the client's domain
              	
	    }
	 
	  
	  @Override
	  public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.enableSimpleBroker("/topic");
	        registry.setApplicationDestinationPrefixes("/app");

	    }
	
	
	
}
