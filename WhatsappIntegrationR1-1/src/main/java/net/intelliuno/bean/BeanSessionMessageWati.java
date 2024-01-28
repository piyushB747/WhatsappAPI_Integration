package net.intelliuno.bean;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BeanSessionMessageWati {

    private String uniqueIdBySessionMessage;
    
    private String isSendBySessionWatiMessage;
    
    private String converstationId;
    
    private String m_strPhoneNumber;
    
	
}
